<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <title>행사사진 게시글</title>
    
    <link rel="stylesheet" href="<c:url value='/resources/css/parents/parents_eventImgDetail.css'/>">
</head>
<body>
	<jsp:include page="../common/teacher_menubar.jsp" />
    <div class="Imgwrap">
         <div class="header">
            <h4>${eib.title}</h4>
        </div>
        <div class="mid-header">
         <p>${eib.tcId} 선생님 | ${eib.createDate}</p>
        </div>
        <div class="content">${eib.boContent}</div>
        <button class="create_button" style="left: 670px; bottom: 130px;" onclick="location.href='teacher_eventImgList'">목록으로</button>
        <button class="create_button" style="right: 350px; bottom: 130px;" onclick="location.href='teacher_eventImgList_modify?boNo=${eib.boNo}'">수정하기</button>
    </div>
</body>
</html>