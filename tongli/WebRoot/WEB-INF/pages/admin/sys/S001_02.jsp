<%--
/*
 * 系统管理_资讯管理_预览 (页面)
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-03-24  wuxiaogang   程序・发布
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
<title>${bean.title}</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" />
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">

<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link  href="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<style>
body{ -webkit-touch-callout: none; -webkit-text-size-adjust: none; }
ol,ul{list-style-position:inside;}
 </style>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-header-fixed">
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="col-md-12">
			<c:choose>
				<c:when test="${bean!=null}">
				<h1>${bean.title}</h1>
				<div>==${sessionScope.SESSION_KEY_USER_WECHAT_OPENID}==</div>
                <div class="text">
					${bean.detail_info}
				 </div>
				</c:when>
				<c:otherwise>
				<h1>信息不存在或已删除</h1>
				</c:otherwise>
			</c:choose>
			</div>
		</div>
	</div>
	<!-- BEGIN FOOTER -->
	<script src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/jquery-1.10.2.min.js" type="text/javascript"></script>
	<script src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
	<!-- END FOOTER -->

</body>
<!-- END BODY -->
</html>
