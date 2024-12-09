<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList,com.twoX.agit.after.vo.AfterSchoolBoard,com.twoX.agit.common.vo.PageInfo"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
ArrayList<AfterSchoolBoard> list = (ArrayList)session.getAttribute("boardList");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AGIT</title>
    <link rel="stylesheet"
	href="<c:url value='/resources/css/student/student_afterschool.css'/>">
	<script src="<c:url value='/resources/js/student/student_myPage.js'/>"></script>
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <jsp:include page="../common/student_menubar.jsp" />

    <div class="wrap">
      <div class="header">
         <div class="header_left">
            <h4>${afterschool.className}</h4>
            <p>${afterschool.explanation}</p>
            <p>담당 선생님 | ${afteacherName} 선생님</p>
         </div>
         <img src="<c:url value='/resources/img/student/afterschool.png'/>" alt="">
      </div>
      
      <div class="list">
         <h4>내가 쓴 글(활동기록)</h4>
         <button class="writebutton" onclick="location.href='enroll_afterschoolPage'">글쓰기</button>
         <table>
            <thead>
               <tr>
                  <th>번호</th>
                  <th>제목</th>
                  <th>작성일</th>
               </tr>
            </thead>
            <tbody> 
            <c:forEach var="b" items="<%=list %>">
             	<tr onclick = "location.href = 'detail_after?boNo=${b.boNo}&cpage=${pi.currentPage}'">
             		<td>${b.boNo}</td>
             		<td class="title-limit">${b.title}</td>
             		<td>${b.createDate}</td>
             	</tr>
             </c:forEach>
            </tbody>
         </table>
        <div id="arrow_button">
            <button id="right_arrow"></button>            
            	<c:choose>
            		<c:when test="${ pi.currentPage eq 1 }">
            			<button onclick="location.href='afterSchool?cpage=${pi.currentPage - 1}'" disabled><img src="<c:url value='/resources/img/parents/left_arrow.png'/>"></button>
            		</c:when>
            		<c:otherwise>
            			<button onclick="location.href='afterSchool?cpage=${pi.currentPage - 1}'"><img src="<c:url value='/resources/img/parents/left_arrow.png'/>"></button>
            		</c:otherwise>
            	</c:choose>

				<c:forEach var="p" begin="${ pi.startPage }" end="${ pi.endPage }">
					<c:choose>
						<c:when test="${ pi.currentPage eq p }">
							<button style="background-color: #DDE5B6; border-radius: 40px;" onclick="location.href='afterSchool?cpage=${p}'">${p}</button>
						</c:when>
						<c:otherwise>
							<button onclick="location.href='afterSchool?cpage=${p}'">${p}</button>
						</c:otherwise>
					</c:choose>
					
				</c:forEach>
                
            	<c:choose>
            		<c:when test="${ pi.currentPage eq pi.maxPage }">
            			<button onclick="location.href='afterSchool?cpage=${pi.currentPage + 1}'" disabled><img src="<c:url value='/resources/img/parents/right_arrow.png'/>"></button>
            		</c:when>
            		<c:otherwise>
            			<button onclick="location.href='afterSchool?cpage=${pi.currentPage + 1}'"><img src="<c:url value='/resources/img/parents/right_arrow.png'/>"></button>
            		</c:otherwise>
            	</c:choose>
            
        </div>
      </div>
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