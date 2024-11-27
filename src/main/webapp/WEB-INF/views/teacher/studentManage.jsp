<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>teacher_menubar</title>
<link rel="stylesheet"
	href="<c:url value='/resources/css/menubar.css'/>">
<link rel="stylesheet"
	href="<c:url value='/resources/css/teacher/studentManage.css'/>">
</head>
<body>
	<jsp:include page="../common/teacher_menubar.jsp" />
	<div class="whole_body">
		<div class="use-body">
			<div class="content1">
				<div class="head-subject">
					<div class="student-list">학생리스트</div>
					<button class="plus-button" onclick="openModal(this)">+</button>
				</div>
				<div class="real-list">
					<div class="notice">
						<div class="list-information">
							<div>번호</div>
							<div>이름</div>
							<div>출석률</div>
						</div>
						<hr class="line1">

						<c:forEach var="g" items="${stuManageList}">
							<div class="real-list-information">
								<div>${g.STUNUM}</div>
								<div>${g.STUNAME}</div>
								<div>${g.ATTENDANCERATE}</div>
							</div>
							<hr class="line2">
						</c:forEach>
					</div>
				</div>
			</div>
			<div class="content2">
				<div class="content2-con1">
					<div class="student-information">학생정보</div>
					<div class="real-student-information">
						<div>이름 : 신서희</div>
						<div>전화번호 : 010-1111-2222</div>
						<div>출석률 : 80%</div>
						<div class="cancel">승 인 취 소</div>
					</div>
				</div>
				<div class="content2-con2">
					<div class="grade-information">성적</div>
					<div class="notice1">
						<div class="real-grade">
							<div>제목</div>
							<div>점수</div>
						</div>
						<hr class="line3">
						<div class="real-grade-information">
							<div>국어 수행평가</div>
							<div>80점</div>
						</div>
						<hr class="line4">
					</div>
				</div>
			</div>
			<div id="noticeModal" class="modal">
				<div class="modal-content">
					<span class="close" onclick="closeModal()">&times;</span>
					<div class="modal-title">승인요청</div>
					<p id="modalContent">
					<div class="num-name-check">
						<div>번호</div>
						<div>이름</div>
						<div>승인</div>
					</div>
					<hr class="line5">
					<div class="real-num-name-check">
						<div>1</div>
						<div>신서희</div>
						<div>
							<div class="radio-label">
								<label> <input type="radio" name="contact" value="email" />
								</label>
							</div>
						</div>
					</div>
					<hr class="line6">
					<button class="save">저장</button>
					</p>
					<p id="modalDate"></p>
				</div>
			</div>

		</div>
		<div id="chat_button">
			<img src="<c:url value='/resources/img/student/message.png'/>">
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

		document.getElementById('noticeModal').style.display = 'block';
	}

	// 모달 닫기
	function closeModal() {
		document.getElementById('noticeModal').style.display = 'none';
	}

	// 공지사항 길이 제한
	document.querySelectorAll(".text-limit").forEach(function(element) {
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