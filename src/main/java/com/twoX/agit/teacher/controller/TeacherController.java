package com.twoX.agit.teacher.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.twoX.agit.member.model.vo.AfterSchool;
import com.twoX.agit.member.model.vo.Homework;
import com.twoX.agit.member.model.vo.Teacher;
import com.twoX.agit.member.service.LoginCheckService;
import com.twoX.agit.member.service.MemberService;
import com.twoX.agit.teacher.service.TeacherService;

@CrossOrigin
@Controller
public class TeacherController {
	private final TeacherService teacherService;
	private final MemberService memberService;

	@Autowired
	public TeacherController(TeacherService teacherService, MemberService memberService) {
		this.teacherService = teacherService;
		this.memberService = memberService;
	}
	
	// 정보수정
	@RequestMapping("updateInfo.me")
	public String updateInfo(String grade, String teacher_class, HttpSession session, Model m) {
		Teacher t = (Teacher) session.getAttribute("loginUser");
		String newClassCode = t.getScCode() + "24" + grade + teacher_class;
		t.setClassCode(newClassCode);
		int result = teacherService.updateInfo(t);

		if (result > 0) {
			session.setAttribute("loginUser", memberService.loginTeacher(t));
			session.setAttribute("alertMsg", "회원정보 수정 성공");
			return "teacher/myPage";
		} else {
			session.setAttribute("alertMsg", "회원정보 수정 실패");
			return "teacher/myPage";
		}
	}

	// 반삭제
	@RequestMapping("classdelete.me")
	public String classDelete(Teacher t, String deleteCode, HttpSession session, Model m) {
		Teacher teacher = (Teacher) session.getAttribute("loginUser");

		if (teacher == null) {
			session.setAttribute("alertMsg", "로그인정보가 없습니다.");
			return "teacher/myPage";
		}

		t.setTcId(teacher.getTcId());

		t.setClassCode(deleteCode);

		int result = teacherService.classDelete(t);

		if (result > 0) {
			session.setAttribute("loginUser", memberService.loginTeacher(t));
			session.setAttribute("alertMsg", "반 삭제 성공");
			return "member/login";
		} else {
			session.setAttribute("alertMsg", "반 삭제 실패");
			return "teacher/myPage";
		}

	}

	// 방과후 반 개설 전
	@RequestMapping("makeAfterClass.me")
	public String showMakeAfterClassPage(AfterSchool as, HttpSession session, Model m) {
		// 세션에서 로그인된 교사 정보 가져오기
		Teacher te = (Teacher) session.getAttribute("loginUser");

		// 로그인된 교사가 없으면 로그인 페이지로 리다이렉트
		if (te == null) {
			m.addAttribute("alertMsg", "로그인 후 시도해주세요.");
			return "member/login_teacher";
		}

		// 교사가 이미 방과후 반을 개설했는지 확인
		AfterSchool existingAfterSchools = teacherService.getAfterClassByTeacherId(te.getTcId());

		System.out.println(existingAfterSchools);
		// 이미 방과후 반을 개설했다면 중복 처리
		if (existingAfterSchools != null) {
			session.setAttribute("as", existingAfterSchools);
			return "teacher/afterClass"; // 방과후 반 개설 후 페이지로 리다이렉트
		}

		// code가 비어있으면 처리하지 않음 (code가 not null 제약조건을 가져서)
		if (as.getCode() == null || as.getCode().isEmpty()) {
			m.addAttribute("alertMsg", "참여 코드를 입력해주세요.");
			return "teacher/makeAfterClass"; // 참여 코드가 없으면 반 개설 페이지로 돌아가기
		}

		// 교사 정보 추가
		as.setTcId(te.getTcId());

		// 방과후 반 개설
		int result = teacherService.makeAfterClass(as);

		if (result > 0) {
			session.setAttribute("afCode", as);
			session.setAttribute("alertMsg", "방과후 반 개설 성공");
			System.out.println("as :" + as);

			// 반 개설 성공 후 'teacher/afterClass' 페이지로 이동
			return "teacher/afterClass";
		} else {
			m.addAttribute("alertMsg", "방과후 반 개설 실패");
			return "teacher/makeAfterClass"; // 반 개설 실패 시 다시 반 개설 페이지로 돌아가기
		}
	}
	
	// 숙제 리스트로 이동
	@RequestMapping("homeworkList")
	public String Teacherhomework(@RequestParam(value = "page", defaultValue = "1") int currentPage, Model model) {
		int listCount = teacherService.getListCount(); // 총 게시글 수를 구하는 메서드 호출
		System.out.println(listCount);

		ArrayList<Homework> homeworkList = teacherService.getAllHomework();
		System.out.println(homeworkList);

		model.addAttribute("homeworkList", homeworkList);
		model.addAttribute("listCount", listCount);

		return "teacher/homeworkList";
	}

	// 숙제 추가페이지 이동
	@RequestMapping("addHomeworkPage")
	public String addHomeworkPage() {
		return "teacher/addHomework";
	}

	// 숙제 등록페이지
	@RequestMapping("enrollHomework")
	public String enrollHomework(String title, String subject, String content, String dueDate, HttpSession session, Model model) {
		if(!LoginCheckService.checkLogin(session)) {
			session.setAttribute("loginMessage", "로그인이 필요합니다.");
			return "member/login_teacher";
		}
		Teacher loginUser = (Teacher) session.getAttribute("loginUser");
		
		System.out.println(title);
		System.out.println(subject);
		System.out.println(content);
		System.out.println(dueDate);

		Teacher loginTeacher = (Teacher) session.getAttribute("loginUser");

		String tcId = loginTeacher.getTcId();
		String classCode = loginTeacher.getClassCode();

		int result = teacherService.enrollHomework(tcId, classCode, title, subject, content, dueDate);
		
		ArrayList<Homework> homeworkList = teacherService.getAllHomework();
		model.addAttribute("homeworkList", homeworkList);

		return "teacher/homeworkList";
	}

	// 숙제 상세 페이지
	@RequestMapping("detailHomework")
	public String homeworkDatail(String subject, String hmTitle, String hmContent, String deadLine, Model model) {
		System.out.println(subject);
		System.out.println(hmTitle);
		System.out.println(hmContent);
		System.out.println(deadLine);

		model.addAttribute("subject", subject);
		model.addAttribute("hmTitle", hmTitle);
		model.addAttribute("hmContent", hmContent);
		model.addAttribute("deadLine", deadLine);

		return "teacher/homeworkDetail";
	}

	// 숙제 삭제
	@RequestMapping("deleteHomework")
	public String deleteHomework(String hmTitle, HttpSession session, Model model) {
		if(!LoginCheckService.checkLogin(session)) {
			session.setAttribute("loginMessage", "로그인이 필요합니다.");
			return "member/login_teacher";
		}
		
		Teacher loginUser = (Teacher) session.getAttribute("loginUser");
		System.out.println(hmTitle);

		int result = teacherService.deleteHomework(hmTitle);
		
		ArrayList<Homework> homeworkList = teacherService.getAllHomework();
		model.addAttribute("homeworkList", homeworkList);

		return "teacher/homeworkList";
	}

	// 숙제 수정하기
	@RequestMapping("updateHomework")
	public String updateHomework(String hmTitle, String subject, String deadLine, String hmContent, HttpSession session, Model model) {
		if (!LoginCheckService.checkLogin(session)) {
	        session.setAttribute("loginMessage", "로그인이 필요합니다.");
	        return "member/login_teacher"; // 로그인되지 않았으면 로그인 페이지로 리다이렉트
	    }
		
		Teacher loginUser = (Teacher) session.getAttribute("loginUser");
		
		System.out.println(hmTitle);
		System.out.println(subject);
		System.out.println(deadLine);
		System.out.println(hmContent);

		int result = teacherService.updateHomework(hmTitle, subject, deadLine, hmContent);
		
		ArrayList<Homework> homeworkList = teacherService.getAllHomework();
		
		model.addAttribute("homeworkList", homeworkList);

		return "teacher/homeworkList";
	}
}