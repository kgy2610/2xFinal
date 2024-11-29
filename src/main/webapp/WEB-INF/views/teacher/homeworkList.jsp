
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>교직원 숙제 페이지</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/teacher/teacher_homework.css'/>">
</head>
<body>
   <jsp:include page="../common/teacher_menubar.jsp" />

    <div class="whole_body">
        <div class="box">
            <h1>숙제 목록 </h1>
            <select id="subjectSelect" name="subject" onchange="filterHomework()">
            	<option value="전체">전체</option>
                <option value="국어">국어</option>
                <option value="수학">수학</option>
                <option value="영어">영어</option>
                <option value="과학">과학</option>
                <option value="사회">사회</option>
            </select>
            <a href="gosubject">
    			<button class="navigate-button">우리반 숙제 이동</button>
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
			                <td><a href="detailHomework?subject=${homework.subject}&hmTitle=${homework.hmTitle}&deadLine=${homework.deadLine}&hmContent=${homework.hmContent}">${homework.hmTitle}</a></td>
			                <td>${homework.deadLine}</td>
			                <td><a href="detailHomework?subject=${homework.subject}&hmTitle=${homework.hmTitle}&deadLine=${homework.deadLine}&hmContent=${homework.hmContent}"><img src="<c:url value='/resources/img/teacher/pencil.PNG'/>"></a></td>
			            </tr>
        		   </c:forEach>
                </tbody>
            </table>

            <img src="<c:url value='/resources/img/teacher/homework.PNG'/>" class="homeworkplus" onclick="addHomeworkPage()">
            
        </div>
    </div>

    <script src="<c:url value='/resources/js/teacher/homework.js'/>"></script>
</body>
</html>
