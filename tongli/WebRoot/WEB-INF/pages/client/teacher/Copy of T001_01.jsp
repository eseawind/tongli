<%--
/*
 * 教师-课程列表-已完成
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
.j-pl-photolist-ul{list-style: none;width:710px;}
.j-pl-photoitem{list-style: none;float: left;width: 152px;height:150px;margin: 10px;}
.mod-photo-item{width: 152px;margin: 13px;}
.item-bd{}
.item-bd img{width: 152px; height: 129px; margin: 0px;border: 0px;}
</style>
<script type="text/javascript" src="${basePath}/js/jquery.form.js"></script>
<c:forEach items="${beans}" var="bean" varStatus="i">
	<div class="item_li item_li_0">${i.index+1}. ${bean.title}</div>
	<div class="item_con item_con_0">
	<div class="grade">
		<table  class="table table-striped table-condensed" >
			<tr>
				<td width="118" height="30" align="right"><strong>时间：</strong></td>
				<td width="474">${bean.day}&nbsp;${bean.begin_time}&nbsp;${bean.end_time}</td>
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
						<table  >
						<c:forEach items="${bean.itemBeans}" var="s_bean">
							<tr>
								<td width="118" height="30" align="right"><strong>学员名称：</strong></td>
								<td width="474"> ${s_bean.name}</td>
							</tr>
							<tr>
								<td width="118" height="30" align="right">对我的评价:
								</td>
								<td width="474">
									<c:choose>
										<c:when test="${ s_bean.teacher_score!=null}">
											<p>
											<c:if test="${s_bean.teacher_score=='0'}">差</c:if>
											<c:if test="${s_bean.teacher_score=='1'}">良</c:if>
											<c:if test="${s_bean.teacher_score=='2'}">优</c:if>
											</p>
											<p>
												${s_bean.teacher_score_note}
											</p>
										</c:when>
										<c:otherwise>
											暂未评价
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
							<tr>
								<td width="118" height="30" align="right" valign="top">
									${s_bean.name}上课情况:
								</td>
								<td width="474" valign="top">
								<c:choose>
									<c:when test="${s_bean.student_status!=null}">
										<p>
										<c:if test="${s_bean.student_status=='0'}">签到完成</c:if>
										<c:if test="${s_bean.student_status=='1'}">旷课</c:if>
										<c:if test="${s_bean.student_status=='2'}">请假</c:if>
										</p>
										<p style="height: 15px;"> </p>
										<p>
											${s_bean.student_status_note}
										</p>
										<c:set var="num" value="${num+1}"/>
									</c:when>
									<c:otherwise>
										<p>
											<input type="hidden" name="item_ids" value="${s_bean.id}">
											<input type="hidden" name="sid${s_bean.id}" value="${s_bean.student_id}">
											<input type="hidden" name="sname${s_bean.id}" value="${s_bean.name}">
											<label><input type="radio" class="xx2${bean.id}" checked="checked" name="sstatus${s_bean.id}" value="0">签到完成</label>
											<label><input type="radio" class="xx2${bean.id}" name="sstatus${s_bean.id}" value="1">旷课</label>
											<label><input type="radio" class="xx2${bean.id}" name="sstatus${s_bean.id}" value="2">请假</label>
										</p>
										<p style="height: 15px;"> </p>
										<p>
											<textarea style="width: 100%;height: 100%;" class="xx2${bean.id}" name="sstatus_note${s_bean.id}"></textarea>
										</p>
									</c:otherwise>
								</c:choose>
								</td>
							</tr>
					</c:forEach>
					</table>
					<div style="clear: both;"></div>
					<c:if test="${num!=fn:length(bean.itemBeans)}">
					<p style="float: left;margin-left: 200px;">
						<input class="login_btn" id="b_${bean.id}" type="button" value="提交" onclick="if(confirm('确认提交吗?')){submitFrom2('${bean.id}');}">
					</p>
					</c:if>
				</form>
				</td>
			</tr>
		</table>
		<table  class="table table-striped table-condensed" >
			<tr>
				<td height="30" align="center" colspan="2">
					============课堂详情==========
				</td>
			</tr>
			<tr>
				<td align="left" colspan="2">
					<form id="${bean.id}" accept-charset="UTF-8"  action="${basePath}/t001_save.ac"  method="post">
						<s:token></s:token>
						<input type="hidden" name="course_syllabus_id" value="${bean.id}">
 
						<div style="clear: both;"></div>
						<div class="form-group" style="width: 700px;">
							<div class="qeditor_border">
								<textarea name="bean.detail_info" style="height: 300px;width: 100%;" id="article_description${bean.id}" >
									${bean.detail_info}
								</textarea>
							</div>
						</div>
						<script type="text/javascript">
						//--编辑框
						editor.create('#article_description${bean.id}', {
							 resizeType : 2,
					         uploadJson : '${basePath}/uploadFile?isrich=1',
					         fileManagerJson : '${basePath}/plugins/editor/jsp/file_manager_json.jsp',
							 allowFileManager : true
						});
						</script>
						<div style="clear: both;"></div>
						<p style="float: left;margin-left: 200px;">
							<input class="login_btn" id="b_${bean.id}" type="button" value="保存详情" onclick="submitFrom3('${bean.id}');">
						</p>
				</form>
				</td>
			</tr>
	</table>
</c:forEach>
<customtag:pagingext func="loadUrlPage" params="'t001_','list1','course_info','&status=0'" />
<script type="text/javascript">
	$(".item_li_0").click(function() {
		$(".item_li").removeClass("on");
		if ($(this).hasClass("on")) {
			$(this).removeClass("on");
		} else {
			$(this).addClass("on");
		}
		$(this).next(".item_con_0").slideToggle();
	});
	// 提交from
	function submitFrom2(from_id) {
		//登录认证
		loginCheck();
		//提交
		jQuery("#"+from_id).ajaxSubmit(function(data) {
			if (data == "1") {
				$('.xx2'+from_id).attr('readonly','readonly');
				$('.xx2'+from_id).attr('disabled','disabled');
				jQuery("#b_"+from_id).remove();
				alert('提交成功!');
			} else {
				alert(data);
			}
		});
	}
	// 提交from
	function submitFrom3(from_id) {
		//登录认证
		loginCheck();
		//提交
		jQuery("#"+from_id).ajaxSubmit(function(data) {
			if (data == "1") {
				//$('.xx2'+from_id).attr('readonly','readonly');
				//$('.xx2'+from_id).attr('disabled','disabled');
				//jQuery("#b_"+from_id).remove();
				alert('提交成功!');
			} else {
				alert(data);
			}
		});
	}
	try{
		$('._struts_0').html('${PAGEROW_OBJECT_KEY.recordCount}');
	}catch(e){}
	
	
</script>
</c:if>