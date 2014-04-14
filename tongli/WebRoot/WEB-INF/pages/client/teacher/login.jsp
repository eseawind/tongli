<%--
/*
 * 教师登录
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-04-14  wuxiaogang        程序・发布
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
<%@ include file="../include/title_meta.jsp"%>
<%@ include file="../include/public_js_css.jsp"%>
<script type="text/javascript" src="${basePath}/js/bxCarousel.js"></script>
</head>

<body class="page-header-fixed">
	<!-- BEGIN HEADER -->
	<%@ include file="../include/header.jsp"%>
	<!-- END   HEADER -->
	<%@ include file="../include/slider.jsp"%>

	<div class="main pr">
		<div class="c10"></div>
		<div class="w">

			<div class="body fl" style="width: 770px;">
				<div class="content" style="min-height: 390px;">

					<div class="login_box">
						<div class="login_header">
							<div class="login_title">登 录</div>
						</div>
						<div class="login_body">
							<div class="login_row">
								<div class="login_lable">用户名：</div>
								<div class="login_input">
									<input name="" type="text" class="input" />
								</div>
							</div>
							<div class="login_row">
								<div class="login_lable">密 &nbsp;&nbsp;码：</div>
								<div class="login_input">
									<input name="" type="password" class="input" />
								</div>
							</div>
							<div class="login_forget">
								<a href="#">忘记密码？</a>
							</div>
							<div class="login_btn">登 录</div>
							<div class="clear"></div>
						</div>
					</div>

				</div>
			</div>

			<div class="body fr" style="width: 197px;">
				<div class="title">
					<a href="#" class="ico_recommend">预约参观</a>
				</div>
				<div class="content" style="height: 150px;">
					<img src="images/img4.jpg" width="177" height="150" />
				</div>
			</div>

			<div class="body fr mt10" style="width: 197px;">
				<div class="content" style="height: 177px;">
					<img src="images/erweima.jpg" width="177" height="177" />
				</div>
			</div>
			<div class="c10"></div>
		</div>

	</div>
	<!-- BEGIN FOOTER -->
	<%@ include file="../include/footer.jsp"%>
	<!-- END FOOTER -->
</body>
</html>