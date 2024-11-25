<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AGIT</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/login.css'/>">
</head>
<body>
    <div id="login-box">
        <div id="login-logo">
            <img src="<c:url value='/resources/img/logo.png'/>">
        </div>
        <div id="login-bar">
            로그인하기
        </div>
        <div id="login-btn">
            <div class="login-con" onclick="studentLogin()">
                <img src="<c:url value='/resources/img/student_icon.png'/>" style="width: 250px; height: 140px;">
                <p>학생 로그인</p>
            </div>
            <div class="login-con" style="padding: 40px;" onclick="parentsLogin()">
                <img src="<c:url value='/resources/img/parents_icon.png'/>" style="width: 190px; height: 140px;">
                <p>학부모 로그인</p>
            </div>
            <div class="login-con" onclick="teacherLogin()">
                <img src="<c:url value='/resources/img/teacher_icon.png'/>" style="width: 240px; height: 130px;">
                <p>선생님 로그인</p>
            </div>
        </div>
    </div>
    <script>
        function studentLogin(){
            window.location.href = 'login.student';
        }
        function parentsLogin(){
            window.location.href = 'login.parents';
        }
        function teacherLogin(){
            window.location.href = 'login.teacher';
        }
    </script>
</body>
</html>