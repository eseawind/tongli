<%--
/*
 * 课程详情
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

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="include/title_meta.jsp"%>
<%@ include file="include/public_js_css.jsp"%>

</head>

<body class="blueBg">
<div class="w640">
<div class="main pr">
<div class="c10"></div>
<div class="w">

<div class="body">
<h1> ${bean.title}信息标题</h1>
<!--<h3>发布日期：${bean.last_updated} &nbsp;&nbsp;&nbsp;&nbsp;发布人：${bean.create_id}</h3>-->
<div class="content" style="min-height:500px;">
  ${bean.detail_info}
</div>
</div>
<div class="body" style="width: 197px;">
	<div class="title">
		<a href="javascript:void(0);" class="ico_aboutus"><c:if test="${typeBeanP.name!=null}">${typeBeanP.name }</c:if><c:if test="${typeBeanP.name==null}">童励俱乐部</c:if></a>
	</div>
	<div class="content">
		<ul>
			<c:forEach items="${tree_array}" var="tree">
		      	<li class="li_0" <c:if test="${tree.id==tid}">class="on"</c:if>>
		      	<a <a href="${basePath}/w/c002_init.ac?tid=${tree.id}&pid=<c:choose><c:when test='${tree.parent_id!=null && tree.parent_id!=""}'>${tree.parent_id}</c:when><c:otherwise>${tree.id}</c:otherwise></c:choose>">${tree.name}</a>
		      	<c:if test="${tree.beans!=null && fn:length(tree.beans)>0}">
		      		<c:forEach items="${tree.beans}" var="bean">
		      		     <li class="li_0" <c:if test="${bean.id==tid}">class="on"</c:if>>
		      		     	|-<a href="${basePath}/w/c002_init.ac?tid=${bean.id}&pid=<c:choose><c:when test='${bean.parent_id!=null && bean.parent_id!=""}'>${bean.parent_id}</c:when><c:otherwise>${bean.id}</c:otherwise></c:choose>">${bean.name }</a>
		      		     </li>
		      		     <c:if test="${bean.beans!=null && fn:length(bean.beans)>0}">
				      		<c:forEach items="${bean.beans}" var="bean2">
				      		     <li class="li_0" <c:if test="${bean2.id==tid}">class="on"</c:if>>
				      		     	|-|-<a href="${basePath}/w/c002_init.ac?tid=${bean2.id}&pid=<c:choose><c:when test='${bean2.parent_id!=null && bean2.parent_id!=""}'>${bean2.parent_id}</c:when><c:otherwise>${bean2.id}</c:otherwise></c:choose>">${bean2.name }</a>
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
</div>
</div>
<!-- BEGIN FOOTER -->
<%@ include file="include/footer.jsp"%>
<!-- END FOOTER -->
</div>
</body>
</html>
