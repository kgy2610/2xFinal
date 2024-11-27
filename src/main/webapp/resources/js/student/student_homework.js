        const ctx = document.getElementById('barCanvas').getContext('2d');
        const myChart = new Chart(ctx, {
            type: 'bar',  // 기본 유형을 막대그래프로 설정
            data: {
                labels: ["국어", "수학", "영어", "과학", "사회", "미술", "체육"],
                datasets: [
                    {
                        label: '내 점수',
                        data: [30, 50, 40, 60, 70, 90, 100],
                        backgroundColor: '#FAEDCD',
                        borderColor: '#FAEDCD',
                        borderWidth: 1,
                        order: 2
                    },
                    {
                        label: '전체 평균 점수',
                        data: [20, 45, 35, 55, 65, 85, 95],
                        type: 'line',  // 이 데이터셋을 꺾은선 그래프로 설정
                        borderColor: '#A98467',
                        borderWidth: 2,
                        fill: false,
                        order: 1
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
                        beginAtZero: true
                    }
                }
            }
        });

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