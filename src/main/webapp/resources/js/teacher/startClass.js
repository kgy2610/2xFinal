function generateClassCode() {
    const schoolCode = document.getElementById('school-code').value;  // 학교 코드
    const grade = document.getElementById('grade').value;  // 학년 (1자리로 그대로)
    const classInput = document.getElementById('class').value.padStart(2, '0');  // 반 2자리로 맞추기

    // 현재 년도에서 24만 추출
    const year = new Date().getFullYear().toString().slice(-2);

    // 학교코드 + 년도(24) + 학년 + 반 형식으로 classCode 생성
    const classCode = schoolCode + year + grade + classInput;

    // 생성된 classCode를 classCode 입력 칸에 설정
    document.getElementById('classCode').value = classCode;
}

// 학년이나 반이 변경될 때마다 classCode를 갱신
window.onload = function() {
    document.getElementById('grade').addEventListener('input', generateClassCode);
    document.getElementById('class').addEventListener('input', generateClassCode);
};
