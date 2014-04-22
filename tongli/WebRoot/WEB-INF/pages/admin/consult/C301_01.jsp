<%--
/*
 * 客服信息--我的咨询信息 (页面)
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-03-18  wuxiaogang   程序・发布
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
<%@include file="include/admin_title.jsp" %>
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<%@ include file="include/public_js_css.jsp"%>
<link href="${basePath}/css/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${basePath}/css/font-awesome/css/font-awesome-ie7.css" rel="stylesheet">
<script type="text/javascript" src="${basePath}/plugins/q+/jsLib/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="${basePath}/plugins/q+/jsLib/myLib.js"></script>
<link rel="stylesheet" type="text/css" href="${basePath}/plugins/q+/themes/default/css/desktop.css">
<link rel="stylesheet" type="text/css" href="${basePath}/plugins/q+/jsLib/jquery-ui-1.8.18.custom.css">

<script type="text/javascript" src="${basePath}/plugins/q+/jsLib/jquery-ui-1.8.18.custom.min.js"></script>
<script type="text/javascript" src="${basePath}/plugins/q+/jsLib/jquery.winResize.js"></script>
<script type="text/javascript" src="${basePath}/plugins/q+/jsLib/desktop.js"></script>
<script type="text/javascript" src="${basePath}/plugins/q+/jsLib/jquery-smartMenu/js/mini/jquery-smartMenu-min.js"></script>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-header-fixed">
	<!-- BEGIN HEADER -->
	<%@ include file="include/header.jsp"%>
	<!-- END HEADER -->
	<div id="wallpapers"></div>
		<div id="desktopInnerPanel" ></div>
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
	<div class="page-container">
		<!-- BEGIN SIDEBAR -->
		<%@ include file="include/leftMenu.jsp"%>
		<script type="text/javascript">
			jQuery(document).ready(function() {
				$('#consult,#consult_sub_menu_l2').addClass('active');
				$('#consult_arrow').addClass('open');
				$('#consult_sub_menu').show();
				
				//myLib.progressBar();
			});
			$(window).load(function() {
				// myLib.stopProgress();
			//  //存储桌面布局元素的jquery对象
				myLib.desktop.winWH();
				myLib.desktop.desktopPanel();
				//初始化任务栏
				myLib.desktop.taskBar.init();
			});
			//digon
			function newWins(title,src,id){
				myLib.desktop.win.newWin({
					WindowTitle : title,
					iframSrc : '${basePath}/h/c302_init.ac?cid='+id,
					WindowsId : id,
					WindowAnimation : 'ok',
					WindowWidth :600,
					WindowHeight : 400
				});
			}
		</script>
		<!-- END SIDEBAR -->
		<!-- BEGIN PAGE -->
		<div class="page-content">
			<!-- BEGIN STYLE CUSTOMIZER -->
			<%@ include file="include/style_customizer.jsp"%>
			<!-- END BEGIN STYLE CUSTOMIZER -->
			<!-- BEGIN PAGE HEADER-->
			<div class="row">
				<div class="col-md-12">
					<!-- BEGIN PAGE TITLE & BREADCRUMB-->
					<h3 class="page-title">
						客服咨询 <small><span class="help-inline">我的咨询信息</span></small>
					</h3>
					<ul class="page-breadcrumb breadcrumb">
						<li><i class="fa fa-home"></i> <a
							href="${basePath }/c301_init.ac">Home</a> <i
							class="fa fa-angle-right"></i></li>
						<li><a href="#">客服咨询</a> <i class="fa fa-angle-right"></i></li>
						<li>个人</li>
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
										<strong>Success!</strong> 操作成功。
									</div>
								</c:otherwise>
							</c:choose>
						</c:if>
							<div class="well form-inline">
								<label>${csbean.name}</label>
								<label>队列
									${PAGEROW_OBJECT_KEY.recordCount}
								</label>
								<label>未处理
									--
								</label>
							</div>
							<table class="table table-condensed table-striped">
								<tbody>
									<tr>
										<th class="col-md-3">发起时间</th>
										<th class="col-md-3">咨询人</th>
										<th class="col-md-3">状态</th>
										<th class="col-md-3"></th>
									</tr>
									<c:forEach items="${beans}" var="bean">
									<tr>
										<td>${bean.date_created}</td>
										<td>${bean.user_name}</td>
										<td>
											<c:if test="${bean.consult_status=='0'}">待处理</c:if>
											<c:if test="${bean.consult_status=='1'}">处理中</c:if>
											<c:if test="${bean.consult_status=='2'}">已处理 </c:if>
											<c:if test="${bean.consult_status=='3'}">已完结</c:if>
										</td>
										<td>
										<c:if test="${bean.consult_status!='3' && bean.consult_status!='2'}">
											<a href="javascript:;" 
											onclick="newWins('${bean.user_name}','${basePath}/h/c301_access.ac?id=${bean.id}','${bean.id}');"
											class="btn edit green">接入</a> 
										</c:if>
										</td>
									</tr>
									</c:forEach>
								</tbody>
							</table>
							<customtag:pagingext func="loadUrlPage" params="'h/c301_','list1'" />
						</div>
					</div>
				</div>
			</div>
			<!-- END PAGE CONTENT-->
			<div id="desktopPanel">
				<div id="taskBarWrap">
					<div id="taskBar">
						<div id="leftBtn"><a href="javascript:void(0);" class="upBtn"></a></div>
						<div id="rightBtn"><a href="javascript:void(0);" class="downBtn"></a></div>
						<div id="task_lb_wrap"><div id="task_lb"></div></div>
					</div>
				</div>
			</div>
		</div>
		<!-- END PAGE -->
	</div>
	<!-- END CONTAINER -->
	<!-- BEGIN FOOTER -->
	<%@ include file="include/footer.jsp"%>
	<!-- END FOOTER -->
</body>
<!-- END BODY -->
</html>
<script type="text/javascript">
function loadUrlPage(offset, url, event) {
	location.href='${basePath}/' + url + event+'.ac?offset=' + offset;
}
</script>
