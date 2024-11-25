package com.twoX.agit.student.Controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.twoX.agit.common.template.Template;
import com.twoX.agit.common.vo.PageInfo;
import com.twoX.agit.member.model.vo.Homework;
import com.twoX.agit.member.model.vo.Student;
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
	public String studentHomeworkDetail(@RequestParam(value = "boNo") int boNo, @RequestParam(value = "cpage", defaultValue = "1") int currentPage, HttpSession session, Model model) {
		Homework npage = studentService.selectNowHomework(boNo);
		
		model.addAttribute("npage", npage);
		model.addAttribute("cpage", currentPage);
		System.out.println(npage);
		return "student/homeworkSubmit";
	}
}
