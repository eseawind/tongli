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
<link rel="stylesheet" type="text/css" href="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/jquery-multi-select/css/multi-select.css" />
<link href="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/select2/select2_metro.css"  rel="stylesheet" type="text/css"  />
<style type="text/css">
select{width: 200px;}

</style>
</head>

<body class="page-header-fixed">
	<!-- BEGIN HEADER -->
	<%@ include file="../include/header.jsp"%>
	<!-- END   HEADER -->
	<div class="main pr">
		<div class="c10"></div>
		<div class="w">

			<div class="body fr" style="width: 770px;">
				<div class="title">&nbsp; 预约参观</div>
				<div class="content" style="min-height: 390px;">

					<div class="user_info">
						<form accept-charset="UTF-8" action="${basePath}/c202_save.ac" method="post">
							<input type="hidden" name="bean.id" value="${bean.id}" />
							<ul>
								<li>
									<div class="tit">参观课程：</div>
									<div class="con">
										<c:set var="bean_course" value="" />
										<select id="bean_course_select" class="" style="border:1px solid #ddd;" onchange="getDA()">
										<c:forEach items="${course_beans}" var="course_bean" varStatus="i">
											<optgroup label="${course_bean.subject_name}">
												<c:forEach items="${course_bean.beans}" var="course_bean2"  varStatus="n">
													<c:if test="${i.index==0 && n.index==0}">
														<c:set var="bean_course" value="${course_bean2.title}" />
													</c:if>
													<option value="${course_bean2.id}">${course_bean2.title}</option>
												</c:forEach>
											</optgroup>
										</c:forEach>
										</select>
										<input type="hidden" name="bean.course" id="bean_course_1" value="${bean_course}" />
										<em>* 你要参观的课程</em>
									</div>
								</li>
								<li>
									<div class="tit">参观时间：</div>
									<div class="con">
										<select id="bean_day" name="bean.day" class="" style="border:1px solid #ddd;" onchange="">
											
										</select>
										<em>* 你要参观的日期时间</em>
									</div>
								</li>
								<li>
									<div class="tit">参观场馆：</div>
									<div class="con">
									<select id="bean_addres" name="bean.addres" class="" style="border:1px solid #ddd;">
									
									</select>
									<em>* 你要参观的场馆</em>
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
							<button type="submit" class="btn btn-info" style="margin-left: 40px;" >提  交</button>
							</div>
							<div class="c10"></div>
						</form>
					</div>

				</div>
			</div>
			<%@ include file="../include/nav_left.jsp"%>
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
	<script type="text/javascript" src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/select2/select2.min.js"></script>
<script type="text/javascript">
function getDA(){
	$('#bean_course_1').val($("#bean_course_select option:selected").text());
	var cid=$("#bean_course_select").val();
	//alert(cid);
	if(cid){
		jQuery.ajax({
			url : '${basePath}/c202_getDate.ac?cid='+cid,
			success : function(req) {
				jQuery("#bean_day").html(req);
			},
			error : function() {
				//--异常--
			}
		});
		jQuery.ajax({
			url : '${basePath}/c202_getAddres.ac?cid='+cid,
			success : function(req) {
				//alert(req);
				jQuery("#bean_addres").html(req);
			},
			error : function() {
				//--异常--
			}
		});
	}
}
/* 
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
}); */
if('${bean_course}'!=''){
	getDA();
}
</script>