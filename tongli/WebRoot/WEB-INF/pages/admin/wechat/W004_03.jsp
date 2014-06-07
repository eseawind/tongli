<%--
/*
 * 微信服务_自动回复_文章选择 (页面)
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-04-01  wuxiaogang   程序・发布
 * -------- ----------- ------------ ------------------------------------------
 * Copyright 2014 上海人保财险微信 System. - All Rights Reserved.
 *
 */
--%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" session="false"%>
<%@page import="cn.com.softvan.common.CommonConstant"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="customtag" uri="/custom-tags"%>

<table class="table table-condensed table-striped">
	<tbody>
		<tr>
			<th class="col-md-6">标题</th>
			<th class="col-md-3">时间</th>
		</tr>
		<c:forEach items="${beans}" var="bean">
		<tr>
			<td>
			<label><input type="radio" name="w004_03_article_id" id="${bean.id}" value="${bean.id}">
			${bean.title}</label>
			<input type="hidden" id="title_${bean.id}" value="${bean.title}">
			<input type="hidden" id="pic_url_${bean.id}" value="${bean.pic_url}">
			<input type="hidden" id="url_${bean.id}" value="${basePath}/w004_a_view.ac?id=${bean.id}">
			<input type="hidden" id="brief_${bean.id}" value="${bean.brief_info}">
			</td>
			<td>${bean.last_updated}</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<customtag:pagingext func="loadUrlPage" params="'h/w004_', 'list1', 'list1_article_div'" />
