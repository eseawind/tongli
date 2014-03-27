<%--
/*
 * 微信服务_接收信息展示 (列表页面)
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-03-19  wuxiaogang   程序・发布
 * -------- ----------- ------------ ------------------------------------------
 * Copyright 2014 车主管家 System. - All Rights Reserved.
 *
 */
--%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" session="false"%>
<%@page import="cn.com.softvan.common.CommonConstant"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="customtag" uri="/custom-tags"%>
<%--消息类型
text:文本类型 
image:图片信息 
voice:语音 
video:视频 
music:音乐 
news:图文 
location:地理位置 
link:链接  
event:事件推送 --%>
<ul class="timeline">
<c:forEach items="${beans}" var="bean" varStatus="i">
	<li 
		class="timeline-blue"
	<%--
	<c:if test="${bean.msgtype=='text'}">
		class="timeline-blue"
	</c:if>
	<c:if test="${bean.msgtype=='image'}">
		class="timeline-green"
	</c:if>
	<c:if test="${bean.msgtype=='voice'}">
		class="timeline-blue"
	</c:if>
	<c:if test="${bean.msgtype=='video'}">
		class="timeline-green"
	</c:if>
	<c:if test="${bean.msgtype=='location'}">
		class="timeline-red"
	</c:if>
	<c:if test="${bean.msgtype=='link'}">
		class="timeline-grey"
	</c:if>
	<c:if test="${bean.msgtype=='event'}">
		class="timeline-purple"
	</c:if>
	--%>
	>
		
		<div class="timeline-icon"><img onerror="this.src='${basePath}/images/getheadimg.jpg'" 
		<c:choose>
			<c:when test="${bean.headimgurl!=null && bean.headimgurl!=''}">
				src="${bean.headimgurl}"
			</c:when>
			<c:otherwise>
			src="${basePath}/images/getheadimg.jpg"
			</c:otherwise>
		</c:choose>
		 style="width: 38px;height: 38px; class="avatar img-circle"></i></div>
		<div class="timeline-body">
			<p class="userInfo">
				<span class="name">${bean.nickname}</span>
				<span class="date">${bean.date_created}</span>
			</p>
			<div class="timeline-content">
				<c:if test="${bean.msgtype=='text'}">
					${bean.content}
				</c:if>
				<c:if test="${bean.msgtype=='image'}">
					<a href="${basePath}${bean.picurl}" target="_blank"><img src="${basePath}${bean.picurl}" style="height: 100px;"></a>
				</c:if>
				<c:if test="${bean.msgtype=='voice'}">
					${bean.url}
				</c:if>
				<c:if test="${bean.msgtype=='video'}">
					${bean.url}
				</c:if>
				<c:if test="${bean.msgtype=='location'}">
					x=${bean.location_x} y=${bean.location_y} ${bean.label }
				</c:if>
				<c:if test="${bean.msgtype=='link'}">
					<a href="${bean.url}" target="_blank">${bean.url}</a>
				</c:if>
				<c:if test="${bean.msgtype=='event'}">
					<c:if test="${bean.event=='subscribe'}">
						用户关注
					</c:if>
					<c:if test="${bean.event=='unsubscribe'}">
						取消关注
					</c:if>
					<c:if test="${bean.event=='SCAN'}">
						事件推送
					</c:if>
					<c:if test="${bean.event=='CLICK'}">
						点击菜单
					</c:if>
					<c:if test="${bean.event=='VIEW'}">
						点击菜单
					</c:if>
					${bean.eventkey}
				</c:if>
			</div>
			<div class="timeline-footer">
				<span class="btn green pull-right">
				<c:if test="${bean.msgtype=='text'}">
					文字
				</c:if>
				<c:if test="${bean.msgtype=='image'}">
					图片
				</c:if>
				<c:if test="${bean.msgtype=='voice'}">
					语音
				</c:if>
				<c:if test="${bean.msgtype=='video'}">
					视频
				</c:if>
				<c:if test="${bean.msgtype=='location'}">
					地理位置
				</c:if>
				<c:if test="${bean.msgtype=='link'}">
					链接
				</c:if>
				<c:if test="${bean.msgtype=='event'}">
					事件
				</c:if>
				</span>   
				<span class="btn pull-left">
				${bean.country} 
				${bean.province}
				${bean.city}
				</span>            
			</div>
		</div>
	</li>
	</c:forEach>
	<li> 
	<customtag:pagingext func="loadUrlPage" params="'h/w010_', 'list', 'a_div_info_list_'" />
	</li>
</ul>
