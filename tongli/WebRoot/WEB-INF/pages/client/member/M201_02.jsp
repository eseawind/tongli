<%--
/*
 * 会员-课程列表--未完成
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-04-14  wuxiaogang        程序・发布
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
<script type="text/javascript" src="${basePath}/js/jquery.form.js"></script>
<c:forEach items="${beans}" var="bean" varStatus="i">
	<div class="item_li item_li_1">${i.index+1}. ${bean.title}</div>
	<div class="item_con item_con_1">
		<div class="grade">
			<table class="table table-striped table-condensed">
				<tr>
					<td width="90" height="30" ><strong>上课时间：</strong></td>
					<td>日期<font color="blue">${bean.day}</font>&nbsp;上课时间<font color="blue">${bean.begin_time}</font>&nbsp;下课时间<font color="blue">${bean.end_time}</font></td>
				</tr>
				<tr class="alert alert-success">
					<td height="30" ><strong>上课地点：</strong></td>
					<td>${bean.addres}</td>
				</tr>
			</table>
			<table class="table table-striped table-condensed">
				<tr>
					<td height="30"><strong>上课老师简介</strong></td>
				</tr>
				<tr>
					<td>${bean.teacher_name}:${bean.brief_info}</td>
				</tr>
			</table>
		</div>
	</div>
</c:forEach>
<customtag:pagingext func="loadUrlPage" params="'m201_','list2','course_info2','${sid}'" />
</c:if>
<script>
	$(".item_li_1").click(function() {
		if ($(this).hasClass("on")) {
			$(this).removeClass("on");
		} else {
			$(this).addClass("on");
		}
		$(this).next(".item_con_1").slideToggle();
	});
	try{
		$('._struts_1').html('${PAGEROW_OBJECT_KEY.recordCount}');
	}catch(e){}
</script>
