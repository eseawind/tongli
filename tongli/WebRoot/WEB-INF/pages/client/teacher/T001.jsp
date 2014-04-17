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
				<div class="content" style="min-height: 500px;">
					<div class="course_info" id="course_info">
						
					</div>
				</div>
			</div>
			<c:set var="student_id" value="" />
			<div class="body fl" style="width: 197px;">
				<div class="title">
					<a href="javascript:void(0);" class="ico_aboutus">学员列表</a>
				</div>
				<div class="content">
					<ul>
						<c:forEach items="${student_beans}" var="student" varStatus="i">
							<c:if test="${i.index==0}">
								<c:set var="student_id" value="${student.id}" />
							</c:if>
							<li>
								<a href="javascript:;" onclick="loadUrlPage(0,'t001_','list1','course_info','${student.id}')">${student.name}</a>
							</li>
						</c:forEach>
					</ul>
				</div>
			</div>
			<div class="body fl mt10" style="width: 197px;">
				<div class="title">
					<a href="#" class="ico_recommend">预约参观</a>
				</div>
				<div class="content" style="height: 150px;">
					<img src="images/img4.jpg" width="177" height="150" />
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
	$(function() {
		//loadUrlPage(0,'t001_','list1','course_info','${student_id}');
	});
	function loadUrlPage(offset,url,event,divId,sid) {
		var load = "<a class='loading' >信息加载中...</a>";
		jQuery("#" + divId).html(load);
		jQuery.ajax({
			url : '/' + url + event+'.ac?offset='+offset+"&sid="+ sid + '&time=' + new Date(),
			success : function(req) {
				jQuery("#"+divId).html(req);
			},
			error : function() {
				jQuery("#"+divId).html('信息加载失败!');
			}
		});
	}
</script>