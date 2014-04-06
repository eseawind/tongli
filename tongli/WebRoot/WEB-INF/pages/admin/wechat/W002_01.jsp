<%--
/*
 * 微信服务_自动回复_图文消息_编辑 (页面)
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-03-14  wuxiaogang   程序・发布
 * -------- ----------- ------------ ------------------------------------------
 * Copyright 2014 车主管家 System. - All Rights Reserved.
 *
 */
--%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" session="false"%>
<%@page import="cn.com.softvan.common.CommonConstant"%>
<%@page import="cn.com.softvan.common.IdUtils"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="zh-CN" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="zh-CN" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="zh-CN" class="no-js">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8" />
<%@include file="../include/admin_title.jsp" %>
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<%@ include file="../include/public_js_css.jsp"%>
<link href="${basePath}/css/messages.css" media="all" rel="stylesheet"
	type="text/css" />

<link href="${basePath}/plugins/bootstrap.admin.theme/assets/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>

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
				$('#wechat,#wechat_sub_menu_li_sub_menu_1').addClass('active');
				$('#wechat_arrow,#wechat_sub_menu_li_arrow').addClass('open');
				$('#wechat_sub_menu,#wechat_sub_menu_li_sub_menu').show();
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
						管理微信公众帐号 <small><span class="help-inline">如果没有公众帐号，<a
								href="http://mp.weixin.qq.com" target="_blank">点这里注册</a></span></small>
					</h3>
					<ul class="page-breadcrumb breadcrumb">
						<li><i class="fa fa-home"></i> <a
							href="${basePath }/home_init.ac">Home</a> <i
							class="fa fa-angle-right"></i></li>
						<li><a href="#">微信服务</a> <i class="fa fa-angle-right"></i></li>
						<li><a href="#">自动回复</a> <i class="fa fa-angle-right"></i></li>
						<li><a href="#">图文消息</a> <i class="fa fa-angle-right"></i></li>
						<li>编辑</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			<!-- END PAGE HEADER-->
			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
				<div class="col-md-12">


					<form accept-charset="UTF-8" action="${basePath}/h/w002_save.ac" class="edit_message" id="edit_message_8932" method="post">
						<s:token></s:token>
						<input id="message_articles_id" name="bean.articles_id" 
						<c:choose>
							<c:when test="${bean.articles_id!=null}">
							value="${bean.articles_id }" 
							</c:when>
							<c:otherwise>
							value="<%=IdUtils.createUUID(32)%>" 
							</c:otherwise>
						</c:choose>
						 type="hidden">
						
						<div class="well form-inline">
								<label>自动回复:关键字 
									<input class="upload-wrapper" id="message_keyword" name="bean.keyword" size="30" value="${bean.keyword }" placeholder="多个关键字请用空格隔开" title="客户发送和关键字匹配的信息，系统回复该内容" type="text">
								</label>
								 <label  title="客户发送关键字以外的信息时，系统回复该内容">
								 <input  <c:if test="${bean.default_flag=='1'}"> checked="checked" </c:if> name="bean.default_flag" type="checkbox" value="1">
								 默认回复</label>
								&nbsp; 
								<label title="客户首次关注微信时，系统自动回复该内容">
								<input <c:if test="${bean.subscribe_flag=='1'}"> checked="checked" </c:if> name="bean.subscribe_flag" type="checkbox" value="1">
								首次关注回复</label>
								<span class="help-inline">(设置后，在微信里回复关键字，系统会回复相应内容)</span>
						</div>
						<c:set var="bean0" value="null"/>
						<div class="row-fluid">
							<div class="col-md-4 message-size">
								<ul id="message-info" class="unstyled">
									<c:choose>
										<c:when test="${beans!=null && fn:length(beans)>0}">
											<c:forEach items="${beans}" var="bean1" varStatus="i">
													
													<li id="data_rid_${i.index}" data-img-version="circle.header" class="article pos-rel <c:if test="${i.index==0 }">cover</c:if>">
														<c:if test="${i.index==0 }">
														<c:set var="bean0" value="${bean1}" />
														<div class="msg-date">${bean1.last_updated}</div>
														</c:if>
														<input class="idx" id="id" name="id" type="hidden" value="${bean1.id }">
														<input class="title" id="title" name="title${bean1.id }" value="${bean1.title }" type="hidden">
														<input class="pic-url" id="picurl" name="picurl${bean1.id }" value="${bean1.picurl }" type="hidden">
														<input class="url" id="url" name="url${bean1.id }" value="${bean1.url }" type="hidden">
														<div class="pic-url">
															 <img onerror="this.src='${basePath}/images/error/404.jpg'" src="${bean1.picurl }" alt="">
														</div>
														<h4 class="title">${bean1.title }</h4>
														<input type="hidden" class="description"  name="description${bean1.id }" value="${bean1.description}">
														<div class="pos-abs article-actions">
															<a href="javascript:void(0)" class="btn edit green"
																title="修改" onclick="edit_message_article(this)"><i class="icon-pencil"></i> 修改</a>
														<c:if test="${i.index!=0 }">		
															 <input id="del_flag" name="del_flag${bean1.id }" type="hidden" value="0">
															 <a href="javascript:void(0)" class="btn btn-danger destroy" title="删除"  onclick="del_message_article(this)">
															 <i class="icon-trash"></i> 删除</a>
														 </c:if>
														</div>
													</li>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<li id="data_rid_0" data-img-version="circle.header" class="article pos-rel cover">
												<div class="msg-date">${date}</div> 
												<c:set var="uuid" value="<%=IdUtils.createUUID(32)%>"></c:set>
												<input class="idx" id="id" name="id" type="hidden" value="${uuid}">
												<input class="title" id="title" name="title${uuid}" type="hidden">
												<input class="pic-url" id="picurl" name="picurl${uuid}" type="hidden">
												<input class="url" id="url" name="url${uuid}" type="hidden">
												<div class="pic-url">
													<span class="default-tip" style="">封面图片</span>
													 <img src="" alt="" style="display: none">
												</div>
												<h4 class="title">标题</h4>
												<input type="hidden" class="description"  name="description${uuid}">
												<div class="pos-abs article-actions">
													<a href="javascript:void(0)" class="btn edit green"
														title="修改" onclick="edit_message_article(this)"><i class="icon-pencil"></i> 修改</a>
												</div>
											</li>
		
											<li id="data_rid_1" data-img-version="metro.menu" class="article pos-rel ">
												<c:set var="uuid" value="<%=IdUtils.createUUID(32)%>"></c:set>
												<input class="idx" id="id" name="id" type="hidden" value="${uuid}">
												<input class="title" id="title" name="title${uuid}" type="hidden">
												<input class="pic-url" id="picurl" name="picurl${uuid}" type="hidden">
												<input class="url" id="url" name="url${uuid}" type="hidden">
												<div class="pic-url">
													<span class="default-tip" style="">缩略图</span> 
													<img src="" alt="" style="display: none">
												</div>
												<h4 class="title">标题</h4>
												<input type="hidden" class="description" name="description${uuid}">
												<div class="pos-abs article-actions">
													<a href="javascript:void(0)" class="btn edit green" title="修改"  onclick="edit_message_article(this)"><i class="icon-pencil"></i> 修改</a> 
														<input id="del_flag" name="del_flag${uuid}" type="hidden" value="0">
														 <a href="javascript:void(0)" class="btn btn-danger destroy" title="删除"  onclick="del_message_article(this)">
														 <i class="icon-trash"></i> 删除</a>
												</div></li>
											</c:otherwise>
											
									</c:choose>
									<li class="add-message-li-btn"><a
										href="javascript:void(0)"
										class="btn btn-block btn-large btn-success"
										onclick="add_message_article(this)"><i
											class="icon-plus"></i> 增加一条</a></li>
								</ul>
							</div>
							<!--  -->
							<div class="col-md-6 msg-edit pos-rel" style="margin-top: 8px;">
								<input class="rid" id="" name="" type="hidden" value="data_rid_0">
								<c:choose>
									<c:when test="${bean0!=null && bean0!='null'}">
									<label>标题</label> 
									<input class="col-md-12 form-control placeholder-no-fix" id="title" name="" type="text" value="${bean0.title }"> 
									<label>封面(最佳大小: 700 x 400)</label>
									<div class="upload-wrapper">
										<a id="link-select-image-modal" class="btn btn-info" href="#selectImageModal"
											data-img-version="circle.header" data-toggle="modal">选图片</a> 
											
										<div id="article-info-filequeue" class="filequeue" <c:if test="${bean0.picurl==null}">style="display: none;"</c:if>>
											<div class="uploadifyQueueItem item">
												<img onerror="this.src='${basePath}/images/error/404.jpg'" class="pic-url" id="new_picurl"  src="${bean0.picurl}">
											</div>
											<!--TODO fix to span_xx -->
											<div class="clearfix"></div>
										</div>
									</div>
									<!-- js in edit.js rel obj_id and file_queue_id -->
									<label>简介</label>
									<div class="controls">
										<div class="input-append">
											<textarea class="col-md-12 form-control placeholder-no-fix" data-target=".resource-url" id="description" name="" placeholder="信息简介">${bean0.description}</textarea>										
										</div>
									</div>
									<label>链接</label>
									<div class="controls">
										<div class="input-append">
											<input class="col-md-12 form-control placeholder-no-fix" data-target=".resource-url" id="url" name="" placeholder="如果是外链地址,请直接输入" type="text" value="${bean0.url}"> 
											<a class="btn btn-info" data-toggle="modal" href="#myModal1">选链接</a>
										</div>
									</div>
									</c:when>
									<c:otherwise>
									<label>标题</label> 
									<input class="col-md-12 form-control placeholder-no-fix" id="title" name="" type="text" value=""> 
									<label>封面(最佳大小: 700 x 400)</label>
									<div class="upload-wrapper">
										<a id="link-select-image-modal" class="btn btn-info" href="#selectImageModal"
											data-img-version="circle.header" data-toggle="modal">选图片</a> 
											
										<div id="article-info-filequeue" class="filequeue" style="display: none;">
											<div class="uploadifyQueueItem item">
												<img class="pic-url" id="new_picurl"  src="">
											</div>
											<!--TODO fix to span_xx -->
											<div class="clearfix"></div>
										</div>
									</div>
									<!-- js in edit.js rel obj_id and file_queue_id -->
									<label>简介</label>
									<div class="controls">
										<div class="input-append">
											<textarea class="col-md-12 form-control placeholder-no-fix" data-target=".resource-url" id="description" name="" placeholder="信息简介">${bean0.description}</textarea>										
										</div>
									</div>
									<label>链接</label>
									<div class="controls">
										<div class="input-append">
											<input class="col-md-12 form-control placeholder-no-fix" data-target=".resource-url" id="url" name="" placeholder="如果是外链地址,请直接输入" type="text" value=""> 
											<a class="btn btn-info" data-toggle="modal" href="#myModal1">选链接</a>
										</div>
									</div>
									</c:otherwise>
								</c:choose>
								<span class="msg-arrow arrow-out pos-abs"></span> 
								<span class="msg-arrow arrow-in pos-abs"></span>
							</div>
						</div>
						<div style="clear: both;"></div>
						<div class="form-actions center">
							<input class="btn btn-primary btn-large" name="commit"
								type="submit" value="完成">
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
<!-- Modal -->
<div id="myModal1" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">选择文章</h4>
			</div>
			<div class="modal-body">
				<p></p>
				<div class="form-group" id="list1_article_div">
				</div>
			</div>
			<div class="modal-footer">
				<button class="btn default" data-dismiss="modal" aria-hidden="true">关闭</button>
				<button class="btn blue" data-dismiss="modal" aria-hidden="true" onclick="onCheckedArticleInfo()">确定</button>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	//选择文章
	function onCheckedArticleInfo(){
		var data_rid=$('.msg-edit').find('.rid').val();
		var id=$('input[name="w004_03_article_id"]:checked').val();
		//标题
		$('.msg-edit').find('#title').val($('#title_'+id).val());
		$('#'+data_rid).find('.title').html($('#title_'+id).val());
		$('#'+data_rid).find('#title').val($('#title_'+id).val());
		//标题图
		var pic_url=$('#pic_url_'+id).val();
		if(pic_url){
			$('.msg-edit').find('#new_picurl').attr('src',pic_url);
			$('.msg-edit').find('#article-info-filequeue').show();
			$('#'+data_rid).find('#picurl').val(pic_url);
			$('#'+data_rid).find('.pic-url').find('img').attr('src',pic_url);
			$('#'+data_rid).find('.pic-url').find('.default-tip').remove();
			$('#'+data_rid).find('.pic-url').find('img').show();
		}else{
			$('.msg-edit').find('#new_picurl').attr('src','');
			$('.msg-edit').find('#article-info-filequeue').hide();
		}
		//简介
		//alert($('#brief_'+id).val());
		$('.msg-edit').find('#description').val($('#brief_'+id).val());
		$('#'+data_rid).find('.description').val($('#brief_'+id).val());
		//链接
		$('.msg-edit').find('#url').val($('#url_'+id).val());
		$('#'+data_rid).find('#url').val($('#url_'+id).val());
	}
	//分页查询
	function loadUrlPage(offset, url, event, divId) {
		if (url == null || url == "" || divId == null || divId == "") {
			return;
		}
		var load = "<a class='loading' >信息努力加载中...</a>";
		jQuery("#" + divId).html(load);
		jQuery.ajax({
			url : '${basePath}/' + url + event+'.ac?offset='
					+ offset + '&time=' + new Date().getTime(),
			success : function(req) {
				jQuery("#" + divId).html(req);
			},
			error : function() {
				jQuery("#" + divId).html("信息加载发生错误");
			}
		});
	}
	jQuery(document).ready(function() {
		//TODO 分页
		loadUrlPage(0, 'h/w004_', 'list1', 'list1_article_div');
		//
		KindEditor.ready(function(K) {
			var editor = K.editor({
						resizeType : 2,
						uploadJson : '${basePath}/uploadFile?isrich=1',
						fileManagerJson : '${basePath}/plugins/editor/jsp/file_manager_json.jsp',
						allowFileManager : true
			});
			K('#link-select-image-modal').click(function() {
				editor.loadPlugin('image',function() {
					editor.plugin.imageDialog({
						imageUrl : $('#new_picurl').attr('src'),clickFn : function(
							url,title,width,height,border,align) {
								url=url.replace('/n3/','/n2/');
								$('#new_picurl').attr('src',url);
								editor.hideDialog();
								$('.msg-edit').find('#article-info-filequeue').show();
								
								var data_rid=$('.msg-edit').find('.rid').val();
								$('#'+data_rid).find('.pic-url').find('img').attr('src',url);
								$('#'+data_rid).find('#picurl').val(url);
								$('#'+data_rid).find('.pic-url').find('.default-tip').remove();
								$('#'+data_rid).find('.pic-url').find('img').show();
							}
					});
				});
			});
		});
		$('.msg-edit').find('#title').blur(function(){
			var data_rid=$('.msg-edit').find('.rid').val();
			if($(this).val()){
				$('#'+data_rid).find('.title').html($(this).val());
			}
			$('#'+data_rid).find('#title').val($(this).val());
			//myAlert(data_rid);
		});
		$('.msg-edit').find('#url').blur(function(){
			var data_rid=$('.msg-edit').find('.rid').val();
			$('#'+data_rid).find('#url').val($(this).val());
		});
		$('.msg-edit').find('.description').blur(function(){
			var data_rid=$('.msg-edit').find('.rid').val();
			$('#'+data_rid).find('.description').val($(this).val());
		});
	});
	function edit_message_article(obj){
		var data_rid=$(obj).parent().parent().attr('id');
		$('.msg-edit').find('.rid').val(data_rid);
		
		$('.msg-edit').find('#title').val($('#'+data_rid).find('#title').val());
		var picurl=$('#'+data_rid).find('#picurl').val();
		if(picurl){
			$('.msg-edit').find('#new_picurl').attr('src',$('#'+data_rid).find('#picurl').val());
			$('.msg-edit').find('#article-info-filequeue').show();
		}else{
			$('.msg-edit').find('#new_picurl').attr('src','');
			$('.msg-edit').find('#article-info-filequeue').hide();
		}
		
		$('.msg-edit').find('#description').val($('#'+data_rid).find('.description').val());
		
		$('.msg-edit').find('#url').val($('#'+data_rid).find('#url').val());
		$('.msg-edit').css({"margin-top":($('#'+data_rid).offset().top-270)+'px'});
		$('.msg-edit').show();
	}
	function add_message_article(obj){
		var length=$(obj).parent().parent().parent().find('li').length-1;
		if(length<=9){
			var uuid=guid();
			$(obj).before("<li id='"+uuid+"' data-new-id='-1' data-img-version='metro.menu' class='article pos-rel '> <input class='title' id='title' name='title"+uuid+"' type='hidden' /><input class='pic-url' id='picurl' name='picurl"+uuid+"' type='hidden' /><input class='idx' id='id' name='id' type='hidden' value='"+uuid+"' /><input class='url' id='url' name='url"+uuid+"' type='hidden' /> <div class='pic-url'> <span class='default-tip' style=''>缩略图</span> <img src='' alt='' style='display:none'> </div> <h4 class='title'>标题</h4> <input type='hidden' class='description'  name='description"+uuid+" value=''><div class='pos-abs article-actions'> <a href='javascript:void(0)' class='btn edit green' title='修改' onclick='edit_message_article(this)' ><i class='icon-pencil'></i> 修改</a> <input id='del_flag' name='del_flag"+uuid+"' type='hidden' /> <a href='javascript:void(0)' class='btn btn-danger destroy' title='删除' onclick='del_message_article(this)'><i class='icon-trash'></i> 删除</a></div> </li>");
		}else{
			myAlert_error('图文信息最多10条!');
		}
	}
	function del_message_article(obj){
		var data_new_id=$(obj).parent().parent().attr('data-new-id');
		if(data_new_id){
			$(obj).parent().parent().remove();
		}else{
			$(obj).parent().parent().find('#del_flag').val('1');
			$(obj).parent().parent().hide();
		}
		$('.msg-edit').hide();
	}
</script>