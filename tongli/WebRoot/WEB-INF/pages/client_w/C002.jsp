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

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="include/title_meta.jsp"%>
<%@ include file="include/public_js_css.jsp"%>

</head>

<body class="blueBg">
<!-- BEGIN HEADER -->
<div class="w640">
<!-- END   HEADER -->
<%-- <%@ include file="include/slider.jsp"%> --%>

<div class="main pr">
<div class="c10"></div>
<div class="w">

<div class="body">
<div class="title">&nbsp; <i class="fa  fa-star-half-full "></i><c:if test="${typeBeanP.name==null}">童励俱乐部</c:if>
<c:choose>
	<c:when test="${typeBeanP.name==typeBean.name}">
		<c:if test="${typeBeanP.name!=null}">${typeBeanP.name}</c:if>
	</c:when>
	<c:otherwise>
		<c:if test="${typeBeanP.name!=null}">${typeBeanP.name }</c:if>-->${typeBean.name}
	</c:otherwise>
</c:choose>
</div>
<div class="content home_news pr" style="min-height:600px;">
	<c:choose>
		<c:when test="${beans!=null && fn:length(beans)>0 }">
		<ul>
			
			<c:forEach items="${beans}" var="bean">
			<li>
			<a href="${basePath}/w/c003_init.ac?id=${bean.id}&tid=${bean.type_id}&pid=${pid}" >• ${bean.title}</a>
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
</div>

</div>

<!-- BEGIN FOOTER -->
<%@ include file="include/footer.jsp"%>
<!-- END FOOTER -->
</div>
</body>
</html>
<script type="text/javascript">
function loadUrlPage(offset, url, event) {
	location.href='${basePath}/' + url + event+'.ac?offset=' + offset;
}
</script>