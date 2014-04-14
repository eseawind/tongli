/*
 * BEAN    会员基本信息表
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.04.06  wuxiaogang      程序.发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.bean.member;

import java.util.List;

import cn.com.softvan.bean.BaseBean;

/**
 * <p>
 * 会员基本信息表
 * <p>
 * 
 * @author wuxiaogang
 * 
 */
public class TcMemberBean extends BaseBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4679696927369503566L;
	/** id */
	private String id;
	/** 用户名 */
	private String user_name;
	/** 密码 */
	private String passwd;
	/** 会员类型 0教练 1家长(会员) */
	private String user_type;
	/** 用��昵称 */
	private String nickname;
	/** 登录次数 */
	private String login_count;
	/** 最后登入时间 */
	private String last_login;
	/** 绑定手机 */
	private String bind_mobile;
	/** 绑定邮箱 */
	private String bind_email;
	/** 性别 */
	private String sex;
	/** 电话 */
	private String tel;
	/** 真实姓名 */
	private String real_name;
	/** 是否可用 */
	private String is_enabled;
	/** 最后登录IP */
	private String last_login_ip;
	/** 头像 */
	private String pic_url;
	//-------------------------------------detail-----------------------------------------------------------------
	/** 用户名 */
	private String user_id;
	/** 生日 */
	private String birthdate;
	/** 血型 */
	private String blood_type;
	/** 文化程度 */
	private String edu_level;
	/** 从事行业 */
	private String trade;
	/** 从事职业 */
	private String job;
	/** 收入水平 */
	private String income_level;
	/** 行政区划_市级 */
	private String city_id;
	/** 行政区划_村级 */
	private String village_id;
	/** 行政区划_镇级 */
	private String town_id;
	/** 行政区划_县级 */
	private String county_id;
	/** 行政区划_省级 */
	private String province_id;
	/** 行政区划_市级 */
	private String city_name;
	/** 行政区划_村级 */
	private String village_name;
	/** 行政区划_镇级 */
	private String town_name;
	/** 行政区划_县级 */
	private String county_name;
	/** 行政区划_省级 */
	private String province_name;
	/** 详细地址 */
	private String address;
	/** 邮政编码 */
	private String zipcode;
	/** 证件类型 */
	private String credential;
	/** 证件号码 */
	private String credential_code;
	/** QQ */
	private String qq;
	/** MSN */
	private String msn;
	/** 爱好 */
	private String hobby;
	/** ��后修改时间 */
	private String last_update_date;
	/** 详情 */
	private String detail_info;
	//-----------------------------------------------------------------------------------------------
	/** 学员id集合*/
	private List<String> sids;
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
	 * 用户名取得
	 * @return 用户名
	 */
	public String getUser_name() {
	    return user_name;
	}
	/**
	 * 用户名设定
	 * @param user_name 用户名
	 */
	public void setUser_name(String user_name) {
	    this.user_name = user_name;
	}
	/**
	 * 密码取得
	 * @return 密码
	 */
	public String getPasswd() {
	    return passwd;
	}
	/**
	 * 密码设定
	 * @param passwd 密码
	 */
	public void setPasswd(String passwd) {
	    this.passwd = passwd;
	}
	/**
	 * 会员类型取得
	 * @return 会员类型
	 */
	public String getUser_type() {
	    return user_type;
	}
	/**
	 * 会员类型设定
	 * @param user_type 会员类型
	 */
	public void setUser_type(String user_type) {
	    this.user_type = user_type;
	}
	/**
	 * 用��昵称取得
	 * @return 用��昵称
	 */
	public String getNickname() {
	    return nickname;
	}
	/**
	 * 用��昵称设定
	 * @param nickname 用��昵称
	 */
	public void setNickname(String nickname) {
	    this.nickname = nickname;
	}
	/**
	 * 登录次数取得
	 * @return 登录次数
	 */
	public String getLogin_count() {
	    return login_count;
	}
	/**
	 * 登录次数设定
	 * @param login_count 登录次数
	 */
	public void setLogin_count(String login_count) {
	    this.login_count = login_count;
	}
	/**
	 * 最后登入时间取得
	 * @return 最后登入时间
	 */
	public String getLast_login() {
	    return last_login;
	}
	/**
	 * 最后登入时间设定
	 * @param last_login 最后登入时间
	 */
	public void setLast_login(String last_login) {
	    this.last_login = last_login;
	}
	/**
	 * 绑定手机取得
	 * @return 绑定手机
	 */
	public String getBind_mobile() {
	    return bind_mobile;
	}
	/**
	 * 绑定手机设定
	 * @param bind_mobile 绑定手机
	 */
	public void setBind_mobile(String bind_mobile) {
	    this.bind_mobile = bind_mobile;
	}
	/**
	 * 绑定邮箱取得
	 * @return 绑定邮箱
	 */
	public String getBind_email() {
	    return bind_email;
	}
	/**
	 * 绑定邮箱设定
	 * @param bind_email 绑定邮箱
	 */
	public void setBind_email(String bind_email) {
	    this.bind_email = bind_email;
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
	 * 电话取得
	 * @return 电话
	 */
	public String getTel() {
	    return tel;
	}
	/**
	 * 电话设定
	 * @param tel 电话
	 */
	public void setTel(String tel) {
	    this.tel = tel;
	}
	/**
	 * 真实姓名取得
	 * @return 真实姓名
	 */
	public String getReal_name() {
	    return real_name;
	}
	/**
	 * 真实姓名设定
	 * @param real_name 真实姓名
	 */
	public void setReal_name(String real_name) {
	    this.real_name = real_name;
	}
	/**
	 * 是否可用取得
	 * @return 是否可用
	 */
	public String getIs_enabled() {
	    return is_enabled;
	}
	/**
	 * 是否可用设定
	 * @param is_enabled 是否可用
	 */
	public void setIs_enabled(String is_enabled) {
	    this.is_enabled = is_enabled;
	}
	/**
	 * 最后登录IP取得
	 * @return 最后登录IP
	 */
	public String getLast_login_ip() {
	    return last_login_ip;
	}
	/**
	 * 最后登录IP设定
	 * @param last_login_ip 最后登录IP
	 */
	public void setLast_login_ip(String last_login_ip) {
	    this.last_login_ip = last_login_ip;
	}
	/**
	 * 头像取得
	 * @return 头像
	 */
	public String getPic_url() {
	    return pic_url;
	}
	/**
	 * 头像设定
	 * @param pic_url 头像
	 */
	public void setPic_url(String pic_url) {
	    this.pic_url = pic_url;
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
	 * 用户名取得
	 * @return 用户名
	 */
	public String getUser_id() {
	    return user_id;
	}
	/**
	 * 用户名设定
	 * @param user_id 用户名
	 */
	public void setUser_id(String user_id) {
	    this.user_id = user_id;
	}
	/**
	 * 生日取得
	 * @return 生日
	 */
	public String getBirthdate() {
	    return birthdate;
	}
	/**
	 * 生日设定
	 * @param birthdate 生日
	 */
	public void setBirthdate(String birthdate) {
	    this.birthdate = birthdate;
	}
	/**
	 * 血型取得
	 * @return 血型
	 */
	public String getBlood_type() {
	    return blood_type;
	}
	/**
	 * 血型设定
	 * @param blood_type 血型
	 */
	public void setBlood_type(String blood_type) {
	    this.blood_type = blood_type;
	}
	/**
	 * 文化程度取得
	 * @return 文化程度
	 */
	public String getEdu_level() {
	    return edu_level;
	}
	/**
	 * 文化程度设定
	 * @param edu_level 文化程度
	 */
	public void setEdu_level(String edu_level) {
	    this.edu_level = edu_level;
	}
	/**
	 * 从事行业取得
	 * @return 从事行业
	 */
	public String getTrade() {
	    return trade;
	}
	/**
	 * 从事行业设定
	 * @param trade 从事行业
	 */
	public void setTrade(String trade) {
	    this.trade = trade;
	}
	/**
	 * 从事职业取得
	 * @return 从事职业
	 */
	public String getJob() {
	    return job;
	}
	/**
	 * 从事职业设定
	 * @param job 从事职业
	 */
	public void setJob(String job) {
	    this.job = job;
	}
	/**
	 * 收入水平取得
	 * @return 收入水平
	 */
	public String getIncome_level() {
	    return income_level;
	}
	/**
	 * 收入水平设定
	 * @param income_level 收入水平
	 */
	public void setIncome_level(String income_level) {
	    this.income_level = income_level;
	}
	/**
	 * 行政区划_市级取得
	 * @return 行政区划_市级
	 */
	public String getCity_id() {
	    return city_id;
	}
	/**
	 * 行政区划_市级设定
	 * @param city_id 行政区划_市级
	 */
	public void setCity_id(String city_id) {
	    this.city_id = city_id;
	}
	/**
	 * 行政区划_村级取得
	 * @return 行政区划_村级
	 */
	public String getVillage_id() {
	    return village_id;
	}
	/**
	 * 行政区划_村级设定
	 * @param village_id 行政区划_村级
	 */
	public void setVillage_id(String village_id) {
	    this.village_id = village_id;
	}
	/**
	 * 行政区划_镇级取得
	 * @return 行政区划_镇级
	 */
	public String getTown_id() {
	    return town_id;
	}
	/**
	 * 行政区划_镇级设定
	 * @param town_id 行政区划_镇级
	 */
	public void setTown_id(String town_id) {
	    this.town_id = town_id;
	}
	/**
	 * 行政区划_县级取得
	 * @return 行政区划_县级
	 */
	public String getCounty_id() {
	    return county_id;
	}
	/**
	 * 行政区划_县级设定
	 * @param county_id 行政区划_县级
	 */
	public void setCounty_id(String county_id) {
	    this.county_id = county_id;
	}
	/**
	 * 行政区划_省级取得
	 * @return 行政区划_省级
	 */
	public String getProvince_id() {
	    return province_id;
	}
	/**
	 * 行政区划_省级设定
	 * @param province_id 行政区划_省级
	 */
	public void setProvince_id(String province_id) {
	    this.province_id = province_id;
	}
	/**
	 * 行政区划_市级取得
	 * @return 行政区划_市级
	 */
	public String getCity_name() {
	    return city_name;
	}
	/**
	 * 行政区划_市级设定
	 * @param city_name 行政区划_市级
	 */
	public void setCity_name(String city_name) {
	    this.city_name = city_name;
	}
	/**
	 * 行政区划_村级取得
	 * @return 行政区划_村级
	 */
	public String getVillage_name() {
	    return village_name;
	}
	/**
	 * 行政区划_村级设定
	 * @param village_name 行政区划_村级
	 */
	public void setVillage_name(String village_name) {
	    this.village_name = village_name;
	}
	/**
	 * 行政区划_镇级取得
	 * @return 行政区划_镇级
	 */
	public String getTown_name() {
	    return town_name;
	}
	/**
	 * 行政区划_镇级设定
	 * @param town_name 行政区划_镇级
	 */
	public void setTown_name(String town_name) {
	    this.town_name = town_name;
	}
	/**
	 * 行政区划_县级取得
	 * @return 行政区划_县级
	 */
	public String getCounty_name() {
	    return county_name;
	}
	/**
	 * 行政区划_县级设定
	 * @param county_name 行政区划_县级
	 */
	public void setCounty_name(String county_name) {
	    this.county_name = county_name;
	}
	/**
	 * 行政区划_省级取得
	 * @return 行政区划_省级
	 */
	public String getProvince_name() {
	    return province_name;
	}
	/**
	 * 行政区划_省级设定
	 * @param province_name 行政区划_省级
	 */
	public void setProvince_name(String province_name) {
	    this.province_name = province_name;
	}
	/**
	 * 详细地址取得
	 * @return 详细地址
	 */
	public String getAddress() {
	    return address;
	}
	/**
	 * 详细地址设定
	 * @param address 详细地址
	 */
	public void setAddress(String address) {
	    this.address = address;
	}
	/**
	 * 邮政编码取得
	 * @return 邮政编码
	 */
	public String getZipcode() {
	    return zipcode;
	}
	/**
	 * 邮政编码设定
	 * @param zipcode 邮政编码
	 */
	public void setZipcode(String zipcode) {
	    this.zipcode = zipcode;
	}
	/**
	 * 证件类型取得
	 * @return 证件类型
	 */
	public String getCredential() {
	    return credential;
	}
	/**
	 * 证件类型设定
	 * @param credential 证件类型
	 */
	public void setCredential(String credential) {
	    this.credential = credential;
	}
	/**
	 * 证件号码取得
	 * @return 证件号码
	 */
	public String getCredential_code() {
	    return credential_code;
	}
	/**
	 * 证件号码设定
	 * @param credential_code 证件号码
	 */
	public void setCredential_code(String credential_code) {
	    this.credential_code = credential_code;
	}
	/**
	 * QQ取得
	 * @return QQ
	 */
	public String getQq() {
	    return qq;
	}
	/**
	 * QQ设定
	 * @param qq QQ
	 */
	public void setQq(String qq) {
	    this.qq = qq;
	}
	/**
	 * MSN取得
	 * @return MSN
	 */
	public String getMsn() {
	    return msn;
	}
	/**
	 * MSN设定
	 * @param msn MSN
	 */
	public void setMsn(String msn) {
	    this.msn = msn;
	}
	/**
	 * 爱好取得
	 * @return 爱好
	 */
	public String getHobby() {
	    return hobby;
	}
	/**
	 * 爱好设定
	 * @param hobby 爱好
	 */
	public void setHobby(String hobby) {
	    this.hobby = hobby;
	}
	/**
	 * ��后修改时间取得
	 * @return ��后修改时间
	 */
	public String getLast_update_date() {
	    return last_update_date;
	}
	/**
	 * ��后修改时间设定
	 * @param last_update_date ��后修改时间
	 */
	public void setLast_update_date(String last_update_date) {
	    this.last_update_date = last_update_date;
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

}