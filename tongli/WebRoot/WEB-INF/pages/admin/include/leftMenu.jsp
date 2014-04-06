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
				<li id="wechat" class="">
					<a class="" href="javascript:;">
					<i class="fa fa-leaf"></i> 
					<span class="title">微信服务</span>
					<span id="wechat_arrow" class="arrow"></span>
					</a>
					<ul id="wechat_sub_menu" style="display: none;" class="sub-menu">
						<li id="w004_init">
							<a href="${basePath}/h/w004_init.ac">
							文章管理
							</a>
						</li>
						<li id="wechat_sub_menu_li" class="">
							<a href="javascript:;">
							自动回复(关键字)
							<span id="wechat_sub_menu_li_arrow" class="arrow"></span>
							</a>
							<ul id="wechat_sub_menu_li_sub_menu" style="display: none;" class="sub-menu">
								<li id="wechat_sub_menu_li_sub_menu_1"><a href="${basePath}/h/w002_init.ac">图文</a></li>
								<li id="wechat_sub_menu_li_sub_menu_2"><a href="${basePath}/h/w003_init.ac">图片</a></li>
								<li id="wechat_sub_menu_li_sub_menu_3"><a href="${basePath}/h/w009_init.ac">文本</a></li>
								<li id="wechat_sub_menu_li_sub_menu_4"><a href="#"  onclick="myAlert('功能即将开放')">音乐</a></li><!-- ${basePath}/h/w005_init.ac -->
								<li id="wechat_sub_menu_li_sub_menu_10"><a href="#" onclick="myAlert('功能即将开放')">语音</a></li>
								<li id="wechat_sub_menu_li_sub_menu_5"><a href="#" onclick="myAlert('功能即将开放')">视频</a></li>
							</ul>
						</li>
						<li id="w010_init">
							<a href="${basePath}/h/w010_init.ac">
							信息接收
							</a>
						</li>
						<li id="w013_init">
							<a href="${basePath}/h/w013_init.ac">
							客服响应
							</a>
						</li>
						<li id="w012_init">
							<a href="${basePath}/h/w012_init.ac">
							粉丝列表
							</a>
						</li>
						<li id="w007_init">
							<a href="${basePath}/h/w007_init.ac">
							自定义菜单
							</a>
						</li>
					</ul>
				</li>
				<li id="course" class="">
					<a class="active" href="javascript:;">
					<i class="fa fa-flag"></i> 
					<span class="title">课程管理</span>
					<span id="course_arrow" class="arrow "></span>
					</a>
					<ul id="course_sub_menu" style="display: none;" class="sub-menu">
						<li id="course_sub_menu_l1">
							<a   href="${basePath}/h/c101_init.ac">
							课程管理
							</a>
						</li>
						<li id="course_sub_menu_l2">
							<a  href="${basePath}/h/c101_recycle.ac">
							课程回收站
							</a>
						</li>
						<li id="course_sub_menu_l3">
							<a   href="${basePath}/h/c102_init.ac">
							课程表管理
							</a>
						</li>
						<li id="course_sub_menu_l4">
							<a  href="${basePath}/h/c102_recycle.ac">
							课程表回收站
							</a>
						</li>
					</ul>
				</li>
				<li id="member" class="">
					<a class="active" href="javascript:;">
					<i class="fa fa-cogs"></i> 
					<span class="title">会员管理</span>
					<span id="member_arrow" class="arrow "></span>
					</a>
					<ul id="member_sub_menu" style="display: none;" class="sub-menu">
						<li id="member_sub_menu_l1" >
							<a href="${basePath}/h/m001_init.ac" >
							会员管理
							</a>
						</li>
						<li id="member_sub_menu_l2" >
							<a href="${basePath}/h/m001_init.ac" >
							会员回收站
							</a>
						</li>
					</ul>
				</li>
				<li id="student" class="">
					<a class="active" href="javascript:;">
					<i class="fa fa-pagelines"></i> 
					<span class="title">学员管理</span>
					<span id="student_arrow" class="arrow "></span>
					</a>
					<ul id="student_sub_menu" style="display: none;" class="sub-menu">
						<li id="student_sub_menu_l1" >
							<a href="${basePath}/h/s201_init.ac" >
							学员管理
							</a>
						</li>
						<li id="student_sub_menu_l2" >
							<a href="${basePath}/h/s201_init.ac" >
							学员回收站
							</a>
						</li>
					</ul>
				</li>
				<li id="sys" class="">
					<a class="active" href="javascript:;">
					<i class="fa fa-bar-chart-o"></i> 
					<span class="title">资讯管理</span>
					<span id="sys_arrow" class="arrow"></span>
					</a>
					<ul id="sys_sub_menu" style="display: none;" class="sub-menu">
						<li id="sys_sub_menu_l1_sub_menu_l0"><a href="${basePath }/h/s002_init.ac">栏目管理</a></li>
						<li id="sys_sub_menu_l1_sub_menu_l1"><a href="${basePath }/h/s001_init.ac">资讯管理</a></li>
						<li id="sys_sub_menu_l1_sub_menu_l2"><a href="${basePath }/h/s001_recycle.ac">资讯回收站</a></li>
					</ul>
				</li>
				<li id="sys3" class="">
					<a class="active" href="javascript:;">
					<i class="fa fa-coffee"></i> 
					<span class="title">友情链接</span>
					<span id="sys3_arrow" class="arrow"></span>
					</a>
					<ul id="sys3_sub_menu" style="display: none;" class="sub-menu">
						<li id="sys3_sub_menu_l1_sub_menu_l1"><a href="${basePath }/h/s003_init.ac">信息管理</a></li>
						<li id="sys3_sub_menu_l1_sub_menu_l2"><a href="${basePath }/h/s003_recycle.ac">信息回收站</a></li>
					</ul>
				</li>
				<li id="sys4" class="">
					<a class="active" href="javascript:;">
					<i class="fa fa-bullhorn"></i> 
					<span class="title">投诉建议</span>
					<span id="sys4_arrow" class="arrow"></span>
					</a>
					<ul id="sys3_sub_menu" style="display: none;" class="sub-menu">
						<li id="sys4_sub_menu_l1_sub_menu_l1"><a href="${basePath }/h/t001_init.ac">信息管理</a></li>
						<li id="sys4_sub_menu_l1_sub_menu_l2"><a href="${basePath }/h/t001_recycle.ac">信息回收站</a></li>
					</ul>
				</li>
			</ul>
			<!-- END SIDEBAR MENU -->
		</div>

