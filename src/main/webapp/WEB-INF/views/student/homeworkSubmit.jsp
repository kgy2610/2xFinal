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
    <title>student_homeworkSubmit</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    
    <!-- Summernote CSS/JS CDN -->
    
    <script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-lite.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/lang/summernote-ko-KR.min.js"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-lite.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value='/resources/css/student/student_homeworkSubmit.css'/>"/>
    <script src="<c:url value='/resources/js/student/student_homework_detail.js'/>"></script>
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
			<div id="enroll_content">
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

				<form action="enroll.homework_student" method="post">
					<div id="submit_area">
						<div id="submit_homework">
							<textarea id="sb_homework" name="hmStuContent" style="background-color: white;">${napge.hmStuContent}</textarea>
						</div>
				<input type="hidden" name="boNo" value="${npage.boNo}">
				<input type="hidden" name="classCode" value="${npage.classCode}">
				<button class="submit_button" id="submit_button">제출</button>
					</div>
				</form>
			<button class="create_button" onclick="location.href='homework?capge=${cpage}'">목록으로</button>
        </div> 
        
    </div>
</body>
</html>
