<%--
/*
 * 系统管理_会员管理_编辑 (页面)
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-04-01  wuxiaogang   程序・发布
 * -------- ----------- ------------ ------------------------------------------
 * Copyright 2014 jfq System. - All Rights Reserved.
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
<%@include file="../include/admin_title.jsp" %>
<%@ include file="../include/public_js_css.jsp"%>
<link href="${basePath}/css/messages.css" media="all" rel="stylesheet" type="text/css" />
<link href="${basePath}/css/font-awesome/css/font-awesome.css" rel="stylesheet"/>
<link href="${basePath}/css/font-awesome/css/font-awesome-ie7.css" rel="stylesheet"/>

<link rel="stylesheet" type="text/css" href="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/bootstrap-datepicker/css/datepicker.css" />

	<script type="text/javascript" src="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>

</head>
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
				$('#member,#member_sub_menu_l1').addClass('active');
				$('#member_arrow').addClass('open');
				$('#member_sub_menu').show();
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
						会员管理 <small><span class="help-inline">在这里你可以编辑会员信息</span></small>
					</h3>
					<ul class="page-breadcrumb breadcrumb">
						<li><i class="fa fa-home"></i> <a
							href="${basePath }/home_init.ac">Home</a> <i
							class="fa fa-angle-right"></i></li>
						<li><a href="#">系统管理</a> <i class="fa fa-angle-right"></i></li>
						<li><a href="${basePath}/h/m001_init.ac">会员管理</a> <i class="fa fa-angle-right"></i></li>
						<li>编辑</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			<!-- END PAGE HEADER-->
			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
				<div class="col-md-12">
					<form accept-charset="UTF-8"  action="${basePath}/h/m001_save.ac" class="edit_article" id="edit_article_13632" method="post">
						<s:token></s:token>
						<input name="bean.id" type="hidden" value="${bean.id}">
						<div class="well form-inline">
							 &nbsp; 
							<label>姓名
								<input class="upload-wrapper" id="message_user_name" name="bean.user_name" size="30" value="${bean.user_name }" placeholder="姓名" title="姓名" type="text">
							</label>
							&nbsp; 
							 <label title="男">
							<input name="bean.sex" value="0"
							<c:if test="${bean.sex=='0'}">
							 	checked="checked"
							 </c:if>
							 type="radio">
							 男</label>
							 <label title="女">
							<input name="bean.sex" value="1"
							<c:if test="${bean.sex=='1'}">
							 	checked="checked"
							 </c:if>
							 type="radio">
							 女</label>
							 &nbsp; 
						</div>
						<div class="portlet box btn default btn-block">
						<div class="portlet-title">
							<div class="caption"  id="select-image-modal"><i class="fa fa-anchor"></i><font color="#000">点击上传[头像]</font></div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="javascript:;" class="remove"></a>
							</div>
						</div>
						<div class="portlet-body" style="display: block; ">
							<input id="input_pic_url"  name="bean.pic_url" size="30" type="hidden" value="${bean.pic_url}">
							<img id="img_pic_url" alt="" src="${bean.pic_url}">
						</div>
					</div>
						<div class="form-group">
							<label for="article_passwd">密码</label> <input
								class="form-control" id="article_passwd" name="bean.passwd"
								type="text" value="${bean.passwd}">
						</div>
						<div class="form-group">
							<label for="article_user_type">会员类型</label> <input
								class="form-control" id="article_user_type"
								name="bean.user_type" type="text" value="${bean.user_type}">
						</div>
						<div class="form-group">
							<label for="article_nickname">用户昵称</label> <input
								class="form-control" id="article_nickname" name="bean.nickname"
								type="text" value="${bean.nickname}">
						</div>
						<div class="form-group">
							<label for="article_last_login">最后登入时间</label> ${bean.last_login}
						</div>
						<div class="form-group">
							<label for="article_bind_mobile">绑定手机</label> <input
								class="form-control" id="article_bind_mobile"
								name="bean.bind_mobile" type="text" value="${bean.bind_mobile}">
						</div>
						<div class="form-group">
							<label for="article_bind_email">绑定邮箱</label> <input
								class="form-control" id="article_bind_email"
								name="bean.bind_email" type="text" value="${bean.bind_email}">
						</div>
						<div class="form-group">
							<label for="article_tel">电话</label> <input class="form-control"
								id="article_tel" name="bean.tel" type="text" value="${bean.tel}">
						</div>
						<div class="form-group">
							<label for="article_real_name">真实姓名</label> <input
								class="form-control" id="article_real_name"
								name="bean.real_name" type="text" value="${bean.real_name}">
						</div>
						<div class="form-group">
							<label for="article_is_enabled">是否可用</label> <input
								class="form-control" id="article_is_enabled"
								name="bean.is_enabled" type="text" value="${bean.is_enabled}">
						</div>
						<div class="form-group">
							<label for="article_last_login_ip">最后登录IP</label> <input
								class="form-control" id="article_last_login_ip"
								name="bean.last_login_ip" type="text"
								value="${bean.last_login_ip}">
						</div>
						<div class="form-group">
							<label for="article_birthdate">生日</label> 
									<div class="input-group input-medium date date-picker" data-date-format="dd-mm-yyyy" data-date-start-date="+0d">
										<input type="text" name="bean.birthdate" value="${bean.birthdate}" class="form-control" readonly="">
										<span class="input-group-btn">
										<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
										</span>
									</div>
						</div>
						<div class="form-group">
							<label for="article_blood_type">血型</label> <input
								class="form-control" id="article_blood_type"
								name="bean.blood_type" type="text" value="${bean.blood_type}">
						</div>
						<div class="form-group">
							<label for="article_edu_level">文化程度</label> <input
								class="form-control" id="article_edu_level"
								name="bean.edu_level" type="text" value="${bean.edu_level}">
						</div>
						<div class="form-group">
							<label for="article_trade">从事行业</label> <input
								class="form-control" id="article_trade" name="bean.trade"
								type="text" value="${bean.trade}">
						</div>
						<div class="form-group">
							<label for="article_job">从事职业</label> <input class="form-control"
								id="article_job" name="bean.job" type="text" value="${bean.job}">
						</div>
						<div class="form-group">
							<label for="article_income_level">收入水平</label> <input
								class="form-control" id="article_income_level"
								name="bean.income_level" type="text"
								value="${bean.income_level}">
						</div>
						<div class="form-group">
							<label for="article_province_id">省</label> <input
								class="form-control" id="article_province_id"
								name="bean.province_id" type="text" value="${bean.province_id}">
						</div>
						<div class="form-group">
							<label for="article_city_id">市</label> <input
								class="form-control" id="article_city_id" name="bean.city_id"
								type="text" value="${bean.city_id}">
						</div>
						<div class="form-group">
							<label for="article_county_id">县</label> <input
								class="form-control" id="article_county_id"
								name="bean.county_id" type="text" value="${bean.county_id}">
						</div>
						<div class="form-group">
							<label for="article_address">详细地址</label> <input
								class="form-control" id="article_address" name="bean.address"
								type="text" value="${bean.address}">
						</div>
						<div class="form-group">
							<label for="article_zipcode">邮政编码</label> <input
								class="form-control" id="article_zipcode" name="bean.zipcode"
								type="text" value="${bean.zipcode}">
						</div>
						<div class="form-group">
							<label for="article_credential">证件类型</label> <input
								class="form-control" id="article_credential"
								name="bean.credential" type="text" value="${bean.credential}">
						</div>
						<div class="form-group">
							<label for="article_credential_code">证件号码</label> <input
								class="form-control" id="article_credential_code"
								name="bean.credential_code" type="text"
								value="${bean.credential_code}">
						</div>
						<div class="form-group">
							<label for="article_qq">QQ</label> <input class="form-control"
								id="article_qq" name="bean.qq" type="text" value="${bean.qq}">
						</div>
						<div class="form-group">
							<label for="article_msn">MSN</label> <input class="form-control"
								id="article_msn" name="bean.msn" type="text" value="${bean.msn}">
						</div>
						<div class="form-group">
							<label for="article_hobby">爱好</label> <input class="form-control"
								id="article_hobby" name="bean.hobby" type="text"
								value="${bean.hobby}">
						</div>
						<div class="form-group">
							<label for="article_last_update_date">最后修改时间</label>
							${bean.last_update_date}
						</div>
						<div class="form-group">
							<label for="article_description">详情</label>
							<div class="qeditor_border">
								<textarea name="bean.detail_info" style="height: 300px;width: 100%;" id="article_description" >
									${bean.detail_info}
								</textarea>
							</div>
						</div>
						<div class="form-actions">
							<input class="btn btn-primary" name="commit" type="submit"
								value="保存"> | <a
								href="${basePath}/h/m001_init.ac">返回</a>
						</div>
					</form>
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
jQuery(document).ready(function() {
	KindEditor.ready(function(K) {
		//--编辑框
		K.create('#article_description', {
			 resizeType : 2,
	         uploadJson : '${basePath}/uploadFile?isrich=1',
	         fileManagerJson : '${basePath}/plugins/editor/jsp/file_manager_json.jsp',
			 allowFileManager : true
		});
		//--图片
		var editor = K.editor({
			resizeType : 2,
			uploadJson : '${basePath}/uploadFile?isrich=1',
			fileManagerJson : '${basePath}/plugins/editor/jsp/file_manager_json.jsp',
			allowFileManager : true
		});
		//--
		K('#select-image-modal').click(function() {
			editor.loadPlugin('image',function() {
				editor.plugin.imageDialog({
					imageUrl : $('#img_pic_url').attr('src'),clickFn : function(
						url,title,width,height,border,align) {
							$('#img_pic_url').attr('src',url);
							$('#input_pic_url').val(url);
							editor.hideDialog();
						}
				});
			});
		});
	});
});
</script>
