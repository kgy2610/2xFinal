
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AGIT</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/teacher/teacher_homework.css'/>">
    
    <script src="<c:url value='/resources/js/teacher/homework.js'/>"></script>
</head>
<body>
   <jsp:include page="../common/teacher_menubar.jsp" />

    <div class="whole_body">
        <div class="box">
            <h1>숙제 목록 </h1>
            <select id="subjectSelect" name="subject" onchange="filterHomework()">
            	<option value="전체">전 체</option>
                <option value="국어">국 어</option>
                <option value="수학">수 학</option>
                <option value="영어">영 어</option>
                <option value="과학">과 학</option>
                <option value="사회">사 회</option>
            </select>
            <a href="gosubject">
    			<button class="navigate-button">채점하러 가기</button>
			</a>

            <br><br>
            <table class="attendance-table">
                <thead>
                    <tr>
                        <th>과목</th>
                        <th>제목</th>
                        <th>마감일</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                   <c:forEach var="homework" items="${homeworkList}">
			            <tr class="homework-item" data-subject="${homework.subject}">
			                <td>${homework.subject}</td>
			                <td>${homework.hmTitle}</td>
			                <td>${homework.deadLine}</td>
			                <td><a href="detailHomework?boNo=${homework.boNo}"><img src="<c:url value='/resources/img/teacher/pencil.PNG'/>"></a></td>
			            </tr>
        		   </c:forEach>
                </tbody>
            </table>
            <img src="<c:url value='/resources/img/teacher/homework.PNG'/>" class="homeworkplus" onclick="addHomeworkPage()">
        </div>
    </div>
</body>
</html>
