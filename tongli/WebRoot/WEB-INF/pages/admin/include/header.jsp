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
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
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
					<a class="dropdown-toggle" href="${basePath}/index.ac">
					<i class="fa fa-home"></i>首页
					</a>
				</li>
				<li class="dropdown">
					<a class="dropdown-toggle" href="${basePath}/h/w001_init.ac">
					<i class="fa fa-user"></i>微信公共号绑定
					</a>
				</li>
				<!-- BEGIN NOTIFICATION DROPDOWN -->
				<li class="dropdown" id="header_notification_bar">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
					<i class="fa fa-warning"></i>
					<span class="badge">6</span>
					</a>
					<ul class="dropdown-menu extended notification">
						<li>
							<p>You have 14 new notifications</p>
						</li>
						<li>
							<div style="position: relative; overflow: hidden; width: auto; height: 250px;" class="slimScrollDiv"><ul class="dropdown-menu-list scroller" style="height: 250px; overflow: hidden; width: auto;">
								<li>
									<a href="#">
									<span class="label label-icon label-success"><i class="fa fa-plus"></i></span>
									New user registered. 
									<span class="time">Just now</span>
									</a>
								</li>
								<li>
									<a href="#">
									<span class="label label-icon label-danger"><i class="fa fa-bolt"></i></span>
									Server #12 overloaded. 
									<span class="time">15 mins</span>
									</a>
								</li>
								<li>
									<a href="#">
									<span class="label label-icon label-warning"><i class="fa fa-bell-o"></i></span>
									Server #2 not responding.
									<span class="time">22 mins</span>
									</a>
								</li>
								<li>
									<a href="#">
									<span class="label label-icon label-info"><i class="fa fa-bullhorn"></i></span>
									Application error.
									<span class="time">40 mins</span>
									</a>
								</li>
								<li>
									<a href="#">
									<span class="label label-icon label-danger"><i class="fa fa-bolt"></i></span>
									Database overloaded 68%. 
									<span class="time">2 hrs</span>
									</a>
								</li>
								<li>
									<a href="#">
									<span class="label label-icon label-danger"><i class="fa fa-bolt"></i></span>
									2 user IP blocked.
									<span class="time">5 hrs</span>
									</a>
								</li>
								<li>
									<a href="#">
									<span class="label label-icon label-warning"><i class="fa fa-bell-o"></i></span>
									Storage Server #4 not responding.
									<span class="time">45 mins</span>
									</a>
								</li>
								<li>
									<a href="#">
									<span class="label label-icon label-info"><i class="fa fa-bullhorn"></i></span>
									System Error.
									<span class="time">55 mins</span>
									</a>
								</li>
								<li>
									<a href="#">
									<span class="label label-icon label-danger"><i class="fa fa-bolt"></i></span>
									Database overloaded 68%. 
									<span class="time">2 hrs</span>
									</a>
								</li>
							</ul><div style="background: none repeat scroll 0% 0% rgb(161, 178, 189); width: 7px; position: absolute; top: 0px; opacity: 0.4; display: block; border-radius: 7px; z-index: 99; right: 1px;" class="slimScrollBar"></div><div style="width: 7px; height: 100%; position: absolute; top: 0px; display: none; border-radius: 7px; background: none repeat scroll 0% 0% rgb(51, 51, 51); opacity: 0.2; z-index: 90; right: 1px;" class="slimScrollRail"></div></div>
						</li>
						<li class="external">
							<a href="#">See all notifications <i class="m-icon-swapright"></i></a>
						</li>
					</ul>
				</li>
				<!-- END NOTIFICATION DROPDOWN -->
				<!-- BEGIN INBOX DROPDOWN -->
				<li class="dropdown" id="header_inbox_bar">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
					<i class="fa fa-envelope"></i>
					<span class="badge">5</span>
					</a>
					<ul class="dropdown-menu extended inbox">
						<li>
							<p>You have 12 new messages</p>
						</li>
						<li>
							<div style="position: relative; overflow: hidden; width: auto; height: 250px;" class="slimScrollDiv"><ul class="dropdown-menu-list scroller" style="height: 250px; overflow: hidden; width: auto;">
								<li>
									<a href="inbox.html?a=view">
									<span class="photo"><img src="${basePath}/plugins/bootstrap.admin.theme/assets/img/avatar2.jpg" alt=""></span>
									<span class="subject">
									<span class="from">Lisa Wong</span>
									<span class="time">Just Now</span>
									</span>
									<span class="message">
									Vivamus sed auctor nibh congue nibh. auctor nibh
									auctor nibh...
									</span>  
									</a>
								</li>
								<li>
									<a href="inbox.html?a=view">
									<span class="photo"><img src="${basePath}/plugins/bootstrap.admin.theme/assets/img/avatar3.jpg" alt=""></span>
									<span class="subject">
									<span class="from">Richard Doe</span>
									<span class="time">16 mins</span>
									</span>
									<span class="message">
									Vivamus sed congue nibh auctor nibh congue nibh. auctor nibh
									auctor nibh...
									</span>  
									</a>
								</li>
								<li>
									<a href="inbox.html?a=view">
									<span class="photo"><img src="${basePath}/plugins/bootstrap.admin.theme/assets/img/avatar1.jpg" alt=""></span>
									<span class="subject">
									<span class="from">Bob Nilson</span>
									<span class="time">2 hrs</span>
									</span>
									<span class="message">
									Vivamus sed nibh auctor nibh congue nibh. auctor nibh
									auctor nibh...
									</span>  
									</a>
								</li>
								<li>
									<a href="inbox.html?a=view">
									<span class="photo"><img src="${basePath}/plugins/bootstrap.admin.theme/assets/img/avatar2.jpg" alt=""></span>
									<span class="subject">
									<span class="from">Lisa Wong</span>
									<span class="time">40 mins</span>
									</span>
									<span class="message">
									Vivamus sed auctor 40% nibh congue nibh...
									</span>  
									</a>
								</li>
								<li>
									<a href="inbox.html?a=view">
									<span class="photo"><img src="${basePath}/plugins/bootstrap.admin.theme/assets/img/avatar3.jpg" alt=""></span>
									<span class="subject">
									<span class="from">Richard Doe</span>
									<span class="time">46 mins</span>
									</span>
									<span class="message">
									Vivamus sed congue nibh auctor nibh congue nibh. auctor nibh
									auctor nibh...
									</span>  
									</a>
								</li>
							</ul><div style="background: none repeat scroll 0% 0% rgb(161, 178, 189); width: 7px; position: absolute; top: 0px; opacity: 0.4; display: block; border-radius: 7px; z-index: 99; right: 1px;" class="slimScrollBar"></div><div style="width: 7px; height: 100%; position: absolute; top: 0px; display: none; border-radius: 7px; background: none repeat scroll 0% 0% rgb(51, 51, 51); opacity: 0.2; z-index: 90; right: 1px;" class="slimScrollRail"></div></div>
						</li>
						<li class="external">
							<a href="inbox.html">See all messages <i class="m-icon-swapright"></i></a>
						</li>
					</ul>
				</li>
				<!-- END INBOX DROPDOWN -->
				<!-- BEGIN TODO DROPDOWN -->
				<li class="dropdown" id="header_task_bar">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
					<i class="fa fa-tasks"></i>
					<span class="badge">5</span>
					</a>
					<ul class="dropdown-menu extended tasks">
						<li>
							<p>You have 12 pending tasks</p>
						</li>
						<li>
							<div style="position: relative; overflow: hidden; width: auto; height: 250px;" class="slimScrollDiv"><ul class="dropdown-menu-list scroller" style="height: 250px; overflow: hidden; width: auto;">
								<li>
									<a href="#">
									<span class="task">
									<span class="desc">New release v1.2</span>
									<span class="percent">30%</span>
									</span>
									<span class="progress">
									<span style="width: 40%;" class="progress-bar progress-bar-success" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100">
									<span class="sr-only">40% Complete</span>
									</span>
									</span>
									</a>
								</li>
								<li>
									<a href="#">
									<span class="task">
									<span class="desc">Application deployment</span>
									<span class="percent">65%</span>
									</span>
									<span class="progress progress-striped">
									<span style="width: 65%;" class="progress-bar progress-bar-danger" aria-valuenow="65" aria-valuemin="0" aria-valuemax="100">
									<span class="sr-only">65% Complete</span>
									</span>
									</span>
									</a>
								</li>
								<li>
									<a href="#">
									<span class="task">
									<span class="desc">Mobile app release</span>
									<span class="percent">98%</span>
									</span>
									<span class="progress">
									<span style="width: 98%;" class="progress-bar progress-bar-success" aria-valuenow="98" aria-valuemin="0" aria-valuemax="100">
									<span class="sr-only">98% Complete</span>
									</span>
									</span>
									</a>
								</li>
								<li>
									<a href="#">
									<span class="task">
									<span class="desc">Database migration</span>
									<span class="percent">10%</span>
									</span>
									<span class="progress progress-striped">
									<span style="width: 10%;" class="progress-bar progress-bar-warning" aria-valuenow="10" aria-valuemin="0" aria-valuemax="100">
									<span class="sr-only">10% Complete</span>
									</span>
									</span>
									</a>
								</li>
								<li>
									<a href="#">
									<span class="task">
									<span class="desc">Web server upgrade</span>
									<span class="percent">58%</span>
									</span>
									<span class="progress progress-striped">
									<span style="width: 58%;" class="progress-bar progress-bar-info" aria-valuenow="58" aria-valuemin="0" aria-valuemax="100">
									<span class="sr-only">58% Complete</span>
									</span>
									</span>
									</a>
								</li>
								<li>
									<a href="#">
									<span class="task">
									<span class="desc">Mobile development</span>
									<span class="percent">85%</span>
									</span>
									<span class="progress progress-striped">
									<span style="width: 85%;" class="progress-bar progress-bar-success" aria-valuenow="85" aria-valuemin="0" aria-valuemax="100">
									<span class="sr-only">85% Complete</span>
									</span>
									</span>
									</a>
								</li>
								<li>
									<a href="#">
									<span class="task">
									<span class="desc">New UI release</span>
									<span class="percent">18%</span>
									</span>
									<span class="progress progress-striped">
									<span style="width: 18%;" class="progress-bar progress-bar-important" aria-valuenow="18" aria-valuemin="0" aria-valuemax="100">
									<span class="sr-only">18% Complete</span>
									</span>
									</span>
									</a>
								</li>
							</ul><div style="background: none repeat scroll 0% 0% rgb(161, 178, 189); width: 7px; position: absolute; top: 0px; opacity: 0.4; display: block; border-radius: 7px; z-index: 99; right: 1px;" class="slimScrollBar"></div><div style="width: 7px; height: 100%; position: absolute; top: 0px; display: none; border-radius: 7px; background: none repeat scroll 0% 0% rgb(51, 51, 51); opacity: 0.2; z-index: 90; right: 1px;" class="slimScrollRail"></div></div>
						</li>
						<li class="external">
							<a href="#">See all tasks <i class="m-icon-swapright"></i></a>
						</li>
					</ul>
				</li>
				<!-- END TODO DROPDOWN -->
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
						<li><a href="${basePath}/plugins/bootstrap.admin.theme/page_calendar.html"><i class="fa fa-calendar"></i> 工作日历</a></li>
						<li><a href="#"><i class="fa fa-tasks"></i> 帮助 </a></li>
						<li><a href="${basePath}/h/w003_uc.ac" target="_blank"><i class="fa fa-tasks"></i> xx </a></li>
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