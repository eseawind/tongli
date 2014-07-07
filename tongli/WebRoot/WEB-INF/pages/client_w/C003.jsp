<%--
/*
 * 资讯内容
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
		<div class="main pr">
			<div class="body">
				<h1>${bean.title}</h1>
				<!--<h3>发布日期：${bean.last_updated} &nbsp;&nbsp;&nbsp;&nbsp;发布人：${bean.create_id}</h3>-->
				<div class="content" style="min-height: 600px;">
					${bean.detail_info}</div>
			</div>
		</div>
		<!-- BEGIN FOOTER -->
		<%@ include file="include/footer.jsp"%>
		<!-- END FOOTER -->
	</div>
</body>
</html>
