package com.twoX.agit.student.Controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.twoX.agit.after.service.AfterSchoolBoardService;
import com.twoX.agit.after.vo.AfterSchoolBoard;
import com.twoX.agit.after.vo.AfterSchoolStudent;
import com.twoX.agit.board.model.vo.HmSubmit;
import com.twoX.agit.board.model.vo.ParentsBoard;
import com.twoX.agit.common.template.Template;
import com.twoX.agit.common.vo.PageInfo;
import com.twoX.agit.member.model.vo.AfterSchool;
import com.twoX.agit.member.model.vo.Homework;
import com.twoX.agit.member.model.vo.Student;
import com.twoX.agit.student.service.StudentService;

@CrossOrigin
@Controller
public class StudentController {
	
	private final StudentService studentService;
	private final AfterSchoolBoardService afterschoolService;
	
	@Autowired
	public StudentController(StudentService studentService,AfterSchoolBoardService afterschoolService) {
		this.studentService = studentService;
		this.afterschoolService = afterschoolService;
	}
	
	@RequestMapping("homework")
	public String homeworkList(@RequestParam(value = "cpage", defaultValue = "1") int currentPage, HttpSession session, Model model) {
	    // 세션에서 로그인된 사용자 정보 가져오기
	    Student loginStudent = (Student) session.getAttribute("loginUser");

	    // 로그인된 사용자가 없으면 로그인 페이지로 리다이렉트
	    if (loginStudent == null) {
	        model.addAttribute("message", "로그인 정보가 없습니다.");
	        return "member/login_student"; // 로그인 페이지로 이동
	    }

	    // 로그인된 사용자의 classCode와 stuId 확인
	    String classCode = loginStudent.getClassCode();
	    String stuId = loginStudent.getStuId();
	    String status = loginStudent.getStatus();
	    
	    int homeworkCount = studentService.selectListCount(loginStudent.getClassCode(), loginStudent.getStuId());
	    
	    PageInfo pi = Template.getPageInfo(homeworkCount, currentPage, 5, 5);
	    
	    ArrayList<Homework> list = studentService.selectStudentHomeworkList(loginStudent.getClassCode(), loginStudent.getStuId(), pi);
	    session.setAttribute("homeworkList", list);
	    model.addAttribute("pi", pi);
	    
	    return "student/homework"; // 숙제 리스트 페이지로 이동
	}

	// 학생 숙제 조회
	@RequestMapping("homework_detail")
	public String studentHomeworkDetail(@RequestParam(value = "boNo") int boNo, @RequestParam(value = "cpage", defaultValue = "1") int currentPage, HttpSession session, Model model) {
		Homework npage = studentService.selectNowHomework(boNo);
		
		model.addAttribute("npage", npage);
		model.addAttribute("cpage", currentPage);
		System.out.println(npage);
		return "student/homeworkSubmit";
	}
	
	// 학생 숙제 제출
	@RequestMapping("enroll.homework_student")
	public String enrollHomeworkStu(@ModelAttribute Homework h, @ModelAttribute HmSubmit hm, @RequestParam MultipartFile upfile, HttpSession session, Model model) {
		// 로그인 정보 확인
		Student s = (Student) session.getAttribute("loginUser");
		if(s == null) {
			model.addAttribute("message", "로그인이 필요합니다.");
			return "redirect:/login_student";
		}
		
		h.setClassCode(s.getClassCode());
		System.out.println("로그인한 유저의 클래스 코드 : " + s.getClassCode());
		
		// HOMEWORK 테이블에서 해당 게시글의 정보 가져오기
		Homework homework = studentService.getHomeworkByBoNo(h.getBoNo());
		
		System.out.println("선택한 게시글 번호 : " + h.getBoNo());
		System.out.println("homework : " + homework);
		
		if(homework == null) {
			model.addAttribute("message", "유효하지 않은 게시글입니다.");
			return "errorPage";
		}

		// 로그인된 학생의 클래스 코드와 글을 올린 선생님의 클래스 코드 비교
		if(!s.getClassCode().equals(homework.getClassCode())) {
			model.addAttribute("message", "권한이 없습니다.");
			return "errorPage";
		}
		
		// 파일 업로드
		if (!upfile.getOriginalFilename().equals("")) {
		    // 파일 저장 및 변경된 파일명 생성
		    String changeName = Template.saveFile(upfile, session, "/resources/homework_files/");
		    
		    // HmSubmit 객체에 파일 정보 설정
		    hm.setChangeName(changeName); // 변경된 파일명 (DB에는 저장되지 않음)
		    hm.setFilePath("/resources/file/homework_files/" + changeName); // 저장 경로
		    hm.setOriginName(upfile.getOriginalFilename()); // 원본 파일명
		}
		
		hm.setStuId(s.getStuId());
		hm.setStatus("Y");
		
		int result = studentService.submitHomework(hm);
		
		if(result > 0) {
			model.addAttribute("message", "숙제가 제출 되었습니다!");
		}else {
			model.addAttribute("message", "숙제가 제출되지 않았습니다. 다시 시도해주세요.");
		}
		
		return "redirect:/homework_detail?boNo=" + hm.getBoNo();
	}
	
	//방과후 반 참가
    @RequestMapping("afterschoolStart.stu")
    public String afterschoolStartStudent(AfterSchoolStudent as, Student s, HttpSession session, Model model) {
        Student loginUser = (Student) session.getAttribute("loginUser");
        Map<String, String> map = new HashMap<>();
        String code = as.getCode();
        String stuId = loginUser.getStuId();
        
        System.out.println(code + " / " + stuId);
        
        
        map.put("code", code);
        map.put("stuId", stuId);
        System.out.println(loginUser);
        
        int result = studentService.updateAfterschoolCode(map);
        
        if (result > 0) {
            session.setAttribute("alertMsg", "참가 성공");
            return "redirect:/afterschool";
        } else { 
            model.addAttribute("alertMsg", "참가 실패");
            return "redirect:/";
        }
    }
	
    //신청 여부 및 승인여부
    @RequestMapping("afterschoolPage")
    public String afterschoolPage(AfterSchoolStudent as, Student s, HttpSession session, Model model) {
    	 Student loginUser = (Student) session.getAttribute("loginUser");
    	
    	System.out.println("방과후 로그인 아이디 : " + loginUser.getStuId() );
    	String stuId = loginUser.getStuId();
    	
    	System.out.println("1: "  + stuId);
    	
    	
    	AfterSchoolStudent afterschoolStudent = studentService.afterschoolStart(stuId);
    	
    	if(afterschoolStudent == null) {
    		model.addAttribute("errorMsg", "참여한 방과후가 없음");
    		return "student/afterSchoolStart";
    	}else{
    		if(afterschoolStudent.getStatus().equals("N")) {
    			System.out.println("승인 필요");
    			model.addAttribute("errorMsg","승인 승락 필요");
    			return "student/afterSchoolStart";
    		}else {
    			session.setAttribute("loginUser", loginUser);
    	        session.setAttribute("afterschoolStudent", afterschoolStudent);
    	        System.out.println("방과후 참가 성공");
    	        return "redirect:/afterSchool";
    		}
	        }
    	
    	}
    
    
    //방과후 참가 후 페이지
	@RequestMapping("afterSchool")
	public String studentAfterschool(@RequestParam(value="cpage",defaultValue="1") int currentPage,AfterSchoolStudent as,HttpSession session, Model model) {
		Student loginUser = (Student) session.getAttribute("loginUser");
		String stuId = loginUser.getStuId();

		String code =  studentService.afterschoolCode(stuId);
		
		AfterSchoolStudent afterschoolStudent = studentService.afterschoolStart(stuId);
		AfterSchool afterschool = studentService.afterschool(code);
		String teacherName = studentService.afterschoolTeacher(afterschool.getTcId());
		
		
		model.addAttribute("afterschool",afterschool);
		model.addAttribute("teacherName",teacherName);
		
		
		session.setAttribute("code", code);
		model.addAttribute("className", code);
		
		
		//내가 쓴 글 리스트 조회
		int afterBoardCount = afterschoolService.selectListCount(loginUser.getStuId());
		
		PageInfo pi = Template.getPageInfo(afterBoardCount, currentPage, 5, 6);
		
		ArrayList<AfterSchoolBoard> list = afterschoolService.selectStudentBoardList(loginUser.getStuId(),pi);
		session.setAttribute("boardList", list);
		model.addAttribute("pi",pi);
		
		
		return "student/afterSchool";
	}
	
	//글 상세 조회
	@RequestMapping("detail_after")
	public String studentAfterschoolDetail(@RequestParam(value="boNo")int boNo,@RequestParam(value="cpage",defaultValue="1") int currentPage,HttpSession session,Model model) {
		
			AfterSchoolBoard npage = afterschoolService.selectNowBoard(boNo);
			model.addAttribute("npage",npage);
			model.addAttribute("cpage",currentPage);
			return "student/afterSchoolBoardDetail";
	}
	
	@RequestMapping("enroll_afterschoolPage")
	public String enroll_afterschoolPage() {
		return "student/enrollafterSchoolBoard";
	}
	
	//글 등록하기
	@RequestMapping("enroll_afterschool")
	public String enroll_afterschool(AfterSchoolBoard asb, MultipartFile upfile, HttpSession session) {
		Student loginUser = (Student)session.getAttribute("loginUser");
		String code =  studentService.afterschoolCode(loginUser.getStuId());
		
		System.out.println(upfile);
		if(!upfile.getOriginalFilename().equals("")) {
			String changeName = Template.saveFile(upfile, session, "/resources/file/");
			
			asb.setChangeName("/resources/file/" + changeName);
			asb.setOriginName(upfile.getOriginalFilename());
		}
		
		asb.setStuId(loginUser.getStuId());
		asb.setCode(code);
		
		int result = afterschoolService.insertAfterschoolBoard(asb);
		if(result > 0) {
			return "redirect:/afterSchool";
		}else {
			session.setAttribute("alertMsg", "작성실패");
			return "student/myPage";
		}
		
	}
	
	//글 수정하기 페이지로 이동
	@RequestMapping("after_modify")
	public String board_modify(@RequestParam(value="boNo")int boNo,@RequestParam(value="cpage", defaultValue="1") int currentPage,HttpSession session,Model model) {
		AfterSchoolBoard npage = afterschoolService.selectNowBoard(boNo);
		session.setAttribute("npage",npage);
		session.setAttribute("cupage",currentPage);
		return "student/modifyAfterschool";
	}
	
	//글 수정하기
	@RequestMapping("update.afterSt")
	public String update_afterschoolStu(AfterSchoolBoard asb,MultipartFile reupfile,HttpSession session) {
		Student loginUser = (Student)session.getAttribute("loginUser");
		if(!reupfile.getOriginalFilename().equals("")) {
			//기존 첨부파일이 있다 -> 기존파일을 삭제
			if(asb.getOriginName() != null) {
				new File(session.getServletContext().getRealPath(asb.getChangeName())).delete();
			}
			
			//새로운 첨부파일을 서버에 업로드하기
			String changeName = Template.saveFile(reupfile, session, "/resources/file/");
			
			asb.setOriginName(reupfile.getOriginalFilename());
			asb.setChangeName( "/resources/file/" + changeName);
		}else {
			AfterSchoolBoard opb = (AfterSchoolBoard)session.getAttribute("npage");
			asb.setOriginName(opb.getOriginName());
			asb.setChangeName(opb.getChangeName());
		}
		
		int result = afterschoolService.updateAfterschoolBoard(asb);
		if(result > 0) {
			session.setAttribute("alertMsg", "수정에 성공하였습니다.");
			return "redirect:/afterSchool";
		}else {
			session.setAttribute("alertMsg", "수정실패");
			return "student/myPage";
		}
		
	}
	
	//글 삭제
	@RequestMapping("delete.afterSt")
	public String delete_afterschoolStu(@RequestParam(value="boNo")int boNo,AfterSchoolBoard asb,HttpSession session) {
		Student loginUser = (Student)session.getAttribute("loginUser");
		asb.setStuId(loginUser.getStuId());
		
		int result = afterschoolService.deleteAfterschoolBoard(asb);
		if(result > 0 ) {
			session.setAttribute("alertMsg", "삭제 성공");
			return "redirect:/afterSchool";
		}else {
			session.setAttribute("alertMsg", "삭제 실패");
			return "student/myPage";
		}
	}
	
	//글 사진업로드
	@ResponseBody
	@PostMapping("afterupload")
	public String upload(List<MultipartFile> fileList, HttpSession session) {
		
		List<String> changeNameList = new ArrayList();
		for(MultipartFile f : fileList) {
			changeNameList.add(Template.saveFile(f, session, "/resources/img/after/"));
		}
		return new Gson().toJson(changeNameList);
	}
	
}
