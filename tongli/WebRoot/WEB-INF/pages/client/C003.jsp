<%--
/*
 * 栏目页
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-03-30  wuxiaogang        程序・发布
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

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="include/title_meta.jsp"%>
<%@ include file="include/public_js_css.jsp"%>
<script type="text/javascript" src="${basePath}/js/bxCarousel.js"></script>
</head>

<body class="page-header-fixed">
<!-- BEGIN HEADER -->
<%@ include file="include/header.jsp"%>
<!-- END   HEADER -->
<%@ include file="include/slider.jsp"%>

<div class="main pr">
<div class="c10"></div>
<div class="w">

<div class="body fl" style=" width:770px;">
<h1> ${bean.title}信息标题</h1>
<h3>发布日期：${bean.last_updated} &nbsp;&nbsp;&nbsp;&nbsp;发布人：${bean.create_id}</h3>
<div class="content" style="min-height:290px;">
  ${bean.detail_info}
</div>
</div>
<div class="body fr" style=" width:197px;">
<div class="title"><a href="#" class="ico_recommend">预约参观</a></div>
<div class="content" style="height:150px;">
<img src="images/img4.jpg" width="177" height="150">
</div>
</div>

<div class="body fr mt10" style=" width:197px;">
<div class="content" style="height:177px;">
<img src="images/erweima.jpg" width="177" height="177">
</div>
</div>
<div class="c10"></div>
</div>

</div>
<div class="c10"></div>
</div>
<!-- BEGIN FOOTER -->
<%@ include file="include/footer.jsp"%>
<!-- END FOOTER -->
</body>
</html>
