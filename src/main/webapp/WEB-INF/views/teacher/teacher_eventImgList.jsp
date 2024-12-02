<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>행사사진</title>
    
	<link rel="stylesheet" href="<c:url value='/resources/css/parents/parents_myPage.css'/>">
    <style>

    </style>
</head>
<body>
    <jsp:include page="../common/teacher_menubar.jsp" />

    <div class="wrap">
         <div class="header">
            <h2>행사사진</h2>
        </div>
        <button id="create_button" onclick="location.href='teacher_eventImgList_enroll'">글 쓰기</button>
        <div class="content">
            <c:choose>
            	<c:when test="${empty ImgList }">
            		<img src="<c:url value='/resources/img/parents/free-sticker-science-12775329.png'/>" style="width:400px; height:400px; position: absolute; top:20%; left:48%;">
	      	 		<div style="margin-top: 430px;"><h2>게시물이 존재하지 않습니다.</h2></div>
            	</c:when>
            	<c:otherwise>
            		<c:forEach var="b" items="${ImgList}">
			        	<div class="thumbnail" onclick="location.href='teacher_eventImgList_detail?boNo=${b.boNo}'">
			                <img src="${b.thumbnail }">
			                <p>${b.title}</p>
			            </div>
		        	</c:forEach>
            	</c:otherwise>
            </c:choose>
        </div>
      </div>
</body>
</html>