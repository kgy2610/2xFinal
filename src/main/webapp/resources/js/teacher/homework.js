
function filterHomework() {
        const selectedSubject = document.getElementById("subjectSelect").value;
        const homeworkItems = document.querySelectorAll(".homework-item");

        // 선택된 과목이 '전체'일 경우, 모든 항목을 보이게 처리
        if (selectedSubject === "전체") {
            homeworkItems.forEach(item => {
                item.style.display = "";
            });
        } else {
            // 선택된 과목과 일치하는 항목만 보이게 처리
            homeworkItems.forEach(item => {
                const subject = item.getAttribute("data-subject");
                if (subject === selectedSubject) {
                    item.style.display = "";
                } else {
                    item.style.display = "none";
                }
            });
        }
    }
    
    
    
function addHomeworkPage() {
  // 페이지 이동 (서버에서 처리)
  window.location.href = 'addHomeworkPage';
}

