<%--
/*
 * 系统管理_在线报名_编辑 (页面)
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-04-07  wuxiaogang   程序・发布
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
<!DOCTYPE html>
<html lang="zh-CN" class="no-js">
<head>
<meta charset="utf-8" />
<%@include file="../include/admin_title.jsp" %>
<%@ include file="../include/public_js_css.jsp"%>
<link href="${basePath}/css/messages.css" media="all" rel="stylesheet" type="text/css" />
<link href="${basePath}/css/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${basePath}/css/font-awesome/css/font-awesome-ie7.css" rel="stylesheet">

<link rel="stylesheet" type="text/css" href="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/bootstrap-datepicker/css/datepicker.css" />
<link rel="stylesheet" type="text/css" href="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/bootstrap-datepicker/less/datepicker.less" />

<link rel="stylesheet" type="text/css" href="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/jquery-multi-select/css/multi-select.css" />

<link rel="stylesheet" type="text/css" href="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/clockface/css/clockface.css" />
</head>
<!-- BEGIN BODY -->
<body class="page-header-fixed">
	<!-- BEGIN HEADER -->
	<%@ include file="../include/header.jsp"%>
	<!-- END HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
	<div class="page-container">
		<!-- BEGIN SIDEBAR -->
		<%@ include file="../include/leftMenu.jsp"%>
		<script type="text/javascript">
			jQuery(document).ready(function() {
				$('#sys7,#sys7_sub_menu_l1').addClass('active');
				$('#sys7_arrow').addClass('open');
				$('#sys7_sub_menu').show();
			});
		</script>
		<!-- END SIDEBAR -->
		<!-- BEGIN PAGE -->
		<div class="page-content">
			<!-- BEGIN STYLE CUSTOMIZER -->
			<%@ include file="../include/style_customizer.jsp"%>
			<!-- END BEGIN STYLE CUSTOMIZER -->
			<!-- BEGIN PAGE HEADER-->
			<div class="row">
				<div class="col-md-12">
					<!-- BEGIN PAGE TITLE & BREADCRUMB-->
					<h3 class="page-title">
						在线报名 <small><span class="help-inline">信息</span></small>
					</h3>
					<ul class="page-breadcrumb breadcrumb">
						<li><i class="fa fa-home"></i> <a
							href="${basePath }/home_init.ac">Home</a> <i
							class="fa fa-angle-right"></i></li>
						<li><a href="#">系统管理</a> <i class="fa fa-angle-right"></i></li>
						<li><a href="${basePath}/h/c104_init.ac">在线报名</a> <i class="fa fa-angle-right"></i></li>
						<li>详情</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			<!-- END PAGE HEADER-->
			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
				<div class="col-md-12">
					<form accept-charset="UTF-8"  action="${basePath}/h/c104_save.ac" class="edit_article" id="edit_article_13632" method="post">
						<s:token></s:token>
						<div class="well form-inline">
							<label>
							<input name="bean.status" value="0"
							<c:if test="${bean.status=='0'}">
							 	checked="checked"
							 </c:if>
							 type="radio">
							 未处理</label>
							 <label>
							<input name="bean.status" value="1"
							<c:if test="${bean.status=='1'}">
							 	checked="checked"
							 </c:if>
							 type="radio">
							 报名失败</label>
							 <label>
							<input name="bean.status" value="2"
							<c:if test="${bean.status=='2'}">
							 	checked="checked"
							 </c:if>
							 type="radio">
							 报名成功</label>
						</div>
						<input name="bean.id" type="hidden" value="${bean.id}">
						<div class="form-horizontal" >
						<c:if test="${bean.type==0}">
								
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
										<div class="form-group">
											<label class="col-md-2 control-label">学员号</label>
											<div class="col-md-8">
												<input class="form-control" type="text" 
													name="bean.student_num" value="${bean.student_num}">
												<span class="help-block"></span>
											</div>
										</div>
										<div class="form-group">
											<label class="col-md-2 control-label">姓名</label>
											<div class="col-md-3">
												<input class="form-control" type="text"  readonly="readonly" disabled="disabled"
													name="bean.name" value="${bean.name}"> <span
													class="help-block"></span>
											</div>
											<label class="col-md-2 control-label">生日</label>
											<div id="bean_birthday1" class="input-group input-medium date date-picker col-md-2" data-date-format="yyyy-mm-dd" data-date-end-date="-730d">
												<input class="form-control" type="text"   readonly="readonly" disabled="disabled"
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
												<label><input type="radio" readonly="readonly" disabled="disabled" name="bean.sex" <c:if test="${bean.sex==0}"> checked="checked"</c:if> value="0">
													男 </label>&nbsp; &nbsp; <label><input type="radio" name="bean.sex" readonly="readonly" disabled="disabled"
													<c:if test="${bean.sex==1}"> checked="checked"</c:if> value="1"> 女 </label> <span
													class="help-block"></span>
											</div>
										</div>
										<div class="form-group">
											<label class="col-md-2 control-label">移动电话</label>
											<div class="col-md-3">
												<input class="form-control" type="text"
													 name="bean.cell_tel" readonly="readonly" disabled="disabled"
													value="${bean.cell_tel}"> <span class="help-block"></span>
											</div>
											<label class="col-md-2 control-label">家庭电话</label>
											<div class="col-md-3">
												<input class="form-control" type="text" readonly="readonly" disabled="disabled"
													 name="bean.tel" value="${bean.tel}">
												<span class="help-block"></span>
											</div>
										</div>
										<div class="form-group">
											<label class="col-md-2 control-label">家庭住址</label>
											<div class="col-md-8">
												<input class="form-control" type="text" readonly="readonly" disabled="disabled"
													 name="bean.home_address"
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
													<label><input type="radio" readonly="readonly" disabled="disabled" name="bean.swim_survey" <c:if test="${bean.swim_survey==0}"> checked="checked"</c:if> value="0"> 是(曾参加过学习/培训) </label>
													<label><input type="radio" readonly="readonly" disabled="disabled" name="bean.swim_survey" <c:if test="${bean.swim_survey==1}"> checked="checked"</c:if> value="1"> 否(从未接触过学习/培训) </label>
													<span class="help-block"></span>
											</div>
										</div>
										<div class="form-group"> <label class="col-md-2 control-label" style="margin-top: -8px;">技能</label> <div class="col-md-8"> 
											<label><input type="radio"  readonly="readonly" disabled="disabled" name="bean.swim_skills" <c:if test="${bean.swim_skills==0}"> checked="checked"</c:if> value="0"> 初级 </label>
											<label><input type="radio" readonly="readonly" disabled="disabled" name="bean.swim_skills" <c:if test="${bean.swim_skills==1}"> checked="checked"</c:if> value="1"> 中级 </label>
											<label><input type="radio" readonly="readonly" disabled="disabled" name="bean.swim_skills" <c:if test="${bean.swim_skills==2}"> checked="checked"</c:if> value="2"> 专业 </label>
											<span class="help-block"></span> </div> 
										</div>
										<div class="form-group">
											 <label class="col-md-2 control-label">培训课程</label>
											 <div class="col-md-8">
													<input class="form-control" type="text" readonly="readonly" disabled="disabled"
														value="${course_beans}">
											</div>
										</div>
										<table class="table table-striped table-condensed">
											<tr class="alert alert-success" >
												<td height="30" colspan="4"><i class="fa  fa-bar-chart-o"></i> <strong>报名信息</strong></td>
											</tr>
										</table>
										<div class="form-group">
											<label class="col-md-2 control-label">学员/监护人姓名</label>
											<div class="col-md-8">
												<input class="form-control" type="text" readonly="readonly" disabled="disabled"
													 name="bean.guardian"
													value="${bean.guardian}"> <span class="help-block"></span>
											</div>
										</div>
										<div class="form-group">
											<label class="col-md-2 control-label">学费</label>
											<div class="col-md-3">
												<input class="form-control" type="number" readonly="readonly" disabled="disabled"
													 name="bean.price"
													value="${bean.price}"> <span class="help-block"></span>
											</div>
											<label class="col-md-2 control-label">经办人</label>
											<div class="col-md-3">
												<input class="form-control" type="text" 
													name="bean.agent" value="${bean.agent}"> <span
													class="help-block"></span>
											</div>
										</div>
										<div class="form-group">
											<label class="col-md-2 control-label">缴费日期</label>
											<div id="bean_pay_day1" class="input-group input-medium date date-picker col-md-2" data-date-format="yyyy-mm-dd" data-date-start-date="-50d">
												<input class="form-control" type="text"
													 name="bean.pay_day"  readonly="readonly" disabled="disabled"
													value="${bean.pay_day}"> 
													<span class="input-group-btn">
													<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
													</span>
													<span class="help-block"></span>
											</div>
										</div>
										<%-- <div class="form-group">
											<label class="col-md-2 control-label">课程开始时间</label>
											<div id="bean_begin_day1" class="input-group input-medium date date-picker col-md-2" data-date-format="yyyy-mm-dd" data-date-start-date="+0d">
												<input class="form-control" type="text"
													placeholder="请输入课程开始时间" name="bean.begin_day"  readonly="readonly" disabled="disabled"
													value="${bean.begin_day}"> 
													<span class="input-group-btn">
													<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
													</span>
													<span class="help-block"></span>
											</div>
											<label class="col-md-2 control-label">课程结束时间</label>
											<div id="bean_end_day1" class="input-group input-medium date date-picker col-md-2" data-date-format="yyyy-mm-dd" data-date-start-date="+0d">
												<input class="form-control" type="text"
													placeholder="请输入课程结束时间" name="bean.end_day"  readonly="readonly" disabled="disabled"
													value="${bean.end_day}"> 
													<span class="input-group-btn">
													<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
													</span>
													<span class="help-block"></span>
											</div> --%>
										<div class="form-group">
											<label class="col-md-2 control-label">期望上课时间</label>
											<div class="col-md-8">
												<input class="form-control" type="text" readonly="readonly" disabled="disabled"
													 name="bean.note"
													value="${bean.note}"> <span class="help-block"></span>
											</div>
										</div>
									</c:if>
									<c:if test="${bean.type==1}">
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
												<div class="col-md-3">
													<input class="form-control" type="text"
														 name="bean.code" readonly="readonly" disabled="disabled"
														value="${bean.code}"> <span class="help-block"></span>
												</div>
												<label class="col-md-2 control-label">报名价格</label>
												<div class="col-md-3">
													<input class="form-control" type="number"  readonly="readonly" disabled="disabled"
														name="bean.price" value="${bean.price}"> 
														<span class="help-block"></span>
												</div>
											</div>
											<%-- <div class="form-group">
												<label class="col-md-2 control-label">课程开始时间</label>
												<div  id="bean_begin_day2" class="input-group input-medium date date-picker col-md-2" data-date-format="yyyy-mm-dd" data-date-start-date="+0d">
													<input class="form-control" type="text"  readonly="readonly" disabled="disabled"
														placeholder="请输入课程开始时间" name="bean.begin_day"
														value="${bean.begin_day}"> 
														<span class="input-group-btn">
															<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
														</span>
														<span class="help-block"></span>
												</div>
												<label class="col-md-2 control-label">课程结束时间</label>
												<div  id="bean_end_day2" class="input-group input-medium date date-picker col-md-2" data-date-format="yyyy-mm-dd" data-date-start-date="+0d">
													<input class="form-control" type="text"  readonly="readonly" disabled="disabled"
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
														<input class="form-control" type="text" readonly="readonly" disabled="disabled"
															 name="bean.addres"
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
													<input class="form-control" type="text"  readonly="readonly" disabled="disabled"
														name="bean.name" value="${bean.name}"> <span
														class="help-block"></span>
												</div>
												<label class="col-md-2 control-label"
													style="margin-top: -8px;">性别</label>
												<div class="col-md-3">
													<label><input type="radio" readonly="readonly" disabled="disabled" <c:if test="${bean.sex==0}"> checked="checked"</c:if> name="bean.sex" value="0">
														男 </label>&nbsp; &nbsp; <label><input type="radio" readonly="readonly" disabled="disabled"<c:if test="${bean.sex==1}"> checked="checked"</c:if> 
														name="bean.sex" value="1"> 女 </label> <span
														class="help-block"></span>
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-2 control-label">国籍</label>
												<div class="col-md-3">
													<input class="form-control" type="text"  readonly="readonly" disabled="disabled"
														name="bean.nationality" value="${bean.nationality}">
													<span class="help-block"></span>
												</div>
												<label class="col-md-2 control-label">生日</label>
													<div id="bean_birthday2" class="input-group input-medium date date-picker col-md-2" data-date-format="yyyy-mm-dd" data-date-end-date="-730d">
														<input class="form-control" type="text"   readonly="readonly" disabled="disabled"
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
													<input class="form-control" type="text"  readonly="readonly" disabled="disabled"
														name="bean.school" value="${bean.school}"> <span
														class="help-block"></span>
												</div>
												<label class="col-md-2 control-label">学员身份证</label>
												<div class="col-md-3">
													<input class="form-control" type="text"
														 name="bean.card_num"
														value="${bean.card_num}"> <span class="help-block"></span>
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-2 control-label">邮箱</label>
												<div class="col-md-3">
													<input class="form-control" type="text"  readonly="readonly" disabled="disabled"
														name="bean.email" value="${bean.email}"> <span
														class="help-block"></span>
												</div>
												<label class="col-md-2 control-label">移动电话</label>
												<div class="col-md-3">
													<input class="form-control" type="text"  readonly="readonly" disabled="disabled"
														name="bean.cell_tel" value="${bean.cell_tel}"> <span
														class="help-block"></span>
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-2 control-label">家庭住址</label>
												<div class="col-md-8">
													<input class="form-control" type="text"  readonly="readonly" disabled="disabled"
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
														<label><input type="radio" name="bean.basketball_skills" <c:if test="${bean.basketball_skills==0}"> checked="checked"</c:if>  readonly="readonly" disabled="disabled" value="0"> 初级 </label>
														<label><input type="radio" name="bean.basketball_skills" <c:if test="${bean.basketball_skills==1}"> checked="checked"</c:if>  readonly="readonly" disabled="disabled" value="1"> 中级 </label>
														<label><input type="radio" name="bean.basketball_skills" <c:if test="${bean.basketball_skills==2}"> checked="checked"</c:if>  readonly="readonly" disabled="disabled" value="2"> 专业 </label>
														<span class="help-block"></span>
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-2 control-label" style="margin-top: -8px;">网球</label>
												<div class="col-md-8">
													<label><input type="radio" name="bean.tennis_skills" readonly="readonly" disabled="disabled" <c:if test="${bean.tennis_skills==0}"> checked="checked"</c:if> value="0"> 初级 </label>
													<label><input type="radio" name="bean.tennis_skills" readonly="readonly" disabled="disabled" <c:if test="${bean.tennis_skills==1}"> checked="checked"</c:if> value="1"> 中级 </label>
													<label><input type="radio" name="bean.tennis_skills" readonly="readonly" disabled="disabled" <c:if test="${bean.tennis_skills==2}"> checked="checked"</c:if> value="2"> 专业 </label>
													<span class="help-block"></span>
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-2 control-label" style="margin-top: -8px;">羽毛球</label>
												<div class="col-md-8">
														<label><input type="radio" name="bean.badminton_skills" readonly="readonly" disabled="disabled" <c:if test="${bean.badminton_skills==0}"> checked="checked"</c:if> value="0"> 初级 </label>
														<label><input type="radio" name="bean.badminton_skills" readonly="readonly" disabled="disabled" <c:if test="${bean.badminton_skills==1}"> checked="checked"</c:if> value="1"> 中级 </label>
														<label><input type="radio" name="bean.badminton_skills" readonly="readonly" disabled="disabled" <c:if test="${bean.badminton_skills==2}"> checked="checked"</c:if> value="2"> 专业 </label>
														 <span class="help-block"></span>
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-2 control-label" style="margin-top: -8px;">空手道</label>
												<div class="col-md-8">
													<label><input type="radio" name="bean.karate_skills" readonly="readonly" disabled="disabled" <c:if test="${bean.karate_skills==0}"> checked="checked"</c:if> value="0"> 初级 </label>
													<label><input type="radio" name="bean.karate_skills" readonly="readonly" disabled="disabled" <c:if test="${bean.karate_skills==1}"> checked="checked"</c:if> value="1"> 中级 </label>
													<label><input type="radio" name="bean.karate_skills" readonly="readonly" disabled="disabled" <c:if test="${bean.karate_skills==2}"> checked="checked"</c:if> value="2"> 专业 </label>
													<span class="help-block"></span>
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-2 control-label" style="margin-top: -8px;">轮滑</label>
												<div class="col-md-8">
													<label><input type="radio" name="bean.inline_skaters_skills" readonly="readonly" disabled="disabled" <c:if test="${bean.inline_skaters_skills==0}"> checked="checked"</c:if> value="0"> 初级 </label>
													<label><input type="radio" name="bean.inline_skaters_skills" readonly="readonly" disabled="disabled" <c:if test="${bean.inline_skaters_skills==1}"> checked="checked"</c:if> value="1"> 中级 </label>
													<label><input type="radio" name="bean.inline_skaters_skills" readonly="readonly" disabled="disabled" <c:if test="${bean.inline_skaters_skills==2}"> checked="checked"</c:if> value="2"> 专业 </label>
													<span class="help-block"></span>
												</div>
											</div>
											<div class="form-group"> <label class="col-md-2 control-label" style="margin-top: -8px;">游泳</label> 
												<div class="col-md-8"> 
													<label><input type="radio" name="bean.swim_skills" readonly="readonly" disabled="disabled" <c:if test="${bean.swim_skills==0}"> checked="checked"</c:if> value="0"> 初级 </label>
													<label><input type="radio" name="bean.swim_skills" readonly="readonly" disabled="disabled" <c:if test="${bean.swim_skills==1}"> checked="checked"</c:if> value="1"> 中级 </label>
													<label><input type="radio" name="bean.swim_skills" readonly="readonly" disabled="disabled" <c:if test="${bean.swim_skills==2}"> checked="checked"</c:if> value="2"> 专业 </label>
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
													<input class="form-control" type="text" placeholder="请输入接送时间" readonly="readonly" disabled="disabled"
														name="bean.shuttle_time" value="${bean.shuttle_time}">
													<span class="help-block"></span>
												</div>
											</div> --%>
											<div class="form-group">
												<label class="col-md-2 control-label">是否重大疾病</label>
												<div class="col-md-8">
													<input class="form-control" type="text"  readonly="readonly" disabled="disabled"
														name="bean.disease_note" value="${bean.disease_note}"> <span
														class="help-block"></span>
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-2 control-label">是否食物过敏</label>
												<div class="col-md-8">
													<input class="form-control" type="text"  readonly="readonly" disabled="disabled"
														name="bean.allergy_note" value="${bean.allergy_note}"> <span
														class="help-block"></span>
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-2 control-label">其它</label>
												<div class="col-md-8">
													<input class="form-control" type="text"  readonly="readonly" disabled="disabled"
														name="bean.other_note" value="${bean.other_note}"> <span
														class="help-block"></span>
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-2 control-label">学员/监护人姓名</label>
												<div class="col-md-8">
													<input class="form-control" type="text"
														 name="bean.guardian" readonly="readonly" disabled="disabled"
														value="${bean.guardian}"> <span class="help-block"></span>
												</div>
											</div>
										</div>
									</c:if>
								</div>
						<div class="form-actions">
							<input class="btn btn-primary" name="commit" type="submit"
								value="确定"> | <a
								href="${basePath}/h/c104_init.ac">返回</a>
						</div>
					</form>
				</div>
			</div>
			<!-- END PAGE CONTENT-->
		</div>
		<!-- END PAGE -->
	</div>
	<!-- END CONTAINER -->
	<!-- BEGIN FOOTER -->
	<%@ include file="../include/footer.jsp"%>
	<!-- END FOOTER -->
</body>
<!-- END BODY -->
</html>
