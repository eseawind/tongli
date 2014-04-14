<%--
/*
 * 会员登录
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
				<div class="content" style="min-height: 360px;">
					<div class="course_info">
						<c:forEach items="${beans}" var="bean" varStatus="i">
							<div class="item_li">${i.index+1}. ${bean.title}</div>
							<div class="item_con">
								<p>时间:${bean.day}${bean.begin_time}${bean.end_time}</p>
								<p>&nbsp;</p>
								<p>地点:${bean.addres}</p>
								<p>&nbsp;</p>
								<p>
									<strong>徐庶：</strong>老师信息老师信息老师信息老师信息老师信息
								</p>
							</div>
						</c:forEach>
						<customtag:pagingext func="loadUrlPage" params="'m201_','init'" />
					</div>
				</div>
			</div>
			<div class="body fl" style="width: 197px;">
				<div class="title">
					<a href="javascript:void(0);" class="ico_aboutus">学员列表</a>
				</div>
				<div class="content">
					<ul>
						<li>课程表</li>
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
		$(".item_li").click(function() {
			if ($(this).hasClass("on")) {
				$(this).removeClass("on");
			} else {
				$(this).addClass("on");
			}
			$(this).next(".item_con").slideToggle();
		});
	});
	function loadUrlPage(offset, url, event) {
		location.href='${basePath}/' + url + event+'.ac?offset=' + offset;
	}
</script>