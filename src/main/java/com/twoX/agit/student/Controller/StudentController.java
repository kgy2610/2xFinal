package com.twoX.agit.student.Controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.twoX.agit.common.template.Template;
import com.twoX.agit.common.vo.PageInfo;
import com.twoX.agit.member.model.vo.Homework;
import com.twoX.agit.member.model.vo.Student;
import com.twoX.agit.member.model.vo.Teacher;
import com.twoX.agit.student.model.vo.HomeworkSubmit;
import com.twoX.agit.student.service.StudentService;

@CrossOrigin
@Controller
public class StudentController {
	
	private final StudentService studentService;
	
	@Autowired
	public StudentController(StudentService studentService) {
		this.studentService = studentService;
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
	    System.out.println(hm);
	    
	    // 모델에 데이터 추가
		model.addAttribute("npage", npage);
		model.addAttribute("cpage", currentPage);
		
		String st = hm.getStatus();
		
		// 숙제 상태에 따라 페이지 이동
	    if (hm != null && "Y".equals(hm.getStatus())) {
	        return "student/homeworkConfirm";
	    } else {
	        return "student/homeworkSubmit";
	    }
	}
	
	// 학생 숙제 제출
	@RequestMapping("enroll.homework_student")
	public String enrollHomeworkStu(@RequestParam("boNo") int boNo,
									@RequestParam("hmStuContent") String hmStuContent,
									@ModelAttribute Homework h,
									@ModelAttribute HomeworkSubmit hm,
									@RequestParam("upfile") MultipartFile upfile,
									HttpSession session) {
		// 로그인 정보 확인
		Student s = (Student) session.getAttribute("loginUser");
		Teacher t = (Teacher) session.getAttribute("loginTeacher");
		if(!upfile.getOriginalFilename().equals("")) {
			String changeName = Template.saveFile(upfile, session, "/resources/file/homework_files/");
			
			hm.setChangeName("/resources/file/homework_files/" + changeName);
			hm.setOriginName(upfile.getOriginalFilename());
		}
		hm.setStuId(s.getStuId());
		hm.setBoNo(boNo);
		hm.setHmStuContent(hmStuContent);
		hm.setStatus("Y");
		
		int result = studentService.insertStudentHomework(hm);
		
		if(result > 0) {
			return "student/homeworkConfirm";
		}else {
			session.setAttribute("alertMsg", "숙제 업로드 실패");
			return "redirect:/homework";
		}
	}
	
	// 학생 숙제 제출 완료 후 제출한 숙제 조회
	@RequestMapping("homework.check")
	public String homeworkCheck(@RequestParam("boNo") int boNo,
								@RequestParam("hmStuContent") String hmStuContent,
								@ModelAttribute HomeworkSubmit hm,
								@RequestParam(value = "cpage", defaultValue = "1") int currentPage,
								@RequestParam("upfile") MultipartFile upfile,
								HttpSession session,
								Model model) {
		Student s = (Student) session.getAttribute("loginUser");
		
		HomeworkSubmit npage = studentService.selectNowAnswer(boNo);
		
		model.addAttribute("npage", npage);
		model.addAttribute("cpage", currentPage);
		
		return "student/homeworkConfirm";
	}
}
