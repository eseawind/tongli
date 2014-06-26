<%--
/*
 * 微信服务_接收信息展示 (初始化页面)
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-03-19  wuxiaogang   程序・发布
 * -------- ----------- ------------ ------------------------------------------
 * Copyright 2014 tongli System. - All Rights Reserved.
 *
 */
--%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" session="false"%>
<%@page import="cn.com.softvan.common.CommonConstant"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
<link href="${basePath}/css/messages.css" media="all" rel="stylesheet" type="text/css" />
<link href="${basePath}/plugins/bootstrap.admin.theme/assets/css/pages/timeline.css" rel="stylesheet" type="text/css"/>
<style type="text/css">
.timeline div.timeline-icon,.timeline .timeline-blue{left: 20px;}
.timeline:before{left: 40px;}
.timeline .timeline-blue div.timeline-body {
    background: none repeat scroll 0% 0% #eee;
}
.timeline .timeline-blue div.timeline-body:after{border-right-color: #eee;}
.timeline div.timeline-body{margin:0px 0px 15px 60px;color: #333;}
.userInfo{
	width:100%;
	margin-top: 0px;
	padding: 0px 0px 5px;
	border-bottom: 1px solid rgba(255, 255, 255, 0.3);
	font-size: 14px;
	height: 25px;
	color: #333;
}
.userInfo .name{

}
.userInfo .date{
	float: right;color: #333;
}
</style>
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
						管理微信公众帐号 <small><span class="help-inline">如果没有公众帐号，<a
								href="http://mp.weixin.qq.com" target="_blank">点这里注册</a></span></small>
					</h3>
					<ul class="page-breadcrumb breadcrumb">
						<li><i class="fa fa-home"></i> <a
							href="${basePath }/home_init.ac">Home</a> <i
							class="fa fa-angle-right"></i></li>
						<li><a href="#">微信服务</a> <i class="fa fa-angle-right"></i></li>
						<li>消息展示</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			<!-- END PAGE HEADER-->
			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
				<div class="col-md-12">
				<div class="well form-inline">
				<form id="small_info_form_list1" accept-charset="UTF-8"  action="${basePath}/h/w010_list.ac"  method="post">
					<s:token></s:token>
					<input type="hidden" id="offsetAA" name="offset" value="0">
						<label>信息类型
							<select class="upload-wrapper " name="bean.msgtype" style="width: 150px;">
								<option value="">全部</option>
								<option value="text">文本类型 </option>
								<option value="image">图片信息 </option>
								<option value="voice">语音 </option>
								<option value="video">视频 </option>
								<option value="music">音乐 </option>
								<option value="news">图文 </option>
								<option value="location">地理位置 </option>
								<option value="link">链接  </option>
								<option value="event">事件推送</option>
							</select>
						</label>
						&nbsp; 
						<label>关键字 
							<input class="upload-wrapper" id="message_keyword" name="bean.keyword" size="20" value="" placeholder="关键字" title="关键字" type="text">
						</label>
						<!-- &nbsp; 
						<label>粉丝昵称
							<input class="upload-wrapper" id="message_nickname" name="bean.nickname" size="20" value="" placeholder="粉丝昵称" title="粉丝昵称" type="text">
						</label> -->
						&nbsp; 
						<label>开始时间
							<input class="upload-wrapper date form_date1" id="message_date1" name="bean.date1" size="20" value="" placeholder="接收时间段(开始)" readonly="readonly" title="接收时间" type="text">
						</label>
						&nbsp; 
						<label>结束时间
							<input class="upload-wrapper date form_date2" id="message_date2" name="bean.date2" size="20" value="" placeholder="接收时间段(结束)" readonly="readonly" title="接收时间" type="text">
						</label>
						&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 
						<a onclick="submitFrom1(0,'small_info_form_list1');" class="reload btn btn-primary">检索</a>
					    <button type="reset" class="reload btn blue">重置</button>
					</form>
				</div>
				<div id="a_div_info_list_"  class="col-md-12" >
					
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
	<script type="text/javascript" src="${basePath}/js/jquery.form.js"></script>
</body>
<!-- END BODY -->
</html>
<script type="text/javascript">
	jQuery(document).ready(function() {
		$('#wechat,#w010_init').addClass('active');
		$('#wechat_arrow').addClass('open');
		$('#wechat_sub_menu').show();
		//
		submitFrom1(0,'small_info_form_list1');
		
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
	});
	//提交from
	function submitFrom1(offset,from_id) {
		$('#offsetAA').val(offset);
		$("#"+from_id).ajaxSubmit(function(data) {
			$('#a_div_info_list_').html(data);
		});
	}
</script>