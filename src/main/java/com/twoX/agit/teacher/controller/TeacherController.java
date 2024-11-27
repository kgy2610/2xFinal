package com.twoX.agit.teacher.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.twoX.agit.board.model.vo.HmSubmit;
import com.twoX.agit.member.model.vo.AfterSchool;
import com.twoX.agit.member.model.vo.Attendance;
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
	
	 //정보수정
	   @RequestMapping("updateInfo.me")
	   public String updateInfo(String grade,String teacher_class, HttpSession session, Model m) {
		   Teacher t = (Teacher)session.getAttribute("loginUser");
		   String newClassCode = t.getScCode()+"24"+grade+teacher_class;
		   t.setClassCode(newClassCode);
		   int result = teacherService.updateInfo(t);
		   
		   if(result > 0) {
			   session.setAttribute("loginUser", memberService.loginTeacher(t));
				session.setAttribute("alertMsg", "회원정보 수정 성공");
				return "teacher/myPage";
		   }else {
			   session.setAttribute("alertMsg", "회원정보 수정 실패");
				return "teacher/myPage";
		   }
	   }

	   //반삭제
	   @RequestMapping("classdelete.me")
	   public String classDelete(Teacher t, String deleteCode, HttpSession session, Model m) {
		   Teacher teacher = (Teacher)session.getAttribute("loginUser");
		   
		   if(teacher == null) {
			   session.setAttribute("alertMsg", "로그인정보가 없습니다.");
			   return "teacher/myPage";
		   }
		   
		   t.setTcId(teacher.getTcId()); 
		   
		   t.setClassCode(deleteCode);
		   
		   int result = teacherService.classDelete(t);
		   
		   if(result > 0) {
			   session.setAttribute("loginUser", memberService.loginTeacher(t));
				session.setAttribute("alertMsg", "반 삭제 성공");
				return "member/login";
		   }else {
			   session.setAttribute("alertMsg", "반 삭제 실패");
			   return "teacher/myPage";
		   }
		   
		   
	   }
	   
	   //방과후 반 개설 전
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
	    	   return "redirect:/list.bo"; // 방과후 반 개설 후 페이지로 리다이렉트
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
	public String Teacherhomework(@RequestParam(value = "page", defaultValue = "1") int currentPage, Model model, HttpSession session) {
		Teacher teacher = (Teacher) session.getAttribute("loginUser");
		
		String tcId = teacher.getTcId();
		
		int listCount = teacherService.getListCount(); // 총 게시글 수를 구하는 메서드 호출
		System.out.println(listCount);
		

		ArrayList<Homework> homeworkList = teacherService.getAllHomework(tcId);
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
		
		ArrayList<Homework> homeworkList = teacherService.getAllHomework(tcId);
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
		
		Teacher teacher = (Teacher) session.getAttribute("loginUser");	
		String tcId = teacher.getTcId();

		int result = teacherService.deleteHomework(hmTitle);
		
		ArrayList<Homework> homeworkList = teacherService.getAllHomework(tcId);
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
		Teacher teacher = (Teacher) session.getAttribute("loginUser");
		
		String tcId = teacher.getTcId();
		
		System.out.println(hmTitle);
		System.out.println(subject);
		System.out.println(deadLine);
		System.out.println(hmContent);

		int result = teacherService.updateHomework(hmTitle, subject, deadLine, hmContent);
		
		ArrayList<Homework> homeworkList = teacherService.getAllHomework(tcId);
		model.addAttribute("homeworkList", homeworkList);

		return "teacher/homeworkList";
	}
	
	//우리 학생 숙제 페이지로 이동
	@RequestMapping("gosubject")
	public String goSubject( Model model, HttpSession session) {
		Teacher loginUser = (Teacher) session.getAttribute("loginUser");
		String classCode = loginUser.getClassCode();
		
		
		ArrayList<Homework> subjectHomeworkList = teacherService.selectSubject(classCode);
		System.out.println(subjectHomeworkList);
		model.addAttribute("subjectHomeworkList", subjectHomeworkList);
		
		return "teacher/teacherhomeworkList";
	}
	
	// 숙제의 대한 상세페이지
	@RequestMapping("homeworkDetail")
	public String homeworkDetail(String title, String studentId, Model model) {
		System.out.println(title);
		System.out.println(studentId);
		
		ArrayList<HmSubmit> submitHomework = teacherService.gosubmitHomework(title, studentId);
		System.out.println(submitHomework);

	    if (!submitHomework.isEmpty()) {
	        // 첫 번째 데이터를 사용하여 필요한 정보 추출
	        HmSubmit homework = submitHomework.get(0);

	        model.addAttribute("hmTitle", homework.getHmTitle());       // 숙제 제목
	        model.addAttribute("hmContent", homework.getHmStuContent()); // 학생이 제출한 답
	        model.addAttribute("score", homework.getScore());         // 점수
	        model.addAttribute("tcComment", homework.getTcComment()); // 교사 코멘트
	        model.addAttribute("stuId", studentId);
	    } else {
	        // 데이터가 없는 경우
	        model.addAttribute("hmTitle", title);
	        model.addAttribute("hmContent", "제출된 답변이 없습니다.");
	        model.addAttribute("score", 0);
	        model.addAttribute("tcComment", "코멘트 없음");
	    }
		
	
		return "teacher/teacherhomeworkDetail";
	}
	
	//숙제 점수 및 말씀 부여
	@RequestMapping("updateSubmitHomework")
	public String updateSubmitHomework(String teacherComment, int score, String stuId, Model model, HttpSession session) {
		Teacher loginUser = (Teacher) session.getAttribute("loginUser");
		String classCode = loginUser.getClassCode();
		System.out.println(teacherComment);
		System.out.println(score);
		System.out.println(stuId);
		
		int result = teacherService.updateSubmitHomework(teacherComment,score,stuId);
		
		ArrayList<Homework> subjectHomeworkList = teacherService.selectSubject(classCode);
		model.addAttribute("subjectHomeworkList", subjectHomeworkList);
		
		
		return "teacher/teacherhomeworkList";
	}
	
	
	// 출석 리스트로 이동
	@RequestMapping("teacherAttendance")
	public String teacherAttendance(HttpSession session, Model model) {
		Teacher loginUser = (Teacher) session.getAttribute("loginUser");
		String classCode = loginUser.getClassCode();
		
		ArrayList<Attendance> attendanceList = teacherService.selectAttendance(classCode);
		System.out.println(attendanceList);
		
		model.addAttribute("attendanceList", attendanceList);
		
		return "teacher/teacherAttendance";
	}
	
	// 지각 출석 결석 제출
	@RequestMapping("submitAttendance")
	public String submitAttendance(String aDate, @RequestParam Map<String, String> attendanceData, Model model) {
		System.out.println(aDate);
		
        // 출결 리스트 생성
        List<Attendance> attendanceList = new ArrayList<>();

        attendanceData.forEach((key, value) -> {
            if (key.startsWith("attendance_")) { // 출결 데이터 필터링
                String STU_ID = key.replace("attendance_", "");
                String LA = value;

                // Attendance 객체 생성 및 리스트에 추가
                Attendance attendance = new Attendance();
                attendance.setADate(aDate);
                attendance.setSTU_ID(STU_ID);
                attendance.setLA(LA);
                attendanceList.add(attendance);
            }
        });
        
        System.out.println(attendanceList);
	
	    int result = teacherService.insertAttendance(attendanceList);
	  
		return "teacher/myPage";
	}
	
	
	//학생관리 페이지 이동
	@RequestMapping("studentManage.me")
	public String studentManage(HttpSession session, Model model) {
	    Teacher loginUser = (Teacher) session.getAttribute("loginUser");
	    String classCode = loginUser.getClassCode();

	    // 학생별 출석률 가져오기
	    List<Map<String, Object>> stuManageList = teacherService.smCodeStudent(classCode);
	    System.out.println(stuManageList);
	    
	    

	    model.addAttribute("stuManageList", stuManageList);
	    return "teacher/studentManage";
	}
	
	 // 한 학생의 과목별 점수 조회
	@RequestMapping(value = "studentManage.me", produces="application/json; charset-UTF-8")
	@ResponseBody
	public String getStudentScores(String stuId) {
	    System.out.println("Received stuId: " + stuId); 
	    List<Map<String, Object>> scoresList = teacherService.getStudentScoresByStuId(stuId);
	    System.out.println("scoresList: " + scoresList);
	    return new Gson().toJson(scoresList);  // JSON 형식으로 반환
	}
	
	// 한학생 승인 취소
	@RequestMapping("studentManageCansel.me")
	public String studentManageCansel(HttpSession session, Model model) {
	    Teacher loginUser = (Teacher) session.getAttribute("loginUser");
	    String classCode = loginUser.getClassCode();

	    
	    
	    return "teacher/studentManage";
	}
	
	
	
}