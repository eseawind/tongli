<%--
/*
 * 首页
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-04-11  wuxiaogang        程序・发布
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
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="include/title_meta.jsp"%>
<%@ include file="include/public_js_css.jsp"%>
</head>

<body class="page-header-fixed">
<!-- BEGIN HEADER -->
<%@ include file="include/header.jsp"%>
<!-- END   HEADER -->
<%@ include file="include/slider.jsp"%>

<div class="main">
<div class="c10"></div>
<div class="w">
				<div class="more_info"><div class="w980"><a href="${basePath}/c002_init.ac?tid=3f2b286347174e728d39169c212fe56b&pid=3f2b286347174e728d39169c212fe56b">更多资讯</a></div></div>
                <ul class="card">
                <c:forEach items="${beans}" var="bean" varStatus="i">
					<li>
						<a href="${basePath}/c003_init.ac?id=${bean.id}&tid=${bean.type_id}&pid=3f2b286347174e728d39169c212fe56b"> 
							<img  onerror="this.src='${basePath}/images/error/404.jpg';this.onerror='';"  src="${bean.pic_url}" 
								width="300" height="170" title="${bean.title}" />
								<h5>${bean.title}</h5>
								<p>${bean.brief_info}..</p>
								<div class="tips">
								 <span class="r">${bean.date_created}</span>
								 </div>
						</a>
					</li>
				</c:forEach>
                </ul>

</div>
<div class="c10"></div>
</div>
<!-- BEGIN FOOTER -->
<%@ include file="include/footer.jsp"%>
<!-- END FOOTER -->
</body>
</html>