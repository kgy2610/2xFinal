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
    <div id="chat_button" onclick="openModal('${loginUser.status }','${loginUser.stuId}','${loginUser.classCode}')">
		<img src="<c:url value='/resources/img/student/message.png'/>">
	</div>
	<div style="background-color:red; width:20px; height:20px; border-radius:40px; border:none; position: absolute; bottom:115px; right:50px; display:none;" id="newMsg"></div>
	
<div id="chatModal" class="chat_modal">
      <div class="chat_modal-content">
         <h2 class="modalTitle">${teacherName.tcName} 선생님</h2>
         <input type="hidden" value="${teacherName.tcId }" id="teacherId">
         <span class="close" onclick="closeModal()">&times;</span>
           <hr>
           
           <div class="modal_talk_content">
           </div>
               <table>
                  <tr>
                     <td><input type="text" placeholder="메세지를 입력하세요" id="msg"></td>
                     <td><button type="button" onclick="sendMsg('${loginUser.stuId}','${loginUser.classCode}')">전 송</button></td>
                  </tr>
               </table>
         </div>
        </div>
    <script>
	   function openModal(status,stuId,classCode) {
		   if(status ==="N"){
			   alert("반 등록 후 이용 가능합니다.")
			   return
		   }
	       document.getElementById('chatModal').style.display = 'flex';
	       document.getElementById("newMsg").style.display='none';
	       selectChatList(stuId,classCode)
	   }
	
	   // 모달 닫기
	   function closeModal() {
		   document.getElementById("newMsg").style.display='none';
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
        	const modal = document.getElementById('chatModal');
            const reveice = JSON.parse(ev.data);
            if(reveice.sendMessenger === 'TC' && window.getComputedStyle(modal).display==='none'){
            	document.getElementById("newMsg").style.display='flex';	
            }
            selectChatList(reveice.stuId,reveice.classCode);
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
        
        function selectChatList(stuId,classCode){
        	$.ajax({
        		url:"selectChatList",
        		data:{stuId:stuId,classCode:classCode},
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
        			document.getElementsByClassName("modal_talk_content")[0].innerHTML = str;
        			setTimeout(scrollToBottom, 50);
        		},
        		error:function(){
        			console.log("이건 또 왜 안되는데")
        		}
        	})
        }
        function scrollToBottom() {
            const chatBox = document.getElementsByClassName("modal_talk_content")[0];
            requestAnimationFrame(() => {
                chatBox.scrollTop = chatBox.scrollHeight; // 스크롤을 맨 아래로 이동
            });
        }
    </script>
</body>
</html>