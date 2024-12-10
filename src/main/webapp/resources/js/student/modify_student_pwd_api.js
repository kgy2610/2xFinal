function modifyStudentPwdAjax(stuId,stuPwd,stuQuestion){
        	const beforePwd = document.getElementById('old_pwd').value;
        	if(stuPwd !== beforePwd){
				alert("기존비밀번호가 다릅니다.");
			}else{
				const stuPwd = document.getElementById('new_pwd').value;
	        	const stAnswer = document.getElementById('new_pwd_co').value;
				if(stuPwd !== stAnswer){
					alert("비밀번호가 서로 다릅니다.");
				}else{
					$.ajax({
		        		url: "modify_student_PWD",
		        		data: {
		        			stuId,
		        			stuPwd,
		        			stuQuestion
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
			    				location.reload();
			    			}                   
				    	},error: function(){
				    			console.log("ajax통신 실패")
			    		}
		        	})
				}
        	
			}
        	
        }