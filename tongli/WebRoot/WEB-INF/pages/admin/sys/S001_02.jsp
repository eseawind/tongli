<%--
/*
 * 系统管理_资讯管理_预览 (页面)
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-03-24  wuxiaogang   程序・发布
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
<!DOCTYPE html>
<html lang="zh-CN" class="no-js">
<head>
<meta charset="utf-8" />
<title>${bean.title}</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" />
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<style>
body {
	-webkit-touch-callout: none;
	-webkit-text-size-adjust: none;
	width:740px;
	margin: 0 auto;
}
</style>
</head>
<body class="page-header-fixed">
	<div class="col-md-12">
		<c:choose>
			<c:when test="${bean!=null}">
				<h3>${bean.title}</h3>
				<div class="text">${bean.detail_info}</div>
			</c:when>
			<c:otherwise>
				<h1>信息不存在或已删除</h1>
			</c:otherwise>
		</c:choose>
	</div>
</body>
</html>
