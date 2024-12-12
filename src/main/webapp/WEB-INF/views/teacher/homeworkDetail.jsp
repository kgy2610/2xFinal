<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>교직원-숙제 수정/삭제하기</title>
    
    <link rel="stylesheet" href="<c:url value='/resources/css/teacher/teacherhomework_modify.css'/>">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css"/>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-lite.min.css" rel="stylesheet">
    
    <script src="https://cdn.jsdelivr.net/npm/jquery/dist/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/moment/min/moment.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-lite.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/lang/summernote-ko-KR.min.js"></script>
    <script src="<c:url value='/resources/js/teacher/modifyhomework.js'/>"></script>
</head>
<body>
    <jsp:include page="../common/teacher_menubar.jsp" />
    <style>
       * {
          overflow:visible;
       }
    </style>

    <div class="whole_body">
        <form action="updateHomework" method="POST" enctype="multipart/form-data">
            <div class="use-body">
                <!-- 숙제 제목 -->
                <div class="subject-name">
                    <input type="text" name="hmTitle" value="${detailHomework[0].hmTitle}">
                </div>
                <!-- 과목 선택 -->
                <select name="subject" class="subject-select">
                    <option value="국어" ${detailHomework[0].subject eq '국어' ? 'selected' : ''}>국어</option>
                    <option value="수학" ${detailHomework[0].subject eq '수학' ? 'selected' : ''}>수학</option>
                    <option value="영어" ${detailHomework[0].subject eq '영어' ? 'selected' : ''}>영어</option>
                    <option value="과학" ${detailHomework[0].subject eq '과학' ? 'selected' : ''}>과학</option>
                    <option value="사회" ${detailHomework[0].subject eq '사회' ? 'selected' : ''}>사회</option>
                    <option value="미술" ${detailHomework[0].subject eq '미술' ? 'selected' : ''}>미술</option>
                    <option value="체육" ${detailHomework[0].subject eq '체육' ? 'selected' : ''}>체육</option>
                </select>
            </div>

            <div class="real-body">
                <div class="real-head">
                    <!-- 마감일 -->
                    <div class="head-real-date" id="selectedDate" name="deadLine">${detailHomework[0].deadLine}</div>
                    <hr>
                    <img src="<c:url value='/resources/img/teacher/free-icon-calendar-check-7955802 4.png'/>" id="dateIcon" style="cursor: pointer;" class="img-calendar"/>
                    <input type="text"  name="deadLine" id="datepicker" style="display:none;" />
                </div>
                <hr class="line1">

                <!-- 숙제 내용 -->
                <div id="enroll_content">
                    <textarea id="en_content" name="hmContent">${detailHomework[0].hmContent}</textarea>
                </div>

                <!-- 파일 첨부 -->
                <div class="file-attachment">
                    <label for="file-upload" class="custom-file-upload">파일 첨부</label>
                    <span class="file-name" id="file-name">
                        <c:choose>
                            <c:when test="${not empty detailHomework[0].originName}">
                                ${detailHomework[0].originName}
                            </c:when>
                            <c:otherwise>
                                선택된 파일 없음
                            </c:otherwise>
                        </c:choose>
                    </span>
                    <input type="hidden" name="changeName" value="${detailHomework[0].changeName}">
                    <input type="file" id="file-upload" name="fileupload" class="file-upload">
                </div>
            </div>

            <div class="real-foot">
                <input type="hidden" name="boNo" value="${detailHomework[0].boNo}">
                <button type="submit" class="foot-second">수정</button>
            </form>

            <!-- 삭제 버튼 -->
            <form method="POST" action="deleteHomework">
                <input type="hidden" name="boNo" value="${detailHomework[0].boNo}">
                <input type="hidden" name="hmTitle" value="${detailHomework[0].hmTitle}">
                <input type="hidden" name="changeName" value="${detailHomework[0].changeName}">
                <button type="submit" class="foot-third">삭제</button> 
            </form>

            <button class="foot-first">목록으로</button>
        </div>
    </body>
</html>
