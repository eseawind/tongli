<%--
/*
 * 会员-课程列表--已完成--课程讨论
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-06-01  wuxiaogang        程序・发布
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
<c:if test="${beans!=null && fn:length(beans)>0 }">
	<c:forEach items="${beans}" var="bean" varStatus="i">
		<div class="media">
			<a href="#" class="pull-left">
			</a>
			<div class="media-body">
				<h4 class="media-heading alert-warning"><label style="color: #777;font-size: 12px;">
				<c:choose>
					<c:when test="${bean.user_name!=null && fn:length(bean.user_name)>0}">${bean.user_name}</c:when>
					<c:otherwise>匿名</c:otherwise>
				</c:choose></label><span>${bean.date_created} </span></h4>
				<p class="alert alert-success alert-dismissable">${bean.detail_info} </p>
			</div>
		</div>
	</c:forEach>
<customtag:pagingext func="loadUrlPageComment" params="'m201_','clist1','${did}','&cid=${cid}'" />
</c:if>