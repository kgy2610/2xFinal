function modifyParentsInfoAjax(prId){
        	const nickname = document.getElementById('nickname').value;
        	const phone = document.getElementById('phone').value;
			$.ajax({
        		url: "modify_parents_info",
        		data: {
        			prId,
        			nickname,
        			phone,
        		},
        	    success: function(res){
	    			if(res === null || res === undefined || res === "" || res === 0){
						alert("변경하는데 실패하였습니다.")
	    			}else{
	    				alert("정상적으로 변경되었습니다.")
	    				closeUpdateModal()
	    			}                   
		    	},error: function(){
		    			console.log("ajax통신 실패")
	    		}
        	})
			
        	
        }