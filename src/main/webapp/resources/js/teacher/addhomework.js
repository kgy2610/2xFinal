
 $(document).ready(function() {
            // Date Range Picker 초기화
            $('#datepicker').daterangepicker({
                singleDatePicker: true,
                showDropdowns: true,
                autoUpdateInput: true,
                autoApply: true,
                startDate: moment(),
                locale: {
                    format: 'YYYY-MM-DD'
                },
                drops: 'up',
                opens: 'center'
            }, function(start) {
                $('#selectedDate').text(start.format('YYYY-MM-DD'));
            });

            // img 클릭 시 달력 열기 및 위치 조정
            $('#dateIcon').on('click', function() {
                $('#datepicker').focus();
                setTimeout(function() {
                    var iconPosition = $('#dateIcon').offset();
                    var iconHeight = $('#dateIcon').outerHeight();
                    $('.daterangepicker').css({
                        top: iconPosition.top + iconHeight + 10 + "px",
                        left: iconPosition.left + "px"
                    });
                }, 10);
            });

            // Summernote 초기화
            $('#en_content').summernote({
                height: 300,
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
        });

        function fileUpload(files) {
            const fd = new FormData();
            for(let file of files) {
                fd.append("fileList", file);
            }

            insertFile(fd, function(nameList) {
                for(let name of nameList) {
                    $("#en_content").summernote("insertImage", "/img/" + name)
                }
            });
        }

        function insertFile(data, callback) {
            $.ajax({
                url: "upload",
                type: "POST",
                data: data,
                processData: false,
                contentType: false,
                dataType: "json",
                success: function(res) {
                    callback(res)
                },
                error: function() {
                    console.log("파일업로드 api요청 실패")
                }
            });
        }

        // 파일 선택 시 파일명을 표시하는 JavaScript 코드
        document.getElementById('file-upload').addEventListener('change', function() {
            const fileName = this.files[0] ? this.files[0].name : "선택된 파일 없음";
            document.getElementById('file-name').textContent = fileName;
        });
