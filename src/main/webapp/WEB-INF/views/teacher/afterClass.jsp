<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="com.twoX.agit.member.model.vo.Teacher, com.twoX.agit.member.model.vo.AfterSchool, com.twoX.agit.member.model.vo.Teacher, com.twoX.agit.member.model.vo.Student"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	Teacher s = (Teacher) session.getAttribute("loginUser");
AfterSchool as = (AfterSchool) session.getAttribute("as");
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>teacher_after_school</title>
<link rel="stylesheet"
	href="<c:url value='/resources/css/menubar.css'/>">
<link rel="stylesheet"
	href="<c:url value='/resources/css/teacher/teacherAfterSchool.css'/>">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
	<jsp:include page="../common/teacher_menubar.jsp" />

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
					<c:forEach var="b" items="${list}">
						<a href="teacherAfterDetail.bo?bno=${b.boNo}">
							<div class="body_content">
								<div class="body_title">${b.title}</div>
								<div class="body_writer">${b.stuId}</div>
							</div>
							<hr class="body_line1">
						</a>
					</c:forEach>
					<div class="select_number">
						<c:choose>
							<c:when test="${ pi.currentPage eq 1 }">
								<button class="move-page" onclick="PreviousPage()">
									<img
										src="<c:url value='/resources/img/teacher/Polygon 4.png'/>">
								</button>
							</c:when>
							<c:otherwise>
								<button class="move-page" onclick="PreviousPageGo()">
									<img
										src="<c:url value='/resources/img/teacher/Polygon 4.png'/>">
								</button>
							</c:otherwise>
						</c:choose>

						<c:forEach var="p" begin="${ pi.startPage }" end="${ pi.endPage }">
							<c:choose>
								<c:when test="${ pi.currentPage eq p }">
									<a href="list.bo?cpage=${p}" class="now_page">${p}</a>
								</c:when>
								<c:otherwise>
									<a href="list.bo?cpage=${p}" style="color: #A98467;">${p}</a>
								</c:otherwise>
							</c:choose>
						</c:forEach>

						<c:choose>
							<c:when test="${ pi.currentPage eq pi.maxPage }">
								<button class="move-page" onclick="NextPage()">
									<img
										src="<c:url value='/resources/img/teacher/Polygon 5.png'/>">
								</button>
							</c:when>
							<c:otherwise>
								<button class="move-page" onclick="NextPageGo()">
									<img
										src="<c:url value='/resources/img/teacher/Polygon 5.png'/>">
								</button>
							</c:otherwise>
						</c:choose>
					</div>
				</div>

				<div class="notice">
					<div class="right_write_subject">학생리스트</div>
					<div class="right_write_content">
						<div class="right_write_content1">학년-반</div>
						<div class="right_write_content2">이름</div>
					</div>
					<hr class="head_line2">

					<c:forEach var="t" items="${studentList}">
						<div class="body_content1">
							<div class="body_title1">${t.classCode}</div>
							<div class="body_writer1">${t.stuName}</div>
						</div>
						<hr class="body_line2">
					</c:forEach>

					<div class="right_div_plus">
						<a href="#" class="plus_button" onclick="requestList()">승인요청+</a>
					</div>
				</div>
			</div>
		</div>

	</div>

	<!-- 모달 -->
	<div id="noticeModal" class="modal">
		<div class="modal-content">
			<span class="close" onclick="closeModal()">&times;</span>
			<p id="modalContent"></p>
			<div class="modal-name">승인요청</div>
			<div class="modal-content1" id="request-students">
				<div class="body_content2">
					<div class="body_class">학년-반</div>
					<div class="body_name">이름</div>
					<div class="body_check">승인</div>
				</div>
				<hr class="body_line3">
			</div>

		</div>
	</div>

	<script>
          
            // 모달 열기
           function requestList(){
				var modal = document.getElementById('noticeModal');
    			modal.style.display = "block";
			}
            
           //승인이 필요한 교직원 목록
           window.onload = function(){
           	// AJAX로 요청 보내기
           	$.ajax({
           		url: "acceptStudent", // 서버 URL
           		type: "GET", // GET 방식 요청
           		success: function(data) {
           			console.log(data);  // 데이터가 어떻게 반환되는지 확인
           			if (data && Array.isArray(data.acceptStudents)) {
           				acceptStudents(data.acceptStudents); // 승인이 필요한 선생님 목록을 화면에 표시
           			} else {
           				console.error('반환된 데이터가 예상한 배열 형식이 아닙니다.', data);
           			}
           		},
           		error: function(xhr, status, error) {
           			console.error('불러오는 중 오류 발생:', error);
           		}
           	});
           }
           
        // 승인 필요 선생님 목록을 화면에 표시
           function acceptStudents(studentOutList) {
               let str = "";
              
               
               // studentOutList가 배열인지 확인
               if (Array.isArray(studentOutList)) {
                   for (let tc of studentOutList) {
                       console.log('tc:', tc); 
                       console.log('status:', tc.status); 
                       
                       let classCode = tc.classCode;
                       let gradeClass = classCode.substring(9, 10) + " - " + classCode.substring(10, 12);

                       str +=  `<div class="modal-content1">` + 
                           `<div class="body_content3">` + 
                               `<div class="body_class1">` + gradeClass + `</div>` + 
                               `<div class="body_name1">` + tc.stuName + `</div>` +
                               `<div>
                               <input type="button" value="수락" onclick="studentRequest('` + tc.stuId + `', '` + tc.status + `')"
                                   style="width: 100px; height: 30px; background: #DDE5B6; border-radius: 10px; border: none; color: #A98467; font-size: 15px; font-weight: 800; cursor: pointer;">
                               </div>` +
                           `</div>` +  
                           `<hr class="body_line4">`;   
                       
                        
                   }
               } else {
                   console.error('studentOutList는 배열이 아닙니다:', studentOutList);
               }

               const element = document.getElementById('request-students');
               element.innerHTML += str; // 'request-teachers' 요소에 HTML 추가
           }
        
         
          

          //교직원 승인
          function request(stuId, status){
          	console.log("request js | 승인할 선생님의 stuId : " + stuId);
          	console.log("request js | 승인할 선생님의 status : "+ status);

          	$.ajax({
          		url: "requestStudent",
          		type: "POST",
          		data: {stuId : stuId, status : status},
          		success: function(response){
          			if(response === "success"){
          				alert("승인 완료");
          				location.reload();
          			}else if(response === "fail"){
          				alert("승인 실패");
          			}else if(response === "올바르지 않은 요청입니다."){
          				alert(response)
          			}
          		},
          		error: function(){
          			alert("요청 작업 실패");
          			location.reload();
          		}
          	});
          }
          

       // 교직원 승인 여부 모달 열기
       function studentRequest(stuId, status){
    	   console.log('stuId:', stuId); 
    	    console.log('status:', status); // status를 확인
    	    
    	    if(status === 'N'){
    	        if(confirm(`승인하시겠습니까?`)){
    	            request(stuId, 'Y');
    	        }    
    	    } else{
    	    	 alert(`올바르지 않은 요청입니다. 받은 status: ${status}`);// 정확한 status 값도 알 수 있음
    	    }
    	}

            // 모달 닫기
            function closeModal() {
                const radios = document.querySelectorAll('input[type="radio"]');
                radios.forEach((radio) => {
                    radio.checked = false;  // 모든 라디오 버튼 초기화
                });
                document.getElementById('noticeModal').style.display = 'none';
            }

           

        // 공지사항 길이 제한
        function limitTextLength() {
            var noticeContent = document.getElementById("noticeContent");
            var maxLength = 100;
            if (noticeContent.value.length > maxLength) {
                noticeContent.value = noticeContent.value.substring(0, maxLength);
            }
        }

        // 이전 페이지 이동
        function PreviousPageGo() {
            let currentPage = ${pi.currentPage};
            if (currentPage > 1) {
                location.href = 'list.bo?cpage=' + (currentPage - 1);
            }
        }

        // 이전 페이지 클릭 시 아무것도 안하게 하기
        function PreviousPage() {
            event.preventDefault();
        }

        // 다음 페이지 이동
        function NextPageGo() {
            let currentPage = ${pi.currentPage};
            if (currentPage < ${pi.maxPage}) {
                location.href = 'list.bo?cpage=' + (currentPage + 1);
            }
        }

        // 다음 페이지 클릭 시 아무것도 안하게 하기
        function NextPage() {
            event.preventDefault();
        }
    </script>
</body>
</html>