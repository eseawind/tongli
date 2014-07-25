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
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" session="false"%>
<%@page import="cn.com.softvan.common.CommonConstant"%>
<%@page import="cn.com.softvan.bean.member.TcMemberBean"%>
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

<div class="header_1">
	<div class="nav_top">
	<div id="top" style=" border:1px solid #fff;"> 
             <div id="menu_bar">
                <div class="back">
                 <%
				 	TcMemberBean member=(TcMemberBean)request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_MEMBER_INFO);
				 	TcMemberBean teacher=(TcMemberBean)request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_TEACHER_INFO);
						if ( member!= null) {
					%>
						<a  class="maLeft10" href="${basePath}/m201_init.ac" >会员个人中心</a>
					<%
						}else
						if (teacher != null) {
					%>
						<a  class="maLeft10" href="${basePath}/t001_init.ac" >教师个人中心</a>
					<%		
						}else{
					%>
						<a  class="maLeft10" href="${basePath}/m201_init.ac" >会员登录</a>
					<%
						}
					%>
					<%
						if ( member!= null) {
					%>
						<a  class="maLeft10" href="${basePath}/m201_logout.ac" >安全退出</a>
					<%
						}else
						if (teacher != null) {
					%>
						<a  class="maLeft10" href="${basePath}/t001_logout.ac" >安全退出</a>
					<%		
						}
					%>
                </div>
             </div>
    </div>
	</div>
	<div class="w1000">
		<div class="nav_box">
			<div class="logo"><img alt="" src="${basePath}/css/images/logo.jpg" style="width:193px; height:75px;"></div>
			<div class="nav_body">
				<ul id="navmenu">
					<li class="sy" style="padding: 2px 10px"><a href="${basePath}/index.ac?pid=sy">首 页</a></li>
					<li class="966a13c753f34faa927510c610b5e0b6"><a href="${basePath}/c002_init.ac?tid=966a13c753f34faa927510c610b5e0b6&pid=966a13c753f34faa927510c610b5e0b6">关于我们</a>
						<div class="subMenu" style="margin-left: -13px;"></div>
					</li>
					<li class="6690aceda07a405a9428e6e02ba2d416"><a href="${basePath}/c002_init.ac?tid=6690aceda07a405a9428e6e02ba2d416&pid=6690aceda07a405a9428e6e02ba2d416">童励课程</a>
						<div class="subMenu" style="margin-left: -200px;"></div>
					</li>
					<li class="26f1017792024a358c73639b08e74393"><a href="${basePath}/c002_init.ac?tid=26f1017792024a358c73639b08e74393&pid=26f1017792024a358c73639b08e74393">冬夏令营</a>
						<div class="subMenu" style="margin-left: -150px;"></div>
					</li>
					<li class="yycg"><a href="${basePath}/c202_init.ac?pid=yycg">预约体验</a></li>
					<li class="zxbm"><a href="${basePath}/c203_init.ac?pid=zxbm">在线报名</a></li>
				</ul>
			</div>

		</div>
	</div>
</div>