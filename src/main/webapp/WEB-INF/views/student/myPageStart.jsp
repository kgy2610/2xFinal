
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
	href="<c:url value='/resources/css/student/student_myPageStart.css'/>">
</head>
<body>
	<jsp:include page="../common/student_menubar.jsp" />

	<div class="wrap">
		<img src="<c:url value='/resources/img/student/myPageStart.png'/>">
		<h4>선생님이 주신 코드를 입력해주세요!</h4>
		<div class="center">
			<form method="post" action="student.classCode">
			<input type="hidden" name="stuId" value="${loginUser.stuId}" />
				<input type="text" placeholder="코드를 입력하세요" id="classCode" name="classCode"> 
				<button class="add-button"  type="submit">+</button>
			</form>
		</div>
	</div>
</body>
</html>
