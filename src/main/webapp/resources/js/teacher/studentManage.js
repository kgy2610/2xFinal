// 모달 열기
	function openModal(element) {
		var fullText = element.getAttribute('data-full-text');
		var date = element.getAttribute('data-date');

		document.getElementById('modalContent').textContent = fullText;
		document.getElementById('modalDate').textContent = date;

		document.getElementById('noticeModal').style.display = 'block';
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
                    <div class="real-grade-information">
                        <div>${sl.SUBJECT}</div>   <!-- 과목명 -->
                        <div>${sl.SCORE}점</div>   <!-- 점수 -->
                    </div>
                    <hr class="line4">  <!-- 구분선 -->
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


	function chCancel(){
		
	}


