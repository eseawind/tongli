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
<script type="text/javascript" src="${basePath}/js/bxCarousel.js"></script>
<link href="${basePath}/js/bootstarp-date/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen"/>
<script type="text/javascript" src="${basePath}/js/bootstarp-date/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="${basePath}/js/bootstarp-date/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
</head>

<body class="page-header-fixed">
	<!-- BEGIN HEADER -->
	<%@ include file="../include/header.jsp"%>
	<!-- END   HEADER -->
	<%@ include file="../include/slider.jsp"%>

	<div class="main pr">
		<div class="c10"></div>
		<div class="w">

			<div class="body fr" style="width: 770px;">
				<div class="title">&nbsp; 在线报名</div>
				<div class="content" style="min-height: 390px;">

					<div class="user_info">
						<form accept-charset="UTF-8" action="${basePath}/c202_save.ac" method="post">
							<input type="hidden" name="bean.id" value="${bean.id}" />
							<ul>
								<li>
									<div class="tit">参观课程：</div>
									<div class="con">
										<select name="bean.course" class="input">
										<c:forEach items="${course_beans}" var="course_bean">
											<option value="${course_bean.title}">${course_bean.title}</option>
										</c:forEach>
										</select>
										<em>*
												你要参观的课程</em>
									</div>
								</li>
								<li>
									<div class="tit">参观时间：</div>
									<div class="con">
										<input name="bean.day" type="text"  readonly="readonly" class="input date form_date1"> <em>*
												你要参观的日期时间</em>
									</div>
								</li>
								<li>
									<div class="tit">参观场馆：</div>
									<div class="con">
										<input name="bean.addres" type="text" class="input"> <em>*
												你要参观的场馆</em>
									</div>
								</li>
								<li>
									<div class="tit">孩子姓名：</div>
									<div class="con">
										<input type="text" name="bean.name" class="input" value="">
											<em>* 孩子姓名</em>
									</div>
								</li>
								<li>
									<div class="tit">孩子性别：</div>
									<div class="con">
										<input type="radio" name="bean.sex" value="0" id="sex_0">
											男 &nbsp; &nbsp; <input type="radio" name="bean.sex" value="1"
											id="sex_1"> 女 <em>* 请选择</em>
									</div>
								</li>
								<li>
									<div class="tit">孩子年龄：</div>
									<div class="con">
										<input name="bean.age" type="text" class="input"> <em>*
												年龄</em>
									</div>
								</li>
								<li>
									<div class="tit">手机号码：</div>
									<div class="con">
										<input name="bean.tel" type="text" class="input"> <em>*
												联系电话</em>
									</div>
								</li>
								<li class="li_0" style="height: 100px;">
									<div class="tit">其它：</div>
									<div class="con" style="height: 80px;">
										<textarea name="bean.detail_info" cols="5" rows="3"
											style="width: 200px; height: 70px; margin-top: 10px;"></textarea>
										<em> 其它</em>
									</div>
								</li>
							</ul>

							<div class="c10"></div>
							<div  style="margin-left: 100px;">
							<input class="reg_submit" type="submit" value="提 交" />
							</div>
							<div class="c10"></div>
						</form>
					</div>

				</div>
			</div>
			<div class="body fl mt10" style="width: 197px;">
				<div class="title">
					<a href="${basePath}/c202_init.ac" class="ico_recommend">预约参观</a>
				</div>
				<div class="content" style="height: 150px;">
					<a href="${basePath}/c202_init.ac"><img src="images/img4.jpg" width="177" height="150"></a>
				</div>
			</div>

			<div class="body fl mt10" style="width: 197px;">
				<div class="content" style="height: 177px;">
					<img src="images/erweima.jpg" width="177" height="177">
				</div>
			</div>
			<div class="c10"></div>
		</div>

	</div>
	<div class="c10"></div>
	</div>
	<!-- BEGIN FOOTER -->
	<%@ include file="../include/footer.jsp"%>
	<!-- END FOOTER -->
</body>
</html>
<script type="text/javascript">
$('.form_date1').datetimepicker({
    language:  'zh-CN',
	format: "yyyy-mm-dd HH:ii:00",
    weekStart: 1,
    todayBtn:  1,
	autoclose: 1,
	todayHighlight: 1,
	startView: 2,
	minView: 0,
	maxView: 3,
	pickerPosition:"bottom-left"
});
</script>