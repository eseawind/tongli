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
<%@ taglib prefix="customtag" uri="/custom-tags"%>
<script type="text/javascript" src="${basePath}/js/jquery.form.js"></script>
<c:forEach items="${beans}" var="bean" varStatus="i">
	<div class="item_li">${i.index+1}. ${bean.title}</div>
	<div class="item_con">
		<p>时间:${bean.day}&nbsp;${bean.begin_time}&nbsp;${bean.end_time}</p>
		<p>&nbsp;</p>
		<p>地点:${bean.addres}</p>
		<p>&nbsp;</p>
		<p>
			<strong>${bean.teacher_name}:</strong>${bean.brief_info}
		</p>
		<p>我的上课信息:
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
			</p>
		<p>对教师评分:
			<c:choose>
				<c:when test="${ bean.teacher_score!=null}">
					<p>
					<c:if test="${bean.teacher_score=='0'}">差</c:if>
					<c:if test="${bean.teacher_score=='1'}">良</c:if>
					<c:if test="${bean.teacher_score=='2'}">优</c:if>
					</p>
					<p>
						备注:
						${bean.teacher_score_note}
					</p>
				</c:when>
				<c:otherwise>
					<form id="${bean.course_syllabus_item_id}" accept-charset="UTF-8"  action="${basePath}/m201_save.ac"  method="post">
					<input type="hidden" name="item_bean.id" value="${bean.course_syllabus_item_id}">
					<input type="hidden" name="item_bean.course_syllabus_id" value="${bean.id}">
					<input type="hidden" name="item_bean.teacher_id" value="${bean.teacher_id}">
					<input type="hidden" name="item_bean.student_id" value="${bean.student_id}">
					<p>
						<label><input type="radio" class="xx2${bean.course_syllabus_item_id}" name="item_bean.teacher_score" value="0">差</label>
						<label><input type="radio" class="xx2${bean.course_syllabus_item_id}" name="item_bean.teacher_score" value="1">良</label>
						<label><input type="radio" class="xx2${bean.course_syllabus_item_id}" name="item_bean.teacher_score" value="2">优</label>
					</p>
					<p>备注:
						<textarea style="width: 100%;height: 100%;" class="xx2${bean.course_syllabus_item_id}" name="item_bean.teacher_score_note"></textarea>
					</p>
					<p>
						<input id="b_${bean.course_syllabus_item_id}" type="button" value="提交" onclick="if(confirm('确认提交吗?')){submitFrom2('${bean.course_syllabus_item_id}');}">
					</p>
					</form>
				</c:otherwise>
			</c:choose>
		</p>
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