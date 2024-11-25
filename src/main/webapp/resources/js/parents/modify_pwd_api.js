function modifyPwdAjax(prId,prQuestion){
        	const prPwd = document.getElementById('prPwd').value;
        	const prAnswer = document.getElementById('prAnswer').value;
			if(prPwd !== prAnswer){
				alert("비밀번호가 서로 다릅니다.");
				
			}else{
				$.ajax({
	        		url: "modify_PWD",
	        		data: {
	        			prId,
	        			prPwd,
	        			prQuestion
	        		},
	        	    success: function(res){
		    			let str = "";
		    			if(res === null || res === undefined || res === "" || res === 0){
		                    str = "<div id='findId-result-fail'>"+
	                        	"<b>올바르지 않은 정보입니다</b>"+
		                    	"</div>"+
		                    	"<div id='successbtn-area'>"+
		                    	"<input type='button' value='다시 찾기'  onclick='reloadPwd()' style='letter-spacing: 5px;'>"+
		                    	"</div>"
		    			}else{
		    				alert("정상적으로 변경되었습니다.")
		    				redirectToLogin()
		    			}                   

		                var element = document.getElementById('findPwd-form');  
		            	element.innerHTML = str;
			    	},error: function(){
			    			console.log("ajax통신 실패")
		    		}
	        	})
			}
        	
        }