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
            height: 580,                 // 에디터 높이
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
            callbacks:{
                onImageUpload: fileUpload
                }
            //placeholder: '최대 2048자까지 쓸 수 있습니다'	//placeholder 설정
            
            });
            $('#create_button').click(function() {
                    var markup = $('#en_content').summernote('code'); // HTML 형식의 내용 가져오기
                    console.log(markup); // 콘솔에 출력
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
                    $("#en_content").summernote("insertImage","/img/"+name)
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
        
        
        //모달
		function openDeleteModal(event) {
    	event.preventDefault(); // 버튼의 기본 동작(폼 제출)을 막음
    	document.getElementById('deleteModal').style.display = 'flex';
		}
		
		function closeDeleteModal() {
		    document.getElementById('deleteModal').style.display = 'none';
		}
		
		// 외부 클릭 시 모달 닫기
		window.onclick = function (event) {
	    const deleteModal = document.getElementById('deleteModal');

	    if (event.target == deleteModal) {
	        closeDeleteModal();
	    }
	    }
	

