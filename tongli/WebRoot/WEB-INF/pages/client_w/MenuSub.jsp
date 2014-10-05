<%--
/*
 * 资讯栏目页
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-06-28  wuxiaogang        程序・发布
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
<c:forEach items="${tree_array}" var="tree">
	<c:if test="${tree.id!='6fba86e8436049e5b30123c538b7fc83'}">
     	<a href="${basePath}/w/c002_init.ac?tid=${tree.id}&pid=<c:choose><c:when test='${tree.parent_id!=null && tree.parent_id!=""}'>${tree.parent_id}</c:when><c:otherwise>${tree.id}</c:otherwise></c:choose>">${tree.name}<em class="${tree.id} unselected-sub-nav"></em></a>
     	<c:if test="${tree.beans!=null && fn:length(tree.beans)>0}">
     		<c:forEach items="${tree.beans}" var="bean">
     		     	<a href="${basePath}/w/c002_init.ac?tid=${bean.id}&pid=<c:choose><c:when test='${bean.parent_id!=null && bean.parent_id!=""}'>${bean.parent_id}</c:when><c:otherwise>${bean.id}</c:otherwise></c:choose>">${bean.name }<em class="${bean.id} unselected-sub-nav"></em></a>
     		      <c:if test="${bean.beans!=null && fn:length(bean.beans)>0}">
	      		<c:forEach items="${bean.beans}" var="bean2">
	      		     <a href="${basePath}/w/c002_init.ac?tid=${bean2.id}&pid=<c:choose><c:when test='${bean2.parent_id!=null && bean2.parent_id!=""}'>${bean2.parent_id}</c:when><c:otherwise>${bean2.id}</c:otherwise></c:choose>">${bean2.name }<em class="${bean2.id} unselected-sub-nav"></em></a>
         		</c:forEach>
	      	</c:if>
        </c:forEach>
     	</c:if>
     </c:if>
</c:forEach>
<script type="text/javascript">
jQuery(document).ready(function() {
	//----菜单选中-----
	try{
		var url=location.href;
		var array1=url.split('pid=');
		if(array1!=null && array1.length>1){
			$('.'+array1[1]).find('em').removeClass('unselected-nav');
			$('.'+array1[1]).find('em').addClass('selected-nav');
			var c_contact_nav=$('.'+array1[1]).attr('class');
			if(!(c_contact_nav && c_contact_nav.indexOf('contact-nav')>0)){
				var array2=url.split('tid=');
				if(array2!=null && array2.length>1){
					var array3=array2[1].split('pid=');
					if(array3!=null && array3.length>1){
						$('.active-submenu').removeClass('active-submenu');
						array3[0]=array3[0].replace('&','');
						//alert($('.'+array3[0]).attr('class'));
						$('.'+array3[0]).parent().parent().addClass('active-submenu');
						$('.'+array3[0]).removeClass('unselected-sub-nav');
						$('.'+array3[0]).addClass('selected-sub-nav');
					}
				}
			}
		}
		
	}catch(e){}
});
</script>