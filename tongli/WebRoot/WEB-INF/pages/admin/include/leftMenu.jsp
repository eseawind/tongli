<%--
/*
 * 底部共通部分画面
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-03-03  wuxiaogang        程序・发布
 * -------- ----------- ------------ ------------------------------------------
 * Copyright 2014 车主管家 System. - All Rights Reserved.
 *
 */
--%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" session="false"%>
<div class="page-sidebar navbar-collapse collapse">
			<!-- BEGIN SIDEBAR MENU -->        
			<ul class="page-sidebar-menu">
				<li>
					<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
					<div class="sidebar-toggler hidden-phone"></div>
					<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
				</li>
				<li>
					<!-- BEGIN RESPONSIVE QUICK SEARCH FORM -->
					<form class="sidebar-search" action="#" method="POST">
						<div class="form-container">
							<div class="input-box">
								<a href="javascript:;" class="remove"></a>
								<input placeholder="Search..." type="text">
								<input class="submit" value=" " type="button">
							</div>
						</div>
					</form>
					<!-- END RESPONSIVE QUICK SEARCH FORM -->
				</li>
				<li id="home_init" class="">
					<a href="${basePath}/home_init.ac">
					<i class="fa fa-home"></i> 
					系统主页
					</a>
				</li>
				<li id="wechar" class="">
					<a class="" href="javascript:;">
					<i class="fa fa-leaf"></i> 
					<span class="title">微信服务</span>
					<span id="wechar_arrow" class="arrow"></span>
					</a>
					<ul id="wechar_sub_menu" style="display: none;" class="sub-menu">
						<li id="w004_init">
							<a href="${basePath}/h/w004_init.ac">
							文章管理
							</a>
						</li>
						<li id="wechar_sub_menu_li" class="">
							<a href="javascript:;">
							自动回复(关键字)
							<span id="wechar_sub_menu_li_arrow" class="arrow"></span>
							</a>
							<ul id="wechar_sub_menu_li_sub_menu" style="display: none;" class="sub-menu">
								<li id="wechar_sub_menu_li_sub_menu_1"><a href="${basePath}/h/w002_init.ac">图文</a></li>
								<li id="wechar_sub_menu_li_sub_menu_2"><a href="${basePath}/h/w003_init.ac">图片</a></li>
								<li id="wechar_sub_menu_li_sub_menu_3"><a href="${basePath}/h/w009_init.ac">文本</a></li>
								<li id="wechar_sub_menu_li_sub_menu_4"><a href="${basePath}/h/w005_init.ac">音乐</a></li>
								<li id="wechar_sub_menu_li_sub_menu_10"><a href="#" onclick="myAlert('功能即将开放')">语音</a></li>
								<li id="wechar_sub_menu_li_sub_menu_5"><a href="#" onclick="myAlert('功能即将开放')">视频</a></li>
							</ul>
						</li>
						<li id="w010_init">
							<a href="${basePath}/h/w010_init.ac">
							信息接收
							</a>
						</li>
						<!-- 
						<li class="">
							<a href="javascript:;">
							消息接收(普通消息)
							<span class="arrow"></span>
							</a>
							<ul style="display: none;" class="sub-menu">
								<li><a href="#" onclick="myAlert('功能即将开放')">文本</a></li>
								<li><a href="#" onclick="myAlert('功能即将开放')">图片</a></li>
								<li><a href="#" onclick="myAlert('功能即将开放')">语音</a></li>
								<li><a href="#" onclick="myAlert('功能即将开放')">视频</a></li>
								<li><a href="#" onclick="myAlert('功能即将开放')">地理位置</a></li>
								<li><a href="#" onclick="myAlert('功能即将开放')">链接</a></li>
							</ul>
						</li>
						<li class="">
							<a href="javascript:;">
							消息接收(事件推送)
							<span class="arrow"></span>
							</a>
							<ul style="display: none;" class="sub-menu">
								<li><a href="#" onclick="myAlert('功能即将开放')">关注/取消事件</a></li>
								<li><a href="#" onclick="myAlert('功能即将开放')">扫描带参数二维码事件</a></li>
								<li><a href="#" onclick="myAlert('功能即将开放')">上报地理位置事件</a></li>
								<li><a href="#" onclick="myAlert('功能即将开放')">自定义菜单事件</a></li>
							</ul>
						</li>
						 -->
						<li class="">
							<a href="#" onclick="myAlert('功能即将开放')">
							客服响应
							</a>
						</li>
						<li id="w012_init">
							<a href="${basePath}/h/w012_init.ac">
							用户管理
							</a>
						</li>
						<li id="w007_init">
							<a href="${basePath}/h/w007_init.ac">
							自定义菜单
							</a>
						</li>
					</ul>
				</li>
				<li class="">
					<a class="active" href="javascript:;">
					<i class="fa fa-cogs"></i> 
					<span class="title">车主管家</span>
					<span class="arrow "></span>
					</a>
					<ul style="display: none;" class="sub-menu">
						<li>
							<a href="#" onclick="myAlert('功能即将开放')">
							功能1
							</a>
						</li>
					</ul>
				</li>
				<li class="">
					<a class="active" href="javascript:;">
					<i class="fa fa-bar-chart-o"></i> 
					<span class="title">系统日志</span>
					<span class="arrow"></span>
					</a>
					<ul style="display: none;" class="sub-menu">
						<li>
							<a href="#" onclick="myAlert('功能即将开放')">
							管理员操作日志
							</a>
						</li>
						<li>
							<a href="#" onclick="myAlert('功能即将开放')">
							系统运行日志
							</a>
						</li>
					</ul>
				</li>
			</ul>
			<!-- END SIDEBAR MENU -->
		</div>
