<%--
/*
 * 系统管理_课程管理_课程表_编辑 (页面)
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-04-07  wuxiaogang   程序・发布
 * -------- ----------- ------------ ------------------------------------------
 * Copyright 2014 jfq System. - All Rights Reserved.
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
<%@include file="../include/admin_title.jsp" %>
<%@ include file="../include/public_js_css.jsp"%>
<link href="${basePath}/css/messages.css" media="all" rel="stylesheet" type="text/css" />
<link href="${basePath}/css/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${basePath}/css/font-awesome/css/font-awesome-ie7.css" rel="stylesheet">

<link rel="stylesheet" type="text/css" href="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/bootstrap-datepicker/css/datepicker.css" />
<link rel="stylesheet" type="text/css" href="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/bootstrap-datepicker/less/datepicker.less" />

<link rel="stylesheet" type="text/css" href="${basePath}/plugins/bootstrap.admin.theme/assets/assets/plugins/select2/select2_metro.css" />
<link rel="stylesheet" type="text/css" href="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/jquery-multi-select/css/multi-select.css" />
</head>
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
				$('#course,#course_sub_menu_l3').addClass('active');
				$('#course_arrow').addClass('open');
				$('#course_sub_menu').show();
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
						课程管理 <small><span class="help-inline">在这里你可以编辑课程信息</span></small>
					</h3>
					<ul class="page-breadcrumb breadcrumb">
						<li><i class="fa fa-home"></i> <a
							href="${basePath }/home_init.ac">Home</a> <i
							class="fa fa-angle-right"></i></li>
						<li><a href="#">系统管理</a> <i class="fa fa-angle-right"></i></li>
						<li><a href="${basePath}/h/c102_init.ac">课程管理</a> <i class="fa fa-angle-right"></i></li>
						<li>编辑</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			<!-- END PAGE HEADER-->
			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
				<div class="col-md-12">
					<form accept-charset="UTF-8"  action="${basePath}/h/c102_save.ac" class="edit_article" id="edit_article_13632" method="post">
						<s:token></s:token>
						<input name="bean.id" type="hidden" value="${bean.id}">
						<div class="well form-inline">
							 &nbsp; 
							<label>会员名
								<c:choose>
									<c:when test="${bean.user_name!=null && fn:length(bean.user_name)>0}">
										<input class="upload-wrapper" readonly="readonly" style="background-color: #eee;" id="message_user_name" name="bean.user_name" size="30" value="${bean.user_name }" placeholder="会员登录名"  type="text">
									</c:when>
									<c:otherwise>
										<input class="upload-wrapper" id="message_user_name" name="bean.user_name" size="30" placeholder="会员登录名"  type="text">
									</c:otherwise>
								</c:choose>
							</label>
							 &nbsp; 
							 <label>密码
								<input class="upload-wrapper" id="message_passwd" name="bean.passwd" size="30" value="${bean.passwd }" placeholder="登录密码"  type="text">
							</label>
							&nbsp; 性别
							 <label title="男">
							<input name="bean.sex" value="0"
							<c:if test="${bean.sex=='0'}">
							 	checked="checked"
							 </c:if>
							 type="radio">
							 男</label>
							 <label title="女">
							<input name="bean.sex" value="1"
							<c:if test="${bean.sex=='1'}">
							 	checked="checked"
							 </c:if>
							 type="radio">
							 女</label>
							 &nbsp; 
							 <label title="是否可用">
							<input name="bean.is_enabled" value="1"
							<c:if test="${bean.is_enabled=='1'}">
							 	checked="checked"
							 </c:if>
							 type="checkbox">
							 是否可用</label>
							  &nbsp; 
							  会员类型
							 <label title="老师">
							<input name="bean.user_type" value="0"
							<c:if test="${bean.user_type=='0'}">
							 	checked="checked"
							 </c:if>
							 type="radio">
							 老师</label>
							 <label title="家长">
							<input name="bean.user_type" value="1"
							<c:if test="${bean.user_type=='1'}">
							 	checked="checked"
							 </c:if>
							 type="radio">
							 家长</label>
						</div>
						<div class="well form-inline">
							 &nbsp;  <label class="control-label">教练</label>
							 <label class="control-label col-md-12">
								<select name="bean.sids" id="student_select2_sample2" class="form-control select2" multiple>
										<optgroup label="教练列表">
											<c:forEach items="${teacher_beans_beans}" var="teacher">
												<c:set var="xxcc" value='' />
												<c:forEach items="${member_student_beans}" var="the_student">
													<c:if test="${teacher.id==teacher.id}">
													<c:set var="xxcc" value='selected="selected"' />
													</c:if>
												</c:forEach>
												<option ${xxcc} value="${teacher.id}">${teacher.name }</option>
											</c:forEach>
										</optgroup>
									</select>
							</label>
						</div>
						<div class="well form-inline">
							 &nbsp;  <label class="control-label">学员</label>
							 <label class="control-label col-md-12">
								<select name="bean.sids" id="student_select2_sample2" class="form-control select2" multiple>
										<optgroup label="学员列表">
											<c:forEach items="${student_beans}" var="student">
												<c:set var="xxcc" value='' />
												<c:forEach items="${member_student_beans}" var="the_student">
													<c:if test="${the_student.id==student.id}">
													<c:set var="xxcc" value='selected="selected"' />
													</c:if>
												</c:forEach>
												<option ${xxcc} value="${student.id}">${student.name }</option>
											</c:forEach>
										</optgroup>
									</select>
							</label>
						</div>
						<div class="form-group">
							<label for="article_nickname">用户昵称</label> <input
								class="form-control" id="article_nickname" name="bean.nickname"
								type="text" value="${bean.nickname}">
						</div>
						<div class="form-group">
							<label for="article_birthdate">生日</label> 
									<div class="input-group input-medium date date-picker" data-date-format="yyyy-mm-dd" data-date-start-date="-100y">
										<input id="bean_birthdate" type="text" name="bean.birthdate" value="${bean.birthdate}" class="form-control" readonly="">
										<span class="input-group-btn">
										<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
										</span>
									</div>
						</div>
						<div class="form-group">
							<label for="article_addres">地址</label> <input class="form-control"
								id="article_addres" name="bean.addres" size="150" type="text" value="${bean.addres}">
						</div>
						<div class="form-group">
							<label for="article_description">详情</label>
							<div class="qeditor_border">
								<textarea name="bean.detail_info" style="height: 300px;width: 100%;" id="article_description" >
									${bean.detail_info}
								</textarea>
							</div>
						</div>
						<div class="form-actions">
							<input class="btn btn-primary" name="commit" type="submit"
								value="保存"> | <a
								href="${basePath}/h/c102_init.ac">返回</a>
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
	<script type="text/javascript" src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
	<script type="text/javascript" src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/bootstrap-datepicker/js/locales/bootstrap-datepicker.zh-CN.js"></script>
	<script type="text/javascript" src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/select2/select2.min.js"></script>
	<script type="text/javascript" src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/jquery-multi-select/js/jquery.multi-select.js"></script>
	<script type="text/javascript" src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/jquery-multi-select/js/jquery.quicksearch.js"></script>   
	<!-- END FOOTER -->
</body>
<!-- END BODY -->
</html>
<script type="text/javascript">
jQuery(document).ready(function() {
	$('.date-picker').datepicker();
	//
	$('#student_select2_sample2').select2({
        placeholder: "点击选择关联学员",
        allowClear: true
    });
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
