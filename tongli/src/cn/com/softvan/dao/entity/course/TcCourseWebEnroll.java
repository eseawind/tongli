/*
 * 基础Entity类  在线报名
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.06.08  wuxiaogang      程序.发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.dao.entity.course;

import cn.com.softvan.dao.entity.BaseEntity;

/**
 * <p>
 * 在线报名
 * <p>
 * 
 * @author wuxiaogang
 * 
 */
public class TcCourseWebEnroll extends BaseEntity {
	/** 编号 */
	private String id;
	/** 类型(0培训班1冬夏令营) */
	private String type;
	/** 报名课程期代码(第几期) */
	private String code;
	/** 报名地址 */
	private String addres;
	/** 报名价格 */
	private String price;
	/** 课程开始时间 */
	private String begin_day;
	/** 课程结束时间 */
	private String end_day;
	/** 缴费日期 */
	private String pay_day;
	/** 经办人 */
	private String agent;
	/** 学员号 */
	private String student_num;
	/** 姓名 */
	private String name;
	/** 性别 */
	private String sex;
	/** 家庭电话 */
	private String tel;
	/** 移动电话 */
	private String cell_tel;
	/** 国籍 */
	private String nationality;
	/** 生日 */
	private String birthday;
	/** 学校 */
	private String school;
	/** 家庭住址 */
	private String home_address;
	/** 邮箱 */
	private String email;
	/** 学员身份证 */
	private String card_num;
	/** 接送时间 */
	private String shuttle_time;
	/** 其它 */
	private String other_note;
	/** 学员/监护人姓名 */
	private String guardian;
	/** 游泳技能调查 */
	private String swim_survey;
	/** 篮球技能 */
	private String basketball_skills;
	/** 网球技能 */
	private String tennis_skills;
	/** 羽毛球技能 */
	private String badminton_skills;
	/** 空手道技能 */
	private String karate_skills;
	/** 轮滑 */
	private String inline_skaters_skills;
	/** 游泳技能 */
	private String swim_skills;
	/** 状态0未处理1报名失败2报名完成 */
	private String status;
	/** 课程 */
	private String course;
	/**是否状态疾病*/
	private String disease_note;
	/**是否食物过敏*/
	private String allergy_note;
	/**
	 * 编号取得
	 * @return 编号
	 */
	public String getId() {
	    return id;
	}
	/**
	 * 编号设定
	 * @param id 编号
	 */
	public void setId(String id) {
	    this.id = id;
	}
	/**
	 * 类型(0培训班1冬夏令营)取得
	 * @return 类型(0培训班1冬夏令营)
	 */
	public String getType() {
	    return type;
	}
	/**
	 * 类型(0培训班1冬夏令营)设定
	 * @param type 类型(0培训班1冬夏令营)
	 */
	public void setType(String type) {
	    this.type = type;
	}
	/**
	 * 报名课程期代码(第几期)取得
	 * @return 报名课程期代码(第几期)
	 */
	public String getCode() {
	    return code;
	}
	/**
	 * 报名课程期代码(第几期)设定
	 * @param code 报名课程期代码(第几期)
	 */
	public void setCode(String code) {
	    this.code = code;
	}
	/**
	 * 报名地址取得
	 * @return 报名地址
	 */
	public String getAddres() {
	    return addres;
	}
	/**
	 * 报名地址设定
	 * @param addres 报名地址
	 */
	public void setAddres(String addres) {
	    this.addres = addres;
	}
	/**
	 * 报名价格取得
	 * @return 报名价格
	 */
	public String getPrice() {
	    return price;
	}
	/**
	 * 报名价格设定
	 * @param price 报名价格
	 */
	public void setPrice(String price) {
	    this.price = price;
	}
	/**
	 * 课程开始时间取得
	 * @return 课程开始时间
	 */
	public String getBegin_day() {
	    return begin_day;
	}
	/**
	 * 课程开始时间设定
	 * @param begin_day 课程开始时间
	 */
	public void setBegin_day(String begin_day) {
	    this.begin_day = begin_day;
	}
	/**
	 * 课程结束时间取得
	 * @return 课程结束时间
	 */
	public String getEnd_day() {
	    return end_day;
	}
	/**
	 * 课程结束时间设定
	 * @param end_day 课程结束时间
	 */
	public void setEnd_day(String end_day) {
	    this.end_day = end_day;
	}
	/**
	 * 缴费日期取得
	 * @return 缴费日期
	 */
	public String getPay_day() {
	    return pay_day;
	}
	/**
	 * 缴费日期设定
	 * @param pay_day 缴费日期
	 */
	public void setPay_day(String pay_day) {
	    this.pay_day = pay_day;
	}
	/**
	 * 经办人取得
	 * @return 经办人
	 */
	public String getAgent() {
	    return agent;
	}
	/**
	 * 经办人设定
	 * @param agent 经办人
	 */
	public void setAgent(String agent) {
	    this.agent = agent;
	}
	/**
	 * 学员号取得
	 * @return 学员号
	 */
	public String getStudent_num() {
	    return student_num;
	}
	/**
	 * 学员号设定
	 * @param student_num 学员号
	 */
	public void setStudent_num(String student_num) {
	    this.student_num = student_num;
	}
	/**
	 * 姓名取得
	 * @return 姓名
	 */
	public String getName() {
	    return name;
	}
	/**
	 * 姓名设定
	 * @param name 姓名
	 */
	public void setName(String name) {
	    this.name = name;
	}
	/**
	 * 性别取得
	 * @return 性别
	 */
	public String getSex() {
	    return sex;
	}
	/**
	 * 性别设定
	 * @param sex 性别
	 */
	public void setSex(String sex) {
	    this.sex = sex;
	}
	/**
	 * 家庭电话取得
	 * @return 家庭电话
	 */
	public String getTel() {
	    return tel;
	}
	/**
	 * 家庭电话设定
	 * @param tel 家庭电话
	 */
	public void setTel(String tel) {
	    this.tel = tel;
	}
	/**
	 * 移动电话取得
	 * @return 移动电话
	 */
	public String getCell_tel() {
	    return cell_tel;
	}
	/**
	 * 移动电话设定
	 * @param cell_tel 移动电话
	 */
	public void setCell_tel(String cell_tel) {
	    this.cell_tel = cell_tel;
	}
	/**
	 * 国籍取得
	 * @return 国籍
	 */
	public String getNationality() {
	    return nationality;
	}
	/**
	 * 国籍设定
	 * @param nationality 国籍
	 */
	public void setNationality(String nationality) {
	    this.nationality = nationality;
	}
	/**
	 * 生日取得
	 * @return 生日
	 */
	public String getBirthday() {
	    return birthday;
	}
	/**
	 * 生日设定
	 * @param birthday 生日
	 */
	public void setBirthday(String birthday) {
	    this.birthday = birthday;
	}
	/**
	 * 学校取得
	 * @return 学校
	 */
	public String getSchool() {
	    return school;
	}
	/**
	 * 学校设定
	 * @param school 学校
	 */
	public void setSchool(String school) {
	    this.school = school;
	}
	/**
	 * 家庭住址取得
	 * @return 家庭住址
	 */
	public String getHome_address() {
	    return home_address;
	}
	/**
	 * 家庭住址设定
	 * @param home_address 家庭住址
	 */
	public void setHome_address(String home_address) {
	    this.home_address = home_address;
	}
	/**
	 * 邮箱取得
	 * @return 邮箱
	 */
	public String getEmail() {
	    return email;
	}
	/**
	 * 邮箱设定
	 * @param email 邮箱
	 */
	public void setEmail(String email) {
	    this.email = email;
	}
	/**
	 * 学员身份证取得
	 * @return 学员身份证
	 */
	public String getCard_num() {
	    return card_num;
	}
	/**
	 * 学员身份证设定
	 * @param card_num 学员身份证
	 */
	public void setCard_num(String card_num) {
	    this.card_num = card_num;
	}
	/**
	 * 接送时间取得
	 * @return 接送时间
	 */
	public String getShuttle_time() {
	    return shuttle_time;
	}
	/**
	 * 接送时间设定
	 * @param shuttle_time 接送时间
	 */
	public void setShuttle_time(String shuttle_time) {
	    this.shuttle_time = shuttle_time;
	}
	/**
	 * 其它取得
	 * @return 其它
	 */
	public String getOther_note() {
	    return other_note;
	}
	/**
	 * 其它设定
	 * @param other_note 其它
	 */
	public void setOther_note(String other_note) {
	    this.other_note = other_note;
	}
	/**
	 * 学员/监护人姓名取得
	 * @return 学员/监护人姓名
	 */
	public String getGuardian() {
	    return guardian;
	}
	/**
	 * 学员/监护人姓名设定
	 * @param guardian 学员/监护人姓名
	 */
	public void setGuardian(String guardian) {
	    this.guardian = guardian;
	}
	/**
	 * 游泳技能调查取得
	 * @return 游泳技能调查
	 */
	public String getSwim_survey() {
	    return swim_survey;
	}
	/**
	 * 游泳技能调查设定
	 * @param swim_survey 游泳技能调查
	 */
	public void setSwim_survey(String swim_survey) {
	    this.swim_survey = swim_survey;
	}
	/**
	 * 篮球技能取得
	 * @return 篮球技能
	 */
	public String getBasketball_skills() {
	    return basketball_skills;
	}
	/**
	 * 篮球技能设定
	 * @param basketball_skills 篮球技能
	 */
	public void setBasketball_skills(String basketball_skills) {
	    this.basketball_skills = basketball_skills;
	}
	/**
	 * 网球技能取得
	 * @return 网球技能
	 */
	public String getTennis_skills() {
	    return tennis_skills;
	}
	/**
	 * 网球技能设定
	 * @param tennis_skills 网球技能
	 */
	public void setTennis_skills(String tennis_skills) {
	    this.tennis_skills = tennis_skills;
	}
	/**
	 * 羽毛球技能取得
	 * @return 羽毛球技能
	 */
	public String getBadminton_skills() {
	    return badminton_skills;
	}
	/**
	 * 羽毛球技能设定
	 * @param badminton_skills 羽毛球技能
	 */
	public void setBadminton_skills(String badminton_skills) {
	    this.badminton_skills = badminton_skills;
	}
	/**
	 * 空手道技能取得
	 * @return 空手道技能
	 */
	public String getKarate_skills() {
	    return karate_skills;
	}
	/**
	 * 空手道技能设定
	 * @param karate_skills 空手道技能
	 */
	public void setKarate_skills(String karate_skills) {
	    this.karate_skills = karate_skills;
	}
	/**
	 * 轮滑取得
	 * @return 轮滑
	 */
	public String getInline_skaters_skills() {
	    return inline_skaters_skills;
	}
	/**
	 * 轮滑设定
	 * @param inline_skaters_skills 轮滑
	 */
	public void setInline_skaters_skills(String inline_skaters_skills) {
	    this.inline_skaters_skills = inline_skaters_skills;
	}
	/**
	 * 游泳技能取得
	 * @return 游泳技能
	 */
	public String getSwim_skills() {
	    return swim_skills;
	}
	/**
	 * 游泳技能设定
	 * @param swim_skills 游泳技能
	 */
	public void setSwim_skills(String swim_skills) {
	    this.swim_skills = swim_skills;
	}
	/**
	 * 状态0未处理1报名失败2报名完成取得
	 * @return 状态0未处理1报名失败2报名完成
	 */
	public String getStatus() {
	    return status;
	}
	/**
	 * 状态0未处理1报名失败2报名完成设定
	 * @param status 状态0未处理1报名失败2报名完成
	 */
	public void setStatus(String status) {
	    this.status = status;
	}
	/**
	 * 课程取得
	 * @return 课程
	 */
	public String getCourse() {
	    return course;
	}
	/**
	 * 课程设定
	 * @param course 课程
	 */
	public void setCourse(String course) {
	    this.course = course;
	}
	/**
	 * 是否状态疾病取得
	 * @return 是否状态疾病
	 */
	public String getDisease_note() {
	    return disease_note;
	}
	/**
	 * 是否状态疾病设定
	 * @param disease_note 是否状态疾病
	 */
	public void setDisease_note(String disease_note) {
	    this.disease_note = disease_note;
	}
	/**
	 * 是否食物过敏取得
	 * @return 是否食物过敏
	 */
	public String getAllergy_note() {
	    return allergy_note;
	}
	/**
	 * 是否食物过敏设定
	 * @param allergy_note 是否食物过敏
	 */
	public void setAllergy_note(String allergy_note) {
	    this.allergy_note = allergy_note;
	}
}