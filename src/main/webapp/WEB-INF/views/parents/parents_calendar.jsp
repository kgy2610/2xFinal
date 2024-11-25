<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="true" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>agit</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/parents/parents_calendar.css'/>">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script src="<c:url value='/resources/js/parents/calendar.js'/>"></script>
</head>

<body>
	<jsp:include page="parents_menubar.jsp" />

    <div class="wrap">
        <div class="header">
            <div>
                2024년
                <select id="monthSelect"></select>월
            </div>
        </div>

        <br />

        <table align="center" width="500" style="text-align: center" id="calendar">
            <thead>
                <tr>
                    <th>일</th>
                    <th>월</th>
                    <th>화</th>
                    <th>수</th>
                    <th>목</th>
                    <th>금</th>
                    <th>토</th>
                </tr>
            </thead>
            <tbody id="cal">
                <!-- 날짜(js) -->
            </tbody>
        </table>
        
    </div>
    <!-- 날짜 저장용 숨겨진 필드 추가 -->
    <input type="hidden" id="date">

    <!-- 모달 -->
    <div id="modal" class="modal">
        <div class="modal-content">
            <form action="updateCounsel">
                <span class="close" id="closeBtn">&times;</span>
                <input type="hidden" id="counselNo" name="csNo" required/>
                <table>
                    <tr>
                        <td>상담 내용</td>
                        <td><input type="text" id="adviceContent" name="csContent" placeholder="원하시는 상담 내용을 간략히 적어주세요." required/></td>
                    </tr>
                </table>
                <button type="submit" id="saveBtn">신청</button>
            </form>
        </div>
    </div>

</body>
</html>