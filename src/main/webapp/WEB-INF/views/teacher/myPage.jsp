<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.twoX.agit.member.model.vo.Teacher"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	String alertMsg = (String) session.getAttribute("alertMsg");
	Teacher s = (Teacher) session.getAttribute("loginUser");
	
	
	String classCode = s.getClassCode();//클래스 코드가 빈값이면 0000이 아니라 null로 바꾸어야한다.
	String grade = classCode.substring(9, 10);
	String teacher_class = classCode.substring(10, 12);	
%>


<!DOCTYPE html>
<html lang="en">
<head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>선생님 마이페이지</title>
<link rel="stylesheet" href="<c:url value='/resources/css/teacher/teacherMypagePlus.css'/>">
<script src="<c:url value='/resources/js/teacher/myPage.js'/>"></script>

</head>
<body>

   <% if(alertMsg != null) {%>
      <script>
      alert("<%=alertMsg%>");
      </script>
   <% session.removeAttribute("alertMsg"); %>
   
   <%} %>
   <jsp:include page="../common/teacher_menubar.jsp" />
	<div class="whole_body">
		<h1><%=grade%>학년
			<%=teacher_class%>반(<%=classCode%>)
		</h1>

		<div class="textbox1">
		<c:forEach var="counsel" items="${teacherCounsel}">
			<div class="text1">
				<span class="date">${counsel.csDate}</span>
				&nbsp;&nbsp;&nbsp;&nbsp;학부모 이름 : ${counsel.prName}
				&nbsp;&nbsp;&nbsp;&nbsp;상담 장소 : ${counsel.csLocation}
				&nbsp;&nbsp;&nbsp;&nbsp;상담 내용 : ${counsel.csContent}
			</div>
			<hr class="body_line">
			</c:forEach>
		</div>

		<div class="textbox2">
			<h1>공지사항</h1>
			    <hr class="body_line1">
			    	<div class="bottom_container">
			        	<c:forEach var="notice" items="${teacherNotice}">
			            	<div class="body_content"  onclick="openDeleteNoticeModal('${notice.ntContent}', '${notice.NTno}')">
			            		<input type="hidden" id="noticeNo" name="noticeNo" value="${notice.NTno}">
			                	<div class="body_title">
			                    	${notice.ntContent} <!-- 공지사항 제목 표시 -->
			               		</div>
			                
			                	<div class="body_writer">
			                    	${notice.createDate} <!-- 공지사항 작성일 표시 -->
			                	</div>
			            	</div>
			            <hr class="body_line2">
			        	</c:forEach>
			    	</div>
			<!-- 이미지 클릭 시 공지사항 작성 모달 열기 -->
	        <img class="body_plus" onclick="openAddNoticeModal()" src="<c:url value='/resources/img/teacher/plus.png'/>">  
		</div> 



		<div class="memo_content">
		    <h1>메모장</h1>
		    
		    <!-- 메모 항목 반복 시작 -->
		    <c:forEach var="memo" items="${teacherMemo}">
		        <div class="memo_box">
		            <!-- 각 메모 항목 표시 -->
		            <div class="memo_text" onclick="openModifyMemoModal('${memo.mmContent}')">
		                ${memo.mmContent}
		                
		            </div>
		            
					<!-- 삭제 버튼 -->
		        <form method="POST" action="deleteMemo">
		            <input type="hidden" name="memoContent" value="${memo.mmContent}">
		            <button type="submit" class="memo-delete-btn">x</button>
		        </form>
		        </div>
		    </c:forEach>
		
		    <!-- 메모 추가 버튼 -->
		    <img class="memo_plus" onclick="openAddMemoModal()" src="<c:url value='/resources/img/teacher/plus2.png'/>">
		</div>




		<!-- 공지사항 추가 모달 -->
		<div id="addNoticeModal" class="modal">
		    <div class="modal-content">
		        <span class="close" onclick="closeAddNoticeModal()">&times;</span>
		        <h3>공지사항을 작성하세요.</h3>
		        <form id="addNoticeForm" action="addNoticeForm" method="POST">
		            <input type="text" id="noticeAddTitle" name="noticeTitle" class="announcementTextField" required>
		            <button type="submit" class="postAnnouncementButton">등록</button>
		        </form>
		    </div>
		</div>


        <!-- 공지사항 수정/삭제 모달 -->
        <div id="deleteNoticeModal" class="modal">
            <div class="modal-content">
                <span class="close" onclick="closeDeleteNoticeModal()">&times;</span>  
                 <h3>공지사항 수정&삭제</h3>      
				<!-- 공지사항 제목 입력 -->
				<input type="text" id="noticeTitle" name="noticeTitle" required>
				<!-- 공지사항 번호 (hidden 필드) -->
			    <input type="hidden" id="noticeNo" value="${notice.NTno}">
	         	<!-- 수정 버튼 -->
	         	<button type="button" class="confirm-button" onclick="confirmNoticeEdit()">수정</button>
	          	<!-- 삭제 버튼 -->
	          	<button type="button" class="delete-button" onclick="confirmNoticeDelete()">삭제</button>
		 	</div>
         </div>


		<!-- 메모 추가 모달 -->
		<div id="addMemoModal" class="modal">
		    <div class="modal-content">
		        <span class="close" onclick="closeAddMemoModal()">&times;</span>
		        <h3>메모 작성</h3>
		        <form id="addMemoForm" action="addMemo" method="POST">
		            <input type="text" id="memoText" name="memoContent" placeholder="메모 작성..." required>
		            <button type="submit">등록</button>
		        </form>
		    </div>
		</div>


        <!-- 메모 수정 모달 -->
        <div id="modifyMemoModal" class="modal">
            <div class="modal-content">
                <span class="close" onclick="closeModifyMemoModal()">&times;</span>
                <h3>메모 수정</h3>
                <form id="modifyMemoForm">
                    <input type="text" id="modifyMemoText" placeholder="메모 내용을 수정하세요..." required>
                    <button type="button" onclick="submitModifiedMemo()">저장</button>
                </form>
            </div>
        </div>

	</div>
</body>
</html>