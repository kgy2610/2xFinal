        function openSubjectModal(subject) {
        	document.getElementById("modaltitle").innerText=subject;
        	$.ajax({
        		url: "score.list",
        		data:{ subject: subject},
        		success: function(res){
        			let str = ''
        			for(let hs of res){
        				str += ("<tr>"+"<td>"+hs.hmTitle+"</td><td>"+hs.score+"</td></tr>")
        			}
        			document.getElementById("scoretable").innerHTML=str;
        		},
        		error: function(){
        			console.log("ajax통신 실패")
        		}
        	});
			
            document.getElementById('subjectModal').style.display = 'block';
         }