<%--
/*
 * 微信服务_自动回复_语音消息_编辑 (页面)
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
<%@page import="cn.com.softvan.common.IdUtils"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
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

<link href="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>


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
			var myCirclePlayer;
			// 音频播放
			function voicePlay(url){
				myCirclePlayer=new CirclePlayer("#jquery_jplayer_1",
				{
					wav: url
				}, {
					cssSelectorAncestor: "#cp_container_1",
					swfPath: "${basePath}/plugins/jPlayer/js",
					supplied: "mp3,webma, m4a, oga,fla,wav",
					wmode: "window"
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
						<li><a href="#">自动回复</a> <i class="fa fa-angle-right"></i></li>
						<li><a href="#">语音消息</a> <i class="fa fa-angle-right"></i></li>
						<li>编辑</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			<!-- END PAGE HEADER-->
			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
				<div class="col-md-12">
					<form accept-charset="UTF-8" action="${basePath}/h/w014_save.ac" class="edit_message" id="edit_message_8932" method="post">
						<s:token></s:token>
						<input id="message_id" name="bean.id" 
						<c:choose>
							<c:when test="${bean.id!=null}">
							value="${bean.id }" 
							</c:when>
							<c:otherwise>
							value="<%=IdUtils.createUUID(32)%>" 
							</c:otherwise>
						</c:choose>
						 type="hidden">
						
						<div class="well form-inline">
								<label>自动回复:关键字 
									<input class="upload-wrapper" id="message_keyword" name="bean.keyword" size="30" value="${bean.keyword }" placeholder="多个关键字请用空格隔开" title="客户发送和关键字匹配的信息，系统回复该内容" type="text">
								</label>
								 <label  title="客户发送关键字以外的信息时，系统回复该内容">
								 <input  <c:if test="${bean.default_flag=='1'}"> checked="checked" </c:if> name="bean.default_flag" type="checkbox" value="1">
								 默认回复</label>
								&nbsp; 
								<label title="客户首次关注微信时，系统自动回复该内容">
								<input <c:if test="${bean.subscribe_flag=='1'}"> checked="checked" </c:if> name="bean.subscribe_flag" type="checkbox" value="1">
								首次关注回复</label>
								<span class="help-inline">(设置后，在微信里回复关键字，系统会回复相应内容)</span>
						</div>
						<div class="row-fluid">
							<div class="col-md-4 message-size">
								<ul id="message-info" class="unstyled">
									<c:choose>
										<c:when test="${bean!=null}">
											<li id="data_rid_0" data-img-version="circle.header" class="article pos-rel cover">
												<div class="msg-date">${bean.last_updated}</div>
												<input class="title" name="bean.title" value="${bean.title }" type="hidden">
												<input class="url" id="url" name="bean.url" value="${bean.url}" type="hidden">
												<div class="pic-url" style="margin-left: 30px;padding-bottom:20px;">
														<div id="jquery_jplayer_1" class="cp-jplayer"></div>
														<div id="cp_container_1" class="cp-container">
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
																<li><a class="cp-play" tabindex="1">play</a></li>
																<li><a class="cp-pause" style="display:none;" tabindex="1">pause</a></li> <!-- Needs the inline style here, or jQuery.show() uses display:inline instead of display:block -->
															</ul>
														</div>
														<script type="text/javascript">
														jQuery(document).ready(function() {
															new CirclePlayer("#jquery_jplayer_1",
																{
																	wav: '${bean.url}'
																}, {
																	cssSelectorAncestor: "#cp_container_1",
																	swfPath: "${basePath}/plugins/jPlayer/js",
																	supplied: "mp3,webma, m4a, oga,fla,wav",
																	wmode: "window"
																});
														});
													</script>
												</div>
												<h4 class="title">${bean.title }</h4>
											</li>
										</c:when>
										<c:otherwise>
											<li id="data_rid_0" data-img-version="circle.header" class="article pos-rel cover">
												<div class="msg-date">${date}</div> 
												<input class="title" id="title" name="bean.title" type="hidden">
												<input class="url" id="url" name="bean.url" type="hidden">
												<div class="pic-url" style="margin-left: 30px;padding-bottom:20px;">
														<div id="jquery_jplayer_1" class="cp-jplayer"></div>
														<div id="cp_container_1" class="cp-container">
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
																<li><a class="cp-play" tabindex="1">play</a></li>
																<li><a class="cp-pause" style="display:none;" tabindex="1">pause</a></li> <!-- Needs the inline style here, or jQuery.show() uses display:inline instead of display:block -->
															</ul>
														</div>
												</div>
												<h4 class="title">语音标题</h4>
											</li>
											</c:otherwise>
									</c:choose>
								</ul>
							</div>
							<!--  -->
							<div class="col-md-6 msg-edit pos-rel" style="margin-top: 0px;">
								<input class="rid" id="" name="" type="hidden" value="data_rid_0">
								<c:choose>
									<c:when test="${bean!=null && bean!='null'}">
										<label>语音标题</label> 
										<input class="col-md-12 form-control placeholder-no-fix" id="title" name="" type="text" value="${bean.title }"> 
		
										<label>链接</label>
										<div class="controls">
											<div class="input-append">
												<input class="col-md-12 form-control placeholder-no-fix" data-target=".resource-url" id="url" name="" placeholder="如果是外链地址,请直接输入" type="text" value="${bean.url}"> 
												<a class="btn btn-info" data-toggle="modal" href="#linkModal">选链接</a>
											</div>
										</div>
									</c:when>
									<c:otherwise>
										<label>语音标题</label> 
										<input class="col-md-12 form-control placeholder-no-fix" placeholder="歌曲名称" id="title" name="" type="text" value=""> 
										<label><a id="link-select-file-modal" class="btn btn-info" data-toggle="modal" >上传音乐文件</a></label>
										<div id="article-info-filequeue" class="filequeue" >
											<input class="col-md-12 form-control placeholder-no-fix url" readonly="readonly" placeholder="暂不支持外链" name="" type="text" value="${bean.url}"> 
										</div>
									</c:otherwise>
								</c:choose>
								<span class="msg-arrow arrow-out pos-abs"></span> 
								<span class="msg-arrow arrow-in pos-abs"></span>
							</div>
						</div>
						<div style="clear: both;"></div>
						<div class="form-actions center">
							<input class="btn btn-primary btn-large" name="commit"
								type="submit" value="完成">
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
<script type="text/javascript">
	jQuery(document).ready(function() {
		KindEditor.ready(function(K) {
			var editor = K.editor({
						resizeType : 2,
						uploadJson : '${basePath}/uploadFile?isrich=1',
						fileManagerJson : '${basePath}/plugins/editor/jsp/file_manager_json.jsp',
						allowFileManager : true
			});
			K('#link-select-file-modal').click(function() {
				editor.loadPlugin('insertfile', function() {
					editor.plugin.fileDialog({
						fileUrl : K('#url').val(),
						clickFn : function(url, title) {
							K('#url').val(url);
							K('.url').val(url);
							//音频
							voicePlay(url);
							editor.hideDialog();
						}
					});
				});
			});
		});
		$('.msg-edit').find('#title').blur(function(){
			var data_rid=$('.msg-edit').find('.rid').val();
			if($(this).val()){
				$('#'+data_rid).find('.title').html($(this).val());
			}
			$('#'+data_rid).find('#title').val($(this).val());
			//myAlert(data_rid);
		});
		/* $('.msg-edit').find('#url').blur(function(){
			var data_rid=$('.msg-edit').find('.rid').val();
			$('#'+data_rid).find('#url').val($(this).val());
		}); */
	});
</script>