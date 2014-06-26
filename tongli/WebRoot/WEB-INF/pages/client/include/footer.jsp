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
<div style="clear: both"></div>
<!-- <div class="footer">
	<div class="w">Copyright@2014&nbsp;&nbsp;&nbsp;&nbsp;沪ICP备案：110055455号&nbsp;&nbsp;&nbsp;&nbsp;联系电话：021-20556847&nbsp;&nbsp;&nbsp;&nbsp;邮箱：xsds@126.com</div>
</div> -->
<div class="footer">
<div class="w980 footer_line">Copyright@2014&nbsp;&nbsp;&nbsp;&nbsp;沪ICP备案：110055455号&nbsp;&nbsp;&nbsp;&nbsp;联系电话：021-20556847&nbsp;&nbsp;&nbsp;&nbsp;邮箱：xsds@126.com</div>
</div>
<script type="text/javascript">
		jQuery(document).ready(function() {
			//-----------
			var mw = $(".mbox").width() + 10;
			var ml = $(".mbox").length;
			$(".themes").width(mw * ml);
			$(".t_menu li").mouseover(function() {
				var index = $(this).index();
				$(".themes").animate({
					left : -mw * index,
					opacity : 1
				}, 500);
			})
			/* //nav menu
			$("#navmenu ul li").hover(function() {
				$(this).children("a").css({color:"#3e7900"});
				$(this).children(".subMenu").stop(true, true).slideDown(200);
				$("#mask").slideDown(60);
				return false;
			},function() {
				$(this).children("a").css({color:"#333"});
				$(this).children(".subMenu").stop(true, true).slideUp(200);
				$("#mask").slideUp(10);
				 return false;
				}); */
			//----菜单选中
			try{
				var url=location.href;
				var array=url.split('pid=');
				if(array!=null && array.length>1){
					$('.m').removeClass('on');
					$('.'+array[1]).addClass('on');
				}
			}catch(e){}
		});
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
