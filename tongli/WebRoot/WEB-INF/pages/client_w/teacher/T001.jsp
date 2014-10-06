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
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../include/title_meta.jsp"%>
<%@ include file="../include/public_js_css.jsp"%>

<link href="${basePath}/plugins/bootstrap.admin.theme/assets/css/style.css" rel="stylesheet" type="text/css"/>
<link href="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<script src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<link href="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/jquery-file-upload/css/jquery.fileupload-ui.css" rel="stylesheet" />
<link href="${basePath}/css/w_style.css"		 rel="stylesheet" type="text/css">
<style type="text/css">
.page-header {
    padding-bottom: 9px;
    margin: 0px 0px 20px;
    border-bottom: 1px solid #EEE;
}
.tab-content {
    display: block;
    overflow: visible;
	position: relative;
	margin: 0px;
	padding: 0px;
}
.page-content {
    margin-top: 0px;
    min-height: 760px;
}
.content {
    margin:0px;
}
.item_con {
 margin:0px;
}
.next-quote {
    z-index: 99999;
    background-image: url('${basePath}/plugins/slideby/images/ui/next1.png');
    background-repeat: no-repeat;
    width: 10px;
    height: 10px;
    background-size: 10px 10px;
    position: absolute;
    right: 0px;
    margin-top:35px;;
}
.prev-quote {
    z-index: 99999;
    background-image: url('${basePath}/plugins/slideby/images/ui/prev1.png');
    background-repeat: no-repeat;
    width: 10px;
    height: 10px;
    background-size: 10px 10px;
    position: absolute;
    left: 0px;
    margin-top:35px;;
}
table tr td{
    text-align: left;
}
textarea{border: 1px solid #ddd;}
.tabbable-custom {
    margin-bottom: 15px;
    padding: 0px;
    overflow: visible;
}
div{
    overflow: visible;
}
.container{
width:auto;
margin-left: 5px;
margin-right: 5px;
}
p{
   border-radius:15px;
}
</style>
</head>
<body>
	<div class="all-elements">
		<%@ include file="../include/header.jsp"%>
		<div id="content" class="page-content">
			<div class="page-header">
				<a href="#" class="deploy-sidebar"></a>
				<p class="bread-crumb">
					我的课程表
				</p>
				<a href="javascript:history.go(-1);" class="deploy-contact left-list"></a>
			</div>
			<div class="content">
				<div class="web_enroll_info tabbable tabbable-custom">
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
			<c:set var="student_id" value="" />
			<%@ include file="../include/footer.jsp"%>
		</div>
	</div>
	</div>

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
			url : '${basePath}/w/' + url + event+'.ac?offset='+offset+ obj + '&time=' + new Date(),
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
			url : '${basePath}/w/tcheck.ac?time=' + new Date().getTime(),
			success : function(req) {
				if(req!='1'){
					alert('未登录或登录超时!请重新登录!');
					location.href='${basePath}/w/t001_login.ac';
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
					$('#'+divid).prepend('<em class="speach-right-title"><span>'+formatdate+' </span>${uid}:</em><p class="speach-right blue-bubble">'+info_val+'</p><div class="clear"></div>');
					info_obj.val('');
					//alert('评论成功!');
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
			url : '${basePath}/w/' + url + event+'.ac?offset='+offset+'&did='+divId+ obj + '&time=' + new Date(),
			success : function(req) {
				jQuery("#"+divId).html(req);
			},
			error : function() {
				jQuery("#"+divId).html('信息加载失败!');
			}
		});
	}
	// 提交from
	function submitFrom2(from_id) {
		//登录认证
		loginCheck();
		//提交
		jQuery("#"+from_id).ajaxSubmit(function(data) {
			if (data == "1") {
				$('.xx2'+from_id).attr('readonly','readonly');
				$('.xx2'+from_id).attr('disabled','disabled');
				jQuery("#b_"+from_id).remove();
				alert('提交成功!');
			} else {
				alert(data);
			}
		});
	}
</script>