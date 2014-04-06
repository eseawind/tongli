<%--
/*
 * 系统管理_课程管理_课程表 (页面)
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-04-01  wuxiaogang   程序・发布
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
<%@ taglib prefix="customtag" uri="/custom-tags"%>
<!DOCTYPE html>
<html lang="zh-CN" class="no-js">
<head>
<meta charset="utf-8" />
<%@include file="../include/admin_title.jsp" %>
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<%@ include file="../include/public_js_css.jsp"%>
<link href="${basePath}/css/messages.css" media="all" rel="stylesheet" type="text/css" />
<link href="${basePath}/css/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${basePath}/css/font-awesome/css/font-awesome-ie7.css" rel="stylesheet">
<link href="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/fullcalendar/fullcalendar/fullcalendar.css" rel="stylesheet" />

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
						课程表 <small><span class="help-inline">课程表信息</span></small>
					</h3>
					<ul class="page-breadcrumb breadcrumb">
						<li><i class="fa fa-home"></i> <a
							href="${basePath }/home_init.ac">Home</a> <i
							class="fa fa-angle-right"></i></li>
						<li><a href="${basePath }/h/c101_init.ac">课程管理</a> <i class="fa fa-angle-right"></i></li>
						<li>课程表</a> </li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			<!-- END PAGE HEADER-->
			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
				<div class="col-md-12">
					<div class="row-fluid">
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
										<strong>Success!</strong> 课程表操作成功。
									</div>
								</c:otherwise>
							</c:choose>
						</c:if>
							<div class="portlet box blue calendar">
								<div class="portlet-title">
									<div class="caption"><i class="fa fa-reorder"></i>课程表管理</div>
								</div>
								<div class="portlet-body light-grey">
									<div class="row">
										<div class="col-md-3 col-sm-12">
											<!-- BEGIN DRAGGABLE EVENTS PORTLET-->    
											<h3 class="event-form-title">Draggable Events</h3>
											<div id="external-events">
												<form class="inline-form">
													<input type="text" value="" class="form-control" placeholder="Event Title..." id="event_title" /><br />
													<a href="javascript:;" id="event_add" class="btn green">Add Event</a>
												</form>
												<hr />
												<div id="event_box"></div>
												<label for="drop-remove">
												<input type="checkbox" id="drop-remove" />remove after drop                         
												</label>
												<hr class="visible-xs" />
											</div>
											<!-- END DRAGGABLE EVENTS PORTLET-->            
										</div>
										<div class="col-md-9 col-sm-9">
											<div id="calendar" class="has-toolbar"></div>
										</div>
									</div>
									<!-- END CALENDAR PORTLET-->
								</div>
							</div>
						</div>
					</div>
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
	<!-- <script src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/jquery-migrate-1.2.1.min.js" type="text/javascript"></script> -->
	<script src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/jquery-ui/jquery-ui-1.10.3.custom.min.js" type="text/javascript"></script>      
	<script src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/fullcalendar/fullcalendar/fullcalendar.min.js"></script>
	<script src="${basePath}/plugins/bootstrap.admin.theme/assets/scripts/calendar.js"></script> 
</body>
<!-- END BODY -->
</html>
<script type="text/javascript">
function loadUrlPage(offset, url, event) {
	location.href='${basePath}/' + url + event+'.ac?offset=' + offset;
}
jQuery(document).ready(function() {       
	   Calendar.init();
});
</script>