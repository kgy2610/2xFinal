
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="com.twoX.agit.member.model.vo.Teacher ,com.twoX.agit.member.model.vo.AfterSchool"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	Teacher s = (Teacher) session.getAttribute("loginUser"); 

	AfterSchool as = (AfterSchool)session.getAttribute("as");
	%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>teacher_after_school</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/menubar.css'/>">
     <link rel="stylesheet" href="<c:url value='/resources/css/teacher/teacherAfterSchool.css'/>">
   
</head>
<body>

    <div class="nav">
       <img src="<c:url value='/resources/img/logo.png'/>">
        <div class="menu">
            <label for="mypage"><a href="#">마이페이지</a></label>
            <label for="homework"><a href="#">숙제</a></label>
            <label for="attendance"><a href="#">출결</a></label>
            <label for="community"><a href="#">방과후 반</a></label>
            <label for="advicePlan" ><a href="#" class="choosePage">상담일정</a></label>           
            <label for="photo"><a href="#">행사사진</a></label>
            <label for="studentManage"><a href="#">학생관리</a></label>
        </div>
        <div class="undermenu">
            <label for="info"><a href="#">정보수정</a></label>
            <label for="logout"><a href="#">로그아웃</a></label>
        </div>
    </div>
    <div class="whole_body">
        <div class="use_body">
            <div class="top_div">
                <div>${as.className}</div>
                <div class="top_in_div">
                <div>${as.explanation}</div>
                </div>
            </div>
            <div class="bottom_container">
                <div class="bottom_left_div">
                    <div class="left_write_subject">학생글 조회</div>
                    <div class="left_write_content">
                    <div class="left_write_content1">제목</div> 
                    <div class="left_write_content2">작성자</div>
                    </div>
                    <hr class="head_line1">
                    <!-- <div class="body_content"> - 이거 자체가 제목, 작성자, 아래 선까지 의미
                        <div class="body_title">오늘은 우리 가족을 그렸어요!</div>
                        <div class="body_writer">신서희</div>
                    </div>
                    <hr class="body_line"> -->
                    <div class="body_content">
                        <div class="body_title">오늘은 우리 가족을 그렸어요!</div>
                        <div class="body_writer">신서희</div>
                    </div>
                    <hr class="body_line1">
                    <div class="body_content">
                        <div class="body_title">오늘은 우리 가족을 그렸어요!</div>
                        <div class="body_writer">신서희</div>
                    </div>
                    <hr class="body_line1">
                    <div class="body_content">
                        <div class="body_title">오늘은 우리 가족을 그렸어요!</div>
                        <div class="body_writer">신서희</div>
                    </div>
                    <hr class="body_line1">
                    <div class="body_content">
                        <div class="body_title">오늘은 우리 가족을 그렸어요!</div>
                        <div class="body_writer">신서희</div>
                    </div>
                    <hr class="body_line1">
                    <div class="body_content">
                        <div class="body_title">오늘은 우리 가족을 그렸어요!</div>
                        <div class="body_writer">신서희</div>
                    </div>
                    <hr class="body_line1">
                        <div class="select_number">
                            <button class="move-page"> <img src="<c:url value='/resources/img/teacher/Polygon 4.png'/>"></button>
                            <a href="#" class="now_page" class="numbera">1</a>
                            <a href="#" class="numbera">2</a>
                            <a href="#" class="numbera">3</a>
                            <a href="#" class="numbera">4</a>
                            <a href="#" class="numbera">5</a>
                            <button class="move-page"> <img src="<c:url value='/resources/img/teacher/Polygon 5.png'/>"></button>
                           
                        </div>
                </div>
                <div class="notice">
                    <div class="right_write_subject">학생리스트</div>
                    <div class="right_write_content">
                    <div class="right_write_content1">반-번호</div> 
                    <div class="right_write_content2">이름</div>
                    </div>
                    <hr class="head_line2">
                    <div class="body_content1">
                        <div class="body_title1">1-5</div>
                        <div class="body_writer1">신서희</div>
                    </div>
                    <hr class="body_line2">
                    <div class="body_content1">
                        <div class="body_title1">1-5</div>
                        <div class="body_writer1">신서희</div>
                        
                    </div>
                    <hr class="body_line2">
                    <div class="body_content1">
                        <div class="body_title1">1-5</div>
                        <div class="body_writer1">신서희</div>
                    </div>
                    <hr class="body_line2">
                    <div class="body_content1">
                        <div class="body_title1">1-5</div>
                        <div class="body_writer1">신서희</div>
                    </div>
                    <hr class="body_line2">
                    <div class="body_content1">
                        <div class="body_title1">1-5</div>
                        <div class="body_writer1">신서희</div>
                    </div>
                    <hr class="body_line2">
                    <div class="body_content1">
                        <div class="body_title1">1-5</div>
                        <div class="body_writer1">신서희</div>
                    </div>
                    <hr class="body_line2">
                    <div class="right_div_plus">
                        <a href="#" class="plus_button" onclick="openModal(this)">승인요청 +</a>
                    </div>  
                </div>
               
            </div>
        </div>
        <div id="chat_button"><img src="<c:url value='/resources/img/teacher/message.png'/>"></div>
    </div>

    <!--모달-->
    
    <div id="noticeModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeModal()">&times;</span>
            <p id="modalContent">
                <div class="modal-name">승인요청</div>
                <div class="modal-content1">
                    <div class="body_content2">
                        <div class="body_class">반-번호</div>
                        <div class="body_name">이름</div>
                        <div class="body_check">승인</div>
                    </div>
                    <hr class="body_line3">
                </div>
                <div class="modal-content1">
                    <div class="body_content3">
                        <div class="body_class1">1-4</div>
                        <div class="body_name1">신서희</div>
                            <div class="radio-label">
                                <label>
                                    <input type="radio" name="contact" value="email"/>
                                </label>
                            </div>
                    </div>
                    <hr class="body_line4">
                </div>
                <div class="modal-content1">
                    <div class="body_content3">
                        <div class="body_class1">1-4</div>
                        <div class="body_name1">신서희</div>
                        <div class="radio-label">
                            <label>
                                <input type="radio" name="contact" value="email"/>
                            </label>
                        </div>
                    </div>
                    <hr class="body_line4">
                </div>
                <div class="modal-content1">
                    <div class="body_content3">
                        <div class="body_class1">1-4</div>
                        <div class="body_name1">신서희</div>
                        <div class="radio-label">
                            <label>
                                <input type="radio" name="contact" value="email"/>
                            </label>
                        </div>
                    </div>
                    <hr class="body_line4">
                </div>
                <div class="modal-content1">
                    <div class="body_content3">
                        <div class="body_class1">1-4</div>
                        <div class="body_name1">신서희</div>
                        <div class="radio-label">
                            <label>
                                <input type="radio" name="contact" value="email"/>
                            </label>
                        </div>
                    </div>
                    <hr class="body_line4">
                </div>
                <div class="modal-content1">
                    <div class="body_content3">
                        <div class="body_class1">1-4</div>
                        <div class="body_name1">신서희</div>
                        <div class="radio-label">
                            <label>
                                <input type="radio" name="contact" value="email"/>
                            </label>
                        </div>
                    </div>
                    <hr class="body_line4">
                </div>
                <div class="modal-content1">
                    <div class="body_content3">
                        <div class="body_class1">1-4</div>
                        <div class="body_name1">신서희</div>
                        <div class="radio-label">
                            <label>
                                <input type="radio" name="contact" value="email"/>
                            </label>
                        </div>
                    </div>
                    <hr class="body_line4">
                </div>
                <div class="modal-content1">
                    <div class="body_content3">
                        <div class="body_class1">1-4</div>
                        <div class="body_name1">신서희</div>
                        <div class="radio-label">
                            <label>
                                <input type="radio" name="contact" value="email"/>
                            </label>
                        </div>
                    </div>
                    <hr class="body_line4">
                </div>
                <div class="modal-content1">
                    <div class="body_content3">
                        <div class="body_class1">1-4</div>
                        <div class="body_name1">신서희</div>
                        <div class="radio-label">
                            <label>
                                <input type="radio" name="contact" value="email"/>
                            </label>
                        </div>
                    </div>
                    <hr class="body_line4">
                </div>
            </p>
          
            <button class="save">저장</button>
           
            <p id="modalDate"></p>
        </div>
    </div>
  
</body>
<script>
    // 모달 열기
        function openModal(element) {
        var fullText = element.getAttribute('data-full-text');
        var date = element.getAttribute('data-date');

        document.getElementById('modalContent').textContent = fullText;
        document.getElementById('modalDate').textContent = date;

        // 라디오 버튼 그룹 선택
        var radioButtons = document.getElementsByName('myRadioGroup'); // 라디오 버튼의 name 속성 값 사용
        var isAnyChecked = Array.from(radioButtons).some(radio => radio.checked);

        // 라디오 버튼 중 아무것도 선택되지 않았다면 require 속성 추가
        if (!isAnyChecked) {
            radioButtons.forEach(radio => radio.setAttribute('required', 'required'));
        } else {
            radioButtons.forEach(radio => radio.removeAttribute('required'));
        }

        document.getElementById('noticeModal').style.display = 'block';
    }
    
            // 모달 닫기
            function closeModal() {
                document.getElementById('noticeModal').style.display = 'none';
            }
    
            // 공지사항 길이 제한
            document.querySelectorAll(".text-limit").forEach(function (element) {
                const text = element.innerText;
                if (text.length > 20) {
                    element.innerText = text.substring(0, 20) + " ...";
                }
            });
    
            // 모달 밖 클릭 시 닫기
            window.onclick = function(event) {
                var modal = document.getElementById('noticeModal');
                if (event.target == modal) {
                    closeModal();
                }
            }
           
    </script>
    
</html>
