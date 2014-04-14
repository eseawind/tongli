/* 我的报案下拉详细信息 */
 function displayMyBA(){
		var dis = $("#tnt_body").attr("style");
		
		if(dis != "display: block;"){
			 $("#mybaoan").removeClass("mybaoan01");
			   $("#mybaoan").addClass("mybaoan02");
			$("#tnt_body").show(2000,function(){
			   $(this).attr("style", "display: block;");
			 });
		} else{
			 $("#mybaoan").removeClass("mybaoan02");
			   $("#mybaoan").addClass("mybaoan01");
			$("#tnt_body").hide(2000,function(){
			   $(this).attr("style", "display: none;");
			  
			 });
			
		}
	}	
	
	/* 我的车损下拉详细信息 */
 function displayMyCS(){
		var dis = $("#cs_body").attr("style");
		
		if(dis != "display: block;"){
			 $("#mycs").removeClass("mybaoan01");
			   $("#mycs").addClass("mybaoan02");
			$("#cs_body").show(2000,function(){
			   $(this).attr("style", "display: block;");
			 });
		} else{
			 $("#mycs").removeClass("mybaoan02");
			   $("#mycs").addClass("mybaoan01");
			$("#cs_body").hide(2000,function(){
			   $(this).attr("style", "display: none;");
			  
			 });
			
		}
	}	
 
 /** myAlert */
 function myAlert(content){
		$("body").append("<div id='alert'></div>");
		$("body").append("<div id='alert_con'>" + content + "</div>");
		$("body").append("<div id ='alert_queren' onclick='closeAlert();' >确认</div>");
	}

	function closeAlert(){
		jQuery('#alert_queren').remove();
		jQuery('#alert_con').remove();
		jQuery('#alert').remove();
	}
	
	 /** myConfirm */
	 function myConfirm(content){
		 $("body").append("<div id='confirm'></div>");
		 $("body").append("<div id='confirm_con'>" + content + "</div>");
		 $("body").append("<div id ='confirm_queren' >确认</div>");
		 $("body").append("<div id ='confirm_quxiao' onclick='closeAlert();' >取消</div>");
	 }
	