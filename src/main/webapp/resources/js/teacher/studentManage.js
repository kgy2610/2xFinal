// 모달 열기
	function openModal(element) {
		var fullText = element.getAttribute('data-full-text');
		var date = element.getAttribute('data-date');

		document.getElementById('modalContent').textContent = fullText;
		document.getElementById('modalDate').textContent = date;

		document.getElementById('noticeModal').style.display = 'block';
		
		console.log(document.getElementById('modalContent'));
	}
	
	
	
          
    	

	// 모달 닫기
	function closeModal() {
		document.getElementById('noticeModal').style.display = 'none';
	}

	// 공지사항 길이 제한
	document.querySelectorAll(".text-limit").forEach(function(element) {
		const text = element.innerText;
		if (text.length > 20) {
			element.innerText = text.substring(0, 20) + " ...";
		}
	});

	// 모달 밖 클릭 시 닫기
	window.onclick = function(event) {
		var modal = document.getElementById('noticeModal');
		if (event.target == modal) {
			closeModal();
		}
	}

	function showStudentInfo(name, phone, attendanceRate, stuId) {
		console.log(stuId);
		document.querySelector('.real-student-information div:nth-child(1)').textContent = '이름 : '
				+ name;
		document.querySelector('.real-student-information div:nth-child(2)').textContent = '전화번호 : '
				+ phone;
		document.querySelector('.real-student-information div:nth-child(3)').textContent = '출석률 : '
				+ attendanceRate + '%';

  		document.querySelector("input[name='stuId']").value = stuId;

		fetchScores(stuId);
		
	}
	
function fetchScores(stuId) {
    if (!stuId) {
        console.error('stuId is undefined or null');
        return;
    }

    $.ajax({
        url: 'studentManage.me',
        data: { stuId: stuId },
        dataType: 'json', // 서버 응답을 JSON으로 기대
        success: function(scoresList) {
            console.log('Scores received:', scoresList); // 응답 데이터 확인
            const gradesContainer = document.querySelector('.real-grade'); // 제목과 점수 부분을 포함하는 div
            const line3 = document.querySelector('.line3');  // 전역적으로 line3 찾기

            if (!line3) {
                console.error('line3 not found');
                return;
            }

            // 이전에 추가된 점수 정보 삭제 (초기화)
            const previousGrades = document.querySelectorAll('.real-grade-information');
            previousGrades.forEach(grade => {
                grade.remove();
            });
            
            // 이전에 추가된 구분선 삭제 (line4)
            const previousLines = document.querySelectorAll('.line4');
            previousLines.forEach(line => {
                line.remove();
            });

            // 반복문을 사용하여 각 점수 항목에 대해 HTML 요소를 동적으로 생성
            for (let sl of scoresList) {
                console.log(sl.SCORE);  // 점수 출력
                console.log(sl.SUBJECT); // 과목 출력

                // 과목명, 점수, 구분선 포함된 전체 구조 생성 후, line3 뒤에 추가
                const gradeHtml = `
                	<div>
                    <div class="real-grade-information">
                        <div>${sl.SUBJECT}</div>   <!-- 과목명 -->
                        <div>${sl.SCORE}점</div>   <!-- 점수 -->
                    </div>
                    <hr class="line4">  <!-- 구분선 -->
                	<div>
                `;

                // line3 이후에 추가
                line3.insertAdjacentHTML('afterend', gradeHtml);
            }
        },
        error: function(xhr, status, error) {
            console.error('Error fetching scores:', status, error);
        }
    });   
    
}
	
 //승인이 필요한 교직원 목록
           window.onload = function(){
           	// AJAX로 요청 보내기
           	$.ajax({
           		url: "yStudentStatus", 
           		type: "GET", 
           		success: function(data) {
           			console.log(data);  // 데이터가 어떻게 반환되는지 확인
           			if (data && Array.isArray(data.yStudentStatuss)) {
           				yStudentStatuss(data.yStudentStatuss); 
           			} else {
           				console.error('반환된 데이터가 예상한 배열 형식이 아닙니다.', data);
           			}
           		},
           		error: function(xhr, status, error) {
           			console.error('선생님 목록을 불러오는 중 오류 발생:', error);
           		}
           	});
           }
	
	  // 승인 필요 선생님 목록을 화면에 표시
           function yStudentStatuss(StudentListIn) {
               let str = "";
              
               
               // studentOutList가 배열인지 확인
               if (Array.isArray(StudentListIn)) {
                   for (let sli of StudentListIn) {
                       console.log('sli:', sli); 
                       console.log('status:', sli.status); 
                       

                       str +=       
	                    `<div class="real-num-name-check">` + 
							`<div>` + sli.stuNum + `</div>` +
							`<div>` + sli.stuName + `</div>` + 
							`<div>`
								<input type="button" value="수락" onclick="studentRequest('`  sli.classCode  `', '`  sli.status  `')"
	                                   style="width: 100px; height: 30px; background: #DDE5B6; border-radius: 10px; border: none; 
	                                   color: #A98467; font-size: 15px; font-weight: 800; cursor: pointer;"> +
							`</div>`+
						`</div>` +
						`<hr class="line6">`;
                       
                        
                   }
               } else {
                   console.error('StudentListIn는 배열이 아닙니다:', StudentListIn);
               }

               const element = document.getElementById('modalContent');
               element.innerHTML += str; // 'modalContent' 요소에 HTML 추가
           }
           
           
       // 교직원 승인 여부 모달 열기
       function studentRequest(stuId, status){
    	    
    	    if(status === 'N'){
    	        if(confirm(`승인하시겠습니까?`)){
    	            request(stuId, 'Y');
    	        }    
    	    } else{
    	    	 alert(`올바르지 않은 요청입니다. 받은 status: ${status}`);// 정확한 status 값도 알 수 있음
    	    }
    	}
    	
    	  //교직원 승인
          function request(stuId, status){
          	console.log("request js | 승인할 선생님의 stuId : " + stuId);
          	console.log("request js | 승인할 선생님의 status : "+ status);

          	$.ajax({
          		url: "requestStudent",
          		type: "POST",
          		data: {stuId : stuId, status : status},
          		success: function(response){
          			if(response === "success"){
          				alert("승인 완료");
          				location.reload();
          			}else if(response === "fail"){
          				alert("승인 실패");
          			}else if(response === "올바르지 않은 요청입니다."){
          				alert(response)
          			}
          		},
          		error: function(){
          			alert("요청 작업 실패");
          			location.reload();
          		}
          	});
          }
