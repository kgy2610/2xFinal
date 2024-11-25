<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>관리자 메인 페이지</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/manager_main.css'/>">
</head>
<body>
    <div id="manager-con">
        <div id="manager_schoolInfo">
            <div id="manager_mainLogo">
                <img src="<c:url value='/resources/img/logo.png'/>">
            </div>
            <div id="school-info">
                <p>${schoolName.scName}</p>
                <p>전체 교직원 수 : ${teacherCount}명</p>
                <p>현재 학생 수 : ${studentCount}명</p>
            </div>
        </div>
        <div id="manager-btn">
            <div id="manager-btnBox" onclick="moveSearchTeacher()">
                <img src="<c:url value='/resources/img/manager_main-icon.png'/>">
                <p>교직원 조회/등록</p>
            </div>
        </div>
    </div>
    <script>
        function moveSearchTeacher(){
            window.location.href = "manager_sub";
        }
    </script>
</body>
</html>