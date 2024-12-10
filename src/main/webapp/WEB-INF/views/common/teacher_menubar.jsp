<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.twoX.agit.member.model.vo.Teacher"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String alertMsg = (String) session.getAttribute("alertMsg");
Teacher s = (Teacher) session.getAttribute("loginUser");

String classCode = null;  // 기본값을 null로 설정
String grade = null;
String teacher_class = null;

if (s != null) {
    classCode = s.getClassCode();

    // classCode가 null이거나 길이가 12보다 작으면 null로 처리
    if (classCode == null || classCode.length() < 12) {
        classCode = null; // classCode가 null 또는 길이가 부족하면 null로 설정
    } else {
        grade = classCode.substring(9, 10);  // 학년 추출
        teacher_class = classCode.substring(10, 12);  // 반 추출
    }
}
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AGIT</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/menubar.css'/>">
    <script src="<c:url value='/resources/js/teacher/teacher_menubar.js'/>"></script>
</head>
<body>
	   <% if(alertMsg != null) {%>
      <script>
      alert("<%=alertMsg%>");
      </script>
   <% session.removeAttribute("alertMsg"); %>
   
   <%} %>
    <div class="nav">
        <img src="<c:url value='/resources/img/logo.png'/>">
        <div class="menu">
            <label for="mypage"><a href="teacher.myPage" ${bbsId == 'teacherMypage' ? 'id="selected"' : ''}>마이페이지</a></label>
            <label for="homework"><a href="homeworkList" ${bbsId == 'teacherHomework' ? 'id="selected"' : ''}>숙제</a></label>
            <label for="attendance"><a href="teacherAttendance" ${bbsId == 'teacherAttendance' ? 'id="selected"' : ''}>출결</a></label>
            <label for="advicePlan"><a href="teacherCounsel" ${bbsId == 'teacherCalendar' ? 'id="selected"' : ''}>상담일정</a></label>
            <label for="community"><a href="makeAfterClass.me" ${bbsId == 'teacherAfterSchool' ? 'id="selected"' : ''}>방과후 반</a></label>
            <label for="photo"><a href="teacher_eventImgList" ${bbsId == 'teacherEvent' ? 'id="selected"' : ''}>행사사진</a></label>
            <label for="studentManage"><a href="studentManage.me" ${bbsId == 'studentManager' ? 'id="selected"' : ''}>학생관리</a></label>
        </div>
        <div class="undermenu">
            <label for="info" onclick="openInfoModal2()"><a href="#">정보수정</a></label>
            <label for="logout"><a href="logout.me">로그아웃</a></label>
        </div>
    </div>
   	<div id="chat_button" onclick="openStuModal('${loginUser.classCode}')">
		<img src="<c:url value='/resources/img/teacher/message.png'/>">
	</div>
	<div style="background-color:red; width:20px; height:20px; border-radius:40px; border:none; position: absolute; bottom:115px; right:50px; display:none;" id="newMsg"></div>
	
	<div id="stuModal" class="chat_modal">
		<div class="chat_modal-content">
         <h2 class="modalTitle" id="classGrade"></h2>
         <input type="hidden" value="${teacherName.tcId }" id="teacherId">
         <span class="closeChatModal" onclick="closeChatModal()">&times;</span>
           <hr>
           <div id="modal_Stu_content">
           		<c:forEach var="sl" items="${slist}">
           			<table class="modal_stuList" style="background-color:#DDE5B6; width:430px; height:60px; border-radius:15px; text-align:center; margin-bottom:10px;" onclick="openChatModal('${sl.stuId}','${loginUser.classCode}')">
	           			<tr>
	           				<td class="modal_stuName" style="width:20%">${sl.stuName}</td>
	           				<td class="modal_lastChat" style="width:60%"></td>
	           				<td class="modal_newMsgCount" style="width:20%;"><div style="width:80%; height:40px; background-color:#efccb0;border-radius:15px; display: none; justify-content: center; align-items: center;" id="newMsgEv${sl.stuId}">n e w</div></td>
	           			</tr>
	           		</table>	
           		</c:forEach>
           		
           </div>
         </div>
        </div>
        <c:forEach var="st" items="${slist}">
        	<div id="chatModal${st.stuId}" class="chat_modal">
      			<div class="chat_modal-content">
         			<h2 class="modalTitle">${st.stuName}</h2>
         			<span class="closeChatModal" onclick="backModal('${st.stuId}')">&lt;</span>
          			<hr>
          			<input type="hidden" value="${st.stuId}" class="ChatStuId">
           			<div class="modal_talk_content" id="${st.stuId}talk_content">
           		
           			</div>
               		<table>
	                  	<tr>
	                    	<td><input type="text" placeholder="메세지를 입력하세요" id="msg${st.stuId}"></td>
	                    	<td><button type="button" onclick="sendMsg('${st.stuId}')">전 송</button></td>
	                  	</tr>
               		</table>
        		</div>
        	</div>
        </c:forEach>
        
		<!-- 모달 -->

		<div id="noticeModal1" class="modal">
			<div class="modal-content">
				<span class="close" onclick="closeInfoModal2()">&times;</span>
				<h3>정보수정</h3>
				<form id="updateForm" action="updateInfo.me">
					<label for="code">코드</label> <input type="text" id="code"
						name="classCode" value="<%=classCode%>" required readonly><br>
					<br>

					<div class="input-group">
						<label for="grade">학년</label> <input type="text" id="grade"
							name="grade" value="<%=grade%>" required readonly><br>
						<br> <label for="class">반</label> <input type="text"
							id="class" name="teacher_class" value="<%=teacher_class%>"
							required readonly><br>
						<br>
					</div>
					<div class="button-group">
						<div class="top-buttons">
							
							<button class="password-button" type="button"
								onclick="openPasswordModal2()">비밀번호 수정</button>
						</div>
						<button class="delete-button" type="button"
							onclick="openDeleteModal()">반 삭제</button>
					</div>
				</form>
			</div>
		</div>


		<!-- 반 삭제 확인 모달 -->
		<div id="deleteClassModal" class="modal">
			<div class="modal-content">
				<span class="close" onclick="closeDeleteModal()">&times;</span>
				<h3>반 삭제</h3>
				<form id="classdeleteForm" action="classdelete.me">
					<input type="hidden" value="<%=s.getClassCode()%>">
					<label for="deleteCode" class="deleteOn">정말로 반을 삭제하시겠습니까?</label>
					<div class="input-group">
						<p id="deleteClassMessage">
							코드: <input type="text" id="deleteCode" name="deleteCode" required>
						</p>
					</div>
					<div class="button-group">
						<button class="confirm-delete-button" type="submit">삭제하기</button>
					</div>
				</form>
			</div>
		</div>



		<!-- 비밀번호 수정 모달 -->
		<div id="passwordModal" class="modal">
			<div class="modal-content">
				<span class="close" onclick="closePasswordModal()">&times;</span>
				<h3>비밀번호 수정</h3>

				<!-- 비밀번호 수정 폼 -->
				<form id="passwordUpdateForm" action="updatePassword.me" method="POST">
				<input type="hidden" value="<%=s.getTcPwd() %>">
				<%System.out.println("아니 뭐야" + s.getTcPwd());%>
					<label for="currentPassword">현재 비밀번호</label> <input type="password"
						id="currentPassword" name="currentPassword" required> <label
						for="newPassword">수정 비밀번호</label> <input type="password"
						id="newPassword" name="newPassword" required> <label
						for="confirmPassword">비밀번호 확인</label> <input type="password"
						id="confirmPassword" name="confirmPassword" required>

					<div class="button-group">
						<button class="confirm-password-button" type="submit">비밀번호 수정하기</button>
					</div>
				</form>
			</div>
		</div>

  
</body>
</html>