<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%String alertMsg = (String)session.getAttribute("alertMsg"); %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AGIT</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/login.css'/>">
    <link rel="stylesheet" href="<c:url value='/resources/css/login_detail.css'/>">
</head>

<body>
	<% if(alertMsg != null) {%>
		<script>
		alert("<%=alertMsg%>");
		</script>
	<% session.removeAttribute("alertMsg"); %>
	<%} %>
    <div id="login-box">
        <div id="login-logo" onclick="redirectToLogin()" style="cursor: pointer;">
                <img src="<c:url value='/resources/img/logo.png'/>">
        </div>
        <div id="login-student">
            학생 로그인
        </div>
    </div>
    <div id="login-form">
        <form action="login.student" method="post">
            <div id="login-id">
                <p>아이디</p>
                <input type="text" placeholder="아이디를 입력하세요." autofocus id="stuId" name="stuId" required>
            </div>
            <div id="login-pwd">
                <p>비밀번호</p>
                <input type="password" placeholder="비밀번호를 입력하세요." autofocus id="stuPwd" name="stuPwd" required>
            </div>
            <div id="login-btn">
                <input type="submit" value="로그인">
            </div>
        </form>

        <div id="join-find">
            <p><a href="enrollForm.stu">회원가입</a></p>
            <p> | </p>
            <p><a href="find.id-pwd">아이디/비밀번호 찾기</a></p>
        </div>
    </div>
    
    <script>
        function redirectToLogin() {
            window.location.href = '<c:url value="/login"/>';
        }
    </script>
</body>
</html>