
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <title>AGIT</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/find_id-pwd.css'/>">
    <link rel="stylesheet" href="<c:url value='/resources/css/find_success.css'/>">
    <script src="<c:url value='/resources/js/find_id_api.js'/>"></script>
    <script src="<c:url value='/resources/js/find_pwd_api.js'/>"></script>
    <script src="<c:url value='/resources/js/modify_pwd_api.js'/>"></script>
</head>
<body>
    <div id="find-box">
        <div id="find-info">
            <div id="find-logo" onclick="redirectToLogin()" style="cursor: pointer;">
                <img src="<c:url value='/resources/img/logo.png'/>">
            </div>
            <div id="find-memo">
                아이디 / 비밀번호 찾기
            </div>
        </div>
        <div class="tab_content">
            <input type="radio" name="findInfo" id="findId" checked>
            <label for="findId">아이디 찾기</label>
        
            <input type="radio" name="findInfo" id="findPwd">
            <label for="findPwd">비밀번호 찾기</label>
        
            <div class="conbox con1">
                <form action="find_ID" id="findId-form" method="POST">
                    <div id="findId-name">
                        <p>이름</p>
                        <input type="text" placeholder="이름을 입력하세요" id="prName" required>
                    </div>
                    <div id="findId-phone">
                        <p>전화번호</p>
                        <input type="text" placeholder="(-)제외하고 입력 ex)01011112222" id="phone" required>
                    </div>
                    <div id="findId-btn">
                        <input type="button" onclick="findIdAjax()" value="아이디 찾기">
                    </div>
                </form>
            </div>
            <div class="conbox con2">
                <form action="" id="findPwd-form" method="POST">
                    <div id="findPwd-id">
                        <p>아이디</p>
                        <input type="text" id="prId" placeholder="아이디를 입력하세요" required>
                    </div>
                    <div id="findPwd-question">
                        <p>보안질문</p>
	                    <select id="prQuestion">
	                        <option value="subject">내가 가장 좋아하는 과목은?</option>
	                        <option value="area">내가 태어난 도시는?</option>
	                        <option value="travel" selected>가장 기억에 남는 여행 장소는?</option>
	                        <option value="animal">가장 좋아하는 동물 이름은?</option>
	                        <option value="character">가장 좋아하는 캐릭터는?</option>
	                        <option value="event" id="event">학교에서 가장 기억에 남는 행사는?</option>
	                        <option value="place" id="place">학교에서 자주 방문하는 곳은 어디인가요?</option>
	                        <option value="bestFood" id="bestFood">학교 근처 자주 찾는 식당이나 장소는?</option>
	                        <option value="firstBuy" id="firstBuy">첫 월급으로 산 물건은 무엇인가요?</option>
	                        <option value="friend">가장 좋아했던 어린 시절 친구의 이름은 무엇인가요?</option>
	                        <option value="food">가장 좋아하는 음식이나 음료는 무엇인가요?</option>
	                        <option value="sport">처음 배운 스포츠나 활동은 무엇인가요?</option>
	                        <option value="weather">결혼식날 날씨는 어땠나요??</option>
	                        <option value="music">가장 좋아하는 음악 장르는 무엇인가요?</option>
	                    </select>
                    </div>
                    <div id="findPwd-answer">
                        <p>답변</p>
                        <input type="text" id="prAnswer" placeholder="답변을 입력해주세요" required>
                    </div>
                    <div id="findPwd-btn">
                        <input type="button" onclick="findPwdAjax()" value="비밀번호 찾기">
                    </div>
                </form>
            </div>
        </div>
    </div>
    
    <script>
        function redirectToLogin() {
            window.location.href = '<c:url value="/login"/>'; // login 컨트롤러로 이동
        }
        function reloadPwd() {
            // 새로 고침 전에 로컬 스토리지에 값 저장
            localStorage.setItem('reloadAfter', 'true');
            window.location.reload();
        }

        // 페이지가 로드된 후 실행되는 코드
        window.onload = function() {
            // 새로 고침 후에만 실행되도록 체크
            if (localStorage.getItem('reloadAfter') === 'true') {
                var radioButton = document.getElementById('findPwd');
                if (radioButton) {
                    radioButton.checked = true;
                }

                // 새로 고침 후 처리 완료되었으므로 로컬 스토리지에서 값 삭제
                localStorage.removeItem('reloadAfter');
            }
        };
    </script>
</body>
</html>
