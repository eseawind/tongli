<%--
/*
 * 资讯栏目页
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
<div class="title">&nbsp; <c:if test="${typeBeanP.name!=null}">${typeBeanP.name }</c:if><c:if test="${typeBeanP.name==null}">童励俱乐部</c:if>-->${typeBean.name}</div>
<div class="content home_news pr" style="min-height:500px;">
	<c:choose>
		<c:when test="${beans!=null && fn:length(beans)>0 }">
		<ul>
			
			<c:forEach items="${beans}" var="bean">
			<li>
			<a href="${basePath}/c003_init.ac?id=${bean.id}&tid=${bean.type_id}&pid=${pid}" >• ${bean.title}</a>
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
		<a href="javascript:void(0);" class="ico_aboutus"><c:if test="${typeBeanP.name!=null}">${typeBeanP.name }</c:if><c:if test="${typeBeanP.name==null}">童励俱乐部</c:if></a>
	</div>
	<div class="content">
		<ul>
			<c:forEach items="${tree_array}" var="tree">
		      	<li <c:if test="${tree.id==tid}">class="on"</c:if>>
		      	<a <a href="${basePath}/c002_init.ac?tid=${tree.id}&pid=<c:choose><c:when test='${tree.parent_id!=null && tree.parent_id!=""}'>${tree.parent_id}</c:when><c:otherwise>${tree.id}</c:otherwise></c:choose>">${tree.name}</a>
		      	<c:if test="${tree.beans!=null && fn:length(tree.beans)>0}">
		      		<c:forEach items="${tree.beans}" var="bean">
		      		     <li <c:if test="${bean.id==tid}">class="on"</c:if>>
		      		     	|-<a href="${basePath}/c002_init.ac?tid=${bean.id}&pid=<c:choose><c:when test='${bean.parent_id!=null && bean.parent_id!=""}'>${bean.parent_id}</c:when><c:otherwise>${bean.id}</c:otherwise></c:choose>">${bean.name }</a>
		      		     </li>
		      		     <c:if test="${bean.beans!=null && fn:length(bean.beans)>0}">
				      		<c:forEach items="${bean.beans}" var="bean2">
				      		     <li <c:if test="${bean2.id==tid}">class="on"</c:if>>
				      		     	|-|-<a href="${basePath}/c002_init.ac?tid=${bean2.id}&pid=<c:choose><c:when test='${bean2.parent_id!=null && bean2.parent_id!=""}'>${bean2.parent_id}</c:when><c:otherwise>${bean2.id}</c:otherwise></c:choose>">${bean2.name }</a>
				      		     </li>
		           			</c:forEach>
				      	</c:if>
           			</c:forEach>
		      	</c:if>
		      	</li>
	      	</c:forEach>
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