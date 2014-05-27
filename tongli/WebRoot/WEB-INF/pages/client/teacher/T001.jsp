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

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../include/title_meta.jsp"%>
<%@ include file="../include/public_js_css.jsp"%>
<script type="text/javascript" src="${basePath}/js/bxCarousel.js"></script>
<link href="${basePath}/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<script src="${basePath}/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>

<link href="${basePath}/plugins/editor/themes/default/default.css" rel="stylesheet" type="text/css"/>
<link href="${basePath}/plugins/editor/plugins/code/prettify.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${basePath}/plugins/editor/kindeditor.js" charset="utf-8" ></script>
<script type="text/javascript" src="${basePath}/plugins/editor/lang/zh_CN.js" charset="utf-8"></script>
<script type="text/javascript" src="${basePath}/plugins/editor/plugins/code/prettify.js" charset="utf-8"></script>
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
						<c:forEach items="${student_beans}" var="student" varStatus="i">
							<c:if test="${i.index==0}">
								<c:set var="student_id" value="${student.id}" />
							</c:if>
							<li>
								<a href="javascript:;" onclick="loadInfo('${student.id}');">${student.name}</a>
							</li>
						</c:forEach>
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
</body>
</html>
<script>
	var editor;
	$(function() {
		loadUrlPage(0,'t001_','list1','course_info1','${student_id}','&status=0');
		loadUrlPage(0,'t001_','list2','course_info2','${student_id}','&status=1');
		
		KindEditor.ready(function(K) {
			editor=K;
		});
	});
	function loadInfo(sid) {
		loginCheck();
		loadUrlPage(0,'t001_','list1','course_info1',sid,'&status=0');
		loadUrlPage(0,'t001_','list2','course_info2',sid,'&status=1');
	}
	function loadUrlPage(offset,url,event,divId,sid,obj) {
		loginCheck();
		var load = "<a class='loading' >信息加载中...</a>";
		jQuery("#" + divId).html(load);
		jQuery.ajax({
			url : '/' + url + event+'.ac?offset='+offset+"&sid="+ sid + obj + '&time=' + new Date(),
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
</script>