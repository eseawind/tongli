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
<div class="footer">
	<div class="w">Copyright@2014&nbsp;&nbsp;&nbsp;&nbsp;沪ICP备案：110055455号&nbsp;&nbsp;&nbsp;&nbsp;联系电话：021-20556847&nbsp;&nbsp;&nbsp;&nbsp;邮箱：xsds@126.com</div>
</div>
<script type="text/javascript">
		$(function() {
			//jquery图片滚动
			$('#img_slide').bxCarousel({
				display_num : 2,
				move : 1,
				margin : 0
			});

			$('#video_slide').bxCarousel({
				display_num : 3,
				move : 1,
				margin : 0
			});
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
			//nav menu
			$("#navmenu ul li:has(ul)").hover(function() {
				$(this).children("a").css({color:"#3e7900"});
				if($(this).find("li").length > 0){
				$(this).children("ul").stop(true, true).slideDown(200)} 
			},function() {
				$(this).children("a").css({color:"#333"});
				$(this).children("ul").stop(true, true).slideUp(200)
				});
		});
	</script>

