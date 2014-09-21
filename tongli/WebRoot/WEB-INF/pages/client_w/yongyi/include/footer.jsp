<%--
/*
 * 底部共通部分画面
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-03-30  wuxiaogang        程序・发布
 * -------- ----------- ------------ ------------------------------------------
 * Copyright 2014 童励 System. - All Rights Reserved.
 *
 */
--%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" session="false"%>
<div class="i_bottom">
		<p>泳易俱乐部 版权所有</p>
</div>

<script type="text/javascript" src="${basePath}/js/swipe.js"></script>
<script type="text/javascript">
!window.jQuery && document.write('<script src=http://static.weiwubao.com/asset/lib/js/jquery/jquery-1.10.0.min.js><\/script>');
</script> 
<script>
		 getBlockUI();
		jQuery(document).ready(function() {    
		   $.unblockUI();
		});
		$(document).ajaxStart(function () {
			$.blockUI({
		        message: '<img src="'+basePath+'/plugins/bootstrap.admin.theme/assets/img/ajax-loading.gif" />',
	           css: {
	               top: '50%',
	               border: 'none',
	               padding: '2px',
	               backgroundColor: 'none'
	           },
	           overlayCSS: {
	               backgroundColor: '#000',
	               opacity: 0.05,
	               cursor: 'wait'
	           }
		    });
		});
		$(document).ajaxStop(function () {
		    // 直接调用，无延时
		    $.unblockUI();
		});
		function getBlockUI(){
			 $.blockUI({
		        message: '<img src="'+basePath+'/plugins/bootstrap.admin.theme/assets/img/ajax-loading.gif" />',
	           css: {
	               top: '50%',
	               border: 'none',
	               padding: '2px',
	               backgroundColor: 'none'
	           },
	           overlayCSS: {
	               backgroundColor: '#000',
	               opacity: 0.05,
	               cursor: 'wait'
	           }
		    });
			setTimeout($.unblockUI, 500);
		}
</script>
<script type="text/javascript" src="${basePath}/js/wechat.api.js"></script>
<!-- <script type="text/javascript">
var wechat = new wechatAPI();
wechat.shareData = {
shareImageUrl: "http://static.weiwubao.com/upload/800066/image/20140515/100x100_201405151034051.jpg",
shareLink: "http://www.weiwubao.com/web/800066/",
shareTitle: "童厉儿童俱乐部",
shareContent: "关注童厉儿童俱乐部,给孩子一个快乐的童年！",
};
/*****分享初始化*****/
wechat.shareInit();
/*****隐藏工具条*****/
wechat.hideBottomTool();
//wechat.hideTopTool();
</script> -->
<script type="text/javascript" src="${basePath}/js/global.js"></script>
