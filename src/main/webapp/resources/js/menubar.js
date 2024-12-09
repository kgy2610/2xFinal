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
    const updateModal = document.getElementById('updateModal');
    const imageSelectModal = document.getElementById('imageSelectModal');

    // 정보 수정 모달 외부 클릭 시 닫기
    if (event.target == updateModal) {
        closeUpdateModal();
    }

    // 이미지 선택 모달 외부 클릭 시 닫기
    if (event.target == imageSelectModal) {
        closeImageSelectModal();
    }
}