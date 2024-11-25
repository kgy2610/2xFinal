
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>교직원-숙제 수정/삭제하기</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/teacher/teacherhomework_modify.css'/>">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css"/>
    
    <!-- Summernote CSS CDN -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-lite.min.css" rel="stylesheet">
    
</head>
<body>
<jsp:include page="../common/teacher_menubar.jsp" />
   
        <div class="whole_body">
        	<form action="updateHomework" method="POST">

            <div class="use-body">
                <div class="subject-name"> <input type="hidden" name="hmTitle" value="${hmTitle}">${hmTitle}</div>
		        <select name="subject" class="subject-select">
		            <option value="국어" ${subject eq '국어' ? 'selected' : ''}>국어</option>
		            <option value="수학" ${subject eq '수학' ? 'selected' : ''}>수학</option>
		            <option value="과학" ${subject eq '과학' ? 'selected' : ''}>과학</option>
		            <option value="영어" ${subject eq '영어' ? 'selected' : ''}>영어</option>
		        </select>
    		</div>
            <div class="real-body">
                <div class="real-head">
                    <div class="head-real-date" id="selectedDate" name="deadLine" value="${deadLine}">${deadLine}</div>
                    <hr>
                    <img src="<c:url value='/resources/img/teacher/free-icon-calendar-check-7955802 4.png'/>" id="dateIcon" style="cursor: pointer;" class="img-calendar"/>
        
                    <input type="text"  name="deadLine" id="datepicker" style="display:none;" />
                </div>
                <hr class="line1">

                <div id="enroll_content">
                    <textarea id="en_content" name="hmContent">${hmContent}</textarea>
                </div>

                <div class="file-attachment">
                    <label for="file-upload" class="custom-file-upload">파일 첨부</label>
                    <span class="file-name" id="file-name">선택된 파일 없음</span>
                    <input type="file" id="file-upload" class="file-upload">
                </div>
            </div>
            
            <div class="real-foot">
                <button class="foot-first">목록으로</button>
                <button type="submit" class="foot-second">수정</button>
            </form>
                
			<!-- 삭제 버튼을 포함한 폼 -->
			<form method="POST" action="deleteHomework">
			    <input type="hidden" name="hmTitle" value="${hmTitle}">
			    <button type="submit" class="foot-third">삭제</button> 
			</form>

            </div>
            <div id="chat_button"><img src="img/message.png"></div>
        	</div> 
    
        <!-- jQuery 및 기타 스크립트 -->
    <script src="https://cdn.jsdelivr.net/npm/jquery/dist/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/moment/min/moment.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-lite.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/lang/summernote-ko-KR.min.js"></script>
    <script src="<c:url value='/resources/js/teacher/modifyhomework.js'/>"></script>
   </body>
</html>
