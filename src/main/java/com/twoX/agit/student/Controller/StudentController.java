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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.twoX.agit.after.service.AfterSchoolBoardService;
import com.twoX.agit.after.vo.AfterSchoolBoard;
import com.twoX.agit.after.vo.AfterSchoolStudent;
import com.twoX.agit.board.model.vo.ParentsBoard;
import com.twoX.agit.common.template.Template;
import com.twoX.agit.common.vo.PageInfo;
import com.twoX.agit.member.model.vo.AfterSchool;
import com.twoX.agit.member.model.vo.Student;
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
	
	// 학생 숙제 리스트 불러오기
	@RequestMapping("homework")
	public String homeworkList(HttpSession session, Model model) {
		Student loginUser = (Student) session.getAttribute("loginUser");
		
		if(loginUser != null) {
			String classCode = loginUser.getClassCode();
			String stuId = loginUser.getStuId();
			model.addAttribute("classCode", classCode);
			
			ArrayList<HomeworkSubmit> homeworkList = studentService.showHomeworkList(classCode, stuId);
			model.addAttribute("homeworkList", homeworkList);
		}else {
			model.addAttribute("message", "로그인 정보가 없습니다.");
			
			return "/member/login_student";
		}
		return "student/homework";
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
	
	//글 수정하기
	@RequestMapping("update.afterSt")
	public String update_afterschoolStu(String title, String boContent,AfterSchoolBoard asb,MultipartFile reupfile,HttpSession session) {
		Student loginUser = (Student)session.getAttribute("loginUser");
		
		
		String code = studentService.afterschoolCode(loginUser.getStuId());
		asb.setStuId(loginUser.getStuId());
		asb.setCode(code);
		asb.setTitle(title);
		asb.setBoContent(boContent);
		
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
