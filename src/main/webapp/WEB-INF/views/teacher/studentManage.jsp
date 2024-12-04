<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.twoX.agit.member.model.vo.Teacher"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	Teacher loginUser = (Teacher) session.getAttribute("loginUser");
String classCode = loginUser.getClassCode();
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>teacher_menubar</title>

<link rel="stylesheet"
	href="<c:url value='/resources/css/menubar.css'/>">


<link rel="stylesheet" href="<c:url value='/resources/css/teacher/studentManage.css'/>">

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="<c:url value='/resources/js/teacher/studentManage.js'/>"></script>
</head>


<body>
	<jsp:include page="../common/teacher_menubar.jsp" />
	<div class="wrap">
	<div class="whole_body">
		<div class="use-body">
			<div class="content1">
				<div class="head-subject">
					<div class="student-list">학생리스트</div>
					<button class="plus-button" onclick="openModal(this)">+</button>
				</div>
				<div class="real-list">
					<div class="notice">
						<div class="list-information">
							<div>번호</div>
							<div>이름</div>
							<div>출석률</div>
						</div>
						<hr class="line1">
						<div class="list-info">
						<c:forEach var="g" items="${stuManageList}">
							<div class="real-list-information"
								onclick="showStudentInfo('${g.STUNAME}', '${g.PHONE}', '${g.ATTENDANCERATE}', '${g.STUID}')">
								<div>${g.STUNUM}</div>
								<div>${g.STUNAME}</div>
								<div>${g.ATTENDANCERATE}%</div>
							</div>
							<hr class="line2">
						</c:forEach>
						</div>
					</div>
				</div>
			</div>
			<div class="content2">
				<div class="content2-con1">
					<div class="student-information">학생정보</div>
					<div class="real-student-information">
						<div>이름 :</div>
						<div>전화번호 :</div>
						<div>출석률 :</div>
						<form action="studentManageCansel.me" method="post">
							<input type="hidden" name="classCode" value="<%=classCode%>">
							<input type="hidden" name="stuId" value="">
							<button type="submit" class="cancel">승인 취소</button>
						</form>
					</div>
				</div>
				<div class="content2-con2">
					<div class="grade-information">성적</div>
					<div class="notice1">
						<div class="real-grade">
							<div>제목</div>
							<div>점수</div>
						</div>
						<hr class="line3">

					</div>
				</div>
			</div>
			<div id="noticeModal" class="modal">
				<div class="modal-content">
					<span class="close" onclick="closeModal1()">&times;</span>
					<div class="modal-title">승인요청</div>
					<p id="modalContent">
					<div class="num-name-check">
						<div>번호</div>
						<div>이름</div>
						<div>승인</div>
					</div>
					<hr class="line5">
					</p>
					<p id="modalDate"></p>
				</div>
			</div>
		</div>
</div>
		
	</div>
</body>

</html>