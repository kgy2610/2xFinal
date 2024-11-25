<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>선생님조회</title>
<link rel="stylesheet" href="<c:url value='/resources/css/searchTeacher.css'/>">
<link rel="stylesheet" href="<c:url value='/resources/css/searchTeacher-modal.css'/>">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="<c:url value='/resources/js/manager/manager_modal.js'/>"></script>
</head>
<body>
	<div id="searchTeacher-con">
		<div id="top-bar">
			<div class="bar-con searchName">
				<input type="text" placeholder="이름 검색" id="teacher-name" onkeyup="filter()"> <img
					src="<c:url value='/resources/img/searchImg.png'/>">
			</div>
			<div class="bar-con btn-con requestList" onclick="requestList()">
				<input type="button" value="승인요청 목록"> <img
					src="<c:url value='/resources/img/requestList.png'/>">
			</div>
			<div class="bar-con btn-con logoutBtn">
				<a href="logout.me"><input type="button" value="로그아웃"></a>
			</div>
		</div>
		<div id="teacher-list">
			<table>
				<tr>
					<th>이름</th>
					<th>고유코드</th>
					<th>삭제</th>
				</tr>
				<c:forEach var="teacher" items="${teachers}" varStatus="status">
					<tr class="teacher-list-detail">
						<td class="teacherName">${teacher.tcName}</td>
						<td class="teacherClassCode">
						  <c:choose>
							<c:when test="${teacher.classCode == '0000'}">개설된 반이 없습니다</c:when>
							<c:otherwise>${teacher.classCode}</c:otherwise>
						  </c:choose>
						</td>
						<td>
						  <input type="button" value="삭제" onclick="teacherDelete('${teacher.tcId}','${teacher.tcName}')">
						</td>
					  </tr>
				</c:forEach>
			</table>
		</div>

		<!-- 승인 리스트 모달 -->
		<div id="listModal" class="modal">
			<div class="modal-list">
				<span class="close" onclick="closeModal('listModal')">&times;</span>
				<div id="requestList-con">
					<div id="request-p">
						<p>승인요청</p>
					</div>
					<div id="request-list">
						<table id="request-teachers">
							<tr>
								<th>이름</th>
								<th>전화번호</th>
								<th>승인</th>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>

		
	</div>
</body>
</html>