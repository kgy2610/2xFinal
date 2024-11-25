<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList,com.twoX.agit.board.model.vo.ParentsBoard,com.twoX.agit.common.vo.PageInfo"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
ArrayList<ParentsBoard> list = (ArrayList)session.getAttribute("boardList");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>parents_Allcommunity</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/parents/parents_Allcommunity.css'/>">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" crossorigin="anonymous">
</head>
<body>
    <jsp:include page="parents_menubar.jsp" />
    <div id="content_border">
    <%if(list.isEmpty()){ %>
   	     <img src="<c:url value='/resources/img/parents/free-sticker-science-12775329.png'/>" style="width:400px; height:400px; position: absolute; top:20%; left:30%;">
      	 <div><h2>게시물이 존재하지 않습니다.</h2></div>
      	 <button id="create_button" onclick="location.href='enroll_community'">글 작성하기</button>
   	<%}else{ %>
        <div id="community_title"><h1>커뮤니티</h1></div>
        <button id="create_button" onclick="location.href='enroll_community'">글 작성하기</button>
        <div id="table_area">
        	<table id="community_table">
	            <thead>
	                <tr>
	                    <th>제목</th>
	                    <th>작성일</th>
	                    <th>작성자</th>
	                    <th>조회수</th>
	                </tr>
	            </thead>
	            <tbody>
					<c:forEach var="b" items="<%=list %>">
		            	<tr onclick="location.href='board_detail?boNo=${b.boNo}&cpage=${pi.currentPage }'">
		           			<td class="title-limit">${b.boTitle } 
		           			<c:choose>
		           				<c:when test="${not empty b.originName}">
		           					<i class="fas fa-paperclip clip-icon">
		           				</c:when>
		           			</c:choose>
							</td>
		           			<td>${b.createDate }</td>
		           			<td><p class="text-limit">${b.prNickname }</p></td>
		           			<td>${b.boCount }</td>
		           		</tr>
	           		</c:forEach>
	    
	            </tbody>
        	</table>
        </div>
        <br><br>
        <div id="arrow_button">
            <button id="right_arrow"></button>            
            	<c:choose>
            		<c:when test="${ pi.currentPage eq 1 }">
            			<button onclick="location.href='all_community?cpage=${pi.currentPage - 1}'" disabled><img src="<c:url value='/resources/img/parents/left_arrow.png'/>"></button>
            		</c:when>
            		<c:otherwise>
            			<button onclick="location.href='all_community?cpage=${pi.currentPage - 1}'"><img src="<c:url value='/resources/img/parents/left_arrow.png'/>"></button>
            		</c:otherwise>
            	</c:choose>

				<c:forEach var="p" begin="${ pi.startPage }" end="${ pi.endPage }">
					<c:choose>
						<c:when test="${ pi.currentPage eq p }">
							<button style="background-color: #DDE5B6; border-radius: 40px;" onclick="location.href='all_community?cpage=${p}'">${p}</button>
						</c:when>
						<c:otherwise>
							<button onclick="location.href='all_community?cpage=${p}'">${p}</button>
						</c:otherwise>
					</c:choose>
					
				</c:forEach>
                
            	<c:choose>
            		<c:when test="${ pi.currentPage eq pi.maxPage }">
            			<button onclick="location.href='all_community?cpage=${pi.currentPage + 1}'" disabled><img src="<c:url value='/resources/img/parents/right_arrow.png'/>"></button>
            		</c:when>
            		<c:otherwise>
            			<button onclick="location.href='all_community?cpage=${pi.currentPage + 1}'"><img src="<c:url value='/resources/img/parents/right_arrow.png'/>"></button>
            		</c:otherwise>
            	</c:choose>
            
        </div>
      <%} %>
    </div>
    <script>
        document.querySelectorAll(".text-limit").forEach(function (element) {
            const text = element.innerText;
            if (text.length > 4) {
                element.innerText = text.substring(0, 3) + "...";
            }
        });
        document.querySelectorAll(".title-limit").forEach(function (element) {
            const text = element.innerText;
            if (text.length > 15) {
                element.innerText = text.substring(0, 12) + "...";
            }
        });
    </script>
</body>
</html>