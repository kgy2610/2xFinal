package com.twoX.agit.manager.controller;

import java.util.ArrayList;
import java.util.HashMap;
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

import com.twoX.agit.manager.Service.ManagerService;
import com.twoX.agit.member.model.vo.School;
import com.twoX.agit.member.model.vo.Teacher;

@CrossOrigin
@Controller
@RequestMapping("/manager")
public class ManagerController {
	private ManagerService managerService;
	
	@Autowired
	public ManagerController(ManagerService managerService) {
		this.managerService = managerService;
	}
	
	@RequestMapping("/manager_main")
    public String managerMain(HttpSession session, Model model) {
		Teacher loginUser = (Teacher) session.getAttribute("loginUser");
		
		if(loginUser != null) {
			//로그인 된 계정의 SC_CODE 가져오기
			String scCode = loginUser.getScCode();
			//SC_CODE로 학교 이름 조회하기
			School schoolName = managerService.getSchoolNameByScCode(scCode);
			
			//SchoolName을 JSP로 전달
			model.addAttribute("schoolName", schoolName);
		}
		
		// 교직원 수를 조회하고 가져옴
	    int teacherCount = managerService.getTeacherCount();
	    
	    String scCode = (String) session.getAttribute("scCode");
	    // 해당 학교의 총 학생 수를 가져오기
        int studentCount = managerService.getStudentCountBySchoolCode(scCode);
	    
	    // 모델에 교직원 수 데이터를 전달
	    model.addAttribute("teacherCount", teacherCount);
	    
	    model.addAttribute("studentCount", studentCount);
	    
        return "manager/manager_main";
    }
	
	
	// 승인된 선생님 리스트 조회 (로그인된 관리자와 학교 코드가 일치하며, 승인을 받은 상태의 선생님 리스트)
	@RequestMapping("/manager_sub")
	public String activeTeacherList(HttpSession session, Model model) {
	    Teacher loginUser = (Teacher) session.getAttribute("loginUser");
	    
	    if (loginUser != null) {
	        String scCode = loginUser.getScCode();
	        model.addAttribute("scCode", scCode);
	        
	        ArrayList<Teacher> teacherList = managerService.getActiveTeachersByScCode(scCode);
	        model.addAttribute("teachers", teacherList);
	    } else {
	        model.addAttribute("message", "로그인 정보가 없습니다.");
	        
	        return "/member/login";
	    }
	    
	    
	    
	    return "manager/manager_sub";
	}

	//교직원 승인 목록 조회 (로그인된 관리자와 학교코드가 일치하며, 승인을 받지 않은 상태의 선생님 리스트)
	@ResponseBody 
	@RequestMapping(value = "acceptTeacher", produces = "application/json; charset=UTF-8")
	public Map<String, Object> ajaxAcceptTeacherList(HttpSession session) {
		Map<String , Object> response = new HashMap<String , Object>();
		
		Teacher loginUser = (Teacher) session.getAttribute("loginUser");
		
		if(loginUser != null) {
			String scCode = loginUser.getScCode();
//			System.out.println("로그인한 유저의 scCode: " + scCode);
			
			//SC_CODE이 로그인된 유저와 같은 선생님의 리스트 불러오기
			ArrayList<Teacher> acceptList = managerService.ajaxAcceptTeacherListbyScCode(scCode);
//	        System.out.println("Controller | 승인이 필요한 선생님 리스트 : " + acceptList);
	        
	        response.put("acceptTeachers", acceptList);  // 데이터가 배열로 들어가야 합니다.
	    } else {
	        response.put("message", "로그인 정보가 없습니다.");
	    }
		
		return response;
	}
		
	//교직원 삭제 (선생님의 고유 클래스 코드를 삭제)
	@ResponseBody
	@RequestMapping(value="deleteTeacher")
	public String ajaxDeleteTeacher(HttpSession session, @RequestParam("tcId") String tcId) {
		Teacher loginUser =(Teacher) session.getAttribute("loginUser");
		
		if(loginUser != null) {
			String scCode = loginUser.getScCode();
//			System.out.println("Delete Controller | 로그인 한 유저의 scCode : " + scCode);
			
			
			int result = managerService.deleteTeacherByTcIdAndScCode(tcId, scCode);
			
//			System.out.println("Delete Controller | 삭제할 선생님의 tcId : " + tcId);
			System.out.println("result : " + result);
			if(result > 0) { //result > 0 -> deleteTeacherByTcIdAndScCode가 영향을 준 row가 0 이상
							  //하나의 tcId 정보가 변경이 되었을 경우 -> result = 1 
				return "success";
			}else {
				return "fail";
			}
		}
		return "success";
	}
	
	//교직원 승인 (status 값이 N인 선생님을 수락버튼을 통해 Y로 변경)
	@ResponseBody
	@RequestMapping(value="requestTeacher")
	public String ajaxRequestTeacher(HttpSession session, @RequestParam("status") String status, @RequestParam("tcId") String tcId){
		Teacher loginUser = (Teacher) session.getAttribute("loginUser");
		
		if(loginUser != null) {
			String scCode = loginUser.getScCode();
			System.out.println("Request Controller | 로그인한 유저의 scCode : " + scCode);
			
			int result = managerService.RequestTeacherByTcIdAndStatus(tcId, status, scCode);
			System.out.println("Request Controller | 승인할 선생님의 tcId, status : " + tcId + ", " + status);
			
			if(result > 0) {
				return "success";
			}else {
				return "fail";
			}
		}
		return "success";
	}
}
