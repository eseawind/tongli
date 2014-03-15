// guid
function guid() {
	var S4 = function() {
		return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
	};
	return 'uuid'+(S4() + S4() +  S4() +  S4() +  S4() +  S4()
			+ S4() + S4());
}

//是否被选中验证有选中的return true,否return false 
function checkInfo(name,type){ 
	var falg = 0; 
	$("input[name="+name+"]:"+type).each(function(){ 
		if($(this).attr("checked")){
			falg +=1;
			return false;
		} 
	}); 
	if(falg>0){
		return true; 
	} 
	return false; 
}
function autoOnClick(id){
	if(document.all) {
		document.getElementById(id).click();
	}
	// 其它浏览器
	else {
		var e = document.createEvent("MouseEvents");
		e.initEvent("click", true, true);
		document.getElementById(id).dispatchEvent(e);
	}
}
//noty
function myAlert(text) {
	noty({
		text : text,
		type : 'information',
		dismissQueue : true,
		layout : 'top',
		theme : 'default'
	});
}

function myAlert_error(text) {
	noty({
		text : text,
		type : 'error',
		dismissQueue : true,
		layout : 'top',
		theme : 'default'
	});
}

function myAlert_success(text) {
	noty({
		text : text,
		type : 'success',
		dismissQueue : true,
		layout : 'top',
		theme : 'default'
	});
}

function myAlert_warning(text) {
	noty({
		text : text,
		type : 'warning',
		dismissQueue : true,
		layout : 'top',
		theme : 'default'
	});
}

function myAlert_confirm(text) {
	noty({
		text : text,
		 type: 'alert',
	      dismissQueue: true,
	      layout: 'top',
	      theme: 'default',
	      buttons: [
	        {addClass: 'btn btn-primary', text: '确定', onClick: function($noty) {
	            $noty.close();
	            
	            return true;
	          }
	        },
	        {addClass: 'btn btn-danger', text: '取消', onClick: function($noty) {
	            $noty.close();
	            return false;
	          }
	        }
	      ]
	});
}
