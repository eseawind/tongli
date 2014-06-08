<%--
/*
 * 教师-个人中心
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-04-17  wuxiaogang        程序・发布
 * -------- ----------- ------------ ------------------------------------------
 * Copyright 2014 wechat System. - All Rights Reserved.
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
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../include/title_meta.jsp"%>
<%@ include file="../include/public_js_css.jsp"%>
<script type="text/javascript" src="${basePath}/js/bxCarousel.js"></script>
<link href="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
<link href="${basePath}/plugins/bootstrap.admin.theme/assets/css/style-metronic.css" rel="stylesheet" type="text/css"/>
<link href="${basePath}/plugins/bootstrap.admin.theme/assets/css/style.css" rel="stylesheet" type="text/css"/>
<link href="${basePath}/plugins/bootstrap.admin.theme/assets/css/style-responsive.css" rel="stylesheet" type="text/css"/>
<%-- <link href="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/fancybox/source/jquery.fancybox.css" rel="stylesheet" /> --%>
<link href="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/jquery-file-upload/css/jquery.fileupload-ui.css" rel="stylesheet" />

<link rel="stylesheet" href="${basePath}/css/blueimp-gallery.min.css">
</head>

<body class="page-header-fixed">
	<!-- BEGIN HEADER -->
	<%@ include file="../include/header.jsp"%>
	<!-- END   HEADER -->
	<%@ include file="../include/slider.jsp"%>
	<div class="main pr">
		<div class="c10"></div>
		<div class="w">
			<div class="body fr" style="width: 770px;">
				<div class="title">&nbsp; 我的课程表</div>
				<div class="content " style="min-height: 500px;">
					<ul class="nav nav-tabs" style="height:40px; ">
							<li id="tab_0_li" class="active "><a href="#tab_0" data-toggle="tab">完结课程(<font class="_struts_0" color="red">0</font>)</a></li>
							<li id="tab_1_li"><a href="#tab_1" data-toggle="tab">未完课程(<font class="_struts_1" color="red">0</font>)</a></li>
					</ul>
					<div class="tab-content">
						<div class="tab-pane active" id="tab_0">
							<!-- BEGIN FORM-->
							<div class="course_info" id="course_info1">
								
							</div>
							<!-- END FORM--> 
						</div>
						<div class="tab-pane" id="tab_1">
							<!-- BEGIN FORM-->
							<div class="course_info" id="course_info2">
								
							</div>
							<!-- END FORM--> 
						</div>
					</div>
				</div>
			</div>
			<c:set var="student_id" value="" />
			<div class="body fl" style="width: 197px;">
				<div class="title">
					<a href="javascript:void(0);" class="ico_recommend">个人中心</a>
				</div>
				<div class="content">
					<ul>
							<li>
								<a href="javascript:;" onclick="loadInfo();">课程表</a>
							</li>
					</ul>
				</div>
			</div>
			<div class="body fl mt10" style="width: 197px;">
				<div class="title">
					<a href="${basePath}/c202_init.ac" class="ico_recommend">预约参观</a>
				</div>
				<div class="content" style="height: 150px;">
					<a href="${basePath}/c202_init.ac"><img src="images/img4.jpg" width="177" height="150" /></a>
				</div>
			</div>

			<div class="body fl mt10" style="width: 197px;">
				<div class="content" style="height: 177px;">
					<img src="images/erweima.jpg" width="177" height="177" />
				</div>
			</div>
			<div class="c10"></div>
		</div>

	</div>
	<!-- BEGIN FOOTER -->
	<%@ include file="../include/footer.jsp"%>
	<!-- END FOOTER -->

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
	
</body>
</html>
<script>
	$(function() {
		loadUrlPage(0,'t001_','list1','course_info1');
		loadUrlPage(0,'t001_','list2','course_info2');
	});
	function loadInfo(sid) {
		loginCheck();
		loadUrlPage(0,'t001_','list1','course_info1');
		loadUrlPage(0,'t001_','list2','course_info2');
	}
	function loadUrlPage(offset,url,event,divId,obj) {
		loginCheck();
		jQuery.ajax({
			url : '${basePath}/' + url + event+'.ac?offset='+offset+ obj + '&time=' + new Date(),
			success : function(req) {
				jQuery("#"+divId).html(req);
			},
			error : function() {
				jQuery("#"+divId).html('信息加载失败!');
			}
		});
	}
	function loginCheck() {
		jQuery.ajax({
			async : false,
			url : '${basePath}/tcheck.ac?time=' + new Date().getTime(),
			success : function(req) {
				if(req!='1'){
					alert('未登录或登录超时!请重新登录!');
					location.href='${basePath}/t001_login.ac';
				}
			},
			error : function() {
				alert("页面发生错误");
			}
		});
	}
	// 提交from comment
	function submitFrom4(from_id,divid) {
		//登录认证
		loginCheck()
		
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
	//--加载评论信息--
	function loadUrlPageComment(offset,url,event,divId,obj) {
		loginCheck();
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
</script>