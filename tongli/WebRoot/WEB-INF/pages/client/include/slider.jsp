<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" session="false"%><div class="navBar">
	<div class="slider">
		<div class="focusBox">
			<ul class="pic">
				<li><a href="#" target="_blank"><img src="images/img1.jpg" /></a></li>
				<li><a href="#" target="_blank"><img src="images/img2.jpg" /></a></li>
				<li><a href="#" target="_blank"><img src="images/img3.jpg" /></a></li>
			</ul>
			<a class="prev" href="javascript:void(0)"></a> <a class="next"
				href="javascript:void(0)"></a>
			<ul class="hd">
				<li></li>
				<li></li>
				<li></li>
			</ul>
		</div>
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
		});
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