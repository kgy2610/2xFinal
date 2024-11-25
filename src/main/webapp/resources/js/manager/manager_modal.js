// 모달 닫기
function closeModal(modalId) {
	document.getElementById(modalId).style.display = 'none';
}

// 모달 밖 클릭 시 닫기
window.onclick = function(event) {
	var deleteModal = document.getElementById('deleteModal');
	var listModal = document.getElementById('listModal');
	var addModal = document.getElementById('addModal')

	if (event.target === deleteModal) {
		closeModal('deleteModal');
	} else if (event.target === listModal) {
		closeModal('listModal');
	} else if(event.target === addModal){
		closeModal('addModal');
	}
}

// 승인 필요 선생님 목록을 화면에 표시
function acceptTeachers(teachers) {
    let str = "";
    
    // teachers가 배열인지 확인
    if (Array.isArray(teachers)) {
        for (let tc of teachers) {
			console.log('tc:', tc); 
            console.log('status:', tc.status); 
            str +=  `<tr>` +
                        `<td>${tc.tcName}</td>` +
                        `<td>${tc.phone}</td>` +
						`<td>
	                        <input type="button" value="수락" onclick="teacherRequest('${tc.tcId}', '${tc.status}')"
							 style="width: 100px; height: 30px; background: #DDE5B6; border-radius: 10px; border: none; color: #A98467; font-size: 15px; font-weight: 800;">
	                    </td>` +
                    `</tr>`;
        }
    } else {
        console.error('teachers는 배열이 아닙니다:', teachers);
    }

    const element = document.getElementById('request-teachers');
    element.innerHTML = str; // 'request-teachers' 요소에 HTML 추가
}

// 승인 리스트 모달 열기
function requestList(){
	var modal = document.getElementById('listModal');
    modal.style.display = "block";
}

//승인이 필요한 교직원 목록
window.onload = function(){
	// AJAX로 요청 보내기
	$.ajax({
		url: "acceptTeacher", // 서버 URL
		type: "GET", // GET 방식 요청
		success: function(data) {
			console.log(data);  // 데이터가 어떻게 반환되는지 확인
			if (data && Array.isArray(data.acceptTeachers)) {
				acceptTeachers(data.acceptTeachers); // 승인이 필요한 선생님 목록을 화면에 표시
			} else {
				console.error('반환된 데이터가 예상한 배열 형식이 아닙니다.', data);
			}
		},
		error: function(xhr, status, error) {
			console.error('선생님 목록을 불러오는 중 오류 발생:', error);
		}
	});
}

// 교직원 승인 여부 모달 열기
function teacherRequest(tcId, status){
	console.log('tcId:', tcId); 
    console.log('status:', status); 

	if(status === 'N'){
		if(confirm(`승인하시겠습니까?`)){
			request(tcId, 'Y');
		}	
	} else{
		alert('올바르지 않은 요청입니다.')
	}
}

//교직원 승인
function request(tcId, status){
	console.log("request js | 승인할 선생님의 tcId : " + tcId);
	console.log("request js | 승인할 선생님의 status : "+ status);

	$.ajax({
		url: "requestTeacher",
		type: "POST",
		data: {tcId : tcId, status : status},
		success: function(response){
			if(response === "success"){
				alert("승인 완료");
				location.reload();
			}else if(response === "fail"){
				alert("승인 실패");
			}else if(response === "올바르지 않은 요청입니다."){
				alert(response)
			}
		},
		error: function(){
			alert("요청 작업 실패");
			location.reload();
		}
	});
}

// 교직원 삭제 모달 열기
function teacherDelete(tcId, tcName) {
	// document.getElementById('deleteModal').style.display = 'block';
	// const input = document.querySelector("deleteModal")

	if(confirm(`${tcName}선생님을 삭제하시겠습니까?`)){
		deleteTeacher(tcId);
	}
}

//선생님 삭제
function deleteTeacher(tcId){
	console.log("js | 삭제할 선생님의 tcId : " + tcId);
	$.ajax({
		url: "deleteTeacher",
		type: "POST",
		data: { tcId : tcId },
		success: function(result){
			if(result  === "success"){
				alert("삭제 성공");
				location.reload();
			}else if(result === "fail"){
				alert("삭제 실패");
			}
		},
		error: function(){
			alert("서버 오류");
			location.reload();
		}
	});
}


//선생님 검색 기능
function filter(){
	const search = document.getElementById("teacher-name").value.toLowerCase();
	const listInner = document.getElementsByClassName("teacher-list-detail");
  
	for (let i = 0; i < listInner.length; i++) {
	  // querySelector로 class 이름을 선택
	  const teacherName = listInner[i].querySelector(".teacherName");
	  const teacherClassCode = listInner[i].querySelector(".teacherClassCode");
  
	  // 요소가 null이 아닐 때만 접근
	  if (teacherName && teacherClassCode) {
		if (teacherName.innerHTML.toLowerCase().indexOf(search) !== -1 ||
			teacherClassCode.innerHTML.toLowerCase().indexOf(search) !== -1) {
		  listInner[i].style.display = "table-row";
		} else {
		  listInner[i].style.display = "none";
		}
	  }
	}
  }
  

//엔터키로 검색
window.enterSearch = () =>{
    if(window.event.keyCode == 13){
        searchSchools();
    }
}