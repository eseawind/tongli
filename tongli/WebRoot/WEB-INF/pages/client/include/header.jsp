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
					<a href="${basePath}/html/login.html" target="_blank">登录</a> | <a
						href="${basePath}/html/register.html" target="_blank">注册</a>
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
						<li class="about"><a href="${basePath}/c002_init.ac?tid=966a13c753f34faa927510c610b5e0b6&pid=966a13c753f34faa927510c610b5e0b6">关于我们</a>
						</li>
						<li class="course"><a href="${basePath}/c002_init.ac?tid=6690aceda07a405a9428e6e02ba2d416&pid=6690aceda07a405a9428e6e02ba2d416">课 程</a>
						</li>
						<li class="camp"><a href="${basePath}/c002_init.ac?tid=26f1017792024a358c73639b08e74393&pid=26f1017792024a358c73639b08e74393">冬夏令营</a>
						</li>
						<li class="contact"><a href="${basePath}/c002_init.ac?tid=966a13c753f34faa927510c610b5e0b6&pid=966a13c753f34faa927510c610b5e0b6">联系我们</a>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>
