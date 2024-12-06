<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList,com.twoX.agit.board.model.vo.HmSubmit,com.twoX.agit.member.model.vo.Homework,com.twoX.agit.common.vo.PageInfo"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	ArrayList<Homework> homeworkList = (ArrayList)session.getAttribute("homeworkList");
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/3.5.0/chart.min.js"></script>
    <title>student_homeworkCheck</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    
    <!-- Summernote CSS/JS CDN -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-lite.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/lang/summernote-ko-KR.min.js"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-lite.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value='/resources/css/student/student_homeworkConfirm.css'/>"/>
    <link rel="stylesheet" href="<c:url value='/resources/css/menubar.css'/>">
	<script src="<c:url value='/resources/js/student/student_homework_confirm.js'/>"></script>
    <script>
            const deadLine = new Date("${npage.deadLine}");
    </script>
</head>
<body>
	<jsp:include page="../common/student_menubar.jsp" />
	<style>
       *{
          overflow: visible;
       }
    </style>
    <div id="content_border">
        <div id="homework_area">
            <div id="enroll_title">${npage.hmTitle}</div>
            <div id="subject_info">${npage.subject}</div>
            <div id="enroll_content" >
                <div id="enroll_info">마감일 | ${npage.deadLine}</div>
                <div id="en_content">${npage.hmContent}</div>
                <div id="enroll_file">
					<c:choose>
		            	<c:when test="${not empty npage.originName }">
		            	<span>첨부된 파일 : </span><a id="downloadLink" href="<c:url value='${npage.changeName}'/>" download="${npage.originName }">${npage.originName }</a>
		            	</c:when>
		            	<c:otherwise>
								<span id="fileName" class="file-name">올라온 파일이 없습니다</span>
							</c:otherwise>
		            </c:choose>
				</div>
            </div>
            <div id="submit_area">
                <div id="submit_homework">
                    <div style="background-color: white;">${npage.hmStuContent}</div>
                </div>
                <button class="submit_button" id="modify_button"  onclick="location.href='hmAnswer_modify?boNo=${npage.boNo}&cpage=${cpage}'">수정</button>
            </div>
            <div id="teacher_confirm">${npage.tcComment}</div>
            <div id="homework_score">
                <div>점수</div>
                <div>${npage.score}</div>
                <div>점</div>
            </div>
            <button class="create_button" onclick="location.href='homework?capge=${cpage}'">목록으로</button>
        </div> 
    </div>
</body>
</html>
