<%--
/*
 * 微信服务_素材管理_文章消息 (页面)
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
<!--[if !IE]><!-->
<html lang="zh-CN" class="no-js">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8" />
<title>微信服务-素材管理-文章【车主管家】</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<meta name="MobileOptimized" content="320">
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<%@ include file="../include/public_js_css.jsp"%>
<link href="${basePath}/css/messages.css" media="all" rel="stylesheet"
	type="text/css" />

<link href="${basePath}/css/font-awesome/css/font-awesome.css"
	rel="stylesheet">
<link href="${basePath}/css/font-awesome/css/font-awesome-ie7.css"
	rel="stylesheet">
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
				$('#wechar,#w004_init').addClass('active');
				$('#wechar_arrow,#wechar_sub_menu_li_arrow').addClass('open');
				$('#wechar_sub_menu,#wechar_sub_menu_li_sub_menu').show();
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
						管理微信公众帐号 <small><span class="help-inline">如果没有公众帐号，<a
								href="http://mp.weixin.qq.com" target="_blank">点这里注册</a></span></small>
					</h3>
					<ul class="page-breadcrumb breadcrumb">
						<li><i class="fa fa-home"></i> <a
							href="${basePath }/home_init.ac">Home</a> <i
							class="fa fa-angle-right"></i></li>
						<li><a href="#">微信服务</a> <i class="fa fa-angle-right"></i></li>
						<li><a href="#">素材管理</a> <i class="fa fa-angle-right"></i></li>
						<li>文章</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			<!-- END PAGE HEADER-->
			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
				<div class="col-md-12">
					<div class="matter">
						<div class="container-fluid">
							<div class="row-fluid">
								<div class="col-md-12">

									<style>
.nav-list li {
	position: relative;
}

.nav-list span.oper {
	position: absolute;
	right: 0;
	top: 0;
}

.nav-list span.oper,li#add-grp-btn {
	font-size: 18px;
}
</style>

									<div class="row-fluid">
										<div class="col-md-2">
											<ul class="nav nav-list well">
												<li class="active"><a href="/articles">未分组 (9)</a></li>


												<li class="divider"></li>
												<li id="add-grp-btn"><a href="javascript:void(0)"
													class="manage-grp-btn"><i class="icon-plus"></i> 新建分组</a></li>
											</ul>
										</div>
										<div class="col-md-10">
											<div class="btn-toolbar">
												<a href="${basePath}/h/w004_edit.ac" class="btn btn-primary">新建文章</a>
											</div>

											<table class="table table-condensed table-striped">
												<tbody>
													<tr>
														<th>时间</th>
														<th>标题</th>
														<th></th>
													</tr>
													<tr>
														<td>2014-02-28 15:31</td>
														<td>联系捷道</td>
														<td><a
															href="${basePath}/h/w004_view.ac"
															class="btn  btn-info" target="_blank">查看</a> <a
															href="${basePath}/h/w004_edit.ac"
															class="btn edit green">编辑</a> <a
															href="/articles/ljjdwz--611" class="btn btn-danger"
															data-confirm="Are you sure?" data-method="delete"
															rel="nofollow">删除</a></td>
													</tr>
													<tr>
														<td>2014-02-28 15:31</td>
														<td>删除广告</td>
														<td><a href="/articles/scggwz--601"
															class="btn  btn-info" target="_blank">查看</a> <a
															href="/articles/scggwz--601/edit" class="btn edit green">编辑</a>
															<a href="/articles/scggwz--601" class="btn btn-danger"
															data-confirm="Are you sure?" data-method="delete"
															rel="nofollow">删除</a></td>
													</tr>
													<tr>
														<td>2014-02-28 15:31</td>
														<td>关于我们</td>
														<td><a href="/articles/gywmwz--616"
															class="btn btn-info" target="_blank">查看</a> <a
															href="/articles/gywmwz--616/edit" class="btn edit green">编辑</a>
															<a href="/articles/gywmwz--616" class="btn btn-danger"
															data-confirm="Are you sure?" data-method="delete"
															rel="nofollow">删除</a></td>
													</tr>
													<tr>
														<td>2014-02-28 15:31</td>
														<td>联系我们</td>
														<td><a href="/articles/ljwmwz--617"
															class="btn btn-info" target="_blank">查看</a> <a
															href="/articles/ljwmwz--617/edit" class="btn edit green">编辑</a>
															<a href="/articles/ljwmwz--617" class="btn btn-danger"
															data-confirm="Are you sure?" data-method="delete"
															rel="nofollow">删除</a></td>
													</tr>
													<tr>
														<td>2014-02-28 15:31</td>
														<td>新闻</td>
														<td><a href="/articles/xwwz--610"
															class="btn btn-info" target="_blank">查看</a> <a
															href="/articles/xwwz--610/edit" class="btn edit green">编辑</a>
															<a href="/articles/xwwz--610" class="btn btn-danger"
															data-confirm="Are you sure?" data-method="delete"
															rel="nofollow">删除</a></td>
													</tr>
													<tr>
														<td>2014-02-28 15:31</td>
														<td>新品推荐</td>
														<td><a href="/articles/xptjwz--614"
															class="btn btn-info" target="_blank">查看</a> <a
															href="/articles/xptjwz--614/edit" class="btn edit green">编辑</a>
															<a href="/articles/xptjwz--614" class="btn btn-danger"
															data-confirm="Are you sure?" data-method="delete"
															rel="nofollow">删除</a></td>
													</tr>
													<tr>
														<td>2014-02-28 15:31</td>
														<td>特惠商品</td>
														<td><a href="/articles/thspwz--613"
															class="btn btn-info" target="_blank">查看</a> <a
															href="/articles/thspwz--613/edit" class="btn edit green">编辑</a>
															<a href="/articles/thspwz--613" class="btn btn-danger"
															data-confirm="Are you sure?" data-method="delete"
															rel="nofollow">删除</a></td>
													</tr>
													<tr>
														<td>2014-02-28 15:31</td>
														<td>关于微播</td>
														<td><a href="/articles/gywbwz--280"
															class="btn btn-info" target="_blank">查看</a> <a
															href="/articles/gywbwz--280/edit" class="btn edit green">编辑</a>
															<a href="/articles/gywbwz--280" class="btn btn-danger"
															data-confirm="Are you sure?" data-method="delete"
															rel="nofollow">删除</a></td>
													</tr>
													<tr>
														<td>2014-02-28 15:31</td>
														<td>如何使用</td>
														<td><a href="/articles/rhsywz--612"
															class="btn btn-info" target="_blank">查看</a> <a
															href="/articles/rhsywz--612/edit" class="btn edit green">编辑</a>
															<a href="/articles/rhsywz--612" class="btn btn-danger"
															data-confirm="Are you sure?" data-method="delete"
															rel="nofollow">删除</a></td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
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
