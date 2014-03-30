<%--
/*
 * 系统管理_资讯栏目管理_编辑 (页面)
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-03-25  wuxiaogang   程序・发布
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
<%@include file="../include/admin_title.jsp" %>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<meta name="MobileOptimized" content="320">
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<%@ include file="../include/public_js_css.jsp"%>
<link href="${basePath}/css/messages.css" media="all" rel="stylesheet"
	type="text/css" />

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
				$('#sys,#sys_sub_menu_l1_sub_menu_l0').addClass('active');
				$('#sys_arrow,#sys_sub_l1_arrow').addClass('open');
				$('#sys_sub_menu,#sys_sub_l1_sub_menu').show();
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
					<h3 class="page-name">
						资讯栏目管理 <small><span class="help-inline">这里编辑各种栏目的介绍</span></small>
					</h3>
					<ul class="page-breadcrumb breadcrumb">
						<li><i class="fa fa-home"></i> <a
							href="${basePath }/home_init.ac">Home</a> <i
							class="fa fa-angle-right"></i></li>
						<li><a href="#">资讯管理</a> <i class="fa fa-angle-right"></i></li>
						<li><a href="${basePath}/h/s002_init.ac">栏目管理</a> <i class="fa fa-angle-right"></i></li>
						<li>编辑</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			<!-- END PAGE HEADER-->
			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
				<div class="col-md-12">
					<form accept-charset="UTF-8"  action="${basePath}/h/s002_save.ac" class="edit_article" id="edit_article_13632" method="post">
						<s:token></s:token>
						<input name="bean.id" type="hidden" value="${bean.id}">
						<div class="form-group">
							<label class="control-label">父栏目</label>
						</div>
						<div  class="form-group">
							<select class="form-control" name="bean.parent_id">
								<option value="">请选择</option>
								<c:forEach items="${beans}" var="bean1">
									<option value="${bean1.id }" 
									<c:if test="${bean.parent_id==bean1.id }">
									selected="selected"
									</c:if>
									>${bean1.name}</option>
									<c:forEach items="${bean1.beans}" var="bean2">
										<option value="${bean2.id }" 
										<c:if test="${bean.parent_id==bean2.id }">
										selected="selected"
										</c:if>
										>|--${bean2.name}</option>
											<c:forEach items="${bean2.beans}" var="bean3">
											<option value="${bean3.id }" 
											<c:if test="${bean.parent_id==bean3.id }">
											selected="selected"
											</c:if>
											>|--|--${bean3.name}</option>
										</c:forEach>
									</c:forEach>
								</c:forEach>
							</select>
						</div>
						<div class="form-group">
							<label for="article_sort_num">显示顺序</label> <input class="form-control"
								id="article_sort_num" name="bean.sort_num" size="30" type="number" value="${bean.sort_num}">
						</div>
						<div class="form-group">
							<label for="article_name">栏目名称</label> <input class="form-control"
								id="article_name" name="bean.name" size="30" type="text" value="${bean.name}">
						</div>
						<div class="form-group">
							<label for="article_description">栏目内容</label>
							<div class="qeditor_border">
								<textarea name="bean.detail_info" style="height: 300px;width: 100%;" id="article_description" >
									${bean.detail_info}
								</textarea>
							</div>
						</div>
						<div class="form-actions">
							<input class="btn btn-primary" name="commit" type="submit"
								value="保存"> | <a
								href="${basePath}/h/s002_init.ac">返回</a>
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
	});
});
</script>
