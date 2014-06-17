<%--
/*
 * 资讯-焦点图
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
<div class="focusBox" >
	<ul class="pic">
		<c:forEach items="${beans}" var="bean">
		<li>
		<a href="${basePath}/c003_init.ac?id=${bean.id}&tid=${bean.type_id}&pid=3f2b286347174e728d39169c212fe56b" >
		<img src="${bean.pic_url}"  onerror="this.src='${basePath}/images/error/404.jpg';this.onerror='';"  />
		</a>
		</li>
		</c:forEach>
	</ul>
	<a class="prev" href="javascript:void(0)"></a> <a class="next"
		href="javascript:void(0)"></a>
	<ul class="hd">
	<c:forEach items="${beans}" var="bean">
		<li></li>
	</c:forEach>
	</ul>
</div>
<script type="text/javascript">
	/*鼠标移过，左右按钮显示*/
	jQuery(".focusBox").hover(
			function() {
				jQuery(this).find(".prev,.next").stop(true, true).fadeTo(
						"show", 0.2)
			}, function() {
				jQuery(this).find(".prev,.next").fadeOut()
			});
	/*SuperSlide图片切换*/
	jQuery(".focusBox").slide({
		mainCell : ".pic",
		effect : "left",
		autoPlay : true,
		delayTime : 600,
		trigger : "click"
	});
</script>