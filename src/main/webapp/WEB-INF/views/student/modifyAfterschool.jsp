<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-lite.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/lang/summernote-ko-KR.min.js"></script>
    <title>parents_modifycommunity</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/student/student_updateAfterschool.css'/>">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-lite.min.css" rel="stylesheet">
    <script src="<c:url value='/resources/js/student/student_afterschool.js'/>"></script>
</head>
<body>
    <jsp:include page="../common/student_menubar.jsp" />
    <style>
    	*{
    		overflow: visible;
    	}
    </style>
    <div id="content_border">
    	<form action="update.afterSt" method="POST" enctype="multipart/form-data">
    		<div id="enroll_title" ><input type="text" id="en_title" placeholder="제목을 입력해주세요" value="${npage.title }" name="title"></div>
	        <div id="enroll_content" >
	        	<input type="hidden" name = "boNo" value = "${npage.boNo}">
	            <textarea id="en_content" name="boContent">${npage.boContent }</textarea>
	            <div id="enroll_file">
	                <input type="file" id="fileInput" name = "reupfile" style="display: none;" onchange="showFileName()">
	                <label for="fileInput" class="custom-file-upload">
	                    파일 선택
	                </label>
	             <c:choose>
						<c:when test="${not empty npage.originName }">
                       		<span id="fileName" class="file-name">${npage.originName}</span>
                       	</c:when>
                       	<c:otherwise>
							<span id="fileName" class="file-name">선택된 파일이 없습니다</span>
						</c:otherwise>
                </c:choose>
	            </div>
	        </div>
	        <button type="submit" class="create_button" id="modify_button">수정하기</button>
	        <button class="create_button" id="delete_button" onclick="openDeleteModal(event)">삭제하기</button>
    	</form>
    	
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
    <script>
        document.querySelectorAll(".text-limit").forEach(function (element) {
            const text = element.innerText;
            if (text.length > 3) {
                element.innerText = text.substring(0, 3) + "...";
            }
        });

        document.querySelectorAll(".title-limit").forEach(function (element) {
            const text = element.innerText;
            if (text.length > 15) {
                element.innerText = text.substring(0, 12) + "...";
            }
        });

        function showFileName() {
            const fileInput = document.getElementById("fileInput");
            const fileName = document.getElementById("fileName");

            if (fileInput.files.length > 0) {
                fileName.textContent = fileInput.files[0].name;
            } else {
                fileName.textContent = "선택된 파일이 없습니다";
            }
        }
        
        $(function(){
            $('#en_content').summernote({
            height: 535,                 // 에디터 높이
            minHeight: null,
            maxHeight: null,
            lang: "ko-KR",
            resizable: false,
            toolbar: [
                ['style', ['style']],
                ['font', ['bold', 'underline', 'clear']],
                ['color', ['color']],
                ['para', ['ul', 'ol', 'paragraph']],
                ['table', ['table']],
                ['insert', ['link', 'picture', 'video']],
                ['view', ['help']]
            ],
            callbacks: {
	        	onImageUpload: fileUpload
	        }
            });
        })
        function fileUpload(files){
            //썸머노트는 이미지를 추가하면 해당 이미지파일을 전달해준다.
            //callbacks에 onImageUpload를 작성하지 않을경우 자동으로 이미지를 string으로 변환하여 준다.
            //callbacks에 onImageUpload를 작성할 경우 해당 이미지 경로를 직접 작성해 주어야 한다.

            //파일업로드 할 때는 form태그에서 encType을 multipart/form-data형식으로 
            //요청했던 것처럼 js객체에 FormData객체를 이용해서 ajax요청을 전달해준다.

            const fd = new FormData();
            for(let file of files){
                fd.append("fileList",file);
            }

            insertFile(fd,function(nameList){
                for(let name of nameList){
                	
                    $("#en_content").summernote("insertImage","/agit/resources/img/board/"+name)
                }
            })
        }
        function insertFile(data, callback){
            $.ajax({
                url: "afterupload",
                type: "POST",
                data: data,
                processData: false, //기본이 true -> 전송하는 data를 string으로 변환해서 요청
                contentType: false, //
                dataType: "json", //받을 때 타입
                success: function(res){
                    callback(res)
                },
                error: function(){
                    console.log("파일업로드 api요청 실패")
                }
            })
        }
        const enTitleInput = document.getElementById("en_title");

        // 포커스가 되었을 때 placeholder 숨기기
        enTitleInput.addEventListener("focus", function() {
            enTitleInput.setAttribute("data-placeholder", enTitleInput.getAttribute("placeholder"));
            enTitleInput.setAttribute("placeholder", "");
        });

        // 포커스가 해제되었을 때 원래 placeholder 복원
        enTitleInput.addEventListener("blur", function() {
            enTitleInput.setAttribute("placeholder", enTitleInput.getAttribute("data-placeholder"));
        });
    </script>
</body>
</html>