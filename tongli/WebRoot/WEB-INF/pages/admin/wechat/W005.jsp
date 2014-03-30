<%--
/*
 * 微信服务_自动回复_音乐消息 (页面)
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-03-14  wuxiaogang   程序・发布
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
<title>微信服务-自动回复-音乐【车主管家】</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<meta name="MobileOptimized" content="320">
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
				$('#wechat,#wechat_sub_menu_li_sub_menu_4').addClass('active');
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
						<li>音乐消息</li>
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
										<strong>Success!</strong> 音乐信息操作成功。
									</div>
								</c:otherwise>
							</c:choose>
						</c:if>
						<div class="col-md-6 message-size">
							<div class="new-msg center" style="height: 300px;line-height: 300px;">
								<a href="${basePath}/h/w005_edit.ac"
									class="btn btn-link" style="margin-top: 100px;"><div>
										<i class="icon-comments-alt icon-5"></i>
									</div>新建音乐消息</a>
							</div>
						</div>
						<c:forEach items="${beans}" var="bean1" varStatus="n">
							<div class="col-md-6 message-size">
							<form accept-charset="UTF-8" action="${basePath}/h/w005_edit.ac?id=${bean1.id}" class="edit_message" method="post">
								<s:token></s:token>
								<ul id="message-info" class="unstyled">
									<li class="article pos-rel cover">
										<div class="msg-date">关键字:${bean1.keyword}</div>
										<div class="msg-date">${bean1.last_updated}</div>
										<div class="pic-url">
											<script type="text/javascript">
												 $(document).ready(function(){
												  $("#jquery_jplayer_1").jPlayer({
												   ready: function () {
												    $(this).jPlayer("setMedia", {
												     m4a: "/media/mysound.mp4",
												     oga: "/media/mysound.ogg"
												    });
												   },
												   swfPath: "${basePath}/plugins/jplayer",
												   supplied: "m4a, oga"
												  });
												 });
												</script>
												<div id="jquery_jplayer_1"></div>
												<div id="jp_container_1">
												 <a href="#" class="jp-play">Play</a>
												 <a href="#" class="jp-pause">Pause</a>
												</div>
										</div>
									</li>
									<li class="msg-actions center">
										<a href="${basePath}/h/w005_edit.ac?id=${bean1.id}" class="btn green">
											<i class="icon-pencil"></i> 编辑
										</a> 
										<a href="javascript:void(0)" class="btn btn-danger" onclick="if(confirm('确认删除吗?')){location.href='${basePath}/h/w005_del.ac?id=${bean1.id}'};" rel="nofollow">
											<i class="icon-trash"></i>删除
										</a>
									</li>
								</ul>
							</form>
							</div>
						</c:forEach>
						
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
</script>