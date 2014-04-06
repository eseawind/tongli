<%--
/*
 * 微信服务_自定义菜单_编辑 (页面)
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
				$('#wechat,#w007_init').addClass('active');
				$('#wechat_arrow').addClass('open');
				$('#wechat_sub_menu').show();
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
						<li><a href="${basePath }/h/w007_init.ac">服务号菜单</a>
							<i class="fa fa-angle-right"></i></li>
						<li>编辑</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			<!-- END PAGE HEADER-->
			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
				<div class="col-md-7">
				
					<form accept-charset="UTF-8" action="${basePath }/h/w007_save.ac"
						class="portlet-body form" method="post">
						<s:token></s:token>
						<div style="margin: 0; padding: 0; display: inline">
							<input name="bean.id" type="hidden" value="${bean.id}">
						</div>
						<div class="form-group">
						<label class="col-md-2 control-label">上一级</label>
						<div class="col-md-9">
							<select multiple="" name="bean.parent_id" class="form-control">
								<option>顶级</option>
								<c:forEach items="${beans}" var="bean1">
									<option value="${bean1.id }" 
									<c:if test="${bean.parent_id==bean1.id }">
									selected="selected"
									</c:if>
									>${bean1.menu_name}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-2 control-label">菜单名称</label>
						<div class="col-md-9">
							<input class="form-control input-lg" placeholder="必填" id="bean.menu_name" name="bean.menu_name" size="30" type="text" value="${bean.menu_name}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-2 control-label">菜单KEY</label>
						<div class="col-md-9">
							<input class="form-control input-lg" placeholder="必填" id="bean.menu_key" name="bean.menu_key" size="30" type="text" value="${bean.menu_key}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-2 control-label">菜单类型</label>
						<div class="col-md-9">
							<div class="radio-list">
								<label class="radio-inline">
									<div id="uniform-optionsRadios25" class="radio">
										<span class="checked">
											<input name="bean.menu_type" id="optionsRadios25" <c:if test="${bean.menu_type=='click'}">checked="checked"</c:if> value="click"  type="radio"/>
										 </span>
									</div> click
								</label>
								<label class="radio-inline">
									<div id="uniform-optionsRadios26" class="radio">
										<span class="checked">
											<input name="bean.menu_type" id="optionsRadios26"  <c:if test="${bean.menu_type=='view'}">checked="checked"</c:if> value="view" type="radio"/>
										 </span>
									</div> view
								</label>
							</div>
						</div>
					</div>
					<div style="clear: both;"></div>
					<div class="form-group">
						<label class="col-md-2 control-label">显示顺序</label>
						<div class="col-md-9">
							<input class="form-control input-lg" placeholder="Large Input" id="bean.sort_num" name="bean.sort_num" size="30" type="number" value="${bean.sort_num}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-2 control-label">VIEW链接</label>
						<div class="col-md-9">
								<textarea style="height: 150px;" class="form-control input-lg" placeholder="如果是外链地址,请直接输入" id="bean.menu_url" name="bean.menu_url">${bean.menu_url}</textarea>
						</div>
					</div>
					<div style="clear: both;"></div>
					<div class="form-actions fluid">
						<div class="col-md-offset-4 col-md-4">
							<input class="btn btn-large btn-primary" name="commit"
								type="submit" value="保存"> | <a href="${basePath }/h/w007_init.ac">返回</a>
						</div>
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
	<%@ include file="../include/footer.jsp"%>
	<!-- END FOOTER -->

</body>
<!-- END BODY -->
</html>
