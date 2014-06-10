<%--
/*
 * 教师-课程列表--未完成
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-04-17  wuxiaogang        程序・发布
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
<style>
.grade p{float: left;text-align: left;color: red;}
</style>
<script type="text/javascript" src="${basePath}/js/jquery.form.js"></script>
<c:forEach items="${beans}" var="bean" varStatus="i">
	<div class="item_li item_li_1">${i.index+1}. ${bean.title}</div>
	<div class="item_con item_con_1">
	<div class="grade">
		<table  class="table table-striped table-condensed" >
			<tr>
				<td width="90" height="30" align="right"><strong>时间：</strong></td>
				<td>日期<font color="blue">${bean.day}</font>&nbsp;上课时间<font color="blue">${bean.begin_time}</font>&nbsp;下课时间<font color="blue">${bean.end_time}</font></td>
			</tr>
			<tr class="alert alert-success">
				<td height="30" align="right"><strong>地点：</strong></td>
				<td>${bean.addres}</td>
			</tr>
		</table>
		<table  class="table table-striped table-condensed" >
			<tr>
				<td height="30" align="center" colspan="2">
			============学员签到==========
				</td>
			</tr>
			<tr>
				<td align="left" colspan="2">
					<form id="${bean.id}" accept-charset="UTF-8"  action="${basePath}/t001_save.ac"  method="post">
						<s:token></s:token>
						<input type="hidden" name="course_title" value="${bean.title}">
						<input type="hidden" name="course_syllabus_id" value="${bean.id}">
						<c:set var="num" value="0"/>
						<div class="panel panel-success">
							<div class="panel-body">
								<ul>
						<c:forEach items="${bean.itemBeans}" var="s_bean">
						
								<li><strong>学员名称：</strong>
									${s_bean.name}
								</li>
							<li style="color: red;">
									${s_bean.name}上课情况:
								<c:choose>
									<c:when test="${s_bean.student_status!=null}">
										<c:if test="${s_bean.student_status=='0'}">已到</c:if>
										<c:if test="${s_bean.student_status=='1'}">旷课</c:if>
										<c:if test="${s_bean.student_status=='2'}">请假</c:if>
											${s_bean.student_status_note}
										<c:set var="num" value="${num+1}"/>
									</c:when>
									<c:otherwise>
											<input type="hidden" name="item_ids" value="${s_bean.id}">
											<input type="hidden" name="sid${s_bean.id}" value="${s_bean.student_id}">
											<input type="hidden" name="sname${s_bean.id}" value="${s_bean.name}">
											<label><input type="radio" class="xx2${bean.id}" checked="checked" name="sstatus${s_bean.id}" value="0">已到</label>
											<label><input type="radio" class="xx2${bean.id}" name="sstatus${s_bean.id}" value="1">旷课</label>
											<label><input type="radio" class="xx2${bean.id}" name="sstatus${s_bean.id}" value="2">请假</label>
											<input style="width:400px;height: 30px;" class=" xx2${bean.id}" name="sstatus_note${s_bean.id}"/>
									</c:otherwise>
								</c:choose>
							</li>
					</c:forEach>
					</ul>
						</div>
					</div>
					<div style="clear: both;"></div>
					<c:if test="${num!=fn:length(bean.itemBeans)}">
					<div class="panel panel-success">
						<div class="panel-heading">
							<a style="margin-left:300px;" type="submit" id="b_${bean.id}"class="btn blue"  onclick="if(confirm('确认提交学员签到情况吗?\n提交后不可更改!')){submitFrom2('${bean.id}');}">签到完成</a></h3>
						</div>
					</div>
					</c:if>
				</form>
				</td>
			</tr>
		</table>
	</div>
	</div>
</c:forEach>

<customtag:pagingext func="loadUrlPage" params="'t001_','list2','course_info2'" />
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
