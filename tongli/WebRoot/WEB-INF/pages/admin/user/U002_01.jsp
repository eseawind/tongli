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
		<th  width="10%">角色名</th>
		<th  width="25%">是否拥有所有权限</th>
		<th  width="25%">排序</th>
		<th  width="25%" >操作</th>
	</tr>
	<c:forEach items="${beans}" var="bean"  varStatus="i">
	<tr>
		<td>${i.index+1}</td>
		<td>${bean.ro_name}</td>
		<td><c:choose>
		<c:when test="${bean.ro_super=='1'}">
		是
		</c:when>
		<c:otherwise>否</c:otherwise>
		</c:choose>          </td>
		<td>${bean.ro_priority}</td>
		<td>
		 <a href="${basePath}/h/u002_edit.ac?id=${bean.role_id}" class="btn edit green">编辑</a>
		 <a class="btn btn-danger"
		   onclick="if(confirm('确认删除吗?')){location.href='${basePath}/h/u002_del.ac?id=${bean.role_id}'};"  rel="nofollow">删除</a>
		</td>
	</tr>
	</c:forEach>
	</tbody>
</table>
