    let scoreElements = document.getElementsByClassName('stu_score');
    let scores = Array.from(scoreElements).map(element => Number(element.innerText));
    
    document.addEventListener('DOMContentLoaded', () => {
	    const canvas = document.getElementById('barCanvas');
	    const ctx = canvas.getContext('2d');
	    
	    if (scores.every(score => score === 0 || isNaN(score))) {
	    	ctx.clearRect(0, 0, canvas.width, canvas.height);
	    	
	    	ctx.font = '20px Arial'; // 글꼴과 크기 설정
		    ctx.fillStyle = '#A98467'; // 텍스트 색상
		    ctx.textAlign = 'center'; // 텍스트 정렬
		    ctx.textBaseline = 'middle'; // 텍스트 기준선
		    
		    ctx.fillText('아직 숙제 검사를 하지 않았습니다.', canvas.width / 2, canvas.height / 2);
		} else {
	    
		    const myChart = new Chart(ctx, {
		        type: 'bar',  // 기본 유형을 막대그래프로 설정
		        data: {
		            labels: ["국어", "수학", "영어", "과학", "사회", "미술", "체육"],
		            datasets: [
		                {
		                    label: '내 점수',
		                    data: scores,
		                    backgroundColor: '#FAEDCD',
		                    borderColor: '#FAEDCD',
		                    borderWidth: 1,
		                    order: 2
		                }
		            ]
		        },
		        options: {
		            plugins: {
		                legend: {
		                    position: 'right'  // 범례를 오른쪽에 배치
		                }
		            },
		            scales: {
		                x: {
		                    ticks: {
		                        font: {
		                            size: 18 // 하단 labels의 글꼴 크기를 16px로 설정   
		                        },
		                        color: '#A98467'
		                    }
		                },
		                y: {
		                    beginAtZero: true,
		                    max: 100
		                }
		            }
		        }
		    });
		}
	});

    // 텍스트 제한 (옵션 기능)
    document.querySelectorAll(".text-limit").forEach(function (element) {
        const text = element.innerText;
        if (text.length > 3) {
            element.innerText = text.substring(0, 3) + "...";
        }
    });

    document.querySelectorAll(".title-limit").forEach(function (element) {
        const text = element.innerText;
        if (text.length > 15) {
            element.innerText = text.substring(0, 12) + "...";
        }
    });
        
function moveHomeworkDetail(boNo, status, currentPage) {
	if (status === 'Y') {
	// 이미 제출된 경우 확인 페이지로 이동
	location.href = `homework.check?boNo=${boNo}&cpage=${currentPage}`;
	} else {
	// 미제출인 경우 제출 페이지로 이동
	location.href = `homework_detail?boNo=${boNo}&cpage=${currentPage}`;
	}
}