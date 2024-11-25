<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AGIT</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/teacher/teacherMypage.css'/>">

</head>
<body>
     <jsp:include page="../common/teacher_menubar.jsp" />
    
    <div class="whole_body">
        <img src="<c:url value='/resources/img/teacher/free-sticker-keyboard-12140857.png'/>">
        <h1>반 개설이 필요합니다.</h1>

        <div class="form">
            <div class="input-row">
                <form method="post" action="teacher.classCode">
                <input type="hidden" name="tcId" value="${loginUser.tcId}" />
                    <input type="text" id="grade" name="grade" class="input-field" placeholder="학년입력">
                    <span class="label">&nbsp학년&nbsp&nbsp&nbsp</span>
                    <input type="text" id="class" name="class" class="input-field" placeholder="반입력">
                    <span class="label">&nbsp반</span>
                </div>

                <div class="button-row">
                    <!-- 학교코드를 숨기기 위한 hidden 필드 -->
                    <input type="hidden" id="school-code" value="${loginUser.scCode}" />
                    <input type="text" id="classCode" name="classCode" class="input-field" placeholder="반코드입력" value="${loginUser.scCode}" readonly>
                    <button class="add-button"  type="submit">+</button>
                </div>
            </form>
        </div>
    </div>
    
<script src="<c:url value='/resources/js/teacher/startClass.js'/>"></script>
</body>
</html>