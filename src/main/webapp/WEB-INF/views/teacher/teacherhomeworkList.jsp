<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html lang="ko">
<head>
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>학생당 숙제 페이지</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/teacher/teacherhomeworkList.css'/>">
</head>
<body>
  <jsp:include page="../common/teacher_menubar.jsp" />

    <div class="whole_body">
        <div class="box">
            <h1>숙제 목록</h1>
            <br><br>
            <table class="attendance-table">
                <thead>
                    <tr>
                        <th>번호</th>
                        <th>이름</th>
                        <th>제목</th>
                        <th>제출일</th>
                        <th>점수</th>
                    </tr>
                </thead>
<tbody>
    <!-- subjectHomeworkList를 반복하여 출력 -->
    <c:forEach var="homework" items="${subjectHomeworkList}">
        <tr onclick="redirectToDetail('${homework.hmTitle}', '${homework.stuId}')">
            <td>${homework.boNo}</td> <!-- 과제 번호 -->
            <td>${homework.stuId}</td> <!-- 학생 이름 -->
            <td>${homework.hmTitle}</td> <!-- 숙제 제목 -->
            <td>${homework.createDate}</td> <!-- 제출일 -->
            <td class="score">${homework.score}</td> <!-- 점수 -->
        </tr>
    </c:forEach>
</tbody>

            </table>
        </div>
    </div>
    
    <script src="<c:url value='/resources/js/teacher/homeworkdetail.js'/>"></script>
</body>
</html>
