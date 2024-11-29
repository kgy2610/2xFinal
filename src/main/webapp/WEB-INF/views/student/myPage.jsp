<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.twoX.agit.member.model.vo.Student"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
   String alertMsg = (String) session.getAttribute("alertMsg");

    Student loginUser = (Student)session.getAttribute("loginUser");

    String classCode = loginUser.getClassCode();
    String grade = classCode.substring(9, 10); 
    String classNum = classCode.substring(10, 12);
    
%>
<!DOCTYPE html>
<html lang="en">

<head>
   <meta charset="UTF-8">
   <meta name="viewport" content="width=device-width, initial-scale=1.0">
   <title>agit</title>
   <link rel="stylesheet"
   href="<c:url value='/resources/css/student/student_myPage.css'/>">
   <script src="<c:url value='/resources/js/student/student_myPage.js'/>"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>

<body>
   <jsp:include page="../common/student_menubar.jsp" />

   <div class="wrap">
      <div class="banner">
         <img src="<c:url value='/resources/img/student/myPageBanner.png'/>">
         <div class="scheduledraw">
         </div>
      </div>

      <div class="student_myPage">
         <!-- 이미지 전체를 클릭하면 모달 열기 -->
         <div onclick="openImageSelectModal()">
             <img src="<c:url value='/resources/img/student/${loginUser.picNo}.png'/>">
         </div>
         
         <h4>${schoolName}</h4>
         <p> <%= grade %> 학년 <%= classNum %>반 ${loginUser.stuName}</p>
         <p>담임선생님 : ${teacherName.tcName} 선생님</p>
     </div>

      <div class="food">
         <img src="<c:url value='/resources/img/student/studentBook.png'/>">
         <div class="food_left">
            <!-- 서버로부터 받아온 시작일자 관련 데이터 -->
         </div>
         <div class="food_right">
            <!-- 서버로부터 받아온 종료일자 관련 데이터 -->
         </div>
      </div>

   </div>

   <!-- 정보수정모달 -->
   <!-- 정보수정 모달 -->
   <div id="updateModal" class="modal2">
   <form method="post" action="student.update" id="infoForm">
      <div class="modal-content2">
         <h2 id="modalTitle">정보 수정</h2>
         <span class="close" onclick="closeUpdateModal()">&times;</span>
         <div id="modalForm">
            <!-- 기본 정보 수정 폼 -->
               <table>
                  <tr>
                     <td>코드</td>
                     <td><input type="text" value="${loginUser.classCode}" readonly></td>
                  </tr>
                  <tr>
                     <td colspan="2">
                        <div>
                           <input type="text" value="<%= grade %>" readonly> 학년
                           <input type="text" value="<%= classNum %>"readonly> 반
                           <input type="text" value="${loginUser.stuNum}" id="updateNum" name="updateNum"> 번
                        </div>
                     </td>
                  </tr>
                  <tr>
                     <td>아이디</td>
                     <td><input type="text" value="${loginUser.stuId}" readonly></td>
                  </tr>
               </table>
               <button type="submit">수정하기</button>
           <hr>
     
            </form>
            
            <!-- 비밀번호 수정 폼 (기본적으로 숨김) -->
            <form method="post" action="student.upadatePwd" id="pwdForm">
               <table>
                  <tr>
                     <td>기존 비밀번호</td>
                     <td><input type="password" placeholder="기존 비밀번호를 입력하세요"></td>
                  </tr>
                  <tr>
                     <td>수정 비밀번호</td>
                     <td><input type="password" placeholder="수정할 비밀번호를 입력하세요" id="updatePwd" name="updatePwd"></td>
                  </tr>
                  <tr>
                     <td>비밀번호 확인</td>
                     <td><input type="password"></td>
                  </tr>
               </table>
               <button type="submit">비밀번호 수정하기</button>
              
            </form>
         </div>
      </div>
   </div>

   <!-- 이미지 선택 모달 -->
   <div id="imageSelectModal" class="modal3">
      <div class="modal-content3">
         <span class="close" onclick="closeImageSelectModal()">&times;</span>
          <h2>이미지 선택</h2>
          <form action="imgselect" method="post">
          <div class="image-grid">
               <img src="<c:url value='/resources/img/student/1.png'/>" alt="이미지1" onclick="selectImage(this)" id="1">
              <img src="<c:url value='/resources/img/student/2.png'/>" alt="이미지2" onclick="selectImage(this)" id="2">
              <img src="<c:url value='/resources/img/student/3.png'/>" alt="이미지3" onclick="selectImage(this)" id="3">
              <img src="<c:url value='/resources/img/student/4.png'/>" alt="이미지4" onclick="selectImage(this)" id="4">
              <img src="<c:url value='/resources/img/student/5.png'/>" alt="이미지5" onclick="selectImage(this)" id="5">
              <img src="<c:url value='/resources/img/student/6.png'/>" alt="이미지6" onclick="selectImage(this)" id="6">
              <img src="<c:url value='/resources/img/student/7.png'/>" alt="이미지7" onclick="selectImage(this)" id="7">
              <img src="<c:url value='/resources/img/student/8.png'/>" alt="이미지8" onclick="selectImage(this)" id="8">
              <img src="<c:url value='/resources/img/student/9.png'/>" alt="이미지9" onclick="selectImage(this)" id="9">
               <input type="hidden" id="selectedImageId" name="picNo">
          </div>
          <button type="submit" onclick="applyImageChange()">변경하기</button>
          </form>
      </div>
  </div>


</body>
<script>
     window.onload = function(){
            searchMealList();
            getAirStatus();
           }
</script>
</html>