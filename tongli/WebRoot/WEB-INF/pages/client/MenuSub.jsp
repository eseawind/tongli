<%--
/*
 * 资讯栏目页
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-06-28  wuxiaogang        程序・发布
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
<c:forEach items="${tree_array}" var="tree">
     	<a href="${basePath}/c002_init.ac?tid=${tree.id}&pid=<c:choose><c:when test='${tree.parent_id!=null && tree.parent_id!=""}'>${tree.parent_id}</c:when><c:otherwise>${tree.id}</c:otherwise></c:choose>">${tree.name}</a>
     	<%-- <c:if test="${tree.beans!=null && fn:length(tree.beans)>0}">
     		<c:forEach items="${tree.beans}" var="bean">
     		     	<a href="${basePath}/c002_init.ac?tid=${bean.id}&pid=<c:choose><c:when test='${bean.parent_id!=null && bean.parent_id!=""}'>${bean.parent_id}</c:when><c:otherwise>${bean.id}</c:otherwise></c:choose>">${bean.name }</a>
     		      <c:if test="${bean.beans!=null && fn:length(bean.beans)>0}">
	      		<c:forEach items="${bean.beans}" var="bean2">
	      		     <a href="${basePath}/c002_init.ac?tid=${bean2.id}&pid=<c:choose><c:when test='${bean2.parent_id!=null && bean2.parent_id!=""}'>${bean2.parent_id}</c:when><c:otherwise>${bean2.id}</c:otherwise></c:choose>">${bean2.name }</a>
         		</c:forEach>
	      	</c:if>
        </c:forEach>
     	</c:if> --%>
   	</li>
</c:forEach>
