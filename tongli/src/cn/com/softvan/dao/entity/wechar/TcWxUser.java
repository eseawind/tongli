/*
 * 基础Entity类  微信用户信息
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.02.26  wangzi           程序发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.dao.entity.wechar;

import cn.com.softvan.dao.entity.BaseEntity;

/**
 * <p>
 * 微信用户信息
 * <p>
 * 
 * @author wangzi
 * 
 */
public class TcWxUser extends BaseEntity {
	/** id **/
	private String id;
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
	/**
	 * id *を取得します。
	 * @return id *
	 */
	public String getId() {
	    return id;
	}
	/**
	 * id *を設定します。
	 * @param id id *
	 */
	public void setId(String id) {
	    this.id = id;
	}
	/**
	 * 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。 *を取得します。
	 * @return 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。 *
	 */
	public String getSubscribe() {
	    return subscribe;
	}
	/**
	 * 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。 *を設定します。
	 * @param subscribe 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。 *
	 */
	public void setSubscribe(String subscribe) {
	    this.subscribe = subscribe;
	}
	/**
	 * 用户的标识，对当前公众号唯一 *を取得します。
	 * @return 用户的标识，对当前公众号唯一 *
	 */
	public String getOpenid() {
	    return openid;
	}
	/**
	 * 用户的标识，对当前公众号唯一 *を設定します。
	 * @param openid 用户的标识，对当前公众号唯一 *
	 */
	public void setOpenid(String openid) {
	    this.openid = openid;
	}
	/**
	 * 昵称 *を取得します。
	 * @return 昵称 *
	 */
	public String getNickname() {
	    return nickname;
	}
	/**
	 * 昵称 *を設定します。
	 * @param nickname 昵称 *
	 */
	public void setNickname(String nickname) {
	    this.nickname = nickname;
	}
	/**
	 * 备注名 *を取得します。
	 * @return 备注名 *
	 */
	public String getRemarkname() {
	    return remarkname;
	}
	/**
	 * 备注名 *を設定します。
	 * @param remarkname 备注名 *
	 */
	public void setRemarkname(String remarkname) {
	    this.remarkname = remarkname;
	}
	/**
	 * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知 *を取得します。
	 * @return 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知 *
	 */
	public String getSex() {
	    return sex;
	}
	/**
	 * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知 *を設定します。
	 * @param sex 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知 *
	 */
	public void setSex(String sex) {
	    this.sex = sex;
	}
	/**
	 * 语言 *を取得します。
	 * @return 语言 *
	 */
	public String getLanguage() {
	    return language;
	}
	/**
	 * 语言 *を設定します。
	 * @param language 语言 *
	 */
	public void setLanguage(String language) {
	    this.language = language;
	}
	/**
	 * 国家 *を取得します。
	 * @return 国家 *
	 */
	public String getCountry() {
	    return country;
	}
	/**
	 * 国家 *を設定します。
	 * @param country 国家 *
	 */
	public void setCountry(String country) {
	    this.country = country;
	}
	/**
	 * 省份 *を取得します。
	 * @return 省份 *
	 */
	public String getProvince() {
	    return province;
	}
	/**
	 * 省份 *を設定します。
	 * @param province 省份 *
	 */
	public void setProvince(String province) {
	    this.province = province;
	}
	/**
	 * 城市 *を取得します。
	 * @return 城市 *
	 */
	public String getCity() {
	    return city;
	}
	/**
	 * 城市 *を設定します。
	 * @param city 城市 *
	 */
	public void setCity(String city) {
	    this.city = city;
	}
	/**
	 * 头像url *を取得します。
	 * @return 头像url *
	 */
	public String getHeadimgurl() {
	    return headimgurl;
	}
	/**
	 * 头像url *を設定します。
	 * @param headimgurl 头像url *
	 */
	public void setHeadimgurl(String headimgurl) {
	    this.headimgurl = headimgurl;
	}
	/**
	 * 用户关注时间 *を取得します。
	 * @return 用户关注时间 *
	 */
	public String getSubscribe_time() {
	    return subscribe_time;
	}
	/**
	 * 用户关注时间 *を設定します。
	 * @param subscribe_time 用户关注时间 *
	 */
	public void setSubscribe_time(String subscribe_time) {
	    this.subscribe_time = subscribe_time;
	}
	/**
	 * 用户所属的groupid *を取得します。
	 * @return 用户所属的groupid *
	 */
	public String getGroupid() {
	    return groupid;
	}
	/**
	 * 用户所属的groupid *を設定します。
	 * @param groupid 用户所属的groupid *
	 */
	public void setGroupid(String groupid) {
	    this.groupid = groupid;
	}

}