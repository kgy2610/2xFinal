// 현재 날짜를 yyyy-MM-dd 형식으로 구하기
const today = new Date().toISOString().split('T')[0];

// date-picker input의 value를 오늘 날짜로 설정
document.getElementById('datePicker').value = today;


    
    function updateAttendance() {
        // 날짜 가져오기
        const aDate = document.getElementById('datePicker').value;

        // 학생 출석 데이터 수집
        const rows = document.querySelectorAll('.attendance-table tbody tr');
        const data = [];
        rows.forEach(row => {
            const studentId = row.querySelector('input[name="studentId"]').value;
            const attendanceStatus = row.querySelector(`input[name="attendance_${studentId}"]:checked`).value;
            data.push({ studentId, attendanceStatus });
        });

        // 폼 데이터를 생성하여 전송
        const form = document.createElement('form');
        form.method = 'post';
        form.action = 'modifyAttendance';

        // 날짜 추가
        const dateInput = document.createElement('input');
        dateInput.type = 'hidden';
        dateInput.name = 'aDate';
        dateInput.value = aDate;
        form.appendChild(dateInput);

        // 학생 데이터 추가
        data.forEach(item => {
            const idInput = document.createElement('input');
            idInput.type = 'hidden';
            idInput.name = 'studentId';
            idInput.value = item.studentId;
            form.appendChild(idInput);

            const statusInput = document.createElement('input');
            statusInput.type = 'hidden';
            statusInput.name = `attendance_${item.studentId}`;
            statusInput.value = item.attendanceStatus;
            form.appendChild(statusInput);
        });

        // 폼을 DOM에 추가하고 제출
        document.body.appendChild(form);
        form.submit();
    }