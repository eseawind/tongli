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
<%@ taglib prefix="customtag" uri="/custom-tags"%>

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

<div class="body fr" style=" width:770px;">
<div class="title">&nbsp; ${typeBean.name}</div>
<div class="content home_news pr" style="min-height:390px;">
	<c:choose>
		<c:when test="${beans!=null && fn:length(beans)>0 }">
		<ul>
			<c:forEach items="${beans}" var="bean">
			<li>
			<a href="${basePath}/c003_init.ac?id=${bean.id}&tid=${bean.type_id}&pid=${bean.parent_id}" target="_blank">• ${bean.title}</a>
			<em>
			${fn:substring(bean.last_updated,0,10)}
			</em>
			</li>
			</c:forEach>
		</ul>
		</c:when>
		<c:otherwise>
			${typeBean.detail_info}
		</c:otherwise>
	</c:choose>
</div>
<customtag:pagingext func="loadUrlPage" params="'c002_','init'" />
</div>
<div class="body fl" style="width: 197px;">
	<div class="title">
		<a href="javascript:void(0);" class="ico_aboutus">童励介绍</a>
	</div>
	<div class="content">
		<ul>
			<li><a href="#" title="童励介绍">• 童励介绍</a></li>
			<li><a href="#" title="童励在上海">• 童励在上海</a></li>
			<li><a href="#" title="联系方式">• 联系方式</a></li>
		</ul>
	</div>
</div>
<div class="body fl mt10" style=" width:197px;">
<div class="title"><a href="#" class="ico_recommend">预约参观</a></div>
<div class="content" style="height:150px;">
<img src="images/img4.jpg" width="177" height="150">
</div>
</div>

<div class="body fl mt10" style=" width:197px;">
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
<script type="text/javascript">
function loadUrlPage(offset, url, event) {
	location.href='${basePath}/' + url + event+'.ac?offset=' + offset;
}
</script>