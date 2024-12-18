<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList, java.util.Arrays,com.twoX.agit.board.model.vo.HmSubmit,com.twoX.agit.member.model.vo.Homework,com.twoX.agit.common.vo.PageInfo, java.util.Arrays"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	ArrayList<Homework> homeworkList = (ArrayList)session.getAttribute("homeworkList");
	ArrayList<Double> stuScoreList = (ArrayList<Double>) session.getAttribute("stuScoreList");
	ArrayList<String> slist = new ArrayList(Arrays.asList("국어", "수학", "영어", "과학", "사회", "미술", "체육"));
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>숙제</title>
<link rel="icon" href="<c:url value='/resources/img/student/homework_monkey.png'/>"/>
<link rel="apple-touch-icon" href="<c:url value='/resources/img/student/homework_monkey.png'/>"/>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="<c:url value='/resources/css/menubar.css'/>">
<link rel="stylesheet" href="<c:url value='/resources/css/student/student_homework.css'/>">
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
	<jsp:include page="../common/student_menubar.jsp" />
	<c:forEach var="ss" items="${stuScoreList}">
		<input type="hidden" value="${ss}" class="stuScore">
	</c:forEach>
	<input type="hidden" value="${loginUser.stuId}" id="stuId">
	<div class="wrap">
	<div id="content_border">
		<div id="community_title">
			<h2>숙제 목록</h2>
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
			    <c:forEach var="homework" items="${homeworkList}">
			        <tr class="homework-list-detail"
			        onclick="moveHomeworkDetail('${homework.boNo}', '${homework.status}', '${pi.currentPage}')">
			            <td>${homework.boNo}</td>
			            <td>${homework.subject}</td>
			            <td>${homework.hmTitle}</td>
			            <td>${homework.deadLine}</td>
						<td>
				            <c:choose>
				                <c:when test="${homework.status == null}">미제출</c:when>
				                <c:otherwise>제출</c:otherwise>
				            </c:choose>
				        </td>
				        <td class="stu_score">
				            <c:choose>
				                <c:when test="${homework.score == 0}">-</c:when>
				                <c:otherwise>${homework.score}</c:otherwise>
				            </c:choose>
				        </td>
			    </c:forEach>
			</tbody>
		</table>
		
		<br>
		<br>
		<div id="arrow_button">
            <button id="right_arrow"></button>            
            	<c:choose>
            		<c:when test="${ pi.currentPage eq 1 }">
            			<button onclick="location.href='homework?cpage=${pi.currentPage - 1}'" disabled><img src="<c:url value='/resources/img/parents/left_arrow.png'/>"></button>
            		</c:when>
            		<c:otherwise>
            			<button onclick="location.href='homework?cpage=${pi.currentPage - 1}'"><img src="<c:url value='/resources/img/parents/left_arrow.png'/>"></button>
            		</c:otherwise>
            	</c:choose>

				<c:forEach var="p" begin="${ pi.startPage }" end="${ pi.endPage }">
					<c:choose>
						<c:when test="${ pi.currentPage eq p }">
							<button style="background-color: #DDE5B6; border-radius: 40px;" onclick="location.href='homework?cpage=${p}'">${p}</button>
						</c:when>
						<c:otherwise>
							<button onclick="location.href='homework?cpage=${p}'">${p}</button>
						</c:otherwise>
					</c:choose>
					
				</c:forEach>
                
            	<c:choose>
            		<c:when test="${ pi.currentPage eq pi.maxPage }">
            			<button onclick="location.href='homework?cpage=${pi.currentPage + 1}'" disabled><img src="<c:url value='/resources/img/parents/right_arrow.png'/>"></button>
            		</c:when>
            		<c:otherwise>
            			<button onclick="location.href='homework?cpage=${pi.currentPage + 1}'"><img src="<c:url value='/resources/img/parents/right_arrow.png'/>"></button>
            		</c:otherwise>
            	</c:choose>
            
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
</div>
	<script src="<c:url value='/resources/js/student/student_homework.js'/>"></script>
</body>
</html>
