package com.twoX.agit.student.Controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import com.twoX.agit.member.model.vo.Student;
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
}
