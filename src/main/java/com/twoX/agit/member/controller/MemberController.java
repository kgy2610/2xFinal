package com.twoX.agit.member.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.twoX.agit.board.model.vo.Counsel;
import com.twoX.agit.board.service.BoardService;
import com.twoX.agit.chat.Chat;
import com.twoX.agit.member.model.vo.Parents;
import com.twoX.agit.member.model.vo.School;
import com.twoX.agit.member.model.vo.Student;
import com.twoX.agit.member.model.vo.Teacher;
import com.twoX.agit.member.service.MemberService;
import com.twoX.agit.teacher.model.vo.TeacherMemo;
import com.twoX.agit.teacher.model.vo.TeacherNotice;

@CrossOrigin
@Controller
public class MemberController {
	public static final String SERVICE_KEY = "f72f3af259f9408d88585329410824e5";
	private final MemberService memberService;

	@Autowired
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}	

	@RequestMapping("login")
	public String login() {
		return "member/login";
	}
	
	// ------------------------------ 공용 ------------------------------
	// ------------------------------ 아이디 중복 확인 ------------------------------
	// 아이디 중복 확인
	@ResponseBody
	@RequestMapping("idCheck.me")
	public String idCheck(String checkId) {
		int result = memberService.idCheck(checkId);

		if (result > 0) {
			return "NNNNN";
		} else {
			return "NNNNY";
		}
	}

	// ------------------------------ 아이디 / 비밀번호 찾기 ------------------------------
	// 아이디비번찾기
	@RequestMapping("find.id-pwd")
	public String find_id_pwd() {
		return "member/find_id_pwd";
	}

	// 아이디찾기
	@ResponseBody
	@RequestMapping("find_ID")
	public Parents findId(Parents p) {
		Parents findId = memberService.findId(p);
		return findId;
	}

	// 비밀번호 찾기
	@ResponseBody
	@RequestMapping("find_PWD")
	public Parents findPwd(Parents p) {
		Parents findPwd = memberService.findPwd(p);
		return findPwd;
	}

	// 비밀번호 수정
	@ResponseBody
	@RequestMapping("modify_PWD")
	public int modifyPwd(Parents p) {
		int updatePwd = memberService.updatePwd(p);
		return updatePwd;
	}
	
	// ------------------------------ 로그아웃 ------------------------------
	// 로그아웃
	@RequestMapping("logout.me")
	public String logoutSt(HttpSession session) {
		session.invalidate();

		return "redirect:/";
	}
	
	// 관리자 로그아웃
	@RequestMapping("manager/logout.me")
	public String logoutMg(HttpSession session) {
		session.removeAttribute("loginTeacher");
		return "redirect:/";
	}
	
	
	// ------------------------------ 학생 ------------------------------
	// 학생 반 참가
	   @RequestMapping("studentMyPage")
	   public String studentMyPage(HttpSession session, Model model) {
	      Student loginUser = (Student) session.getAttribute("loginUser");

		if (loginUser != null) {
			String classCode = loginUser.getClassCode();
			
			// 학교명 조회
			String schoolName = memberService.getSchoolNameByClassCode(classCode);
			Teacher teacher = memberService.selectTeacher(loginUser);
			session.setAttribute("schoolName", schoolName);
			model.addAttribute("schoolName", schoolName);
			model.addAttribute("loginUser", loginUser);
			session.setAttribute("teacherName", teacher);
		}
		
		model.addAttribute("bbsId", "mypage");
		
		return "student/myPage";
	}
	   
	   @ResponseBody
		@RequestMapping(value="selectChatList", produces="application/json; charset-UTF-8")
		public String ajaxSelectChatList(String stuId,String classCode,HttpSession session) {
		   ArrayList<Chat> clist =new ArrayList();
		   if(stuId==null) {
			   Student loginUser = (Student) session.getAttribute("loginUser");
			   clist = memberService.selectChatList(loginUser);
		   }else {
			   Student s = new Student();
			   s.setStuId(stuId);
			   s.setClassCode(classCode);
			   clist = memberService.selectChatList(s);
		   }
		   return new Gson().toJson(clist);
		}
	      
	   // 학생 로그인
	   @RequestMapping("login.student")
	   public String login_student(Student s, HttpSession session, Model model) {

	      Student loginStudent = memberService.loginStudent(s);

	      if (loginStudent == null) {
	         System.out.println("로그인 실패");
	         model.addAttribute("errorMsg", "로그인 실패");
	         return "member/login_student";
	      } else {
	         session.setAttribute("loginUser", loginStudent);
	         System.out.println("로그인 성공");

	         if(loginStudent.getClassCode() == null) {
	        	 return "student/myPageStart";
	         }else {
	        	// classNum이 null이면 myPageStart로, 그렇지 않으면 myPage로 리다이렉트
		         if (loginStudent.getStatus().equals("N")) {
		            return "student/waitClassStatus";
		         } else {
		            return "redirect:/studentMyPage";
		         }
	         }
	         
	      }
	   }
	   
	   // 학생 회원가입 창으로 이동
	   @RequestMapping("enrollForm.stu")
	      public String enrollForm_stu() {
	         return "member/enrollForm_student";
	   }

	   // 학생 회원가입
	   @RequestMapping("enrollForm.student")
	   public String enrollForm_student(Student s, HttpSession session, Model model) {
	      s.setPicNo("String");

	      int result = memberService.insertStudent(s);

	      if (result > 0) {
	         session.setAttribute("alertMsg", "성공적으로 회원가입이 완료되었습니다.");
	         return "redirect:/login.student";
	      } else {
	         model.addAttribute("errorMsg", "회원가입 실패");
	         return "common/errorPage";
	      }
	   }
	   
	   // 반 참가
	   @RequestMapping("student.classCode")
	   public String studentUpdateClassCode(Student s, HttpSession session, Model model) {
		   model.addAttribute("bbsId", "mypage");
		   
		   Student loginUser = (Student) session.getAttribute("loginUser");

	      if (s.getClassCode() == null || s.getClassCode().trim().isEmpty()) {
	         model.addAttribute("alertMsg", "반 코드를 입력해야 합니다.");
	         return "redirect:/"; // 적절한 메시지로 안내
	      }

	      int result = memberService.studentUpdateClassCode(s);
	      
	      if (result > 0) {
	         Student student = memberService.selectInfo(s.getStuId());
	         session.setAttribute("loginUser", student);
	         session.setAttribute("alertMsg", "반 참가 성공");
	         
	         if (loginUser.getStatus().equals("N")) {
		            return "student/waitClassStatus";
		         } else {
		            return "redirect:/studentMyPage";
		         }
	         
	      } else {
	         model.addAttribute("alertMsg", "반 참가 실패");
	         return "redirect:/";
	      }
	   }

	@RequestMapping("student.update")
	public String studentUpdate(String updateNum, HttpSession session, Model model) {
		model.addAttribute("bbsId", "mypage");

		Student s = (Student) session.getAttribute("loginUser");
		String stuId = s.getStuId();

		Map<String, String> map = new HashMap<String, String>();
		map.put("stuId", stuId);
		map.put("updateNum", updateNum);
		int result = memberService.studentUpdate(map);

		if (result > 0) {
			Student student = memberService.loginStudent(s);
			session.setAttribute("loginUser", student);
			session.setAttribute("alertMsg", "수정 성공");

			return "redirect:/studentMyPage";
		} else {
			model.addAttribute("alertMsg", "수정실패");
			return "redirect:/";
		}
	}
	@RequestMapping("student.upadatePwd")
	public String updatePwdSt(Student s,String newPwd, HttpSession session, Model model) {
		model.addAttribute("bbsId", "mypage");

		Student student = (Student)session.getAttribute("loginUser");
		
		s.setStuId(student.getStuId());
		s.setStuPwd(newPwd);
		
		int result = memberService.studentPwdUpdate(s);
		if (result > 0) {
			session.setAttribute("loginUser", memberService.loginStudent(s));
			session.setAttribute("alertMsg", "비밀번호 수정 성공");
			return "redirect:/studentMyPage";
		} else {
			session.setAttribute("alertMsg", "비밀번호 수정 실패");
			return "redirect:/";
		}
	}

	@RequestMapping("imgselect")
	public String imgselectupdate(String picNo, HttpSession session, Model model) {
		model.addAttribute("bbsId", "mypage");
		
		Student s = (Student) session.getAttribute("loginUser");
		String stuId = s.getStuId();

		Map<String, String> map = new HashMap<String, String>();
		map.put("stuId", stuId);
		map.put("picNo", picNo);
		int result = memberService.studentSelectImg(map);

		if (result > 0) {
			Student student = memberService.loginStudent(s);
			session.setAttribute("loginUser", student);
			return "redirect:/studentMyPage";
		} else {
			model.addAttribute("alertMsg", "수정실패");
			return "redirect:/";
		}
	}

	// 급식
	@ResponseBody
	@GetMapping(value = "mealService", produces = "application/json; charset=UTF-8")
	public String getMealInfo(HttpSession session, Model model) throws IOException {
		model.addAttribute("bbsId", "mypage");
		
		Student s = (Student) session.getAttribute("loginUser");
		String schoolCode = s.getClassCode().substring(0, 7);
		String oecode = memberService.selectOeCode(schoolCode);
		String url = "https://open.neis.go.kr/hub/mealServiceDietInfo";
		LocalDate today = LocalDate.now();
		// 날짜를 특정 형식으로 포맷
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		String formattedDate = today.format(formatter);
		LocalDate tomorrow = today.plusDays(1);
		String tomorrowString = tomorrow.format(formatter);
		url += "?KEY=" + SERVICE_KEY;
		url += "&type=json";
		url += "&pIndex=1";
		url += "&pSize=100";
		url += "&ATPT_OFCDC_SC_CODE=" + URLEncoder.encode(oecode, "UTF-8");
		url += "&SD_SCHUL_CODE=" + URLEncoder.encode(schoolCode, "UTF-8");
		url += "&MLSV_FROM_YMD=" + URLEncoder.encode(formattedDate, "UTF-8");
		url += "&MLSV_TO_YMD=" + URLEncoder.encode(tomorrowString, "UTF-8");

		// 1. 요청하고자하는 url을 전달하면서 java.net.URL객체 생성
		URL requestURL = new URL(url);

		// 2. 만들어진 URL 객체를 가지고 HttpURLConnection객체를 생성
		HttpURLConnection urlConnection = (HttpURLConnection) requestURL.openConnection();

		// 3. 요청에 필요한 Header정보 설정하기 POST로 보낼꺼면 POST적기
		urlConnection.setRequestMethod("GET");

		// 4. 해당 api서버로 요청 보낸 후 입력데이터 읽어오기
		BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

		String result = "";
		String line;
		while ((line = br.readLine()) != null) {
			result += line;
		}

		br.close();
		urlConnection.disconnect();

		return result;

	}

	// 시간표
	@ResponseBody
	@GetMapping(value = "scheduleService", produces = "application/json; charset=UTF-8")
	public String getAirInfo(HttpSession session, Model model) throws IOException {
		model.addAttribute("bbsId", "mypage");
		
		Student s = (Student) session.getAttribute("loginUser");

		// 학교 코드 추출
		String schoolCode = s.getClassCode().substring(0, 7);
		String oecode = memberService.selectOeCode(schoolCode);

		// 현재 날짜를 기반으로 연도(year)와 학기(semester) 계산
		LocalDate today = LocalDate.now();
		int currentYear = today.getYear();
		String year = String.valueOf(currentYear); // 현재 연도

		// 학기 결정 (1월~6월: 1학기, 7월~12월: 2학기)
		int currentMonth = today.getMonthValue();
		String semester = (currentMonth >= 1 && currentMonth <= 6) ? "1" : "2";

		String grade = s.getClassCode().substring(9, 9); // 학년 (예: "01")
		String classNum = s.getClassCode().substring(11, 11); // 반 (예: "02")

		// 날짜 형식 지정 및 시간표 시작일자와 종료일자 설정
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		String formattedDate = today.format(formatter);
		String tomorrowString = today.plusDays(1).format(formatter);

		// API URL 생성
		String url = "https://open.neis.go.kr/hub/elsTimetable";
		url += "?KEY=" + SERVICE_KEY;
		url += "&Type=json";
		url += "&pIndex=1";
		url += "&pSize=100";
		url += "&ATPT_OFCDC_SC_CODE=" + URLEncoder.encode(oecode, "UTF-8");
		url += "&SD_SCHUL_CODE=" + URLEncoder.encode(schoolCode, "UTF-8");
		url += "&AY=" + URLEncoder.encode(year, "UTF-8");
		url += "&SEM=" + URLEncoder.encode(semester, "UTF-8");
		url += "&GRADE=" + URLEncoder.encode(grade, "UTF-8");
		url += "&CLASS_NM=" + URLEncoder.encode(classNum, "UTF-8");
		url += "&TI_FROM_YMD=" + URLEncoder.encode(formattedDate, "UTF-8");
		url += "&TI_TO_YMD=" + URLEncoder.encode(tomorrowString, "UTF-8");

		// URL 연결 및 데이터 수신
		URL requestURL = new URL(url);
		HttpURLConnection urlConnection = (HttpURLConnection) requestURL.openConnection();
		urlConnection.setRequestMethod("GET");

		BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		String result = "";
		String line;
		while ((line = br.readLine()) != null) {
			result += line;
		}

		br.close();
		urlConnection.disconnect();

		return result;
	}

	// ------------------------------ 부모님 ------------------------------
	@RequestMapping("login.parents")
	public String login_parents() {
		return "member/login_parents";
	}
	
	// 부모님 로그인
	@RequestMapping("parentsLogin")
	public ModelAndView parentsLogin(Parents p, HttpSession session, ModelAndView mv, Model model) {
		model.addAttribute("bbsId", "parentsMypage");
		
		Parents loginParents = memberService.loginParents(p);
		if (loginParents == null) { // 아이디없는 경우
			mv.addObject("errorMsg", "일치하는 아이디를 찾을 수 없습니다.");
			mv.setViewName("common/errorPage");
		} else {
			session.setAttribute("loginUser", loginParents);
			Student s = memberService.selectStudent(p);
			session.setAttribute("child", s);
			if (s.getClassCode() == null) {
				mv.setViewName("parents/parents_myPage");
			} else {
				Teacher t = memberService.selectTeacher(s);
				String part1 = t.getPhone().substring(0, 3);
				String part2 = t.getPhone().substring(3, 7);
				String part3 = t.getPhone().substring(7);
				t.setPhone(part1 + "-" + part2 + "-" + part3);
				session.setAttribute("teacher", t);
				mv.setViewName("parents/parents_myPage");
			}

		}
		return mv;
	}

	@RequestMapping("enrollForm.parents")
	public String enroll_parents() {
		return "member/enrollForm_parents";
	}
	
	// 학부모 회원가입
	@RequestMapping("enroll.parents")
	public String enroll_parents(Parents p,HttpSession session, Model model) {
		int result = memberService.insertParents(p);
		
		if(result > 0) {
			session.setAttribute("alertMsg", "성공적으로 회원가입이 완료되었습니다.");
			return "member/login_parents";
		}
		return "member/enrollForm_teacher";
	}

	// 자녀 아이디 검색
	@ResponseBody
	@RequestMapping(value="child.list", produces="application/json; charset-UTF-8")
	public String ajaxSelectReplyList(String name, String phone) {
		Student s = new Student("","","",name,phone,"","","","",""); 
		ArrayList<Student> list = memberService.selectStudentList(s);
		for (Student st : list) {
			if(st.getClassCode()==null) {
				st.setClassCode("아직반이배정되지않았습니다");
			}else {
				try {
					st.setClassCode(Integer.parseInt(st.getClassCode().substring(st.getClassCode().length()-2))+"반 / "+st.getStuNum()+"번");
				}catch (NumberFormatException e) {
					st.setClassCode(st.getClassCode().substring(st.getClassCode().length()-2)+"반 / "+st.getStuNum()+"번");
				}
				
			}			
		}
		return new Gson().toJson(list);
	}

	// 부모님 정보수정
	@ResponseBody
	@RequestMapping("modify_parents_info")
	public int modifyParentsInfo(Parents p, HttpSession session, Model model) {
		int result = memberService.updateParentsInfo(p);
		return result;
	}

	// 부모님 비밀번호변경
	@ResponseBody
	@RequestMapping("modify_parents_PWD")
	public int modifyParentsPWD(Parents p,HttpSession session) {
		int result = memberService.updatePwd(p);
		if(result>0) {
			session.setAttribute("loginUser", memberService.loginParents(p));
		}
		return result;
	}

	// 로그아웃
	@RequestMapping("logout.parents")
	public String logout_parents(HttpSession session) {
		session.invalidate();
		return "member/login";
	}
	// ------------------------------ 선생님 ------------------------------
	// 선생님 로그인
	@RequestMapping("login.teacher")
	public String login_teacher(Teacher t, HttpSession session, ModelAndView mv, String savetcId, HttpServletResponse response, Model model) {
		model.addAttribute("bbsId", "teacherMypage");
		
	    Teacher loginTeacher = memberService.loginTeacher(t);

	    if (loginTeacher == null) {
	        System.out.println("로그인 실패");
	        return "member/login_teacher";
	    } else {
	        session.setAttribute("loginUser", loginTeacher);  // 로그인한 Teacher 객체를 세션에 저장
	        System.out.println("로그인 성공");

	        // 로그인 된 유저 정보의 status 값
	        String status = loginTeacher.getStatus();
	        
	        // 총 공지사항 수를 구하는 메서드 호출
	     	int NoticeCount = memberService.getNoticeCount();
	     	String tcId = loginTeacher.getTcId();
	     	
	     	// 상담일정 리스트 가져오기
	     	ArrayList<Counsel> teacherCounsel = memberService.getTeacherCounsel(tcId);
	     	System.out.println(teacherCounsel);
	     	session.setAttribute("teacherCounsel", teacherCounsel);
	        
	     	 // 공지사항 수를 이용해 공지사항 리스트 가져오기
	        ArrayList<TeacherNotice> teacherNotices = memberService.getTeacherNotices(NoticeCount, tcId);
	        // 공지사항 리스트를 세션에 추가
	        session.setAttribute("teacherNotice", teacherNotices);
	        
	        String classCode = loginTeacher.getClassCode();
	        
	        // classCode를 이용해 메모 리스트 가져오기
	        ArrayList<TeacherMemo> teacherMemo = memberService.getTeacherMemo(classCode);
	        //메모 리스트를 세션에 추가
	        session.setAttribute("teacherMemo", teacherMemo);

	        // 로그인 된 유저 정보의 status 값이 'A'일 경우
	        if ("A".equals(status)) {
	            // 로그인된 유저의 SC_CODE를 세션에 저장
	            String scCode = loginTeacher.getScCode();
	            session.setAttribute("scCode", scCode);  // 세션에 SC_CODE 저장
	            session.setAttribute("loginUser", loginTeacher);
	            
	            System.out.println("관리자 로그인 성공");
	            
	            return "redirect:/manager/manager_main";  // 관리자 메인 페이지로 리다이렉트
	        } else if("0000".equals(classCode)) { // classCode 값이 0000일 경우(반 개설 전 선생님)
	        	
	        	System.out.println("반 개설 전 선생님 로그인 성공");
	        	
	            return "teacher/myPageStart";
	        } else if(!"0000".equals(classCode)) { // classCode 값이 0000이 아닐 경우(반 개설 후 선생님)
	        	
	        	System.out.println("반 개설 후 선생님 로그인 성공");
	        	ArrayList<Student> slist = memberService.selectStuList(classCode);
	        	System.out.println(slist);
	        	session.setAttribute("slist", slist);
	            return "teacher/myPage";
	        } else {
	            // 오류 처리
	            System.out.println("알 수 없는 정보입니다.");
	            return "member/login_teacher";
	        }
	    }
	}
	
	// 선생님 회원가입 창으로 이동
	@RequestMapping("enrollForm.tea")
	public String enrollForm_tea() {
		return "member/enrollForm_teacher";
	}

	// 선생님 회원가입
	@RequestMapping("enrollForm.teacher")
	public String enrollForm_teacher(Teacher t, HttpSession session, Model model) {

		int result = memberService.insertTeacher(t);

		if (result > 0) {
			session.setAttribute("alertMsg", "성공적으로 회원가입이 완료되었습니다.");
			return "redirect:/login.teacher";
		} else {
			model.addAttribute("errorMsg", "회원가입 실패");
			return "common/errorPage";
		}
	}

	// 선생님 회원가입 학교 검색(학교코드 불러오기)
	@ResponseBody
	@RequestMapping(value = "searchSchool.list", produces = "application/json; charset-UTF-8")
	public String ajaxSelectReplyList(String scName) {
		ArrayList<School> list = memberService.searchSchoolsByName(scName);
		return new Gson().toJson(list);
	}
	
	// 교사 비번 수정
		@RequestMapping("updatePassword.me")
		public String updatePassword(Teacher t, String newPassword, HttpSession session, Model m) {
			// 로그인 한 교사정보 가져오기
			Teacher teacher = (Teacher) session.getAttribute("loginUser");
			if (teacher == null) {
				session.setAttribute("alertMsg", "로그인정보가 없습니다.");
				return "teacher/myPage";
			}

			t.setTcId(teacher.getTcId());
			t.setTcPwd(newPassword);

			int result = memberService.updatePassword(t);

			if (result > 0) {
				session.setAttribute("loginUser", memberService.loginTeacher(t));
				session.setAttribute("alertMsg", "비밀번호 수정 성공");
				return "teacher/myPage";
			} else {
				session.setAttribute("alertMsg", "비밀번호 수정 실패");
				return "teacher/myPage";
			}
		}
		
		// 선생님 반 개설
		@RequestMapping("teacher.classCode")
		public String updateClassCode(Teacher t, HttpSession session, Model model) {
			model.addAttribute("bbsId", "teacherMypage");
			t.setTcPwd(((Teacher)session.getAttribute("loginUser")).getTcPwd());

			if (t.getClassCode() == null || t.getClassCode().trim().isEmpty()) {
				model.addAttribute("alertMsg", "반 코드를 입력해야 합니다.");
				return "redirect:/"; // 적절한 메시지로 안내
			}

			int result = memberService.updateClassCode(t);

			if (result > 0) {
				session.setAttribute("loginUser", memberService.loginTeacher(t));
				session.setAttribute("alertMsg", "반 개설 성공");
				
				return "teacher/myPage";
			} else {
				model.addAttribute("alertMsg", "반 개설 실패");
				return "teacher/myPageStart";
			}
		}
		
	// 선생님 마이페이지 이동
	@RequestMapping("teacher.myPage")
	public String teacherMyPage(Model model) {
		model.addAttribute("bbsId", "teacherMypage");
		
		return "teacher/myPage";
	}
	
	// 선생님 공지사항 추가
	@RequestMapping("addNoticeForm")
	public String addNoticeForm(String noticeTitle, HttpSession session, Model model) {
		model.addAttribute("bbsId", "teacherMypage");
		
		Teacher loginTeacher = (Teacher) session.getAttribute("loginUser");

		String tcId = loginTeacher.getTcId();
		String classCode = loginTeacher.getClassCode();

		int result = memberService.insertNotice(tcId, classCode, noticeTitle);		
		
		int noticeCount = memberService.getNoticeCount();
        ArrayList<TeacherNotice> teacherNotices = memberService.getTeacherNotices(noticeCount, tcId);
        model.addAttribute("teacherNotice", teacherNotices);

		return "teacher/myPage";
	}

	//공지사항 수정
	@RequestMapping("updateNotice")
	public String updateNotice(String noticeTitle, int noticeNo, HttpSession session, Model model) {
		model.addAttribute("bbsId", "teacherMypage");
		
		Teacher loginTeacher = (Teacher) session.getAttribute("loginUser");

		String tcId = loginTeacher.getTcId();
		
		int result = memberService.updateNotice(noticeTitle, noticeNo);
		
		int noticeCount = memberService.getNoticeCount();
        ArrayList<TeacherNotice> teacherNotices = memberService.getTeacherNotices(noticeCount, tcId);
        model.addAttribute("teacherNotice", teacherNotices);
		
		return "teacher/myPage";
	}

	// 선생님 공지사항 삭제
	@RequestMapping("deleteNotice")
	public String deleteNotice(String noticeTitle, HttpSession session, Model model) {
		model.addAttribute("bbsId", "teacherMypage");
		
		Teacher loginTeacher = (Teacher) session.getAttribute("loginUser");

		String tcId = loginTeacher.getTcId();

		int result = memberService.deleteNotice(noticeTitle);
		
		int noticeCount = memberService.getNoticeCount();
        ArrayList<TeacherNotice> teacherNotices = memberService.getTeacherNotices(noticeCount, tcId);
        model.addAttribute("teacherNotice", teacherNotices);

		return "teacher/myPage";
	}

	// 선생님 메모 추가
	@RequestMapping("addMemo")
	public String AddMemo(String memoContent, HttpSession session, Model model) {
		model.addAttribute("bbsId", "teacherMypage");
		
		Teacher loginTeacher = (Teacher) session.getAttribute("loginUser");

		String tcId = loginTeacher.getTcId();
		String classCode = loginTeacher.getClassCode();

		int result = memberService.insertMemo(tcId, classCode, memoContent);
		
		ArrayList<TeacherMemo> teacherMemo = memberService.getTeacherMemo(classCode);
		model.addAttribute("teacherMemo", teacherMemo);

		return "teacher/myPage";
	}

	// 선생님 메모 삭제
	@RequestMapping("deleteMemo")
	public String deleteMemo(String memoContent, HttpSession session, Model model) {
		model.addAttribute("bbsId", "teacherMypage");
		
		Teacher loginTeacher = (Teacher) session.getAttribute("loginUser");
		String classCode = loginTeacher.getClassCode();
		
		int result = memberService.deleteMemo(memoContent);
		
		ArrayList<TeacherMemo> teacherMemo = memberService.getTeacherMemo(classCode);
		model.addAttribute("teacherMemo", teacherMemo);

		return "teacher/myPage";
	}
	
}