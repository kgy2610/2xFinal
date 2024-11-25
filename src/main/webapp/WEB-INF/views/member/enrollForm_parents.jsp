<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AGIT</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link rel="stylesheet" href="<c:url value='/resources/css/enrollForm.css'/>">
    <link rel="stylesheet" href="<c:url value='/resources/css/enrollForm_parents-modal.css'/>">
    <script src="<c:url value='/resources/js/enrollForm_parents_api.js'/>"></script>
</head>
<body>
    <div id="enrollForm-box">
        <div id="enroll-logo" onclick="redirectToLogin()" style="cursor: pointer;">
            <img src="<c:url value='/resources/img/logo.png'/>">
        </div>
        <div id="enroll-bar">
            부모님 회원가입
        </div>

        <div id="enrollForm-form">
            <form action="enroll.parents" method="POST">
                <div id="enroll-name">
                    <p>이름</p>
                    <input type="text" placeholder="이름을 입력하세요." id="pr_name" name="prName" required autofocus>
                </div>
                <div id="enroll-nickname">
                    <p>닉네임</p>
                    <input type="text" placeholder="닉네임을 입력하세요." id="pr_nickname" name="nickname" required autofocus>
                </div>
                <div id="enroll-id">
                    <p>아이디</p>
                    <input type="text" id="pr_id" name="prId" placeholder="자녀아이디 검색" readonly required>
                    <input type="button" value="자녀아이디 검색" onclick="openChildIdModal()">
                </div>
                <div id="enroll-pwd">
                    <p id="enroll-pwd-p">비밀번호</p>
                    <input type="password" placeholder="비밀번호를 입력하세요." id="pr_pwd" name="prPwd" autofocus required>
                </div>
                <div id="enroll-pwd">
                    <p>비밀번호 확인</p>
                    <input type="password" placeholder="비밀번호를 다시 입력하세요." autofocus required>
                </div>
                <div id="enroll-phone">
                    <p>전화번호</p>
                    <input type="text" id="phone" name="phone" placeholder="(-)제외하고 입력 ex)01011112222" autofocus required>
                </div>
                <div id="enroll-Q">
                    <p>본인 확인 질문</p>
                    <select name="prQuestion">
                        <option value="entrance">아이가 입학할 때 기억에 남던 순간은?</option>
                        <option value="travel">가장 기억에 남는 여행지는?</option>
                        <option value="subject">가장 좋아했던 과목은?</option>
                        <option value="weather">결혼식날 날씨는 어땠나요??</option>
                        <option value="pet">키웠던 반려동물의 이름은?</option>
                    </select>
                </div>
                <div id="enroll-A">
                    <p>답변</p>
                    <input type="text" id="pr_answer" name="prAnswer" placeholder="비밀번호 찾기에 사용됩니다. 잘 기억해주세요!" required>
                </div>
                <div id="enroll-btn">
                    <input type="submit" value="회원가입">
                </div>
            </form>
        </div>
    </div>

    <!-- 모달 -->
    <div id="noticeModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeModal()">&times;</span>
            <div id="kid-search">
                <div id="kid-info">
                    <div id="kid-name">
                        <p>자녀 이름</p>
                        <input type="text" id="child_name">
                    </div>
                    <div id="kid-phone">
                        <p>전화번호</p>
                        <input type="text" id="phone_num" placeholder="(-)제외한 자녀 전화번호 입력">
                    </div>
                </div>
                <div id="kidSearch-btn">
                    <input type="button" onclick="searchPrId()" value="검 색">
                </div>
            </div>
            <div id="kid-list">
                <table id="child_list">
                    <tr>
                        <th>자녀 이름</th>
                        <th>반 / 번호</th>
                        <th>아이디 선택</th>
                    </tr>
                </table>
            </div>
        </div>
    </div>

    <script>
        // 자녀 아이디 검색 모달 열기
        function openChildIdModal() {
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
        function inputPrId(element){
        	var prid = element.innerText;
        	console.log(prid);
        	const inputId = document.getElementById('pr_id');
        	inputId.value = prid;
        	document.getElementById('noticeModal').style.display = 'none';
        }

    </script>
    <script>
        function redirectToLogin() {
            window.location.href = '<c:url value="/login"/>'; // login 컨트롤러로 이동
        }
    </script>
</body>
</html>