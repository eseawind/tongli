
var c203Validator_2 = null;
var c203Validator_3 = null;

var c203Validator_2_id = null;
var c203Validator_3_id = null;
/**
 * 初始化校验
 * 
 * @author wuxiaogang
 */
function initc203Validator_2(fid) {
	c203Validator_2_id=fid;
	c203Validator_2 = $("#"+fid).validate({
		debug : false,
		errorElement : "em",
		errorContainer : $("#warning"),
		event : "blur",
		errorClass : "invalid",
		focusInvalid : false,
		rules : {
			"bean.name" : {// 名称
				required : true
			},
			"bean.birthday" : {// 生日
				required : true
			},
			"bean.sex" : {// 性别
				required : true
			},
			"bean.cell_tel" : {// 移动电话
				mobileOrTel : true
			},
			"bean.tel" : {//家庭电话
				mobileOrTel : true
			},
			"bean.home_address" : {//家庭住址
				required : true
			},
			"bean.swim_survey": {//学习/培训
				required : true
			},
			"bean.swim_skills": {//技能
				required : true
			},
			"bean.course": {//选择课程
				required : true
			},
			"bean.guardian": {//学员/监护人姓名"
				required : true
			},
			"bean.price": {//报名价格
				required : true
			}
		},
		messages : {
			"bean.name" : {// 名称
				required :  "<font color='red'>*</font>"
			},
			"bean.birthday" : {// 生日
				required : "<font color='red'>*</font>"
			},
			"bean.sex" : {// 性别
				required : "<font color='red'>*</font>"
			},
			"bean.cell_tel" : {// 移动电话
				mobileOrTel : "<font color='red'>*</font>"
			},
			"bean.tel" : {//家庭电话
				mobileOrTel : "<font color='red'>*</font>"
			},
			"bean.home_address" : {//家庭住址
				required : "<font color='red'>*</font>"
			},
			"bean.swim_survey": {//学习/培训
				required : "<font color='red'>*</font>"
			},
			"bean.swim_skills": {//技能
				required : "<font color='red'>*</font>"
			},
			"bean.course": {//选择课程
				required : "<font color='red'>*</font>"
			},
			"bean.guardian": {//学员/监护人姓名
				required : "<font color='red'>*</font>"
			},
			"bean.price": {//报名价格
				required : "<font color='red'>*</font>"
			}
		},
		ignore : ".o-save",
		errorPlacement : function(error, element) {
			error.appendTo(element.parent());
			element.parent().find("em").hide();
		},
		success : function(label) {
			label.addClass("valid");
		}
	});
}
$.validator.addMethod("mobileOrTel", function(value, element) {// 电话或者手机 
	if(checktel() || checkmobile()){
		return true;
	}else{
		return false;
	}
});
// 验证电话
function checktel(){
	var tel=$('#'+c203Validator_2_id+'_tel').val();
	if (/\d{3}-\d{8}|\d{4}-\d{7}/.test(tel)) {
		return true;
	} else{
		return false;
	}		
}
//验证手机
function checkmobile(){
	var mobile=$('#'+c203Validator_2_id+'_mobile').val();
	if(/^(\-?)(\d+)$/.test(mobile)) {
		return true;
	} else {
		return false;
	}
}
/**
 * 初始化校验
 * 
 * @author wuxiaogang
 */
function initc203Validator_3(fid) {
	c203Validator_3_id=fid;
	c203Validator_3 = $("#"+fid).validate({
		debug : false,
		errorElement : "em",
		errorContainer : $("#warning"),
		event : "blur",
		errorClass : "invalid",
		focusInvalid : false,
		rules : {
			"bean.code" : {// 第几期
				required : true
			},
			"bean.addres" : {// 地址
				required : true
			},
			"bean.name" : {// 姓名
				required : true
			},
			"bean.sex" : {//性别
				required : true
			},
			"bean.nationality": {//请输入国籍
				required : true
			},
			"bean.birthday" : {// 生日
				required : true
			},
			"bean.school" : {//请输入学校
				required : true
			},
			"bean.card_num": {//请输入学员身份证
				required : true
			},
			"bean.email": {//请输入邮箱
				required : true
			},
			"bean.email": {//请输入邮箱
				required : true
			},
			"bean.cell_tel" : {// 移动电话
				mobileOrTel2 : true
			},
			"bean.home_address" : {//家庭住址
				required : true
			},
			"bean.basketball_skills": {//篮球
				required : true
			},
			"bean.tennis_skills": {//网球
				required : true
			},
			"bean.badminton_skills": {//羽毛球
				required : true
			},
			"bean.karate_skills": {//空手道
				required : true
			},
			"bean.inline_skaters_skills": {//轮滑
				required : true
			},
			"bean.swim_skills": {//游泳
				required : true
			},
			"bean.guardian": {//学员/监护人姓名
				required : true
			},
			"bean.allergy_note": {//是否食物过敏
				required : true
			},
			"bean.disease_note": {//是否重大疾病
				required : true
			}
		},
		messages : {
			"bean.code" : {// 第几期
				required :  "<font color='red'>*</font>"
			},
			"bean.addres" : {// 地址
				required : "<font color='red'>*</font>"
			},
			"bean.name" : {// 姓名
				required : "<font color='red'>*</font>"
			},
			"bean.sex" : {//性别
				required : "<font color='red'>*</font>"
			},
			"bean.nationality": {//请输入国籍
				required : "<font color='red'>*</font>"
			},
			"bean.birthday" : {// 生日
				required : "<font color='red'>*</font>"
			},
			"bean.school" : {//请输入学校
				required : "<font color='red'>*</font>"
			},
			"bean.card_num": {//请输入学员身份证
				required : "<font color='red'>*</font>"
			},
			"bean.email": {//请输入邮箱
				required : "<font color='red'>*</font>"
			},
			"bean.email": {//请输入邮箱
				required : "<font color='red'>*</font>"
			},
			"bean.cell_tel" : {// 移动电话
				mobileOrTel2 : "<font color='red'>*</font>"
			},
			"bean.tel" : {//家庭电话
				mobileOrTel2 : "<font color='red'>*</font>"
			},
			"bean.home_address" : {//家庭住址
				required : "<font color='red'>*</font>"
			},
			"bean.basketball_skills": {//篮球
				required : "<font color='red'>*</font>"
			},
			"bean.tennis_skills": {//网球
				required : "<font color='red'>*</font>"
			},
			"bean.badminton_skills": {//羽毛球
				required : "<font color='red'>*</font>"
			},
			"bean.karate_skills": {//空手道
				required : "<font color='red'>*</font>"
			},
			"bean.inline_skaters_skills": {//轮滑
				required : "<font color='red'>*</font>"
			},
			"bean.swim_skills": {//游泳
				required : "<font color='red'>*</font>"
			},
			"bean.guardian": {//学员/监护人姓名
				required : "<font color='red'>*</font>"
			},
			"bean.allergy_note": {//是否食物过敏
				required : "<font color='red'>*</font>"
			},
			"bean.disease_note": {//是否重大疾病
				required : "<font color='red'>*</font>"
			}
		},
		ignore : ".o-save",
		errorPlacement : function(error, element) {
			error.appendTo(element.parent());
			element.parent().find("em").hide();
		},
		success : function(label) {
			label.addClass("valid");
		}
	});
}

$.validator.addMethod("mobileOrTel2", function(value, element) {// 电话或者手机 
	if(checkmobile2()){
		return true;
	}else{
		return false;
	}
});
//验证手机
function checkmobile2(){
	var mobile=$('#'+c203Validator_3_id+'_mobile').val();
	if(/^(\-?)(\d+)$/.test(mobile)) {
		return true;
	} else {
		return false;
	}
}