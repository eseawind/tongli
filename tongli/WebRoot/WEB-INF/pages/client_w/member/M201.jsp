<%--
/*
 * 会员-个人中心
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-04-14  wuxiaogang        程序・发布
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
<%@ taglib prefix="customtag" uri="/custom-tags"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../include/title_meta.jsp"%>
<%@ include file="../include/public_js_css.jsp"%>

<link href="${basePath}/plugins/bootstrap.admin.theme/assets/css/style.css" rel="stylesheet" type="text/css"/>
<link href="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<script src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<link href="${basePath}/css/w_style.css"		 rel="stylesheet" type="text/css">
<link rel="stylesheet" href="${basePath}/css/blueimp-gallery.min.css">
<style type="text/css">
.page-header {
    padding-bottom: 9px;
    margin: 0px 0px 20px;
    border-bottom: 1px solid #EEE;
}
.tab-content {
    display: block;
    overflow: visible;
	position: relative;
}
.page-content {
    margin-top: 0px;
    min-height: 760px;
}
.content {
    margin:0px;
}
.item_con {
 margin:0px;
}
.next-quote {
    z-index: 99999;
    background-image: url('${basePath}/plugins/slideby/images/ui/next1.png');
    background-repeat: no-repeat;
    width: 10px;
    height: 10px;
    background-size: 10px 10px;
    position: absolute;
    right: 0px;
    margin-top:35px;;
}
.prev-quote {
    z-index: 99999;
    background-image: url('${basePath}/plugins/slideby/images/ui/prev1.png');
    background-repeat: no-repeat;
    width: 10px;
    height: 10px;
    background-size: 10px 10px;
    position: absolute;
    left: 0px;
    margin-top:35px;;
}
table tr td{
    text-align: left;
}
textarea{border: 1px solid #ddd;}
.tabbable-custom {
    margin-bottom: 15px;
    padding: 0px;
    overflow: visible;
}
div{
    overflow: visible;
}
.container{
width:auto;
margin-left: 5px;
margin-right: 5px;
}
p{
   border-radius:15px;
}
</style>
</head>
<body>
	<div class="all-elements">
		<%@ include file="../include/header.jsp"%>
		<div id="content" class="page-content">
			<div class="page-header">
				<a href="#" class="deploy-sidebar"></a>
				<p class="bread-crumb">
					我的课程表
				</p>
				<a href="javascript:history.go(-1);" class="deploy-contact left-list"></a>
			</div>
			<div class="content">
				<div class="container">
		                <a href="#" class="next-quote"></a>
		                <a href="#" class="prev-quote"></a>
		                <div class="quote-slider" data-snap-ignore="true" id="s_">
		                <c:forEach items="${student_beans}" var="student" varStatus="i">
							<c:if test="${i.index==0}">
								<c:set var="student_id" value="${student.id}" />
							</c:if>
							<div>
                        	 <div class="services-item">
					    		<a id="s_${student.id}" class="button button-dark center-button" href="javascript:;" onclick="loadInfo('${student.id}');">${student.name}</a>
							  </div>
			               </div>
						</c:forEach>
		             </div>
	            </div>    
				<div class="decoration"></div>
				<div class="web_enroll_info tabbable tabbable-custom">
					<ul class="nav nav-tabs" style="height:40px; ">
							<li id="tab_0_li" class="active "><a href="#tab_0" data-toggle="tab">完结课程(<font class="_struts_0" color="red">0</font>)</a></li>
							<li id="tab_1_li"><a href="#tab_1" data-toggle="tab">未完课程(<font class="_struts_1" color="red">0</font>)</a></li>
					</ul>
					<div class="tab-content">
						<div class="tab-pane active" id="tab_0">
							<!-- BEGIN FORM-->
							<div class="course_info" id="course_info1">
								
							</div>
							<!-- END FORM--> 
						</div>
						<div class="tab-pane" id="tab_1">
							<!-- BEGIN FORM-->
							<div class="course_info" id="course_info2">
								
							</div>
							<!-- END FORM--> 
						</div>
					</div>
				</div>
				<%@ include file="../include/footer.jsp"%>
			</div>
		</div>
	</div>
	<script src="${basePath}/js/jquery.blueimp-gallery.min.js"></script>
</body>
</html>
<script>
	$(function() {
		loadUrlPage(0,'m201_','list1','course_info1','${student_id}');
		loadUrlPage(0,'m201_','list2','course_info2','${student_id}');
	});
	function loadInfo(sid) {
		loginCheck();
		loadUrlPage(0,'m201_','list1','course_info1',sid);
		loadUrlPage(0,'m201_','list2','course_info2',sid);
	}
	function loadUrlPage(offset,url,event,divId,sid) {
		try{
			$('#s_').find('.services-item').find('a').removeClass('button-orange');
			$('#s_'+sid).addClass('button-orange');
		}catch(e){}
		loginCheck();
		//var load = "<a class='loading' >信息加载中...</a>";
		//jQuery("#" + divId).html(load);
		jQuery.ajax({
			url : '${basePath}/w/' + url + event+'.ac?offset='+offset+"&sid="+ sid  + '&time=' + new Date(),
			success : function(req) {
				jQuery("#"+divId).html(req);
			},
			error : function() {
				jQuery("#"+divId).html('信息加载失败!');
			}
		});
		reSetH();//重设高
	}
	function loginCheck() {
		jQuery.ajax({
			async : false,
			url : '${basePath}/w/mcheck.ac?time=' + new Date().getTime(),
			success : function(req) {
				if(req!='1'){
					alert('未登录或登录超时!请重新登录!');
					location.href='${basePath}/w/m201_login.ac';
				}
			},
			error : function() {
				alert("页面发生错误");
			}
		});
	}
	//--加载评论信息--
	function loadUrlPageComment(offset,url,event,divId,obj) {
		loginCheck();
		//var load = "<a class='loading' >信息加载中...</a>";
		//jQuery("#" + divId).html(load);
		jQuery.ajax({
			url : '${basePath}/w/' + url + event+'.ac?offset='+offset+'&did='+divId+ obj + '&time=' + new Date(),
			success : function(req) {
				jQuery("#"+divId).html(req);
			},
			error : function() {
				jQuery("#"+divId).html('信息加载失败!');
			}
		});
		reSetH();//重设高
	}
</script>