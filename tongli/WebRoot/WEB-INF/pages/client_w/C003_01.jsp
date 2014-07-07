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
function loadSwipe(){
var aaa = document.getElementById('mySwipe');
window.mySwipe = Swipe(aaa, {
startSlide: 0,
speed: 400,
auto: 5000,//设置自动切换时间，单位毫秒
continuous: true,//无限循环的图片切换效果
disableScroll: false, //阻止由于触摸而滚动屏幕
stopPropagation: false,//停止滑动事件
callback: function(pos) {
$(".dian li").removeClass('on')
$(".dian li").eq(pos).addClass('on');		
  },
transitionEnd: function(auto, aaa) {}//回调函数，切换结束调用该函数。
});
}
</script> 