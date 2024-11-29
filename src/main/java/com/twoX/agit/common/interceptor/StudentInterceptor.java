package com.twoX.agit.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import com.twoX.agit.member.model.vo.Student;

public class StudentInterceptor implements HandlerInterceptor{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//return : true -> 기존요청흐름대로 진행(Controller로 이동)
		//return : false -> 요청중단후 반환
		
		HttpSession session = request.getSession();
		if(session.getAttribute("loginUser") instanceof Student) {
			return true;	
		} else {
			session.setAttribute("alertMsg", "잘못된 접근입니다.");
			response.sendRedirect(request.getContextPath()+"/login");
			return false;
		}
	}
}
