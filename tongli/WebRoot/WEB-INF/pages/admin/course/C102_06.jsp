<%--
/*
 * 系统管理_课程管理_课程表--已完成 (页面)
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-06-08  wuxiaogang   程序・发布
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
<table class="table table-condensed table-striped">
<tbody>
	<tr>
		<th class="col-md-1">课程日期</th>
		<th class="col-md-1">开始时间</th>
		<th class="col-md-1">结束时间</th>
		<th class="col-md-3">课程名称</th>
		<th class="col-md-3">上课地点</th>
		<th class="col-md-3"></th>
	</tr>
	<c:choose>
		<c:when test="${beans!=null && fn:length(beans)>0 }">
			<c:forEach items="${beans}" var="bean">
			<tr>
				<td>${bean.day}</td>
				<td>${bean.begin_time}</td>
				<td>${bean.end_time}</td>
				<td>${bean.title}</td>
				<td>${bean.addres}</td>
				<td><%-- <a
					href="${basePath}/h/c102_view.ac?id=${bean.id}"
					class="btn  btn-info" target="_blank">详情</a> --%> <a
					href="${basePath}/h/c102_edit.ac?id=${bean.id}"
					class="btn edit green">编辑</a> <a href="javascript:void(0)"   class="btn btn-danger" 
					onclick="if(confirm('确认删除吗?')){location.href='${basePath}/h/c102_del.ac?id=${bean.id}'};"
					rel="nofollow">删除</a></td>
			</tr>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<tr>
				<td colspan="6"></td>
			</tr>
		</c:otherwise>
	</c:choose>
	</tbody>
</table>
<customtag:pagingext func="loadUrlPage" params="'h/c102_','list1','course_info1','&t=0'" />
<script>
try{
	$('._struts_0').html('${PAGEROW_OBJECT_KEY.recordCount}');
}catch(e){}
</script>