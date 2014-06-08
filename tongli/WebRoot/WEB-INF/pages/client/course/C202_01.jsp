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
<script type="text/javascript" src="${basePath}/js/bxCarousel.js"></script>
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
				<div class="title">&nbsp; 预约参观</div>
				<div class="content" style="min-height: 390px;">
				<c:if test="${msg!=null}">
					<c:choose>
						<c:when test="${msg=='1'}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert"></button>
							<strong>Success!</strong> 信息提交成功!
						</div>
						</c:when>
						<c:otherwise>
							<div class="alert alert-danger">
								<button class="close" data-dismiss="alert"></button>
								<strong>Error!</strong> ${msg}
							</div>
						</c:otherwise>
					</c:choose>
				</c:if>
					<div class="user_info">
							<ul>
								<li>
									<div class="tit">参观课程：</div>
									<div class="con">
										${bean.course} 
									</div>
								</li>
								<li>
									<div class="tit">参观时间：</div>
									<div class="con">
										${bean.day} 
									</div>
								</li>
								<li>
									<div class="tit">参观场馆：</div>
									<div class="con">
										${bean.addres} 
									</div>
								</li>
								<li>
									<div class="tit">孩子姓名：</div>
									<div class="con">
										${bean.name }
									</div>
								</li>
								<li>
									<div class="tit">孩子性别：</div>
									<div class="con">
									<c:if test="${bean.sex=='0'}">男</c:if>
									<c:if test="${bean.sex=='1'}">女</c:if>
									</div>
								</li>
								<li>
									<div class="tit">孩子年龄：</div>
									<div class="con">
										${bean.age}
									</div>
								</li>
								<li>
									<div class="tit">手机号码：</div>
									<div class="con">
										${bean.tel }
									</div>
								</li>
								<li class="li_0" style="height: 100px;">
									<div class="tit">其它：</div>
									<div class="con" style="height: 80px;">
										${bean.detail_info}
									</div>
								</li>
							</ul>

							<div class="c10"></div>
							<div  style="margin-left: 100px;">
							</div>
							<div class="c10"></div>
					</div>

				</div>
			</div>
			<div class="body fl mt10" style="width: 197px;">
				<div class="title">
					<a href="${basePath}/c202_init.ac" class="ico_recommend">预约参观</a>
				</div>
				<div class="content" style="height: 150px;">
					<a href="${basePath}/c202_init.ac"><img src="images/img4.jpg" width="177" height="150" /></a>
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
