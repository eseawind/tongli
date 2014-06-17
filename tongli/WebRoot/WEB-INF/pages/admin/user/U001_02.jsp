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

    <script type="text/javascript" src="${basePath}/plugins/bootstrapvalidator/vendor/jquery/jquery-1.10.2.min.js"></script>
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
				$('#sys9,#sys9_sub_menu_l1').addClass('active');
				$('#sys9_arrow').addClass('open');
				$('#sys9_sub_menu').show();
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
						<li><a href="#">用户管理</a> <i class="fa fa-angle-right"></i></li>
						<li>用户列表</li>

					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			<c:if test="${msg!=null}">
							<c:choose>
								<c:when test="${msg!='1'}">
									<div class="alert alert-danger">
										<button class="close" data-dismiss="alert"></button>
										<strong>Error!</strong> ${msg}
									</div>
								</c:when>
								<c:otherwise>
									<div class="alert alert-success">
										<button class="close" data-dismiss="alert"></button>
										<strong>Success!</strong> 操作成功。
									</div>
								</c:otherwise>
							</c:choose>
						</c:if>
			<!-- END PAGE HEADER-->
			<form accept-charset="UTF-8"  action="${basePath}/h/u001_save.ac" class="form-horizontal" method="post" id="defaultForm" >
				<table  class="table table-condensed" >
					<tbody>
						<tr>
						
						<c:choose>
							<c:when test="${flag=='edit'}">
							<td colspan="2" width="50%" style="background-color: #f9f9f9"> 
							<div class="form-group">
                      				  <label class="col-lg-3 control-label" style="margin-left: 10px"><span class="pn-frequired">*</span>用户名:</label>
                        				<div class="col-lg-5" style="margin-left: -22px">
                            			<input type="text" class="form-control" value="${bean.username}"  disabled  name="bean.username"/>
                       				 </div>
                    				</div>
							</td>
							</c:when>
						    <c:when test="${flag=='add'}">
						    <td colspan="2" width="50%" style="background-color: #f9f9f9"> 
							<div class="form-group">
                      				  <label class="col-lg-3 control-label"  style="margin-left: 10px"><span class="pn-frequired">*</span>用户名:</label>
                        				<div class="col-lg-5" style="margin-left: -22px">
                            			<input type="text" class="form-control"    value="${bean.username}"  name="bean.username"/>
                       				 </div>
                    				</div>
							</td>
						    </c:when>
							</c:choose>
							
							
							<c:choose>
							<c:when test="${flag=='edit'}">
							 <td width="12%" class="pn-flabel pn-flabel-h">密码:</td>
							 <td colspan="1" width="38%" class="pn-fcontent"><input
								type="password" autocomplete="off" id="password" maxlength="100"  class="form-control"   name="bean.passwd"><span class="pn-fhelp">不修改请留空</span></td>
							</c:when>
						    <c:when test="${flag=='add'}">
						    <td colspan="2" width="50%" style="background-color: #f9f9f9"> 
							<div class="form-group">
                      				  <label class="col-lg-3 control-label"><span class="pn-frequired">*</span>密码:</label>
                        				<div class="col-lg-5" style="margin-left: -22px">
                            			<input type=txt class="form-control" value="${bean.passwd}"   name="bean.passwd"/>
                       				 </div>
                    				</div>
							</td>
						    </c:when>
							</c:choose>
						</tr>
						<tr>
						
						    <td width="12%" class="pn-flabel pn-flabel-h">电子邮箱:</td>
							<td colspan="1" width="38%" class="pn-fcontent"><input
								type="text"  value="${bean.email}" name="bean.email"  class="form-control
								class="email" ></td>
							<td width="12%" class="pn-flabel pn-flabel-h">禁用:</td>
							<td colspan="1" width="38%" class="pn-fcontent"><label><input
									type="radio" value="0" <c:if test="${bean.enabled==0 }"> checked="checked" </c:if> name="bean.enabled">是</label> <label><input
									type="radio" value="1" <c:if test="${bean.enabled==1||bean.enabled==null }"> checked="checked" </c:if> name="bean.enabled">否</label></td>
											
						</tr>
						 
						 
						<tr>
							 
							 <td width="12%" class="pn-flabel pn-flabel-h">性别:</td>
							<td colspan="1" width="38%" class="pn-fcontent"><label><input
									type="radio" value="1" name="bean.sex" <c:if test="${bean.sex==1 }"> checked="checked" </c:if>>男</label> <label><input
									type="radio" value="2" name="bean.sex" <c:if test="${bean.sex==2 }"> checked="checked" </c:if>>女</label> <label><input
									type="radio" value="3" <c:if test="${bean.sex==null||bean.sex==3 }"> checked="checked" </c:if> name="bean.sex">保密</label></td>
							 <!-- text不能有maxlength属性 否则会出现js错误 -->
							<td width="12%" class="pn-flabel pn-flabel-h">真实姓名:</td>
							<td colspan="1" width="38%" class="pn-fcontent"><input  class="form-control
								type="text"  name="bean.nickname" value="${bean.nickname}"></td>
						</tr>
						 
						 
						<tr>
						 <td width="12%" class="pn-flabel pn-flabel-h">角色:</td>
							<td colspan="3" width="88%" class="pn-fcontent">
                       	 <c:forEach items="${roleList}" var="role"  varStatus="i">
                             <label>${role.ro_name}<input type="checkbox" value="${role.role_id}" 
                             <c:forEach items="${roleIds}" var="myrole"  varStatus="i">
                           
                             <c:if test="${role.role_id==myrole }"> checked="checked" </c:if>
                            
	                         </c:forEach>  name="roleIds"/></label> &nbsp;&nbsp;&nbsp;&nbsp;
                         </c:forEach>
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
				<input type="hidden" value="${flag}"  name="flag">
				<input  type="hidden"  name="bean.id"   value="${bean.id}">
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
        	'bean.username': {
                 validators: {
                    notEmpty: {
                         message: '用户名不能为空'
                     }, 
                     regexp: {
                         regexp: /^[a-zA-Z0-9_\.]+$/,
                         message: '用户名只能包含字母、数字、下划线和点'
                     },
                     stringLength: {
                         min: 3,
                         max: 20,
                         message: '用户名必须大于3且小于15个字符长'
                     }
                    
                     
                 }
             },
             'bean.passwd': {
                 validators: {
                	 notEmpty: {
                         message: '密码不能为空'
                     },
		             regexp: {
		                 regexp: /^[a-zA-Z0-9_]+$/,
		                 message: '密码只能包含字母、数字、下划线'
		              },
		             stringLength: {
		                 min: 6,
		                 max: 10,
		                 message: '密码必须大于4且小于10个字符长'
		             }
              
                 }
              
             }
             
         
        }
    });
    
    var roles=${roles};
    $(".page-content input[type=checkbox]").each(function() {
		var role = $(this).val();
		var index = role.indexOf(",");		
		if(index!=-1) {
			role = role.substring(0,index);
		}
		for(var i=0,len=roles.length;i<len;i++) {
			if(roles[i]==role) {
				 $(this).attr("checked",true);
				 $(this).parent().addClass("checked");
				
				break;
			}
		}
	});
    
    
    
    $(function() {
		$(".page-content input[type=checkbox]").bind("click",function() {
			var obj=$(this).parent();
			if(this.checked){
				 obj.find("input[type=checkbox]").attr("checked","checked");
			 }else{
				 obj.find("input[type=checkbox]").removeAttr("checked");
			 }
		});
	});
});
</script>

 
								
								