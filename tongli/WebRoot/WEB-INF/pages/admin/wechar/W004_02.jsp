<%--
/*
 * 微信服务_素材管理_文章_预览 (页面)
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-03-04  wuxiaogang   程序・发布
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
<title>微信服务-素材管理-文章【车主管家】</title>
<meta http-equiv="Content-Type"
	content="application/xhtml+xml;charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache,must-revalidate">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="expires" content="0">
<meta name="format-detection" content="telephone=no, address=no">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable">
<!-- apple devices fullscreen -->
<meta content="black-translucent"
	name="apple-mobile-web-app-status-bar-style">

<!-- BEGIN GLOBAL MANDATORY STYLES -->
<%@ include file="../include/public_js_css.jsp"%>
<link href="${basePath}/css/messages.css" media="all" rel="stylesheet"
	type="text/css" />

<link href="${basePath}/css/font-awesome/css/font-awesome.css"
	rel="stylesheet">
<link href="${basePath}/css/font-awesome/css/font-awesome-ie7.css"
	rel="stylesheet">
<style>
@media ( min-width : 1200px) {
}

@media ( min-width : 768px) and (max-width: 979px) {
}

@media ( max-width : 480px) {
}

body {
	background:
		url(${basePath}/images/bg-72b6cbabcebd909ea4106e2958c6714d.png) repeat
		scroll 0 0 #FFFFFF;
}
/* Sticky footer styles
-------------------------------------------------- */
.caret-up {
	/* Safari */
	-webkit-transform: rotate(-180deg);
	/* Firefox */
	-moz-transform: rotate(-180deg);
	/* IE */
	-ms-transform: rotate(-180deg);
	/* Opera */
	-o-transform: rotate(-180deg);
	/* Internet Explorer */
	filter: progid:DXImageTransform.Microsoft.BasicImage(rotation=6);
}

.drop-up {
	top: auto;
	width: 100%;
	text-align: center;
	bottom: 100% !important;
}

.text-muted {
	width:100%;
	display:block;
	position: fixed;
	bottom: 0px;
}

.btn-group {
	width: 33.1%;
}

.btn {
	width: 100%;
	text-align: center;
}
.btn-default{padding: 6px 0;}
</style>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-header-fixed">
	<div class="container-fluid">
		<div class="row-fluid">
			<!-- #NOTE 别动row-fluid，否则门店地图会出错 -->
			<div class="col-md-12">


				<h1>联系捷道</h1>

				<div>2014-02-28</div>
				<div></div>
				<p>
					热线 <a href="tel://4006-734-518">4006-734-518</a>
				</p>

				<p>
					固话 <a href="tel://0577-86000083">0577-86000083</a>
				</p>

				<p>QQ 2805686025</p>

				<p>电邮 robin.wu@agideo.com</p>

			</div>
		</div>
	</div>

	<p style="margin-bottom: 40px"></p>
	<div class="text-muted">
	<div class="btn-group">
		<button type="button" class="btn btn-default dropdown-toggle"
			data-toggle="dropdown">
			Dropup <span class="caret caret-up"></span>
		</button>
		<ul class="dropdown-menu drop-up" role="menu">
			<li><a href="#">Action</a></li>
			<li><a href="#">Another action</a></li>
			<li><a href="#">Something else here</a></li>
			<li class="divider"></li>
			<li><a href="#">Separated link</a></li>
		</ul>
	</div>
	<div class="btn-group">
		<button type="button" class="btn btn-default dropdown-toggle"
			data-toggle="dropdown">
			Dropup <span class="caret caret-up"></span>
		</button>
		<ul class="dropdown-menu drop-up" role="menu">
			<li><a href="#">Action</a></li>
			<li><a href="#">Another action</a></li>
			<li><a href="#">Something else here</a></li>
			<li class="divider"></li>
			<li><a href="#">Separated link</a></li>
		</ul>
	</div>
	<div class="btn-group">
		<button type="button" class="btn btn-default dropdown-toggle"
			data-toggle="dropdown">
			Dropup <span class="caret caret-up"></span>
		</button>
		<ul class="dropdown-menu drop-up" role="menu">
			<li><a href="#">Action</a></li>
			<li><a href="#">Another action</a></li>
			<li><a href="#">Something else here</a></li>
			<li class="divider"></li>
			<li><a href="#">Separated link</a></li>
		</ul>
	</div>
	</div>
	<!-- BEGIN FOOTER -->
	<%@ include file="../include/footer_no_copyright.jsp"%>
	<!-- END FOOTER -->

</body>
<!-- END BODY -->
</html>
