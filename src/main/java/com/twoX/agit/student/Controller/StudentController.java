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

import com.twoX.agit.board.model.vo.HmSubmit;
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
}
