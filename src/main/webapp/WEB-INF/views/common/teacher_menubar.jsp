<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.twoX.agit.member.model.vo.Teacher"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AGIT</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/menubar.css'/>">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <div class="nav">
        <img src="<c:url value='/resources/img/logo.png'/>">
        <div class="menu">
            <label for="mypage"><a href="teacher.myPage">마이페이지</a></label>
            <label for="homework"><a href="homeworkList">숙제</a></label>
            <label for="attendance"><a href="teacherAttendance">출결</a></label>
            <label for="advicePlan"><a href="#">상담일정</a></label>
            <label for="community"><a href="makeAfterClass.me">방과후 반</a></label>
            <label for="photo"><a href="teacher_eventImgList">행사사진</a></label>
            <label for="studentManage"><a href="studentManage.me">학생관리</a></label>
        </div>
        <div class="undermenu">
            <label for="info" onclick="openInfoModal()"><a href="#">정보수정</a></label>
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
         <span class="close" onclick="closeModal()">&times;</span>
           <hr>
           
           <div id="modal_Stu_content">
           		<c:forEach var="sl" items="${slist}">
           			<table class="modal_stuList" style="background-color:#DDE5B6; width:430px; height:60px; border-radius:15px; text-align:center; margin-bottom:10px;">
	           			<tr>
	           				<td class="modal_stuName" style="width:20%">${sl.stuId}</td>
	           				<td class="modal_lastChat" style="width:60%">${sl.chContent}</td>
	           				<td class="modal_newMsgCount" style="width:20%"><span style="display:inline-bloack; background-color:#efccb0; padding: 10px 15px 10px 15px; border-radius:15px;">4 +</span></td>
	           			</tr>
	           		</table>	
           		</c:forEach>
           		
           </div>
         </div>
        </div>
        
	<div id="chatModal" class="chat_modal">
      <div class="chat_modal-content">
         <h2 class="modalTitle">${teacherName.tcName} 선생님</h2>
         <input type="hidden" value="${teacherName.tcId }" id="teacherId">
         <span class="close" onclick="closeModal()">&times;</span>
           <hr>
           
           <div id="modal_talk_content">
           		
           </div>
               <table>
                  <tr>
                     <td><input type="text" placeholder="메세지를 입력하세요" id="msg"></td>
                     <td><button type="button" onclick="sendMsg()">전 송</button></td>
                  </tr>
               </table>
         </div>
        </div>
	<script>
	   function openStuModal(classCode) {
		   console.log(classCode)
		   if(classCode === undefined){
			   alert("반 배정 후 이용가능합니다.")
			   return
		   }
		   document.getElementById('classGrade').innerText = classCode.slice(9,10) + "학년 " + classCode.slice(10,12) + "반"
	       document.getElementById('stuModal').style.display = 'flex';
	       document.getElementById("newMsg").style.display='none';
	       selectChatList()
	   }
	
	   // 모달 닫기
	   function closeModal() {
	       document.getElementById('stuModal').style.display = 'none';
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
            const reveice = JSON.parse(ev.data);
            if(reveice.sendMessenger === 'ST'){
            	document.getElementById("newMsg").style.display='flex';	
            }
            selectChatList();
            setTimeout(scrollToBottom, 50);
        }

        function sendMsg(){
            const msgData = {
                message: document.querySelector("#msg").value,
                target: document.querySelector("#teacherId").value,
            }
            document.querySelector("#msg").value='';
            socket.send(JSON.stringify(msgData));
        }
        
        function selectChatList(){
        	$.ajax({
        		url:"selectChatList",
        		success:function(res){
        			let str=''
        			let todate=''
        			for(let ch of res){
        				if(ch.chDate !== todate){
        					str+="<div style='text-align: center; font-size:15px;'>"+ch.chDate+"</div>"
        	      			+"<hr style='width:90%; height:3px; margin: 20px auto;'>"
        					todate=ch.chDate
        				}
        				if(ch.sendMessenger === 'ST'){
        					str+="<div class='sendMsg'><span class='chMsg'>"+ch.chContent+"</span></div>"
        				}else{
        					str+="<div class='recMsg'><span class='chMsg'>"+ch.chContent+"</span></div>"
        				}
        			}
        			document.getElementById("modal_talk_content").innerHTML = str;
        			setTimeout(scrollToBottom, 50);
        		},
        		error:function(){
        			console.log("이건 또 왜 안되는데")
        		}
        	})
        }
        function scrollToBottom() {
            const chatBox = document.getElementById("modal_talk_content");
            requestAnimationFrame(() => {
                chatBox.scrollTop = chatBox.scrollHeight; // 스크롤을 맨 아래로 이동
            });
        }
    </script>
</body>
</html>