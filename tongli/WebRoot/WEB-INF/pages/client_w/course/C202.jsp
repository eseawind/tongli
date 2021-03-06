<%--
/*
 * 课程--预约体验
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-05-18  wuxiaogang        程序・发布
 * -------- ----------- ------------ ------------------------------------------
 * Copyright 2014 wechat System. - All Rights Reserved.
 *
 */
--%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" session="false"%>
<%@page import="cn.com.softvan.common.CommonConstant"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../include/title_meta.jsp"%>
<%@ include file="../include/public_js_css.jsp"%>
<link href="${basePath}/plugins/bootstrap.admin.theme/assets/css/style.css" rel="stylesheet" type="text/css"/>
<link href="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link href="${basePath}/plugins/bootstrap.admin.theme/assets/css/style-metronic.css" rel="stylesheet" type="text/css"/>
<link href="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
<script src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/jquery.blockui.min.js" type="text/javascript"></script> 

<script type="text/javascript" src="${basePath}/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="${basePath}/js/c202.js"></script>
<style type="text/css">
em.invalid {
	color: red;
	padding-left: 18px;
	vertical-align: top;
	width: 196px;
	background: url("${basePath}/css/images/main_bg.gif") 0px -1352px
		no-repeat;
}
em.valid {
	background: url("${basePath}/css/images/main_bg.gif") 0px -1377px
		no-repeat;
	color: #065FB9;
}
select{width: 200px;}
.page-header {
    padding-bottom: 9px;
    margin: 0px 0px 20px;
    border-bottom: 1px solid #EEE;
}
  .tab-content {
    display: block;
    overflow: visible;
	position: relative;
}
</style>
</head>
<body>
	<div class="all-elements">
		<%@ include file="../include/header.jsp"%>
		<div id="content" class="page-content">
			<div class="page-header">
				<a href="#" class="deploy-sidebar"></a>
				<p class="bread-crumb">
					预约体验
				</p>
				<a href="javascript:history.go(-1);" class="deploy-contact left-list"></a>
			</div>
			<div class="content">
				<!-- END   HEADER -->
							<div class="col-xs-12">
									<form id="c202Form_1" accept-charset="UTF-8" action="${basePath}/w/c202_save.ac" method="post">
										<input type="hidden" name="bean.id" value="${bean.id}" />
										<ul>
											<li>
												<div class="tit">参观课程：</div>
												<div class="con">
													<c:set var="bean_course" value="" />
													<select id="bean_course_select" class="form-control" type="text"
																placeholder="请输入你要参观的课程"  style="border:1px solid #ddd;" onchange="getDA()">
													<c:forEach items="${course_beans}" var="course_bean" varStatus="i">
														<optgroup label="${course_bean.subject_name}">
															<c:forEach items="${course_bean.beans}" var="course_bean2"  varStatus="n">
																<c:if test="${i.index==0 && n.index==0}">
																	<c:set var="bean_course" value="${course_bean2.title}" />
																</c:if>
																<option value="${course_bean2.id}">${course_bean2.title}</option>
															</c:forEach>
														</optgroup>
													</c:forEach>
													</select>
													<input type="hidden" class="form-control" type="text"
																placeholder="请输入你要参观的课程" name="bean.course" id="bean_course_1" value="${bean_course}" />
												</div>
											</li>
											<li>
												<div class="tit">参观时间：</div>
												<div class="con">
													<select id="bean_day" class="form-control" type="text"
																placeholder="请输入你要参观的日期时间" name="bean.day" style="border:1px solid #ddd;" onchange="">
														
													</select>
													
												</div>
											</li>
											<li>
												<div class="tit">参观场馆：</div>
												<div class="con">
												<select id="bean_addres"  class="form-control" type="text"
																placeholder="请输入你要参观的场馆" name="bean.addres"  style="border:1px solid #ddd;">
												</select>
												</div>
											</li>
											<li>
												<div class="tit">孩子姓名：</div>
												<div class="con">
													<input type="text"  class="form-control" type="text"
																placeholder="请输入孩子姓名" name="bean.name" class="input" value="">
														
												</div>
											</li>
											<li>
												<div class="tit">孩子性别：</div>
												<div class="con">
													<label><input type="radio" class=" " name="bean.sex" value="0" id="sex_0">男
														</label> &nbsp; &nbsp; <label><input type="radio" class=" "name="bean.sex" value="1"
														id="sex_1"> 女 </label>
												</div>
											</li>
											<li>
												<div class="tit">孩子年龄：</div>
												<div class="con">
													<input name="bean.age"   class="form-control" type="text"
																placeholder="请输入孩子年龄" type="text" class="input"> 
																
												</div>
											</li>
											<li>
												<div class="tit">手机号码：</div>
												<div class="con">
													<input name="bean.tel" id="bean_tel"  class="form-control" type="text"
																placeholder="请输入联系电话" type="text" class="input">
												</div>
											</li>
											<li class="li_0" style="height: 100px;">
												<div class="tit">其它：</div>
												<div class="con" style="height: 80px;">
													<textarea name="bean.detail_info" class="form-control" type="text"
																placeholder="请输入其它备注信息" cols="5" rows="3"
														style="width: 98%; height: 70px; margin-top: 10px;"></textarea>
												</div>
											</li>
										</ul>
			
										<div class="c10"></div>
										<div  style="margin-left: 30%;">
										<button type="button" class="btn btn-info" onclick="javascript:onCheckForm1('c202Form_1');" style="margin-left: 40px;" >提  交</button>
										</div>
										<div class="c10"></div>
									</form>
			
							</div>
				<%@ include file="../include/footer.jsp"%>
				</div>
		</div>
	</div>
</body>
</html>
<script type="text/javascript" src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/select2/select2.min.js"></script>
<script type="text/javascript">
function getDA(){
	$('#bean_course_1').val($("#bean_course_select option:selected").text());
	var cid=$("#bean_course_select").val();
	//myAlert(cid);
	if(cid){
		jQuery.ajax({
			url : '${basePath}/w/c202_getDate.ac?cid='+cid,
			success : function(req) {
				jQuery("#bean_day").html(req);
			},
			error : function() {
				//--异常--
			}
		});
		jQuery.ajax({
			url : '${basePath}/w/c202_getAddres.ac?cid='+cid,
			success : function(req) {
				//myAlert(req);
				jQuery("#bean_addres").html(req);
			},
			error : function() {
				//--异常--
			}
		});
	}
}
/* 
$('.form_date1').datetimepicker({
    language:  'zh-CN',
	format: "yyyy-mm-dd HH:ii:00",
    weekStart: 1,
    todayBtn:  1,
	autoclose: 1,
	todayHighlight: 1,
	startView: 2,
	minView: 0,
	maxView: 3,
	startDate:'${startDate} 07:00:00',
	pickerPosition:"bottom-left"
}); */
if('${bean_course}'!=''){
	getDA();
}
function onCheckForm1(formId){
	initc202Validator_1(formId);
	if(c202Validator.form()){
		$('#'+formId).submit();
	}
}
</script>