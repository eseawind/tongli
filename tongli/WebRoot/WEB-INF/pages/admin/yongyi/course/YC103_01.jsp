<%--
/*
 * 系统管理_预约参观_编辑 (页面)
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-04-07  wuxiaogang   程序・发布
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
<html lang="zh-CN" class="no-js">
<head>
<meta charset="utf-8" />
<%@include file="../../include/admin_title.jsp" %>
<%@ include file="../../include/public_js_css.jsp"%>
<link href="${basePath}/css/messages.css" media="all" rel="stylesheet" type="text/css" />
<link href="${basePath}/css/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${basePath}/css/font-awesome/css/font-awesome-ie7.css" rel="stylesheet">

<link rel="stylesheet" type="text/css" href="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/bootstrap-datepicker/css/datepicker.css" />
<link rel="stylesheet" type="text/css" href="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/bootstrap-datepicker/less/datepicker.less" />

<link rel="stylesheet" type="text/css" href="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/jquery-multi-select/css/multi-select.css" />

<link rel="stylesheet" type="text/css" href="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/clockface/css/clockface.css" />
</head>
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
				$('#sys10,#sys10_sub_menu_li_2_sub_menu_1').addClass('active');
				$('#sys10_arrow,#sys10_sub_menu_li_2_arrow').addClass('open');
				$('#sys10_sub_menu,#sys10_sub_menu_li_2_sub_menu').show();
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
						预约参观 <small><span class="help-inline">信息</span></small>
					</h3>
					<ul class="page-breadcrumb breadcrumb">
						<li><i class="fa fa-home"></i> <a
							href="${basePath }/home_init.ac">Home</a> <i
							class="fa fa-angle-right"></i></li>
						<li><a href="#">系统管理</a> <i class="fa fa-angle-right"></i></li>
						<li><a href="${basePath}/h/yc103_init.ac">预约参观</a> <i class="fa fa-angle-right"></i></li>
						<li>详情</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			<!-- END PAGE HEADER-->
			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
				<div class="col-md-12">
					<form accept-charset="UTF-8"  action="${basePath}/h/yc103_save.ac" class="edit_article" id="edit_article_13632" method="post">
						<s:token></s:token>
						<div class="well form-inline">
							<label>
							<input name="bean.status" value="0"
							<c:if test="${bean.status=='0'}">
							 	checked="checked"
							 </c:if>
							 type="radio">
							 未处理</label>
							 <label>
							<input name="bean.status" value="1"
							<c:if test="${bean.status=='1'}">
							 	checked="checked"
							 </c:if>
							 type="radio">
							 已处理</label>
						</div>
						<input name="bean.id" type="hidden" value="${bean.id}">
						<div class="form-group">
							<label for="article_addres">姓名</label> <label class="form-control">${bean.name}</label>
						</div>
						<div class="form-group">
							<label for="">性别</label> <label class="form-control">
									<c:if test="${bean.sex=='0'}">男</c:if>
									<c:if test="${bean.sex=='1'}">女</c:if>
							</label>
						</div>
						<div class="form-group">
							<label for="">电话</label> <label class="form-control">${bean.tel}</label>
						</div>
						<div class="form-group">
							<label for="">参观时间</label> <label class="form-control">${bean.day}</label>
						</div>
						<div class="form-group">
							<label for="">参观场馆</label> <label class="form-control">${bean.addres}</label>
						</div>
						<div class="form-group">
							<label for="">参观课程</label> <label class="form-control">${bean.course}</label>
						</div>
						
						<div class="form-group">
							<label for="article_description">其它</label>
							<div class="qeditor_border">
									${bean.detail_info}
							</div>
						</div>
						<div class="form-actions">
							<input class="btn btn-primary" name="commit" type="submit"
								value="确定"> | <a
								href="${basePath}/h/yc103_init.ac">返回</a>
						</div>
					</form>
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
