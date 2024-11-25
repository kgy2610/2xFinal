<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AGIT</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/enrollForm.css'/>">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <div id="enrollForm-box">
        <div id="enroll-logo" onclick="redirectToLogin()" style="cursor: pointer;">
            <img src="<c:url value='/resources/img/logo.png'/>"">
        </div>
        <div id="enroll-bar">
            학생 회원가입
        </div>

        <div id="enrollForm-form">
            <form action="enrollForm.student" method="post">
                <div id="enroll-name">
                    <p>이름</p>
                    <input type="text" placeholder="이름을 입력하세요." autofocus id="stuName" name="stuName" required>
                </div>
                <div id="enroll-id">
                    <p>아이디</p>
                    <input type="text" placeholder="아이디를 입력하세요." autofocus id="stuId" name="stuId" required>
                    <input type="button" value="중복확인" id="checkBtn">
                </div>
                <div id="enroll-pwd">
                    <p id="enroll-pwd-p">비밀번호</p>
                    <input type="password" placeholder="비밀번호를 입력하세요." autofocus id="stuPwd" name="stuPwd" required>
                </div>
                <div id="enroll-pwd">
                    <p>비밀번호 확인</p>
                    <input type="password" placeholder="작성한 비밀번호를 입력하세요." autofocus id="stuPwdCheck" name="stuPwdCheck" required >
                </div>
                <div id="enroll-phone">
                    <p>전화번호</p>
                    <input type="text" placeholder="(-)제외하고 입력 ex)01011112222" autofocus id="phone" name="phone" required>
                </div>
                <div id="enroll-Q">
                    <p>본인 확인 질문</p>
                    <select name="stuQuestion">
                        <option value="subject">내가 가장 좋아하는 과목은?</option>
                        <option value="area">내가 태어난 도시는?</option>
                        <option value="travel">가장 기억에 남는 여행 장소는?</option>
                        <option value="animal">가장 좋아하는 동물 이름은?</option>
                        <option value="character">가장 좋아하는 캐릭터는?</option>
                    </select>
                </div>
                <div id="enroll-A">
                    <p>답변</p>
                    <input type="text" placeholder="비밀번호 찾기에 사용됩니다. 잘 기억해주세요!" id="stuAnswer" name="stuAnswer" required>
                </div>
                <div id="enroll-btn">
                    <input type="submit" value="회원가입" onclick="return handleSubmit()" id="submitButton">
                </div>
            </form>
        </div>
    </div>
    
    <script>
        function redirectToLogin() {
            window.location.href = '<c:url value="/login"/>'; // login 컨트롤러로 이동
        }
    </script>
    <script src="<c:url value='/resources/js/form_student.js'/>"></script>
    
</body>
</html>