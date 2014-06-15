<%--
/*
 * 课程--在线报名
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-06-07  wuxiaogang        程序・发布
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

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../include/title_meta.jsp"%>
<%@ include file="../include/public_js_css.jsp"%>
<link rel="stylesheet" type="text/css" href="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/jquery-multi-select/css/multi-select.css" />
<link href="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/select2/select2_metro.css"  rel="stylesheet" type="text/css"  />
<link rel="stylesheet" type="text/css" href="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/bootstrap-datepicker/css/datepicker.css" />
<link rel="stylesheet" type="text/css" href="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/bootstrap-datepicker/less/datepicker.less" />
<link rel="stylesheet" type="text/css" href="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/clockface/css/clockface.css" />
<link href="${basePath}/js/bootstarp-date/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen"/>

<style type="text/css">

</style>
</head>

<body class="page-header-fixed">
	<!-- BEGIN HEADER -->
	<%@ include file="../include/header.jsp"%>
	<!-- END   HEADER -->
	<div class="main pr">
		<div class="c10"></div>
		<div class="w">
			<div class="body fr" style="width: 980px;">
				<div class="title">&nbsp; 在线报名</div>
				<div class="content" style="min-height: 390px;">
					<div class="web_enroll_info tabbable tabbable-custom">
						<ul class="nav nav-tabs" style="height:40px; ">
								<li id="tab_0_li" class="active "><a href="#tab_0" data-toggle="tab" style="color: #468847;">运动培训班</a></li>
								<li id="tab_1_li"><a href="#tab_1" data-toggle="tab" style="color: #B94A48;">冬夏令营</a></li>
						</ul>
						<div class="tab-content">
							<div class="tab-pane active" id="tab_0" >
								<!-- BEGIN FORM-->
								<form accept-charset="UTF-8" role="form"   action="${basePath}/c203_save.ac" method="post">
								<div class="form-horizontal" >
									<input type="hidden" name="bean.id" value="${bean.id}" />
									<input type="hidden" name="bean.type" value="0">
									<table class="table table-striped table-condensed" style="text-align: center;">
										<tr class="alert alert-success">
											<td height="30"><strong style="font-size: 3em;">运动培训班申请表</strong></td>
										</tr>
									</table>
									<table class="table table-striped table-condensed">
										<tr class="alert alert-warning" >
											<td height="30" colspan="4"><i class="fa  fa-bar-chart-o"></i> <strong>申请人资料</strong></td>
										</tr>
									</table>
										<div class="form-group">
											<label class="col-md-3 control-label">学员号</label>
											<div class="col-md-8">
												<input class="form-control" type="text" placeholder="请输入学员号"
													name="bean.student_num" value="${bean.student_num}">
												<span class=help-block"></span>
											</div>
										</div>
										<div class="form-group">
											<label class="col-md-3 control-label">姓名</label>
											<div class="col-md-3">
												<input class="form-control" type="text" placeholder="请输入姓名"
													name="bean.name" value="${bean.name}"> <span
													class=help-block"></span>
											</div>
											<label class="col-md-2 control-label">生日</label>
											<div id="bean_birthday1" class="input-group input-medium date date-picker col-md-2" data-date-format="yyyy-mm-dd" data-date-end-date="-730d">
												<input class="form-control" type="text" placeholder="请输入生日" readonly="readonly"
													name="bean.birthday" value="${bean.birthday}">
													 <span class="input-group-btn">
													<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
													</span>
													<span class=help-block"></span>
											</div>
											
										</div>
										<div class="form-group">
											<label class="col-md-3 control-label"
												style="margin-top: -8px;">性别</label>
											<div class="col-md-8">
												<label><input type="radio" name="bean.sex" value="0">
													男 </label>&nbsp; &nbsp; <label><input type="radio"
													name="bean.sex" value="1"> 女 </label> <span
													class=help-block"></span>
											</div>
										</div>
										<div class="form-group">
											<label class="col-md-3 control-label">移动电话</label>
											<div class="col-md-3">
												<input class="form-control" type="text"
													placeholder="请输入移动电话" name="bean.cell_tel"
													value="${bean.cell_tel}"> <span class=help-block"></span>
											</div>
											<label class="col-md-2 control-label">家庭电话</label>
											<div class="col-md-3">
												<input class="form-control" type="text"
													placeholder="请输入家庭电话" name="bean.tel" value="${bean.tel}">
												<span class=help-block"></span>
											</div>
										</div>
										<div class="form-group">
											<label class="col-md-3 control-label">家庭住址</label>
											<div class="col-md-8">
												<input class="form-control" type="text"
													placeholder="请输入家庭住址" name="bean.home_address"
													value="${bean.home_address}"> <span
													class=help-block"></span>
											</div>
										</div>
										<table class="table table-striped table-condensed">
											<tr class="alert alert-warning" >
												<td height="30" colspan="4"><i class="fa  fa-bar-chart-o"></i> <strong>详情</strong></td>
											</tr>
										</table>
										<div class="form-group">
											<label class="col-md-3 control-label" style="margin-top: -8px;">是否接受过任何游泳教学课程</label>
											<div class="col-md-8">
													<label><input type="radio" name="bean.swim_survey" value="0"> 是(曾参加过游泳学习/培训) </label>
													<label><input type="radio" name="bean.swim_survey" value="1"> 否(从未接触过游泳学习/培训) </label>
													<span class=help-block"></span>
											</div>
										</div>
										<div class="form-group"> <label class="col-md-3 control-label" style="margin-top: -8px;">游泳技能</label> <div class="col-md-8"> 
											<label><input type="radio" name="bean.swim_skills" value="0"> 初级 </label>
											<label><input type="radio" name="bean.swim_skills" value="1"> 中级 </label>
											<label><input type="radio" name="bean.swim_skills" value="2"> 专业 </label>
											<span class=help-block"></span> </div> 
										</div>
										<div class="form-group">
										 <label class="col-md-3 control-label">培训课程</label>
										 <div class="col-md-8">
											 <div class="input-group">
											 	<span class="input-group-addon">
												<i class="fa fa-flag"></i>
												</span>
												<select name="bean.course" id="course_select2_sample2"  class="form-control select2 select2me" data-placeholder="选择课程.." multiple>
													<optgroup label="课程列表">
													<c:forEach items="${course_beans}" var="course_bean">
														<option value="${course_bean.title}">${course_bean.title}</option>
													</c:forEach>
													</optgroup>
												</select>
											</div>
										</div>
										
									</div>
										<div class="panel panel-warning">
											<div class="panel-heading ">
												<div class="panel-title" style="color: #999;"><i class="fa fa-bar-chart-o"></i> 学员须知</div>
											</div>
											<div class="panel-body">
												<div class="note note-danger">
													<h4 class="block">一.补课事宜</h4>
													<p>
														略
													</p>
												</div>
												<div class="note note-warning">
													<h4 class="block">二.其他事宜</h4>
													<p>
														略
													</p>
												</div>
											</div>
										</div>
										<table class="table table-striped table-condensed">
											<tr class="alert alert-warning" >
												<td height="30" colspan="4"><i class="fa  fa-bar-chart-o"></i> <strong>=============</strong></td>
											</tr>
										</table>
										<div class="form-group">
											<label class="col-md-3 control-label">学员/监护人姓名</label>
											<div class="col-md-8">
												<input class="form-control" type="text"
													placeholder="请输入学员/监护人姓名" name="bean.guardian"
													value="${bean.guardian}"> <span class=help-block"></span>
											</div>
										</div>
										<div class="form-group">
											<label class="col-md-3 control-label">学费</label>
											<div class="col-md-3">
												<input class="form-control" type="number"
													placeholder="请输入报名价格" name="bean.price"
													value="${bean.price}"> <span class=help-block"></span>
											</div>
											<label class="col-md-2 control-label">经办人</label>
											<div class="col-md-3">
												<input class="form-control" type="text" placeholder="请输入经办人"
													name="bean.agent" value="${bean.agent}"> <span
													class=help-block"></span>
											</div>
										</div>
										<div class="form-group">
											<label class="col-md-3 control-label">缴费日期</label>
											<div id="bean_pay_day1" class="input-group input-medium date date-picker col-md-2" data-date-format="yyyy-mm-dd" data-date-start-date="-50d">
												<input class="form-control" type="text"
													placeholder="请输入缴费日期" name="bean.pay_day" readonly="readonly"
													value="${bean.pay_day}"> 
													<span class="input-group-btn">
													<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
													</span>
													<span class=help-block"></span>
											</div>
										</div>
										<div class="form-group">
											<label class="col-md-3 control-label">课程开始时间</label>
											<div id="bean_begin_day1" class="input-group input-medium date date-picker col-md-2" data-date-format="yyyy-mm-dd" data-date-start-date="+0d">
												<input class="form-control" type="text"
													placeholder="请输入课程开始时间" name="bean.begin_day" readonly="readonly"
													value="${bean.begin_day}"> 
													<span class="input-group-btn">
													<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
													</span>
													<span class=help-block"></span>
											</div>
											<label class="col-md-2 control-label">课程结束时间</label>
											<div id="bean_end_day1" class="input-group input-medium date date-picker col-md-2" data-date-format="yyyy-mm-dd" data-date-start-date="+0d">
												<input class="form-control" type="text"
													placeholder="请输入课程结束时间" name="bean.end_day" readonly="readonly"
													value="${bean.end_day}"> 
													<span class="input-group-btn">
													<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
													</span>
													<span class=help-block"></span>
											</div>
										</div>
										
										<div class="c10"></div>
										<div class="alert alert-danger">
											<label><input type="checkbox" style="margin-top: -1px;" onclick="if(!!$(this).prop('checked')){$('#btn1_1111').show();}else{$('#btn1_1111').hide()}">本人承诺遵守俱乐部规章制度以及以上学员行为准则.</label> 
										</div>
										<div class="c10"></div>
									<div  style="margin-left: 410px;">
									<button id="btn1_1111" type="submit" style="display: none;" class="btn green">提交报名申请表</button>
									</div>
									<div class="c10"></div>
									</div>
								</form>
								<!-- END FORM--> 
							</div>
							<div class="tab-pane" id="tab_1">
								<!-- BEGIN FORM-->
								<form accept-charset="UTF-8" class="form-horizontal" action="${basePath}/c203_save.ac" method="post">
									<input type="hidden" name="bean.id" value="${bean.id}" />
									<input type="hidden" name="bean.type" value="1">
									<table class="table table-striped table-condensed" style="text-align: center;">
										<tr class="alert alert-danger">
											<td height="30"><strong style="font-size: 3em;">童励运动冬夏令营报名表</strong></td>
										</tr>
									</table>
<div class="form-group"> <label class="col-md-3 control-label">报名课程期代码(第几期)</label> <div class="col-md-8"> <input class="form-control"  type="text" placeholder="请输入报名课程期代码(第几期)" name="bean.code" value="${bean.code}"> <span class=help-block"></span> </div> </div>
<div class="form-group"> <label class="col-md-3 control-label">报名地址</label> <div class="col-md-8"> <input class="form-control"  type="text" placeholder="请输入报名地址" name="bean.addres" value="${bean.addres}"> <span class=help-block"></span> </div> </div>
<div class="form-group"> <label class="col-md-3 control-label">报名价格</label> <div class="col-md-8"> <input class="form-control"  type="text" placeholder="请输入报名价格" name="bean.price" value="${bean.price}"> <span class=help-block"></span> </div> </div>
<div class="form-group"> <label class="col-md-3 control-label">课程开始时间</label> <div class="col-md-8"> <input class="form-control"  type="text" placeholder="请输入课程开始时间" name="bean.begin_day" value="${bean.begin_day}"> <span class=help-block"></span> </div> </div>
<div class="form-group"> <label class="col-md-3 control-label">课程结束时间</label> <div class="col-md-8"> <input class="form-control"  type="text" placeholder="请输入课程结束时间" name="bean.end_day" value="${bean.end_day}"> <span class=help-block"></span> </div> </div>
<div class="form-group"> <label class="col-md-3 control-label">缴费日期</label> <div class="col-md-8"> <input class="form-control"  type="text" placeholder="请输入缴费日期" name="bean.pay_day" value="${bean.pay_day}"> <span class=help-block"></span> </div> </div>
<div class="form-group"> <label class="col-md-3 control-label">经办人</label> <div class="col-md-8"> <input class="form-control"  type="text" placeholder="请输入经办人" name="bean.agent" value="${bean.agent}"> <span class=help-block"></span> </div> </div>
<div class="form-group"> <label class="col-md-3 control-label">学员号</label> <div class="col-md-8"> <input class="form-control"  type="text" placeholder="请输入学员号" name="bean.student_num" value="${bean.student_num}"> <span class=help-block"></span> </div> </div>
<div class="form-group"> <label class="col-md-3 control-label">姓名</label> <div class="col-md-8"> <input class="form-control"  type="text" placeholder="请输入姓名" name="bean.name" value="${bean.name}"> <span class=help-block"></span> </div> </div>
<div class="form-group"> <label class="col-md-3 control-label">性别</label> <div class="col-md-8"> <input class="form-control"  type="text" placeholder="请输入性别" name="bean.sex" value="${bean.sex}"> <span class=help-block"></span> </div> </div>
<div class="form-group"> <label class="col-md-3 control-label">家庭电话</label> <div class="col-md-8"> <input class="form-control"  type="text" placeholder="请输入家庭电话" name="bean.tel" value="${bean.tel}"> <span class=help-block"></span> </div> </div>
<div class="form-group"> <label class="col-md-3 control-label">移动电话</label> <div class="col-md-8"> <input class="form-control"  type="text" placeholder="请输入移动电话" name="bean.cell_tel" value="${bean.cell_tel}"> <span class=help-block"></span> </div> </div>
<div class="form-group"> <label class="col-md-3 control-label">国籍</label> <div class="col-md-8"> <input class="form-control"  type="text" placeholder="请输入国籍" name="bean.nationality" value="${bean.nationality}"> <span class=help-block"></span> </div> </div>
<div class="form-group"> <label class="col-md-3 control-label">生日</label> <div class="col-md-8"> <input class="form-control"  type="text" placeholder="请输入生日" name="bean.birthday" value="${bean.birthday}"> <span class=help-block"></span> </div> </div>
<div class="form-group"> <label class="col-md-3 control-label">学校</label> <div class="col-md-8"> <input class="form-control"  type="text" placeholder="请输入学校" name="bean.school" value="${bean.school}"> <span class=help-block"></span> </div> </div>
<div class="form-group"> <label class="col-md-3 control-label">家庭住址</label> <div class="col-md-8"> <input class="form-control"  type="text" placeholder="请输入家庭住址" name="bean.home_address" value="${bean.home_address}"> <span class=help-block"></span> </div> </div>
<div class="form-group"> <label class="col-md-3 control-label">邮箱</label> <div class="col-md-8"> <input class="form-control"  type="text" placeholder="请输入邮箱" name="bean.email" value="${bean.email}"> <span class=help-block"></span> </div> </div>
<div class="form-group"> <label class="col-md-3 control-label">学员身份证</label> <div class="col-md-8"> <input class="form-control"  type="text" placeholder="请输入学员身份证" name="bean.card_num" value="${bean.card_num}"> <span class=help-block"></span> </div> </div>
<div class="form-group"> <label class="col-md-3 control-label">接送时间</label> <div class="col-md-8"> <input class="form-control"  type="text" placeholder="请输入接送时间" name="bean.shuttle_time" value="${bean.shuttle_time}"> <span class=help-block"></span> </div> </div>
<div class="form-group"> <label class="col-md-3 control-label">其它</label> <div class="col-md-8"> <input class="form-control"  type="text" placeholder="请输入其它" name="bean.other_note" value="${bean.other_note}"> <span class=help-block"></span> </div> </div>
<div class="form-group"> <label class="col-md-3 control-label">学员/监护人姓名</label> <div class="col-md-8"> <input class="form-control"  type="text" placeholder="请输入学员/监护人姓名" name="bean.guardian" value="${bean.guardian}"> <span class=help-block"></span> </div> </div>
<div class="form-group"> <label class="col-md-3 control-label">游泳技能调查</label> <div class="col-md-8"> <input class="form-control"  type="text" placeholder="请输入游泳技能调查" name="bean.swim_survey" value="${bean.swim_survey}"> <span class=help-block"></span> </div> </div>
<div class="form-group"> <label class="col-md-3 control-label">篮球技能</label> <div class="col-md-8"> <input class="form-control"  type="text" placeholder="请输入篮球技能" name="bean.basketball_skills" value="${bean.basketball_skills}"> <span class=help-block"></span> </div> </div>
<div class="form-group"> <label class="col-md-3 control-label">网球技能</label> <div class="col-md-8"> <input class="form-control"  type="text" placeholder="请输入网球技能" name="bean.tennis_skills" value="${bean.tennis_skills}"> <span class=help-block"></span> </div> </div>
<div class="form-group"> <label class="col-md-3 control-label">羽毛球技能</label> <div class="col-md-8"> <input class="form-control"  type="text" placeholder="请输入羽毛球技能" name="bean.badminton_skills" value="${bean.badminton_skills}"> <span class=help-block"></span> </div> </div>
<div class="form-group"> <label class="col-md-3 control-label">空手道技能</label> <div class="col-md-8"> <input class="form-control"  type="text" placeholder="请输入空手道技能" name="bean.karate_skills" value="${bean.karate_skills}"> <span class=help-block"></span> </div> </div>
<div class="form-group"> <label class="col-md-3 control-label">轮滑</label> <div class="col-md-8"> <input class="form-control"  type="text" placeholder="请输入轮滑" name="bean.inline_skaters_skills" value="${bean.inline_skaters_skills}"> <span class=help-block"></span> </div> </div>
<div class="form-group"> <label class="col-md-3 control-label">游泳技能</label> <div class="col-md-8"> <input class="form-control"  type="text" placeholder="请输入游泳技能" name="bean.swim_skills" value="${bean.swim_skills}"> <span class=help-block"></span> </div> </div>
									<div class="c10"></div>
									<div  style="margin-left: 100px;">
									<button type="button" class="btn purple"><i class="fa fa-bullhorn"></i> Feeds</button>
									</div>
									<div class="c10"></div>
								</form>
								<!-- END FORM--> 
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="c10"></div>
		</div>

	</div>
	<div class="c10"></div>
	</div>
	<!-- BEGIN FOOTER -->
	<%@ include file="../include/footer.jsp"%>
	<script type="text/javascript" src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
	<script type="text/javascript" src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/bootstrap-datepicker/js/locales/bootstrap-datepicker.zh-CN.js"></script>
	<script type="text/javascript" src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/select2/select2.min.js"></script>
	<script type="text/javascript" src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/clockface/js/clockface.js"></script>
	<script src="${basePath}/plugins/bootstrap.admin.theme/assets/scripts/app.js"></script>     
	<!-- END FOOTER -->
</body>
</html>
<script type="text/javascript">
jQuery(document).ready(function() {
	$('#course_select2_sample2').select2({
        placeholder: "点击选择课程",
        allowClear: true
    });
	$('#bean_birthday1').datepicker();
	$('#bean_begin_day1').datepicker();
	$('#bean_end_day1').datepicker();
	$('#bean_pay_day1').datepicker();
});
</script>