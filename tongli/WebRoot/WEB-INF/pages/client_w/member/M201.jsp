<%--
/*
 * 会员-个人中心
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-04-14  wuxiaogang        程序・发布
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
<link href="${basePath}/plugins/bootstrap.admin.theme/assets/css/style-metronic.css" rel="stylesheet" type="text/css"/>
<link href="${basePath}/plugins/bootstrap.admin.theme/assets/css/style.css" rel="stylesheet" type="text/css"/>
<link href="${basePath}/plugins/bootstrap.admin.theme/assets/css/style-responsive.css" rel="stylesheet" type="text/css"/>
<%-- <link href="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/dropzone/css/dropzone.css" rel="stylesheet"/> --%>
<link href="${basePath}/plugins/bootstrap.admin.theme/assets/css/pages/blog.css" rel="stylesheet" type="text/css"/>
<link href="${basePath}/plugins/bootstrap.admin.theme/assets/css/pages/news.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" href="${basePath}/css/blueimp-gallery.min.css">
</head>

<body class="blueBg ">
	<!-- BEGIN HEADER -->
	<div class="w640">
	<!-- END   HEADER -->
	<div class="main pr">
		<div class="c10"></div>
		<div class="w">
			<div class="body" >
				<div class="title">&nbsp; 我的课程表</div>
				<div class="content  tabbable tabbable-custom" style="min-height: 700px;">
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
			<div class="body" style="width: 197px;">
				<div class="title">
					<a href="javascript:void(0);" class="ico_aboutus">学员列表</a>
				</div>
				<div class="content">
					<ul id="s_">
						<c:forEach items="${student_beans}" var="student" varStatus="i">
							<c:if test="${i.index==0}">
								<c:set var="student_id" value="${student.id}" />
							</c:if>
							<li id="s_${student.id}">
								<a href="javascript:;" onclick="loadInfo('${student.id}');">${student.name}</a>
							</li>
						</c:forEach>
					</ul>
				</div>
			</div>
			<div class="c10"></div>
		</div>

	</div>
	<!-- BEGIN FOOTER -->
	<%@ include file="../include/footer.jsp"%>
	</div>
		<script src="${basePath}/js/jquery.blueimp-gallery.min.js"></script>
	<!-- END FOOTER -->
</body>
</html>
<script>
	$(function() {
		loadUrlPage(0,'m201_','list1','course_info1','${student_id}');
		loadUrlPage(0,'m201_','list2','course_info2','${student_id}');
	});
	function loadInfo(sid) {
		loginCheck();
		loadUrlPage(0,'m201_','list1','course_info1',sid);
		loadUrlPage(0,'m201_','list2','course_info2',sid);
	}
	function loadUrlPage(offset,url,event,divId,sid) {
		try{
			$('#s_').find('li').removeClass('on');
			$('#s_'+sid).addClass('on');
		}catch(e){}
		loginCheck();
		//var load = "<a class='loading' >信息加载中...</a>";
		//jQuery("#" + divId).html(load);
		jQuery.ajax({
			url : '${basePath}/' + url + event+'.ac?offset='+offset+"&sid="+ sid  + '&time=' + new Date(),
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
			url : '${basePath}/w/mcheck.ac?time=' + new Date().getTime(),
			success : function(req) {
				if(req!='1'){
					alert('未登录或登录超时!请重新登录!');
					location.href='${basePath}/w/m201_login.ac';
				}
			},
			error : function() {
				alert("页面发生错误");
			}
		});
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