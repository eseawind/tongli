<%--
/*
 * 课程--在线报名
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-06-08  wuxiaogang        程序・发布
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

</head>

<body class="page-header-fixed">
	<!-- BEGIN HEADER -->
	<%@ include file="../include/header.jsp"%>
	<!-- END   HEADER -->
	<div class="main pr">
		<div class="c10"></div>
		<div class="w">

			<div class="body fr" style="width: 770px;">
				<div class="title">&nbsp; 在线报名</div>
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
									<div class="tit">报名课程：</div>
									<div class="con">
										${bean.course} 
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
									<div class="tit">手机号码：</div>
									<div class="con">
										${bean.tel }
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
			<%@ include file="../include/nav_right.jsp"%>
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
