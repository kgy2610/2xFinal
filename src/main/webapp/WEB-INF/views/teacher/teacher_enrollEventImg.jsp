<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    
    <!-- Summernote CSS/JS CDN -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-lite.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/lang/summernote-ko-KR.min.js"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-lite.min.css" rel="stylesheet">

    <title>행사사진 작성하기</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/teacher/teacherEnrollEvent.css'/>">
    
	<script src="<c:url value='/resources/js/teacher/teacherEnrollEventImg.js'/>"></script>
</head>
<body>
    <jsp:include page="../common/teacher_menubar.jsp" />
	<style>
		*{
			overflow:visible;
		}
	</style>
    <div id="content_border" >
    	<form action="enroll_IMG" method="POST" id="enroll_bo">
        <div id="enroll_title" ><input type="text" id="en_title" name="title" placeholder="제목을 입력해주세요" required></div>
        <div id="enroll_content">
            <textarea id="en_content" name="boContent"></textarea>
        </div>
        <button type = "submit" id="create_button">등록하기</button>
        </form>
    </div>
</body>
</html>