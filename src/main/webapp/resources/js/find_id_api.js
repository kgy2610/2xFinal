function findIdAjax(){
        	const prName = document.getElementById('prName').value;
        	const phone = document.getElementById('phone').value;
        	$.ajax({
        		url: "find_ID",
        		data: {
        			prName,
        			phone
        		},
        	    success: function(res){
        	    	document.getElementById('searchInfo').innerText='';
	    			let str = "";
	    			if(res === null || res === undefined || res === ""){
	                    str= "<div id='findId-result-fail'>"+
                        	"<b>"+prName+"</b>님의 아이디는 <br>"+
                        	"<b>존재하지 않습니다</b>"+
	                    	"</div>"+
	                    	"<div id='successbtn-area'>"+
	                    	"<input type='button' value='다시 찾기'  onclick='window.location.reload()' style='letter-spacing: 5px;'>"+
	                    	"</div>"
	    			}else{
	    				str = "<div id='findId-result-fail'>"+
                  	  "<b>"+res.prName+"</b>님의 아이디는 <br>"+
                  	  "<b>"+res.prId+"</b>입니다."+
                  	  "</div>"+
                  	  "<div id='successbtn-area'>"+
                  	  "<input type='button' value='로그인화면으로'  onclick='redirectToLogin()' style='letter-spacing: 5px;'>"+
                  	  "</div>"
	    			}
	    			
	                var element = document.getElementById('findId-form');  
	            	element.innerHTML = str;
		    	},error: function(){
		    			alert("존재하지 않는 정보입니다.");
		    			console.log("ajax통신 실패");
	    		}
        	})
        }