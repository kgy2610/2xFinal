<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>agit</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/parents/parents_eventImgList.css'/>">

    
</head>
<body>
    <jsp:include page="parents_menubar.jsp" />

    <div class="wrap">
         <div class="header">
            <h2>행사사진</h2>
        </div>
        <div class="content">
            <c:choose>
            	<c:when test="${empty ImgList }">
            		<img src="<c:url value='/resources/img/parents/free-sticker-science-12775329.png'/>" style="width:400px; height:400px; position: absolute; top:20%; left:48%;">
	      	 		<div style="margin-top: 430px;"><h2>게시물이 존재하지 않습니다.</h2></div>
            	</c:when>
            	<c:otherwise>
            		<c:forEach var="b" items="${ImgList}">
			        	<div class="thumbnail" onclick="location.href='eventImgList_detail?boNo=${b.boNo}'">
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