<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>숙제</title>
<link rel="stylesheet" href="<c:url value='/resources/css/menubar.css'/>">
<link rel="stylesheet" href="<c:url value='/resources/css/student/student_homework.css'/>">
<script src="<c:url value='/resources/js/student/student_homework.js'/>"></script>
</head>
<body>
	<jsp:include page="../common/student_menubar.jsp" />
	
	</div>
	<div id="content_border">
		<div id="community_title">
			<h1>숙제 목록</h1>
		</div>
		<table id="community_table">
			<thead>
				<tr>
					<th>번호</th>
					<th>과목</th>
					<th>제목</th>
					<th>마감일</th>
					<th>제출</th>
					<th>점수</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="homework" items="${homeworkList}" varStatus="status">
					<tr class="homework-list-detail">
						<td class="BNo">${homework.boNo}</td>
						<td class="sjt">${homework.subject}</td>
						<td class="Title">${homework.hmTitle}</td>
						<td class="deadLine">${homework.deadLine}</td>
						<td class="status"><c:choose>
								<c:when test="${homework.status == NULL}">미제출</c:when>
								<c:otherwise>제출</c:otherwise>
							</c:choose></td>
						<td class="score">${homework.score}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<br>
		<br>
		<div id="arrow_button">
			<button id="left_arrow"></button>
			<button style="background-color: #DDE5B6; border-radius: 40px;">1</button>
			<button>2</button>
			<button>3</button>
			<button>4</button>
			<button>5</button>
			<button id="right_arrow"></button>
		</div>
	</div>
	<div id="graph_content">
		<div class="graphBox">
			<canvas id="barCanvas" width="650px" height="270px"></canvas>
		</div>
	</div>
	<div id="monkey_img">
		<img src="<c:url value='/resources/img/student/homework_monkey.png'/>">
	</div>
	<div id="chat_button">
		<img src="<c:url value='/resources/img/student/message.png'/>">
	</div>
</body>
</html>