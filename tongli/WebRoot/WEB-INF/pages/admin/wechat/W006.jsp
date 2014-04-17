<%--
/*
 * 微信服务_自动回复_视频消息 (页面)
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-04-11  wuxiaogang   程序・发布
 * 1.01     2014-04-14  wuxiaogang   程序・更新  完善
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
<link href="${basePath}/css/messages.css" media="all" rel="stylesheet" type="text/css" />

<link href="${basePath}/plugins/jPlayer/skin/blue.monday/jplayer.blue.monday.css" rel="stylesheet" type="text/css" />

<script charset="utf-8" type="text/javascript" src="${basePath}/plugins/jPlayer/js/jquery.jplayer.min.js"></script>
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
								<a href="${basePath}/h/w006_edit.ac" class="btn btn-primary"><i class="fa fa-book"></i> 新增视频</a>
							</div>
							<c:forEach items="${beans}" var="bean">
							<div class="col-md-9">
							<ul id="message-info" class="unstyled">
								<li class="article pos-rel cover">
									<div class="msg-date">关键字:${bean.keyword}</div>
									<div class="msg-date">${bean.last_updated}</div>
									<div class="well alert alert-success" style="padding-left: 30px;">
										<div id="jp_container_1${bean.id}" class="jp-video jp-video-360p">
											<div class="jp-type-single">
												<div id="jquery_jplayer_1${bean.id}" class="jp-jplayer"></div>
												<div class="jp-gui">
													<div class="jp-video-play">
														<a href="javascript:;" class="jp-video-play-icon" tabindex="1">play</a>
													</div>
													<div class="jp-interface">
														<div class="jp-progress">
															<div class="jp-seek-bar">
																<div class="jp-play-bar"></div>
															</div>
														</div>
														<div class="jp-current-time"></div>
														<div class="jp-duration"></div>
														<div class="jp-controls-holder">
															<ul class="jp-controls">
																<li><a href="javascript:;" class="jp-play" tabindex="1">play</a></li>
																<li><a href="javascript:;" class="jp-pause" tabindex="1">pause</a></li>
																<li><a href="javascript:;" class="jp-stop" tabindex="1">stop</a></li>
																<li><a href="javascript:;" class="jp-mute" tabindex="1" title="mute">mute</a></li>
																<li><a href="javascript:;" class="jp-unmute" tabindex="1" title="unmute">unmute</a></li>
																<li><a href="javascript:;" class="jp-volume-max" tabindex="1" title="max volume">max volume</a></li>
															</ul>
															<div class="jp-volume-bar">
																<div class="jp-volume-bar-value"></div>
															</div>
															<ul class="jp-toggles">
																<li><a href="javascript:;" class="jp-full-screen" tabindex="1" title="full screen">full screen</a></li>
																<li><a href="javascript:;" class="jp-restore-screen" tabindex="1" title="restore screen">restore screen</a></li>
																<li><a href="javascript:;" class="jp-repeat" tabindex="1" title="repeat">repeat</a></li>
																<li><a href="javascript:;" class="jp-repeat-off" tabindex="1" title="repeat off">repeat off</a></li>
															</ul>
														</div>
														<div class="jp-details">
															<ul>
																<li><span class="jp-title">${bean.title}</span></li>
															</ul>
														</div>
													</div>
												</div>
												<div class="jp-no-solution">
													<span>Update Required</span>
													To play the media you will need to either update your browser to a recent version or update your <a href="http://get.adobe.com/flashplayer/" target="_blank">Flash plugin</a>.
												</div>
											</div>
										</div>
										<script type="text/javascript">
										$(document).ready(function(){
											$("#jquery_jplayer_1${bean.id}").jPlayer({
												ready: function () {
													$(this).jPlayer("setMedia", {
														title: "${bean.title}",
														m4v: "${bean.url}",
														poster: "${bean.picurl}"
													});
												},
												swfPath: "${basePath}/plugins/jPlayer/js",
												supplied: "m4v,webmv,ogv,flv",
												size: {
													width: "640px",
													height: "360px",
													cssClass: "jp-video-360p"
												},
												smoothPlayBar: true,
												keyEnabled: true,
												remainingDuration: true,
												toggleDuration: true
											});
										});
										</script>
										</div>
									</li>
									<li class="msg-actions center">
										<a href="${basePath}/h/w006_edit.ac?id=${bean.id}" class="btn green">
											<i class="icon-pencil"></i> 编辑
										</a> 
										<a href="javascript:void(0)" class="btn btn-danger" onclick="if(confirm('确认删除吗?')){location.href='${basePath}/h/w006_del.ac?id=${bean.id}'};" rel="nofollow">
											<i class="icon-trash"></i>删除
										</a>
									</li>
								</ul>
							</div>
						</c:forEach>
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
function loadUrlPage(offset, url, event) {
	location.href='${basePath}/' + url + event+'.ac?offset=' + offset;
}
</script>