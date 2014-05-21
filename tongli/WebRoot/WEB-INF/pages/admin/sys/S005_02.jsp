<%--
/*
 * 短消息管理_编辑 (页面)
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-05-20  wuxiaogang   程序・发布
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
				$('#sys6,#sys6_sub_menu_l1_sub_menu_l3').addClass('active');
				$('#sys6_arrow,#sys6_sub_l1_arrow').addClass('open');
				$('#sys6_sub_menu,#sys6_sub_l1_sub_menu').show();
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
					<h3 class="page-name">
						短消息管理 <small><span class="help-inline">这里查看短消息信息</span></small>
					</h3>
					<ul class="page-breadcrumb breadcrumb">
						<li><i class="fa fa-home"></i> <a
							href="${basePath }/home_init.ac">Home</a> <i
							class="fa fa-angle-right"></i></li>
						<li><a href="#">短消息管理</a> <i class="fa fa-angle-right"></i></li>
						<li>详情</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			<!-- END PAGE HEADER-->
			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
				<div class="col-md-12">
						<div class="well form-inline">
							
						</div>
						<div class="form-group">
							<label for="bean_sms_dst_id">收信人</label> 
							<label class="form-control">${bean.sms_dst_id}</label>
						</div>
						<div class="form-group">
							<label for="bean_sms_content">短信内容</label>
							<div class="qeditor_border">
								${bean.sms_content}
							</div>
						</div>
						<div class="form-group">
							<label for="bean_sms_send_time">预定发送时间</label> 
							<label class="form-control">${bean.sms_send_time}</label>
						</div>
						<div class="form-group">
							<label for="bean_sms_sended_time">实际发送时间</label> 
							<label class="form-control">${bean.sms_sended_time}</label>
						</div>
						<div class="form-group">
							<label for="bean_sms_send_count">实际发送次数</label> 
							<label	class="form-control" >${bean.sms_send_count}</label> 
						</div>
						<div class="form-group">
							<label for="bean_sms_status">短信状态</label>
								<label	class="form-control" >
								<c:if test="${bean.sms_status=='1'}">未发送</c:if>
								<c:if test="${bean.sms_status=='2'}">发送中</c:if>
								<c:if test="${bean.sms_status=='3'}">已发送</c:if>
								</label> 
						</div>
						<div class="form-actions">
							 <a href="${basePath}/h/s005_init.ac">返回</a>
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
