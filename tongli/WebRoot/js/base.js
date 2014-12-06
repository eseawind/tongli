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
	