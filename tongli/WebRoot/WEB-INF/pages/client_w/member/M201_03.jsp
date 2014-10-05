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
<div class="container no-bottom">
<c:if test="${beans!=null && fn:length(beans)>0 }">
	<c:forEach items="${beans}" var="bean" varStatus="i">
	<em class="speach-left-title"><span>${bean.date_created} </span>${bean.create_id}:</em>
     <p class="speach-left">${bean.detail_info}</p>
     <div class="clear"></div>
	</c:forEach>
 <div class="clear"></div>
<customtag:pagingext func="loadUrlPageComment" params="'m201_','clist1','${did}','&cid=${cid}'" />
</c:if>
</div>