function searchNowStatus(){
		  $.ajax({
      		url: "searchNowStatus",
      		success: function(res){
				  document.getElementById('attendance_rate').textContent = res.rate;
		          document.getElementById('now_status').textContent = res.status;
			  },
			  error:function(){
				  console.log("이건 왜 또 안되는데");
			  }
      	});
	  }