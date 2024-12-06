<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%String alertMsg = (String)session.getAttribute("alertMsg"); %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>parents_menubar</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/menubar.css'/>">
    <link rel="stylesheet" href="<c:url value='/resources/css/parents/parents_myPage.css'/>">
    <script src="<c:url value='/resources/js/parents/modify_parents_info_api.js'/>"></script>
    <script src="<c:url value='/resources/js/parents/modify_parents_pwd_api.js'/>"></script>
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
            <label for="mypage"><a href="parents_mypage" ${bbsId == 'parentsMypage' ? 'id="selected"' : ''}>마이페이지</a></label>
            <label for="score"><a href="parents_score" ${bbsId == 'Score' ? 'id="selected"' : ''}>성적</a></label>
            <label for="advice"><a href="parents_calendar" ${bbsId == 'parentsCalendar' ? 'id="selected"' : ''}>상담신청</a></label>
            <label for="community"><a href="parents_community" ${bbsId == 'parentsCommunity' ? 'id="selected"' : ''}>커뮤니티</a></label>
            <label for="photo"><a href="parents_eventImgList" ${bbsId == 'parentsEvent' ? 'id="selected"' : ''}>행사사진</a></label>
        </div>
        <div class="undermenu">
            <label for="info" onclick="openUpdateModal()"><a href="#">정보수정</a></label>
            <label for="logout"><a href="logout.parents">로그아웃</a></label>
        </div>
    </div>
    <!-- 공지사항 모달 -->
   <div id="noticeModal" class="modal">
      <div class="modal-content">
         <span class="close" onclick="closeModal()">&times;</span>
         <p id="modalContent"></p>
         <p id="modalDate"></p>
      </div>
   </div>

   <!-- 정보수정 모달 -->
   <div id="updateModal" class="modal2">
      <div class="modal-content2">
         <h2 id="modalTitle">정보 수정</h2>
         <span class="close" onclick="closeUpdateModal()">&times;</span>
         <div id="modalForm">
            <!-- 기본 정보 수정 폼 -->
            <form id="infoForm" action="modify_parents_info" method="POST" >
               <table>
                  <tr>
                     <td>닉네임</td>
                     <td><input type="text" id="nickname" placeholder="닉네임" value="${loginUser.nickname}"></td>
                  </tr>
                  <tr>
                     <td>전화번호</td>
                     <td><input type="text" id="phone" placeholder="전화번호" value="${loginUser.phone}"></td>
                  </tr>
               </table>
               <button type="button" onclick="modifyParentsInfoAjax('${loginUser.prId}')">수정하기</button>
               <button type="button" onclick="switchToPwdForm()">비밀번호 수정</button>
            </form>
            <!-- 비밀번호 수정 폼 (기본적으로 숨김) -->
            <form id="pwdForm" style="display: none;">
               <table>
                  <tr>
                     <td>기존 비밀번호</td>
                     <td><input type="password" placeholder="기존 비밀번호를 입력하세요" id="old_pwd" required></td>
                  </tr>
                  <tr>
                     <td>수정 비밀번호</td>
                     <td><input type="password" placeholder="수정할 비밀번호를 입력하세요" id="new_pwd" required></td>
                  </tr>
                  <tr>
                     <td>비밀번호 확인</td>
                     <td><input type="password" placeholder="비밀번호를 다시 입력하세요" id="new_pwd_co" required></td>
                  </tr>
               </table>
               <button type="button" onclick="modifyParentsPwdAjax('${loginUser.prId}','${loginUser.prPwd}','${loginUser.prQuestion}')">비밀번호 수정하기</button>
               <button type="button" onclick="switchToInfoForm()">뒤로가기</button>
            </form>
         </div>
      </div>
   </div>
</body>
   <script>
      //공지사항 댓글 수 제한(20글자)
      document.querySelectorAll(".text-limit").forEach(function (element) {
         const text = element.innerText;
         if (text.length > 20) {
            element.innerText = text.substring(0, 20) + "  ...";
         }
      });

      // 공지사항 모달
      function openModal(element) {
         var fullText = element.getAttribute('data-full-text');
         var date = element.getAttribute('data-date');

         document.getElementById('modalContent').textContent = fullText;
         document.getElementById('modalDate').textContent = date;

         document.getElementById('noticeModal').style.display = 'block';
      }

      function closeModal() {
         document.getElementById('noticeModal').style.display = 'none';
      }


      // 모달 열기 및 닫기 함수
      function openUpdateModal() {
         document.getElementById('updateModal').style.display = 'flex';
      }

      function closeUpdateModal() {
         document.getElementById('updateModal').style.display = 'none';
      }

      // 정보 수정 폼에서 비밀번호 수정 폼으로 전환
      function switchToPwdForm() {
         document.getElementById('infoForm').style.display = 'none';
         document.getElementById('pwdForm').style.display = 'block';
         document.getElementById('modalTitle').textContent = "비밀번호 수정";
      }

      // 비밀번호 수정 폼에서 정보 수정 폼으로 전환
      function switchToInfoForm() {
         document.getElementById('pwdForm').style.display = 'none';
         document.getElementById('infoForm').style.display = 'block';
         document.getElementById('modalTitle').textContent = "정보 수정";
      }

      // 외부 클릭 시 모달 닫기
      window.onclick = function (event) {
         const noticeModal = document.getElementById('noticeModal');
         const updateModal = document.getElementById('updateModal');

         // 공지사항 모달 외부 클릭 시 닫기
         if (event.target === noticeModal) {
            closeModal();
         }

         // 정보 수정 모달 외부 클릭 시 닫기
         if (event.target === updateModal) {
            closeUpdateModal();
         }
      };
   </script>
</html>