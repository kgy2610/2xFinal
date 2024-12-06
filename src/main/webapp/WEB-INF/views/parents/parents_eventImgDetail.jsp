<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>agit</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/parents/parents_eventImgDetail.css'/>">
</head>
<body>
	<jsp:include page="parents_menubar.jsp" />
    <div class="Imgwrap">
         <div class="header">
            <h4>${eib.title}</h4>
        </div>
        <div class="mid-header">
         <p>${eib.tcId} 선생님 | ${eib.createDate}</p>
        </div>
        <div class="content">${eib.boContent}</div>
        <button class="create_button" id="back_toList" onclick="location.href='parents_eventImgList'">목록으로</button>
    </div>
</body>
</html>