<%--
/*
 * 课程--预约参观
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-05-18  wuxiaogang        程序・发布
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

<script src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/jquery.blockui.min.js" type="text/javascript"></script>  
<link href="${basePath}/js/bootstarp-date/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen"/>
<script type="text/javascript" src="${basePath}/js/bootstarp-date/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="${basePath}/js/bootstarp-date/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
</head>

<body class="blueBg">
	<!-- BEGIN HEADER -->
	<div class="w640">
	<!-- END   HEADER -->
	<div class="main pr">
		<div class="c10"></div>
		<div class="w">
			<div class="body" >
				<div class="title">&nbsp; 预约参观</div>
				<div class="content">
					<div class="col-xs-12 ">
						<form accept-charset="UTF-8" action="${basePath}/w/c202_save.ac" method="post">
							<input type="hidden" name="bean.id" value="${bean.id}" />
							<ul>
								<li class="form-group">
									<div class="tit">参观课程：</div>
									<div class="con">
										<select name="bean.course" class="form-control" style="border:1px solid #ddd;">
										<c:forEach items="${course_beans}" var="course_bean">
											<option value="${course_bean.title}">${course_bean.title}</option>
										</c:forEach>
										</select>
									</div>
								</li>
								<li class="form-group">
									<div class="tit">参观时间：</div>
									<div class="con">
										<input name="bean.day" type="text"  placeholder="你要参观的日期时间" readonly="readonly" class="date form_date1 form-control"> 
									</div>
								</li>
								<li class="form-group">
									<div class="tit">参观场馆：</div>
									<div class="con">
										<select name="bean.addres" class="form-control" style="border:1px solid #ddd;">
										<option value="绿城总部(浦东锦和路99弄)">绿城总部(浦东锦和路99弄)</option>
										</select>
									</div>
								</li>
								<li class="form-group">
									<div class="tit">孩子姓名：</div>
									<div class="con">
										<input type="text" name="bean.name"  placeholder="孩子姓名" class="form-control" value="">
									</div>
								</li>
								<li class="form-group">
									<div class="tit">孩子性别：
										<input type="radio" name="bean.sex" value="0" id="sex_0">
											男 &nbsp; &nbsp; <input type="radio" name="bean.sex" value="1"
											id="sex_1"> 女 
									</div>
								</li>
								<li class="form-group">
									<div class="tit">孩子年龄：</div>
									<div class="con">
										<input name="bean.age" type="text" placeholder="孩子年龄(数字)"  class="form-control"> 
									</div>
								</li>
								<li class="form-group">
									<div class="tit">手机号码：</div>
									<div class="con">
										<input name="bean.tel" type="text"  placeholder="你的联系电话" class="form-control"> 
									</div>
								</li>
								<li class="li_0" style="height: 100px;">
									<div class="tit">其它：</div>
									<div class="con" style="height: 80px;">
										<textarea name="bean.detail_info"  class="form-control"   placeholder="其它备注信息"
											style="width: 100%; height: 70px; margin-top: 10px;"></textarea>
									</div>
								</li>
							</ul>

							<div class="c10"></div>
							<div  style="margin-left: 100px;">
							<button type="submit" class="btn btn-info" style="margin-left: 40px;" >提  交</button>
							</div>
							<div class="c10"></div>
						</form>
					</div>

				</div>
			</div>
			<div class="c10"></div>
		</div>

	</div>
	<div class="c10"></div>
	<!-- BEGIN FOOTER -->
	<%@ include file="../include/footer.jsp"%>
	<!-- END FOOTER -->
	</div>
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
	startDate:'${startDate} 07:00:00',
	pickerPosition:"bottom-left"
});
</script>