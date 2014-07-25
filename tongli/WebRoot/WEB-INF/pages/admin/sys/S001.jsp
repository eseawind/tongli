<%--
/*
 * 系统管理_资讯管理 (页面)
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-03-24  wuxiaogang   程序・发布
 * -------- ----------- ------------ ------------------------------------------
 * Copyright 2014 童励 System. - All Rights Reserved.
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
				$('#sys,#sys_sub_menu_l1_sub_menu_l1').addClass('active');
				$('#sys_arrow,#sys_sub_menu_l1_arrow').addClass('open');
				$('#sys_sub_menu,#sys_sub_menu_l1_sub_menu').show();
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
						资讯列表 <small><span class="help-inline">展示所有未删除的资讯</span></small>
					</h3>
					<ul class="page-breadcrumb breadcrumb">
						<li><i class="fa fa-home"></i> <a
							href="${basePath }/home_init.ac">Home</a> <i
							class="fa fa-angle-right"></i></li>
						<li><a href="${basePath }/h/s001_init.ac">资讯管理</a> <i class="fa fa-angle-right"></i></li>
						<li>资讯列表</a> </li>
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
						<div class=" well btn-toolbar col-md-12">
							<div class="col-md-2">
							</div>
								&nbsp; 
								<label>标题
									<input class="upload-wrapper" id="message_keyword" name="" size="20" value="${k}" placeholder="关键字" title="关键字" type="text">
								</label>
								&nbsp; 
								&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 
								<label>
								<input class="btn default" name="commit" value="检索" type="button" onclick="loadUrlPage(0, 'h/s001_','init','${tid}');">
								</label>
						</div>
					</div>
						<div class="col-md-2">
							<div class="list-group">
							  <a href="javascript:void(0);" class="list-group-item <c:if test="${tid==null || tid==''}"> active </c:if>" onclick="loadUrlPage(0, 'h/s001_','init','');">
							   <i class="fa <c:choose><c:when test="${tid==null || tid==''}">fa-folder-open</c:when><c:otherwise>fa-folder-o</c:otherwise></c:choose>"></i> 所有资讯
							  </a>
							<c:forEach items="${tree}" var="tree">
							  <a class="list-group-item <c:if test="${tree.id==tid}"> active </c:if>" href="javascript:void(0);" onclick="loadUrlPage(0, 'h/s001_','init','${tree.id}');">
							    <i class="fa <c:choose><c:when test="${tree.id==tid}">fa-folder-open</c:when><c:otherwise>fa-folder-o</c:otherwise></c:choose>"></i> ${tree.name}
							  </a>
							<c:forEach items="${tree.beans}" var="tree1">
							 <a class="list-group-item <c:if test="${tree1.id==tid}"> active </c:if>" href="javascript:void(0);" onclick="loadUrlPage(0, 'h/s001_','init','${tree1.id}');">
							    <i class="fa <c:choose><c:when test="${tree1.id==tid}">fa-folder-open</c:when><c:otherwise>fa-folder-o</c:otherwise></c:choose>"></i> |--${tree1.name}
							  </a>
								<c:forEach items="${tree1.beans}" var="tree2">
								 <a class="list-group-item <c:if test="${tree2.id==tid}"> active </c:if>" href="javascript:void(0);" onclick="loadUrlPage(0, 'h/s001_','init','${tree2.id}');">
							    <i class="fa <c:choose><c:when test="${tree2.id==tid}">fa-folder-open</c:when><c:otherwise>fa-folder-o</c:otherwise></c:choose>"></i> |--|--${tree2.name}
							  </a>
								</c:forEach>
							</c:forEach>
						</c:forEach>
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
										<strong>Success!</strong> 资讯操作成功。
									</div>
								</c:otherwise>
							</c:choose>
						</c:if>
							<div class="btn-toolbar col-md-2">
								<a href="${basePath}/h/s001_edit.ac" class="btn btn-primary"><i class="fa "></i> 新增资讯</a>
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
											href="${basePath}/c003_init.ac?id=${bean.id}&tid=${bean.id}&tid=${tid}&pid=${tid}"
											class="btn  btn-info" target="_blank">预览</a> 
											<a
											href="${basePath}/h/s001_edit.ac?id=${bean.id}"
											class="btn edit green">编辑</a> <a href="javascript:void(0)"   class="btn btn-danger" 
											onclick="if(confirm('确认删除吗?')){location.href='${basePath}/h/s001_del.ac?id=${bean.id}'};"
											rel="nofollow">删除</a></td>
									</tr>
									</c:forEach>
								</tbody>
							</table>
							<customtag:pagingext func="loadUrlPage" params="'h/s001_','init','${tid}'" />
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
function loadUrlPage(offset, url, event,tid) {
	var k='';
	if($('#message_keyword').val()){
		k='&k='+encodeURI(encodeURI($('#message_keyword').val()));
	}
	location.href='${basePath}/' + url + event+'.ac?offset=' + offset+'&tid='+tid+k;
}
</script>