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
import com.twoX.agit.common.template.Template;
import com.twoX.agit.common.vo.PageInfo;
import com.twoX.agit.member.model.vo.AfterSchool;
import com.twoX.agit.member.model.vo.Homework;
import com.twoX.agit.member.model.vo.Student;
import com.twoX.agit.member.model.vo.Teacher;
import com.twoX.agit.student.model.vo.HomeworkSubmit;
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
	@SuppressWarnings("unused") // 불필요한 컴파일러 경고 무시
	@RequestMapping("homework_detail")
	public String studentHomeworkDetail(@RequestParam("boNo") int boNo,
										@RequestParam(value = "cpage", defaultValue = "1") int currentPage,
										HttpSession session,
										Model model) {
		// 로그인 한 학생 정보
		Student s = (Student) session.getAttribute("loginUser");
		
		// 클릭한 숙제 정보 불러오기
		Homework npage = studentService.selectNowHomework(boNo);
		
		// 현재 학생이 제출한 숙제 상태를 가져옴
	    HomeworkSubmit hm = studentService.selectHomeworkSubmit(boNo, s.getStuId());
	    
	    
	    // 모델에 데이터 추가
		model.addAttribute("npage", npage);
		model.addAttribute("cpage", currentPage);
		
		
		if (hm == null) {
	        // hm_submit에 데이터가 없으면 제출 페이지로 이동
	        return "student/homeworkSubmit";
	    } else if ("Y".equals(hm.getStatus())) {
	        // hm_submit에 데이터가 있고 상태가 "Y"이면 확인 페이지로 이동
	        return "student/homeworkConfirm";
	    } else {
	        // 기타 상태일 경우 기본적으로 제출 페이지로 이동
	        return "student/homeworkSubmit";
	    }
	}
	
	// 학생 숙제 제출
	@RequestMapping("enroll.homework_student")
	public String enrollHomeworkStu(@ModelAttribute Homework h,
									@ModelAttribute HomeworkSubmit hm,
									@RequestParam("upfile") MultipartFile upfile,
									HttpSession session) {
		System.out.println(hm);
		System.out.println(hm.getHmStuContent());
		// 로그인 정보 확인
		Student s = (Student) session.getAttribute("loginUser");
		
		if(!upfile.getOriginalFilename().equals("")) {
			String changeName = Template.saveFile(upfile, session, "/resources/file/homework_files/");
			
			hm.setChangeName("/resources/file/homework_files/" + changeName);
			hm.setOriginName(upfile.getOriginalFilename());
		}
		hm.setStuId(s.getStuId());
		hm.setStatus("Y");
		System.out.println(hm);
		int result = studentService.insertStudentHomework(hm);
		
		if(result > 0) {
			return "redirect:/homework";
		}else {
			session.setAttribute("alertMsg", "숙제 업로드 실패");
			return "redirect:/homework";
		}
	}
	
	// 학생 숙제 제출 완료 후 제출한 숙제 조회
	@RequestMapping("homework.check")
	public String homeworkCheck(int boNo,
            					@RequestParam(value = "cpage", defaultValue = "1") int currentPage,
            					HttpSession session,
            					Model model) {
		Student s = (Student) session.getAttribute("loginUser");
		
		HomeworkSubmit npage = studentService.selectNowAnswer(boNo);
		
		// null 체크 및 예외 처리
	    if (npage == null) {
	        throw new IllegalStateException("제출된 숙제 데이터를 찾을 수 없습니다.");
	    }
		
		model.addAttribute("npage", npage);
		model.addAttribute("cpage", currentPage);
		
		return "student/homeworkConfirm";
	}
	
	// 학생 숙제 답변 수정 페이지로 이동
	@RequestMapping("hmAnswer_modify")
	public String homeworkModify(int boNo,@RequestParam(value="cpage", defaultValue="1") int currentPage, HttpSession session, Model model) {
		HomeworkSubmit npage = studentService.selectNowAnswer(boNo);
		
		session.setAttribute("npage", npage);
		session.setAttribute("cpage", currentPage);
		
		return "student/modifyHomework";
	}
	
	// 글 수정하기
//	@RequestMapping("update.afterSt")
//	public String updateStuAnswer(HomeworkSubmit hm, MultipartFile reupfile, HttpSession session) {
//		Student loginUser = (Student) session.getAttribute("loginUser");
//		if (!reupfile.getOriginalFilename().equals("")) {
//			// 기존 첨부파일이 있다 -> 기존파일을 삭제
//			if (asb.getOriginName() != null) {
//				new File(session.getServletContext().getRealPath(asb.getChangeName())).delete();
//			}
//
//			// 새로운 첨부파일을 서버에 업로드하기
//			String changeName = Template.saveFile(reupfile, session, "/resources/file/");
//
//			asb.setOriginName(reupfile.getOriginalFilename());
//			asb.setChangeName("/resources/file/" + changeName);
//		} else {
//			AfterSchoolBoard opb = (AfterSchoolBoard) session.getAttribute("npage");
//			asb.setOriginName(opb.getOriginName());
//			asb.setChangeName(opb.getChangeName());
//		}
//
//		int result = afterschoolService.updateAfterschoolBoard(asb);
//		if (result > 0) {
//			session.setAttribute("alertMsg", "수정에 성공하였습니다.");
//			return "redirect:/afterSchool";
//		} else {
//			session.setAttribute("alertMsg", "수정실패");
//			return "student/myPage";
//		}

	
	
// =================================== 방과후 ===================================
	
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
