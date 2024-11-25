// 학교 검색 모달 열기
function openSchoolModal() {
	document.getElementById('noticeModal').style.display = 'block';
}

// 모달 닫기
function closeModal() {
	document.getElementById('noticeModal').style.display = 'none';
}

// 모달 밖 클릭 시 닫기
window.onclick = function(event) {
	var modal = document.getElementById('noticeModal');
	if (event.target == modal) {
	closeModal();
	}
}

function toggleVerification() {
	const teacherSelected = document.getElementById("teacher").checked;
	document.getElementById("enroll-Q").style.display = teacherSelected ? "block" : "none";
	document.getElementById("enroll-A").style.display = teacherSelected ? "block" : "none";
}

// 초기 호출
toggleVerification();

console.log("JavaScript 파일이 제대로 연결되었습니다.");