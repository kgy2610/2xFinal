<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList, java.lang.Math, java.util.Arrays"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	ArrayList<Double> list = (ArrayList<Double>)session.getAttribute("stu_score_list");
	ArrayList<Double> alist = (ArrayList<Double>)session.getAttribute("stu_AVG_score_list");
	ArrayList<String> slist = new ArrayList(Arrays.asList("국어", "수학", "영어","과학","사회","미술","체육"));
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>parents_grade</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="<c:url value='/resources/js/parents/search_detail_score_list_api.js'/>"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/3.5.0/chart.min.js"></script>
    <link rel="stylesheet" href="<c:url value='/resources/css/parents/parents_grade.css'/>">
</head>
<body>
    <jsp:include page="parents_menubar.jsp" />
    <div id="gradeTitle"><h1>${child.stuName}의 성적!</h1></div>
    <div id="content_border">
        <div class="graphBox">
            <canvas id="barCanvas" width="900px" height="270px"></canvas>  
        </div>
        <table id="grade_table">
            <thead>
                <tr>
                    <th>과목</th>
                    <th>점수</th>
                    <th>과목평균</th>
                </tr>
            </thead>
            <tbody>
            <%for(int i = 0; i <7; i++){ %>
            	<tr onclick="openSubjectModal('<%=slist.get(i) %>')">
                    <td><%=slist.get(i) %></td>
                    <td><p class="stu_score"><%=list.get(i) %></p></td>
                    <td class="avg_score"><%=alist.get(i) %></td>
                </tr>
            <%} %>
            </tbody>
                        
        </table>
    </div>
    <div id="subjectModal" class="modal">
      <div class="modal-content">
      	<span id="modaltitle"></span>
         <span class="close" onclick="closeModal()">&times;</span>
		<table id="modaltable">
			<tr id="modaltableunderbar" >
				<td>제목</td>
				<td>점수</td>
			</tr>
			<tr>
				<td colspan="2">
						<table id="scoretable"></table>
				</td>
			</tr>
		</table>
      </div>
   </div>
    <script>
    	let elements = document.getElementsByClassName('stu_score');
    	let scores = Array.from(elements).map(element => Number(element.innerText));
    	let avgelements = document.getElementsByClassName('avg_score');
    	let avgscores = Array.from(avgelements).map(avgelement => Number(avgelement.innerText));
        const ctx = document.getElementById('barCanvas').getContext('2d');
        const myChart = new Chart(ctx, {
            type: 'bar',  // 기본 유형을 막대그래프로 설정
            data: {
                labels: ["국어", "수학", "영어", "과학", "사회", "미술", "체육"],
                datasets: [
                    {
                        label: '자녀 점수',
                        data: scores,
                        backgroundColor: '#FAEDCD',
                        borderColor: '#FAEDCD',
                        borderWidth: 1,
                        order: 2
                    },
                    {
                        label: '전체 평균 점수',
                        data: avgscores,
                        type: 'line',  // 이 데이터셋을 꺾은선 그래프로 설정
                        borderColor: '#A98467',
                        borderWidth: 2,
                        fill: false,
                        order: 1
                    }
                ]
            },
            options: {
                plugins: {
                    legend: {
                        position: 'right'  // 범례를 오른쪽에 배치
                    }
                },
                scales: {
                    x: {
                        ticks: {
                            font: {
                                size: 18 // 하단 labels의 글꼴 크기를 16px로 설정   
                            },
                            color: '#A98467'
                        }
                    },
                    y: {
                        beginAtZero: true,
                        max: 100
                    }
                }
            }
        });
         

         function closeModal() {
            document.getElementById('subjectModal').style.display = 'none';
         }
         
         window.onclick = function (event) {
             const noticeModal = document.getElementById('subjectModal');
             // 공지사항 모달 외부 클릭 시 닫기
             if (event.target === noticeModal) {
                closeModal();
             }
          };
    </script>
</body>
</html>