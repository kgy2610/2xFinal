<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%  String alertMsg = (String)session.getAttribute("alertMsg");%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AGIT</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
   
    
    <!-- Summernote CSS/JS CDN -->
    
    <script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-lite.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/lang/summernote-ko-KR.min.js"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-lite.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value='/resources/css/student/student_modifyafterschool.css'/>">
    <script src="<c:url value='/resources/js/student/student_afterschool.js'/>"></script>
</head>
<body>

    <jsp:include page="../common/student_menubar.jsp" />
    
    <style>
    	*{
    		overflow:visible;
    	}
    </style>
    	 <div id="content_border">
      <input type="hidden" name="boNo" value="${npage.boNo}" />
        <div id="enroll_title" ><input type="text" id="en_title" value="${npage.title}" name="title"></div>
        <div id="enroll_content" >
            <textarea id="en_content" name="boContent">${npage.boContent}</textarea>
            <div id="enroll_file">
            	<c:choose>
            		<c:when test="${not empty npage.originName }">
            		<span>첨부된 파일 : </span><a id="downloadLink" href="<c:url value='${npage.changeName}'/>" download="${npage.originName }">${npage.originName }</a>
            		</c:when>
            		<c:otherwise>
							<span id="fileName" class="file-name">선택된 파일이 없습니다</span>
						</c:otherwise>
            	</c:choose>
              
            </div>
        </div>
        <button class="create_button" id="re_button" onclick="location.href='afterSchool?cpage=${cpage}'">목록으로</button>
        <button class="create_button" id="modify_button"  onclick="location.href='after_modify?boNo=${npage.boNo}&cpage=${cpage}'">수정하기</button>
        <button class="create_button" id="delete_button" onclick="openDeleteModal(event)">삭제하기</button>
    </div>
    
    <!-- 글 삭제 모달 -->
    <div id="deleteModal" class="modal">
    <form method="post" action="delete.afterSt">
    <div class="modal-content">
    <input type="hidden" name="boNo" value="${npage.boNo}" />
    <h2>" ${npage.title} "</h2><br>
    <h2>글을 삭제하시겠습니까?</h2>
    <br><br>
    <span class="close" onclick="closeDeleteModal()">&times;</span>
    <button type="submit">삭제하기</button>
    </div>
    </form>
    </div>
    
   
    <div id="chat_button"><img src="img/message.png"></div>
</body>
</html>