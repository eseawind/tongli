<%--
/*
 * 系统管理_会员管理 (页面)
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-04-01  wuxiaogang   程序・发布
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
<%@ taglib prefix="customtag" uri="/custom-tags"%>
<!DOCTYPE html>
<html lang="zh-CN" class="no-js">
<head>
<meta charset="utf-8" />
<%@include file="../include/admin_title.jsp" %>
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<%@ include file="../include/public_js_css.jsp"%>
<link href="${basePath}/css/messages.css" media="all" rel="stylesheet" type="text/css" />
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
				$('#member,#member_sub_menu_l1').addClass('active');
				$('#member_arrow').addClass('open');
				$('#member_sub_menu').show();
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
						会员列表 <small><span class="help-inline">展示所有未删除的会员信息</span></small>
					</h3>
					<ul class="page-breadcrumb breadcrumb">
						<li><i class="fa fa-home"></i> <a
							href="${basePath }/home_init.ac">Home</a> <i
							class="fa fa-angle-right"></i></li>
						<li><a href="${basePath }/h/m001_init.ac">会员管理</a> <i class="fa fa-angle-right"></i></li>
						<li>会员列表</a> </li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			<!-- END PAGE HEADER-->
			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
				<div class="col-md-12">
					<div class="row-fluid">
						<div class="col-md-12">
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
										<strong>Success!</strong> 会员操作成功。
									</div>
								</c:otherwise>
							</c:choose>
						</c:if>
							<div class="btn-toolbar">
								<a href="${basePath}/h/m001_edit.ac" class="btn btn-primary">新建会员</a>
							</div>
							<table class="table table-condensed table-striped">
								<tbody>
									<tr>
										<th class="col-md-3">时间</th>
										<th class="col-md-6">会员名称</th>
										<th class="col-md-3"></th>
									</tr>
									<c:forEach items="${beans}" var="bean">
									<tr>
										<td>${bean.last_updated}</td>
										<td>${bean.title}</td>
										<td><a
											href="${basePath}/h/m001_view.ac?id=${bean.id}"
											class="btn  btn-info" target="_blank">预览</a> <a
											href="${basePath}/h/m001_edit.ac?id=${bean.id}"
											class="btn edit green">编辑</a> <a href="javascript:void(0)"   class="btn btn-danger" 
											onclick="if(confirm('确认删除吗?')){location.href='${basePath}/h/m001_del.ac?id=${bean.id}'};"
											rel="nofollow">删除</a></td>
									</tr>
									</c:forEach>
								</tbody>
							</table>
							<customtag:pagingext func="loadUrlPage" params="'h/m001_','init'" />
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