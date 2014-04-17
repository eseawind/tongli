<%--
/*
 * 教师-课程列表
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
<c:forEach items="${beans}" var="bean" varStatus="i">
	<div class="item_li">${i.index+1}. ${bean.title}</div>
	<div class="item_con">
		<p>时间:${bean.day}&nbsp;${bean.begin_time}&nbsp;${bean.end_time}</p>
		<p>&nbsp;</p>
		<p>地点:${bean.addres}</p>
		<p>&nbsp;</p>
		<p>
			<strong>${bean.teacher_name}</strong>老师信息老师信息老师信息老师信息老师信息
		</p>
	</div>
</c:forEach>
<customtag:pagingext func="loadUrlPage" params="'t001_','list1','course_info'" />
<script>
	$(".item_li").click(function() {
		if ($(this).hasClass("on")) {
			$(this).removeClass("on");
		} else {
			$(this).addClass("on");
		}
		$(this).next(".item_con").slideToggle();
	});
</script>