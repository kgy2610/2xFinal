<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.twoX.agit.member.model.vo.Teacher"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>선생님 방과후 개설</title>
    
    <link rel="stylesheet" href="<c:url value='/resources/css/teacher/madeAfterSchool.css'/>">
	
	<script src="<c:url value='/resources/js/teacher/makeAfterClass.js'/>"></script>
</head>
<body>
    <jsp:include page="../common/teacher_menubar.jsp" />

    <div class="whole_body">
        <img src="<c:url value='/resources/img/teacher/free-sticker-keyboard-12140857.png'/>">
        <h1>개설한 방과후가 없습니다.</h1>

        <form id="classForm" action="makeAfterClass.me" method="post">
            <div class="form">
                <div class="input-row">
                    <span class="label">반 이름&nbsp;&nbsp;&nbsp;</span>
                    <input type="text" id="grade1" name="className" class="input-field" maxlength="10" placeholder="방과후 반 이름을 입력하세요." required>
                </div>
                <div class="input-row">
                    <span class="label">반 설명&nbsp;&nbsp;&nbsp;</span>
                    <input type="text" id="description1" name="explanation" class="input-field" maxlength="20" placeholder="반 설명을 입력하세요." required>
                </div>
                <div class="button-row">
                    <input type="text" id="class-code1" name="code" class="input-field" placeholder="참여코드를 입력하세요." required>
                    <button class="add-button" type="submit">+</button>
                </div>
            </div>
        </form>
    </div>
</body>
</html>