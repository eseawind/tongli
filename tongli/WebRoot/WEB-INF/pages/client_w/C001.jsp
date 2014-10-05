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
<%@page import="cn.com.softvan.common.CommonConstant"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="include/title_meta.jsp"%>
<%@ include file="include/public_js_css.jsp"%>
<link rel="stylesheet" href="${basePath}/css/w_style.css" type="text/css">
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
						<img src="${bean.pic_url}" class="responsive-image" alt="img" />
					</div>
				</c:forEach>              
               </div>
               <a href="#" class="next-slider"></a>
               <a href="#" class="prev-slider"></a>
				<div class="decoration"></div>
				<div class="i_menu">
					<a class="i_menu_item br_t" href="${basePath}/w/index.ac?pid=sy"><i class="icon3"></i>首页</a> 
					<a class="i_menu_item br_t" href="${basePath}/w/m201_init.ac"><i class="icon8"></i>个人中心</a> 
					<a class="i_menu_item br_t" href="${basePath}/w/c002_init.ac??tid=3f2b286347174e728d39169c212fe56b&pid=3f2b286347174e728d39169c212fe56b"><i class="icon1"></i>新闻资讯</a> 
					<a class="i_menu_item" href="${basePath}/w/c002_init.ac?tid=966a13c753f34faa927510c610b5e0b6&pid=966a13c753f34faa927510c610b5e0b6"><i class="icon4"></i>关于我们</a> 
					<a class="i_menu_item" href="${basePath}/w/c002_init.ac?tid=6690aceda07a405a9428e6e02ba2d416&pid=6690aceda07a405a9428e6e02ba2d416"><i class="icon2"></i>童厉课程</a> 
					<a class="i_menu_item" href="${basePath}/w/c002_init.ac?tid=26f1017792024a358c73639b08e74393&pid=26f1017792024a358c73639b08e74393"><i class="icon7"></i>冬夏令营</a> 
					<a class="i_menu_item" href="${basePath}/w/c202_init.ac?pid=yycg"><i class="icon5"></i>预约参观</a>
					<a class="i_menu_item" href="${basePath}/w/c203_init.ac?pid=zxbm"><i class="icon6"></i>在线报名</a> 
					<a class="i_menu_item" href=""><i class="icon9"></i>警民互动</a>
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
