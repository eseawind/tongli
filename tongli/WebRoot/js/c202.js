
var c202Validator = null;

/**
 * 初始化校验
 * 
 * @author wuxiaogang
 */
function initc202Validator_1(fid) {
	c202Validator = $("#"+fid).validate({
		debug : false,
		errorElement : "em",
		errorContainer : $("#warning"),
		event : "blur",
		errorClass : "invalid",
		focusInvalid : false,
		rules : {
			"bean.course" : {// 课程
				required : true
			},
			"bean.day" : {// 日期
				required : true
			},
			"bean.addres" : {// 地址
				required : true
			},
			"bean.name" : {// 姓名
				required : true,
				minlength : 2,
				maxlength : 20
			},
			"bean.sex" : {//性别
				required : true
			},
			"bean.age" : {//年龄
				required : true,
				minlength : 1,
				maxlength : 10
			},
			"bean.tel": {//手机
				checkmobile : true,
				minlength : 11,
				maxlength : 11
			}
		},
		messages : {
			"bean.course" : {// 课程
				required : "<font color='red'>*请选择您要体验的课程</font>"
			},
			"bean.day" : {// 日期
				required : "<font color='red'>*请选择您要体验的时间</font>"
			},
			"bean.addres" : {// 地址
				required : "<font color='red'>*请选择您要体验的场馆</font>"
			},
			"bean.name" : {// 姓名
				required : "<font color='red'>*请输入您孩子的姓名</font>",
				minlength : "<font color='red'>*姓名最少2个字</font>",
				maxlength : "<font color='red'>*姓名最大20个字</font>"
			},
			"bean.sex" : {//性别
				required : "<font color='red'>*请输入您孩子的姓名</font>"
			},
			"bean.age" : {//年龄
				required : "<font color='red'>*请输入您孩子的年龄</font>",
				minlength : "<font color='red'>*请输入您孩子的年龄</font>",
				maxlength : "<font color='red'>*请输入您孩子的年龄</font>"
			},
			"bean.tel": {//手机
				checkmobile : "<font color='red'>*请输入您的手机号码</font>",
				minlength : "<font color='red'>*必须11位数字</font>",
				maxlength : "<font color='red'>*必须11位数字</font>"
			}
		},
		ignore : ".o-save",
		errorPlacement : function(error, element) {
			error.appendTo(element.parent("div"));
			element.parent("div").find("em").hide();
		},
		success : function(label) {
			label.addClass("valid");
		}
	});
}
/**
 * 自定义验证规则
*/
$.validator.addMethod("checkmobile", function(value, element) {// 手机 
	var mobile=$('#bean_tel').val();
	if(/^(\-?)(\d+)$/.test(mobile)) {
		return true;
	} else {
		return false;
	}
});
