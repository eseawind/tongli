<%--
/*
 * 客服信息-- 应答交互 (页面)
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-04-21  wuxiaogang   程序・发布
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
<title>x</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<meta name="MobileOptimized" content="320">
<script type="text/javascript">
	var basePath = '${basePath}';
</script>
<style type="text/css">
ul,li{
	margin: 0px;
	padding: 0px;
	text-decoration: none;
	list-style: none;
	clear: both;
}
</style>
<link href="${basePath}/css/boxy.css" rel="stylesheet" type="text/css" />
<link href="${basePath}/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${basePath}/plugins/q+/jsLib/jquery-1.7.1.min.js"></script>
<script src="${basePath}/js/jquery.boxy.js" type="text/javascript"></script>
<script src="${basePath}/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${basePath}/js/jquery.form.js" type="text/javascript"></script>

</head>
<body style="overflow: hidden;">
	<div class="row"> 
		<div class="col-md-12">
			<div id="div_consult" class="well form-inline col-md-10"
				style="height: 220px; overflow: scroll; overflow-x: hidden;">
				<ul id="ul_li_consult">
					<c:forEach items="${beans}" var="bean">
						<li>
						<c:choose>
							<c:when test="${bean.info_source=='1'}">
								<div style="text-align: left;">
									<p>${cbean.user_name}&nbsp;${bean.date_created}</p>
									<c:choose>
										<c:when test="${bean.pic_url!=null}">
										<p><a target="_blank"  href="${bean.pic_url}" ><img src="${bean.pic_url}" style="max-height:50px;"></a></p>
										</c:when>
										<c:otherwise>
										<p>${bean.content}</p>
										</c:otherwise>
									</c:choose>
								</div>
							</c:when>
							<c:otherwise>
								<div style="text-align: right;">
									<p>&nbsp;${bean.date_created}${cbean.cs_name}</p>
									<c:choose>
										<c:when test="${bean.pic_url!=null}">
										<p><a target="_blank" href="${bean.pic_url}" ><img src="${bean.pic_url}" style="max-height:50px;"></a></p>
										</c:when>
										<c:otherwise>
										<p>${bean.content}</p>
										</c:otherwise>
									</c:choose>
								</div>
							</c:otherwise>
							</c:choose>
						</li>
					</c:forEach>
				</ul>
			</div>
			<div class="well form-inline col-md-12">
				<form accept-charset="UTF-8"  action="${basePath}/h/c302_send.ac" id="send_content_13632" method="post">
					<input type="hidden" name="bean.user_id" value="${cbean.user_id}" />
					<input type="hidden" name="bean.consult_id" value="${cbean.id}" />
					<textarea   class="upload-wrapper" id="bean_content" name="bean.content" style="height: 50px;width: 100%;"></textarea>
					<a href="javascript:;" class="btn edit green" onclick="submitFrom2();">发送</a>
					<a href="javascript:;" class="btn edit green">常用信息</a>
					<a href="javascript:;" class="btn edit green" onclick="wclose();">咨询完结</a>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
<script type="text/javascript">
	$(function() {
		$('#div_consult').scrollTop($('#ul_li_consult').height());
		setInterval('loadUrl()',1000);
	});
	// 加载链接
	function loadUrl() {
		jQuery.ajax({
			url : '${basePath}/h/c302_receive.ac?oid=${cbean.user_id}&time=' + new Date().getTime(),
			success : function(req) {
				//json数组 解析出数据,然后显示
				try{
					var myobj=eval('['+req+']');  
					for(var i=0;i<myobj.length;i++){
					   var li='<li> <div style="text-align: left;" > <p>${cbean.user_name}&nbsp;'+myobj[i].d+'</p> <p>';
					   if(myobj[i].type=='image'){
						   li=li+'<a target="_blank"  href="'+myobj[i].t+'" ><img src="'+myobj[i].t+'" style="max-height:50px;"></a>';
					   }else{
						   li=li+myobj[i].t;
					   }
					   li=li+'</p></div></li>';
						$('#ul_li_consult').append(li);
						$('#div_consult').scrollTop($('#ul_li_consult').height());
					}
				}catch(e){}
			},
			error : function() {
				myBoxy("${cbean.user_name}信息接收发生错误");
			}
		});
	}
	// 提交from
	function submitFrom2() {
		var now = new Date()
		$("#send_content_13632").ajaxSubmit(function(data) {
			var li;
			if (data == "0") {
				li='<li> <div style="text-align: right;" > <p>${cbean.cs_name}&nbsp;&nbsp;'
				+(now.getMonth() + 1) + "/" 
				+ now.getDate() + " "
				+ now.getHours() + ":" 
				+ now.getMinutes() + ":"
				+ now.getSeconds() + ""
				+'</p> <p>'+$('#bean_content').val()+'</p></div></li>';
				$('#bean_content').val('')
			} else {
				li='<li> <div style="text-align: right;" > <p>${cbean.cs_name}&nbsp;'+
				+(now.getMonth() + 1) + "/" 
				+ now.getDate() + " "
				+ now.getHours() + ":" 
				+ now.getMinutes() + ":"
				+ now.getSeconds() + ""
				+'</p> <p>error!'+$('#bean_content').val()+'</p></div></li>';
			}
			$('#ul_li_consult').append(li);
			$('#div_consult').scrollTop($('#ul_li_consult').height());
		});
	}
	function wclose() {
		jQuery.ajax({
			url : '${basePath}/h/c301_close().ac?oid=${cbean.user_id}&id=${cbean.id}&time=' + new Date().getTime(),
			success : function(req) {
				if(req=='1'){
					myBoxy('已完结,请直接关闭本窗口!');
				}
			},
			error : function() {
				myBoxy("发生错误");
			}
		});
	}
</script>