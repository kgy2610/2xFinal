<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.twoX.agit.member.model.vo.Parents"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <title>parents_detailcommunity</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/parents/parents_detailcommunity.css'/>">
</head>
<body>
    <jsp:include page="parents_menubar.jsp" />
    <input type="hidden" value=${loginUser.prId } id="loginInfo">
    <input type="hidden" value=${npage.prId } id="pageInfo">
    <div id="content_border">
        <div id="enroll_title">${npage.boTitle }</div>
        <div id="enroll_content" >
            <div id="enroll_info">작성자 ${npage.prNickname } | 조회수 ${npage.boCount } | ${npage.createDate }</div>
            <div id="en_content">${npage.contents } </div>
            <div id="enroll_file">
                <c:choose>
						<c:when test="${not empty npage.originName }">
                       		<span>첨부된 파일:</span><a id="downloadLink" href="<c:url value='${npage.changeName}'/>" download="${npage.originName }">${npage.originName }</a>
                       	</c:when>
                       	<c:otherwise>
							<span id="fileName" class="file-name">선택된 파일이 없습니다</span>
						</c:otherwise>
                </c:choose>
            </div>
        </div>
        <div id="reply_area"></div>
                 
        <table class="parents_reply" id="enroll_reply">
        	<input type="hidden" id="boardNum" name="boNo" value="${npage.boNo}">
            <tr>
                <td><div></div></td>
                <td>
                	<c:choose>
                		<c:when test="${npage.prId eq loginUser.prId}">
                			<p style="font-weight:bold;">작성자</p>
                		</c:when>
                		<c:otherwise>
                			${loginUser.nickname}
                		</c:otherwise>
                	</c:choose>
                </td>
                <td><div><input type="text" placeholder="댓글을 입력하세요" id="en_reply"  name="reContent" required></div></td>
                <td><div></div></td>
                <td><div><button type="button" id="enroll_button" onclick="insertReply()">등록</button></div></td>
                <td></td>
            </tr>
        </table>

        <button class="create_button" onclick="location.href='all_community?cpage=${cpage}'">목록으로</button>
        <c:choose>
        	<c:when test="${loginUser.prId eq npage.prId }">
        		<button class="create_button" id="modify_button" onclick="location.href='board_modify?boNo=${npage.boNo}&cpage=${cpage}'">수정하기</button>	
        	</c:when>
        </c:choose>
    </div>
    <script>
    	var RrepNickname;
    	window.onload=function(){
			showReplyList()    	
    	}
    	
    	function showReplyList(){
    		const boNo = document.getElementById('boardNum').value;
    		const loginInfo = document.getElementById('loginInfo').value; 
    		const pageInfo = document.getElementById('pageInfo').value;
    		$.ajax({
    			url:'selectReply',
    			data: {boNo:boNo},
    			success: function(res){
    				let str = '';
    				document.getElementById('reply_area').innerHTML = str;
    				for(let re of res){
    					if(re.prepNo === 0){
    						str = "<table class='parents_reply' id='pr"+re.reNo+"'>" +
    			            "<tr>" +
    			            "<td><div></div></td>" +
    			            "<td>" + (pageInfo === re.prId ? "<p style='font-weight:bold'>작성자</p>":re.nickname) + "</td>" +
    			            "<td><div class='replyContent' id='el"+re.reNo+"'>" + (re.reContent ? re.reContent : "삭제된 댓글입니다.") + "</div></td>" +
    			            "<td><div>" + (loginInfo === re.prId ? "<button onclick='changeModifyForm(\""+re.reNo+"\",\""+re.reContent+"\",this)'>수정</button>" : "") + "</div></td>" +
    	                    "<td><div>" + (loginInfo === re.prId ? "<button onclick='deleteReply(\""+re.reNo+"\")'>삭제</button>" : "") + "</div></td>" +
    			            "<td onclick='insertChildRep(\""+re.reNo+"\",\""+re.nickname+"\",\""+re.prId+"\")'><img src='/agit/resources/img/parents/Vector.png' class='reply_icon'/></td>" +
    			            "</tr>" +
    			            "</table>";
    						document.getElementById('reply_area').innerHTML += str;	
    					}else{
    						continue;
    					}
    				}
    				for(let rp of res){
    					if(rp.prepNo === 0){
    						continue;
    					}else{
    						str = "<table class='parents_reply child_reply'>" +
    			            "<tr>" +
    			            "<td><div></div></td>" +
    			            "<td>" + (pageInfo === rp.prId ? "<p style='font-weight:bold'>작성자</p>":rp.nickname) + "</td>" +
    			            "<td><div class='replyContent' id='el"+rp.reNo+"'>" + (rp.reContent ? rp.reContent : "삭제된 댓글입니다.") + "</div></td>" +
    			            "<td><div>" + (loginInfo === rp.prId ? "<button onclick='changeModifyForm(\""+rp.reNo+"\",\""+rp.reContent+"\",this)'>수정</button>" : "") + "</div></td>" +
    	                    "<td><div>" + (loginInfo === rp.prId ? "<button onclick='deleteReply(\""+rp.reNo+"\")'>삭제</button>" : "") + "</div></td>" +
    			            "<td></td>" +
    			            "</tr>" +
    			            "</table>";
    			            let table = document.getElementById('pr'+rp.prepNo);
    			            table.insertAdjacentHTML('afterend', str);
    					}
    				}
					
    			},
    			error: function(){
    				console.log('이건 또 왜 안되는데');
    			}
    		})
    	}
    	
    	function insertChildRep(reNo,nickname,prId){
    		const pageInfo = document.getElementById('pageInfo').value;
    		RrepNickname = '@'+(pageInfo === prId ? "작성자":nickname);
    		const divs = document.querySelectorAll('#enroll_reply div'); 
    		divs.forEach(div => {
    	        div.style.backgroundColor = '#F4DABF';
    	    });
    		document.getElementById('en_reply').value='@'+(pageInfo === prId ? "작성자":nickname)+'  '
    		document.getElementById('enroll_button').setAttribute('onclick',"insertReply('"+reNo+"')");
    	}
    	
    	document.getElementById("en_reply").oninput = function() {
    		if(document.getElementById("en_reply").value.split(' ')[0] !== RrepNickname){
    			const divs = document.querySelectorAll('#enroll_reply div'); 
        		divs.forEach(div => {
        	        div.style.backgroundColor = '#DDE5B6';
        	        document.getElementById('enroll_button').setAttribute('onclick',"insertReply()");
        	    });
    		}
    	  };
    	  
    	function deleteReply(reNo){
    		$.ajax({
    			url: 'deleteReply',
    			data: {reNo:reNo},
    			success: function(res){
    				if(res!==0){
    					alert("삭제되었습니다.");
    					showReplyList();
    				}else{
    					alert("댓글 삭제 실패");
    				}
    			},
    			error: function(){
    				console.log("이건 또 왜 안되는데")
    			}
    		})
    	}
    	
    	function changeModifyForm(reNo,reContent,el){
    		el.innerText = '저장';
    		el.setAttribute('onclick', "modifyReply('"+reNo+"',this)");
    		document.getElementById('el'+reNo).innerHTML="<input type='text' class='modify_reply_input' id='co"+reNo+"' value='"+reContent+"'>"
    		
    	}
    	
    	function modifyReply(reNo){
    		const reContent = document.getElementById('co'+reNo).value;
    		if(reContent === ""){
    			alert("댓글 입력해주세요.")
    			return
    		}
    		$.ajax({
    			url:'modifyReply',
    			data: {reNo:reNo,reContent:reContent},
    			success: function(res){
    				if(res!==0){
    					alert("수정되었습니다.");
    					showReplyList();
    				}else{
    					alert("댓글 수정 실패");
    				}
    			},
    			error: function(){
    				console.log('이건 또 왜 안되는데');
    			}
    		})
    	}
    	
    	function insertReply(prepNo){
    		const boNo = document.getElementById('boardNum').value;
    		const reContent = document.getElementById('en_reply').value;
    		if(reContent==="" || reContent.split(' ')[2] === ""){
    			alert("댓글 입력해주세요.")
    			return
    		}
    		const divs = document.querySelectorAll('#enroll_reply div'); 
    		divs.forEach(div => {
    	        div.style.backgroundColor = '#DDE5B6';
    	    });
    		
    		document.getElementById('enroll_button').setAttribute('onclick',"insertReply()");
    		$.ajax({
    			url:'insertReply',
    			data: {boNo:boNo,reContent:reContent,prepNo:prepNo},
    			success: function(res){
    				if(res!==0){
    					document.getElementById('en_reply').value='';
    					showReplyList();
    				}else{
    					alert("게시글작성 실패");
    				}
    			},
    			error: function(){
    				console.log('이건 또 왜 안되는데');
    			}
    		})
    	}
        document.querySelectorAll(".text-limit").forEach(function (element) {
            const text = element.innerText;
            if (text.length > 3) {
                element.innerText = text.substring(0, 3) + "...";
            }
        });

        document.querySelectorAll(".title-limit").forEach(function (element) {
            const text = element.innerText;
            if (text.length > 15) {
                element.innerText = text.substring(0, 12) + "...";
            }
        });

        function showFileName() {
            const fileInput = document.getElementById("fileInput");
            const fileName = document.getElementById("fileName");

            if (fileInput.files.length > 0) {
                fileName.textContent = fileInput.files[0].name;
            } else {
                fileName.textContent = "선택된 파일이 없습니다";
            }
        }
        const enTitleInput = document.getElementById("en_reply");

        // 포커스가 되었을 때 placeholder 숨기기
        enTitleInput.addEventListener("focus", function() {
            enTitleInput.setAttribute("data-placeholder", enTitleInput.getAttribute("placeholder"));
            enTitleInput.setAttribute("placeholder", "");
        });

        // 포커스가 해제되었을 때 원래 placeholder 복원
        enTitleInput.addEventListener("blur", function() {
            enTitleInput.setAttribute("placeholder", enTitleInput.getAttribute("data-placeholder"));
        });
        
        function showRreplyInput(tdElement,prNickname) {
            // 클릭된 td의 부모 tr 요소를 찾음
            const currentTr = tdElement.closest('tr');

            // 새로운 tr 생성
            const newTr = document.createElement('tr');
            newTr.innerHTML = "<td><div></div></td><td>"+prNickname+"</td><td><div><input type='text' placeholder='댓글을 입력하세요' id='en_reply' required></div></td><td><div></div></td><td><div><button type='submit'>등록</button></div></td><td></td>";

            // 현재 tr 바로 다음에 새로운 tr 삽입
            currentTr.parentNode.insertBefore(newTr, currentTr.nextSibling);
        }
        
    </script>
</body>
</html>