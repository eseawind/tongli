<%--
/*
 * 头部共通部分画面
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-02-28  wuxiaogang   程序・发布
 * -------- ----------- ------------ ------------------------------------------
 * Copyright 2014 童励 System. - All Rights Reserved.
 *
 */
--%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" session="false"%>
<%@page import="cn.com.softvan.common.CommonConstant"%>
<%@page import="cn.com.softvan.bean.BaseUserBean"%>
<%
String path = request.getContextPath();
String basePath = null;
if (request.getServerPort() != 80) {
	basePath = request.getScheme() + "://" + request.getServerName() 
			+ ":" + request.getServerPort() + path;	
} else {
	basePath = request.getScheme() + "://" + request.getServerName() + path;
}
request.setAttribute("path", path);
request.setAttribute("basePath", basePath);
%>
<div class="header navbar navbar-inverse navbar-fixed-top">
		<!-- BEGIN TOP NAVIGATION BAR -->
		<div class="header-inner">
			<!-- BEGIN LOGO -->
			<a class="navbar-brand" href="${basePath}/h/home_init.ac">
			童励俱乐部V1.0
			</a>
			<!-- END LOGO -->
			<!-- BEGIN RESPONSIVE MENU TOGGLER -->
			<a href="javascript:;" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
			<img src="${basePath}/plugins/bootstrap.admin.theme/assets/img/menu-toggler.png" alt="">
			</a> 
			<!-- END RESPONSIVE MENU TOGGLER -->
			<!-- BEGIN TOP NAVIGATION MENU -->
			<ul class="nav navbar-nav pull-right">
				<li class="dropdown">
					<a class="dropdown-toggle" href="${basePath}/index.ac" target="_blank">
					<i class="fa fa-home"></i>首页
					</a>
				</li>
				<li class="dropdown">
					<a class="dropdown-toggle" href="${basePath}/h/w001_init.ac">
					<i class="fa fa-user"></i>微信公共号绑定
					</a>
				</li>
				<!-- BEGIN USER LOGIN DROPDOWN -->
				<li class="dropdown user">
					 <%
						BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER);
					if (user != null) {
					%>
					<a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
					<img alt="" src="${basePath}/plugins/bootstrap.admin.theme/assets/img/avatar1_small.jpg">
					<span class="username hidden-480"><%=user.getUser_id()%></span>
					<i class="fa fa-angle-down"></i>
					</a>
					<ul class="dropdown-menu">
						<li><a href="${basePath}/plugins/bootstrap.admin.theme/extra_profile.html"><i class="fa fa-cogs"></i> 信息修改</a></li>
						<li><a href="${basePath}/h/w003_uc.ac" target="_blank"><i class="fa fa-tasks"></i> 缓存更新 </a></li>
						<li class="divider"></li>
						<li><a href="javascript:;" id="trigger_fullscreen"><i class="fa fa-move"></i> 全屏展示</a></li>
						<li><a href="<%=basePath%>/home_logout.ac"><i class="fa fa-key"></i> 安全退出</a></li>
					</ul>
					 <%
						}
					%>
				</li>
				<!-- END USER LOGIN DROPDOWN -->
			</ul>
			<!-- END TOP NAVIGATION MENU -->
		</div>
		<!-- END TOP NAVIGATION BAR -->
	</div>