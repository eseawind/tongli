<%--
/*
 * 微信服务_自动回复_视频消息_编辑 (页面)
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
						<li><a href="#">视频消息</a> <i class="fa fa-angle-right"></i></li>
						<li>编辑</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			<!-- END PAGE HEADER-->
			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
				<div class="col-md-12">
					<form accept-charset="UTF-8" action="${basePath}/h/w006_save.ac" class="edit_message" id="edit_message_8932" method="post">
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
												<input class="idx" id="id" name="id" type="hidden" value="${bean.id }">
												<input class="title" id="title" name="title${bean.id }" value="${bean.title }" type="hidden">
												<input class="pic-url" id="picurl" name="picurl${bean.id }" value="${bean.picurl }" type="hidden">
												<input class="url" id="url" name="url${bean.id }" value="${bean.url }" type="hidden">
												<div class="pic-url">
													 <img src="${bean.picurl }" alt="">
												</div>
												<h4 class="title">${bean.title }</h4>
											</li>
										</c:when>
										<c:otherwise>
											<li id="data_rid_0" data-img-version="circle.header" class="article pos-rel cover">
												<div class="msg-date">${date}</div> 
												<c:set var="uuid" value="<%=IdUtils.createUUID(32)%>"></c:set>
												<input class="idx" id="id" name="id" type="hidden" value="${uuid}">
												<input class="title" id="title" name="title${uuid}" type="hidden">
												<input class="pic-url" id="picurl" name="picurl${uuid}" type="hidden">
												<input class="url" id="url" name="url${uuid}" type="hidden">
												<div class="pic-url">
													<span class="default-tip" style="">封面图片</span>
													 <img src="" alt="" style="display: none">
												</div>
												<h4 class="title">视频标题</h4>
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
										<label>视频标题</label> 
										<input class="col-md-12 form-control placeholder-no-fix" id="title" name="" type="text" value="${bean.title }"> 
										<label>封面(最佳大小: 700 x 400)</label>
										<div class="upload-wrapper">
											<a id="link-select-image-modal" class="btn btn-info" href="#selectImageModal"
												data-img-version="circle.header" data-toggle="modal">选图片</a> 
												
											<div id="article-info-filequeue" class="filequeue" <c:if test="${bean.picurl==null}">style="display: none;"</c:if>>
												<div class="uploadifyQueueItem item">
													<img class="pic-url" id="new_picurl"  src="${bean.picurl}">
												</div>
												<!--TODO fix to span_xx -->
												<div class="clearfix"></div>
											</div>
										</div>
										<!-- js in edit.js rel obj_id and file_queue_id -->
		
										<label>链接</label>
										<div class="controls">
											<div class="input-append">
												<input class="col-md-12 form-control placeholder-no-fix" data-target=".resource-url" id="url" name="" placeholder="如果是外链地址,请直接输入" type="text" value="${bean.url}"> 
												<a class="btn btn-info" data-toggle="modal" href="#linkModal">选链接</a>
											</div>
										</div>
									</c:when>
									<c:otherwise>
										<label>视频标题</label> 
										<input class="col-md-12 form-control placeholder-no-fix" placeholder="歌曲名称" id="title" name="" type="text" value=""> 
										<label>视频描述</label> 
										<input class="col-md-12 form-control placeholder-no-fix" placeholder="视频描述" id="description" name="" type="text" value=""> 
										<div class="controls">
											<div class="input-append">
												<a id="link-select-file-modal" class="btn btn-info" data-toggle="modal" >上传视频文件</a>
												<a id="link-select-image-modal" class="btn btn-info" data-toggle="modal" >上传视频图片</a>
											</div>
										</div>
										<label>视频链接</label> 
										<input class="col-md-12 form-control placeholder-no-fix" placeholder="如果是外链地址,请直接输入" id="musicurl" name="" type="text" value=""> 
										<label>高品质视频链接，wifi环境优先使用该链接播放视频</label> 
										<input class="col-md-12 form-control placeholder-no-fix" placeholder="如果是外链地址,请直接输入" id="hqmusicurl" name="" type="text" value=""> 
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
							editor.hideDialog();
						}
					});
				});
			});
			K('#link-select-image-modal').click(function() {
				editor.loadPlugin('image',function() {
					editor.plugin.imageDialog({
						imageUrl : $('#new_picurl').attr('src'),clickFn : function(
							url,title,width,height,border,align) {
								url=url.replace('/n3/','/n2/');
								$('#new_picurl').attr('src',url);
								editor.hideDialog();
								$('.msg-edit').find('#article-info-filequeue').show();
								
								var data_rid=$('.msg-edit').find('.rid').val();
								$('#'+data_rid).find('.pic-url').find('img').attr('src',url);
								$('#'+data_rid).find('#picurl').val(url);
								$('#'+data_rid).find('.pic-url').find('.default-tip').remove();
								$('#'+data_rid).find('.pic-url').find('img').show();
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
		$('.msg-edit').find('#url').blur(function(){
			var data_rid=$('.msg-edit').find('.rid').val();
			$('#'+data_rid).find('#url').val($(this).val());
		});
	});
</script>