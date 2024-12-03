   //글자 수 제한
   document.querySelectorAll(".body_title").forEach(function (element) {
            const text = element.innerText;
            if (text.length > 15) {
                element.innerText = text.substring(0, 15) + "...";
            }
        });

       
   
    // 모달 열기
    function openInfoModal() {
        document.getElementById('noticeModal').style.display = 'block';
    }

    // 모달 닫기
    function closeInfoModal() {
        document.getElementById('noticeModal').style.display = 'none';
    }

    // 정보 수정하기
    function updateInfo() {
        const code = document.getElementById('code').value;
        const grade = document.getElementById('grade').value;
        const classValue = document.getElementById('class').value;

        // 수정 기능 구현 로직 추가
        alert(`정보 수정:\n코드: ${code}\n학년: ${grade}\n반: ${classValue}`);
        closeInfoModal();
    }

    // 비밀번호 수정 모달 열기
    function openPasswordModal() {
        document.getElementById('passwordModal').style.display = 'block';
    }

    // 비밀번호 수정 모달 닫기
    function closePasswordModal() {
        document.getElementById('passwordModal').style.display = 'none';
    }

    // 비밀번호 수정 확인
    function confirmPasswordUpdate() {
        const currentPassword = document.getElementById('currentPassword').value;
        const newPassword = document.getElementById('newPassword').value;
        const confirmPassword = document.getElementById('confirmPassword').value;

        // 비밀번호 수정 로직 추가
        if (newPassword === confirmPassword) {
            alert('비밀번호가 수정되었습니다.');
            closePasswordModal();
            closeInfoModal();
        } else {
            alert('비밀번호가 일치하지 않습니다.');
        }
    }

    // 반 삭제 모달 열기
    function openDeleteModal() {
        document.getElementById('deleteClassModal').style.display = 'block';
    }

    // 반 삭제 모달 닫기
    function closeDeleteModal() {
        document.getElementById('deleteClassModal').style.display = 'none';
    }

    // 반 삭제 확인
    function confirmDelete() {
        const deleteCode = document.getElementById('deleteCode').value;

		//서버에 요청
		
        // 반 삭제 로직 추가
        alert(`반 ${deleteCode}가 삭제되었습니다.`);
        closeDeleteModal();
        closeInfoModal();
    }

// 공지사항 추가 모달 열기
function openAddNoticeModal() {
    document.getElementById('addNoticeModal').style.display = 'block';
}


// 공지사항 추가 모달 닫기
function closeAddNoticeModal() {
    document.getElementById('addNoticeModal').style.display = 'none';
    clearNoticeForm();  // 입력값 초기화 (선택 사항)
}


// 공지사항 입력 폼 초기화
function clearNoticeForm() {
    document.getElementById('noticeAddTitle').value = '';
}   
    

// 공지사항 수정/삭제 모달 열기
function openDeleteNoticeModal(noticeTitle, ntNo) {
    document.getElementById("deleteNoticeModal").style.display = "block";
    
// 공지사항 제목을 모달의 입력 필드에 설정
document.getElementById("noticeTitle").value = noticeTitle;
    // 공지사항 번호 (NTno) 설정
    document.getElementById('noticeNo').value = ntNo;

}

// 공지사항 모달 닫기
function closeDeleteNoticeModal() {
    document.getElementById("deleteNoticeModal").style.display = "none";
}
    
    
    
    
// 공지사항 수정    
function confirmNoticeEdit() {
    const noticeNo = document.getElementById('noticeNo').value; // 모달에서 noticeNo 가져오기
    const noticeTitle = document.getElementById('noticeTitle').value; // 수정된 공지사항 제목

    // form 엘리먼트를 생성
    const form = document.createElement('form');
    form.method = 'POST';
    form.action = 'updateNotice'; // 수정 요청 URL

    // 공지사항 번호를 폼에 추가
    const noInput = document.createElement('input');
    noInput.type = 'hidden';
    noInput.name = 'noticeNo';
    noInput.value = noticeNo;

    // 수정된 제목을 폼에 추가
    const titleInput = document.createElement('input');
    titleInput.type = 'hidden';
    titleInput.name = 'noticeTitle';
    titleInput.value = noticeTitle;

    form.appendChild(noInput);
    form.appendChild(titleInput);

    // form을 문서에 추가하고 제출
    document.body.appendChild(form);
    form.submit();

    // 모달 닫기
    closeDeleteNoticeModal();
}






// 공지사항 삭제
function confirmNoticeDelete() {
    const noticeTitle = document.getElementById("noticeTitle").value;  // 삭제할 공지사항 제목

    // 삭제 요청을 위한 폼 생성
    const form = document.createElement('form');
    form.method = 'POST';
    form.action = 'deleteNotice'; // 삭제 요청 URL

    // 삭제할 제목을 폼에 추가
    const titleInput = document.createElement('input');
    titleInput.type = 'hidden';
    titleInput.name = 'noticeTitle';  // noticeTitle로 공지사항 제목 전송
    titleInput.value = noticeTitle;

    form.appendChild(titleInput);

    // form을 문서에 추가하고 제출
    document.body.appendChild(form);
    form.submit();

    // 모달 닫기
    closeDeleteNoticeModal();

}











    // 메모 추가 모달 열기
    function openAddMemoModal() {
        document.getElementById('addMemoModal').style.display = 'block';
    }

    // 메모 추가 모달 닫기
    function closeAddMemoModal() {
        document.getElementById('addMemoModal').style.display = 'none';
        clearMemoForm();
    }

    // 메모 추가 모달 입력 초기화
    function clearMemoForm() {
        document.getElementById('memoText').value = '';
    }

// 메모 추가
function submitMemo() {
    const memoText = document.getElementById('memoText').value;  // 입력된 메모 내용

    // 메모 내용이 비어있지 않으면 메모 추가
    if (memoText.trim() === '') {
        alert('메모 내용을 입력해 주세요.');
        return;
    }

    // 메모 추가 요청을 위한 폼 생성
    const form = document.createElement('form');
    form.method = 'POST';
    form.action = 'addMemo'; // 메모 추가 요청 URL

    // 메모 내용을 폼에 추가
    const memoContentInput = document.createElement('input');
    memoContentInput.type = 'hidden';
    memoContentInput.name = 'memoContent';  // 메모 내용 전송
    memoContentInput.value = memoText;

    form.appendChild(memoContentInput);

    // 폼을 문서에 추가하고 제출
    document.body.appendChild(form);
    form.submit();

    // 모달 닫기
    closeAddMemoModal();
}

// 메모 추가 모달 닫기
function closeAddMemoModal() {
    document.getElementById('addMemoModal').style.display = 'none';
}













    // 메모 수정 모달 열기
    function openModifyMemoModal(memoContent) {
        document.getElementById('modifyMemoModal').style.display = 'block';
        document.getElementById('modifyMemoText').value = memoContent;
    }

    // 메모 수정 모달 닫기
    function closeModifyMemoModal() {
        document.getElementById('modifyMemoModal').style.display = 'none';
    }

    // 메모 수정 내용 저장
    function submitModifiedMemo() {
        const modifiedMemoText = document.getElementById('modifyMemoText').value;
        
        // 수정 로직 추가 (예: 기존 메모 데이터 업데이트)
        alert(`메모 수정 완료:\n내용: ${modifiedMemoText}`);
        closeModifyMemoModal();
    }


    // 페이지 클릭 시 모달 닫기
    window.onclick = function(event) {
        if (event.target.className === 'modal')
        {
            closeInfoModal();
            closePasswordModal();
            closeDeleteModal();
            closeAddNoticeModal();
            closeAddMemoModal();
            closeModifyMemoModal();
            closeDeleteNoticeModal();
        }   
    }