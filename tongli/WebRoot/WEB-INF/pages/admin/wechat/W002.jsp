<%--
/*
 * 微信服务_自动回复_图文消息 (页面)
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-03-04  wuxiaogang   程序・发布
 * -------- ----------- ------------ ------------------------------------------
 * Copyright 2014 童励 System. - All Rights Reserved.
 *
 */
--%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" session="false"%>
<%@page import="cn.com.softvan.common.CommonConstant"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="customtag" uri="/custom-tags"%>

<!DOCTYPE html>
<!--[if IE 8]> <html lang="zh-CN" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="zh-CN" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="zh-CN" class="no-js">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8" />
<%@include file="../include/admin_title.jsp" %>
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<%@ include file="../include/public_js_css.jsp"%>

<link href="${basePath}/css/messages.css" media="all" rel="stylesheet" type="text/css" />
<!-- <style type="text/css">
.message-main{
column-width:300px;
-moz-column-width:300px; /* Firefox */
-webkit-column-width:300px; /* Safari 和 Chrome */
}
</style> -->
<style type="text/css">
	
	.message-size{position: absolute;;}
</style>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-header-fixed">
	<!-- BEGIN HEADER -->
	<%@ include file="../include/header.jsp"%>
	<!-- END HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
	<div class="page-container">
		<!-- BEGIN SIDEBAR -->
		<%@ include file="../include/leftMenu.jsp"%>
		<script type="text/javascript">
			jQuery(document).ready(function() {
				$('#wechat,#wechat_sub_menu_li_sub_menu_1').addClass('active');
				$('#wechat_arrow,#wechat_sub_menu_li_arrow').addClass('open');
				$('#wechat_sub_menu,#wechat_sub_menu_li_sub_menu').show();
			});
		</script>
		<!-- END SIDEBAR -->
		<!-- BEGIN PAGE -->
		<div class="page-content">
			<!-- BEGIN STYLE CUSTOMIZER -->
			<%@ include file="../include/style_customizer.jsp"%>
			<!-- END BEGIN STYLE CUSTOMIZER -->
			<!-- BEGIN PAGE HEADER-->
			<div class="row">
				<div class="col-md-12">
					<!-- BEGIN PAGE TITLE & BREADCRUMB-->
					<h3 class="page-title">
						管理微信公众帐号 <small><span class="help-inline">如果没有公众帐号，<a
								href="http://mp.weixin.qq.com" target="_blank">点这里注册</a></span></small>
					</h3>
					<ul class="page-breadcrumb breadcrumb">
						<li><i class="fa fa-home"></i> <a
							href="${basePath }/home_init.ac">Home</a> <i
							class="fa fa-angle-right"></i></li>
						<li><a href="#">微信服务</a> <i class="fa fa-angle-right"></i></li>
						<li><a href="#">自动回复</a> <i class="fa fa-angle-right"></i></li>
						<li>图文消息</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			<!-- END PAGE HEADER-->
			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
				<div class="col-md-12">
					<c:if test="${msg!=null}">
							<c:choose>
								<c:when test="${msg!='1'}">
									<div class="alert alert-danger">
										<button class="close" data-dismiss="alert"></button>
										<strong>Error!</strong> ${msg}
									</div>
								</c:when>
								<c:otherwise>
									<div class="alert alert-success">
										<button class="close" data-dismiss="alert"></button>
										<strong>Success!</strong> 图文信息操作成功。
									</div>
								</c:otherwise>
							</c:choose>
						</c:if>
						<div class="message-size">
							<div class="new-msg center">
								<a href="${basePath}/h/w002_edit.ac"
									class="btn btn-link"><div>
										<i class="icon-comments-alt icon-5"></i>
									</div>新增图文消息</a>
							</div>
						</div>
						<div class="message-main">
						<c:forEach items="${map }" var="map" varStatus="i">
							<div class="col-md-6 message-size message-size">
								<ul id="message-info" class="unstyled">
									<c:forEach items="${map.value}" var="bean1" varStatus="n">
										<li class="article pos-rel <c:if test="${n.index==0 }">cover</c:if>">
											<c:if test="${n.index==0 }">
											<div class="msg-date">关键字:${bean1.keyword}</div>
											<div class="msg-date">${bean1.last_updated}</div>
											</c:if>
											<div class="pic-url">
												<span class="default-tip" style="display: none">封面图片</span> 
												<img  onerror="this.src='${basePath}/images/error/404.jpg';this.onerror='';"  src="${bean1.picurl}"
													alt="" style="">
											</div> <a href=${bean1.url} target="_blank"><h4
													class="title">${bean1.title}</h4></a>
										</li>
									</c:forEach>
									<li class="msg-actions center">
										<a href="${basePath}/h/w002_edit.ac?aid=${map.key}" class="btn green">
											<i class="icon-pencil"></i> 编辑
										</a> 
										<a href="javascript:void(0)" class="btn btn-danger" onclick="if(confirm('确认删除吗?')){location.href='${basePath}/h/w002_del.ac?aid=${map.key}'};" rel="nofollow">
											<i class="icon-trash"></i>删除
										</a>
									</li>
								</ul>
							</div>
						</c:forEach>
						</div>
				</div>
			</div>
			<!-- END PAGE CONTENT-->
		</div>
		<!-- END PAGE -->
	</div>
	<!-- END CONTAINER -->
	<!-- BEGIN FOOTER -->
	<%@ include file="../include/footer.jsp"%>
	<!-- END FOOTER -->

</body>
<!-- END BODY -->
</html>
<script type="text/javascript">
function loadUrlPage(offset, url, event) {
	location.href='${basePath}/' + url + event+'.ac?offset=' + offset;
}
$(function(){
	var margin = 10;//这里设置间距
	var li=$(".message-size");//这里是区块名称
	var	li_W = $(li[0]).outerWidth();//取区块的实际宽度（包含间距，这里使用源生的offsetWidth函数，不适用jQuery的width()函数是因为它不能取得实际宽度，例如元素内有pandding就不行了）
	function liuxiaofan(){//定义成函数便于调用
		var h=[];//记录区块高度的数组
		var n =Math.floor( $('.message-main').outerWidth()/li_W);//窗口的宽度除以区块宽度就是一行能放几个区块
		
		for(var i = 0;i < li.length;i++) {//有多少个li就循环多少次
			li_H = $(li[i]).outerHeight();//获取每个li的高度
			console.log(li_H);
			if(i < n) {//n是一行最多的li，所以小于n就是第一行了
				h[i]=li_H;//把每个li放到数组里面
				li.eq(i).css("top",0);//第一行的Li的top值为0
				li.eq(i).css("left",i * li_W);//第i个li的左坐标就是i*li的宽度
			} else{
				min_H =Math.min.apply(null,h) ;//取得数组中的最小值，区块中高度值最小的那个
				minKey = getarraykey(h, min_H);//最小的值对应的指针
				h[minKey] += li_H+margin ;//加上新高度后更新高度值
				li.eq(i).css("top",min_H+margin);//先得到高度最小的Li，然后把接下来的li放到它的下面
				li.eq(i).css("left",minKey * li_W);	//第i个li的左坐标就是i*li的宽度
			}
			$('.message-main').height(Math.max.apply(null,h));
		}
	}
	/* 使用for in运算返回数组中某一值的对应项数(比如算出最小的高度值是数组里面的第几个) */
	function getarraykey(s, v) {for(k in s) {if(s[k] == v) {return k;}}}
	/*这里一定要用onload，因为图片不加载完就不知道高度值*/
	window.onload = function() {liuxiaofan();};
	/*浏览器窗口改变时也运行函数*/
	window.onresize = function() {liuxiaofan();};
});

</script>