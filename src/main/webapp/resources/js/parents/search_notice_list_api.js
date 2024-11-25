
	 function searchNoticeList(){
           $.ajax({
                url: "notice.list",
                success: function(res){
                	let str =''
                   for(let nt of res){
	            	str += ("<tr data-full-text='"+nt.ntContent+"' data-date='"+nt.createDate+"' onclick='openModal(this)'>" +
	                        "<td class='text-limit'>" + nt.ntContent + "</td>" +
	                        "<td>" + nt.createDate + "</td>" +
	                        "</tr>")
	            }
            var element = document.getElementById('notice_table');  
        	element.innerHTML += str;
                },error: function(){
                   console.log("ajax통신 실패")
                }
             })
           
           
        };
