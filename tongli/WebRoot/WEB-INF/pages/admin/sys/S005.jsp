<%--
/*
 * 短消息管理(页面)
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-05-20  wuxiaogang   程序・发布
 * -------- ----------- ------------ ------------------------------------------
 * Copyright 2014 童励 System. - All Rights Reserved.
 *
 */
--%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" session="false"%>
<%@page import="cn.com.softvan.common.CommonConstant"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="customtag" uri="/custom-tags"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="zh-CN" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="zh-CN" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="zh-CN" class="no-js">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8" />
<%@include file="../include/admin_title.jsp" %>

<!-- BEGIN GLOBAL MANDATORY STYLES -->
<%@ include file="../include/public_js_css.jsp"%>
<link href="${basePath}/css/messages.css" media="all" rel="stylesheet"
	type="text/css" />

<link href="${basePath}/css/font-awesome/css/font-awesome.css"	rel="stylesheet">
<link href="${basePath}/css/font-awesome/css/font-awesome-ie7.css" rel="stylesheet">
<script type="text/javascript" src="${basePath}/js/jquery.form.js"></script>

<link href="${basePath}/js/bootstarp-date/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen"/>
<script type="text/javascript" src="${basePath}/js/bootstarp-date/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="${basePath}/js/bootstarp-date/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>

</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-header-fixed">
	<!-- BEGIN HEADER -->
	<%@ include file="../include/header.jsp"%>
	<!-- END HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
	<div class="page-container">
		<!-- BEGIN SIDEBAR -->
		<%@ include file="../include/leftMenu.jsp"%>
		<script type="text/javascript">
			jQuery(document).ready(function() {
				$('#sys6,#sys6_sub_menu_l1_sub_menu_l3').addClass('active');
				$('#sys6_arrow,#sys6_sub_l1_arrow').addClass('open');
				$('#sys6_sub_menu,#sys6_sub_l1_sub_menu').show();
			});
		</script>
		<!-- END SIDEBAR -->
		<!-- BEGIN PAGE -->
		<div class="page-content">
			<!-- BEGIN STYLE CUSTOMIZER -->
			<%@ include file="../include/style_customizer.jsp"%>
			<!-- END BEGIN STYLE CUSTOMIZER -->
			<!-- BEGIN PAGE HEADER-->
			<div class="row">
				<div class="col-md-12">
					<!-- BEGIN PAGE TITLE & BREADCRUMB-->
					<h3 class="page-title">
						短消息管理 <small><span class="help-inline">展示所有</span></small>
					</h3>
					<ul class="page-breadcrumb breadcrumb">
						<li><i class="fa fa-home"></i> <a
							href="${basePath }/home_init.ac">Home</a> <i
							class="fa fa-angle-right"></i></li>
						<li><a href="#">短消息管理</a> <i class="fa fa-angle-right"></i></li>
						<li>列表</a> </li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			<!-- END PAGE HEADER-->
			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
				<div class="col-md-12">
					<div class="matter">
						<c:if test="${msg!=null}">
							<c:choose>
								<c:when test="${msg!='1'}">
									<div class="alert alert-danger">
										<button class="close" data-dismiss="alert"></button>
										<strong>Error!</strong> ${msg}
									</div>
								</c:when>
								<c:otherwise>
									<div class="alert alert-success">
										<button class="close" data-dismiss="alert"></button>
										<strong>Success!</strong> 操作成功。
									</div>
								</c:otherwise>
							</c:choose>
						</c:if>
						<div id="msg"></div>
						<div class="well form-inline">
							<form id="small_info_form_list1" accept-charset="UTF-8"  action="${basePath}/h/s005_list1.ac"  method="post">
								<s:token></s:token>
								<input type="hidden" id="offsetAA" name="offset" value="0">
								<label>电话号码:
								<input name="bean.sms_src_id" class="upload-wrapper" value=""   placeholder="输入需要搜索的手机号" 
								 type="text">
								 </label>
								 <label>短信内容:
								<input name="bean.keyword" class="upload-wrapper"  value=""  placeholder="关键字" 
								 type="text">
								 </label>
								 <label>开始时间:
								<input name="bean.date1" class="upload-wrapper date form_date1" value=""  readonly="readonly" placeholder="时间段" 
								 type="text">
								 </label>
								 <label>结束时间:
								<input name="bean.date2" class="upload-wrapper date form_date2" value=""   readonly="readonly" placeholder="时间段" 
								 type="text">
								 </label>
								 <a onclick="submitFrom1(0,'small_info_form_list1');" class="reload btn btn-primary">检索</a>
								 <button type="reset" class="reload btn blue">重置</button>
							 </form>
						</div>
						<div class="btn btn-toolbar">
							<a href="${basePath}/h/s005_edit.ac" class="btn btn-primary">发送短信</a>
						</div>
						<div class="col-md-12" id="small_info_div_list1">
						</div>
					</div>
				</div>
			</div>
			<!-- END PAGE CONTENT-->
		</div>
		<!-- END PAGE -->
	</div>
	<!-- END CONTAINER -->
	<!-- BEGIN FOOTER -->
	<%@ include file="../include/footer.jsp"%>
	<!-- END FOOTER -->

</body>
<!-- END BODY -->
</html>
<script type="text/javascript">
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
	pickerPosition:"bottom-left"
});
$('.form_date2').datetimepicker({
    language:  'zh-CN',
	format: "yyyy-mm-dd HH:ii:00",
    weekStart: 1,
    todayBtn:  1,
	autoclose: 1,
	todayHighlight: 1,
	startView: 2,
	minView: 0,
	maxView: 3,
	pickerPosition:"bottom-left"
});
/* function loadUrlPage(offset, url, event) {
	location.href='${basePath}/' + url + event+'.ac?offset=' + offset;
} */
//提交from
function submitFrom1(offset,from_id) {
	$('#offsetAA').val(offset);
	jQuery("#"+from_id).ajaxSubmit(function(data) {
		$('#small_info_div_list1').html(data);
	});
}
submitFrom1(0,'small_info_form_list1');
</script>