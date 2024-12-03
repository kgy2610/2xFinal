package com.twoX.agit.after.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.twoX.agit.after.service.AfterSchoolBoardService;
import com.twoX.agit.after.vo.AfterSchoolBoard;
import com.twoX.agit.common.template.Template;
import com.twoX.agit.common.vo.PageInfo;
import com.twoX.agit.member.model.vo.AfterSchool;
import com.twoX.agit.member.model.vo.Student;
import com.twoX.agit.member.model.vo.Teacher;
import com.twoX.agit.member.service.MemberService;

@CrossOrigin
@Controller
public class AfterSchoolBoardController {
	private final AfterSchoolBoardService afterSchoolBoardService;
	private final AfterSchoolBoardService afterschoolService;

	@Autowired
	public AfterSchoolBoardController(AfterSchoolBoardService afterSchoolBoardService,AfterSchoolBoardService afterschoolService) {
		this.afterSchoolBoardService = afterSchoolBoardService;
		this.afterschoolService = afterschoolService;
	}

	

	@RequestMapping("list.bo")
	public String selectList(AfterSchool as, HttpSession session, @RequestParam(value = "cpage", defaultValue = "1") int currentPage, Model model) {
		
		AfterSchool afCode = (AfterSchool) session.getAttribute("as");
		System.out.println(afCode);
		if (afCode == null) {
			System.out.println("세션에서 afCode 값이 null입니다.");
			model.addAttribute("alertMsg", "세션에 방과후 코드가 없습니다.");
			return "teacher/afterClass";
		} else {
			System.out.println("세션에서 afCode 값을 가져왔습니다: " + afCode.getCode());
		}
		
		//현재 세션의 코드를 code 라는 변수에 넣기
		String code = afCode.getCode();
		System.out.println("방과후 반 코드: " + code);
		
		//선생님의 코드와 학생의 코드가 같은 학생의 총 명수
		int boardCount = afterSchoolBoardService.afterSchoolListCount(code);
		System.out.println(boardCount);
		


		PageInfo pi = Template.getPageInfo(boardCount, currentPage, 5, 5);
		ArrayList<AfterSchoolBoard> list = afterSchoolBoardService.afSelectList(code, pi);
		
		//status가 y이고 code가 선생님과 학생이 같을때 가져오기
		ArrayList<Student> studentList = afterSchoolBoardService.asCodeStudent(code);
		for (Student s : studentList) {
			if(s.getClassCode()==null) {
				continue;
			}
			s.setClassCode(s.getClassCode().substring(9, 10)+" - "+s.getClassCode().substring(10,12));
			
			}
		
		System.out.println("list" + list);

		
		System.out.println("밍" + studentList);
		
		
		model.addAttribute("list", list);
		model.addAttribute("pi", pi);
		model.addAttribute("studentList", studentList);
		System.out.println(pi);
		return "teacher/afterClass";
	}
	
	@RequestMapping("teacherAfterDetail.bo")
	public String teacherAfterschoolDetail(@RequestParam(value="boNo")int boNo,@RequestParam(value="cpage",defaultValue="1") int currentPage,HttpSession session,Model model) {
		AfterSchoolBoard npage = afterschoolService.selectNowBoard(boNo);
		model.addAttribute("npage",npage);
		model.addAttribute("cpage",currentPage);
		return "teacher/teacherAsDetail";
	}
	
	//학생 승인 목록 조회 (로그인된 관리자와 학교코드가 일치하며, 승인을 받지 않은 상태의 선생님 리스트)
		@ResponseBody 
		@RequestMapping(value = "acceptStudent", produces = "application/json; charset=UTF-8")
		public Map<String, Object> ajaxAcceptTeacherList(HttpSession session) {
			Map<String , Object> response = new HashMap<String , Object>();
			
			AfterSchool afCode = (AfterSchool) session.getAttribute("as");
			
			if(afCode != null) {
				String scCode = afCode.getCode();
//				System.out.println("로그인한 유저의 Code: " + code);
				
				//CODE가 로그인된 선생님과 같은 학생의 리스트 불러오기
				ArrayList<Student> acceptList = afterSchoolBoardService.ajaxAcceptStudentListbyScCode(scCode);
	        System.out.println("Controller | 승인이 필요한 선생님 리스트 : " + acceptList);
				
				
		        response.put("acceptStudents", acceptList);  // 데이터가 배열로 들어가야 합니다.
		    } else {
		        response.put("message", "로그인 정보가 없습니다.");
		    }
			
			return response;
		}
	
		
		//학생 승인 (status 값이 N인 선생님을 수락버튼을 통해 Y로 변경)
		@ResponseBody
		@RequestMapping(value="requestStudent")
		public String ajaxRequestTeacher(HttpSession session, @RequestParam("status") String status, @RequestParam("stuId") String stuId){
			AfterSchool afCode = (AfterSchool) session.getAttribute("as");
			
			if(afCode != null) {
				String scCode = afCode.getCode();
				System.out.println("Request Controller | 로그인한 유저의 scCode : " + scCode);
				
				int result = afterSchoolBoardService.RequestStudentByTcIdAndStatus(stuId, status, scCode);
				System.out.println("Request Controller | 승인할 학생의 stuId, status : " + stuId + ", " + status);
				
				if(result > 0) {
					return "success";
				}else {
					return "fail";
				}
			}
			return "success";
		}
		
		
		// 학생 회원가입 창으로 이동
		@RequestMapping("eventPhotos.af")
			public String eventPhotos() {
				return "teacher/eventPhotos";
		}
}
