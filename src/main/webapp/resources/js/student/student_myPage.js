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
    // 데이터가 없을 때 처리
    if (!itemArr || itemArr.length === 0) {
        document.getElementsByClassName("scheduledraw")[0].innerHTML = "<p>시간표 데이터가 없습니다.</p>";
        return;
    }

    // 오늘과 내일의 시간표 분리 (최대 5교시 기준)
    const maxPeriods = 7; // 최대 교시 수
	const referenceDate = getTodayDate();
    const todaySchedule = []; // 오늘의 시간표
    const tomorrowSchedule = []; // 내일의 시간표
	for (const entry of itemArr){
		if(entry.ALL_TI_YMD === referenceDate){
			todaySchedule.push(entry);
		}else{
			tomorrowSchedule.push(entry);
		}
	}
	
    // 테이블 시작
    let tableHtml = "<div class='draw1'><h4>오늘과 내일의 시간표</h4>";
    tableHtml += "<table>";
    tableHtml += "<tr><th>교시</th><th>오늘의 시간표</th><th>내일의 시간표</th></tr>";
	if(todaySchedule.length>7){
		// 콘솔로 확인
	    console.log("오늘의 시간표:", todaySchedule);
	    console.log("내일의 시간표:", tomorrowSchedule);
		tableHtml += "<tr><td>1교시</td><td>-</td><td>-</td></tr>"+
					"<tr><td>2교시</td><td>-</td><td>-</td></tr>"+
					"<tr><td>3교시</td><td>-</td><td>-</td></tr>"+
					"<tr><td>4교시</td><td>-</td><td>-</td></tr>"
	    tableHtml += "</table></div>";
	    document.getElementsByClassName("scheduledraw")[0].innerHTML = tableHtml;
	    return;
	}
    // 교시별 시간표 작성
    for (let i = 0; i < maxPeriods; i++) {
        const period = (i+1) + "교시";
        const todayContent = todaySchedule[i] && todaySchedule[i].ITRT_CNTNT !== "주말입니다" ? todaySchedule[i].ITRT_CNTNT : null;
        const tomorrowContent = tomorrowSchedule[i] && tomorrowSchedule[i].ITRT_CNTNT !== "주말입니다" ? tomorrowSchedule[i].ITRT_CNTNT : null;

        // 교시가 유효하고, 시간표 내용이 있는 경우만 출력
        if (period && (todayContent || tomorrowContent)) {
            tableHtml += `<tr>`;
            tableHtml += `<td>${period}</td>`;
            tableHtml += `<td>${todayContent || "-"}</td>`;
            tableHtml += `<td>${tomorrowContent || "-"}</td>`;
            tableHtml += `</tr>`;
        }
    }
	
    // 콘솔로 확인
    console.log("오늘의 시간표:", todaySchedule);
    console.log("내일의 시간표:", tomorrowSchedule);

    tableHtml += "</table></div>";
    document.getElementsByClassName("scheduledraw")[0].innerHTML = tableHtml;
}
function getTodayDate() {
    const today = new Date();
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 1을 더함
    const day = String(today.getDate()).padStart(2, '0'); // 날짜는 두 자릿수로 맞추기 위해 padStart 사용

    return year+month+day;
}
