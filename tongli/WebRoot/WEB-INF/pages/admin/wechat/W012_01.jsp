<%--
/*
 * 微信服务_关注者(粉丝)信息展示 (列表页面)
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-03-20  wuxiaogang   程序・发布
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
		<th class="col-md-1"></th>
		<th class="col-md-2">昵称</th>
		<th class="col-md-2">备注名</th>
		<th class="col-md-1">性别</th>
		<th class="col-md-2">地区</th>
		<th class="col-md-1">订阅否</th>
		<th class="col-md-2">关注时间</th>
	</tr>
	<c:forEach items="${beans}" var="bean">
	<tr>
		<td><img src="${bean.headimgurl}" style="width: 40px;height: 40px;"  onerror="this.src='${basePath}/images/getheadimg.jpg'" ></td>
		<td>${bean.nickname}</td>
		<td>${bean.remarkname}</td>
		<td>
		<c:choose>
			<c:when test="${bean.sex=='1' }">男</c:when>
			<c:otherwise>
				<c:choose>
						<c:when test="${bean.sex=='2' }">女</c:when>
						<c:otherwise>未知</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>
		
		</td>
		<td>${bean.country}${bean.province}${bean.city}</td>
		<td>
		<c:choose>
			<c:when test="${bean.subscribe=='0' }">no</c:when>
			<c:otherwise>yes</c:otherwise>
		</c:choose>
		</td>
		<td>${bean.subscribe_time}</td>
	</tr>
	</c:forEach>
	</tbody>
</table>
<customtag:pagingext func="loadUrlPage" params="'h/w012_', 'list', 'a_div_info_list_'" />
