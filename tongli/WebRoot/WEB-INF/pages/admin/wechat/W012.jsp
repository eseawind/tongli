<%--
/*
 * 微信服务_关注者(粉丝)信息展示 (初始化页面)
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-03-20  wuxiaogang   程序・发布
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
				$('#wechat,#w012_init').addClass('active');
				$('#wechat_arrow').addClass('open');
				$('#wechat_sub_menu').show();
				//
				loadUrlPage(0, 'h/w012_', 'list', 'a_div_info_list_');
			});
			// 分页查询
			function loadUrlPage(offset, url, event, divId) {
				if (url == null || url == "" || divId == null || divId == "") {
					return;
				}
				//var load = "<a class='loading' >信息努力加载中...</a>";
				//jQuery("#" + divId).html(load);
				jQuery.ajax({
					url : '${basePath}/' + url + event+'.ac?offset='
							+ offset + '&time=' + new Date().getTime(),
					success : function(req) {
						jQuery("#" + divId).html(req);
					},
					error : function() {
						jQuery("#" + divId).html("信息加载发生错误");
					}
				});
			}
			function downUser(obj) {
				myAlert('粉丝信息拉取中!');
				$(obj).attr("disabled",true);
				jQuery.ajax({
					url : '${basePath}/h/w012_down.ac',
					success : function(req) {
						if(req==1){
							jQuery("#msg").html('<div class="alert alert-success"><button class="close" data-dismiss="alert"></button><strong>Success!</strong> 粉丝拉取成功!</div>');
						}else{
							jQuery("#msg").html('<div class="alert alert-danger"><button class="close" data-dismiss="alert"></button><strong>Error!</strong> '+req+'!</div>');
						}
						$(obj).attr("disabled",false);
					},
					error : function() {
						jQuery("#msg").html('<div class="alert alert-danger"><button class="close" data-dismiss="alert"></button><strong>Error!</strong> 粉丝拉取失败!</div>');
						$(obj).attr("disabled",false);
					}
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
							href="${basePath }/home_init.ac">主页</a> <i
							class="fa fa-angle-right"></i></li>
						<li><a href="#">微信服务</a> <i class="fa fa-angle-right"></i></li>
						<li>粉丝列表</li>
						<li class="btn-group">
							<button type="button" class="btn blue dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-delay="1000" data-close-others="true">
							<span>Actions</span><i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right" role="menu">
								<li onclick="downUser(this)"><button  class="btn">拉取粉丝</button></li>
							</ul>
						</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			<!-- END PAGE HEADER-->
			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
				<div id="msg"></div>
				<div class="matter btn btn-toolbar">
					
				</div>
				<div id="a_div_info_list_" class="col-md-12">
					
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
