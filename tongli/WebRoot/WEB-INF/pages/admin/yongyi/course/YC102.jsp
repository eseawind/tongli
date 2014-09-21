<%--
/*
 * 系统管理_课程管理_课程表 (页面)
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-04-01  wuxiaogang   程序・发布
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
<html lang="zh-CN" class="no-js">
<head>
<meta charset="utf-8" />
<%@include file="../../include/admin_title.jsp" %>
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<%@ include file="../../include/public_js_css.jsp"%>
<link href="${basePath}/css/messages.css" media="all" rel="stylesheet" type="text/css" />
<link href="${basePath}/css/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${basePath}/css/font-awesome/css/font-awesome-ie7.css" rel="stylesheet">
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-header-fixed">
	<!-- BEGIN HEADER -->
	<%@ include file="../../include/header.jsp"%>
	<!-- END HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
	<div class="page-container">
		<!-- BEGIN SIDEBAR -->
		<%@ include file="../../include/leftMenu.jsp"%>
		<script type="text/javascript">
			jQuery(document).ready(function() {
				$('#sys10,#sys10_sub_menu_li_3_sub_menu_1').addClass('active');
				$('#sys10_arrow,#sys10_sub_menu_li_3_arrow').addClass('open');
				$('#sys10_sub_menu,#sys10_sub_menu_li_3_sub_menu').show();
			});
		</script>
		<!-- END SIDEBAR -->
		<!-- BEGIN PAGE -->
		<div class="page-content">
			<!-- BEGIN STYLE CUSTOMIZER -->
			<%@ include file="../../include/style_customizer.jsp"%>
			<!-- END BEGIN STYLE CUSTOMIZER -->
			<!-- BEGIN PAGE HEADER-->
			<div class="row">
				<div class="col-md-12">
					<!-- BEGIN PAGE TITLE & BREADCRUMB-->
					<h3 class="page-title">
						课程表 <small><span class="help-inline">课程表信息</span></small>
					</h3>
					<ul class="page-breadcrumb breadcrumb">
						<li><i class="fa fa-home"></i> <a
							href="${basePath }/home_init.ac">Home</a> <i
							class="fa fa-angle-right"></i></li>
						<li><a href="${basePath }/h/yc102_init.ac">课程管理</a> <i class="fa fa-angle-right"></i></li>
						<li>课程表</a> </li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			<!-- END PAGE HEADER-->
			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
				<div class="col-md-12">
					<div class="row-fluid">
						<div class="col-md-12  tabbable tabbable-custom">
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
										<strong>Success!</strong> 课程表操作成功。
									</div>
								</c:otherwise>
							</c:choose>
						</c:if>
						<div class="well form-inline">
							<div class="btn-toolbar">
								<a href="${basePath}/h/yc102_edit.ac?type=0" class="btn btn-primary">安排课程表</a>
								<a href="${basePath}/h/yc102_edit.ac?type=1" class="btn purple">安排【夏令营】课程表</a>
								<a href="${basePath}/h/yc102_edit.ac?type=2" class="btn dark">安排【冬令营】程表</a>
							</div>
						</div>
							<ul class="nav nav-tabs" style="height:40px; ">
									<li id="tab_1_li" class="active "><a href="#tab_1" data-toggle="tab">完结课程(<font class="_struts_0" color="red">0</font>)</a></li>
									<li id="tab_2_li"><a href="#tab_2" data-toggle="tab">未完课程(<font class="_struts_1" color="red">0</font>)</a></li>
									<li id="tab_3_li"><a href="#tab_3" data-toggle="tab">完结课程【夏令营】(<font class="_struts_2" color="red">0</font>)</a></li>
									<li id="tab_4_li"><a href="#tab_4" data-toggle="tab">未完课程【夏令营】(<font class="_struts_3" color="red">0</font>)</a></li>
									<li id="tab_5_li"><a href="#tab_5" data-toggle="tab">完结课程【冬令营】(<font class="_struts_4" color="red">0</font>)</a></li>
									<li id="tab_6_li"><a href="#tab_6" data-toggle="tab">未完课程【冬令营】(<font class="_struts_5" color="red">0</font>)</a></li>
							</ul>
							<div class="tab-content">
								<div class="tab-pane active" id="tab_1">
									<!-- BEGIN FORM-->
									<div class="course_info" id="course_info1">
										
									</div>
									<!-- END FORM--> 
								</div>
								<div class="tab-pane" id="tab_2">
									<!-- BEGIN FORM-->
									<div class="course_info" id="course_info2">
										
									</div>
									<!-- END FORM--> 
								</div>
								<div class="tab-pane" id="tab_3">
									<!-- BEGIN FORM-->
									<div class="course_info" id="course_info3">
										
									</div>
									<!-- END FORM--> 
								</div>
								<div class="tab-pane" id="tab_4">
									<!-- BEGIN FORM-->
									<div class="course_info" id="course_info4">
										
									</div>
									<!-- END FORM--> 
								</div>
								<div class="tab-pane" id="tab_5">
									<!-- BEGIN FORM-->
									<div class="course_info" id="course_info5">
										
									</div>
									<!-- END FORM--> 
								</div>
								<div class="tab-pane" id="tab_6">
									<!-- BEGIN FORM-->
									<div class="course_info" id="course_info6">
										
									</div>
									<!-- END FORM--> 
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
	<%@ include file="../../include/footer.jsp"%>
	<!-- END FOOTER -->
</body>
<!-- END BODY -->
</html>
<script type="text/javascript">
jQuery(document).ready(function() {
	loadUrlPage(0,'h/yc102_','list1','course_info1','&t=0');
	loadUrlPage(0,'h/yc102_','list2','course_info2','&t=0');
	loadUrlPage(0,'h/yc102_','list1','course_info3','&t=1');
	loadUrlPage(0,'h/yc102_','list2','course_info4','&t=1');
	loadUrlPage(0,'h/yc102_','list1','course_info5','&t=2');
	loadUrlPage(0,'h/yc102_','list2','course_info6','&t=2');
});
function loadUrlPage(offset,url,event,divId,obj) {
	jQuery.ajax({
		url : '${basePath}/' + url + event+'.ac?offset='+offset+ obj + '&time=' + new Date(),
		success : function(req) {
			jQuery("#"+divId).html(req);
		},
		error : function() {
			jQuery("#"+divId).html('信息加载失败!');
		}
	});
}
</script>