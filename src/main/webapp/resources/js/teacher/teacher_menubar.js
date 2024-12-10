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
	   function closeChatModal() {
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
        
        
        
        // 모달 열기
        function openInfoModal2() {
            document.getElementById('noticeModal1').style.display = 'block';
        }

        // 모달 닫기
        function closeInfoModal2() {
            document.getElementById('noticeModal1').style.display = 'none';
        }

        // 정보 수정하기
        function updateInfo() {
          
            closeInfoModal();
        }

        // 비밀번호 수정 모달 열기
        function openPasswordModal2() {
            document.getElementById('passwordModal').style.display = 'block';
        }

        // 비밀번호 수정 모달 닫기
        function closePasswordModal() {
            document.getElementById('passwordModal').style.display = 'none';
        }

        // 비밀번호 수정 확인
        function confirmPasswordUpdate() {
            const currentPassword = document.getElementById('currentPassword').value;
            const newPassword = document.getElementById('newPassword').value;
            const confirmPassword = document.getElementById('confirmPassword').value;

            // 비밀번호 수정 로직 추가
            if (newPassword === confirmPassword) {
                alert('비밀번호가 수정되었습니다.');
                closePasswordModal();
                closeInfoModal();
            } else {
                alert('비밀번호가 일치하지 않습니다.');
            }
        }

        // 반 삭제 모달 열기
        function openDeleteModal() {
            document.getElementById('deleteClassModal').style.display = 'block';
        }

        // 반 삭제 모달 닫기
        function closeDeleteModal() {
            document.getElementById('deleteClassModal').style.display = 'none';
        }

        // 반 삭제 확인
        function confirmDelete() {
            const deleteCode = document.getElementById('deleteCode').value;

    		//서버에 요청
    		
            // 반 삭제 로직 추가
            alert(`반 ${deleteCode}가 삭제되었습니다.`);
            closeDeleteModal();
            closeInfoModal();
        }