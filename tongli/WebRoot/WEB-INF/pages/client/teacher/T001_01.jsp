<%--
/*
 * 教师-课程列表-已完成
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-04-17  wuxiaogang        程序・发布
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
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="customtag" uri="/custom-tags"%>

<c:if test="${beans!=null && fn:length(beans)>0 }">

<style>
.grade p{float: left;text-align: left;color: red;}
.j-pl-photolist-ul{list-style: none;width:710px;}
.j-pl-photoitem{list-style: none;float: left;width: 152px;height:150px;margin: 10px;}
.mod-photo-item{width: 152px;margin: 13px;}
.item-bd{}
.item-bd img{width: 152px; height: 129px; margin: 0px;border: 0px;}
</style>
<script type="text/javascript" src="${basePath}/js/jquery.form.js"></script>
<c:forEach items="${beans}" var="bean" varStatus="i">
	<div class="item_li item_li_0">${i.index+1}. ${bean.title}</div>
	<div class="item_con item_con_0">
	<div class="grade">
		<table  class="table table-striped table-condensed" >
			<tr>
				<td width="118" height="30" align="right"><strong>时间：</strong></td>
				<td width="474">${bean.day}&nbsp;${bean.begin_time}&nbsp;${bean.end_time}</td>
			</tr>
			<tr class="alert alert-success">
				<td height="30" align="right"><strong>地点：</strong></td>
				<td>${bean.addres}</td>
			</tr>
		</table>
		<table  class="table table-striped table-condensed" >
			<tr>
				<td height="30" align="center" colspan="2">
			============学员签到==========
				</td>
			</tr>
			<tr>
				<td align="left" colspan="2">
					<form id="${bean.id}" accept-charset="UTF-8"  action="${basePath}/t001_save.ac"  method="post">
						<s:token></s:token>
						<input type="hidden" name="course_title" value="${bean.title}">
						<input type="hidden" name="course_syllabus_id" value="${bean.id}">
						<c:set var="num" value="0"/>
						<div class="panel panel-success">
							<div class="panel-body">
								<ul>
						<c:forEach items="${bean.itemBeans}" var="s_bean">
						
								<li><strong>学员名称：</strong>
									${s_bean.name}
								</li>
							<li>
								对我的评价:
									<c:choose>
										<c:when test="${ s_bean.teacher_score!=null}">
											<c:if test="${s_bean.teacher_score=='0'}">差</c:if>
											<c:if test="${s_bean.teacher_score=='1'}">良</c:if>
											<c:if test="${s_bean.teacher_score=='2'}">优</c:if>
												${s_bean.teacher_score_note}
										</c:when>
										<c:otherwise>
											暂未评价
										</c:otherwise>
									</c:choose>
							</li>
							<li style="color: red;">
									${s_bean.name}上课情况:
								<c:choose>
									<c:when test="${s_bean.student_status!=null}">
										<c:if test="${s_bean.student_status=='0'}">已到</c:if>
										<c:if test="${s_bean.student_status=='1'}">旷课</c:if>
										<c:if test="${s_bean.student_status=='2'}">请假</c:if>
											${s_bean.student_status_note}
										<c:set var="num" value="${num+1}"/>
									</c:when>
									<c:otherwise>
											<input type="hidden" name="item_ids" value="${s_bean.id}">
											<input type="hidden" name="sid${s_bean.id}" value="${s_bean.student_id}">
											<input type="hidden" name="sname${s_bean.id}" value="${s_bean.name}">
											<label><input type="radio" class="xx2${bean.id}" checked="checked" name="sstatus${s_bean.id}" value="0">已到</label>
											<label><input type="radio" class="xx2${bean.id}" name="sstatus${s_bean.id}" value="1">旷课</label>
											<label><input type="radio" class="xx2${bean.id}" name="sstatus${s_bean.id}" value="2">请假</label>
											<input style="width:400px;height: 30px;" class=" xx2${bean.id}" name="sstatus_note${s_bean.id}"/>
									</c:otherwise>
								</c:choose>
							</li>
					</c:forEach>
					</ul>
						</div>
					</div>
					<div style="clear: both;"></div>
					<c:if test="${num!=fn:length(bean.itemBeans)}">
					<div class="panel panel-success">
						<div class="panel-heading">
							<button style="margin-left:300px;" type="submit" id="b_${bean.id}"class="btn blue"  onclick="if(confirm('确认提交学员签到情况吗?')){submitFrom2('${bean.id}');}">签到完成</button></h3>
						</div>
					</div>
					</c:if>
				</form>
				</td>
			</tr>
		</table>
		<table  class="table table-striped table-condensed" >
			<tr>
				<td height="30" align="center" colspan="2">
					============课堂详情==========
				</td>
			</tr>
			<tr>
				<td align="left" colspan="2">
					<form id="fileupload" action="${basePath}/uploadFile?isrich=1" method="POST" enctype="multipart/form-data">
						<s:token></s:token>
						<input type="hidden" name="course_syllabus_id" value="${bean.id}">
						<div class="row fileupload-buttonbar">
							<div class="col-lg-12">
								<span class="btn green fileinput-button">
								<i class="fa fa-plus"></i>
								<span>添加照片...</span>
								<input type="file" name="files[]" multiple>
								</span>
								<button type="submit" class="btn blue start">
								<i class="fa fa-upload"></i>
								<span>开始上传</span>
								</button>
								<button type="reset" class="btn yellow cancel">
								<i class="fa fa-ban"></i>
								<span>取消上传</span>
								</button>
								<button type="button" class="btn red delete">
								<i class="fa fa-trash-o"></i>
								<span>删除</span>
								</button>
								<input type="checkbox" class="toggle">
								<span class="fileupload-loading"></span>
							</div>
							<div class="col-lg-10 fileupload-progress fade">
								<div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100">
									<div class="progress-bar progress-bar-success" style="width:0%;"></div>
								</div>
								<div class="progress-extended">&nbsp;</div>
							</div>
						</div>
						<table role="presentation" class="table table-striped clearfix">
							<tbody class="files"></tbody>
						</table>
						<div style="clear: both;"></div>
						<div class="panel panel-success">
							<div class="panel-heading">
									<button style="margin-left:300px;" type="submit" id="c_${bean.id}"class="btn blue"  onclick="submitFrom3('${bean.id}');">相册保存</button></h3>
							</div>
						</div>
					</form>
					<div style="clear: both;"></div>
					<div class="panel panel-success">
						<div class="panel-body">
							<ul>
								<li>The maximum file size for uploads in this demo is <strong>5 MB</strong> (default file size is unlimited).</li>
								<li>Only image files (<strong>JPG, GIF, PNG</strong>) are allowed in this demo (by default there is no file type restriction).</li>
								<li>Uploaded files will be deleted automatically after <strong>5 minutes</strong> (demo setting).</li>
							</ul>
						</div>
					</div>
				</td>
			</tr>
	</table>
</c:forEach>
<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<script id="template-upload" type="text/x-tmpl">
		{% for (var i=0, file; file=o.files[i]; i++) { %}
		    <tr class="template-upload fade">
		        <td>
		            <span class="preview"></span>
		        </td>
		        <td>
		            <p class="name">{%=file.name%}</p>
		            {% if (file.error) { %}
		                <div><span class="label label-danger">Error</span> {%=file.error%}</div>
		            {% } %}
		        </td>
		        <td>
		            <p class="size">{%=o.formatFileSize(file.size)%}</p>
		            {% if (!o.files.error) { %}
		                <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0">
		                <div class="progress-bar progress-bar-success" style="width:0%;"></div>
		                </div>
		            {% } %}
		        </td>
		        <td>
		            {% if (!o.files.error && !i && !o.options.autoUpload) { %}
		                <button class="btn blue start">
		                    <i class="fa fa-upload"></i>
		                    <span>开始</span>
		                </button>
		            {% } %}
		            {% if (!i) { %}
		                <button class="btn red cancel">
		                    <i class="fa fa-ban"></i>
		                    <span>取消</span>
		                </button>
		            {% } %}
		        </td>
		    </tr>
		{% } %}
	</script>
	<!-- The template to display files available for download -->
	<script id="template-download" type="text/x-tmpl">
		{% for (var i=0, file; file=o.files[i]; i++) { %}
		    <tr class="template-download fade">
		        <td>
		            <span class="preview">
		                {% if (file.thumbnailUrl) { %}
		                    <a href="{%=file.url%}" title="{%=file.name%}" download="{%=file.name%}" data-gallery><img src="{%=file.thumbnailUrl%}"></a>
		                {% } %}
		            </span>
		        </td>
		        <td>
		            <p class="name">
		                {% if (file.url) { %}
		                    <a href="{%=file.url%}" title="{%=file.name%}" download="{%=file.name%}" {%=file.thumbnailUrl?'data-gallery':''%}>{%=file.name%}</a>
		                {% } else { %}
		                    <span>{%=file.name%}</span>
		                {% } %}
		            </p>
		            {% if (file.error) { %}
		                <div><span class="label label-danger">Error</span> {%=file.error%}</div>
		            {% } %}
		        </td>
		        <td>
		            <span class="size">{%=o.formatFileSize(file.size)%}</span>
		        </td>
		        <td>
		            {% if (file.deleteUrl) { %}
		                <button class="btn red delete" data-type="{%=file.deleteType%}" data-url="{%=file.deleteUrl%}"{% if (file.deleteWithCredentials) { %} data-xhr-fields='{"withCredentials":true}'{% } %}>
		                    <i class="fa fa-trash-o"></i>
		                    <span>删除</span>
		                </button>
		                <input type="checkbox" name="delete" value="1" class="toggle">
		            {% } else { %}
		                <button class="btn yellow cancel">
		                    <i class="fa fa-ban"></i>
		                    <span>取消</span>
		                </button>
		            {% } %}
		        </td>
		    </tr>
		{% } %}
	</script>
	<!-- BEGIN CORE PLUGINS -->   
<customtag:pagingext func="loadUrlPage" params="'t001_','list1','course_info','&status=0'" />
<script type="text/javascript">
	jQuery(document).ready(function() {
		try{
		FormFileUpload.init();
		}catch(e){alert(e);}
	});


	$(".item_li_0").click(function() {
		$(".item_li").removeClass("on");
		if ($(this).hasClass("on")) {
			$(this).removeClass("on");
		} else {
			$(this).addClass("on");
		}
		$(this).next(".item_con_0").slideToggle();
	});
	// 提交from
	function submitFrom2(from_id) {
		//登录认证
		loginCheck();
		//提交
		jQuery("#"+from_id).ajaxSubmit(function(data) {
			if (data == "1") {
				$('.xx2'+from_id).attr('readonly','readonly');
				$('.xx2'+from_id).attr('disabled','disabled');
				jQuery("#b_"+from_id).remove();
				alert('提交成功!');
			} else {
				alert(data);
			}
		});
	}
	// 提交from
	function submitFrom3(from_id) {
		//登录认证
		loginCheck();
		//提交
		jQuery("#"+from_id).ajaxSubmit(function(data) {
			if (data == "1") {
				//$('.xx2'+from_id).attr('readonly','readonly');
				//$('.xx2'+from_id).attr('disabled','disabled');
				//jQuery("#b_"+from_id).remove();
				alert('提交成功!');
			} else {
				alert(data);
			}
		});
	}
	try{
		$('._struts_0').html('${PAGEROW_OBJECT_KEY.recordCount}');
	}catch(e){}
	
	
</script>
</c:if>