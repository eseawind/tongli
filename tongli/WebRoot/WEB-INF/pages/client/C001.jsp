<%--
/*
 * 首页
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-04-11  wuxiaogang        程序・发布
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

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="include/title_meta.jsp"%>
<%@ include file="include/public_js_css.jsp"%>
<script type="text/javascript" src="${basePath}/js/bxCarousel.js"></script>
</head>

<body class="page-header-fixed">
<!-- BEGIN HEADER -->
<%@ include file="include/header.jsp"%>
<!-- END   HEADER -->
<%@ include file="include/slider.jsp"%>

<div class="main">
<div class="c10"></div>
<div class="w">

<div class="body fl" style=" width:400px;">
<div class="title"><a href="${basePath}/c002_init.ac?tid=3f2b286347174e728d39169c212fe56b&pid=3f2b286347174e728d39169c212fe56b" class="ico_news">新闻中心</a><em><a href="${basePath}/c002_init.ac?tid=3f2b286347174e728d39169c212fe56b&pid=3f2b286347174e728d39169c212fe56b"></a></em></div>
<div class="content home_news" style="height:170px;">
<ul>
<c:forEach items="${maps}" var="map">
	<c:if test="${map.key=='3f2b286347174e728d39169c212fe56b'}">
		<c:forEach items="${map.value}" var="bean">
			<li><a href="${basePath}/c003_init.ac?id=${bean.id}&tid=${bean.type_id}&pid=3f2b286347174e728d39169c212fe56b">• ${bean.title}</a>
			<em>
			${fn:substring(bean.last_updated,5,10)}
			</em>
			</li>
		</c:forEach>
	</c:if>
</c:forEach>
</ul>
</div>
</div>

<div class="body fr" style=" width:567px;">
<div class="title"><a href="${basePath}/c202_init.ac" class="ico_order">预约参观</a><em><a href="${basePath}/c202_init.ac"></a></em></div>
<div class="content" style="height:170px;">
<a href="${basePath}/c202_init.ac"><img src="images/img1.jpg" width="547" height="170"/></a>
</div>
</div>

<div class="c10"></div>
<div class="body fl" style=" width:400px;">
<div class="title"><a href="${basePath}/c002_init.ac?tid=690e3d1f73224bb7bd766b4648041798&pid=3f2b286347174e728d39169c212fe56b" class="ico_pics">图片展示</a><em><a href="${basePath}/c002_init.ac?tid=690e3d1f73224bb7bd766b4648041798&pid=3f2b286347174e728d39169c212fe56b"></a></em></div>
<div class="content img_slide" style="height:120px;">
	<ul id="img_slide">
	<c:forEach items="${maps}" var="map">
		<c:if test="${map.key=='690e3d1f73224bb7bd766b4648041798'}">
			<c:forEach items="${map.value}" var="bean">
			<li>
				<div class="img_box">
					<a  href="${basePath}/c003_init.ac?id=${bean.id}&tid=${bean.type_id}&pid=3f2b286347174e728d39169c212fe56b">
						<img src="${bean.pic_url}" width="148" height="90" title="${bean.title}" />
					</a>
				</div>
				<div class="txt_box">
			        <a href="${basePath}/c003_init.ac?id=${bean.id}&tid=${bean.type_id}&pid=3f2b286347174e728d39169c212fe56b"  title="2011创意网站后台登陆界面设计">
			        ${bean.title}
			        </a>
		        </div>
			</li>
	        </c:forEach>
		</c:if>
	</c:forEach>
	</ul>
</div>
</div>

<div class="body fr" style=" width:567px;">
<div class="title"><a href="${basePath}/c002_init.ac?tid=d76b4e2ba4b84e5db471988377f8ba52&pid=3f2b286347174e728d39169c212fe56b" class="ico_video">视频展示</a><em><a href="${basePath}/c002_init.ac?tid=d76b4e2ba4b84e5db471988377f8ba52&pid=3f2b286347174e728d39169c212fe56b"></a></em></div>
<div class="content video_slide" style="height:120px;">
<ul id="video_slide">
        <c:forEach items="${maps}" var="map">
			<c:if test="${map.key=='d76b4e2ba4b84e5db471988377f8ba52'}">
				<c:forEach items="${map.value}" var="bean">
				<li>
					<div class="img_box">
						<a  href="${basePath}/c003_init.ac?id=${bean.id}&tid=${bean.type_id}&pid=3f2b286347174e728d39169c212fe56b">
							<img src="${bean.pic_url}" width="148" height="90" title="${bean.title}" />
						</a>
					</div>
					<div class="txt_box">
				        <a href="${basePath}/c003_init.ac?id=${bean.id}&tid=${bean.type_id}&pid=3f2b286347174e728d39169c212fe56b"  title="${bean.title}">
				        ${bean.title}
				        </a>
			        </div>
				</li>
		        </c:forEach>
			</c:if>
		</c:forEach>
</ul>
</div>
</div>

</div>
<div class="c10"></div>
</div>
<!-- BEGIN FOOTER -->
<%@ include file="include/footer.jsp"%>
<!-- END FOOTER -->
</body>
</html>