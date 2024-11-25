function findPwdAjax(){
        	const prId = document.getElementById('prId').value;
        	const prQuestion = document.getElementById('prQuestion').value;
        	const prAnswer = document.getElementById('prAnswer').value;
        	$.ajax({
        		url: "find_PWD",
        		data: {
        			prId,
        			prQuestion,
        			prAnswer
        		},
        	    success: function(res){
	    			let str = "";
	    			if(res === null || res === undefined || res === ""){
	                    str = "<div id='findId-result-fail'>"+
                        	"<b>올바르지 않은 정보입니다</b>"+
	                    	"</div>"+
	                    	"<div id='successbtn-area'>"+
	                    	"<input type='button' value='다시 찾기'  onclick='reloadPwd()' style='letter-spacing: 5px;'>"+
	                    	"</div>"
	    			}else{
	    				
	    				str = "<div id='findPwd-id'>"+
			                  "<p style='font-size: 20px'>비밀번호</p>"+
			                  "<input type='password' id='prPwd' placeholder='새 비밀번호를 입력하세요' required>"+
			                  "</div>"+
			                  "<div id='findPwd-answer' style='margin-left: 0px;'>"+
		                      "<p style='font-size: 20px; margin-left: 27px;'>비밀번호 확인</p>"+
		                      "<input type='password' id='prAnswer' placeholder='새 비밀번호를 다시 입력하세요' required>"+
		                      "</div>"+
	                    	  "<div id='findPwd-btn' style='margin-top: 55px;'>"+
                        	  "<input type='button' onclick='modifyPwdAjax(\""+prId+"\",\""+prQuestion+"\")' value='비밀번호 변경'>"+
                    		  "</div>"
	    			}                   

	                var element = document.getElementById('findPwd-form');  
	            	element.innerHTML = str;
		    	},error: function(){
		    			console.log("ajax통신 실패")
	    		}
        	})
        }