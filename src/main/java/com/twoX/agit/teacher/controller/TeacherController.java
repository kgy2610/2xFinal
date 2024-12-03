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
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.twoX.agit.board.model.vo.HmSubmit;
import com.twoX.agit.common.template.Template;
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
		
		ArrayList<Homework> homeworkList = teacherService.getAllHomework(tcId);
		
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
	public String enrollHomework(String title, String subject, String content, String dueDate, @RequestParam("upfile") MultipartFile upfile,  HttpSession session, Model model) {
		
		if(!LoginCheckService.checkLogin(session)) {
			session.setAttribute("loginMessage", "로그인이 필요합니다.");
			return "member/login_teacher";
		}
		
		Teacher loginUser = (Teacher) session.getAttribute("loginUser");
		Teacher loginTeacher = (Teacher) session.getAttribute("loginUser");

		String tcId = loginTeacher.getTcId();
		String classCode = loginTeacher.getClassCode();

		int result = teacherService.enrollHomework(tcId, classCode, title, subject, content, dueDate);
		int boNo = teacherService.getRecentHomeworkBoNo(tcId); // 가장 최근 등록된 BO_NO 가져오기
		
		// 파일이 있는 경우 처리
	    if (!upfile.getOriginalFilename().equals("") && result > 0) {
	        String changeName = Template.saveFile(upfile, session, "/resources/file/");
	        String originName = upfile.getOriginalFilename();

	        // 파일 정보 DB 저장
	        int fileResult = teacherService.uploadHomeworkFile(boNo, originName, "/resources/file/" + changeName);
	    }
		
		ArrayList<Homework> homeworkList = teacherService.getAllHomework(tcId);
		model.addAttribute("homeworkList", homeworkList);

		return "teacher/homeworkList";
	}

	// 숙제 상세 페이지
	@RequestMapping("detailHomework")
	public String homeworkDatail(int boNo, String subject, String hmTitle, String hmContent, String deadLine, String changeName, Model model) {
		model.addAttribute("boNo", boNo);
		model.addAttribute("subject", subject);
		model.addAttribute("hmTitle", hmTitle);
		model.addAttribute("hmContent", hmContent);
		model.addAttribute("deadLine", deadLine);
		model.addAttribute("changeName", changeName);

		return "teacher/homeworkDetail";
	}

	// 숙제 삭제
	@RequestMapping("deleteHomework")
	public String deleteHomework(int boNo, String hmTitle, String changeName, HttpSession session, Model model) {		
		if(!LoginCheckService.checkLogin(session)) {
			session.setAttribute("loginMessage", "로그인이 필요합니다.");
			return "member/login_teacher";
		}
		
		Teacher loginUser = (Teacher) session.getAttribute("loginUser");
		Teacher teacher = (Teacher) session.getAttribute("loginUser");	
		
		String tcId = teacher.getTcId();

		int res1 = teacherService.deleteFile(boNo, changeName);
		int result = teacherService.deleteHomework(hmTitle);
		
		ArrayList<Homework> homeworkList = teacherService.getAllHomework(tcId);
		model.addAttribute("homeworkList", homeworkList);

		return "teacher/homeworkList";
	}

	// 숙제 수정하기
	@RequestMapping("updateHomework")
	public String updateHomework(int boNo, String hmTitle, String subject, String deadLine, String hmContent, String changeName, MultipartFile fileupload,  HttpSession session, Model model) {
		if (!LoginCheckService.checkLogin(session)) {
	        session.setAttribute("loginMessage", "로그인이 필요합니다.");
	        return "member/login_teacher"; // 로그인되지 않았으면 로그인 페이지로 리다이렉트
	    }
		
		Teacher loginUser = (Teacher) session.getAttribute("loginUser");
		Teacher teacher = (Teacher) session.getAttribute("loginUser");
		
		String tcId = teacher.getTcId();
		
		int result = teacherService.updateHomework(hmTitle, subject, deadLine, hmContent);
		
		// 파일이 새로 업로드된 경우 처리
	    if (!fileupload.getOriginalFilename().equals("") && result > 0) {
	    	// 파일 저장
	        String newFileName = Template.saveFile(fileupload, session, "/resources/file/");
	        String originName = fileupload.getOriginalFilename();

	        // 파일 정보 DB 저장
	        int fileResult = teacherService.updateHomeworkFile(boNo, originName, newFileName, "/resources/file/" + changeName);
	    }
		
		
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

		model.addAttribute("subjectHomeworkList", subjectHomeworkList);
		
		return "teacher/teacherhomeworkList";
	}
	
	// 숙제의 대한 상세페이지
	@RequestMapping("homeworkDetail")
	public String homeworkDetail(String title, String studentId, Model model) {

		ArrayList<HmSubmit> submitHomework = teacherService.gosubmitHomework(title, studentId);
		System.out.println(submitHomework);

	    if (!submitHomework.isEmpty()) {
	        // 첫 번째 데이터를 사용하여 필요한 정보 추출
	        HmSubmit homework = submitHomework.get(0);

	        model.addAttribute("hmTitle", homework.getHmTitle());       // 숙제 제목
	        model.addAttribute("hmStuContent", homework.getHmStuContent()); // 학생이 제출한 답
	        model.addAttribute("score", homework.getScore());         // 점수
	        model.addAttribute("tcComment", homework.getTcComment()); // 교사 코멘트
	        model.addAttribute("stuId", studentId);
	        model.addAttribute("hmContent", homework.getHmContent()); // 숙제 내용
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
		
		ArrayList<Teacher> attendanceList = memberService.selectAttendance(classCode);
		
		model.addAttribute("attendanceList", attendanceList);
		
		return "teacher/teacherAttendance";
	}
	
	
	// 지각 출석 결석 제출
	@RequestMapping("submitAttendance")
	public String submitAttendance(String aDate, @RequestParam Map<String, String> attendanceData, Model model) {
		
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
	
	    int result = teacherService.insertAttendance(attendanceList);
	    
		return "teacher/myPage";
	}
	
	//학생 출결 상태 업데이트
	@RequestMapping("modifyAttendance")
	    public String modifyAttendance(String aDate, @RequestParam("studentId") List<String> studentIds, 
	    								@RequestParam Map<String, String> params) {
		
	    // 수정할 출석 데이터를 담을 리스트
	    ArrayList<Attendance> updateAttendance = new ArrayList<>();

	    // 넘어온 날짜 확인
	    System.out.println("수정 요청 날짜: " + aDate);

	    // 넘어온 학생 ID 확인
	    System.out.println("학생 ID 목록: ");
	    for (String studentId : studentIds) {
	        System.out.println("학생 ID: " + studentId);
	    }

	    // 각 학생의 출석 상태 확인
	    for (String studentId : studentIds) {
	        // 출석 상태 가져오기
	        String attendanceStatus = params.get("attendance_" + studentId);

	        System.out.println("학생 ID: " + studentId + ", 출석 상태: " + attendanceStatus);

	        // Attendance 객체 생성하여 리스트에 추가
	        Attendance attendance = new Attendance();
	        attendance.setADate(aDate);
	        attendance.setSTU_ID(studentId);
	        attendance.setLA(attendanceStatus);
	        
	        // 리스트에 추가
	        updateAttendance.add(attendance);
	    }
	    
	    System.out.println("이게 나오나용:" + updateAttendance);
	    
	    int result = teacherService.updateAttendance(updateAttendance);
	       
	        
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