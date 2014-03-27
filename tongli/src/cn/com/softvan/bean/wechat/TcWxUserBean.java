/*
 * BEAN类  微信用户信息
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.02.26  wangzi           程序发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.bean.wechat;

import cn.com.softvan.bean.BaseBean;
/**
 * <p> 微信用户信息 <p>
 * @author wangzi
 *
 */
public class TcWxUserBean extends BaseBean {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8952825809433839406L;
	/** 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。 **/
	private String subscribe;
	/** 用户的标识，对当前公众号唯一 **/
	private String openid;
	/** 昵称 **/
	private String nickname;
	/** 备注名 **/
	private String remarkname;
	/** 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知 **/
	private String sex;
	/** 语言 **/
	private String language;
	/** 国家 **/
	private String country;
	/** 省份 **/
	private String province;
	/** 城市 **/
	private String city;
	/** 头像url **/
	private String headimgurl;
	/** 用户关注时间 **/
	private String subscribe_time;
	/** 用户所属的groupid **/
	private String groupid;
	/** total	 关注该公众账号的总用户数*/
	private Long total;
	/** count	 拉取的OPENID个数，最大值为10000*/
	private Integer count;
	/** next_openid	 拉取列表的后一个用户的OPENID*/
	private String next_openid;
	/**
	 * 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。 *取得
	 * @return 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。 *
	 */
	public String getSubscribe() {
	    return subscribe;
	}
	/**
	 * 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。 *设定
	 * @param subscribe 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。 *
	 */
	public void setSubscribe(String subscribe) {
	    this.subscribe = subscribe;
	}
	/**
	 * 用户的标识，对当前公众号唯一 *取得
	 * @return 用户的标识，对当前公众号唯一 *
	 */
	public String getOpenid() {
	    return openid;
	}
	/**
	 * 用户的标识，对当前公众号唯一 *设定
	 * @param openid 用户的标识，对当前公众号唯一 *
	 */
	public void setOpenid(String openid) {
	    this.openid = openid;
	}
	/**
	 * 昵称 *取得
	 * @return 昵称 *
	 */
	public String getNickname() {
	    return nickname;
	}
	/**
	 * 昵称 *设定
	 * @param nickname 昵称 *
	 */
	public void setNickname(String nickname) {
	    this.nickname = nickname;
	}
	/**
	 * 备注名 *取得
	 * @return 备注名 *
	 */
	public String getRemarkname() {
	    return remarkname;
	}
	/**
	 * 备注名 *设定
	 * @param remarkname 备注名 *
	 */
	public void setRemarkname(String remarkname) {
	    this.remarkname = remarkname;
	}
	/**
	 * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知 *取得
	 * @return 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知 *
	 */
	public String getSex() {
	    return sex;
	}
	/**
	 * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知 *设定
	 * @param sex 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知 *
	 */
	public void setSex(String sex) {
	    this.sex = sex;
	}
	/**
	 * 语言 *取得
	 * @return 语言 *
	 */
	public String getLanguage() {
	    return language;
	}
	/**
	 * 语言 *设定
	 * @param language 语言 *
	 */
	public void setLanguage(String language) {
	    this.language = language;
	}
	/**
	 * 国家 *取得
	 * @return 国家 *
	 */
	public String getCountry() {
	    return country;
	}
	/**
	 * 国家 *设定
	 * @param country 国家 *
	 */
	public void setCountry(String country) {
	    this.country = country;
	}
	/**
	 * 省份 *取得
	 * @return 省份 *
	 */
	public String getProvince() {
	    return province;
	}
	/**
	 * 省份 *设定
	 * @param province 省份 *
	 */
	public void setProvince(String province) {
	    this.province = province;
	}
	/**
	 * 城市 *取得
	 * @return 城市 *
	 */
	public String getCity() {
	    return city;
	}
	/**
	 * 城市 *设定
	 * @param city 城市 *
	 */
	public void setCity(String city) {
	    this.city = city;
	}
	/**
	 * 头像url *取得
	 * @return 头像url *
	 */
	public String getHeadimgurl() {
	    return headimgurl;
	}
	/**
	 * 头像url *设定
	 * @param headimgurl 头像url *
	 */
	public void setHeadimgurl(String headimgurl) {
	    this.headimgurl = headimgurl;
	}
	/**
	 * 用户关注时间 *取得
	 * @return 用户关注时间 *
	 */
	public String getSubscribe_time() {
	    return subscribe_time;
	}
	/**
	 * 用户关注时间 *设定
	 * @param subscribe_time 用户关注时间 *
	 */
	public void setSubscribe_time(String subscribe_time) {
	    this.subscribe_time = subscribe_time;
	}
	/**
	 * 用户所属的groupid *取得
	 * @return 用户所属的groupid *
	 */
	public String getGroupid() {
	    return groupid;
	}
	/**
	 * 用户所属的groupid *设定
	 * @param groupid 用户所属的groupid *
	 */
	public void setGroupid(String groupid) {
	    this.groupid = groupid;
	}
	/**
	 * total	 关注该公众账号的总用户数取得
	 * @return total	 关注该公众账号的总用户数
	 */
	public Long getTotal() {
	    return total;
	}
	/**
	 * total	 关注该公众账号的总用户数设定
	 * @param total total	 关注该公众账号的总用户数
	 */
	public void setTotal(Long total) {
	    this.total = total;
	}
	/**
	 * count	 拉取的OPENID个数，最大值为10000取得
	 * @return count	 拉取的OPENID个数，最大值为10000
	 */
	public Integer getCount() {
	    return count;
	}
	/**
	 * count	 拉取的OPENID个数，最大值为10000设定
	 * @param count count	 拉取的OPENID个数，最大值为10000
	 */
	public void setCount(Integer count) {
	    this.count = count;
	}
	/**
	 * next_openid	 拉取列表的后一个用户的OPENID取得
	 * @return next_openid	 拉取列表的后一个用户的OPENID
	 */
	public String getNext_openid() {
	    return next_openid;
	}
	/**
	 * next_openid	 拉取列表的后一个用户的OPENID设定
	 * @param next_openid next_openid	 拉取列表的后一个用户的OPENID
	 */
	public void setNext_openid(String next_openid) {
	    this.next_openid = next_openid;
	}
}