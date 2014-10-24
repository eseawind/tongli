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

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../include/title_meta.jsp"%>
<%@ include file="../include/public_js_css.jsp"%>

</head>

<body class="page-header-fixed">
	<!-- BEGIN HEADER -->
	<%@ include file="../include/header.jsp"%>
	<!-- END   HEADER -->
	<%-- <%@ include file="../include/slider.jsp"%> --%>

	<div class="main pr">
		<div class="c10"></div>
		<div class="w">

			<div class="body fl" style="width: 970px;">
				<div class="content" style="min-height: 390px;">
					<form accept-charset="UTF-8"  action="${basePath}/t001_login.ac" class="edit_article" id="edit_article_13632" method="post">
						<s:token></s:token>
						<div class="login_box">
							<div class="login_header">
								<div class="login_title">登 录</div>
							</div>
							<div class="login_body">
								<div class="login_row" style="color:red;">
								<div class="login_lable"></div>${msg}
								</div>
								<div class="login_row">
									<div class="login_lable">用户名：</div>
									<div class="login_input">
										<input name="uid" type="text" class="input" />
									</div>
								</div>
								<div class="login_row">
									<div class="login_lable">密 &nbsp;&nbsp;码：</div>
									<div class="login_input">
										<input name="pwd" type="password" class="input" />
									</div>
								</div>
								<!-- <div class="login_forget">
									<a href="#">忘记密码？</a>
								</div> -->
								<div class="login_register">
									<label><input type="radio"  checked="checked" name="t" value="0" onclick="$('#edit_article_13632').attr('action','${basePath}/t001_login.ac');"/>老师</label>
								</div>
								<div class="login_register">	
									<label><input type="radio" name="t" value="1"  onclick="$('#edit_article_13632').attr('action','${basePath}/m201_login.ac');" />学生/家长</label>
								</div>
								<button type="submit" class="btn btn-info" style="margin-left: 40px;" >登 录</button>
								<div class="clear"></div>
							</div>
						</div>
					</form>
				</div>
			</div>
			<%-- <%@ include file="../include/nav_right.jsp"%> --%>
			<div class="c10"></div>
		</div>

	</div>
	<!-- BEGIN FOOTER -->
	<%@ include file="../include/footer.jsp"%>
	<!-- END FOOTER -->
</body>
</html>