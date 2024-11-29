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
</head>
<body>
    <div class="nav">
        <img src="<c:url value='/resources/img/logo.png'/>">
        <div class="menu">
            <label for="mypage"><a href="teacher.myPage">마이페이지</a></label>
            <label for="homework"><a href="homeworkList">숙제</a></label>
            <label for="attendance"><a href="teacherAttendance">출결</a></label>
            <label for="advicePlan"><a href="teacherCounsel">상담일정</a></label>
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
         <span class="closeModal" onclick="closeModal()">&times;</span>
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
         			<span class="closeModal" onclick="backModal('${st.stuId}')">&lt;</span>
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
	<script>
	   function openStuModal(classCode) {
		   if(classCode === undefined){
			   alert("반 배정 후 이용가능합니다.")
			   return
		   }
		   document.getElementById('classGrade').innerText = classCode.slice(9,10) + "학년 " + classCode.slice(10,12) + "반"
	       document.getElementById('stuModal').style.display = 'flex';
	       document.getElementById("newMsg").style.display='none';
	   }
	
	   function openChatModal(stuId,classCode){
		   document.getElementById('stuModal').style.display = 'none';
		   document.getElementById("newMsg").style.display='none';
		   document.getElementById('chatModal'+stuId).style.display = 'flex';
		   document.getElementById("newMsgEv"+stuId).style.display='none';
		   selectChatList(stuId,classCode)
	   }
	   // 모달 닫기
	   function closeModal() {
	       document.getElementById('stuModal').style.display = 'none';
	   }
	   
	   function backModal(stuId){
		   document.getElementById('stuModal').style.display = 'flex';
		   document.getElementById('chatModal'+stuId).style.display = 'none';
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
            if(reveice.sendMessenger === 'ST' && window.getComputedStyle(document.getElementById('chatModal'+reveice.stuId)).display==='none'){
            	document.getElementById("newMsg").style.display='flex';
            	document.getElementById("newMsgEv"+reveice.stuId).style.display='flex';
            }
            selectChatList(reveice.stuId,reveice.classCode);
            setTimeout(scrollToBottom, 50);
        }

        function sendMsg(stuId){
            const msgData = {
                message: document.querySelector("#msg"+stuId).value,
                target: stuId,
            }
            document.querySelector("#msg"+stuId).value='';
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
        				if(ch.sendMessenger === 'TC'){
        					str+="<div class='sendMsg'><span class='chMsg'>"+ch.chContent+"</span></div>"
        				}else{
        					str+="<div class='recMsg'><span class='chMsg'>"+ch.chContent+"</span></div>"
        				}
        			}
        			document.getElementById(stuId+"talk_content").innerHTML = str;
        			setTimeout(scrollToBottom, 50);
        		},
        		error:function(){
        			console.log("이건 또 왜 안되는데")
        		}
        	})
        }
        
        
        function scrollToBottom() {
            const chatBoxes = document.getElementsByClassName("modal_talk_content");
            // 각 요소에 대해 스크롤을 맨 아래로 이동
            Array.from(chatBoxes).forEach(chatBox => {
                requestAnimationFrame(() => {
                    chatBox.scrollTop = chatBox.scrollHeight;
                });
            });
        }
    </script>
</body>
</html>