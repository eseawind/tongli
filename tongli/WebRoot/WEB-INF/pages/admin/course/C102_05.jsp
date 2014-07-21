<%--
/*
 * 课程--讨论
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
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="customtag" uri="/custom-tags"%>
<style>
.note-danger{background-color: #DFF0D8;}
</style>
<c:if test="${beans!=null && fn:length(beans)>0 }">
	<c:forEach items="${beans}" var="bean" varStatus="i">
		<div class="media">
			
			<c:set var="xianshi" value='style="display: block;"' />
			<c:set var="yingchang" value='style="display: none;"' />
				<c:if test="${bean.is_show=='0'}">
					<c:set var="xianshi" value='style="display: none;"' />
					<c:set var="yingchang" value='style="display: block;"' />
				</c:if>
			<button class="btn blue pull-left xianshi${bean.id}" ${xianshi} onclick="hvomment('${bean.id}','0')">
			<i class="fa fa-star"></i>
			显示
			</button>
			<button class="btn red pull-left yingchang${bean.id}" ${yingchang} onclick="hvomment('${bean.id}','1')">
			<i class="fa fa-star-o"></i>
			隐藏
			</button>
			<div class="media-body">
				<div class="note note-danger">
					<h4 class="media-heading alert-warning"><label style="color: #777;font-size: 12px;text-indent: 1em;">
					<c:choose>
						<c:when test="${bean.user_name!=null && fn:length(bean.user_name)>0}">${bean.user_name}</c:when>
						<c:otherwise>匿名</c:otherwise>
					</c:choose></label><span>${bean.date_created} </span></h4>
					<p class="alert alert-success alert-dismissable">${bean.detail_info} </p>
				</div>
			</div>
		</div>
	</c:forEach>
	<customtag:pagingext func="loadUrlPageComment" params="'h/c102_','clist1','${did}','&cid=${cid}'" />
</c:if>
<script>
	function hvomment(id,state){
		jQuery.ajax({
			url : '${basePath}/h/c102_csh.ac?id=' +id+"&s="+state,
			success : function(req) {
				if(req=='1'){
					if(state=='0'){
						$('.xianshi'+id).hide();
						$('.yingchang'+id).show();
					}else{
						$('.xianshi'+id).show();
						$('.yingchang'+id).hide();
					}
					myAlert_success("操作成功!");
				}else{
					myAlert_error(req);
				}
			},
			error : function() {
				myAlert_error('系统出错!');
			}
		});
	}
</script>