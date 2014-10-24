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
<div class="w980 footer_line">Copyright@2014&nbsp;&nbsp;&nbsp;&nbsp;沪ICP备11046863号&nbsp;&nbsp;&nbsp;&nbsp;联系电话：400-005-0806&nbsp;&nbsp;&nbsp;&nbsp;</div>
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
			//----菜单选中-----
			try{
				var url=(location.href).split('#')[0];
				var array=url.split('pid=');
				if(array!=null && array.length>1){
					$('.m').removeClass('on');
					$('.'+array[1]).addClass('on');
				}
			}catch(e){}
			//-关于我们
			loadMenuSub('966a13c753f34faa927510c610b5e0b6');
			//-童励课程
			loadMenuSub('6690aceda07a405a9428e6e02ba2d416');
			//-东夏令营
			loadMenuSub('26f1017792024a358c73639b08e74393');
			 //nav menu
			$("#navmenu > li").hover(function() {
				$(this).children(".subMenu").stop(true, true).slideDown(200);
				return false;
			},function() {
				$(this).children(".subMenu").stop(true, true).slideUp(200);
				 return false;
			});
		});
		//------------------------------------------------------------
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
		//------------子菜单-----------------------------------------
		function loadMenuSub(pid) {
			jQuery.ajax({
				url : '${basePath}/menu.ac?pid='+pid,
				success : function(req) {
					try{
						$("."+pid).find('.subMenu').html(req);
					}catch(e){
						
					}
				},
				error : function() {
					//--异常--
				}
			});
		}
</script>
