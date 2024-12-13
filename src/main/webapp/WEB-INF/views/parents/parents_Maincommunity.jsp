<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>커뮤니티 인기글</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/parents/parents_Maincommunity.css'/>">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" crossorigin="anonymous">
</head>
<body>
    <jsp:include page="parents_menubar.jsp" />
    <div class="wrap">
    	<button id="create_button" onclick="location.href='enroll_community'">글 작성하기</button>  
        <div id="community_title"><h1>커뮤니티 인기글</h1></div>
        <div id="best_contents">
        	<c:forEach var="b" items="${HotBoard}">
        		<div class="best_content" onclick="location.href='board_detail?boNo=${b.boNo}'">
		            <table>
		                <tr>
		                    <th colspan="2" class="best_title-limit">${b.boTitle}</th>
		                    <td></td>
		                </tr>
		                <tr>
		                    <td colspan="2"><div class="best_content_pic">
		                    					<c:choose>
		                    						<c:when test="${empty b.thumbnail }">
		                    							<img src="<c:url value='/resources/img/parents/free-sticker-science-12775329.png'/>" style="width:104px; height:90px; margin-top:10px">
		                    							<h4>사진이없습니다.</h4>
		                    							</c:when>
		                    						<c:otherwise>
		                    							<img src="${b.thumbnail }">
		                    						</c:otherwise>
		                    					</c:choose>
		                    				</div></td>
		                    <td></td>
		                </tr>
		                <tr>
		                    <td class="icon"><div><img src="<c:url value='/resources/img/parents/eye.png'/>" alt="조회수" ><span>${b.boCount }</span></div></td>
		                    <td class="icon"><div><img src="<c:url value='/resources/img/parents/comment.png'/>" alt="댓글수"><span>${b.reCount }</span></div></td>
		                </tr>
		            </table>
		        </div>
	        </c:forEach>
        </div>
        <div id="community_table_border">
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
	                <c:forEach var="b" items="${HotBoard}">
		            	<tr onclick="location.href='board_detail?boNo=${b.boNo}'">
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
        <div id="showAll"><a href="all_community">게시글 전체보기</a></div>
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
            if (text.length > 18) {
                element.innerText = text.substring(0, 15) + "...";
            }
        });
        document.querySelectorAll(".best_title-limit").forEach(function (element) {
            const text = element.innerText;
            if (text.length > 12) {
                element.innerText = text.substring(0, 11) + "...";
            }
        });
    </script>
</body>
</html>