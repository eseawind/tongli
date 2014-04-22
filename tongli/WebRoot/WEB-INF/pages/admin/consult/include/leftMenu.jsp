<%--
/*
 * 客服 底部共通部分画面
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-04-18  wuxiaogang        程序・发布
 * -------- ----------- ------------ ------------------------------------------
 * Copyright 2014 童励 System. - All Rights Reserved.
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
				<li id="consult">
					<a class="active" href="javascript:;">
					<i class="fa fa-comment-o"></i> 
					<span class="title">客服响应</span>
					<span id="consult_arrow" class="arrow "></span>
					</a>
					<ul id="consult_sub_menu" style="display: none;" class="sub-menu">
						<li id="consult_sub_menu_l1">
							<a  href="javascript:;" onclick="location.href='${basePath}/h/c301_init.ac'">
								咨询队列
							</a>
						</li>
						<li id="consult_sub_menu_l2">
							<a   href="javascript:;" onclick="location.href='${basePath}/h/c301_list1.ac'">
								我的咨询
							</a>
						</li>
					</ul>
				</li>
			</ul>
			<!-- END SIDEBAR MENU -->
		</div>
