<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>교직원 숙제 페이지</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/teacher/teacherhomeworkDetail.css'/>">
</head>
<body>
  <jsp:include page="../common/teacher_menubar.jsp" />

    <div class="whole_body">
        <div class="homework-container">
            <div class="homework-title">
                ${hmTitle}
            </div>
            <div class="homework-content">
             	${hmContent}
                <div class="homework-photo">첨부 사진</div>
            </div>
            <div class="answer-section">
                <div class="answer-list">
                    <ul>
                        <li>${hmStuContent}</li>
                    </ul>
                </div>
                <h1></h1>
            </div>
            <form action="updateSubmitHomework" method="POST" class="teacher-comments">
                <textarea name="teacherComment" placeholder="선생님 말씀 작성" required>${tcComment}</textarea>
                <div class="score-save">
                	<input type="hidden" name="stuId" value="${stuId}">
                    <input type="number" name="score" value="${score}"  placeholder="점수 입력" min="0" max="100" required>
                    <button type="submit" class="save-button">저장</button>
                </div>
            </form>
        </div>
    </div>
    </body>
</html>