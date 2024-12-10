<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>학생 출결 페이지</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/teacher/teacherAttendance.css'/>">
</head>
<body>
    <jsp:include page="../common/teacher_menubar.jsp" />

    <div class="attendance-container">
        <div class="header">
            <span class="title">출석부</span>
            <form id="attendanceForm" action="submitAttendance" method="post">
                <input type="date" class="date-picker" id="datePicker" name="aDate" >
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
            <tbody class="tableBody">
                <c:forEach var="attendance" items="${attendanceList}">
                    <tr>
                        <td>${attendance.stuId}</td>
                        <td>${attendance.stuName}</td>
                        <input type="hidden" name="studentId" value="${attendance.stuId}">

                        <!-- 출석 상태가 TeacherAttendanceList에 있는지 확인 -->
                        <c:set var="attendanceStatus" value=""/> <!-- 기본값을 '출석'으로 설정 -->
                        
                        <!-- TeacherAttendanceList에서 해당 학생의 출석 상태 찾기 -->
                        <c:forEach var="teacherAttendance" items="${TeacherAttendanceList}">
                            <c:if test="${attendance.stuId == teacherAttendance.STU_ID}">
                                <c:set var="attendanceStatus" value="${teacherAttendance.LA}"/> <!-- 'TA', 'AB', 'AT' 값으로 변경 -->
                            </c:if>
                        </c:forEach>

                        <!-- 각 출석 상태에 맞는 라디오 버튼을 체크 -->
                        <td><label>
                            <input type="radio" name="attendance_${attendance.stuId}" value="TA" ${attendanceStatus == 'TA' ? 'checked' : ''}> 지각
                        </label></td>
                        <td><label>
                            <input type="radio" name="attendance_${attendance.stuId}" value="AB" ${attendanceStatus == 'AB' ? 'checked' : ''}> 결석
                        </label></td>
                        <td><label>
                            <input type="radio" name="attendance_${attendance.stuId}" value="AT" ${attendanceStatus == 'AT' ? 'checked' : ''}> 출석
                        </label></td>
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
