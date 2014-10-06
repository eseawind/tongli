<%--
/*
 * 会员登录
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
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../include/title_meta.jsp"%>
<%@ include file="../include/public_js_css.jsp"%>
<link href="${basePath}/plugins/bootstrap.admin.theme/assets/css/style.css" rel="stylesheet" type="text/css"/>
<link href="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<script src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<link href="${basePath}/css/w_style.css"		 rel="stylesheet" type="text/css">

<style type="text/css">
.page-header {
    padding-bottom: 9px;
    margin: 0px 0px 20px;
    border-bottom: 1px solid #EEE;
}
div{
overflow: visible;
}
</style>
</head>
<body>
	<div class="all-elements">
		<%@ include file="../include/header.jsp"%>
		<div id="content" class="page-content">
			<div class="page-header">
				<a href="#" class="deploy-sidebar"></a>
				<p class="bread-crumb">
					登录验证
				</p>
				<a href="javascript:history.go(-1);" class="deploy-contact left-list"></a>
			</div>
			<div class="content">
					<form accept-charset="UTF-8"  action="${basePath}/w/t001_login.ac" class="edit_article" id="edit_article_13632" method="post">
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
									<label><input type="radio" class=" radio-one" name="t" value="0" onclick="$('#edit_article_13632').attr('action','${basePath}/w/t001_login.ac');"/>老师</label>
								</div>
								<div class="login_register">	
									<label><input type="radio" class=" radio-one" checked="checked" name="t" value="1"  onclick="$('#edit_article_13632').attr('action','${basePath}/w/m201_login.ac');" />学生/家长</label>
								</div>
								<button type="submit" class="btn btn-info" style="margin-left: 40px;" >登 录</button>
								<div class="clear"></div>
							</div>
						</div>
					</form>
					<%@ include file="../include/footer.jsp"%>
				</div>
		</div>
	</div>
</body>
</html>