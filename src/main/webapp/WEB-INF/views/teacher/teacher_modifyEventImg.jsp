<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	    <title>AGIT</title>
	    
	    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	    <script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-lite.min.js"></script>
	    <script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/lang/summernote-ko-KR.min.js"></script>
	    <script src="<c:url value='/resources/js/teacher/teacherModifyEventImg.js'/>"></script>
	    
	    <link rel="stylesheet" href="<c:url value='/resources/css/teacher/teacher_modifyEvent.css'/>">
	    <link href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-lite.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="../common/teacher_menubar.jsp" />
        <style>
	    	*{
	    		overflow: visible;
	    	}
	    </style>
	    
    <div id="content_border">
    	<form action="modifyImgBoard" method="POST">
    		<div id="enroll_title" ><input type="text" id="en_title" placeholder="제목을 입력해주세요" value="${npage.title}" name="title"></div>
	        <div id="enroll_content" >
	        	<input type="hidden" name = "boNo" value = "${npage.boNo}">
	            <textarea id="en_content" name="boContent">${npage.boContent }</textarea>
	        </div>
	        <button type="submit" class="create_button" id="modify_button">수정하기</button>
	        <button type="button" class="create_button" id="delete_button" onclick = "location.href='deleteImgBoard?boNo=${npage.boNo}'">삭제하기</button>  
    	</form>
    </div>
</body>
</html>