<%--
/*
 * 首页
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-03-30  wuxiaogang        程序・发布
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
<div class="title"><a href="${basePath}/c002_init.ac?tid=523c1d77d4de4390af8a0924e69e3084" class="ico_news">新闻中心</a><em><a href="${basePath}/c002_init.ac?tid=523c1d77d4de4390af8a0924e69e3084"></a></em></div>
<div class="content home_news" style="height:170px;">
<ul>
<c:forEach items="${beans}" var="bean">
<li><a href="${basePath}/c003_init.ac?id=${bean.id}&tid=${bean.type_id}&pid=${bean.parent_id}">• ${bean.title}</a>
<em>
${fn:substring(bean.last_updated,5,10)}
</em>
</li>
</c:forEach>
</ul>
</div>
</div>

<div class="body fr" style=" width:567px;">
<div class="title"><a href="#" class="ico_order">预约参观</a><em><a href="news.html"></a></em></div>
<div class="content" style="height:170px;">
<a href="#"><img src="images/img1.jpg" width="547" height="170"/></a>
</div>
</div>

<div class="c10"></div>
<div class="body fl" style=" width:400px;">
<div class="title"><a href="#" class="ico_pics">图片展示</a><em><a href="news.html"></a></em></div>
<div class="content img_slide" style="height:120px;">
<ul id="img_slide">
        <li> 
        <div class="img_box">
        <a href="/design/30/8/199.html" target="_blank"><img src="images/img1.jpg" width="148" height="90" title="2011创意网站后台登陆界面设计" /></a>
        </div>
        <div class="txt_box">
        <a href="#" target="_blank" title="2011创意网站后台登陆界面设计">2011创意网站后台登陆界面设计</a>
        </div>
        </li>
        
         <li> 
        <div class="img_box">
        <a href="/design/30/8/199.html" target="_blank"><img src="images/img2.jpg" width="148" height="90" title="2011创意网站后台登陆界面设计" /></a>
        </div>
        <div class="txt_box">
        <a href="#" target="_blank" title="2011创意网站后台登陆界面设计">2011创意网站后台登陆界面设计</a>
        </div>
        </li>
        
         <li> 
        <div class="img_box">
        <a href="/design/30/8/199.html" target="_blank"><img src="images/img3.jpg" width="148" height="90" title="2011创意网站后台登陆界面设计" /></a>
        </div>
        <div class="txt_box">
        <a href="#" target="_blank" title="2011创意网站后台登陆界面设计">2011创意网站后台登陆界面设计</a>
        </div>
        </li>
      </ul>
</div>
</div>

<div class="body fr" style=" width:567px;">
<div class="title"><a href="#" class="ico_video">视频展示</a><em><a href="news.html"></a></em></div>
<div class="content video_slide" style="height:120px;">
<ul id="video_slide">
        <li> 
        <div class="img_box">
        <a href="/design/30/8/199.html" target="_blank"><img src="images/img1.jpg" width="148" height="90" title="2011创意网站后台登陆界面设计" /></a>
        </div>
        <div class="txt_box">
        <a href="#" target="_blank" title="2011创意网站后台登陆界面设计">2011创意网站后台登陆界面设计</a>
        </div>
        </li>
        
         <li> 
        <div class="img_box">
        <a href="/design/30/8/199.html" target="_blank"><img src="images/img2.jpg" width="148" height="90" title="2011创意网站后台登陆界面设计" /></a>
        </div>
        <div class="txt_box">
        <a href="#" target="_blank" title="2011创意网站后台登陆界面设计">2011创意网站后台登陆界面设计</a>
        </div>
        </li>
        
         <li> 
        <div class="img_box">
        <a href="/design/30/8/199.html" target="_blank"><img src="images/img3.jpg" width="148" height="90" title="2011创意网站后台登陆界面设计" /></a>
        </div>
        <div class="txt_box">
        <a href="#" target="_blank" title="2011创意网站后台登陆界面设计">2011创意网站后台登陆界面设计</a>
        </div>
        </li>
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