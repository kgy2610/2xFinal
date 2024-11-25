<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AGIT</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/enrollForm_teacher.css'/>">
    <link rel="stylesheet" href="<c:url value='/resources/css/enrollForm_teacher-modal.css'/>">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <div id="enrollForm-box">
        <div id="enroll-logo" onclick="redirectToLogin()" style="cursor: pointer;">
            <img src="<c:url value='/resources/img/logo.png'/>">
        </div>
        <div id="enroll-bar">
            선생님 회원가입
        </div>

        <div id="enrollForm-form">
            <form action="enrollForm.teacher" method="post">
                <div id="enroll-type">
                    <input type="radio" id="teacher" name="choice" value="teacher" checked onclick="toggleVerification()">
                    <label for="teacher">선생님</label>
                    <input type="radio" id="manager" name="choice" value="manager" onclick="toggleVerification()">
                    <label for="manager">관리자</label>
                </div>
                <input type="hidden" id="status" name="status" value="N">
                <div id="enroll-find">
                    <p>학교찾기</p>
                    <input type="text" placeholder="학교코드 입력" autofocus id="scCode" name="scCode">
                    <input type="button" value="학교찾기" onclick="openSchoolModal()">
                </div>
                <div id="enroll-name">
                    <p>이름</p>
                    <input type="text" placeholder="이름을 입력하세요." autofocus id="tcName" name="tcName">
                </div>
                <div id="enroll-id">
                    <p>아이디</p>
                    <input type="text" placeholder="아이디를 입력하세요." autofocus id="tcId" name="tcId" required>
                    <input type="button" value="중복확인" id="checkBtn">
                </div>
                <div id="enroll-pwd">
                    <p id="enroll-pwd-p">비밀번호</p>
                    <input type="password" placeholder="비밀번호를 입력하세요." autofocus id="tcPwd" name="tcPwd">
                </div>
                <div id="enroll-pwd">
                    <p>비밀번호 확인</p>
                    <input type="password" placeholder="이름을 입력하세요." autofocus id="checkPwdTea" name="checkPwdTea">
                </div>
                <div id="enroll-phone">
                    <p>전화번호</p>
                    <input type="text" placeholder="(-)제외하고 입력 ex)01011112222" autofocus id="phone" name="phone">
                </div>
                <!-- 본인 확인 질문 및 답변 (기본적으로 숨김 처리) -->
                <div id="enroll-Q" style="display: none;">
                    <p>본인 확인 질문</p>
                    <select name="tcQuestion">
                        <option value="event" id="event" selected>학교에서 가장 기억에 남는 행사는?</option>
                        <option value="place" id="place">학교에서 자주 방문하는 곳은 어디인가요?</option>
                        <option value="bestFood" id="bestFood">학교 근처 자주 찾는 식당이나 장소는?</option>
                        <option value="firstBuy" id="firstBuy">첫 월급으로 산 물건은 무엇인가요?</option>
                        <option value="travel" id="travel">가장 기억에 남는 여행지는 어디인가요?</option>
                    </select>
                </div>
                <div id="enroll-A" style="display: none;">
                    <p>답변</p>
                    <input type="text" placeholder="비밀번호 찾기에 사용됩니다. 잘 기억해주세요!" id="tcAnswer" name="tcAnswer">
                </div>
                <div id="enroll-btn">
                    <input type="submit" value="회원가입" onclick="return handleSubmit()" id="submitButton">
                </div>
            </form>
        </div>

		<!-- 학교검색 모달창 -->
		<div id="noticeModal" class="modal">
		    <div class="modal-content">
		        <span class="close" onclick="closeModal()">&times;</span>
		        <div id="school-con">
		            <div id="school-search">
		                <div id="school-info">
		                    <div id="schoolName-search">
		                        <input type="text" placeholder="학교명을 입력하세요" id="school-name">
		                    </div>
		                </div>
		                <div id="schoolSearch-btn">
		                    <input type="button" value="검색" onclick="searchSchools()">
		                </div>
		            </div>
		        </div>
		        <div id="school-list">
						<table id="school-NameList">
							<tr>
								<th>학교명</th>
								<th>주소</th>
							</tr>
	
						</table>
					</div>
		    </div>
		</div>
		</div>


		<script src="resources/js/school-select.js"></script>
        <script src="resources/js/school-modal.js"></script>
        <script src="<c:url value='/resources/js/form_teacher.js'/>"></script>
        <script>
	        function redirectToLogin() {
	            window.location.href = '<c:url value="/login"/>'; // login 컨트롤러로 이동
	        }
    	</script>
    </div>
</body>
</html>