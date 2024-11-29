<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>교직원-숙제 등록하기</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/teacher/teacherhomework_modify.css'/>">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css"/>
    
    
    <!-- Summernote CSS CDN -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-lite.min.css" rel="stylesheet">

</head>
<body>

		<jsp:include page="../common/teacher_menubar.jsp" />
   
        <div class="whole_body">
       
            <form action="enrollHomework" method="POST" enctype="multipart/form-data">
	            <div class="use-body">
	                <div class="subject-name"><input type="text" name="title"></div>
		                <select class="subject-select" name="subject">
		                    <option value="국어">국어</option>
		                    <option value="수학">수학</option>
		                    <option vlaue="과학">과학</option>
		                    <option vlaue="영어">영어</option>
		                </select>
	            </div>
	            
	            <div class="real-body">
	                <div class="real-head">
	                    <div class="head-real-date" id="selectedDate" >마감일</div>
	                    <hr>
	                    <img id="dateIcon" style="cursor: pointer;" class="img-calendar" src="<c:url value='/resources/img/teacher/free-icon-calendar-check-7955802 4.png'/>">
	        
	                    <input type="text" name="dueDate"  id="datepicker" style="display:none;" />
	                </div>
	                <hr class="line1">
	
	                <div id="enroll_content">
	                    <textarea name="content" id="en_content"></textarea>
	                </div>
	
	                <div class="file-attachment">
	                    <label for="file-upload" class="custom-file-upload">파일 첨부</label>
	                    <span class="file-name" id="file-name">선택된 파일 없음</span>
	                    <input type="file" id="file-upload" class="file-upload" name="upfile">
	                </div>
	            </div>
	            
	            <div class="real-foot">
	                <div></div>
	                <div></div>
	                <div></div>
	                <div></div>
	                <div></div>
	                <div></div>
	                <div></div>
	                <div></div>
	                <button type="submit" class="foot-first">등록</button>
	                <div></div>
	                <div></div>
	                <div></div>
	                <div></div>
	                <div></div>
	                <div></div>
	                <div></div>
	                <div></div>
	                <div></div>
	            </div>
        </div>
    </form>
     
     
    <!-- jQuery 및 기타 스크립트 -->
    <script src="https://cdn.jsdelivr.net/npm/jquery/dist/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/moment/min/moment.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-lite.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/lang/summernote-ko-KR.min.js"></script>

    <script src="<c:url value='/resources/js/teacher/addhomework.js'/>"></script>

</body>
</html>