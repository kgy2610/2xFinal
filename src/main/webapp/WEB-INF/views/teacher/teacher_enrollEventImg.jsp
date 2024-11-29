<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    
    <!-- Summernote CSS/JS CDN -->
    
    <script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-lite.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/lang/summernote-ko-KR.min.js"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-lite.min.css" rel="stylesheet">

    <title>parents_enrollcommunity</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/parents/parents_enrollcommunity.css'/>">
</head>
<body>
    <jsp:include page="../common/teacher_menubar.jsp" />
    <style>
    	*{
    		overflow:visible;
    	}
    </style>
    <div id="content_border">
    	<form action="enroll_IMG" method="POST">
        <div id="enroll_title" ><input type="text" id="en_title" name="title" placeholder="제목을 입력해주세요" required></div>
        <div id="enroll_content">
            <textarea id="en_content" name="boContent" style="overflow:visible;"></textarea>
        </div>
        <button type = "submit" id="create_button">등록하기</button>
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
        
        $(document).ready(function(){
            $('#en_content').summernote({
            height: 620,                 // 에디터 높이
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
                url: "upload",
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