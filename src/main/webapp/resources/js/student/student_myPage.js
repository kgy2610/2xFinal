// 모달 열기 및 닫기 함수
function openUpdateModal() {
    document.getElementById('updateModal').style.display = 'flex';
}

function closeUpdateModal() {
    document.getElementById('updateModal').style.display = 'none';
}

// 정보 수정 폼에서 비밀번호 수정 폼으로 전환
function switchToPwdForm() {
    document.getElementById('infoForm').style.display = 'none';
    document.getElementById('pwdForm').style.display = 'block';
    document.getElementById('modalTitle').textContent = "비밀번호 수정";
}

// 비밀번호 수정 폼에서 정보 수정 폼으로 전환
function switchToInfoForm() {
    document.getElementById('pwdForm').style.display = 'none';
    document.getElementById('infoForm').style.display = 'block';
    document.getElementById('modalTitle').textContent = "정보 수정";
}

// 외부 클릭 시 모달 닫기
window.onclick = function (event) {
    const updateModal = document.getElementById('updateModal');
    const imageSelectModal = document.getElementById('imageSelectModal');

    // 정보 수정 모달 외부 클릭 시 닫기
    if (event.target == updateModal) {
        closeUpdateModal();
    }

    // 이미지 선택 모달 외부 클릭 시 닫기
    if (event.target == imageSelectModal) {
        closeImageSelectModal();
    }
}

// 이미지 선택 모달 열기 및 닫기 함수
function openImageSelectModal() {
    document.getElementById('imageSelectModal').style.display = 'flex';
}

function closeImageSelectModal() {
    document.getElementById('imageSelectModal').style.display = 'none';
}


function selectImage(imageElement) {
    document.getElementById('selectedImageId').value = imageElement.id;
}

function applyImageChange() {
    const selectedImageId = document.getElementById('selectedImageId').value;

    if (selectedImageId === "") {
        alert("이미지를 선택해주세요.");
        return false;  // 이미지가 선택되지 않으면 폼 전송을 막음 
    }
    return true;

}

//급식
function searchMealList(){
          $.ajax({
               url: "mealService",
               success: function(result){
                if (result && result.mealServiceDietInfo && result.mealServiceDietInfo[1] && result.mealServiceDietInfo[1].row) {
                      drawAirBody(result.mealServiceDietInfo[1].row);
                  } else {
                      drawAirBody([]); // 데이터가 없을 경우 빈 배열 반환
                  }
               },
               error: function(){
                  console.log("ajax통신 실패")
               }
            })
    };
    
   function drawAirBody(itemArr) {


   // 데이터가 없거나 모든 항목이 비어있는 경우 "쉬는날!" 메시지 표시
   if (!itemArr || itemArr.length === 0 || itemArr.every(item => 
       !item.MLSV_YMD && 
       !item.DDISH_NM && 
       !item.ORPLC_INFO && 
       !item.CAL_INFO && 
       !item.NTR_INFO)) 
   {
       document.getElementsByClassName("food")[0].innerHTML = "<tr><td colspan='5'>쉬는날!</td></tr>";
       return;
   }
	// 오늘 날짜 가져오기
	const today = new Date();
	
	// 연도, 월, 일 추출
	const year = today.getFullYear();
	const month = String(today.getMonth() + 1).padStart(2, "0"); // 월은 0부터 시작하므로 +1 필요
	const day = String(today.getDate()).padStart(2, "0"); // 일은 2자리로 변환
	const tomorrow = new Date(today);
	tomorrow.setDate(today.getDate() + 1); // 오늘에 1일을 추가
	
	// 연도, 월, 일 추출
	const tomyear = tomorrow.getFullYear();
	const tommonth = String(tomorrow.getMonth() + 1).padStart(2, "0"); // 월은 0부터 시작하므로 +1 필요
	const tomday = String(tomorrow.getDate()).padStart(2, "0"); // 일은 2자리로 변환
	
	
	const formattedDate = `${year}${month}${day}`;
	const toformattedDate = `${tomyear}${tommonth}${tomday}`;
   // 데이터가 있을 경우 테이블에 데이터 추가
   try{
   		if(itemArr[0].MLSV_FROM_YMD === formattedDate){
	   		document.getElementsByClassName("food_left")[0].innerHTML += ("<div class='food1'>"+"<h4>오늘의 급식</h4>"+"<p>" + (itemArr[0].DDISH_NM || "정보 없음") + "</p>"+"</div>");
	    }
   		if(itemArr[1].MLSV_FROM_YMD === toformattedDate){
   			document.getElementsByClassName("food_right")[0].innerHTML += ("<div class='food2'>"+"<h4>내일의 급식</h4>"+"<p>" + (itemArr[1].DDISH_NM || "정보 없음") + "</p>"+"</div>");
   		}
   		
   }catch(error){
		if(itemArr[0].MLSV_FROM_YMD === formattedDate){
			document.getElementsByClassName("food_right")[0].innerHTML += ("<div class='food2'>"+"<h4>내일의 급식</h4>"+"<p>" + "쉬는날 입니다" + "</p>"+"</div>");
		}
		if(itemArr[0].MLSV_FROM_YMD === toformattedDate){
			document.getElementsByClassName("food_left")[0].innerHTML += ("<div class='food1'>"+"<h4>오늘의 급식</h4>"+"<p>" + "쉬는날 입니다" + "</p>"+"</div>");
			document.getElementsByClassName("food_right")[0].innerHTML += ("<div class='food2'>"+"<h4>내일의 급식</h4>"+"<p>" + (itemArr[0].DDISH_NM || "정보 없음") + "</p>"+"</div>");
		}
   }
   
   
}


//시간표

function getAirStatus() {
        $.ajax({
            url: "scheduleService",  
            success: function(result) {
                if (result && result.elsTimetable && result.elsTimetable[1]) {
                    drawschedule(result.elsTimetable[1].row);
                  } else {
                      drawschedule([]); // 데이터가 없을 경우 빈 배열 반환
                  }
            },
            error: function() {
                console.log("시간표 API 요청 실패");
            }
        });
    }

    function drawschedule(itemArr) {
    // 오늘과 내일의 시간표 데이터 구분
    const todaySchedule = itemArr.slice(0, 5);  // 오늘의 시간표
    const tomorrowSchedule = itemArr.slice(5, 10);  // 내일의 시간표

    // 테이블 시작 태그 생성
    let tableHtml = "<div class='draw1'><h4>오늘과 내일의 시간표</h4>";
    tableHtml += "<table>";
    tableHtml += "<tr><th></th><th>오늘의 시간표</th><th>내일의 시간표</th></tr>"; // 테이블 헤더

    // 교시별로 오늘과 내일의 시간표 내용을 테이블에 추가
    for (let i = 0; i < 5; i++) {
        const period = todaySchedule[i] && todaySchedule[i].PERIO ? todaySchedule[i].PERIO : "정보 없음";
        const todayContent = todaySchedule[i] && todaySchedule[i].ITRT_CNTNT ? todaySchedule[i].ITRT_CNTNT : "주말입니다";
        const tomorrowContent = tomorrowSchedule[i] && tomorrowSchedule[i].ITRT_CNTNT ? tomorrowSchedule[i].ITRT_CNTNT : "주말입니다";
        
        tableHtml += "<tr>";
        tableHtml += "<td>" + period + "교시</td>";  // 교시 번호
        tableHtml += "<td>" + todayContent + "</td>";  // 오늘의 시간표
        tableHtml += "<td>" + tomorrowContent + "</td>";  // 내일의 시간표
        tableHtml += "</tr>";
    }
    
    tableHtml += "</table></div>"; // 테이블 닫기

    // 테이블 HTML을 특정 클래스에 출력
    document.getElementsByClassName("scheduledraw")[0].innerHTML = tableHtml;
}