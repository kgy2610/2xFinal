<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <div id="login-box">
        <div id="login-logo" onclick="redirectToLogin()" style="cursor: pointer;">
                <img src="<c:url value='/resources/img/logo.png'/>">
        </div>
        <div id="login-teacher">
            선생님 로그인
        </div>
    </div>
    <div id="login-form">
        <form action="login.teacher" method="post">
            <div id="login-id">
                <p>아이디</p>
                <input type="text" placeholder="아이디를 입력하세요." autofocus id="tcId" name="tcId">
            </div>
            <div id="login-pwd">
                <p>비밀번호</p>
                <input type="password" placeholder="비밀번호를 입력하세요." autofocus id="tcPwd" name="tcPwd">
            </div>
            <div id="login-btn">
                <input type="submit" value="로그인">
            </div>
        </form>

        <div id="join-find">
            <p><a href="enrollForm.tea">회원가입</a></p>
            <p> | </p>
            <p><a href="find.id-pwd">아이디/비밀번호 찾기</a></p>
        </div>
    </div>
    
    
     
    
    <script>
        function redirectToLogin() {
            window.location.href = '<c:url value="/login"/>'; // login 컨트롤러로 이동
        }
    </script>
    
   <c:if test="${not empty loginError}">
        <script>
            alert("${loginError}");
        </script>
    </c:if>
</body>
</html>