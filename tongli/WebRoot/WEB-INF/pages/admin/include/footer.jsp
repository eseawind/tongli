<%--
/*
 * 底部共通部分画面
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-03-03  wuxiaogang        程序・发布
 * -------- ----------- ------------ ------------------------------------------
 * Copyright 2014 童励 System. - All Rights Reserved.
 *
 */
--%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" session="false"%>
<div style="clear: both;"></div>
<div class="footer">
	<div class="footer-inner">
		Copyright 2014 童励儿童俱乐部 System. - All Rights Reserved.
	</div>
	<div class="footer-tools">
		<span class="go-top">
		<i class="fa fa-angle-up"></i>
		</span>
	</div>
</div>
<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
<!-- BEGIN CORE PLUGINS -->   
<!--[if lt IE 9]>
<script src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/excanvas.min.js"></script>
<script src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/respond.min.js"></script>  
<![endif]--> 

<script src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>  
<script src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/bootstrap-hover-dropdown/twitter-bootstrap-hover-dropdown.min.js" type="text/javascript" ></script>
<script src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
<script src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/jquery.blockui.min.js" type="text/javascript"></script>  
<script src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/jquery.cookie.min.js" type="text/javascript"></script>
<script src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/uniform/jquery.uniform.min.js" type="text/javascript" ></script>

<script type="text/javascript" src="${basePath}/plugins/editor/kindeditor.js" charset="utf-8" ></script>
<script type="text/javascript" src="${basePath}/plugins/editor/lang/zh_CN.js" charset="utf-8"></script>
<script type="text/javascript" src="${basePath}/plugins/editor/plugins/code/prettify.js" charset="utf-8"></script>

<script type="text/javascript" src="${basePath}/plugins/noty/js/jquery.noty.js"></script>
<script type="text/javascript" src="${basePath}/plugins/noty/js/layouts/top.js"></script>
<script type="text/javascript" src="${basePath}/plugins/noty/js/themes/default.js"></script>

<script type="text/javascript" src="${basePath}/js/global.js"></script>

<!-- END CORE PLUGINS -->
<script src="${basePath}/plugins/bootstrap.admin.theme/assets/scripts/app.js"></script>      
<script>
		getBlockUI();
		jQuery(document).ready(function() {    
		   App.init();
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
<!-- END JAVASCRIPTS -->
