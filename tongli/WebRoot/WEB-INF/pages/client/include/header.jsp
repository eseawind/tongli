<%--
/*
 * 头部共通部分画面
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-03-30  wuxiaogang   程序・发布
 * -------- ----------- ------------ ------------------------------------------
 * Copyright 2014 jfq System. - All Rights Reserved.
 *
 */
--%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" session="false"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
	request.setAttribute("path", path);
	request.setAttribute("basePath", basePath);
%>

<div class="top_line"></div>
<div class="nav_body">
	<div class="w">
		<div class="logo"></div>
		<div class="nav_con">
			<div class="login">
				<div class="loglink">
					<a href="${basePath}/c005_init.ac" target="_blank">登录</a> | <a
						href="${basePath}/c005_init.ac" target="_blank">注册</a>
				</div>
				<div class="shortcut">
					<select>
						<option selected value="我的俱乐部">我的俱乐部</option>
						<option value="我的课程表">我的课程表</option>
						<option value="我的评语">我的评语</option>
						<option value="我的记录">我的记录</option>
					</select>
				</div>
			</div>
			<div class="nav">
				<div id="navmenu">
					<ul>
						<li class="home"><a href="${basePath}/index.ac">首 页</a></li>
						<li class="about"><a href="${basePath}/c002_init.ac?id=cc273bdf05ff466cbbaddedb8071dda4">关于我们</a>
							<ul>
								<li><a href="#">• 俱乐部历史介绍</a></li>
							</ul></li>
						<li class="course"><a href="${basePath}/c002_init.ac?id=30b7f89148054a94801ce579df68e78c">课 程</a>
							<ul>
								<li><a href="#">• 游泳</a></li>
								<li><a href="#">• 网球</a></li>
								<li><a href="#">• 篮球</a></li>
								<li><a href="#">• 羽毛球</a></li>
								<li><a href="#">• 空手道</a></li>
								<li><a href="#">• 乒乓球</a></li>
								<li><a href="#">• 轮滑</a></li>
								<li><a href="#">• 舞蹈</a></li>
							</ul></li>
						<li class="camp"><a href="${basePath}/c002_init.ac?id=e90ac273a9534c90bc725112125b83ff">冬夏令营</a>
							<ul>
								<li><a href="#">• 夏令营</a></li>
								<li><a href="#">• 冬令营</a></li>
							</ul></li>
						<li class="contact"><a href="${basePath}/c002_init.ac?id=47d9cbde8f104c97b7e963548fa59ea5">联系我们</a>
							<ul>
								<li><a href="#">• 中心查询</a></li>
							</ul></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>
