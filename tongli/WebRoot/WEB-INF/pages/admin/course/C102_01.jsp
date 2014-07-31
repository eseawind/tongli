<%--
/*
 * 系统管理_课程管理_课程表_编辑 (页面)
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-04-07  wuxiaogang   程序・发布
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
<%@include file="../include/admin_title.jsp" %>
<%@ include file="../include/public_js_css.jsp"%>
<link href="${basePath}/css/messages.css" media="all" rel="stylesheet" type="text/css" />
<link href="${basePath}/css/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${basePath}/css/font-awesome/css/font-awesome-ie7.css" rel="stylesheet">

<link rel="stylesheet" type="text/css" href="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/bootstrap-datepicker/css/datepicker.css" />
<link rel="stylesheet" type="text/css" href="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/bootstrap-datepicker/less/datepicker.less" />

<link rel="stylesheet" type="text/css" href="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/jquery-multi-select/css/multi-select.css" />

<link rel="stylesheet" type="text/css" href="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/clockface/css/clockface.css" />

<link href="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/jquery-file-upload/css/jquery.fileupload-ui.css" rel="stylesheet" />
<link rel="stylesheet" href="${basePath}/css/blueimp-gallery.min.css">
<link href="${basePath}/plugins/bootstrap.admin.theme/assets/css/pages/blog.css" rel="stylesheet" type="text/css"/>
<link href="${basePath}/plugins/bootstrap.admin.theme/assets/css/pages/news.css" rel="stylesheet" type="text/css"/>
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
			
			
		<c:choose>
			<c:when test="${type==null}">
			
			<div class="row  tabbable tabbable-custom">
			<ul class="nav nav-tabs" style="height:40px; ">
					<li id="tab_0_li" class="active "><a href="#tab_0" data-toggle="tab">课程信息</a></li>
					<li id="tab_1_li"><a href="#tab_1" data-toggle="tab">课程相册</a></li>
					<li id="tab_2_li"><a href="#tab_2" data-toggle="tab">课程讨论</a></li>
			</ul>
			<div class="tab-content">
						<div class="tab-pane active" id="tab_0">
						
				<div class="col-md-12">
					<form accept-charset="UTF-8"  action="${basePath}/h/c102_save.ac" class="edit_article" id="edit_article_13632" method="post">
						<s:token></s:token>
						<input name="bean.id" type="hidden" value="${bean.id}">
						<input name="bean.type" type="hidden" value="${bean.type}">
						
								<input name="type_flag" type="hidden" value="0">
							
						<div class="form-group">
							 &nbsp;  <label class="control-label">课程</label>
							 <label class="control-label col-md-12">
							 <div class="input-group">
							 <span class="input-group-addon">
								<i class="fa fa-flag"></i>
								</span>
								<select name="bean.course_id" id="course_select2_sample2"  class="form-control select2me" data-placeholder="选择课程..">
									<optgroup label="课程列表">
									<c:forEach items="${course_beans}" var="course">
										<c:set var="xxcc" value='' />
											<c:if test="${bean.course_id==course.id}">
												<c:set var="xxcc" value='selected="selected"' />
											</c:if>
										<option ${xxcc} value="${course.id}">${course.title}</option>
									</c:forEach>
									</optgroup>
								</select>
							</div>
							</label>
						</div>
						<div class="form-group">
							 &nbsp;  <label class="control-label">教练</label>
							 <label class="control-label col-md-12">
							 <div class="input-group">
								<span class="input-group-addon">
								<i class="fa fa-user"></i>
								</span>
								<select name="bean.teacher_id" id="teacher_select2_sample2"  class="form-control select2me" data-placeholder="选择教练..">
									<optgroup label="教练列表">
									<c:forEach items="${teacher_beans}" var="teacher">
										<c:set var="xxcc" value='' />
											<c:if test="${bean.teacher_id==teacher.id}">
												<c:set var="xxcc" value='selected="selected"' />
											</c:if>
										<option ${xxcc} value="${teacher.id}">${teacher.name }</option>
									</c:forEach>
									</optgroup>
								</select>
							</div>
							</label>
						</div>
						<div class="form-group">
							 &nbsp;  <label class="control-label">班级</label>
							 <label class="control-label col-md-12">
							 <div class="input-group">
								<span class="input-group-addon">
								<i class="fa fa-user"></i>
								</span>
								<select id="classes_id" id="classes_select2_sample2" onchange="loadStu()"  class="form-control select2me" data-placeholder="选择班级..">
									<optgroup label="班级列表">
									<option   value="">--请选择班级--</option>
									<c:forEach items="${classes_beans}" var="classes">
										<option value="${classes.id}">${classes.name }</option>
									</c:forEach>
									</optgroup>
								</select>
							</div>
							</label>
						</div>
						<div class="form-group">
							 &nbsp;  <label class="control-label">学员</label>
							 <label class="control-label col-md-12">
							 <div class="input-group">
							 	<span class="input-group-addon">
								<i class="fa fa-female"></i>
								</span>
								<select name="bean.sids" id="student_select2_sample2" class="form-control select2 select2me" multiple>
										<optgroup label="学员列表" >
											<c:forEach items="${student_beans}" var="student">
												<c:set var="xxcc" value='' />
												<c:forEach items="${course_student_beans}" var="the_student">
													<c:if test="${the_student.id==student.id}">
													<c:set var="xxcc" value='selected="selected"' />
													</c:if>
												</c:forEach>
												<option ${xxcc} value="${student.id}">${student.name }</option>
											</c:forEach>
										</optgroup>
									</select>
								</div>
							</label>
						</div>
						<div class="well form-inline form-group">
							<div class="col-md-1"><label>上课时间</label></div> 
							<div id="article_day" style="margin-top: -8px;" class="input-group input-medium date date-picker col-md-2" data-date-format="yyyy-mm-dd" data-date-start-date="+0d">
								<input type="text" name="bean.day" value="${bean.day}" class="form-control" readonly="">
								<span class="input-group-btn">
								<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
								</span>
							</div>
							
							<div  class="input-group bootstrap-timepicker col-md-2" id="article_begin_time_toggle" style="margin-top: -8px;" >                                       
								<input id="article_begin_time" name="bean.begin_time" value="${bean.begin_time}" type="text" class="form-control" readonly="" />
								<span class="input-group-btn">
								<button class="btn default" type="button" ><i class="fa fa-clock-o"></i></button>
								</span>
							</div>
							<div  class="input-group bootstrap-timepicker col-md-2" id="article_end_time_toggle" style="margin-top: -8px;" >                                       
								<input id="article_end_time" type="text" name="bean.end_time" value="${bean.end_time}" class="form-control" readonly="" >
								<span class="input-group-btn">
								<button class="btn default" type="button" ><i class="fa fa-clock-o"></i></button>
								</span>
							</div>
							&nbsp; 
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
				<div class="tab-pane" id="tab_1">
		  <table  class="table table-striped table-condensed" >
			<tr>
				<td align="left" >
					<form id="fileupload${bean.id}" action="" method="POST" >
						<s:token></s:token>
						<input type="hidden" name="course_syllabus_id" value="${bean.id}">
						<div class="row fileupload-buttonbar">
							<div class="col-lg-12">
								<span class="btn green fileinput-button">
								<i class="fa fa-plus"></i>
								<span>添加照片...</span>
								<input type="file" name="files[]" multiple>
								</span>
								<button type="submit" class="btn blue start">
								<i class="fa fa-upload"></i>
								<span>开始上传</span>
								</button>
								<button type="reset" class="btn yellow cancel">
								<i class="fa fa-ban"></i>
								<span>取消上传</span>
								</button>
								<%-- <button type="button" class="btn red delete">
								<i class="fa fa-trash-o"></i>
								<span>删除</span>
								</button>
								<input type="checkbox" class="toggle"> --%>
								<span class="fileupload-loading"></span>
							</div>
							 <div class="col-lg-10 fileupload-progress fade">
								<div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100">
									<div class="progress-bar progress-bar-success" style="width:0%;"></div>
								</div>
								<div class="progress-extended">&nbsp;</div>
							</div>
						</div>
						<div class="col-md-6">
						<table role="presentation" class="table table-striped table-condensed col-md-12" style="text-align: center;">
							<thead>
								<tr style="height: 0px;">
									<th style="width: 100px;">
									</th>
									<th  class="col-md-6">
									</th>
									<th  class="col-md-1">
									</th>
									<th class="col-md-2">
									</th>
								</tr>
								<c:forEach items="${bean.picBeans}" var="photoBean">
									 <tr class="template-download">
									        <td>
									            <span class="preview">
									                    <a href="${photoBean.pic_url}"  title="${photoBean.pic_title}" download="${photoBean.pic_title}" data-gallery>
									                    <img style="height:100px;"  src="${fn:replace(photoBean.pic_url, "n3", "n0")}"></a>
									            </span>
									        </td>
									        <td>
									            <p class="name">
														<input type="hidden" name="picid" value="${photoBean.id}" />
														<input type="hidden" name="picurl${photoBean.id}" value="${photoBean.pic_url}" />
														<textarea  class="upload-wrapper" name="pictit${photoBean.id}" style="height:100px;width:95%;">${photoBean.pic_title}</textarea>
									            </p>
									        </td>
									        <td>
									            <span class="size"></span>
									        </td>
									        <td>
								                <a class="btn btn-danger pdel_flag_a" onclick="delInfo(this)">
								                	<input type="hidden" class="pdel_flag" name="delflag${photoBean.id}" value="0" />
								                    <i class="glyphicon glyphicon-trash"></i>
								                    <span>删除</span>
								                </a>
								                <a class="btn blue pdel_flag_b" onclick="recoveryInfo(this)" style="display: none;">
								                    <i class="fa fa-mail-reply-all"></i>
								                    <span>恢复</span>
								                </a>
									        </td>
									   </tr>
								</c:forEach>
							</thead>
							<tbody class="files"></tbody>
						</table>
						</div>
							<script type="text/javascript">
							var uploader${bean.id} = $('#fileupload${bean.id}');
							$(function () {
								'use strict';
								uploader${bean.id}.fileupload({
									'url': '${basePath}/jQueryFileUpload',
									'method': 'POST',
									'enctype':'multipart/form-data',
									'autoUpload': false,
									'limitMultiFileUploads': 2, // 限定最多两个文件
									'limitConcurrentUploads': 1, // 限定同时上传N个文件
									'maxFileSize': 5 * 1024 * 1024,
									'maxNumberOfFiles': 40,
									'acceptFileTypes': /(\.|\/)(gif|jpe?g|png)$/i,
						        	'previewMaxWidth': 100,
						        	'previewMaxHeight': 100
								});
							});
						</script>
						<div style="clear: both;"></div>
						<div class="panel panel-success">
							<div class="panel-heading">
									<a style="margin-left:300px;"  id="c_${bean.id}" class="btn blue"  onclick="submitFrom3('fileupload${bean.id}');">相册保存</a></h3>
							</div>
						</div>
					</form>
					<div style="clear: both;"></div>
					<div class="panel panel-success">
						<div class="panel-body">
							<ul>
								<li></li>
							</ul>
						</div>
					</div>
				</td>
			</tr>
	</table>
	<!-- The blueimp Gallery widget data-filter=":even" -->
<div id="blueimp-gallery" class="blueimp-gallery blueimp-gallery-controls" >
    <div class="slides"></div>
    <h3 class="title"></h3>
    <a class="prev">‹</a>
    <a class="next">›</a>
    <a class="close">×</a>
    <a class="play-pause"></a>
    <ol class="indicator"></ol>
</div>

<!-- The template to display files available for upload -->
<script id="template-upload" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-upload fade">
        <td>
            <span class="preview"></span>
        </td>
        <td>
            <p class="name">{%=file.name%}</p>
            {% if (file.error) { %}
                <div><span class="label label-danger">错误</span> {%=file.error%}</div>
            {% } %}
        </td>
        <td>
            <p class="size">{%=o.formatFileSize(file.size)%}</p>
            {% if (!o.files.error) { %}
                <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0"><div class="progress-bar progress-bar-success" style="width:0%;"></div></div>
            {% } %}
        </td>
        <td>
            {% if (!o.files.error && !i && !o.options.autoUpload) { %}
                <button class="btn btn-primary start">
                    <i class="glyphicon glyphicon-upload"></i>
                    <span>开始</span>
                </button>
            {% } %}
            {% if (!i) { %}
                <button class="btn btn-warning cancel">
                    <i class="glyphicon glyphicon-ban-circle"></i>
                    <span>取消</span>
                </button>
            {% } %}
        </td>
    </tr>
{% } %}
	</script>
<!-- The template to display files available for download -->
<script id="template-download" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
	{% var uuid=guid(); %}
    <tr class="template-download fade">
        <td>
            <span class="preview">
                {% if (file.thumbnailUrl) { %}
                    <a href="{%=file.url%}" title="{%=file.name%}" download="{%=file.name%}" data-gallery><img style="height:100px;" src="{%=file.thumbnailUrl%}"></a>
                {% } %}
            </span>
        </td>
        <td>
            <p class="name">
                {% if (file.url) { %}
				
                    <!--<a href="{%=file.url%}" title="{%=file.name%}" download="{%=file.name%}" {%=file.thumbnailUrl?'data-gallery':''%}>{%=file.name%}</a>-->
					
					<input type="hidden" name="picid" value="{%=uuid%}" />
					<input type="hidden" name="picurl{%=uuid%}" value="{%=file.url%}" />
					<textarea   class="upload-wrapper" name="pictit{%=uuid%}" style="height:100px;width:95%;">{%=file.name%}</textarea>
                {% } else { %}
                    <span>{%=file.name%}</span>
                {% } %}
            </p>
            {% if (file.error) { %}
                <div><span class="label label-danger">错误</span> {%=file.error%}</div>
            {% } %}
        </td>
        <td>
            <span class="size">{%=o.formatFileSize(file.size)%}</span>
        </td>
        <td>
            {% if (file.deleteUrl) { %}
                <a class="btn btn-danger pdel_flag_a" onclick="delInfo(this)">
                    <input type="hidden" class="pdel_flag" name="delflag{%=uuid%}" value="0" />
					<i class="glyphicon glyphicon-trash"></i>
                    <span>删除</span>
                </a>
               <a class="btn blue pdel_flag_b" onclick="recoveryInfo(this)" style="display: none;">
					<i class="fa fa-mail-reply-all"></i>
					<span>恢复</span>
			   </a>
            {% } else { %}
                <button class="btn btn-warning cancel">
                    <i class="glyphicon glyphicon-ban-circle"></i>
                    <span>取消</span>
                </button>
            {% } %}
        </td>
    </tr>
{% } %}
</script>
				</div>
				<div class="tab-pane" id="tab_2">
					 <div class="col-md-12 blog-page">
						<h3>参与讨论</h3>
						<div id="comment_list1_div_${bean.id}">
						</div>
						<hr>
						<div class="post-comment">
							<form role="form" id="comment_form_${bean.id}" accept-charset="UTF-8"  action="${basePath}/h/c102_csave.ac"  method="post">
								<div class="form-group">
									<label class="control-label">评论信息<span class="required">*200字以内</span></label>
									<input type="hidden" name="cbean.info_id" id="cbean_info_id" value="${bean.id}"  />
									<textarea name="cbean.detail_info" id="cbean_detail_info" class="form-control" rows="4"></textarea>
								</div>
								<a class="btn blue margin-top-10" onclick="submitFrom4('comment_form_${bean.id}','comment_list1_div_${bean.id}');">提交评论信息</a>
							</form>
						</div>
					</div>
				</div>
			</div>
			<!-- END PAGE CONTENT-->
		</div>
		<!-- END PAGE -->
	</div>
	<!-- END CONTAINER -->
	</c:when>
		<c:otherwise>
			<div class="row">
			<div class="tab-content">
				<div class="col-md-12">
					<form accept-charset="UTF-8"  action="${basePath}/h/c102_save.ac" class="edit_article" id="edit_article_13632" method="post">
						<s:token></s:token>
						<input name="bean.id" type="hidden" value="${bean.id}">
						<input name="bean.type" type="hidden" value="${bean.type}">
						<input name="type_flag" type="hidden" value="2x">
						<div class="well form-inline form-group">
							<div class="col-md-1"><label>开始日期</label></div> 
							<div id="article_date1" style="margin-top: -8px;" class="input-group input-medium date date-picker col-md-2" data-date-format="yyyy-mm-dd" data-date-start-date="+0d">
								<input type="text" name="bean.date1" value="${bean.day}" class="form-control" readonly="">
								<span class="input-group-btn">
								<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
								</span>
							</div>
							<div class="col-md-1"><label>结束日期</label></div> 
							<div id="article_date2" style="margin-top: -8px;" class="input-group input-medium date date-picker col-md-2" data-date-format="yyyy-mm-dd" data-date-start-date="+0d">
								<input type="text" name="bean.date2" value="${bean.day}" class="form-control" readonly="">
								<span class="input-group-btn">
								<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
								</span>
							</div>
							&nbsp; 
						</div>
						<div class="well form-inline">
							<label>
								<input name="day_week"  value="0"	 type="checkbox" />星期日
							</label>
							<label>
								<input name="day_week"  value="1"	 type="checkbox" />星期一
							</label>
							<label>
								<input name="day_week"  value="2"	 type="checkbox" />星期二
							</label>
							<label>
								<input name="day_week"  value="3"	 type="checkbox" />星期三
							</label>
							<label>
								<input name="day_week"  value="4"	 type="checkbox" />星期四
							</label>
							<label>
								<input name="day_week"  value="5"	 type="checkbox" />星期五
							</label>
							<label>
								<input name="day_week"  value="6"	 type="checkbox" />星期六
							</label>
						</div>
						<div class="well form-inline form-group">
							<div class="col-md-1"><label>上课时间</label></div> 
							<div  class="input-group bootstrap-timepicker col-md-2" id="article_begin_time_toggle" style="margin-top: -8px;" >                                       
								<input id="article_begin_time" name="bean.begin_time" value="${bean.begin_time}" type="text" class="form-control" readonly="" />
								<span class="input-group-btn">
								<button class="btn default" type="button" ><i class="fa fa-clock-o"></i></button>
								</span>
							</div>
							<div class="col-md-1"><label>下课时间</label></div> 
							<div  class="input-group bootstrap-timepicker col-md-2" id="article_end_time_toggle" style="margin-top: -8px;" >                                       
								<input id="article_end_time" type="text" name="bean.end_time" value="${bean.end_time}" class="form-control" readonly="" >
								<span class="input-group-btn">
								<button class="btn default" type="button" ><i class="fa fa-clock-o"></i></button>
								</span>
							</div>
							&nbsp; 
						</div>
						<div class="form-group">
							 &nbsp;  <label class="control-label">课程</label>
							 <label class="control-label col-md-12">
							 <div class="input-group">
							 <span class="input-group-addon">
								<i class="fa fa-flag"></i>
								</span>
								<select name="bean.course_id" id="course_select2_sample2"  class="form-control select2me" data-placeholder="选择课程..">
									<optgroup label="课程列表">
									<c:forEach items="${course_beans}" var="course">
										<c:set var="xxcc" value='' />
											<c:if test="${bean.course_id==course.id}">
												<c:set var="xxcc" value='selected="selected"' />
											</c:if>
										<option ${xxcc} value="${course.id}">${course.title}</option>
									</c:forEach>
									</optgroup>
								</select>
							</div>
							</label>
						</div>
						<div class="form-group">
							 &nbsp;  <label class="control-label">教练</label>
							 <label class="control-label col-md-12">
							 <div class="input-group">
								<span class="input-group-addon">
								<i class="fa fa-user"></i>
								</span>
								<select name="bean.teacher_id" id="teacher_select2_sample2"  class="form-control select2me" data-placeholder="选择教练..">
									<optgroup label="教练列表">
									<c:forEach items="${teacher_beans}" var="teacher">
										<c:set var="xxcc" value='' />
											<c:if test="${bean.teacher_id==teacher.id}">
												<c:set var="xxcc" value='selected="selected"' />
											</c:if>
										<option ${xxcc} value="${teacher.id}">${teacher.name }</option>
									</c:forEach>
									</optgroup>
								</select>
							</div>
							</label>
						</div>
						<div class="form-group">
							 &nbsp;  <label class="control-label">班级</label>
							 <label class="control-label col-md-12">
							 <div class="input-group">
								<span class="input-group-addon">
								<i class="fa fa-user"></i>
								</span>
								<select id="classes_id" id="classes_select2_sample2" onchange="loadStu()"  class="form-control select2me" data-placeholder="选择班级..">
									<optgroup label="班级列表">
									<option   value="">--请选择班级--</option>
									<c:forEach items="${classes_beans}" var="classes">
										<option value="${classes.id}">${classes.name }</option>
									</c:forEach>
									</optgroup>
								</select>
							</div>
							</label>
						</div>
						<div class="form-group">
							 &nbsp;  <label class="control-label">学员</label>
							 <label class="control-label col-md-12">
							 <div class="input-group">
							 	<span class="input-group-addon">
								<i class="fa fa-female"></i>
								</span>
								<select name="bean.sids" id="student_select2_sample2" class="form-control select2 select2me" multiple>
										<optgroup label="学员列表">
											<c:forEach items="${student_beans}" var="student">
												<c:set var="xxcc" value='' />
												<c:forEach items="${course_student_beans}" var="the_student">
													<c:if test="${the_student.id==student.id}">
													<c:set var="xxcc" value='selected="selected"' />
													</c:if>
												</c:forEach>
												<option ${xxcc} value="${student.id}">${student.name }</option>
											</c:forEach>
										</optgroup>
									</select>
								</div>
							</label>
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
		</c:otherwise>
	</c:choose>
	<!-- BEGIN FOOTER -->
	<%@ include file="../include/footer.jsp"%>
	<script type="text/javascript" src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
	<script type="text/javascript" src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/bootstrap-datepicker/js/locales/bootstrap-datepicker.zh-CN.js"></script>
	<script type="text/javascript" src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/select2/select2.min.js"></script>
	<script type="text/javascript" src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/jquery-multi-select/js/jquery.multi-select.js"></script>
	<script type="text/javascript" src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/jquery-multi-select/js/jquery.quicksearch.js"></script>   
	
	<script type="text/javascript" src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/clockface/js/clockface.js"></script>
	
	<script src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/jquery-file-upload/js/vendor/jquery.ui.widget.js"></script>
	<!-- The Templates plugin is included to render the upload/download listings -->
	<script src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/jquery-file-upload/js/vendor/tmpl.min.js"></script>
	<!-- The Load Image plugin is included for the preview images and image resizing functionality -->
	<script src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/jquery-file-upload/js/vendor/load-image.min.js"></script>
	<!-- The Canvas to Blob plugin is included for image resizing functionality -->
	<script src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/jquery-file-upload/js/vendor/canvas-to-blob.min.js"></script>
	<!-- The Iframe Transport is required for browsers without support for XHR file uploads -->
	<script src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/jquery-file-upload/js/jquery.iframe-transport.js"></script>
	<!-- The basic File Upload plugin -->
	<script src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/jquery-file-upload/js/jquery.fileupload.js"></script>
	<!-- The File Upload processing plugin -->
	<script src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/jquery-file-upload/js/jquery.fileupload-process.js"></script>
	<!-- The File Upload image preview & resize plugin -->
	<script src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/jquery-file-upload/js/jquery.fileupload-image.js"></script>
	<!-- The File Upload validation plugin -->
	<script src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/jquery-file-upload/js/jquery.fileupload-validate.js"></script>
	<!-- The File Upload user interface plugin -->
	<script src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/jquery-file-upload/js/jquery.fileupload-ui.js"></script>
	<script src="${basePath}/js/jquery.blueimp-gallery.min.js"></script>
	<!-- END FOOTER -->
	<script type="text/javascript" src="${basePath}/js/jquery.form.js"></script>
</body>
<!-- END BODY -->
</html>
<script type="text/javascript">
jQuery(document).ready(function() {
	$('#article_date1').datepicker();
	$('#article_date2').datepicker();
	$('#article_day').datepicker();
	//
	 $('#article_begin_time').clockface({
         format: 'HH:mm',
         trigger: 'manual'
     });
	  $('#article_begin_time_toggle').click(function (e) {
          e.stopPropagation();
          $('#article_begin_time').clockface('toggle');
      });
	//
	$('#article_end_time').clockface({
        format: 'HH:mm',
        trigger: 'manual'
    });
	$('#article_end_time_toggle').click(function (e) {
        e.stopPropagation();
        $('#article_end_time').clockface('toggle');
    });
	//
	$('#student_select2_sample2').select2({
        placeholder: "点击选择学员",
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
	});
	
	loadUrlPageComment(0,'h/c102_','clist1','comment_list1_div_${bean.id}','&cid=${bean.id}');
});
//提交from
function submitFrom3(from_id) {
	jQuery("#"+from_id).attr('action','${basePath}/h/c102_savePic.ac');
	jQuery("#"+from_id).attr('method','POST');
	jQuery("#"+from_id).attr('accept-charset','UTF-8');
	jQuery("#"+from_id).removeAttr('enctype');
	//提交
	jQuery("#"+from_id).ajaxSubmit(function(data) {
		if (data == "1") {
			//$('.xx2'+from_id).attr('readonly','readonly');
			//$('.xx2'+from_id).attr('disabled','disabled');
			//jQuery("#b_"+from_id).remove();
			alert('提交成功!');
		} else {
			alert(data);
		}
	});
}
//删除信息
function delInfo(obj){
	$(obj).find('.pdel_flag').val('1');
	$(obj).hide();
	$(obj).parent().find('.pdel_flag_b').show();
}

//恢复信息
function recoveryInfo(obj){
	$(obj).parent().find('.pdel_flag').val('0');
	$(obj).hide();
	$(obj).parent().find('.pdel_flag_a').show();
}
//--加载评论信息--
function loadUrlPageComment(offset,url,event,divId,obj) {
	//var load = "<a class='loading' >信息加载中...</a>";
	//jQuery("#" + divId).html(load);
	jQuery.ajax({
		url : '${basePath}/' + url + event+'.ac?offset='+offset+'&did='+divId+ obj + '&time=' + new Date(),
		success : function(req) {
			jQuery("#"+divId).html(req);
		},
		error : function() {
			jQuery("#"+divId).html('信息加载失败!');
		}
	});
}
//提交from comment
function submitFrom4(from_id,divid) {
	
	var info_obj=$('#'+from_id).find('#cbean_detail_info');
	var info_val=$.trim(info_obj.val());
	if(info_val.length<200){
		if(info_val==''||info_val.length==0){
			alert('评论信息为空!');
			return false;
		}
		//提交
		jQuery("#"+from_id).ajaxSubmit(function(data) {
			if (data == "1") {
				var d=new Date(); 
				var formatdate=d.getFullYear()+'-'+(d.getMonth()+1)+"-"+d.getDate()+" "+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds()+"";
				$('#'+divid).prepend('<div class="media"><a href="#" class="pull-left"></a><div class="media-body"><h4 class="media-heading alert-warning"><label style="color: #777;font-size: 12px;">${uid}</label><span>'+formatdate+'</span></h4><p class="alert alert-success alert-dismissable">'+info_val+'</p></div></div>');
				info_obj.val('');
				alert('评论成功!');
			} else {
				alert(data);
			}
		});
	}else{
		alert('评论字数超过限制,200字以内!');
	}
}
//--加载班级学员信息--
function loadStu() {
	var cid=$('#classes_id').val();
	if(cid==null || cid==''){
		return false;
	}
	jQuery.ajax({
		url : '${basePath}/w/getStu.ac?cid='+cid+ '&time=' + new Date(),
		success : function(req) {
			try{
				var json = eval("("+req+")");
				if(json){
					$("#student_select2_sample2 option").each(function(){
						for(var i=0;i<json.length;i++){
							if($(this).val() == json[i].id){
								var lt_flag=false;
								var xx=$('#s2id_student_select2_sample2').find('.select2-search-choice').each(function(){
									if(json[i].name==$(this).find('div').html()){
										lt_flag=true;
									}
								});
								if(!lt_flag){
									var li='<li class="select2-search-choice">    <div>'+json[i].name+'</div>    <a href="#" onclick="return false;" class="select2-search-choice-close" tabindex="-1"></a></li>';
									$('#s2id_student_select2_sample2').find('.select2-choices').find('.select2-search-field').before(li);
								}
								$(this).attr('selected','selected');
							}
						}
					});
				}
			}catch(e){}
		},
		error : function() {
			//jQuery("#"+divId).html('信息加载失败!');
		}
	});
}
</script>
