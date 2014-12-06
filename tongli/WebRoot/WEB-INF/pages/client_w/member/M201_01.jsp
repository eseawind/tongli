<%--
/*
 * 会员-课程列表--已完成
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
<style>
.table{margin-bottom: 0px;}
.blog-images li img {
opacity: 1;
}

</style>
<script type="text/javascript" src="${basePath}/js/jquery.form.js"></script>
<input type="hidden"   id="uid" value="${uid}"  />
<c:forEach items="${beans}" var="bean" varStatus="i">
	<div class="item_li item_li_0"  style="margin:10px 0;width: 100%;"><span class="badge badge-info">${i.index+1}</span><font >${bean.day}&nbsp;${bean.begin_time}</font> ${bean.title}</div>
	<div class="item_con item_con_0">
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
			<table class="table table-striped table-condensed">
				<tr>
					<td height="30"><strong>我的上课信息</strong></td>
				</tr>
				<tr>
					<td style="color: red;">
								<c:choose>
									<c:when test="${bean.student_status!=null}">
										<c:if test="${bean.student_status=='0'}">签到完成</c:if>
										<c:if test="${bean.student_status=='1'}">旷课</c:if>
										<c:if test="${bean.student_status=='2'}">请假</c:if>
										${bean.student_status_note}
									</c:when>
									<c:otherwise>
										暂未评价
									</c:otherwise>
								</c:choose>
				</td>
				</tr>
			</table>
			<table class="table table-striped table-condensed">
				<tr>
					<td height="30" valign="top"><strong>对老师评分：</strong></td>
				</tr>
				<tr>
					<td align="left">
						<div class="panel-body">
						<c:choose>
							<c:when test="${ bean.teacher_score!=null}">
								<c:if test="${bean.teacher_score=='0'}">一般</c:if>
								<c:if test="${bean.teacher_score=='1'}">很好</c:if>
								<c:if test="${bean.teacher_score=='2'}">优秀</c:if>
								${bean.teacher_score_note}
							</c:when>
							<c:otherwise>
								<form style="float: left;width: 100%;"  id="${bean.course_syllabus_item_id}" accept-charset="UTF-8"  action="${basePath}/w/m201_save.ac"  method="post">
								<input type="hidden" name="item_bean.id" value="${bean.course_syllabus_item_id}">
								<input type="hidden" name="item_bean.course_syllabus_id" value="${bean.id}">
								<input type="hidden" name="item_bean.teacher_id" value="${bean.teacher_id}">
								<input type="hidden" name="item_bean.student_id" value="${bean.student_id}">
								<label><input type="radio" class="xx2${bean.course_syllabus_item_id}" name="item_bean.teacher_score" value="0">一般</label>
								<label><input type="radio" class="xx2${bean.course_syllabus_item_id}" name="item_bean.teacher_score" value="1">很好</label>
								<label><input type="radio" class="xx2${bean.course_syllabus_item_id}" name="item_bean.teacher_score" value="2">优秀</label>
								<textarea style="width: 100%;height: 50px; " class="xx2${bean.course_syllabus_item_id}" name="item_bean.teacher_score_note"></textarea>
								<a style="margin-top:10px;" class="button-icon icon-setting button-green" id="b_${bean.course_syllabus_item_id}" onclick="if(confirm('确认提交评价信息吗?')){submitFrom2('${bean.course_syllabus_item_id}');}">提交评价信息</a>
								</form>
							</c:otherwise>
						</c:choose>
						</div>
					</td>
				</tr>
			</table>
			<table  class="table table-striped table-condensed" >
				<tr>
					<td height="30" align="center" >
						课堂详情
					</td>
				</tr>
				<tr>
					<td align="left" >
						<ul class="list-inline blog-images">
							<c:forEach items="${bean.picBeans}" var="photoBean">
									<li>
										<a href="${photoBean.pic_url}"  title="${photoBean.pic_title}" download="${photoBean.pic_title}" data-gallery>
					                   	 <img src="${fn:replace(photoBean.pic_url, "n3", "n0")}">
					                    </a>
									</li>
							</c:forEach>
						</ul>
					</td>
				</tr>
				<tr>
					<td align="left" class="">
						<div class="blog-page">
							<h3>参与讨论</h3>
							<div id="comment_list1_div_${bean.id}_${i.index+1}">
							</div>
							<hr>
							<div class="post-comment">
								<h3></h3>
								<form role="form" id="comment_form_${bean.id}_${i.index+1}" accept-charset="UTF-8"  action="${basePath}/w/m201_csave.ac"  method="post">
									<div class="form-group">
										<label class="control-label">评论信息<span class="required">*200字以内</span></label>
										<input type="hidden" name="cbean.info_id" id="cbean_info_id" value="${bean.id}"  />
										<textarea style="border: 1px solid #eee;" name="cbean.detail_info" id="cbean_detail_info" class="form-control" rows="4"></textarea>
									</div>
									<a class="button button-blue margin-top-10" onclick="submitFrom4('comment_form_${bean.id}_${i.index+1}','comment_list1_div_${bean.id}_${i.index+1}');">提交评论信息</a>
								</form>
							</div>
						</div>
						</td>
					</tr>
			</table>
			<script type="text/javascript">
			loadUrlPageComment(0,'m201_','clist1','comment_list1_div_${bean.id}_${i.index+1}','&cid=${bean.id}');
			</script>
	</div>
</c:forEach>
<!-- The blueimp Gallery widget data-filter=":even" -->
<div id="blueimp-gallery" class="blueimp-gallery blueimp-gallery-controls" >
    <div class="slides"></div>
    <h3 class="title"></h3>
    <a class="prev">‹</a>
    <a class="next">›</a>
    <a class="close">×</a>
    <a class="play-pause"></a>
    <ol class="indicator"></ol>
</div>
<customtag:pagingext func="loadUrlPage" params="'m201_','list1','course_info1','${sid}'" />
</c:if>
<script>
	$(".item_li_0").click(function() {
		$(".item_li").removeClass("on");
		if ($(this).hasClass("on")) {
			$(this).removeClass("on");
		} else {
			$(this).addClass("on");
		}
		var xxobj=$(this).next(".item_con_0").is(":hidden");
		$(".item_con_0").hide();
		if(xxobj){
			$(this).next(".item_con_0").slideToggle();
		}else{
			//$(this).next(".item_con_0").show();
		}
		reSetH();//重设高
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
				myAlert('提交成功!');
			} else {
				myAlert(data);
			}
		});
	}
	// 提交from comment
	function submitFrom4(from_id,divid) {
		//登录认证
		loginCheck()
		
		var info_obj=$('#'+from_id).find('#cbean_detail_info');
		var info_val=$.trim(info_obj.val());
		if(info_val.length<200){
			if(info_val==''||info_val.length==0){
				myAlert_error('评论信息为空!');
				return false;
			}
			//提交
			jQuery("#"+from_id).ajaxSubmit(function(data) {
				if (data == "1") {
					var d=new Date(); 
					var formatdate=d.getFullYear()+'-'+(d.getMonth()+1)+"-"+d.getDate()+" "+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds()+"";
					$('#'+divid).prepend('<em class="speach-right-title"><span>'+formatdate+' </span>${uid}:</em><p class="speach-right blue-bubble">'+info_val+'</p><div class="clear"></div>');
					info_obj.val('');
					//myAlert('评论成功!');
				} else {
					myAlert_error(data);
				}
			});
		}else{
			myAlert_error('评论字数超过限制,200字以内!');
		}
	}
	try{
		$('._struts_0').html('${PAGEROW_OBJECT_KEY.recordCount}');
	}catch(e){}
</script>
