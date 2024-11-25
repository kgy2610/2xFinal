function modifyParentsPwdAjax(prId,oldPwd,prQuestion){
        	const beforePwd = document.getElementById('old_pwd').value;
        	if(oldPwd !== beforePwd){
				alert("기존비밀번호가 다릅니다.");
			}else{
				const prPwd = document.getElementById('new_pwd').value;
	        	const prAnswer = document.getElementById('new_pwd_co').value;
				if(prPwd !== prAnswer){
					alert("비밀번호가 서로 다릅니다.");
				}else{
					$.ajax({
		        		url: "modify_parents_PWD",
		        		data: {
		        			prId,
		        			prPwd,
		        			prQuestion
		        		},
		        	    success: function(res){
			    			if(res === null || res === undefined || res === "" || res === 0){
			    				alert("변경하는데 실패하였습니다.")
			    			}else{
			    				alert("정상적으로 변경되었습니다.")
			    				document.getElementById('old_pwd').value = '';
			    				document.getElementById('new_pwd').value = '';
			    				document.getElementById('new_pwd_co').value = '';
			    				closeUpdateModal()
			    			}                   
				    	},error: function(){
				    			console.log("ajax통신 실패")
			    		}
		        	})
				}
        	
			}
        	
        }