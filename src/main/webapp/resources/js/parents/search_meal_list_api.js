function searchMealList(){
          $.ajax({
               url: "BoMealService",
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
   	 document.getElementsByClassName("contents-right")[0].innerHTML = "";
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
   // 데이터가 없거나 모든 항목이 비어있는 경우 "쉬는날!" 메시지 표시
   if (!itemArr || itemArr.length === 0 || itemArr.every(item => 
       !item.MLSV_YMD && 
       !item.DDISH_NM && 
       !item.ORPLC_INFO && 
       !item.CAL_INFO && 
       !item.NTR_INFO)) 
   {
       document.getElementsByClassName("contents-right")[0].innerHTML = "<tr><td colspan='5'>쉬는날!</td></tr>";
       return;
   }
	
   // 데이터가 있을 경우 테이블에 데이터 추가
   try{
   		if(itemArr[0].MLSV_FROM_YMD === formattedDate){
	   		document.getElementsByClassName("contents-right")[0].innerHTML += ("<div class='food'>"+"<p>오늘의 급식</p>"+"<p>" + (itemArr[0].DDISH_NM || "정보 없음") + "</p>"+"</div>");
	    }
   		if(itemArr[1].MLSV_FROM_YMD === toformattedDate){
   			document.getElementsByClassName("contents-right")[0].innerHTML += ("<div class='food'>"+"<p>내일의 급식</p>"+"<p>" + (itemArr[1].DDISH_NM || "정보 없음") + "</p>"+"</div>");
   		}
   		
   }catch(error){
		if(itemArr[0].MLSV_FROM_YMD === formattedDate){
			document.getElementsByClassName("contents-right")[0].innerHTML += ("<div class='food'>"+"<p>내일의 급식</p>"+"<p>" + "쉬는날 입니다" + "</p>"+"</div>");
		}
		if(itemArr[0].MLSV_FROM_YMD === toformattedDate){
			document.getElementsByClassName("contents-right")[0].innerHTML += ("<div class='food'>"+"<p>오늘의 급식</p>"+"<p>" + "쉬는날 입니다" + "</p>"+"</div>");
			document.getElementsByClassName("contents-right")[0].innerHTML += ("<div class='food'>"+"<p>내일의 급식</p>"+"<p>" + (itemArr[0].DDISH_NM || "정보 없음") + "</p>"+"</div>");
		}
   }
   
}