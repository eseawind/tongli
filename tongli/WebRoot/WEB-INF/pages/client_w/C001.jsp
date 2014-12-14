<%--
/*
 * 首页
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-04-11  wuxiaogang        程序・发布
 * -------- ----------- ------------ ------------------------------------------
 * Copyright 2014 wechat System. - All Rights Reserved.
 *
 */
--%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="include/title_meta.jsp"%>
<%@ include file="include/public_js_css.jsp"%>
</head>
<body >
	<div class="all-elements">
		<%@ include file="include/header.jsp"%>
		<div id="content" class="page-content">
			<div class="page-header">
				<a href="#" class="deploy-sidebar"></a>
				<p class="bread-crumb">童励儿童俱乐部</p>
				<a href="javascript:history.go(-1);" class="deploy-contact left-list"></a>
			</div>
			<div class="content">
				<div class="slider-controls" data-snap-ignore="true">  
              	 <c:forEach items="${beans}" var="bean" varStatus="i">
					<div>
						<img  onerror="this.src='${basePath}/images/error/404.png';this.onerror='';" src="${fn:replace(fn:replace(bean.pic_url, "/n3", "/n2"), "/n4", "/n2")}" class="responsive-image" alt="img"  />
					</div>
				</c:forEach>              
               </div>
				<div class="decoration"></div>
				<div class="i_menu">
					<%
						if ( member!= null) {
					%>
						<a class="i_menu_item a_1 br_t" href="${basePath}/w/m201_init.ac?pid=grzx"><i class="icon1"></i><!-- 个人中心 --></a> 
					<%
						}else
						if (teacher != null) {
					%>
						<a class="i_menu_item a_1 br_t" href="${basePath}/w/t001_init.ac?pid=grzx"><i class="icon1"></i><!-- 个人中心 --></a> 
					<%		
						}else{
					%>
						<a class="i_menu_item a_1 br_t" href="${basePath}/w/m201_init.ac?pid=grzx"><i class="icon1"></i><!-- 个人中心 --></a> 
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
					<a class="i_menu_item a_2 br_t" href="${basePath}/w/c002_init.ac??tid=3f2b286347174e728d39169c212fe56b&pid=3f2b286347174e728d39169c212fe56b"><i class="icon2"></i><!-- 新闻资讯 --></a> 
					<a class="i_menu_item a_3" href="${basePath}/w/c002_init.ac?tid=6690aceda07a405a9428e6e02ba2d416&pid=6690aceda07a405a9428e6e02ba2d416"><i class="icon3"></i><!-- 童厉课程 --></a> 
					<a class="i_menu_item a_4" href="${basePath}/w/c002_init.ac?tid=26f1017792024a358c73639b08e74393&pid=26f1017792024a358c73639b08e74393"><i class="icon4"></i><!-- 冬夏令营 --></a> 
					<a class="i_menu_item a_5" href="${basePath}/w/c202_init.ac?pid=yycg"><i class="icon5"></i><!-- 预约体验 --></a>
					<a class="i_menu_item a_6" href="${basePath}/w/c203_init.ac?pid=zxbm"><i class="icon6"></i><!-- 在线报名 --></a> 
					<div class="clear"></div>
				</div>
				<!-- BEGIN FOOTER -->
				<%@ include file="include/footer.jsp"%>
				<!-- END FOOTER -->
			</div>
		</div>
	</div>
</body>
</html>
