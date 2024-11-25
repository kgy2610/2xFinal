document.addEventListener("DOMContentLoaded", function() {
    // 비밀번호 확인 함수
    function checkPwd() {
        const pwd = document.querySelector("#stuPwd").value; // 비밀번호 필드
        const pwdCheck = document.querySelector("#stuPwdCheck").value; // 비밀번호 확인 필드

        // 비밀번호가 일치하지 않으면 경고창을 띄우고 false 반환
        if (pwd !== pwdCheck) {
            alert("비밀번호가 일치하지 않습니다.");
            return false;
        }
        return true; // 비밀번호가 일치하면 true 반환
    }

    // 폼 제출 처리
    function handleSubmit(event) {
        // 비밀번호 확인이 일치하지 않으면 폼 제출을 막음
        if (!checkPwd()) {
            event.preventDefault(); // 폼 제출을 막음
        }
    }

    // 폼 제출 버튼 클릭 시 handleSubmit 함수 실행
    const form = document.querySelector('form');
    const submitButton = document.querySelector("#submitButton"); // 제출 버튼

    if (form) {
        form.addEventListener('submit', handleSubmit);
    }

    console.log("form.js가 성공적으로 로드되었습니다.");
});

// 아이디 중복확인
$(function(){
    const idInput = document.querySelector("#enrollForm-form input[name=stuId]");
    const checkButton = document.querySelector("#checkBtn"); // 중복확인 버튼
    const submitButton = document.querySelector("#submitButton"); // 제출 버튼

    checkButton.onclick = function() { // 중복확인 버튼 클릭 시
        const str = idInput.value.trim();
        if (str.length >= 5) { // 아이디가 5자 이상일 때만 요청
            $.ajax({
                url: "idCheck.me",
                data: { checkId: str },
                success: function(result) {
                    if (result === "NNNNN") {
                        alert("이미 사용중인 아이디입니다.");
                        submitButton.disabled = true; // 이미 사용중인 아이디일 때 회원가입 버튼 비활성화
                        idInput.style.borderColor = 'red';
                    } else {
                        alert("사용 가능한 아이디입니다.");
                        submitButton.disabled = false; // 사용 가능한 아이디일 때 회원가입 버튼 활성화
                        idInput.style.borderColor = '#6C584C';
                    }
                },
                error: function() {
                    console.log("아이디 중복체크 ajax 실패");
                }
            });
        } else {
            alert("아이디는 5자 이상이어야 합니다.");
            submitButton.disabled = true; // 아이디가 5자 미만이면 회원가입 버튼 비활성화
        }
    };
});