<%--
/*
 * 微信服务_接收信息展示 (初始化页面)
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-03-19  wuxiaogang   程序・发布
 * -------- ----------- ------------ ------------------------------------------
 * Copyright 2014 车主管家 System. - All Rights Reserved.
 *
 */
--%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" session="false"%>
<%@page import="cn.com.softvan.common.CommonConstant"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
</head>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="zh-CN" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="zh-CN" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="zh-CN" class="no-js">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8" />
<title>微信服务-信息接收【车主管家】</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<meta name="MobileOptimized" content="320">
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<%@ include file="../include/public_js_css.jsp"%>
<link href="${basePath}/css/messages.css" media="all" rel="stylesheet" type="text/css" />
<link href="${basePath}/plugins/bootstrap.admin.theme/assets/css/pages/timeline.css" rel="stylesheet" type="text/css"/>
<style type="text/css">
.timeline div.timeline-icon,.timeline .timeline-blue{left: 20px;}
.timeline:before{left: 40px;}
.timeline div.timeline-body{margin:0px 0px 15px 60px;}
.userInfo{
	width:100%;
	margin-top: 0px;
	padding: 0px 0px 5px;
	border-bottom: 1px solid rgba(255, 255, 255, 0.3);
	font-size: 14px;
	height: 25px;
}
.userInfo .name{

}
.userInfo .date{
	float: right;
}
</style>
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
				$('#wechat,#w010_init').addClass('active');
				$('#wechat_arrow').addClass('open');
				$('#wechat_sub_menu').show();
				//
				loadUrlPage(0, 'h/w010_', 'list', 'a_div_info_list_');
			});
			// 分页查询
			function loadUrlPage(offset, url, event, divId) {
				if (url == null || url == "" || divId == null || divId == "") {
					return;
				}
				var load = "<a class='loading' >信息努力加载中...</a>";
				jQuery("#" + divId).html(load);
				jQuery.ajax({
					url : '${basePath}/' + url + event+'.ac?offset='
							+ offset + '&time=' + new Date().getTime(),
					success : function(req) {
						jQuery("#" + divId).html(req);
					},
					error : function() {
						jQuery("#" + divId).html("信息加载发生错误");
					}
				});
			}
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
						<label>信息类型
							<select class="upload-wrapper " name="bean.msgtype" style="width: 150px;">
								<option>事件信息</option>
							</select>
						</label>
						&nbsp; 
						<label>关键字 
							<input class="upload-wrapper" id="message_keyword" name="bean.keyword" size="20" value="" placeholder="关键字" title="关键字" type="text">
						</label>
						&nbsp; 
						<label>粉丝昵称
							<input class="upload-wrapper" id="message_keyword" name="bean.keyword" size="20" value="" placeholder="粉丝昵称" title="粉丝昵称" type="text">
						</label>
						&nbsp; 
						<label>接收时间
							<input class="upload-wrapper" id="message_keyword" name="bean.keyword" size="20" value="" placeholder="接收时间" title="接收时间" type="text">
						</label>
						&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 
						<label>
						<input class="btn default" name="commit" type="submit" value="检索">
						</label>
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

</body>
<!-- END BODY -->
</html>
