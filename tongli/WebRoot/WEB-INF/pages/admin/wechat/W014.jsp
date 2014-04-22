<%--
/*
 * 微信服务_自动回复_语音消息 (页面)
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-04-11  wuxiaogang   程序・发布
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
<link href="${basePath}/css/messages.css" media="all" rel="stylesheet" type="text/css" />

<link rel="stylesheet" href="${basePath}/plugins/jPlayer/skin/circle.skin/circle.player.css" type="text/css"  />

<script charset="utf-8" type="text/javascript" src="${basePath}/plugins/jPlayer/js/jquery.jplayer.min.js"></script>
<script charset="utf-8" type="text/javascript" src="${basePath}/plugins/jPlayer/js/jquery.transform.js"></script>
<script charset="utf-8" type="text/javascript" src="${basePath}/plugins/jPlayer/js/jquery.grab.js"></script>
<script charset="utf-8" type="text/javascript" src="${basePath}/plugins/jPlayer/js/mod.csstransforms.min.js"></script>
<script charset="utf-8" type="text/javascript" src="${basePath}/plugins/jPlayer/js/circle.player.js"></script>
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
				$('#wechat,#wechat_sub_menu_li_sub_menu_10').addClass('active');
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
						<li>语音消息</li>
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
										<strong>Success!</strong> 语音信息操作成功。
									</div>
								</c:otherwise>
							</c:choose>
						</c:if>
						<div class="col-md-6 message-size">
							<div class="new-msg center" style="height: 300px;line-height: 300px;">
								<a href="${basePath}/h/w014_edit.ac"
									class="btn btn-link" style="margin-top: 100px;"><div>
										<i class="icon-comments-alt icon-5"></i>
									</div>新增语音消息</a>
							</div>
						</div>
						<c:forEach items="${beans}" var="bean1" varStatus="n">
							<div class="col-md-6 message-size">
								<ul id="message-info" class="unstyled">
									<li class="article pos-rel cover">
										<div class="msg-date">关键字:${bean1.keyword}</div>
										<div class="msg-date">${bean1.last_updated}</div>
										<div class="pic-url">
												<div style="margin-left: 30px;padding-bottom:0px;">
													<div id="${bean1.id}_player" class="cp-jplayer"></div>
													<div id="${bean1.id}_cp_container" class="cp-container">
														<div class="cp-buffer-holder"> <!-- .cp-gt50 only needed when buffer is > than 50% -->
															<div class="cp-buffer-1"></div>
															<div class="cp-buffer-2"></div>
														</div>
														<div class="cp-progress-holder"> <!-- .cp-gt50 only needed when progress is > than 50% -->
															<div class="cp-progress-1"></div>
															<div class="cp-progress-2"></div>
														</div>
														<div class="cp-circle-control"></div>
														<ul class="cp-controls">
															<li><a href="#" class="cp-play" tabindex="1">play</a></li>
															<li><a href="#" class="cp-pause" style="display:none;" tabindex="1">pause</a></li> <!-- Needs the inline style here, or jQuery.show() uses display:inline instead of display:block -->
														</ul>
													</div>
													<script type="text/javascript">
													$(document).ready(function() {
														//var myCirclePlayer=
														new CirclePlayer("#${bean1.id}_player",
														{
															mp3 : "${bean1.url}"
														}, {
															cssSelectorAncestor: "#${bean1.id}_cp_container",
															swfPath: "${basePath}/plugins/jPlayer/js",
															supplied: "mp3,webma, m4a, oga,fla,wav",
															wmode: "window"
														});
													});
													</script>
												</div>
										</div>
										<h4 class="title">${bean1.title}</h4>
									</li>
									<li class="msg-actions center">
										<a href="${basePath}/h/w014_edit.ac?id=${bean1.id}" class="btn green">
											<i class="icon-pencil"></i> 编辑
										</a> 
										<a href="javascript:void(0)" class="btn btn-danger" onclick="if(confirm('确认删除吗?')){location.href='${basePath}/h/w014_del.ac?id=${bean1.id}'};" rel="nofollow">
											<i class="icon-trash"></i>删除
										</a>
									</li>
								</ul>
							</div>
						</c:forEach>
						<customtag:pagingext func="loadUrlPage" params="'h/w014_','init'" />
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