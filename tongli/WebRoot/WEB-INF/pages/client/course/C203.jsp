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
										<tr class="alert alert-success" >
											<td height="30" colspan="4"><i class="fa  fa-bar-chart-o"></i> <strong>申请人资料</strong></td>
										</tr>
									</table>
										<%-- <div class="form-group">
											<label class="col-md-2 control-label">学员号</label>
											<div class="col-md-8">
												<input class="form-control" type="text" placeholder="请输入学员号"
													name="bean.student_num" value="${bean.student_num}">
												<span class="help-block"></span>
											</div>
										</div> --%>
										<div class="form-group">
											<label class="col-md-2 control-label">姓名</label>
											<div class="col-md-3">
												<input class="form-control" type="text" placeholder="请输入姓名"
													name="bean.name" value="${bean.name}"> <span
													class="help-block"></span>
											</div>
											<label class="col-md-2 control-label">生日</label>
											<div id="bean_birthday1" class="input-group input-medium date date-picker col-md-2" data-date-format="yyyy-mm-dd" data-date-end-date="-730d">
												<input class="form-control" type="text" placeholder="请输入生日" readonly="readonly"
													name="bean.birthday" value="${bean.birthday}">
													 <span class="input-group-btn">
													<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
													</span>
													<span class="help-block"></span>
											</div>
											
										</div>
										<div class="form-group">
											<label class="col-md-2 control-label"
												style="margin-top: -8px;">性别</label>
											<div class="col-md-8">
												<label><input type="radio" name="bean.sex" value="0">
													男 </label>&nbsp; &nbsp; <label><input type="radio"
													name="bean.sex" value="1"> 女 </label> <span
													class="help-block"></span>
											</div>
										</div>
										<div class="form-group">
											<label class="col-md-2 control-label">移动电话</label>
											<div class="col-md-3">
												<input class="form-control" type="text"
													placeholder="请输入移动电话" name="bean.cell_tel"
													value="${bean.cell_tel}"> <span class="help-block"></span>
											</div>
											<label class="col-md-2 control-label">家庭电话</label>
											<div class="col-md-3">
												<input class="form-control" type="text"
													placeholder="请输入家庭电话" name="bean.tel" value="${bean.tel}">
												<span class="help-block"></span>
											</div>
										</div>
										<div class="form-group">
											<label class="col-md-2 control-label">家庭住址</label>
											<div class="col-md-8">
												<input class="form-control" type="text"
													placeholder="请输入家庭住址" name="bean.home_address"
													value="${bean.home_address}"> <span
													class="help-block"></span>
											</div>
										</div>
										<table class="table table-striped table-condensed">
											<tr class="alert alert-success" >
												<td height="30" colspan="4"><i class="fa  fa-bar-chart-o"></i> <strong>详情</strong></td>
											</tr>
										</table>
										<div class="form-group">
											<label class="col-md-3 control-label" style="margin-top: -8px;">是否接受过任何教学课程</label>
											<div class="col-md-8">
													<label><input type="radio" name="bean.swim_survey" value="0"> 是(曾参加过学习/培训) </label>
													<label><input type="radio" name="bean.swim_survey" value="1"> 否(从未接触过学习/培训) </label>
													<span class="help-block"></span>
											</div>
										</div>
										<div class="form-group"> <label class="col-md-2 control-label" style="margin-top: -8px;">技能</label> <div class="col-md-8"> 
											<label><input type="radio" name="bean.swim_skills" value="0"> 初级 </label>
											<label><input type="radio" name="bean.swim_skills" value="1"> 中级 </label>
											<label><input type="radio" name="bean.swim_skills" value="2"> 专业 </label>
											<span class="help-block"></span> </div> 
										</div>
										<div class="form-group">
											 <label class="col-md-2 control-label">培训课程</label>
											 <div class="col-md-8">
												 <div class="input-group">
												 	<span class="input-group-addon">
													<i class="fa fa-flag"></i>
													</span>
													<select name="bean.course" id="course_select2_sample2" onchange="sumPrice(this)" class="form-control select2 select2me" data-placeholder="选择课程.." multiple>
														<optgroup label="课程列表">
														<c:forEach items="${course_beans}" var="course_bean">
															<option value="${course_bean.title}"  price="${course_bean.market_price}">${course_bean.title}</option>
														</c:forEach>
														</optgroup>
													</select>
												</div>
											</div>
										</div>
										<div class="form-group">
											<label class="col-md-2 control-label">学员/监护人姓名</label>
											<div class="col-md-8">
												<input class="form-control" type="text"
													placeholder="请输入学员/监护人姓名" name="bean.guardian"
													value="${bean.guardian}"> <span class="help-block"></span>
											</div>
										</div>
										<div class="form-group">
											<label class="col-md-2 control-label">学费</label>
											<div class="col-md-3">
												<input class="form-control" type="number"
													placeholder="请输入报名价格" id="bean_price_1" name="bean.price" readonly="readonly"
													value="0.00"> <span class="help-block"></span>
											</div>
										</div>
										<div class="form-group">
											<label class="col-md-2 control-label">期望上课时间</label>
											<div class="col-md-8">
												<input class="form-control" type="text"
													placeholder="请输期望上课时间" name="bean.note"
													value="${bean.note}"> <span
													class="help-block"></span>
											</div>
										</div>
											<%-- <label class="col-md-2 control-label">经办人</label>
											<div class="col-md-3">
												<input class="form-control" type="text" placeholder="请输入经办人"
													name="bean.agent" value="${bean.agent}"> <span
													class="help-block"></span>
											</div> --%>
										
										<%-- <div class="form-group">
											<label class="col-md-2 control-label">缴费日期</label>
											<div id="bean_pay_day1" class="input-group input-medium date date-picker col-md-2" data-date-format="yyyy-mm-dd" data-date-start-date="-50d">
												<input class="form-control" type="text"
													placeholder="请输入缴费日期" name="bean.pay_day" readonly="readonly"
													value="${bean.pay_day}"> 
													<span class="input-group-btn">
													<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
													</span>
													<span class="help-block"></span>
											</div>
										</div> --%>
										<%-- <div class="form-group">
											<label class="col-md-2 control-label">课程开始时间</label>
											<div id="bean_begin_day1" class="input-group input-medium date date-picker col-md-2" data-date-format="yyyy-mm-dd" data-date-start-date="+0d">
												<input class="form-control" type="text"
													placeholder="请输入课程开始时间" name="bean.begin_day" readonly="readonly"
													value="${bean.begin_day}"> 
													<span class="input-group-btn">
													<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
													</span>
													<span class="help-block"></span>
											</div>
											<label class="col-md-2 control-label">课程结束时间</label>
											<div id="bean_end_day1" class="input-group input-medium date date-picker col-md-2" data-date-format="yyyy-mm-dd" data-date-start-date="+0d">
												<input class="form-control" type="text"
													placeholder="请输入课程结束时间" name="bean.end_day" readonly="readonly"
													value="${bean.end_day}"> 
													<span class="input-group-btn">
													<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
													</span>
													<span class="help-block"></span>
											</div>
										</div> --%>
										<div class="panel panel-warning">
											<div class="panel-heading ">
												<div class="panel-title" style="color: #468847;"><i class="fa fa-bar-chart-o"></i> <strong>学员须知</strong></div>
											</div>
											<div class="panel-body">
												<div class="note note-danger">
													<h4 class="block">一.补课事宜</h4>
													<p>
														1.病假及事假必须事前申请及先交回证明文件，否则本校不会接纳补课申请。
													</p>
													<p>
														2.每10课时课程最多2次请假。以此类推。
													</p>
													<p>
														3.超出请假课程时，视自动放弃，不得补课。
													</p>
													<p>
														4.未安排之补课，不可作为学费扣减。
													</p>
													<p>
														5.已安排之补课在任何情况下都不能更改时间，缺席补课者当自动弃权论。
													</p>
												</div>
												<div class="note note-warning">
													<h4 class="block">二.其他事宜</h4>
													<p>
														1.所有报班，于开班后不得转让。如自动退学，所有已缴之费用，概不退还。
													</p>
													<p>
														2.除有关课程取消外，已缴学费，概不退还。
													</p>
													<p>
														3.4——12岁学员须家长陪同进入俱乐部。
													</p>
													<p>
														4.为使学员集中注意力，家长请在教室外观课等候
													</p>
													<p>
														5.学员所报课程以十课时为单位，须在一季度内学完，二十课时为半年，以此类推。
													</p>
												</div>
											</div>
										</div>
										<div class="c10"></div>
										<div class="alert alert-danger">
											<label><input type="checkbox" checked="checked" style="margin-top: -1px;" onclick="if(!!$(this).prop('checked')){$('#btn1_1111').show();}else{$('#btn1_1111').hide()}"> 本人承诺遵守俱乐部规章制度以及以上学员行为准则.</label> 
										</div>
										<div class="c10"></div>
									<div align="center">
									<button id="btn1_1111" type="submit"  class="btn green">提交报名申请表</button>
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
									<table class="table table-striped table-condensed">
										<tr class="alert alert-danger" >
											<td height="30" colspan="4"><i class="fa  fa-bullhorn"></i> <strong>报名信息</strong></td>
										</tr>
									</table>
									<div class="form-group">
										<label class="col-md-2 control-label">(第几期)</label>
										<div class="col-md-3" style="margin-top:5px;" id="bean_code_2" >
											<label> <input type="checkbox" name="bean.code"  onclick="sumPriceB()" price="5000.00" value="1"> 第一期</label>
											<label> <input type="checkbox" name="bean.code"  onclick="sumPriceB()" price="4000.00" value="2"> 第二期</label>
										</div>
										<label class="col-md-2 control-label">报名价格</label>
										<div class="col-md-3">
											<input class="form-control" type="number" readonly="readonly" id="bean_price_2" placeholder="请输入报名价格"
												name="bean.price" value="0.00"> 
												<span class="help-block"></span>
										</div>
									</div>
									<%-- <div class="form-group">
										<label class="col-md-2 control-label">课程开始时间</label>
										<div  id="bean_begin_day2" class="input-group input-medium date date-picker col-md-2" data-date-format="yyyy-mm-dd" data-date-start-date="+0d">
											<input class="form-control" type="text" readonly="readonly"
												placeholder="请输入课程开始时间" name="bean.begin_day"
												value="${bean.begin_day}"> 
												<span class="input-group-btn">
													<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
												</span>
												<span class="help-block"></span>
										</div>
										<label class="col-md-2 control-label">课程结束时间</label>
										<div  id="bean_end_day2" class="input-group input-medium date date-picker col-md-2" data-date-format="yyyy-mm-dd" data-date-start-date="+0d">
											<input class="form-control" type="text" readonly="readonly"
												placeholder="请输入课程结束时间" name="bean.end_day"
												value="${bean.end_day}">  
												<span class="input-group-btn">
													<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
												</span>
												<span class="help-block"></span>
										</div>
									</div> --%>
									<div class="form-group">
											<label class="col-md-2 control-label">报名地址</label>
											<div class="col-md-8">
												<input class="form-control" type="text"
													placeholder="请输报名地址" name="bean.addres"
													value="${bean.addres}"> <span
													class="help-block"></span>
											</div>
									</div>
									<table class="table table-striped table-condensed">
										<tr class="alert alert-danger" >
											<td height="30" colspan="4"><i class="fa  fa-bullhorn"></i> <strong>学员基本信息</strong></td>
										</tr>
									</table>
									<div class="form-group">
										<label class="col-md-2 control-label">姓名</label>
										<div class="col-md-3">
											<input class="form-control" type="text" placeholder="请输入姓名"
												name="bean.name" value="${bean.name}"> <span
												class="help-block"></span>
										</div>
										<label class="col-md-2 control-label"
											style="margin-top: -8px;">性别</label>
										<div class="col-md-3">
											<label><input type="radio" name="bean.sex" value="0">
												男 </label>&nbsp; &nbsp; <label><input type="radio"
												name="bean.sex" value="1"> 女 </label> <span
												class="help-block"></span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-2 control-label">国籍</label>
										<div class="col-md-3">
											<input class="form-control" type="text" placeholder="请输入国籍"
												name="bean.nationality" value="${bean.nationality}">
											<span class="help-block"></span>
										</div>
										<label class="col-md-2 control-label">生日</label>
											<div id="bean_birthday2" class="input-group input-medium date date-picker col-md-2" data-date-format="yyyy-mm-dd" data-date-end-date="-730d">
												<input class="form-control" type="text" placeholder="请输入生日" readonly="readonly"
													name="bean.birthday" value="${bean.birthday}">
													 <span class="input-group-btn">
													<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
													</span>
													<span class="help-block"></span>
											</div>
									</div>
									<div class="form-group">
										<label class="col-md-2 control-label">学校</label>
										<div class="col-md-3">
											<input class="form-control" type="text" placeholder="请输入学校"
												name="bean.school" value="${bean.school}"> <span
												class="help-block"></span>
										</div>
										<label class="col-md-2 control-label">学员身份证</label>
										<div class="col-md-3">
											<input class="form-control" type="text"
												placeholder="请输入学员身份证" name="bean.card_num"
												value="${bean.card_num}"> <span class="help-block"></span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-2 control-label">邮箱</label>
										<div class="col-md-3">
											<input class="form-control" type="text" placeholder="请输入邮箱"
												name="bean.email" value="${bean.email}"> <span
												class="help-block"></span>
										</div>
										<label class="col-md-2 control-label">移动电话</label>
										<div class="col-md-3">
											<input class="form-control" type="text" placeholder="请输入移动电话"
												name="bean.cell_tel" value="${bean.cell_tel}"> <span
												class="help-block"></span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-2 control-label">家庭住址</label>
										<div class="col-md-8">
											<input class="form-control" type="text" placeholder="请输入家庭住址"
												name="bean.home_address" value="${bean.home_address}">
											<span class="help-block"></span>
										</div>
									</div>
									<table class="table table-striped table-condensed">
										<tr class="alert alert-danger" >
											<td height="30" colspan="4"><i class="fa  fa-bullhorn"></i> <strong>学员技能信息</strong></td>
										</tr>
									</table>
									<div class="form-group">
										<label class="col-md-2 control-label" style="margin-top: -8px;">篮球</label>
										<div class="col-md-8">
												<label><input type="radio" name="bean.basketball_skills" value="0"> 初级 </label>
												<label><input type="radio" name="bean.basketball_skills" value="1"> 中级 </label>
												<label><input type="radio" name="bean.basketball_skills" value="2"> 专业 </label>
												<span class="help-block"></span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-2 control-label" style="margin-top: -8px;">网球</label>
										<div class="col-md-8">
											<label><input type="radio" name="bean.tennis_skills" value="0"> 初级 </label>
											<label><input type="radio" name="bean.tennis_skills" value="1"> 中级 </label>
											<label><input type="radio" name="bean.tennis_skills" value="2"> 专业 </label>
											<span class="help-block"></span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-2 control-label" style="margin-top: -8px;">羽毛球</label>
										<div class="col-md-8">
												<label><input type="radio" name="bean.badminton_skills" value="0"> 初级 </label>
												<label><input type="radio" name="bean.badminton_skills" value="1"> 中级 </label>
												<label><input type="radio" name="bean.badminton_skills" value="2"> 专业 </label>
												 <span class="help-block"></span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-2 control-label" style="margin-top: -8px;">空手道</label>
										<div class="col-md-8">
											<label><input type="radio" name="bean.karate_skills" value="0"> 初级 </label>
											<label><input type="radio" name="bean.karate_skills" value="1"> 中级 </label>
											<label><input type="radio" name="bean.karate_skills" value="2"> 专业 </label>
											<span class="help-block"></span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-2 control-label" style="margin-top: -8px;">轮滑</label>
										<div class="col-md-8">
											<label><input type="radio" name="bean.inline_skaters_skills" value="0"> 初级 </label>
											<label><input type="radio" name="bean.inline_skaters_skills" value="1"> 中级 </label>
											<label><input type="radio" name="bean.inline_skaters_skills" value="2"> 专业 </label>
											<span class="help-block"></span>
										</div>
									</div>
									<div class="form-group"> <label class="col-md-2 control-label" style="margin-top: -8px;">游泳</label> 
										<div class="col-md-8"> 
											<label><input type="radio" name="bean.swim_skills" value="0"> 初级 </label>
											<label><input type="radio" name="bean.swim_skills" value="1"> 中级 </label>
											<label><input type="radio" name="bean.swim_skills" value="2"> 专业 </label>
											<span class="help-block"></span> 
										</div> 
									</div>
									<table class="table table-striped table-condensed">
										<tr class="alert alert-danger" >
											<td height="30" colspan="4"><i class="fa  fa-bullhorn"></i> <strong>备注</strong></td>
										</tr>
									</table>
									<%-- <div class="form-group">
										<label class="col-md-2 control-label">接送时间</label>
										<div class="col-md-8">
											<input class="form-control" type="text" placeholder="请输入接送时间"
												name="bean.shuttle_time" value="${bean.shuttle_time}">
											<span class="help-block"></span>
										</div>
									</div> --%>
									<div class="form-group">
										<label class="col-md-2 control-label">是否重大疾病</label>
										<div class="col-md-8">
											<input class="form-control" type="text" placeholder="请输入是否有重大疾病"
												name="bean.disease_note" value="${bean.disease_note}"> <span
												class="help-block"></span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-2 control-label">是否食物过敏</label>
										<div class="col-md-8">
											<input class="form-control" type="text" placeholder="请输入是否有食物过敏"
												name="bean.allergy_note" value="${bean.allergy_note}"> <span
												class="help-block"></span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-2 control-label">其它</label>
										<div class="col-md-8">
											<input class="form-control" type="text" placeholder="请输入其它"
												name="bean.other_note" value="${bean.other_note}"> <span
												class="help-block"></span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-2 control-label">学员/监护人姓名</label>
										<div class="col-md-8">
											<input class="form-control" type="text"
												placeholder="请输入学员/监护人姓名" name="bean.guardian"
												value="${bean.guardian}"> <span class="help-block"></span>
										</div>
									</div>
									<div class="panel panel-warning">
											<div class="panel-heading ">
												<div class="panel-title" style="color: #B94A48;"><i class="fa fa-bar-chart-o"></i> <strong>报名须知</strong></div>
											</div>
											<div class="panel-body">
												<div class="note note-success">
													<p>
														  1.报名者应身心健康且符合报名年龄（5——12岁），报名时请出示家长（监护人）有效身份证件。
													</p>
													<p>
														  2.学员家长（监护人）应配合和尊重教练、老师及工作人员的管理，并引导孩子也这么做。
													</p>
													<p>
														  3.为了孩子安全，如需提前接送孩子必须事先向俱乐部申请。接送孩子需出示本人接送卡，并在
														    接送表上签字。如有委托人持卡接送，当事人必须先与俱乐部老师联系做确认，方可由委托人
														    签字接走孩子。
													</p>
													<p>
														  4.在夏令营上课期间俱乐部将保证学员安全的基础上合理安排学员的冬夏令营日常活动。所有
														    学员必须遵守俱乐部设施使用规定，并在教练或老师指导下合理使用。擅自或盲目使用器械和
														    游泳，所造成的一切不利后果由学员及其家长(监护人)自行承担，本俱乐部不承担法律责任。
													</p>
													<p>
														  5.我们将给每位学员购买保险。学员及其家长（监护人）应认识到所参加的运动本身可能造成的
														    伤害。
												   </p>
													<p>
														  6.每期课程不得无故旷课，请学员家长务必安排好假期的时间，避免影响学员的正常课程活动。
														    如遇到病假及事假必须事前提交申请及相关证明。已支付本俱乐部的相关费用，因非本俱乐部
														    所造成的任何原因，一律不予退还。
													</p>
													<p>
														  7.取消资格：故意损坏俱乐部设施及影响到其他学员安全，扰乱课堂秩序并沟通无效的，我们将
														    取消学员的参加资格，同时扣除相应的费用。
													</p>
													<p>
														  8.冬夏令营报名缴费即合同生效，学员资格不得转让。
													</p>
													<p>
														  9.家长（监护人）的签名意味同意同时遵守本报名须知。非常感谢您对童励俱乐部的配合和支持。
													</p>
												</div>
											</div>
										</div>
									<div class="alert alert-danger">
											<label><input type="checkbox" checked="checked" style="margin-top: -1px;" onclick="if(!!$(this).prop('checked')){$('#btn2_222').show();}else{$('#btn2_222').hide()}"> 本人承诺遵守俱乐部规章制度以及以上学员行为准则.</label> 
										</div>
									<div class="c10"></div>
									<div align="center">
									<button type="submit" id="btn2_222" class="btn purple">提交冬夏令营报名表</button>
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
	//$('#bean_begin_day1').datepicker();
	//$('#bean_end_day1').datepicker();
	//$('#bean_pay_day1').datepicker();
	
	$('#bean_birthday2').datepicker();
	//$('#bean_begin_day2').datepicker();
	//$('#bean_end_day2').datepicker();
});
function sumPrice(obj){
	var sum=0.00;
	$(obj).find("option:selected").each(function(){
		sum=sum+Number(($(this).attr('price')),10);
	});
	$('#bean_price_1').val(sum);
}
function sumPrice(obj){
	var sum=0.00;
	$(obj).find("option:selected").each(function(){
		sum=sum+Number(($(this).attr('price')),10);
	});
	$('#bean_price_1').val(sum);
}
function sumPriceB(){
	var sum=0.00;
	
	var codeLength1=$('#bean_code_2').find("[name='bean.code']").length;
	var codeLength2=0;
	$('#bean_code_2').find("[name='bean.code']:checked").each(function(){
		sum=sum+Number(($(this).attr('price')),10);
		codeLength2++;
	});
	if(codeLength1==codeLength2){
		sum=8000;
	}
	$('#bean_price_2').val(sum);
}
</script>