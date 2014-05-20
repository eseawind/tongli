<%--
/*
 * 会员-课程列表
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
<script type="text/javascript" src="${basePath}/js/jquery.form.js"></script>
<c:forEach items="${beans}" var="bean" varStatus="i">
	<div class="item_li">${i.index+1}. ${bean.title}</div>
	<div class="item_con">
		<div class="grade">
			<table width="600" border="0" cellspacing="0" cellpadding="2">
				<tr>
					<td width="118" height="30" align="right"><strong>时间：</strong></td>
					<td width="474">${bean.day}&nbsp;${bean.begin_time}&nbsp;${bean.end_time}</td>
				</tr>
				<tr>
					<td height="30" align="right"><strong>地点：</strong></td>
					<td>${bean.addres}</td>
				</tr>
				<tr>
					<td height="30" align="right"><strong>${bean.teacher_name}老师：</strong></td>
					<td>${bean.brief_info}</td>
				</tr>
				<tr>
					<td height="30" align="right"><strong>我的上课信息：</strong></td>
					<td>
					<c:choose>
						<c:when test="${bean.student_status!=null}">
							<p>
							<c:if test="${bean.student_status=='0'}">签到完成</c:if>
							<c:if test="${bean.student_status=='1'}">旷课</c:if>
							<c:if test="${bean.student_status=='2'}">请假</c:if>
							</p>
							<p>
								备注:
								${bean.student_status_note}
							</p>
						</c:when>
						<c:otherwise>
							暂未评价
						</c:otherwise>
					</c:choose>
				</td>
				</tr>
				<tr>
					<td height="30" align="right" valign="top"><strong>对老师评分：</strong></td>
					<td align="left">
						<c:choose>
							<c:when test="${ bean.teacher_score!=null}">
								<c:if test="${bean.teacher_score=='0'}">差</c:if>
								<c:if test="${bean.teacher_score=='1'}">良</c:if>
								<c:if test="${bean.teacher_score=='2'}">优</c:if>
								<p>
									备注:
									${bean.teacher_score_note}
								</p>
							</c:when>
							<c:otherwise>
								<form style="float: left;width: 100%;"  id="${bean.course_syllabus_item_id}" accept-charset="UTF-8"  action="${basePath}/m201_save.ac"  method="post">
								<input type="hidden" name="item_bean.id" value="${bean.course_syllabus_item_id}">
								<input type="hidden" name="item_bean.course_syllabus_id" value="${bean.id}">
								<input type="hidden" name="item_bean.teacher_id" value="${bean.teacher_id}">
								<input type="hidden" name="item_bean.student_id" value="${bean.student_id}">
								<p>
									<label><input type="radio" class="xx2${bean.course_syllabus_item_id}" name="item_bean.teacher_score" value="0">差</label>
									<label><input type="radio" class="xx2${bean.course_syllabus_item_id}" name="item_bean.teacher_score" value="1">良</label>
									<label><input type="radio" class="xx2${bean.course_syllabus_item_id}" name="item_bean.teacher_score" value="2">优</label>
								</p>
								<p style="height: 15px;"> </p>
								<p>
								<textarea style="width: 100%;height: 100%;" class="xx2${bean.course_syllabus_item_id}" name="item_bean.teacher_score_note"></textarea>
								</p>
								<p style="float: left;margin-left: 200px;">
									<input class="login_btn" id="b_${bean.course_syllabus_item_id}" type="button" value="提交" onclick="if(confirm('确认提交吗?')){submitFrom2('${bean.course_syllabus_item_id}');}">
								</p>
								</form>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</table>
		</div>
	</div>
</c:forEach>
<customtag:pagingext func="loadUrlPage" params="'m201_','list1','course_info'" />
<script>
	$(".item_li").click(function() {
		if ($(this).hasClass("on")) {
			$(this).removeClass("on");
		} else {
			$(this).addClass("on");
		}
		$(this).next(".item_con").slideToggle();
	});
	// 提交from
	function submitFrom2(from_id) {
		//登录认证
		loginCheck()
		//提交
		jQuery("#"+from_id).ajaxSubmit(function(data) {
			if (data == "1") {
				$('.xx2'+from_id).attr('readonly','readonly');
				jQuery("#b_"+from_id).remove();
				alert('提交成功!');
			} else {
				alert(data);
			}
		});
	}
</script>