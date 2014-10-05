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

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="include/title_meta.jsp"%>
<%@ include file="include/public_js_css.jsp"%>
</head>

<body class="blueBg">
<div class="w640">
	 <!--欢迎页-->
		<div class="welcome" style="opacity: 0; display: none;">
			<img class="img1" src="${basePath}/css/images/logo.jpg" style="opacity: 1;">
			<img class="img2" src="${basePath}/css/images/guohui2.png" style="margin-top: -20px;"> 
			<img class="img3" src="${basePath}/css/images/guohui3.png" style="display: inline;">
		</div>
		<!--slider -->
		<div id="mySwipe" class="swipe" style="visibility: visible; ">
		    <div id="image" class="swipe_wrap" style="width:${(fn:length(beans))*640}px;max-height:320px; ">
		      <!--图片--> 
		         <c:forEach items="${beans}" var="bean" varStatus="i">
					<div class="item"
						style="width: 640px; left: ${0-(i.index*640)}px; -webkit-transition: 0ms; -webkit-transform: translate(${(i.index*640)}px, 0px) translateZ(0px); " data-index="${i.index}">
						<img src="${bean.pic_url}"  onerror="this.src='${basePath}/images/error/404.jpg';this.onerror='';"  />
						<span class="text">${bean.title}</span>
					</div>
				</c:forEach>   
		    </div>
		    <!--页码-->
		    <ul class="dian">
		    	<c:forEach begin="1" end="${fn:length(beans)}" varStatus="i" step="1">
		    		<c:choose>
		    			<c:when test="${i.index==(fn:length(beans))}">
		    				 <li class="on"></li>
		    			</c:when>
		    			<c:otherwise>
		    				 <li class=""></li>
		    			</c:otherwise>
		    		</c:choose>
		        </c:forEach>
		    </ul>
		    <!--页码end--> 
		</div>
		<script>
			// pure JS
			function loadSwipe() {
				var aaa = document.getElementById('mySwipe');
				window.mySwipe = Swipe(aaa, {
					startSlide : 0,
					speed : 400,
					auto : 5000,//设置自动切换时间，单位毫秒
					continuous : true,//无限循环的图片切换效果
					disableScroll : false, //阻止由于触摸而滚动屏幕
					stopPropagation : false,//停止滑动事件
					callback : function(pos) {
						$(".dian li").removeClass('on')
						$(".dian li").eq(pos).addClass('on');
					},
					transitionEnd : function(auto, aaa) {
					}//回调函数，切换结束调用该函数。
				});
			}
		</script>
		<!--slider -->
		<div class="i_menu">
				<a class="i_menu_item br_t"
				href="${basePath}/w/index.ac?pid=sy"><i
				class="icon3"></i>首页</a> 
				<a class="i_menu_item br_t"
				href="${basePath}/w/c002_init.ac?tid=966a13c753f34faa927510c610b5e0b6&pid=966a13c753f34faa927510c610b5e0b6"><i
				class="icon1"></i>关于我们</a>
				 <a class="i_menu_item br_t"
				href="${basePath}/w/c002_init.ac?tid=6690aceda07a405a9428e6e02ba2d416&pid=6690aceda07a405a9428e6e02ba2d416"><i
				class="icon2"></i>童励课程</a> 
				<a class="i_menu_item"
				href="${basePath}/w/c002_init.ac?tid=26f1017792024a358c73639b08e74393&pid=26f1017792024a358c73639b08e74393"><i
				class="icon4"></i>冬夏令营</a> 
				<a class="i_menu_item"
				href="${basePath}/w/c202_init.ac?pid=yycg"><i
				class="icon5"></i>预约参观</a> 
				<a class="i_menu_item"
				href="${basePath}/w/c203_init.ac?pid=zxbm"><i
				class="icon6"></i>在线报名</a> 
				<a class="i_menu_item"
				href="${basePath}/w/m201_init.ac"><i
				class="icon7"></i>会员中心</a> 
				<a class="i_menu_item"
				href="${basePath}/w/t001_init.ac"><i
				class="icon8"></i>教师中心</a> 
				<a class="i_menu_item br_b"
				href="http://www.weiwubao.com/web/800066/?sid=1438#mp.weixin.qq.com"><i
				class="icon9"></i>警民互动</a>
			<div class="clear"></div>
		</div>
	<!-- BEGIN FOOTER -->
	<%@ include file="include/footer.jsp"%>
	<!-- END FOOTER -->
</div>
	<%-- <div class="main">
		<div class="c10"></div>
		<div class="w">
			<div class="more_info">
				<div class="w980">
					<a
						href="${basePath}/w/c002_init.ac?tid=3f2b286347174e728d39169c212fe56b&pid=3f2b286347174e728d39169c212fe56b">更多资讯</a>
				</div>
			</div>
			<ul class="card">
				<c:forEach items="${beans}" var="bean" varStatus="i">
					<li><a
						href="${basePath}/w/c003_init.ac?id=${bean.id}&tid=${bean.type_id}&pid=3f2b286347174e728d39169c212fe56b">
							<img
							onerror="this.src='${basePath}/images/error/404.jpg';this.onerror='';"
							src="${bean.pic_url}" width="300" height="170"
							title="${bean.title}" />
							<h5>${bean.title}</h5>
							<p>${bean.brief_info}..</p>
							<div class="tips">
								<span class="r">${bean.date_created}</span>
							</div>
					</a></li>
				</c:forEach>
			</ul>

		</div>
	</div> --%>
	<script type="text/javascript">
	
		// with jQuery
		//window.mySwipe = $('#mySwipe').Swipe().data('Swipe');	
		if (window.WeixinJSBridgeReady) {
			document.addEventListener('WeixinJSBridgeReady',
					function onBridgeReady() {
						setTimeout(function() {
							$(".img1").animate({
								opacity : '1'
							}, 1000);
							$(".img2").animate({
								'margin-top' : '-20px'
							}, 700);
							setTimeout(function() {
								$('.img3').show()
							}, 1000);
							setTimeout(function() {
								$('.welcome').animate({
									'opacity' : 0
								}, 1000);
								loadSwipe();
							}, 2000);
							setTimeout(function() {
								$('.welcome').hide();
							}, 4000);
						}, 100);

					});
		} else {
			setTimeout(function() {
				$(".img1").animate({
					opacity : '1'
				}, 1000);
				$(".img2").animate({
					'margin-top' : '-20px'
				}, 700);
				setTimeout(function() {
					$('.img3').show()
				}, 1000);
				setTimeout(function() {
					$('.welcome').animate({
						'opacity' : 0
					}, 1000);
					loadSwipe();
				}, 2000);
				setTimeout(function() {
					$('.welcome').hide();
				}, 4000);
			}, 100);
		}
	</script>
</body>
</html>