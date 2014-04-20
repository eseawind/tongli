/*
 * BEAN    课程表
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.04.07  wuxiaogang      程序.发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.bean.course;

import java.util.List;

import cn.com.softvan.bean.BaseBean;

/**
 * <p>
 * 课程表
 * <p>
 * 
 * @author wuxiaogang
 * 
 */
public class TcCourseSyllabusBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2482423928643657313L;
	/** id */
	private String id;
	/** 课程id */
	private String course_id;
	/** 教师id */
	private String teacher_id;
	/** 教师名称 */
	private String teacher_name;
	/** 课程日期 */
	private String day;
	/** 开始时间 */
	private String begin_time;
	/** 结束时间 */
	private String end_time;
	/** 详细地址 */
	private String addres;
	/** 详情 */
	private String detail_info;
	/** 课程状态 0未完成 1 已完成*/
	private String course_status;
	/** 简介 */
	private String brief_info;
	//--------------------------------------------------------------
	/** 标题 */
	private String title;
	/** 主题 */
	private String subject_id;
	/** 课种 */
	private String course_type;
	/** 时长 */
	private String duration;
	/** 时长单位 */
	private String duration_unit;
	/** 年龄段 */
	private String age_group;
	/** 人数 */
	private String number;
	/** 非会员价 */
	private String market_price;
	/** 会员价 */
	private String member_price;
	/** 是否室内 */
	private String is_indoor;
	/** 是否包含场地费 */
	private String is_site_fee;
	/** 标题图 */
	private String pic_url;
	/** 学员id集合 */
	private List<String> sids;
	//--------------------------------------------------------
	/**学员id*/
	private String student_id;
	/**课程表详情ID*/
	private String course_syllabus_item_id;
	/** 教师得分 */
	private String teacher_score;
	/** 教师得分描述 */
	private String teacher_score_note;
	/** 学员状态 */
	private String student_status;
	/** 学员状态描述 */
	private String student_status_note;
	//--------------------------------------------------------
	/**课程表详情 集合*/
	private List<TcCourseSyllabusItemsBean> itemBeans;
	/**
	 * id取得
	 * @return id
	 */
	public String getId() {
	    return id;
	}
	/**
	 * id设定
	 * @param id id
	 */
	public void setId(String id) {
	    this.id = id;
	}
	/**
	 * 课程id取得
	 * @return 课程id
	 */
	public String getCourse_id() {
	    return course_id;
	}
	/**
	 * 课程id设定
	 * @param course_id 课程id
	 */
	public void setCourse_id(String course_id) {
	    this.course_id = course_id;
	}
	/**
	 * 教师id取得
	 * @return 教师id
	 */
	public String getTeacher_id() {
	    return teacher_id;
	}
	/**
	 * 教师id设定
	 * @param teacher_id 教师id
	 */
	public void setTeacher_id(String teacher_id) {
	    this.teacher_id = teacher_id;
	}
	/**
	 * 教师名称取得
	 * @return 教师名称
	 */
	public String getTeacher_name() {
	    return teacher_name;
	}
	/**
	 * 教师名称设定
	 * @param teacher_name 教师名称
	 */
	public void setTeacher_name(String teacher_name) {
	    this.teacher_name = teacher_name;
	}
	/**
	 * 课程日期取得
	 * @return 课程日期
	 */
	public String getDay() {
	    return day;
	}
	/**
	 * 课程日期设定
	 * @param day 课程日期
	 */
	public void setDay(String day) {
	    this.day = day;
	}
	/**
	 * 开始时间取得
	 * @return 开始时间
	 */
	public String getBegin_time() {
	    return begin_time;
	}
	/**
	 * 开始时间设定
	 * @param begin_time 开始时间
	 */
	public void setBegin_time(String begin_time) {
	    this.begin_time = begin_time;
	}
	/**
	 * 结束时间取得
	 * @return 结束时间
	 */
	public String getEnd_time() {
	    return end_time;
	}
	/**
	 * 结束时间设定
	 * @param end_time 结束时间
	 */
	public void setEnd_time(String end_time) {
	    this.end_time = end_time;
	}
	/**
	 * 详细地址取得
	 * @return 详细地址
	 */
	public String getAddres() {
	    return addres;
	}
	/**
	 * 详细地址设定
	 * @param addres 详细地址
	 */
	public void setAddres(String addres) {
	    this.addres = addres;
	}
	/**
	 * 详情取得
	 * @return 详情
	 */
	public String getDetail_info() {
	    return detail_info;
	}
	/**
	 * 详情设定
	 * @param detail_info 详情
	 */
	public void setDetail_info(String detail_info) {
	    this.detail_info = detail_info;
	}
	/**
	 * 课程状态 0未完成 1 已完成取得
	 * @return 课程状态 0未完成 1 已完成
	 */
	public String getCourse_status() {
	    return course_status;
	}
	/**
	 * 课程状态 0未完成 1 已完成设定
	 * @param course_status 课程状态 0未完成 1 已完成
	 */
	public void setCourse_status(String course_status) {
	    this.course_status = course_status;
	}
	/**
	 * 简介取得
	 * @return 简介
	 */
	public String getBrief_info() {
	    return brief_info;
	}
	/**
	 * 简介设定
	 * @param brief_info 简介
	 */
	public void setBrief_info(String brief_info) {
	    this.brief_info = brief_info;
	}
	/**
	 * 标题取得
	 * @return 标题
	 */
	public String getTitle() {
	    return title;
	}
	/**
	 * 标题设定
	 * @param title 标题
	 */
	public void setTitle(String title) {
	    this.title = title;
	}
	/**
	 * 主题取得
	 * @return 主题
	 */
	public String getSubject_id() {
	    return subject_id;
	}
	/**
	 * 主题设定
	 * @param subject_id 主题
	 */
	public void setSubject_id(String subject_id) {
	    this.subject_id = subject_id;
	}
	/**
	 * 课种取得
	 * @return 课种
	 */
	public String getCourse_type() {
	    return course_type;
	}
	/**
	 * 课种设定
	 * @param course_type 课种
	 */
	public void setCourse_type(String course_type) {
	    this.course_type = course_type;
	}
	/**
	 * 时长取得
	 * @return 时长
	 */
	public String getDuration() {
	    return duration;
	}
	/**
	 * 时长设定
	 * @param duration 时长
	 */
	public void setDuration(String duration) {
	    this.duration = duration;
	}
	/**
	 * 时长单位取得
	 * @return 时长单位
	 */
	public String getDuration_unit() {
	    return duration_unit;
	}
	/**
	 * 时长单位设定
	 * @param duration_unit 时长单位
	 */
	public void setDuration_unit(String duration_unit) {
	    this.duration_unit = duration_unit;
	}
	/**
	 * 年龄段取得
	 * @return 年龄段
	 */
	public String getAge_group() {
	    return age_group;
	}
	/**
	 * 年龄段设定
	 * @param age_group 年龄段
	 */
	public void setAge_group(String age_group) {
	    this.age_group = age_group;
	}
	/**
	 * 人数取得
	 * @return 人数
	 */
	public String getNumber() {
	    return number;
	}
	/**
	 * 人数设定
	 * @param number 人数
	 */
	public void setNumber(String number) {
	    this.number = number;
	}
	/**
	 * 非会员价取得
	 * @return 非会员价
	 */
	public String getMarket_price() {
	    return market_price;
	}
	/**
	 * 非会员价设定
	 * @param market_price 非会员价
	 */
	public void setMarket_price(String market_price) {
	    this.market_price = market_price;
	}
	/**
	 * 会员价取得
	 * @return 会员价
	 */
	public String getMember_price() {
	    return member_price;
	}
	/**
	 * 会员价设定
	 * @param member_price 会员价
	 */
	public void setMember_price(String member_price) {
	    this.member_price = member_price;
	}
	/**
	 * 是否室内取得
	 * @return 是否室内
	 */
	public String getIs_indoor() {
	    return is_indoor;
	}
	/**
	 * 是否室内设定
	 * @param is_indoor 是否室内
	 */
	public void setIs_indoor(String is_indoor) {
	    this.is_indoor = is_indoor;
	}
	/**
	 * 是否包含场地费取得
	 * @return 是否包含场地费
	 */
	public String getIs_site_fee() {
	    return is_site_fee;
	}
	/**
	 * 是否包含场地费设定
	 * @param is_site_fee 是否包含场地费
	 */
	public void setIs_site_fee(String is_site_fee) {
	    this.is_site_fee = is_site_fee;
	}
	/**
	 * 标题图取得
	 * @return 标题图
	 */
	public String getPic_url() {
	    return pic_url;
	}
	/**
	 * 标题图设定
	 * @param pic_url 标题图
	 */
	public void setPic_url(String pic_url) {
	    this.pic_url = pic_url;
	}
	/**
	 * 学员id集合取得
	 * @return 学员id集合
	 */
	public List<String> getSids() {
	    return sids;
	}
	/**
	 * 学员id集合设定
	 * @param sids 学员id集合
	 */
	public void setSids(List<String> sids) {
	    this.sids = sids;
	}
	/**
	 * 学员id取得
	 * @return 学员id
	 */
	public String getStudent_id() {
	    return student_id;
	}
	/**
	 * 学员id设定
	 * @param student_id 学员id
	 */
	public void setStudent_id(String student_id) {
	    this.student_id = student_id;
	}
	/**
	 * 课程表详情ID取得
	 * @return 课程表详情ID
	 */
	public String getCourse_syllabus_item_id() {
	    return course_syllabus_item_id;
	}
	/**
	 * 课程表详情ID设定
	 * @param course_syllabus_item_id 课程表详情ID
	 */
	public void setCourse_syllabus_item_id(String course_syllabus_item_id) {
	    this.course_syllabus_item_id = course_syllabus_item_id;
	}
	/**
	 * 教师得分取得
	 * @return 教师得分
	 */
	public String getTeacher_score() {
	    return teacher_score;
	}
	/**
	 * 教师得分设定
	 * @param teacher_score 教师得分
	 */
	public void setTeacher_score(String teacher_score) {
	    this.teacher_score = teacher_score;
	}
	/**
	 * 教师得分描述取得
	 * @return 教师得分描述
	 */
	public String getTeacher_score_note() {
	    return teacher_score_note;
	}
	/**
	 * 教师得分描述设定
	 * @param teacher_score_note 教师得分描述
	 */
	public void setTeacher_score_note(String teacher_score_note) {
	    this.teacher_score_note = teacher_score_note;
	}
	/**
	 * 学员状态取得
	 * @return 学员状态
	 */
	public String getStudent_status() {
	    return student_status;
	}
	/**
	 * 学员状态设定
	 * @param student_status 学员状态
	 */
	public void setStudent_status(String student_status) {
	    this.student_status = student_status;
	}
	/**
	 * 学员状态描述取得
	 * @return 学员状态描述
	 */
	public String getStudent_status_note() {
	    return student_status_note;
	}
	/**
	 * 学员状态描述设定
	 * @param student_status_note 学员状态描述
	 */
	public void setStudent_status_note(String student_status_note) {
	    this.student_status_note = student_status_note;
	}
	/**
	 * 课程表详情 集合取得
	 * @return 课程表详情 集合
	 */
	public List<TcCourseSyllabusItemsBean> getItemBeans() {
	    return itemBeans;
	}
	/**
	 * 课程表详情 集合设定
	 * @param itemBeans 课程表详情 集合
	 */
	public void setItemBeans(List<TcCourseSyllabusItemsBean> itemBeans) {
	    this.itemBeans = itemBeans;
	}
}