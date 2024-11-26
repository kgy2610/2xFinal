
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>AGIT</title>
<link rel="stylesheet"
	href="<c:url value='/resources/css/student/waitClassStatus.css'/>">
</head>
<body>
	<jsp:include page="../common/student_menubar.jsp" />

	<div class="wrap">
		<img src="<c:url value='/resources/img/student/myPageStart.png'/>">
		<h4>선생님이 요청을 받아주실 때까지 기다려주세요!</h4>
		<div class="center">
			<form method="post" action="student.classCode">
			<input type="hidden" name="stuId" value="${loginUser.stuId}" />
				<input type="text" value="${loginUser.classCode}" id="classCode" name="classCode"> 
				<button class="add-button"  type="submit">수정</button>
			</form>
		</div>
	</div>
</body>
</html>
