<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AGIT</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/menubar.css'/>">
    <style>

    </style>
</head>
<body>
	
    <div class="nav">
        <img src="<c:url value='/resources/img/logo.png'/>">
        <div class="menu">
            <label for="mypage"><a href="studentMyPage">마이페이지</a></label>
            <label for="homework"><a href="homework">숙제</a></label>
             <label for="afterSchool"><a href="afterschoolPage">방과후 반</a></label>
        </div>
        <div class="undermenu">
            <label for="info" onclick="openUpdateModal()"><a href="#">정보수정</a></label>
            <label for="logout"> <a href="logout.me">로그아웃</a></label>
        </div>
    </div>
    <div id="chat_button" onclick="openModal()">
		<img src="<c:url value='/resources/img/student/message.png'/>">
	</div>
	
<div id="chatModal" class="modal3">
      <div class="modal-content3">
         <h2 id="modalTitle">${teacherName.tcName} 선생님</h2>
         <input type="hidden" value="${teacherName.tcId }" id="teacherId">
         <span class="close" onclick="closeModal()">&times;</span>
           <hr>
           
           <div id="modal_talk_content">
           <c:if test="${empty loginUser.classCode  }">
           	<div>반 등록 이후 이용 가능한 서비스입니다.</div>
           </c:if>
           <c:set var="prevValue" value="" />
           <c:forEach var="c" items="${clist}">
           	<c:choose>
           		<c:when test="${prevValue ne c.chDate}">
           			<div style="text-align: center; font-size:15px;">${c.chDate}</div>
           			<hr style="width:90%; height:3px; margin: 20px auto;">
           			<c:set var="prevValue" value="${c.chDate}" />
           		</c:when>
            </c:choose>
            <c:choose>
           		<c:when test="${empty c.tcId }">
           			<div class="sendMsg"><span class="chMsg">${c.chContent}</span></div>		
           		</c:when>
           		<c:otherwise>
           			<div class="recMsg"><span class="chMsg">${c.chContent}</span></div>			
           		</c:otherwise>
           	</c:choose>    	
           </c:forEach>
           </div>
            <!-- 비밀번호 수정 폼 (기본적으로 숨김) -->
               <table>
                  <tr>
                     <td><input type="text" placeholder="메세지를 입력하세요" id="msg"></td>
                     <td><button type="button" onclick="sendMsg()">전 송</button></td>
                  </tr>
               </table>
         </div>
        </div>
    <script>
	   function openModal() {
	       document.getElementById('chatModal').style.display = 'flex';
	   }
	
	   // 모달 닫기
	   function closeModal() {
	       document.getElementById('chatModal').style.display = 'none';
	   }
        const socket = new WebSocket("ws://localhost:2222/agit/server");

        socket.onopen = function(){
            console.log('연결성공....')
        }

        socket.onclose = function(){
            console.log('연결끊어짐....')
        }

        socket.onerror = function(){
            console.log('연결실패....')
        }

        //socket연결로부터 데이터가 도착했을 때 실행하는 이벤트
        socket.onmessage = function(ev){
            console.log(ev)
            const reveice = JSON.parse(ev.data);

            const msgContainer = document.querySelector("#msg-container");
            msgContainer.innerHTML += (reveice.nick + "(" + reveice.time + ") <br>" + reveice.msg + "<br>");
        }

        function sendMsg(){
            const msgData = {
                message: document.querySelector("#msg").value,
                target: document.querySelector("#teacherId").value,
            }

            console.log(msgData)
            socket.send(JSON.stringify(msgData));
        }
    </script>
</body>
</html>