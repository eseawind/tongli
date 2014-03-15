<%--
/*
 * 车主管家后台主页(初始化页面)
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-03-04  wuxiaogang   程序・发布
 * -------- ----------- ------------ ------------------------------------------
 * Copyright 2014 车主管家 System. - All Rights Reserved.
 *
 */
--%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" session="false"%>
<%@page import="cn.com.softvan.common.CommonConstant"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
</head>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="zh-CN" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="zh-CN" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="zh-CN" class="no-js"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
	<meta charset="utf-8" />
	<title>管理主页【车主管家】</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta content="width=device-width, initial-scale=1.0" name="viewport" />
	<meta content="" name="description" />
	<meta content="" name="author" />
	<meta name="MobileOptimized" content="320">
	<!-- BEGIN GLOBAL MANDATORY STYLES -->      
	<%@ include file="../include/public_js_css.jsp"%>  
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-header-fixed">
	<!-- BEGIN HEADER -->   
	<%@ include file="../include/header.jsp"%>  
	<!-- END HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->   
	<div class="page-container">
		<!-- BEGIN SIDEBAR -->
		<%@ include file="../include/leftMenu.jsp"%>  
		<script type="text/javascript">
		jQuery(document).ready(function() {
			$('#home_init').addClass('active');
		});
		</script>
		<!-- END SIDEBAR -->
		<!-- BEGIN PAGE -->
		<div class="page-content">
			<!-- BEGIN STYLE CUSTOMIZER -->
			<%@ include file="../include/style_customizer.jsp"%>
			<!-- END BEGIN STYLE CUSTOMIZER -->
			<!-- BEGIN PAGE HEADER-->
			<div class="row">
				<div class="col-md-12">
					<!-- BEGIN PAGE TITLE & BREADCRUMB-->
					<h3 class="page-title">
						车主管家系统V1.0 <small>后台</small>
					</h3>
					<ul class="page-breadcrumb breadcrumb">
						<li>
							<i class="fa fa-home"></i>
							<a href="${basePath }/home_init.ac">Home</a> 
							<i class="fa fa-angle-right"></i>
						</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			<!-- END PAGE HEADER-->
			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
				<div class="col-md-12">
					<!-- BEGIN PAGE CONTENT-->
					<div class="tiles">
						<div class="tile double-down bg-blue">
							<div class="tile-body">
								<i class="fa fa-bell-o"></i>
							</div>
							<div class="tile-object">
								<div class="name">
									Notifications
								</div>
								<div class="number">
									6
								</div>
							</div>
						</div>
						<div class="tile bg-green">
							<div class="tile-body">
								<i class="fa fa-calendar"></i>
							</div>
							<div class="tile-object">
								<div class="name">
									Meetings
								</div>
								<div class="number">
									12
								</div>
							</div>
						</div>
						<div class="tile double selected bg-blue">
							<div class="corner"></div>
							<div class="check"></div>
							<div class="tile-body">
								<h4>support@metronic.com</h4>
								<p>Re: Metronic v1.2 - Project Update!</p>
								<p>24 March 2013 12.30PM confirmed for the project plan update meeting...</p>
							</div>
							<div class="tile-object">
								<div class="name">
									<i class="fa fa-envelope"></i>
								</div>
								<div class="number">
									14
								</div>
							</div>
						</div>
						<div class="tile selected bg-red">
							<div class="corner"></div>
							<div class="tile-body">
								<i class="fa fa-user"></i>
							</div>
							<div class="tile-object">
								<div class="name">
									Members
								</div>
								<div class="number">
									452
								</div>
							</div>
						</div>
						<div class="tile double bg-purple">
							<div class="tile-body">
								<img src="${basePath}/plugins/bootstrap.admin.theme/assets/img/photo1.jpg" alt="">
								<h4>Announcements</h4>
								<p>
									Easily style icon color, size, shadow, and anything that's possible with CSS.
								</p>
							</div>
							<div class="tile-object">
								<div class="name">
									Bob Nilson
								</div>
								<div class="number">
									24 Jan 2013
								</div>
							</div>
						</div>
						<div class="tile bg-yellow">
							<div class="tile-body">
								<i class="fa fa-shopping-cart"></i>
							</div>
							<div class="tile-object">
								<div class="name">
									Orders
								</div>
								<div class="number">
									121
								</div>
							</div>
						</div>
						<div class="tile image selected">
							<div class="tile-body">
								<img src="${basePath}/plugins/bootstrap.admin.theme/assets/img/gallery/image2.jpg" alt="">
							</div>
							<div class="tile-object">
								<div class="name">
									Media
								</div>
							</div>
						</div>
						<div class="tile bg-green">
							<div class="tile-body">
								<i class="fa fa-comments-alt"></i>
							</div>
							<div class="tile-object">
								<div class="name">
									Feedback
								</div>
								<div class="number">
									12
								</div>
							</div>
						</div>
						<div class="tile double bg-dark">
							<div class="tile-body">
								<img src="${basePath}/plugins/bootstrap.admin.theme/assets/img/photo2.jpg" alt="" class="pull-right">
								<h3>@lisa_wong</h3>
								<p>
									I really love this theme. I look forward to check the next release!
								</p>
							</div>
							<div class="tile-object">
								<div class="name">
									<i class="fa fa-twitter"></i>
								</div>
								<div class="number">
									10:45PM, 23 Jan
								</div>
							</div>
						</div>
						<div class="tile bg-blue">
							<div class="tile-body">
								<i class="fa fa-coffee"></i>
							</div>
							<div class="tile-object">
								<div class="name">
									Meetups
								</div>
								<div class="number">
									12 Jan
								</div>
							</div>
						</div>
						<div class="tile bg-green">
							<div class="tile-body">
								<i class="fa fa-bar-chart-o"></i>
							</div>
							<div class="tile-object">
								<div class="name">
									Reports
								</div>
								<div class="number"></div>
							</div>
						</div>
						<div class="tile bg-purple">
							<div class="tile-body">
								<i class="fa fa-briefcase"></i>
							</div>
							<div class="tile-object">
								<div class="name">
									Documents
								</div>
								<div class="number">
									124
								</div>
							</div>
						</div>
						<div class="tile image double selected">
							<div class="tile-body">
								<img src="${basePath}/plugins/bootstrap.admin.theme/assets/img/gallery/image4.jpg" alt="">
							</div>
							<div class="tile-object">
								<div class="name">
									Gallery
								</div>
								<div class="number">
									124
								</div>
							</div>
						</div>
						<div class="tile bg-yellow selected">
							<div class="corner"></div>
							<div class="check"></div>
							<div class="tile-body">
								<i class="fa fa-cogs"></i>
							</div>
							<div class="tile-object">
								<div class="name">
									Settings
								</div>
							</div>
						</div>
						<div class="tile bg-purple">
							<div class="tile-body">
								<i class="fa fa-plane"></i>
							</div>
							<div class="tile-object">
								<div class="name">
									Projects
								</div>
								<div class="number">
									34
								</div>
							</div>
						</div>
					</div>
					<!-- END PAGE CONTENT-->
				
				</div>
			</div>
			<!-- END PAGE CONTENT-->
		</div>
		<!-- END PAGE -->    
	</div>
	<!-- END CONTAINER -->
	<!-- BEGIN FOOTER -->
	<%@ include file="../include/footer.jsp"%>  
	<!-- END FOOTER -->
	
</body>
<!-- END BODY -->
</html>
