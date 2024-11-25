
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>agit</title>
    <link rel="stylesheet"
	href="<c:url value='/resources/css/student/student_afterschoolStart.css'/>">
</head>
<body>
    <jsp:include page="../common/student_menubar.jsp" />

    <div class="wrap">
      <img src="<c:url value='/resources/img/student/afterschoolStart.png'/>" alt="">
      <h4>현재 참여중인 방과후가 없습니다</h4>
      <div class="center">
      <form action="afterschoolStart.stu" method="post">
      <input type="hidden" name="stuId" id="stuId" value="${loginUser.stuId}" />
         <input type="text" placeholder="참여 코드를 입력하세요" id="code" name="code">
         <button class="add-button"  type="submit">+</button>
      </form>
      </div>
    </div>

</body>
</html>
