<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AGIT</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/menubar.css'/>">
</head>
<body>
    <div class="nav">
        <img src="<c:url value='/resources/img/logo.png'/>">
        <div class="menu">
            <label for="mypage"><a href="studentMyPage">마이페이지</a></label>
            <label for="homework"><a href="homework">숙제</a></label>
            <label for="afterSchool"><a href="#">방과후 반</a></label>
        </div>
        <div class="undermenu">
            <label for="info" onclick="openUpdateModal()"><a href="#">정보수정</a></label>
            <label for="logout"> <a href="logout.me">로그아웃</a></label>
        </div>
    </div>
</body>
</html>