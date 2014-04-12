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
<%@ taglib prefix="customtag" uri="/custom-tags"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="zh-CN" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="zh-CN" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="zh-CN" class="no-js">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8" />
<%@include file="../include/admin_title.jsp" %>
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<%@ include file="../include/public_js_css.jsp"%>
<link href="${basePath}/css/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${basePath}/css/font-awesome/css/font-awesome-ie7.css" rel="stylesheet">
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
				$('#wechat,#w004_init').addClass('active');
				$('#wechat_arrow,#wechat_sub_menu_li_arrow').addClass('open');
				$('#wechat_sub_menu,#wechat_sub_menu_li_sub_menu').show();
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
					<div class="row-fluid">
						<div class="col-md-2">
							<div class="list-group">
							  <a href="#" class="list-group-item active">
							   <i class="fa fa-check"></i> 未分组 (${fn:length(beans)})
							  </a>
							  <a class="list-group-item" href="#myModal1" data-toggle="modal"><i class="fa fa-bolt"></i> 新建分组</a>
							</div>
						</div>
						<div class="col-md-10">
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
										<strong>Success!</strong> 图文信息操作成功。
									</div>
								</c:otherwise>
							</c:choose>
						</c:if>
							<div class="btn-toolbar">
								<a href="${basePath}/h/w004_edit.ac" class="btn btn-primary"><i class="fa fa-book"></i> 新建文章</a>
							</div>

							<table class="table table-condensed table-striped">
								<tbody>
									<tr>
										<th class="col-md-3">时间</th>
										<th class="col-md-6">标题</th>
										<th class="col-md-3"></th>
									</tr>
									<c:forEach items="${beans}" var="bean">
									<tr>
										<td>${bean.last_updated}</td>
										<td>${bean.title}</td>
										<td><a
											href="${basePath}/h/w004_view.ac?id=${bean.id}"
											class="btn  btn-info" target="_blank">查看</a> <a
											href="${basePath}/h/w004_edit.ac?id=${bean.id}"
											class="btn edit green">编辑</a> <a href="javascript:void(0)"   class="btn btn-danger" 
											onclick="if(confirm('确认删除吗?')){location.href='${basePath}/h/w004_del.ac?id=${bean.id}'};"
											rel="nofollow">删除</a></td>
									</tr>
									</c:forEach>
								</tbody>
							</table>
							<customtag:pagingext func="loadUrlPage" params="'h/w004_','init'" />
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
<script type="text/javascript">
function loadUrlPage(offset, url, event) {
	location.href='${basePath}/' + url + event+'.ac?offset=' + offset;
}
</script>
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