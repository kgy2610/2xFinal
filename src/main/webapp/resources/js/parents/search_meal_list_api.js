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
   
   document.getElementsByClassName("contents-right")[0].innerHTML += ("<div class='food'>"+"<p>오늘의 급식</p>"+"<p>" + (itemArr[0].DDISH_NM || "정보 없음") + "</p>"+"</div>");
   document.getElementsByClassName("contents-right")[0].innerHTML += ("<div class='food'>"+"<p>내일의 급식</p>"+"<p>" + (itemArr[1].DDISH_NM || "정보 없음") + "</p>"+"</div>");
}