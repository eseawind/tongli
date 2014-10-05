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
<link href="${basePath}/plugins/bootstrap.admin.theme/assets/css/style.css" rel="stylesheet" type="text/css"/>
<link href="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link href="${basePath}/plugins/bootstrap.admin.theme/assets/css/style-metronic.css" rel="stylesheet" type="text/css"/>
<link href="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
<script src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/jquery.blockui.min.js" type="text/javascript"></script> 
<style type="text/css">
.page-header {
    padding-bottom: 9px;
    margin: 0px 0px 20px;
    border-bottom: 1px solid #EEE;
}
</style>
</head>
<body>
	<div class="all-elements">
		<%@ include file="../include/header.jsp"%>
		<div id="content" class="page-content">
			<div class="page-header">
				<a href="#" class="deploy-sidebar"></a>
				<p class="bread-crumb">
					预约参观
				</p>
				<a href="javascript:history.go(-1);" class="deploy-contact left-list"></a>
			</div>
			<div class="content ">
				<c:if test="${msg!=null}">
					<c:choose>
						<c:when test="${msg=='1'}">
						<div class="alert alert-success">
							<strong>Success!</strong> 信息提交成功!
						</div>
						</c:when>
						<c:otherwise>
							<div class="alert alert-danger">
								<strong>Error!</strong> ${msg}
							</div>
						</c:otherwise>
					</c:choose>
				</c:if>
					<div class="col-xs-12 ">
							<ul>
								<li>
									<div class="tit">参观课程：</div>
									<div class="com form-control">
										${bean.course} 
									</div>
								</li>
								<li>
									<div class="tit">参观时间：</div>
									<div class="com form-control">
										${bean.day} 
									</div>
								</li>
								<li>
									<div class="tit">参观场馆：</div>
									<div class="com form-control">
										${bean.addres} 
									</div>
								</li>
								<li>
									<div class="tit">孩子姓名：</div>
									<div class="com form-control">
										${bean.name }
									</div>
								</li>
								<li>
									<div class="tit">孩子性别：</div>
									<div class="com form-control">
									<c:if test="${bean.sex=='0'}">男</c:if>
									<c:if test="${bean.sex=='1'}">女</c:if>
									</div>
								</li>
								<li>
									<div class="tit">孩子年龄：</div>
									<div class="com form-control">
										${bean.age}
									</div>
								</li>
								<li>
									<div class="tit">手机号码：</div>
									<div class="com form-control">
										${bean.tel }
									</div>
								</li>
								<li class="li_0" style="height: 100px;">
									<div class="tit">其它：</div>
									<div class="com form-control" style="height: 80px;">
										${bean.detail_info}
									</div>
								</li>
							</ul>

							<div class="c10"></div>
							<div  style="margin-left: 100px;">
							</div>
							<div class="c10"></div>
					</div>
				<%@ include file="../include/footer.jsp"%>
			</div>
		</div>
	</div>
</body>
</html>
