<%--
/*
 * 头部共通部分画面
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-03-30  wuxiaogang   程序・发布
 * -------- ----------- ------------ ------------------------------------------
 * Copyright 2014 童励 System. - All Rights Reserved.
 *
 */
--%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" session="false"%>
<%@page import="cn.com.softvan.common.CommonConstant"%>
<%@page import="cn.com.softvan.bean.member.TcMemberBean"%>
<%
	String path = request.getContextPath();
	String basePath = null;
	if (request.getServerPort() != 80) {
		basePath = request.getScheme() + "://"
				+ request.getServerName() + ":"
				+ request.getServerPort() + path;
	} else {
		basePath = request.getScheme() + "://"
				+ request.getServerName() + path;
	}
	request.setAttribute("path", path);
	request.setAttribute("basePath", basePath);
%>
<div id="sidebar" class="page-sidebar">
	<div class="page-sidebar-scroll">
		<div class="sidebar-section">
			<p>童厉儿童俱乐部</p>
			<a href="#" class="sidebar-close"></a>
		</div>
		<div class="navigation-items">
			<div class="nav-item">
				<a href="${basePath}/w/index.ac?pid=sy" class="home-list">首页<em
					class=""></em></a>
			</div>
			<div class="nav-item">
				
					<%
				 	TcMemberBean member=(TcMemberBean)request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_MEMBER_INFO);
				 	TcMemberBean teacher=(TcMemberBean)request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_TEACHER_INFO);
						if ( member!= null) {
					%>
						<a href="${basePath}/w/m201_init.ac?pid=grzx" class="grzx briefcase-list">个人中心
						<em class="unselected-nav"></em></a>
					<%
						}else
						if (teacher != null) {
					%>
						<a href="${basePath}/w/t001_init.ac?pid=grzx" class="grzx briefcase-list">个人中心
						<em class="unselected-nav"></em></a>
					<%		
						}else{
					%>
						<a href="${basePath}/w/m201_init.ac?pid=grzx" class="grzx briefcase-list">个人中心
						<em class="unselected-nav"></em></a>
					<%
						}
					%>
					<%-- <%
						if ( member!= null) {
					%>
						<a  href="${basePath}/m201_logout.ac" >安全退出</a>
					<%
						}else
						if (teacher != null) {
					%>
						<a  href="${basePath}/t001_logout.ac" >安全退出</a>
					<%		
						}
					%> --%>
			</div>
			<div class="nav-item">
				<a
					href="${basePath}/w/c002_init.ac??tid=3f2b286347174e728d39169c212fe56b&pid=3f2b286347174e728d39169c212fe56b"
					class="3f2b286347174e728d39169c212fe56b bubble-list submenu-deploy">新闻资讯<em
					class="dropdown-nav"></em></a>
				<div class="nav-item-submenu">
				</div>
			</div>
			<div class="nav-item">
				<a
					href="${basePath}/w/c002_init.ac?tid=966a13c753f34faa927510c610b5e0b6&pid=966a13c753f34faa927510c610b5e0b6"
					class="966a13c753f34faa927510c610b5e0b6 document-list submenu-deploy">关于我们<em
					class="dropdown-nav"></em></a>
				<div class="nav-item-submenu">
				</div>
			</div>
			<div class="nav-item">
				<a
					href="${basePath}/w/c002_init.ac?tid=6690aceda07a405a9428e6e02ba2d416&pid=6690aceda07a405a9428e6e02ba2d416"
					class="6690aceda07a405a9428e6e02ba2d416 statistics-list submenu-deploy">童厉课程<em
					class="dropdown-nav"></em></a>
				<div class="nav-item-submenu">
				</div>
			</div>
			<div class="nav-item">
				<a
					href="${basePath}/w/c002_init.ac?tid=26f1017792024a358c73639b08e74393&pid=26f1017792024a358c73639b08e74393"
					class="26f1017792024a358c73639b08e74393  help-list submenu-deploy">冬夏令营<em
					class="dropdown-nav"></em></a>
				<div class="nav-item-submenu">
				</div>
			</div>
			<div class="nav-item">
				<a href="${basePath}/w/c202_init.ac?pid=yycg"
					class="yycg clipboard-list">预约体验<em class="unselected-nav"></em></a>
			</div>
			<div class="nav-item">
				<a href="${basePath}/w/c203_init.ac?pid=zxbm"
					class="zxbm monitor-list">在线报名<em class="unselected-nav"></em></a>
			</div>
			<div class="nav-item">
				<a href="#" class="close-nav">Close<em class="unselected-nav"></em></a>
			</div>
			<div class="sidebar-decoration"></div>
		</div>
		<div class="sidebar-section copyright-sidebar">
			<p>童励儿童俱乐部 版权所有</p>
		</div>
	</div>
</div>