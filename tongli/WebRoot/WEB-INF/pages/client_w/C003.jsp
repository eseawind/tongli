<%--
/*
 * 资讯内容
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-03-30  wuxiaogang        程序・发布
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

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="include/title_meta.jsp"%>
<%@ include file="include/public_js_css.jsp"%>

</head>
<body>
	<div class="all-elements">
		<%@ include file="include/header.jsp"%>
		<div id="content" class="page-content">
			<div class="page-header">
				<a href="#" class="deploy-sidebar"></a>
				<p class="bread-crumb">
					${bean.title}
				</p>
				<a href="javascript:history.go(-1);" class="deploy-contact left-list"></a>
			</div>
			<div class="content ">
			<div class="container">
	                <a href="#" class="next-quote"></a>
	                <a href="#" class="prev-quote"></a>
	                <div class="quote-slider" data-snap-ignore="true">
	                	<c:forEach items="${tree_array}" var="tree">
							<c:if test="${tree.id!='6fba86e8436049e5b30123c538b7fc83'}">
							  <div>
	                        	<div class="services-item">
	                        		
						    		<a class="button <c:choose><c:when test="${tree.id==tid}">button-orange</c:when><c:otherwise>button-dark</c:otherwise></c:choose> center-button" href="${basePath}/w/c002_init.ac?tid=${tree.id}&pid=<c:choose><c:when test='${tree.parent_id!=null && tree.parent_id!=""}'>${tree.parent_id}</c:when><c:otherwise>${tree.id}</c:otherwise></c:choose>">${tree.name}</a>
								  </div>
				               </div>
							    	<c:if test="${tree.beans!=null && fn:length(tree.beans)>0}">
							    		<c:forEach items="${tree.beans}" var="bean">
							    			<c:if test="${bean.id!='6fba86e8436049e5b30123c538b7fc83'}">
							     		<div>
	                        				<div class="services-item">
							     		     	|-<a class="button <c:choose><c:when test="${bean.id==tid}">button-orange</c:when><c:otherwise>button-dark</c:otherwise></c:choose> center-button" href="${basePath}/w/c002_init.ac?tid=${bean.id}&pid=<c:choose><c:when test='${bean.parent_id!=null && bean.parent_id!=""}'>${bean.parent_id}</c:when><c:otherwise>${bean.id}</c:otherwise></c:choose>">${bean.name }</a>
										    </div>
							                 </div>
							     		  </c:if>
							    		     <%-- <c:if test="${bean.beans!=null && fn:length(bean.beans)>0}">
							      		<c:forEach items="${bean.beans}" var="bean2">
							      		     <li <c:if test="${bean2.id==tid}">class="on"</c:if>>
							      		     	|-|-<a class="button <c:choose><c:when test="${bean2.id==tid}">button-orange</c:when><c:otherwise>button-dark</c:otherwise></c:choose> center-button" href="${basePath}/w/c002_init.ac?tid=${bean2.id}&pid=<c:choose><c:when test='${bean2.parent_id!=null && bean2.parent_id!=""}'>${bean2.parent_id}</c:when><c:otherwise>${bean2.id}</c:otherwise></c:choose>">${bean2.name }</a>
							      		     </li>
							         			</c:forEach>
							      	</c:if> --%>
							       			</c:forEach>
							    	</c:if>
						    	</c:if>
						   	</c:forEach>
	                </div>
	            </div>    
				<div class="decoration"></div>
				<!-- END   HEADER -->
				<div class="container">
							${bean.detail_info}
				</div>
				<!-- BEGIN FOOTER -->
				<%@ include file="include/footer.jsp"%>
			</div>
		</div>
	</div>
</body>
</html>
