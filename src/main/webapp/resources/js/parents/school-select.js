// 학교 검색 기능
function searchSchools() {
    const schoolName = document.getElementById("school-name").value.trim();  // 입력된 학교명
    if (!schoolName) {
        alert("학교명을 입력해주세요.");
        return;
    }

    // AJAX로 요청 보내기
    $.ajax({
    	url: "searchSchool.list",
        data: { scName : schoolName },
        success: function(data) {
            displaySchools(data); // 검색된 학교 목록을 화면에 표시
        },
        error: function(xhr, status, error) {
            console.error('학교 검색 중 오류 발생:', error);
        }
    });
}

// 학교 목록을 화면에 표시
function displaySchools(data) {
    let str = "";
    for (let sc of data) {
        str += `<tr onclick="selectSchool('${sc.scCode}')">` +
               `<td>${sc.scName}</td>` +
               `<td>${sc.scAddress}</td>` +
               `</tr>`;
    }

    const element = document.getElementById('school-NameList');  
    element.innerHTML += str;
}

// 학교 선택 시, 학교 코드 입력 필드에 값 채우기
function selectSchool(scCode) {
    document.getElementById("scCode").value = scCode;  // 학교 코드 입력 필드에 학교 코드 설정
    closeModal();  // 모달 닫기
}

// 모달 닫기 함수
function closeModal() {
    const modal = document.getElementById("noticeModal");
    modal.style.display = "none";
}

// 모달 열기 함수
function openSchoolModal() {
    const modal = document.getElementById("noticeModal");
    modal.style.display = "block";
}

console.log("JavaScript 파일이 제대로 연결되었습니다.");
