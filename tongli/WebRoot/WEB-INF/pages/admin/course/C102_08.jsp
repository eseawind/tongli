<%--
/*
 * 系统管理_课程管理_课程表(冬夏令营)_编辑 (页面)
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-06-10  wuxiaogang   程序・发布
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
						<li><a href="${basePath}/h/c102_init.ac">课程管理(冬夏令营)</a> <i class="fa fa-angle-right"></i></li>
						<li>编辑</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			<!-- END PAGE HEADER-->
			<!-- BEGIN PAGE CONTENT-->
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
									<c:forEach items="${course_beans}" var="course_bean" varStatus="i">
										<optgroup label="${course_bean.subject_name}">
											<c:forEach items="${course_bean.beans}" var="course_bean2"  varStatus="n">
												<c:set var="xxcc" value='' />
												<c:if test="${bean.course_id==course.id}">
													<c:set var="xxcc" value='selected="selected"' />
												</c:if>
												<option ${xxcc} value="${course_bean2.id}">${course_bean2.title}</option>
											</c:forEach>
										</optgroup>
									</c:forEach>
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
	
	<script type="text/javascript" src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/clockface/js/clockface.js"></script>
	<!-- END FOOTER -->
	<script type="text/javascript" src="${basePath}/js/jquery.form.js"></script>
</body>
<!-- END BODY -->
</html>
<script type="text/javascript">
jQuery(document).ready(function() {
	$('#article_date1').datepicker();
	$('#article_date2').datepicker();
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
});
</script>
