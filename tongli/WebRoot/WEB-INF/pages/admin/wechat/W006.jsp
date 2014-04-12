<%--
/*
 * 微信服务_自动回复_视频消息 (页面)
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-04-11  wuxiaogang   程序・发布
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
<link href="${basePath}/css/messages.css" media="all" rel="stylesheet" type="text/css" />

<link href="/js/prettify/prettify-jPlayer.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="../skin/circle.skin/circle.player.css">


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
				$('#wechat,#wechat_sub_menu_li_sub_menu_5').addClass('active');
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
						<li><a href="#">自动回复</a> <i class="fa fa-angle-right"></i></li>
						<li>视频消息</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			<!-- END PAGE HEADER-->
			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
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
										<strong>Success!</strong> 视频信息操作成功。
									</div>
								</c:otherwise>
							</c:choose>
						</c:if>
						<div class="btn-toolbar">
								<a href="${basePath}/h/w006_edit.ac" class="btn btn-primary"><i class="fa fa-book"></i> 新建文章</a>
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
											href="${basePath}/h/w006_edit.ac?id=${bean.id}"
											class="btn edit green">编辑</a> <a href="javascript:void(0)"   class="btn btn-danger" 
											onclick="if(confirm('确认删除吗?')){location.href='${basePath}/h/w006_del.ac?id=${bean.id}'};"
											rel="nofollow">删除</a></td>
									</tr>
									</c:forEach>
								</tbody>
							</table>
							<customtag:pagingext func="loadUrlPage" params="'h/w006_','init'" />
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
jQuery(document).ready(function() {
	KindEditor.ready(function(K) {
		var editor = K.editor({
					resizeType : 2,
					uploadJson : '${basePath}/uploadFile?isrich=1',
					fileManagerJson : '${basePath}/plugins/editor/jsp/file_manager_json.jsp',
					allowFileManager : true
		});
		K('#add_image1').click(function() {
			editor.loadPlugin('image',function() {
				editor.plugin.imageDialog({
					imageUrl : $('#qe-new-attachment').val(),clickFn : function(
						url,title,width,height,border,align) {
							$('#qe-new-attachment').val(url);
							editor.hideDialog();
						}
				});
			});
		});
	});
});
function loadUrlPage(offset, url, event) {
	location.href='${basePath}/' + url + event+'.ac?offset=' + offset;
}
</script>