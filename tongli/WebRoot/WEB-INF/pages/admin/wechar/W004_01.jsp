<%--
/*
 * 微信服务_素材管理_文章_编辑 (页面)
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
<title>微信服务-素材管理-文章【车主管家】</title>
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
				$('#wechar,#w004_init').addClass('active');
				$('#wechar_arrow,#wechar_sub_menu_li_arrow').addClass('open');
				$('#wechar_sub_menu,#wechar_sub_menu_li_sub_menu').show();
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
						<li><a href="#">素材管理</a> <i class="fa fa-angle-right"></i></li>
						<li><a href="${basePath}/h/w004_init.ac">文章</a> <i class="fa fa-angle-right"></i></li>
						<li>编辑</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			<!-- END PAGE HEADER-->
			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
				<div class="col-md-12">
					<div class="container-fluid">
						<div class="row-fluid">
							<div class="col-md-12">


								<style>
.qeditor_preview {
	height: 280px;
}

.qeditor_preview .item {
	float: none !important;
	height: auto;
	display: inline-block;
	vertical-align: middle;
}
.description{height: 300px;width: 100%;}
</style>

								<form accept-charset="UTF-8" action="/articles/ljjdwz--611"
									class="edit_article" id="edit_article_13632" method="post">
									<div style="margin: 0; padding: 0; display: inline">
										<input name="utf8" type="hidden" value="✓"><input
											name="_method" type="hidden" value="put"><input
											name="authenticity_token" type="hidden"
											value="/R+iAD/G4/qC0XwornJEJMDRh2dQgwxvj9BNHzftRSs=">
									</div>
									<div class="form-group">
										<label for="article_article_grp_id">分组</label> <select
											class="col-md-6" id="article_article_grp_id"
											name="article[article_grp_id]"><option value="">未分组</option></select>
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
											<option selected="selected" value="2">二月</option>
											<option value="3">三月</option>
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
											<option value="4">4</option>
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
											<option selected="selected" value="28">28</option>
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
											<option selected="selected" value="15">15</option>
											<option value="16">16</option>
											<option value="17">17</option>
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
											<option value="24">24</option>
											<option value="25">25</option>
											<option value="26">26</option>
											<option value="27">27</option>
											<option value="28">28</option>
											<option value="29">29</option>
											<option value="30">30</option>
											<option selected="selected" value="31">31</option>
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
										<label for="article_title">标题</label> <input class="form-control"
											id="article_title" name="article[title]" size="30"
											type="text" value="联系捷道">
									</div>
									<div class="form-group">
										<label for="article_description">内容</label>
										<div class="qeditor_border">
											<textarea class="description qeditor"
												id="article_description" >&lt;p&gt;热线 &lt;a href="tel://4006-734-518"&gt;4006-734-518&lt;/a&gt;&lt;/p&gt;
											&lt;p&gt;固话 &lt;a href="tel://0577-86000083"&gt;0577-86000083&lt;/a&gt;&lt;/p&gt;
											
											&lt;p&gt;QQ 2805686025&lt;/p&gt;
											
											&lt;p&gt;电邮 robin.wu@agideo.com&lt;/p&gt;
											</textarea>
										</div>
									</div>
									<input id="article_message_id" name="article[message_id]"
										type="hidden">

									<div class="form-actions">
										<input class="btn btn-primary" name="commit" type="submit"
											value="保存"> | <a
											href="/articles?search%5Barticle_grp_id%5D=">返回</a>
									</div>
								</form>

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
jQuery(document).ready(function() {
	KindEditor.ready(function(K) {
		K.create('#article_description', {
			 resizeType : 2,
	         uploadJson : '${basePath}/uploadFile?isrich=1',
	         fileManagerJson : '${basePath}/plugins/editor/jsp/file_manager_json.jsp',
			 allowFileManager : true
		});
	});
});
</script>
