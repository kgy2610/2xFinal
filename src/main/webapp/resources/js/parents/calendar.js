    $(document).ready(function() {
    const fixedYear = 2024;
    initializeMonthSelector();
    createCalendar(fixedYear);

    $('#monthSelect').on('change', function() {
        createCalendar(fixedYear);
    });

    $('#calendar').on('click', '.cs_button', function() {
        const day = $(this).text();
        if (day) {
            $('#date').val(`${fixedYear}-${$('#monthSelect').val().padStart(2, '0')}-${day.padStart(2, '0')}`);
            selectedCell = $(this); // 선택한 셀 저장
            document.getElementById('counselNo').value = $(selectedCell).find('input').val()
            $('#modal').css('display', 'block'); // 모달 열기
            
        }
    });

    $('#closeBtn').on('click', function() {
        $('#modal').css('display', 'none'); // 모달 닫기
    });


    $(window).on('click', function(event) {
        if ($(event.target).is('#modal')) {
            $('#modal').css('display', 'none'); // 모달 외부 클릭 시 닫기
        }
    });
});

let selectedCell; 

function initializeMonthSelector() {
    // 월 선택기 설정
    for (let month = 1; month <= 12; month++) {
        $('#monthSelect').append(new Option(month, month));
    }
    $('#monthSelect').val(new Date().getMonth() + 1); // 현재 월로 초기화
    
    // 월 선택기의 스타일 설정 (배경색과 중앙 정렬)
    $('#monthSelect').css({
        'background-color': '#A98467', // 원하는 배경색
        'text-align': 'center',
        'font-size' : '30px'
    });
    
    $('#monthSelect').change(function () {
        const selectedMonth = $(this).val(); // 선택된 값
        selectSameMonthCounsel(selectedMonth); // 함수 호출
    });
}

function createCalendar(year) {
    const month = $('#monthSelect').val();
    const daysInMonth = new Date(year, month, 0).getDate(); // 해당 월의 일 수
    const firstDay = new Date(year, month - 1, 1).getDay(); // 첫 번째 날의 요일
    const tbody = $('#calendar tbody');

    // 테이블 비우기
    tbody.empty();
    
    // 빈 셀 추가
    let rows = '';
    let day = 1;

    for (let i = 0; i < 6; i++) { // 6주까지
        rows += '<tr>';
        for (let j = 0; j < 7; j++) { // 7일
            if (i === 0 && j < firstDay) {
                rows += '<td></td>'; // 빈 셀
            } else if (day > daysInMonth) {
                rows += '<td></td>'; // 빈 셀
            } else {
                rows += `<td>${day}</td>`;
                day++;
            }
        }
        rows += '</tr>';
    }

    tbody.html(rows);
}

window.onload=function(){
	selectSameMonthCounsel();
}

function selectSameMonthCounsel(){
	const month = $('#monthSelect').val();
	const today = new Date();
	$.ajax({
		url: 'selectSameMonthCounsel',
		data: {month: month},
		success:function(res){
			const currentMonth = String(today.getMonth() + 1).padStart(2, '0');
			let cal = document.getElementById("cal");
			let elements = cal.getElementsByTagName("td");
    		for(let c of res){
    			for(let m of elements){
    				let to = today.getDate();
    				if((m.innerText.split(":")[0].length===1||m.innerText.split(":")[0].length===3?'0'+m.innerText.substring(0,1):m.innerText.substring(0,2)) == c.csDate.substring(3,5)){
    					if(c.csDate.substring(3,5) < to){
    						m.innerHTML+="<button class='cs_nubutton'>"+c.csDate.substring(6,11)+" <br> "+c.csLocation+"</button>"
    					}else if(c.prId != null){
    						m.innerHTML+="<button class='cs_nubutton'>"+c.csDate.substring(6,11)+" <br> "+c.csLocation+"</button>"
    					}else if(c.csDate.substring(0,2)!== currentMonth){
            				m.innerHTML+="<button class='cs_nubutton'>"+c.csDate.substring(6,11)+" <br> "+c.csLocation+"</button>"
            			}else{
    						m.innerHTML+="<button class='cs_button'>"+c.csDate.substring(6,11)+" <br> "+c.csLocation+"<input type='hidden' id='bt_cs_no' value='"+c.csNo+"'></button>"
    					}
    				}  
    			}
    		}
		},
		error:function(){
			console.log("일단 왜인지는 모르겠는데 안됨");
		}
	})
}