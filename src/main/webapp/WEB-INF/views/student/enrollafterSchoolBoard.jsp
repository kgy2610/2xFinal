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

    <title>AGIT</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/student/student_enrollafterschool.css'/>">
    
    <script src="<c:url value='/resources/js/student/enrollafterSchoolBoard.js'/>"></script>
</head>
<body>
    <jsp:include page="../common/student_menubar.jsp" />
    <style>
    	*{
    		overflow:visible;
    	}
    </style>
    <div id="content_border">
    	<form action="enroll_afterschool" method="POST" enctype="multipart/form-data">
        <div id="enroll_title" ><input type="text" id="en_title" name="title" placeholder="제목을 입력해주세요" required></div>
        <div id="enroll_content">
            <textarea id="en_content" name="boContent" id="sub_content"></textarea>
            <div id="enroll_file">
                <input type="file" id="fileInput" name="upfile" style="display: none;" onchange="showFileName()">
                <label for="fileInput" class="custom-file-upload">
                    파일 선택
                </label>
                <span id="fileName" class="file-name">선택된 파일이 없습니다</span>
            </div>
        </div>
        <button type = "submit" id="create_button" onclick="enroll_afterschool()">등록하기</button>
        </form>
    </div>
</body>
</html>