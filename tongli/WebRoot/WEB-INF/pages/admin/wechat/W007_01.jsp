<%--
/*
 * 微信服务_自定义菜单_编辑 (页面)
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-03-04  wuxiaogang   程序・发布
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
</head>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="zh-CN" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="zh-CN" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="zh-CN" class="no-js">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8" />
<title>微信服务-自定义菜单_编辑【车主管家】</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<meta name="MobileOptimized" content="320">
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<%@ include file="../include/public_js_css.jsp"%>
<link href="${basePath}/css/messages.css" media="all" rel="stylesheet"
	type="text/css" />
<link href="${basePath}/css/font-awesome/css/font-awesome.css"
	rel="stylesheet">
<link href="${basePath}/css/font-awesome/css/font-awesome-ie7.css"
	rel="stylesheet">

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
				$('#wechat,#w007_init').addClass('active');
				$('#wechat_arrow').addClass('open');
				$('#wechat_sub_menu').show();
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
						<li><a href="${basePath }/h/w007_init.ac">服务号菜单</a>
							<i class="fa fa-angle-right"></i></li>
						<li>编辑</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			<!-- END PAGE HEADER-->
			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
				<div class="col-md-7">
				
					<form accept-charset="UTF-8" action="${basePath }/h/w007_save.ac"
						class="portlet-body form" method="post">
						<div style="margin: 0; padding: 0; display: inline">
							<input name="bean.id" type="hidden" value="${bean.id}">
						</div>
						<div class="form-group">
						<label class="col-md-2 control-label">上一级</label>
						<div class="col-md-9">
							<select multiple="" name="bean.parent_id" class="form-control">
								<option>顶级</option>
								<c:forEach items="${beans}" var="bean1">
									<option value="${bean1.id }" 
									<c:if test="${bean.parent_id==bean1.id }">
									selected="selected"
									</c:if>
									>${bean1.menu_name}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-2 control-label">菜单名称</label>
						<div class="col-md-9">
							<input class="form-control input-lg" placeholder="必填" id="bean.menu_name" name="bean.menu_name" size="30" type="text" value="${bean.menu_name}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-2 control-label">菜单KEY</label>
						<div class="col-md-9">
							<input class="form-control input-lg" placeholder="必填" id="bean.menu_key" name="bean.menu_key" size="30" type="text" value="${bean.menu_key}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-2 control-label">菜单类型</label>
						<div class="col-md-9">
							<div class="radio-list">
								<label class="radio-inline">
									<div id="uniform-optionsRadios25" class="radio">
										<span class="checked">
											<input name="bean.menu_type" id="optionsRadios25" <c:if test="${bean.menu_type=='click'}">checked="checked"</c:if> value="click"  type="radio"/>
										 </span>
									</div> click
								</label>
								<label class="radio-inline">
									<div id="uniform-optionsRadios26" class="radio">
										<span class="checked">
											<input name="bean.menu_type" id="optionsRadios26"  <c:if test="${bean.menu_type=='view'}">checked="checked"</c:if> value="view" type="radio"/>
										 </span>
									</div> view
								</label>
							</div>
						</div>
					</div>
					<div style="clear: both;"></div>
					<div class="form-group">
						<label class="col-md-2 control-label">显示顺序</label>
						<div class="col-md-9">
							<input class="form-control input-lg" placeholder="Large Input" id="bean.sort_num" name="bean.sort_num" size="30" type="number" value="${bean.sort_num}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-2 control-label">VIEW链接</label>
						<div class="col-md-8">
								<input class="form-control input-lg" placeholder="如果是外链地址,请直接输入" id="bean.menu_url" name="bean.menu_url" size="30" type="text" value="${bean.menu_url}">
						</div>
						<button class="btn btn-info" data-toggle="modal" href="#linkModal">选链接</button>
					</div>
					<div class="form-actions fluid">
						<div class="col-md-offset-4 col-md-4">
							<input class="btn btn-large btn-primary" name="commit"
								type="submit" value="保存"> | <a href="${basePath }/h/w007_init.ac">返回</a>
						</div>
					</div>
					</form>
					<style>
#linkModal {
	width: 800px;
}

.qeditor_preview {
	height: 128px;
}

.qeditor_preview .item {
	float: none !important;
	height: auto;
	display: inline-block;
	vertical-align: middle;
}
</style>

					<div class="modal well fade" id="linkModal" style="display: none">
						<div class="modal-header">
							<input id="h_current_target" name="h_current_target"
								type="hidden" value=""> <input id="h_article_pagination"
								name="h_article_pagination" type="hidden" value="9"> <a
								class="close" data-dismiss="modal">×</a>
							<h3>选链接</h3>
						</div>
						<div class="modal-body">
							<ul class="nav nav-tabs">
								<li><a data-toggle="tab" href="#tab_article_grp">文章分组</a></li>
								<li><a data-toggle="tab" href="#tab_article">单篇文章</a></li>
								<li><a data-toggle="tab" href="#tab_tel">电话</a></li>
								<li><a data-toggle="tab" href="#tab_page">微网站</a></li>
							</ul>

							<div class="tab-content">
								<div class="tab-pane well" id="tab_article_grp">
									<div class="article-grp-toggle" style="display: none">
										<form accept-charset="UTF-8" action="/article_grps"
											class="new_article_grp" data-remote="true"
											id="new_article_grp" method="post">
											<div style="margin: 0; padding: 0; display: inline">
												<input name="utf8" type="hidden" value="✓"><input
													name="authenticity_token" type="hidden"
													value="d7SU0v3HNgFCdoCmdRtLj+BeVaQcFwQD29dTsUF1qcI=">
											</div>
											<div class="form-group">
												<label for="article_grp_name">组名</label><input
													class="col-md-12" id="article_grp_name"
													name="article_grp[name]" size="30" type="text">
											</div>
											<input class="btn btn-primary" name="commit" type="submit"
												value="保存"> | <a class="btn btn-link" href="#"
												onclick="$(&quot;.article-grp-toggle&quot;).slideToggle(); return false;">取消</a>
										</form>
									</div>
									<div class="article-grp-toggle">
										<div class="btn-toolbar">
											<a class="btn btn-primary" href="#"
												onclick="$(&quot;.article-grp-toggle&quot;).slideToggle(); return false;">新建分组</a>
										</div>
										<ul id="article-grp-list" class="unstyled"></ul>
									</div>
								</div>

								<div class="tab-pane well clearfix" id="tab_article">
									<div class="article-toggle" style="display: none">
										<form accept-charset="UTF-8" action="/articles"
											class="new_article" data-remote="true" id="new_article"
											method="post">
											<div style="margin: 0; padding: 0; display: inline">
												<input name="utf8" type="hidden" value="✓"><input
													name="authenticity_token" type="hidden"
													value="d7SU0v3HNgFCdoCmdRtLj+BeVaQcFwQD29dTsUF1qcI=">
											</div>
											<div class="form-group datetime">
												<label for="article_happen_at">时间</label> <select
													id="article_happen_at_1i" name="article[happen_at(1i)]">
													<option value="2009">2009</option>
													<option value="2010">2010</option>
													<option value="2011">2011</option>
													<option value="2012">2012</option>
													<option value="2013">2013</option>
													<option selected="selected" value="2014">2014</option>
													<option value="2015">2015</option>
													<option value="2016">2016</option>
													<option value="2017">2017</option>
													<option value="2018">2018</option>
													<option value="2019">2019</option>
												</select> <select id="article_happen_at_2i"
													name="article[happen_at(2i)]">
													<option value="1">一月</option>
													<option value="2">二月</option>
													<option selected="selected" value="3">三月</option>
													<option value="4">四月</option>
													<option value="5">五月</option>
													<option value="6">六月</option>
													<option value="7">七月</option>
													<option value="8">八月</option>
													<option value="9">九月</option>
													<option value="10">十月</option>
													<option value="11">十一月</option>
													<option value="12">十二月</option>
												</select> <select id="article_happen_at_3i"
													name="article[happen_at(3i)]">
													<option value="1">1</option>
													<option value="2">2</option>
													<option value="3">3</option>
													<option selected="selected" value="4">4</option>
													<option value="5">5</option>
													<option value="6">6</option>
													<option value="7">7</option>
													<option value="8">8</option>
													<option value="9">9</option>
													<option value="10">10</option>
													<option value="11">11</option>
													<option value="12">12</option>
													<option value="13">13</option>
													<option value="14">14</option>
													<option value="15">15</option>
													<option value="16">16</option>
													<option value="17">17</option>
													<option value="18">18</option>
													<option value="19">19</option>
													<option value="20">20</option>
													<option value="21">21</option>
													<option value="22">22</option>
													<option value="23">23</option>
													<option value="24">24</option>
													<option value="25">25</option>
													<option value="26">26</option>
													<option value="27">27</option>
													<option value="28">28</option>
													<option value="29">29</option>
													<option value="30">30</option>
													<option value="31">31</option>
												</select> — <select id="article_happen_at_4i"
													name="article[happen_at(4i)]">
													<option value="00">00</option>
													<option value="01">01</option>
													<option value="02">02</option>
													<option value="03">03</option>
													<option value="04">04</option>
													<option value="05">05</option>
													<option value="06">06</option>
													<option value="07">07</option>
													<option value="08">08</option>
													<option value="09">09</option>
													<option value="10">10</option>
													<option value="11">11</option>
													<option value="12">12</option>
													<option value="13">13</option>
													<option value="14">14</option>
													<option value="15">15</option>
													<option value="16">16</option>
													<option selected="selected" value="17">17</option>
													<option value="18">18</option>
													<option value="19">19</option>
													<option value="20">20</option>
													<option value="21">21</option>
													<option value="22">22</option>
													<option value="23">23</option>
												</select> : <select id="article_happen_at_5i"
													name="article[happen_at(5i)]">
													<option value="00">00</option>
													<option value="01">01</option>
													<option value="02">02</option>
													<option value="03">03</option>
													<option value="04">04</option>
													<option value="05">05</option>
													<option value="06">06</option>
													<option value="07">07</option>
													<option value="08">08</option>
													<option value="09">09</option>
													<option value="10">10</option>
													<option value="11">11</option>
													<option value="12">12</option>
													<option value="13">13</option>
													<option value="14">14</option>
													<option value="15">15</option>
													<option value="16">16</option>
													<option value="17">17</option>
													<option value="18">18</option>
													<option value="19">19</option>
													<option value="20">20</option>
													<option value="21">21</option>
													<option value="22">22</option>
													<option value="23">23</option>
													<option selected="selected" value="24">24</option>
													<option value="25">25</option>
													<option value="26">26</option>
													<option value="27">27</option>
													<option value="28">28</option>
													<option value="29">29</option>
													<option value="30">30</option>
													<option value="31">31</option>
													<option value="32">32</option>
													<option value="33">33</option>
													<option value="34">34</option>
													<option value="35">35</option>
													<option value="36">36</option>
													<option value="37">37</option>
													<option value="38">38</option>
													<option value="39">39</option>
													<option value="40">40</option>
													<option value="41">41</option>
													<option value="42">42</option>
													<option value="43">43</option>
													<option value="44">44</option>
													<option value="45">45</option>
													<option value="46">46</option>
													<option value="47">47</option>
													<option value="48">48</option>
													<option value="49">49</option>
													<option value="50">50</option>
													<option value="51">51</option>
													<option value="52">52</option>
													<option value="53">53</option>
													<option value="54">54</option>
													<option value="55">55</option>
													<option value="56">56</option>
													<option value="57">57</option>
													<option value="58">58</option>
													<option value="59">59</option>
												</select>

											</div>
											<div class="form-group">
												<label for="article_title">标题</label><input
													class="col-md-12" id="article_title" name="article[title]"
													size="30" type="text">
											</div>
											<div class="form-group">
												<label for="article_description">内容</label>
												<div class="qeditor_border">
													<textarea class="description qeditor" cols="40"
														id="article_description" name="article[description]"
														rows="20" style="display: none;"></textarea>
													<div class="qeditor_toolbar">
														<a href="#" onclick="return QEditor.ac(this,'bold');"
															class="qe-bold"><span class="icon-bold"></span></a> <a
															href="#" onclick="return QEditor.ac(this,'italic');"
															class="qe-italic"><span class="icon-italic"></span></a> <a
															href="#"
															onclick="return QEditor.ac(this,'underline');"
															class="qe-underline"><span class="icon-underline"></span></a>
														<a href="#"
															onclick="return QEditor.ac(this,'strikethrough');"
															class="qe-strikethrough"><span
															class="icon-strikethrough"></span></a> <span class="vline"></span>
														<a href="#"
															onclick="return QEditor.ac(this,'insertorderedlist');"
															class="qe-ol"><span class="icon-list-ol"></span></a> <a
															href="#"
															onclick="return QEditor.ac(this,'insertunorderedlist');"
															class="qe-ul"><span class="icon-list-ul"></span></a> <a
															href="#" onclick="return QEditor.ac(this,'indent')"
															class="qe-indent"><span class="icon-indent-right"></span></a>
														<a href="#"
															onclick="return QEditor.ac(this,'outdent')"
															class="qe-outdent"><span class="icon-indent-left"></span></a>
														<span class="vline"></span> <a href="#"
															onclick="return QEditor.ac(this,'insertHorizontalRule');"
															class="qe-hr"><span class="icon-minus"></span></a> <a
															href="#"
															onclick="return QEditor.ac(this,'formatBlock','blockquote');"
															class="qe-blockquote"><span class="icon-quote-left"></span></a>
														<a href="#"
															onclick="return QEditor.ac(this,'formatBlock','pre');"
															class="qe-pre"><span class="icon-code"></span></a> <a
															href="#"
															onclick="return QEditor.ac(this,'createLink');"
															class="qe-link"><span class="icon-link"></span></a> <a
															href="#" id="qe-image" style="display: none;"><span
															class="icon-picture"></span></a>
														<object type="application/x-shockwave-flash"
															data="/uploadify.swf" width="20" height="14"
															id="qe-imageUploader" style="visibility: visible;">
															<param name="quality" value="high">
															<param name="wmode" value="opaque">
															<param name="allowScriptAccess" value="always">
															<param name="flashvars"
																value="uploadifyID=qe-image&amp;pagepath=/menu_items/8122/&amp;buttonImg=/assets/qe-picture.png&amp;buttonText=Browse&amp;script=/attachments&amp;folder=&amp;scriptData=account_id%3D10387%26img_version%3Dembed&amp;width=20&amp;height=14&amp;wmode=opaque&amp;method=POST&amp;queueSizeLimit=20&amp;simUploadLimit=1&amp;fileDesc=*.gif, *.jpg, *.png, *.jpeg&amp;multi=true&amp;auto=true&amp;sizeLimit=5242880&amp;fileDataName=Filedata&amp;queueID=link_modal_qeditor_preview_description">
														</object>
													</div>
													<div class="qeditor_preview clearfix description qeditor"
														style="overflow: scroll;" contenteditable="true"
														id="link_modal_qeditor_preview_description"></div>
												</div>
											</div>
											<input class="btn btn-primary" name="commit" type="submit"
												value="保存"> | <a class="btn btn-link" href="#"
												onclick="$(&quot;.article-toggle&quot;).slideToggle(); return false;">取消</a>
										</form>
									</div>
									<div class="article-toggle">
										<div class="btn-toolbar">
											<a class="btn btn-primary" href="#"
												onclick="$(&quot;.article-toggle&quot;).slideToggle(); return false;">新建文章</a>
											<form accept-charset="UTF-8" action="/articles"
												class="form-inline pull-right" method="post"
												onsubmit="return false">
												<div style="margin: 0; padding: 0; display: inline">
													<input name="utf8" type="hidden" value="✓"><input
														name="authenticity_token" type="hidden"
														value="d7SU0v3HNgFCdoCmdRtLj+BeVaQcFwQD29dTsUF1qcI=">
												</div>
												<label class="checkbox"> 分组 <select
													id="search_article_grp_id" name="search[article_grp_id]"><option
															value="">未分组</option></select>
												</label>
												<button class="btn article-search-btn">搜索</button>
											</form>
										</div>

										<ul id="article-list" class="unstyled"></ul>
										<div id="article_pagination" class="pagination"></div>
									</div>
								</div>

								<div class="tab-pane well" id="tab_tel">
									<div class="tel-toggle" style="display: none">
										<form accept-charset="UTF-8" action="/tels" class="new_tel"
											data-remote="true" id="new_tel" method="post">
											<div style="margin: 0; padding: 0; display: inline">
												<input name="utf8" type="hidden" value="✓"><input
													name="authenticity_token" type="hidden"
													value="d7SU0v3HNgFCdoCmdRtLj+BeVaQcFwQD29dTsUF1qcI=">
											</div>
											<div class="form-group">
												<label for="tel_value">电话(一键拨号)</label> <input
													class="col-md-6" id="tel_value" name="tel[value]" size="30"
													type="text">
											</div>
											<input class="btn btn-primary" name="commit" type="submit"
												value="保存">
										</form>
									</div>
									<div class="tel-toggle">
										<div class="btn-toolbar">
											<a class="btn btn-primary" href="#"
												onclick="$(&quot;.tel-toggle&quot;).slideToggle(); return false;">新建电话</a>
										</div>
										<ul id="tel-list" class="unstyled"></ul>
									</div>
								</div>

								<div class="tab-pane well" id="tab_page">
									<ul id="page-list" class="unstyled"></ul>
								</div>
							</div>

						</div>
						<div class="modal-footer">
							<a data-dismiss="modal" href="javascript:void(0)"
								class="btn default">关闭</a>
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
