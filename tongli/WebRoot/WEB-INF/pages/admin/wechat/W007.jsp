<%--
/*
 * 微信服务_自定义菜单 (页面)
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
<title>微信服务-自定义菜单【车主管家】</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<meta name="MobileOptimized" content="320">
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<%@ include file="../include/public_js_css.jsp"%>
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
			function uploadMenu() {
				jQuery.ajax({
					url : '${basePath}/h/w007_uploadMenu.ac?time=' + new Date().getTime(),
					success : function(req) {
						if(req=='0'){
							jQuery("#msg").html('<div class="alert alert-success"><button class="close" data-dismiss="alert"></button><strong>Success!</strong> 菜单发布成功!</div>');
						}else{
							jQuery("#msg").html('<div class="alert alert-danger"><button class="close" data-dismiss="alert"></button><strong>Error!</strong> '+req+'!</div>');
						}
					},
					error : function() {
						jQuery("#msg").html('<div class="alert alert-danger"><button class="close" data-dismiss="alert"></button><strong>Error!</strong> 菜单发布失败!</div>');
					}
				});
			}
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
						<li>服务号菜单</li>
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
										<strong>Success!</strong> 菜单项操作成功。
									</div>
								</c:otherwise>
							</c:choose>
						</c:if>
						<div id="msg"></div>
						<div class="btn btn-toolbar">
							<a href="${basePath}/h/w007_edit.ac" class="btn btn-primary">新建</a>
						</div>
						<button id="download" class="btn">从微信下载</button>
						<button id="upload" onclick="uploadMenu();" class="btn">发布到微信</button>
						<table class="table table-condensed table-striped">
							<tbody>
								<tr>
									<th class="col-md-2">名称</th>
									<th class="col-md-1">显示顺序</th>
									<th class="col-md-1">类型</th>
									<th class="col-md-1">KEY</th>
									<th class="col-md-4" style="word-break:break-all;">链接地址</th>
									<th class="col-md-2"></th>
								</tr>
							<c:forEach items="${beans}" var="bean">
								<tr>
									<td>
									${bean.menu_name}
									</td>
									<td></td>
									<c:choose>
										<c:when test="${bean.beans!=null && fn:length(bean.beans)>0}">
											<td></td>
											<td></td>
											<td></td>
										</c:when>
										<c:otherwise>
											<td>${bean.menu_type}</td>
											<td>
											<c:if test="${bean.menu_type!='view'}">
											${bean.menu_key}
											</c:if>
											</td>
											<td>
											<c:if test="${bean1.menu_type=='view'}">
											<a href="${bean1.menu_url}" target="_blank">${bean1.menu_url}</a>
											</c:if>
											</td>
										</c:otherwise>
									</c:choose>
									<td c><a href="${basePath}/h/w007_edit.ac?id=${bean.id}" class="btn edit green">编辑</a>
										<a href="${basePath}/h/w007_del.ac?id=${bean.id}" class="btn btn-danger"
										data-confirm="确定删除吗?" data-method="delete" rel="nofollow">删除</a>
									</td>
								</tr>
									<c:forEach items="${bean.beans}" var="bean1">
									<tr>
										<td>
										|-- ${bean1.menu_name}
										</td>
										<td>${bean1.sort_num}</td>
										<td>${bean1.menu_type}</td>
										<td>
										<c:if test="${bean1.menu_type!='view'}">
										${bean1.menu_key}
										</c:if>
										</td>
										<td style="word-break:break-all;">
										<c:if test="${bean1.menu_type=='view'}">
										<a href="${bean1.menu_url}" target="_blank">${bean1.menu_url}</a>
										</c:if>
										</td>
										<td><a href="${basePath}/h/w007_edit.ac?id=${bean1.id}" class="btn edit green">编辑</a>
											<a href="${basePath}/h/w007_del.ac?id=${bean1.id}" class="btn btn-danger"
											data-confirm="确定删除吗?" data-method="delete" rel="nofollow">删除</a>
										</td>
									</tr>
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
