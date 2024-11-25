package com.twoX.agit.member.service;

import javax.servlet.http.HttpSession;

public class LoginCheckService {
	public static boolean checkLogin(HttpSession session) {
        // 세션에서 로그인 유저 정보 가져오기
        Object loginUser = session.getAttribute("loginUser");
        
        // 로그인되지 않은 경우 false 반환
        return loginUser != null;
    }
}
