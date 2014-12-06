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
<!-- <div class="decoration22"></div>
<div class="content-footer">
	<p class="copyright-content">童励儿童俱乐部.<br> &nbsp;&nbsp;&nbsp;版权所有</p>
    <a href="#" class="go-up-footer"></a>
    <div class="clear"></div>
</div> -->
<script type="text/javascript" src="${basePath}/plugins/slideby/scripts/jqueryui.js"></script>
<script type="text/javascript" src="${basePath}/plugins/slideby/scripts/owl.carousel.min.js"></script>
<script type="text/javascript" src="${basePath}/plugins/slideby/scripts/jquery.swipebox.js"></script>
<script type="text/javascript" src="${basePath}/plugins/slideby/scripts/colorbox.js"></script>
<script type="text/javascript" src="${basePath}/plugins/slideby/scripts/snap.js"></script>
<script type="text/javascript" src="${basePath}/plugins/slideby/scripts/contact.js"></script>
<script type="text/javascript" src="${basePath}/plugins/slideby/scripts/custom.js"></script>
<script type="text/javascript" src="${basePath}/plugins/slideby/scripts/framework.js"></script>
<script type="text/javascript" src="${basePath}/plugins/slideby/scripts/framework.launcher.js"></script>

<script type="text/javascript" src="${basePath}/plugins/noty/js/jquery.noty.js"></script>
<script type="text/javascript" src="${basePath}/plugins/noty/js/layouts/top.js"></script>
<script type="text/javascript" src="${basePath}/plugins/noty/js/themes/default.js"></script>

<script type="text/javascript">
	function reSetH(){
		var sidebar_h=$('#sidebar').height();
		var content_h=$('#content').height();
		if(content_h<sidebar_h){
			$('#content').height(sidebar_h)
		}
	}
		jQuery(document).ready(function() {
			//-新闻资讯
			loadMenuSub('3f2b286347174e728d39169c212fe56b');
			//-关于我们
			loadMenuSub('966a13c753f34faa927510c610b5e0b6');
			//-童励课程
			loadMenuSub('6690aceda07a405a9428e6e02ba2d416');
			//-东夏令营
			loadMenuSub('26f1017792024a358c73639b08e74393');
			
			reSetH();//重设高
		});
		//------------子菜单-----------------------------------------
		function loadMenuSub(pid) {
			jQuery.ajax({
				url : '${basePath}/w/menu.ac?pid='+pid,
				success : function(req) {
					try{
						$("."+pid).next('.nav-item-submenu').html(req);
					}catch(e){
						
					}
				},
				error : function() {
					//--异常--
				}
			});
		}
</script>
<script type="text/javascript" src="${basePath}/js/global.js"></script>
