<%--
/*
 * 用户详情页面
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-03-20  ll   程序・发布
 * -------- ----------- ------------ ------------------------------------------
 * Copyright 2014 上海人保财险微信 System. - All Rights Reserved.
 *
 */
--%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<%@include file="../include/admin_title.jsp"%>
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<%@ include file="../include/public_js_css.jsp"%>
<link href="${basePath}/css/font-awesome/css/font-awesome.css"
	rel="stylesheet">
<link href="${basePath}/css/font-awesome/css/font-awesome-ie7.css"
	rel="stylesheet">
<link rel="stylesheet" href="${basePath}/plugins/bootstrapvalidator/vendor/bootstrap/css/bootstrap.css"/>
    <link rel="stylesheet" href="${basePath}/plugins/bootstrapvalidator/dist/css/bootstrapValidator.css"/>

    <script type="text/javascript" src="${basePath}/plugins/bootstrapvalidator/vendor/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${basePath}/plugins/bootstrapvalidator/dist/js/bootstrapValidator.js"></script>
    <style>
 
.col-lg-5 {
width: 71.66666667%;
}
.col-lg-offset-4 {
margin-left: 23.33333333%;
}
.col-lg-4 {
width: 53.33333333%;
}
    </style>
<style>
.pn-ftable {
	background-color: #B4CFCF;
	margin-top: 5px;
}

 

.pn-flabel {
	background-color: #f9f9f9;
	text-align: right;
	 
}

.pn-fcontent {
	background-color: #FFFFFF;
	padding-left: 5px;
}
.perm-layout-2 {
padding-left: 30px;
}
.pn-frequired {
color: red;
}
label {
display: inline-block;
font-weight:normal;
font-size: 14px;
}
</style>
<script src="${basePath}/js/jquery.form.js" type="text/javascript"></script>
<script type="text/javascript">
	var basePath = "${basePath}";
</script>


</head>
<body class="page-header-fixed">
	<!-- BEGIN HEADER -->
	<%@ include file="../include/header.jsp"%>
	<!-- END HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
	<div class="page-container">
		<!-- BEGIN SIDEBAR -->
		<%@ include file="../include/leftMenu.jsp"%>
		<script type="text/javascript">
			jQuery(document).ready(function() {
				$('#sys9,#sys9_sub_menu_l2').addClass('active');
				$('#sys9_arrow').addClass('open');
				$('#sys9_sub_menu').show();
			});
			
			  var perms=${perms};
			  function disablePerms() {
				
				if($("input[name=ro_super]:checked").val()=="1") {
					$(".perm-container input[type=checkbox]").attr("disabled","disabeld");
					$(".perm-container input[type=checkbox]").attr("checked","checked");
					$(".perm-container").find(".checker").each(function() {
						$(this).addClass("disabled");
					}); 
					 $(".perm-container").find("span").each(
							 function(){
								 $(this).addClass("checked");
					 });
					
				 } else {
					 $(".perm-container input[type=checkbox]").removeAttr("checked");
					 $(".perm-container input[type=checkbox]").removeAttr("disabled");
					 $(".perm-container").find(".checker").each(function() {
							$(this).removeClass("disabled");
							 
						}); 
					 $(".perm-container").find("span").each(
							 function(){
								 $(this).removeClass("checked");
					 });
					 
					 $(".perm-container input[type=checkbox]").each(function() {
							var perm = $(this).val();
							var index = perm.indexOf(",");		
							if(index!=-1) {
								perm = perm.substring(0,index);
							}
							for(var i=0,len=perms.length;i<len;i++) {
								if(perms[i]==perm) {
									$(this).attr("checked",true);
									 $(this).parent().addClass("checked");
									
									break;
								}
							}
						});
				}
			}
			jQuery(document).ready(function() {
				disablePerms();
				$(".perm-container input[type=checkbox]").each(function() {
					var perm = $(this).val();
					var index = perm.indexOf(",");		
					if(index!=-1) {
						perm = perm.substring(0,index);
					}
					for(var i=0,len=perms.length;i<len;i++) {
						if(perms[i]==perm) {
							$(this).attr("checked",true);
							break;
						}
					}
				});
				$("input[name=ro_super]").bind("click",function(){
					disablePerms();
				});
			});
			
			
		</script>
		<div class="page-content">

			<!-- BEGIN STYLE CUSTOMIZER -->
			<%@ include file="../include/style_customizer.jsp"%>
			<!-- END BEGIN STYLE CUSTOMIZER -->

			<!-- BEGIN PAGE HEADER-->
			<div class="row">
				<div class="col-md-12">
					<!-- BEGIN PAGE TITLE & BREADCRUMB-->
					<h3 class="page-title">
						管理微信公众帐号 <small><span class="help-inline">如果没有公众帐号，<a
								href="http://mp.weixin.qq.com" target="_blank">点这里注册</a></span></small>
					</h3>
					<ul class="page-breadcrumb breadcrumb">
						<li><i class="fa fa-home"></i> <a
							href="${basePath }/home_init.ac">主页</a> <i
							class="fa fa-angle-right"></i></li>
						<li><a href="#">角色管理</a> <i class="fa fa-angle-right"></i></li>
						<li>角色编辑</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			<!-- END PAGE HEADER-->
			<form accept-charset="UTF-8"  action="${basePath}/h/u002_save.ac" class="form-horizontal" method="post" id="defaultForm">
				<table class="table table-condensed">
					<tbody>
						 <tr>
							<td colspan="2" width="50%" style="background-color: #f9f9f9"> 
							<div class="form-group">
                      				  <label class="col-lg-3 control-label"><span class="pn-frequired">*</span>角色名:</label>
                        				<div class="col-lg-5">
                            			<input type="text" class="form-control" name="bean.ro_name" value="${bean.ro_name}"/>
                       				 </div>
                    				</div>
							</td>
							<td colspan="2" width="50%" style="background-color: #f9f9f9"> 
							<div class="form-group">
                      				  <label class="col-lg-3 control-label">排列顺序:</label>
                        				<div class="col-lg-5">
                            			<input type="text" class="form-control" name="bean.ro_priority" value="${bean.ro_priority}"/>
                       				 </div>
                    				</div>
							</td>
						</tr>
						
						<tr>
							<td width="15%" class="pn-flabel pn-flabel-h">拥有所有权限:</td>
							<td colspan="3" width="85%" class="pn-fcontent">
							<label><input  type="radio" value="1"  <c:if test="${bean.ro_super==1 }"> checked="checked" </c:if> name="ro_super">是</label>
							<label><input  type="radio" value="0" <c:if test="${bean.ro_super==0 }"> checked="checked" </c:if> name="ro_super">否</label></td>
						</tr>
						<tr>
							<td width="15%" class="pn-flabel pn-flabel-h">功能权限:</td>
							<td colspan="3" width="85%" class="pn-fcontent">
								<div class="perm-container">
									<input type="hidden" name="perms" value="/home">

									<!-- <div class="perm-layout-1">
										<label><input value="/home" type="checkbox"  name="perms">主页</label>
									</div> -->

									<div class="perm-layout-1">
										 <label><input  value="/weixin"  type="checkbox" name="perms">微信服务</label>
										 <div class="perm-layout-2">
										        <label><input   value="/w004"  type="checkbox" name="perms">文章管理</label>
												<label><input   value="/w002,/w003,/w009"  type="checkbox" name="perms">自动回复</label>
												<label><input   value="/w101,/w102,/w103"  type="checkbox" name="perms">信息群发</label>
												<label><input   value="/w010"   type="checkbox" name="perms">信息接收</label>
												<label><input	value="/w013"   type="checkbox" name="perms">客服响应</label>
												<label><input   value="/w012"   type="checkbox" name="perms">粉丝列表</label>
												<label><input   value="/w007"   type="checkbox" name="perms">自定义菜单</label>
										 </div>
									 </div>
									 <div class="perm-layout-1">
										 <label><input  value="/course"  type="checkbox" name="perms">课程管理</label>
										 <div class="perm-layout-2">
										        <label><input   value="/c102"  type="checkbox" name="perms">课程表管理</label>
												<label><input   value="/w101"   type="checkbox" name="perms">课程管理</label>
												<label><input	value="/w105"   type="checkbox" name="perms">课程地址管理</label>
										 </div>
									 </div>
									 <div class="perm-layout-1">
									        <label><input  value="/member"  type="checkbox" name="perms">会员管理</label>
										    <div class="perm-layout-2">
												<label><input   value="/m001"   type="checkbox" name="perms">会员管理</label>
										     </div>
									</div>
									<div class="perm-layout-1">
									        <label><input  value="/student"  type="checkbox" name="perms">学员管理</label>
										    <div class="perm-layout-2">
												<label><input   value="/s201"   type="checkbox" name="perms">学员管理</label>
												<label><input   value="/c401"   type="checkbox" name="perms">班级管理</label>
										     </div>
									</div>
									<div class="perm-layout-1">
									        <label><input  value="/info"  type="checkbox" name="perms">资讯管理</label>
										    <div class="perm-layout-2">
												<label><input   value="/s002"   type="checkbox" name="perms">栏目管理</label>
												<label><input   value="/s001"   type="checkbox" name="perms">资讯管理</label>
										     </div>
									</div>
									<div class="perm-layout-1">
									        <label><input  value="/yqlj"  type="checkbox" name="perms">友情链接管理</label>
										    <div class="perm-layout-2">
												<label><input   value="/s003"   type="checkbox" name="perms">信息管理</label>
										     </div>
									</div>
									<div class="perm-layout-1">
									        <label><input  value="/yycg"  type="checkbox" name="perms">预约体验</label>
										    <div class="perm-layout-2">
												<label><input   value="/c103"   type="checkbox" name="perms">信息管理</label>
										     </div>
									</div>
									<div class="perm-layout-1">
									        <label><input  value="/sms"  type="checkbox" name="perms">短信管理</label>
										    <div class="perm-layout-2">
												<label><input   value="/s004"   type="checkbox" name="perms">通讯录管理</label>
												<label><input   value="/s005"   type="checkbox" name="perms">短信管理</label>
										     </div>
									</div>
									<div class="perm-layout-1">
									        <label><input  value="/zxbm"  type="checkbox" name="perms">在线报名管理</label>
										    <div class="perm-layout-2">
												<label><input   value="/c104"   type="checkbox" name="perms">信息管理</label>
										     </div>
									</div>
									<div class="perm-layout-1">
									        <label><input  value="/tongji"  type="checkbox" name="perms">统计信息管理</label>
										    <div class="perm-layout-2">
										     </div>
									</div>
									<div class="perm-layout-1">
									        <label><input  value="/system"  type="checkbox" name="perms">系统管理</label>
										    <div class="perm-layout-2">
												<label><input   value="/u001"   type="checkbox" name="perms">用户管理</label>
												<label><input   value="/u002"   type="checkbox" name="perms">角色管理</label>
										     </div>
									</div>
									
 								</div>
								<script type="text/javascript">
									$(function() {
										$(".perm-container input[type=checkbox]").bind("click",function() {
											parentCheck(this);
											childCheck(this);
										});
									});
									function parentCheck(chk) {//控制当前节点的父亲勾选或不勾选
										
										var obj = $(chk).parent().parent().parent().parent().parent();
									    while(obj && obj.attr("class").indexOf("perm-container")==-1) {//当前节点不是顶层节点
									    	 if(chk.checked) {
													 $(obj.children()[0]).children().find("span").each(
															 function(){
																 $(this).addClass("checked");
																 $(this).find("input[type=checkbox]").attr("checked","checked");
																 
													 });
													 $(obj.children()[0]).children().attr("checked",true);
												
											}
											obj = obj.parent();
										}
									}
									function childCheck(chk) {////控制当前节点的孩子节点勾选或不勾选
										var obj = $(chk).parent().parent().parent().parent();
										if(obj&& obj.attr("class").indexOf("perm-layout-2")==-1){//点的第一层复选框
											$(chk).parent().parent().parent().next().find("span").each(
													 function(){
														if(chk.checked){
															 $(this).addClass("checked");
															 $(this).find("input[type=checkbox]").attr("checked","checked");
														 }else{
															 $(this).removeClass("checked");
															 $(this).find("input[type=checkbox]").removeAttr("checked");
														 }
														
													});
										}else{
											$(chk).parent().parent().find("span").each(//点的第二层复选框
													 function(){
														 if(chk.checked){
															 alert($(this).html());
															 $(this).addClass("checked");
															 $(this).find("input[type=checkbox]").attr("checked","checked");
															
														 }else{
															 $(this).removeClass("checked");
															 $(this).find("input[type=checkbox]").removeAttr("checked");
														 }
														
													});
											
											
										}
										
										
									}
								</script>

							</td>
						</tr>
						<tr align="center">
						
						<td colspan="4" class="pn-fbutton">
						<input class="btn green" name="commit" type="submit"  value="保存">
		                <input class="btn btn-primary" onclick="javascript:history.back(-1);"  name="commit" type="reset"
								value="返回"></td>
						 
		  
		  
							
						</tr>
					</tbody>
				</table>
				<input  type="hidden"  name="bean.role_id"   value="${bean.role_id}">
			</form>
           <br />


		</div>
	</div>
	<!-- END CONTAINER -->
	<!-- BEGIN FOOTER -->
	<%@ include file="../include/footer.jsp"%>
	<!-- END FOOTER -->

</body>
</html>
<script type="text/javascript">
$(document).ready(function() {
    $('#defaultForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	'bean.ro_name': {
                 validators: {
                    notEmpty: {
                         message: '角色名不能为空'
                     },
                 }
             },
             'bean.ro_priority': {
                 validators: {
                	 regexp: {
		                 regexp: /^[0-9]+$/,
		                 message: '排列顺序只能数字'
		              },
		             stringLength: {
		                 max: 5,
		                 message: '排列顺序必须小于5个字符长'
		             }
              
                 }
              
             }
             
         
        }
    });
});
</script>
