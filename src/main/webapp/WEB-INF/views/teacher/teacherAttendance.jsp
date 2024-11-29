<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>학생 출결 페이지</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/teacher/teacherAttendance.css'/>">
</head>
<body>
   <jsp:include page="../common/teacher_menubar.jsp" />

   <div class="attendance-container">
        <div class="header">
            <span class="title">출석 리스트</span>
             <form id="attendanceForm" action="submitAttendance" method="post">
            <input type="date" class="date-picker" id="datePicker" name="aDate" readonly>
        </div>


        <table class="attendance-table">
            <thead>
                <tr>
                    <th>학생 아이디</th>
                    <th>이름</th>
                    <th>지각</th>
                    <th>결석</th>
                    <th>출석</th>
                </tr>
            </thead>
			<tbody>
			    <c:forEach var="attendance" items="${attendanceList}">
			        <tr>
			            <td>${attendance.stuId}</td>
			            <td>${attendance.stuName}</td>
			            <input type="hidden" name="studentId" value="${attendance.stuId}">
			            <td><input type="radio" name="attendance_${attendance.stuId}" value="TA"> 지각</td>
			            <td><input type="radio" name="attendance_${attendance.stuId}" value="AB"> 결석</td>
			            <td><input type="radio" name="attendance_${attendance.stuId}" value="AT" checked> 출석</td>
			        </tr>
			    </c:forEach>
			</tbody>
        </table>
        <button type="submit" class="save-button">저장</button>
    </form>
    
    <button type="button" class="update_button" onclick="updateAttendance()">수정</button>
   </div>

<script src="<c:url value='/resources/js/teacher/teacherAttendance.js'/>"></script>

</body>
</html>
