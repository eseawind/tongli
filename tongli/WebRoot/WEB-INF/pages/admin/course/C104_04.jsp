<%--
/*
 * 系统管理_在线报名 (页面)
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
<table class="table table-condensed table-striped ">
<tbody>
	<tr>
		<th class="col-md-3">姓名</th>
		<th class="col-md-1">性别</th>
		<th class="col-md-2">报名</th>
		<th class="col-md-2">状态</th>
		<th class="col-md-3"></th>
	</tr>
	<c:forEach items="${beans}" var="bean">
	<tr>
		<td>${bean.name}</td>
		<td>
			<c:if test="${bean.sex=='0'}">男</c:if>
			<c:if test="${bean.sex=='1'}">女</c:if>
		</td>
		<td>
			
			<c:if test="${bean.type=='0'}"><span class="label label-info">培训班</span></c:if>
			<c:if test="${bean.type=='1'}"><span class="label label-danger">冬夏令营</span></c:if>
			
		</td>
		<td>
			<c:if test="${bean.status=='0'}"><span class="label label-info">未处理</span></c:if>
			<c:if test="${bean.status=='1'}"><span class="label label-danger">报名失败</span></c:if>
			<c:if test="${bean.status=='2'}"><span class="label label-success">报名完成</span></c:if>
		</td>
		<td><a
			href="${basePath}/h/c104_edit.ac?id=${bean.id}"
			class="btn edit green">处理</a> <a href="javascript:void(0)"   class="btn btn-danger" 
			onclick="if(confirm('确认删除吗?')){location.href='${basePath}/h/c104_del.ac?id=${bean.id}'};"
			rel="nofollow">删除</a></td>
	</tr>
	</c:forEach>
	</tbody>
</table>
<customtag:pagingext func="submitFrom1" params="'small_info_form_list1'" />
