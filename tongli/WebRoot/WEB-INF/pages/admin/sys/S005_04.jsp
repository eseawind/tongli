<%--
/*
 * 短消息管理--短信列表(页面)
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-06-02  wuxiaogang   程序・发布
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
			<th class="col-md-2">电话</th>
			<th class="col-md-4">内容</th>
			<th class="col-md-2">发送时间</th>
			<th class="col-md-1">状态</th>
			<th class="col-md-2"></th>
		</tr>
	    <c:forEach items="${beans}" var="bean">
		<tr>
			<td>
			${bean.sms_dst_id}
			</td>
			<td>
			${bean.sms_content}
			</td>
			<td>
			${bean.sms_sended_time}
			</td>
			<td>
			<c:if test="${bean.sms_status=='1'}">未发送</c:if>
			<c:if test="${bean.sms_status=='2'}">发送中</c:if>
			<c:if test="${bean.sms_status=='3'}">已发送</c:if>
			</td>
			<td><a href="${basePath}/h/s005_view.ac?id=${bean.sms_id}" class="btn edit green">详情</a>
				<%-- <a href="javascript:void(0)" class="btn btn-danger"
				onclick="if(confirm('确认删除吗?删除后无法恢复!')){location.href='${basePath}/h/s005_delxx.ac?id=${bean.sms_id}'};">删除</a> --%>
			</td>
		</tr>
		</c:forEach>
	</tbody>
</table>
<customtag:pagingext func="submitFrom1" params="'small_info_form_list1'" />
