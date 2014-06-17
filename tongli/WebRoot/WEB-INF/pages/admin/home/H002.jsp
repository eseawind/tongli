<%--
/*
 * 上海人保财险微信后台主页(初始化页面)
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-03-04  wuxiaogang   程序・发布
 * -------- ----------- ------------ ------------------------------------------
 * Copyright 2014 上海人保财险微信 System. - All Rights Reserved.
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
<%@include file="../include/admin_title.jsp"%>
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<%@ include file="../include/public_js_css.jsp"%>
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
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
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
				$('#home_init').addClass('active');
			});
		</script>
		<!-- END SIDEBAR -->
		<!-- BEGIN PAGE -->
		<div class="page-content">
			<!-- BEGIN STYLE CUSTOMIZER -->
			<%@ include file="../include/style_customizer.jsp"%>
			<!-- END BEGIN STYLE CUSTOMIZER -->
			<!-- BEGIN PAGE HEADER-->
			<div class="row">
				<div class="col-md-12">
					<!-- BEGIN PAGE TITLE & BREADCRUMB-->
					<h3 class="page-title">
						上海人保财险微信系统V1.0 <small>后台</small>
					</h3>
					<ul class="page-breadcrumb breadcrumb">
						<li><i class="fa fa-home"></i> <a
							href="${basePath }/home_init.ac">主页</a> <i
							class="fa fa-angle-right"></i></li>
						<li><a href="#">个人信息修改</a></li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			<!-- END PAGE HEADER-->
			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
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
						
						
				 <div class="container" style="margin-top: 10px;">
		        <div class="row">
		            <div class="col-lg-4 col-lg-offset-4">
		                <div class="panel panel-default">
		                    <div class="panel-heading">
		                        <h3 class="panel-title">个人信息修改</h3>
		                    </div>
		
		                    <div class="panel-body">
		                        <form id="defaultForm" method="post" class="form-horizontal" accept-charset="UTF-8"  action="${basePath}/h/home_save.ac">
		                           <div class="form-group">
                      				  <label class="col-lg-3 control-label">用户名</label>
                        				<div class="col-lg-5">
                            			<input type="text" class="form-control" name="username"   width="190px"  disabled   value="${bean.username}"/>
                       				 </div>
                    				</div>
		                            <div class="form-group">
                      				  <label class="col-lg-3 control-label">原密码</label>
                        				<div class="col-lg-5">
                            			<input type="password" class="form-control" name="oldpassword" placeholder="原密码"/>
                       				 </div>
                    				</div>
		                            <div class="form-group">
                      				  <label class="col-lg-3 control-label">新密码</label>
                        				<div class="col-lg-5">
                            			<input type="password" class="form-control" name="password" placeholder="新密码"/>
                       				 </div>
                    				</div>
		                            
			 						  <div class="form-group">
                      				  <label class="col-lg-3 control-label">确认密码</label>
                        				<div class="col-lg-5">
                            			<input type="password" class="form-control" name="confirmPassword" placeholder="确认密码"/>
                       				 </div>
                    				</div>
                    				 <div class="form-group">
				                        <div class="col-lg-9 col-lg-offset-3">
				                            <button type="submit" class="btn btn-primary">修改</button>
				                        </div>
                    				</div>
                    				<input type="hidden" name="id" value="${bean.id}">
 		                        </form>
		                    </div>
		                </div>
		            </div>
		        </div>
		    </div>

 			</div>
			<!-- END PAGE CONTENT-->
		</div>
		<!-- END PAGE -->
	</div>
	<!-- END CONTAINER -->
	<!-- BEGIN FOOTER -->
	<%@ include file="../include/footer.jsp"%>
	<!-- END FOOTER -->

</body>
<!-- END BODY -->
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
        	oldpassword: {
                 validators: {
                    notEmpty: {
                         message: '旧密码不能为空'
                     },
                 }
             },
             
            email: {
                validators: {
                   
                    emailAddress: {
                        message: '邮箱格式不正确'
                    }
                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: '新密码不能为空'
                    },
                    identical: {
                        field: 'confirmPassword',
                        message: '密码和确认密码不一致'
                    }
                }
            },
            confirmPassword: {
                validators: {
                    notEmpty: {
                        message: '确认密码不能为空'
                    },
                    identical: {
                        field: 'password',
                        message: '密码和确认密码不一致'
                    }
                }
            }
        }
    });
});
</script>
