<%--
/*
 * 用户列表页面
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-03-20  ll   程序・发布
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
		<th  width="5%">编号</th>
		<th  width="10%">登录名</th>
		<th  width="10%">真实姓名</th>
		<th  width="35%">角色</th>
		<th  width="25%">状态</th>
		<th  width="25%" >操作</th>
	</tr>
	<c:forEach items="${beans}" var="bean"  varStatus="i">
	<tr>
		<td>${i.index+1}</td>
		<td>${bean.username}</td>
		<td>${bean.nickname}</td>
		<td><c:forEach items="${bean.roles}" var="role">
				${role.ro_name} 
		</c:forEach></td>
		<td>
		<c:choose>
			<c:when test="${bean.enabled=='0' }">禁用</c:when>
			<c:otherwise>
				<c:choose>
						<c:when test="${bean.enabled=='1' }">启用</c:when>
						<c:otherwise>未知</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>
		
		</td>
		<td>
		 <a href="${basePath}/h/u001_edit.ac?id=${bean.id}&flag=edit" class="btn edit green">编辑</a>
		 <a class="btn btn-danger"
		  onclick="if(confirm('确认删除吗?')){location.href='${basePath}/h/u001_del.ac?id=${bean.id}'};" rel="nofollow">删除</a>
		</td>
	</tr>
	</c:forEach>
	</tbody>
</table>
<customtag:pagingext func="loadUrlPage" params="'h/u001_', 'list', 'a_div_info_list_'" />
