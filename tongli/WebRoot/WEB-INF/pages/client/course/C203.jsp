<%--
/*
 * 课程--在线报名
 *
 * VERSION  DATE        BY           REASON
 * -------- ----------- ------------ ------------------------------------------
 * 1.00     2014-06-07  wuxiaogang        程序・发布
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
<%@ taglib prefix="customtag" uri="/custom-tags"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../include/title_meta.jsp"%>
<%@ include file="../include/public_js_css.jsp"%>

<link href="${basePath}/js/bootstarp-date/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen"/>
<script type="text/javascript" src="${basePath}/js/bootstarp-date/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="${basePath}/js/bootstarp-date/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
</head>

<body class="page-header-fixed">
	<!-- BEGIN HEADER -->
	<%@ include file="../include/header.jsp"%>
	<!-- END   HEADER -->
	<div class="main pr">
		<div class="c10"></div>
		<div class="w">
			<div class="body fr" style="width: 980px;">
				<div class="title">&nbsp; 在线报名</div>
				<div class="content" style="min-height: 390px;">
					<div class="user_info">
						<form accept-charset="UTF-8" action="${basePath}/c203_save.ac" method="post">
							<input type="hidden" name="bean.id" value="${bean.id}" />
							<ul>
								<li>
									<div class="tit">孩子性别：</div>
									<div class="con">
										<label>
										<input type="radio" name="bean.sex" value="0" id="sex_0">
											男 </label>&nbsp; &nbsp; <label><input type="radio" name="bean.sex" value="1"
											id="sex_1"> 女 </label><em>* 请选择</em>
									</div>
								</li>
								<li> <div class="tit">类型(0培训班1冬夏令营)：</div> <div class="con"> <input name="bean.type" type=text" class="input"> <em>* 类型(0培训班1冬夏令营)</em> </div> </li>
								<li> <div class="tit">报名课程期代码(第几期)：</div> <div class="con"> <input name="bean.code" type=text" class="input"> <em>* 报名课程期代码(第几期)</em> </div> </li>
								<li> <div class="tit">报名地址：</div> <div class="con"> <input name="bean.addres" type=text" class="input"> <em>* 报名地址</em> </div> </li>
								<li> <div class="tit">报名价格：</div> <div class="con"> <input name="bean.price" type=text" class="input"> <em>* 报名价格</em> </div> </li>
								<li> <div class="tit">课程开始时间：</div> <div class="con"> <input name="bean.begin_day" type=text" class="input"> <em>* 课程开始时间</em> </div> </li>
								<li> <div class="tit">课程结束时间：</div> <div class="con"> <input name="bean.end_day" type=text" class="input"> <em>* 课程结束时间</em> </div> </li>
								<li> <div class="tit">缴费日期：</div> <div class="con"> <input name="bean.pay_day" type=text" class="input"> <em>* 缴费日期</em> </div> </li>
								<li> <div class="tit">经办人：</div> <div class="con"> <input name="bean.agent" type=text" class="input"> <em>* 经办人</em> </div> </li>
								<li> <div class="tit">学员号：</div> <div class="con"> <input name="bean.student_num" type=text" class="input"> <em>* 学员号</em> </div> </li>
								<li> <div class="tit">姓名：</div> <div class="con"> <input name="bean.name" type=text" class="input"> <em>* 姓名</em> </div> </li>
								<li> <div class="tit">性别：</div> <div class="con"> <input name="bean.sex" type=text" class="input"> <em>* 性别</em> </div> </li>
								<li> <div class="tit">家庭电话：</div> <div class="con"> <input name="bean.tel" type=text" class="input"> <em>* 家庭电话</em> </div> </li>
								<li> <div class="tit">移动电话：</div> <div class="con"> <input name="bean.cell_tel" type=text" class="input"> <em>* 移动电话</em> </div> </li>
								<li> <div class="tit">国籍：</div> <div class="con"> <input name="bean.nationality" type=text" class="input"> <em>* 国籍</em> </div> </li>
								<li> <div class="tit">生日：</div> <div class="con"> <input name="bean.birthday" type=text" class="input"> <em>* 生日</em> </div> </li>
								<li> <div class="tit">学校：</div> <div class="con"> <input name="bean.school" type=text" class="input"> <em>* 学校</em> </div> </li>
								<li> <div class="tit">家庭住址：</div> <div class="con"> <input name="bean.home_address" type=text" class="input"> <em>* 家庭住址</em> </div> </li>
								<li> <div class="tit">邮箱：</div> <div class="con"> <input name="bean.email" type=text" class="input"> <em>* 邮箱</em> </div> </li>
								<li> <div class="tit">学员身份证：</div> <div class="con"> <input name="bean.card_num" type=text" class="input"> <em>* 学员身份证</em> </div> </li>
								<li> <div class="tit">接送时间：</div> <div class="con"> <input name="bean.shuttle_time" type=text" class="input"> <em>* 接送时间</em> </div> </li>
								<li> <div class="tit">其它：</div> <div class="con"> <input name="bean.other_note" type=text" class="input"> <em>* 其它</em> </div> </li>
								<li> <div class="tit">学员/监护人姓名：</div> <div class="con"> <input name="bean.guardian" type=text" class="input"> <em>* 学员/监护人姓名</em> </div> </li>
								<li> <div class="tit">游泳技能调查：</div> <div class="con"> <input name="bean.swim_survey" type=text" class="input"> <em>* 游泳技能调查</em> </div> </li>
								<li> <div class="tit">篮球技能：</div> <div class="con"> <input name="bean.basketball_skills" type=text" class="input"> <em>* 篮球技能</em> </div> </li>
								<li> <div class="tit">网球技能：</div> <div class="con"> <input name="bean.tennis_skills" type=text" class="input"> <em>* 网球技能</em> </div> </li>
								<li> <div class="tit">羽毛球技能：</div> <div class="con"> <input name="bean.badminton_skills" type=text" class="input"> <em>* 羽毛球技能</em> </div> </li>
								<li> <div class="tit">空手道技能：</div> <div class="con"> <input name="bean.karate_skills" type=text" class="input"> <em>* 空手道技能</em> </div> </li>
								<li> <div class="tit">轮滑：</div> <div class="con"> <input name="bean.inline_skaters_skills" type=text" class="input"> <em>* 轮滑</em> </div> </li>
								<li> <div class="tit">游泳技能：</div> <div class="con"> <input name="bean.swim_skills" type=text" class="input"> <em>* 游泳技能</em> </div> </li>
							</ul>
							<div class="c10"></div>
							<div  style="margin-left: 100px;">
							<input class="reg_submit" type="submit" value="提 交" />
							</div>
							<div class="c10"></div>
						</form>
					</div>

				</div>
			</div>
			<div class="c10"></div>
		</div>

	</div>
	<div class="c10"></div>
	</div>
	<!-- BEGIN FOOTER -->
	<%@ include file="../include/footer.jsp"%>
	<!-- END FOOTER -->
</body>
</html>
