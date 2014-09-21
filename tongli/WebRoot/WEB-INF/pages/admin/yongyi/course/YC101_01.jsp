<%--
/*
 * 系统管理_课程管理_编辑 (页面)
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-04-01  wuxiaogang   程序・发布
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
				$('#sys10,#sys10_sub_menu_li_1_sub_menu_1').addClass('active');
				$('#sys10_arrow,#sys10_sub_menu_li_1_arrow').addClass('open');
				$('#sys10_sub_menu,#sys10_sub_menu_li_1_sub_menu').show();
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
						课程管理 <small><span class="help-inline">在这里你可以编辑课程信息</span></small>
					</h3>
					<ul class="page-breadcrumb breadcrumb">
						<li><i class="fa fa-home"></i> <a
							href="${basePath }/home_init.ac">Home</a> <i
							class="fa fa-angle-right"></i></li>
						<li><a href="#">系统管理</a> <i class="fa fa-angle-right"></i></li>
						<li><a href="${basePath}/h/yc101_init.ac">课程管理</a> <i class="fa fa-angle-right"></i></li>
						<li>编辑</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			<!-- END PAGE HEADER-->
			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
				<div class="col-md-12">
					<form accept-charset="UTF-8"  action="${basePath}/h/yc101_save.ac" class="edit_article" id="edit_article_13632" method="post">
						<s:token></s:token>
						<input name="bean.id" type="hidden" value="${bean.id}">
						<div class="well form-inline">
							 &nbsp; 
							<label>关键字 
								<input class="upload-wrapper" id="message_keyword" name="bean.keyword" size="30" value="${bean.keyword }" placeholder="多个关键字请用空格隔开" title="关键字" type="text">
							</label>
							&nbsp; 
							 <label title="是否室内">
							<input name="bean.is_indoor" value="1"
							<c:if test="${bean.is_indoor=='1'}">
							 	checked="checked"
							 </c:if>
							 type="checkbox">
							 室内</label>
							 &nbsp; 
							 <label title="是否包含场地费">
							<input name="bean.is_site_fee" value="1"
							<c:if test="${bean.is_site_fee=='1'}">
							 	checked="checked"
							 </c:if>
							 type="checkbox">
							 含场地费</label>
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
							<label for="article_title">课程名称</label> <input class="form-control"
								id="article_title" name="bean.title" size="30" type="text" value="${bean.title}">
						</div>
						<div class="form-group">
							 &nbsp;  <label class="control-label">课程主题</label>
							 <label class="control-label col-md-12">
							 <div class="input-group">
								<span class="input-group-addon">
								<i class="fa fa-align-center"></i>
								</span>
								<select name="bean.subject_id" id="teacher_select2_sample2"  class="form-control select2me" data-placeholder="选择课程主题..">
									<optgroup label="课程主题列表">
									<c:forEach items="${course_subject}" var="c_subject">
										<option <c:if test="${c_subject.variable_sub_id==bean.subject_id}">selected="selected"</c:if> value="${c_subject.variable_sub_id}">${c_subject.variable_sub_name}</option>
									</c:forEach>
									</optgroup>
								</select>
							</div>
							</label>
						</div>
						<div class="form-group">
							<label for="article_course_type">课种</label> <input class="form-control"
								id="article_course_type" name="bean.course_type" size="50" type="text" value="${bean.course_type}">
						</div>
						<div class="form-group">
							<label for="article_duration">时长</label> <input class="form-control"
								id="article_duration" name="bean.duration" size="50" type="text" value="${bean.duration}">
						</div>
						<div class="form-group">
							<label for="article_duration_unit">时长单位</label> <input class="form-control"
								id="article_duration_unit" name="bean.duration_unit" size="50" type="text" value="${bean.duration_unit}">
						</div>
						<div class="form-group">
							<label for="article_age_group">年龄段</label> <input class="form-control"
								id="article_age_group" name="bean.age_group" size="50" type="text" value="${bean.age_group}">
						</div>
						<div class="form-group">
							<label for="article_number">人数</label> <input class="form-control"
								id="article_number" name="bean.number" size="50" type="text" value="${bean.number}">
						</div>
						<div class="form-group">
							<label for="article_market_price">非会员价</label> <input class="form-control"
								id="article_market_price" name="bean.market_price" size="50" type="text" value="${bean.market_price}">
						</div>
						<div class="form-group">
							<label for="article_addres">地址</label>
							<div class="checkbox-list">
							<c:forEach items="${addres_beans}" var="addres_bean" varStatus="i">
								<c:set var="i_checkbox" value="" />
								<c:forEach items="${course_addres_beans}" var="addres_bean2">
									<c:if test="${addres_bean.id==addres_bean2.id}">
										<c:set var="i_checkbox" value="checked='checked'" />
									</c:if>
								</c:forEach>
							
								<label>
								<input type="checkbox" name="bean.aids" ${i_checkbox} value="${addres_bean.id}">${addres_bean.addres}
								</label>
							</c:forEach>
							</div>
						</div>
						<div class="form-group">
							<label for="article_member_price">会员价</label> <input class="form-control"
								id="article_member_price" name="bean.member_price" size="50" type="text" value="${bean.member_price}">
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
								href="${basePath}/h/yc101_init.ac">返回</a>
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
		<script type="text/javascript" src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/select2/select2.min.js"></script>
	<script type="text/javascript" src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/jquery-multi-select/js/jquery.multi-select.js"></script>
	<script type="text/javascript" src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/jquery-multi-select/js/jquery.quicksearch.js"></script>   
	
	<script type="text/javascript" src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/clockface/js/clockface.js"></script>
	<!-- END FOOTER -->
</body>
<!-- END BODY -->
</html>
<script type="text/javascript">
jQuery(document).ready(function() {
	KindEditor.ready(function(K) {
		//--编辑框
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
		//--
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
