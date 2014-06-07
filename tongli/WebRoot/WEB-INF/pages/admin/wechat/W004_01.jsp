<%--
/*
 * 微信服务_素材管理_文章_编辑 (页面)
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-03-18  wuxiaogang   程序・发布
 * -------- ----------- ------------ ------------------------------------------
 * Copyright 2014 上海人保财险微信 System. - All Rights Reserved.
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
<link href="${basePath}/css/messages.css" media="all" rel="stylesheet"	type="text/css" />

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
				$('#wechat,#w004_init').addClass('active');
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
							href="${basePath }/home_init.ac">主页</a> <i
							class="fa fa-angle-right"></i></li>
						<li><a href="#">微信服务</a> <i class="fa fa-angle-right"></i></li>
						<li><a href="#">素材管理</a> <i class="fa fa-angle-right"></i></li>
						<li><a href="${basePath}/h/w004_init.ac">文章</a> <i class="fa fa-angle-right"></i></li>
						<li>编辑</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			<!-- END PAGE HEADER-->
			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
				<div class="col-md-12">
					<form accept-charset="UTF-8"  action="${basePath}/h/w004_save.ac" class="edit_article" id="edit_article_13632" method="post">
						<s:token></s:token>
						<input name="bean.id" type="hidden" value="${bean.id}">
						<div class="form-group">
							<label for="article_title">标题</label> <input class="form-control"
								id="article_title" name="bean.title" size="30" type="text" value="${bean.title}">
						</div>
						<div class="portlet box btn default btn-block">
							<div class="portlet-title">
								<div class="caption"  id="select-image-modal"><i class="fa fa-anchor"></i><font color="#000">点击上传[标题图片]</font></div>
								<div class="tools">
									<a href="javascript:;" class="collapse"></a>
									<a href="javascript:;" class="remove"></a>
								</div>
							</div>
							<div class="portlet-body" style="display: block; ">
								<input id="input_pic_url"  name="bean.pic_url" size="30" type="hidden" value="${bean.pic_url}">
								<img id="img_pic_url" alt="" src="${bean.pic_url}">
							</div>
						</div>
						<div class="form-group">
							<label for="article_brief_info">简介</label>
							<div class="qeditor_border">
								<textarea class="form-control" name="bean.brief_info" style="height: 100px;width: 100%;" id="article_brief_info" >${bean.brief_info}</textarea>
							</div>
						</div>
						<div class="form-group">
							<label for="article_description">内容</label>
							<div class="qeditor_border">
								<textarea name="bean.detail_info" style="height: 300px;width: 100%;" id="article_description" >
									${bean.detail_info}
								</textarea>
							</div>
						</div>
						<div class="form-actions">
							<input class="btn btn-primary" name="commit" type="submit"
								value="保存"> | <a
								href="${basePath}/h/w004_init.ac">返回</a>
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
		K.create('#article_description', {
			 resizeType : 2,
	         uploadJson : '${basePath}/uploadFile?isrich=1',
	         fileManagerJson : '${basePath}/plugins/editor/jsp/file_manager_json.jsp',
			 allowFileManager : true
		});
		//--图片
		var editor = K.editor({
			resizeType : 2,
			uploadJson : '${basePath}/uploadFile?isrich=1',
			fileManagerJson : '${basePath}/plugins/editor/jsp/file_manager_json.jsp',
			allowFileManager : true
		});
		K('#select-image-modal').click(function() {
			editor.loadPlugin('image',function() {
				editor.plugin.imageDialog({
					imageUrl : $('#img_pic_url').attr('src'),clickFn : function(
						url,title,width,height,border,align) {
							$('#img_pic_url').attr('src',url);
							$('#input_pic_url').val(url);
							editor.hideDialog();
						}
				});
			});
		});
	});
});
</script>
