<%--
/*
 * 微信 号码绑定(页面)
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-03-04  wuxiaogang   程序・发布
 * -------- ----------- ------------ ------------------------------------------
 * Copyright 2014 车主管家 System. - All Rights Reserved.
 *
 */
--%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" session="false"%>
<%@page import="cn.com.softvan.common.CommonConstant"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
</head>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="zh-CN" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="zh-CN" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="zh-CN" class="no-js">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8" />
<title>微信服务-微信号绑定【车主管家】</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<meta name="MobileOptimized" content="320">
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<%@ include file="../include/public_js_css.jsp"%>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-header-fixed">
	<!-- BEGIN HEADER -->
	<%@ include file="../include/header.jsp"%>
	<!-- END HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
	<div class="page-container">
		<!-- BEGIN SIDEBAR -->
		<%@ include file="../include/leftMenu.jsp"%>
		<script type="text/javascript">
			jQuery(document).ready(function() {
				$('#wechat,#w001_init').addClass('active');
				$('#wechat_arrow').addClass('open');
				$('#wechat_sub_menu').show();
			});
		</script>
		<!-- END SIDEBAR -->
		<!-- BEGIN PAGE -->
		<div class="page-content">
			<!-- BEGIN STYLE CUSTOMIZER -->
			<%@ include file="../include/style_customizer.jsp"%>
			<!-- END BEGIN STYLE CUSTOMIZER -->
			<!-- BEGIN PAGE HEADER-->
			<div class="row">
				<div class="col-md-12">
					<!-- BEGIN PAGE TITLE & BREADCRUMB-->
					<h3 class="page-title">
						管理微信公众帐号 <small><span class="help-inline">如果没有公众帐号，<a
								href="http://mp.weixin.qq.com" target="_blank">点这里注册</a></span></small>
					</h3>
					<ul class="page-breadcrumb breadcrumb">
						<li><i class="fa fa-home"></i> <a
							href="${basePath }/home_init.ac">Home</a> <i
							class="fa fa-angle-right"></i></li>
						<li><a href="#">微信服务</a> <i class="fa fa-angle-right"></i></li>
						<li>微信号绑定</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			<!-- END PAGE HEADER-->
			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
				<div class="col-md-12">
					<c:if test="${msg!=null}">
						<c:choose>
							<c:when test="${msg!='1'}">
								<div class="alert alert-danger">
									<button class="close" data-dismiss="alert"></button>
									<strong>Error!</strong> ${msg}
								</div>
							</c:when>
							<c:otherwise>
								<div class="alert alert-success">
									<button class="close" data-dismiss="alert"></button>
									<strong>Success!</strong> 操作成功。
								</div>
							</c:otherwise>
						</c:choose>
					</c:if>
					<div class="col-md-4">
						<form accept-charset="UTF-8" action="${basePath }/h/w001_save.ac" data-remote="true"
							method="post">
							<div style="margin: 0; padding: 0; display: inline">
								<input name="id" type="hidden" value="${bean.id}">
							</div>
							<div class="form-group">
								<label >公众账号用户名</label>
								<div class="input-icon">
									<i class="fa fa-user"></i> <input
										class="form-control placeholder-no-fix" autocomplete="off"
										placeholder="User" value="${bean.userid}" name="bean.userid" type="text">
								</div>
							</div>
							<div class="form-group">
								<label >公众账号密码</label>
								<div class="input-icon">
									<i class="fa fa-lock"></i> <input
										class="form-control placeholder-no-fix" autocomplete="off"
										placeholder="Password" value="${bean.password}" name="bean.password" type="password">
								</div>
							</div>
							<div class="form-group">
								<label >appid</label>
								<div class="input-icon">
									 <input class="form-control placeholder-no-fix" autocomplete="off"
										placeholder="开发者凭据 appid" value="${bean.appid}" name="bean.appid" type="text">
								</div>
							</div>
							<div class="form-group">
								<label >AppSecret</label>
								<div class="input-icon">
									 <input class="form-control placeholder-no-fix" autocomplete="off"
										placeholder="开发者凭据 AppSecret" value="${bean.appsecret}" name="bean.appsecret" type="text">
								</div>
							</div>
							<div class="col-md-offset-4 form-group">
								<button href="javascript:void(0)" class="btn btn-primary">绑定</button>
							</div>
						</form>
					</div>

					<div class="col-md-6 well col-md-offset-1">
						<h4>公众帐号的服务器配置（用于接收用户信息）</h4>
						接口地址: ${basePath}/wxapi.ac<br>
						接口TOKEN:${bean.id}
					</div>
					<div class="col-md-6 well col-md-offset-1">
						<h4>开发者凭据</h4>
						AppId: 
						<span id="wx_app_id">${bean.appid}</span>
						<br> 
						AppSecret: 
						<span id="wx_app_secret">${bean.appsecret}</span>
					</div>
				</div>
			</div>
			<!-- END PAGE CONTENT-->
		</div>
		<!-- END PAGE -->
	</div>
	<!-- END CONTAINER -->
	<!-- BEGIN FOOTER -->
	<%@ include file="../include/footer.jsp"%>
	<!-- END FOOTER -->

</body>
<!-- END BODY -->
</html>
