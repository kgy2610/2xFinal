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
	href="<c:url value='/resources/css/student/student_afterschool.css'/>">
</head>
<body>
    <jsp:include page="../common/student_menubar.jsp" />

    <div class="wrap">
      <div class="header">
         <div class="header_left">
            <h4>${className}</h4>
            <p>${explanation}</p>
            <p>${tcName}선생님</p>
         </div>
         <img src="<c:url value='/resources/img/student/afterschool.png'/>" alt="">
      </div>
      
      <div class="list">
         <h4>내가 쓴 글(활동기록)</h4>
         <button>글쓰기</button>
         <table>
            <thead>
               <tr>
                  <th>번호</th>
                  <th>제목</th>
                  <th>작성일</th>
               </tr>
            </thead>
            <tbody>
               <tr>
                  <td>1</td>
                  <td>오늘 배운 내용</td>
                  <td>2024-11-04</td>
               </tr>
               <tr>
                  <td>1</td>
                  <td>오늘 배운 내용</td>
                  <td>2024-11-04</td>
               </tr>
               <tr>
                  <td>1</td>
                  <td>오늘 배운 내용</td>
                  <td>2024-11-04</td>
               </tr>
               <tr>
                  <td>1</td>
                  <td>오늘 배운 내용</td>
                  <td>2024-11-04</td>
               </tr>
               <tr>
                  <td>1</td>
                  <td>오늘 배운 내용</td>
                  <td>2024-11-04</td>
               </tr>
               <tr>
                  <td>1</td>
                  <td>오늘 배운 내용</td>
                  <td>2024-11-04</td>
               </tr>
             
            </tbody>
         </table>
      </div>
    </div>
</body>
</html>