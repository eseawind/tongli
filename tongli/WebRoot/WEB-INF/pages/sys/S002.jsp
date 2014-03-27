<%--
/*
 * 系统管理_资讯栏目管理 (页面)
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-03-24  wuxiaogang   程序・发布
 * -------- ----------- ------------ ------------------------------------------
 * Copyright 2014 jfq System. - All Rights Reserved.
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
<title>系统管理-资讯栏目管理【jfq】</title>
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
				$('#sys,#sys_sub_menu_l1_sub_menu_l0').addClass('active');
				$('#sys_arrow,#sys_sub_l1_arrow').addClass('open');
				$('#sys_sub_menu,#sys_sub_l1_sub_menu').show();
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
						资讯栏目 <small><span class="help-inline">展示所有资讯栏目</span></small>
					</h3>
					<ul class="page-breadcrumb breadcrumb">
						<li><i class="fa fa-home"></i> <a
							href="${basePath }/home_init.ac">Home</a> <i
							class="fa fa-angle-right"></i></li>
						<li><a href="#">资讯管理</a> <i class="fa fa-angle-right"></i></li>
						<li><a href="${basePath }/h/s001_init.ac">资讯栏目</a> <i class="fa fa-angle-right"></i></li>
						<li>列表</a> </li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			<!-- END PAGE HEADER-->
			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
				<div class="col-md-12">
					<div class="matter">
						<c:if test="${msg!=null}">
							<c:choose>
								<c:when test="${msg!='1'}">
									<div class="alert alert-danger">
										<button class="close" data-dismiss="alert"></button>
										<strong>Error!</strong> ${msg}
									</div>
								</c:when>
								<c:otherwise>
									<div class="alert alert-success">
										<button class="close" data-dismiss="alert"></button>
										<strong>Success!</strong> 操作成功。
									</div>
								</c:otherwise>
							</c:choose>
						</c:if>
						<div id="msg"></div>
						<div class="btn btn-toolbar">
							<a href="${basePath}/h/s002_edit.ac" class="btn btn-primary">新增</a>
						</div>
						<table class="table table-condensed table-striped">
							<tbody>
								<tr>
									<th class="col-md-1">显示顺序</th>
									<th class="col-md-4">名称</th>
									<th class="col-md-2"></th>
								</tr>
							<c:forEach items="${beans}" var="bean">
								<tr>
									<td>${bean.sort_num}</td>
									<td>
									${bean.name}
									</td>
									<td c><a href="${basePath}/h/s002_edit.ac?id=${bean.id}" class="btn edit green">编辑</a>
									</td>
								</tr>
									<c:forEach items="${bean.beans}" var="bean1">
									<tr>
										<td>${bean1.sort_num}</td>
										<td>
										|-- ${bean1.name}
										</td>
										<td><a href="${basePath}/h/s002_edit.ac?id=${bean1.id}" class="btn edit green">编辑</a>
										</td>
									</tr>
										<c:forEach items="${bean1.beans}" var="bean2">
										<tr>
											<td>${bean2.sort_num}</td>
											<td>
											|--|-- ${bean2.name}
											</td>
											<td><a href="${basePath}/h/s002_edit.ac?id=${bean2.id}" class="btn edit green">编辑</a>
											</td>
										</tr>
										<c:forEach items="${bean2.beans}" var="bean3">
										<tr>
											<td>${bean3.sort_num}</td>
											<td>
											|--|--|-- ${bean3.name}
											</td>
											<td><a href="${basePath}/h/s002_edit.ac?id=${bean3.id}" class="btn edit green">编辑</a>
											</td>
										</tr>
										</c:forEach>
										</c:forEach>
									</c:forEach>
								</c:forEach>
							</tbody>
						</table>
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
<!-- Modal -->
<div id="myModal1" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">新建分组</h4>
			</div>
			<div class="modal-body">
				<p></p>
				<div class="form-group">
					<input class="form-control" placeholder="请输入分组名称" size="5" data-tabindex="1" type="text">
				</div>
			</div>
			<div class="modal-footer">
				<button class="btn default" data-dismiss="modal" aria-hidden="true">关闭</button>
				<button class="btn blue">保存</button>
			</div>
		</div>
	</div>
</div>