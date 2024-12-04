<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.twoX.agit.member.model.vo.Student" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%  String alertMsg = (String)session.getAttribute("alertMsg");
	Student s = (Student)session.getAttribute("child");
	String classCode = s.getClassCode();	

 	
%>
<!DOCTYPE html>
<html lang="ko">

<head>
   <meta charset="UTF-8">
   <meta name="viewport" content="width=device-width, initial-scale=1.0">
   <title>agit</title>
   <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
   <script src="<c:url value='/resources/js/parents/modify_parents_info_api.js'/>"></script>
   <script src="<c:url value='/resources/js/parents/modify_parents_pwd_api.js'/>"></script>
   <script src="<c:url value='/resources/js/parents/search_notice_list_api.js'/>"></script>
   <script src="<c:url value='/resources/js/parents/search_meal_list_api.js'/>"></script>
   <script src="<c:url value='/resources/js/parents/search_status_api.js'/>"></script>
   <link rel="stylesheet" href="<c:url value='/resources/css/parents/parents_myPage.css'/>">
</head>

<body>
	<jsp:include page="parents_menubar.jsp" />

   <!--배너-->
	

   <!--마이페이지-->
   <div class="wrap">
   	<%if(classCode==null){ %>
   	     <img src="<c:url value='/resources/img/parents/free-sticker-science-12775329.png'/>" style="width:400px; height:400px; position: absolute; top:20%; left:50%;">
      	 <div style="position: absolute; top:600px; left:880px;"><h2>자녀분의 반이 아직 배정되지 않았습니다.</h2></div>
   	<%}else{ %>
      <!--왼쪽전체-->
      <div class=".contents-left">
         <div class="mypage">
			<%String grade = classCode.substring(9,10);
			  String child_class = classCode.substring(10,12); 
				if (child_class.split("")[0].equals("0")){ 
				child_class = child_class.split("")[1];
			} %>
            <p><%=grade %>학년 <%=child_class %>반 ${child.stuName}</p>
            <p>담임선생님 : ${teacher.tcName}</p>
            <p>선생님 연락처 : ${teacher.phone}</p>

            <div class="present">
               <div>
                  <p>출석률</p>
                  <p id="attendance_rate"></p>
               </div>
               <div>
                  <p>현재 출석 상태</p>
                  <p id="now_status"></p>
               </div>
            </div>
         </div>
         
         <div class="notice">
         	<p>공지사항</p>
            <hr>
            <table id="notice_table"></table>
         </div>
      </div>
      <!--오른쪽 전체 급식페이지-->
      <div class="contents-right">
      </div>
   <%} %>
   </div>

</body>
<script>
	  window.onload = function(){
	   		searchMealList();
	   		searchNoticeList();
	   		searchNowStatus();
	  }
	  
</script>
</html>