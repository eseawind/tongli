/*
 *  BEAN   评论信息
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.06.01  wuxiaogang      程序.发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.bean.comment;

import cn.com.softvan.bean.BaseBean;

/**
 * <p>
 * 评论信息
 * <p>
 * 
 * @author wuxiaogang
 * 
 */
public class TcCommentBean extends BaseBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1020877789387699896L;

	/** id */
	private String id;
	/** 会员类型 */
	private String member_type;
	/** 会员id */
	private String member_id;
	/** 被评论信息id */
	private String info_id;
	/** 评论内容 */
	private String detail_info;
	/** 评论人名称 */
	private String user_name;
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
	 * 会员类型取得
	 * @return 会员类型
	 */
	public String getMember_type() {
	    return member_type;
	}
	/**
	 * 会员类型设定
	 * @param member_type 会员类型
	 */
	public void setMember_type(String member_type) {
	    this.member_type = member_type;
	}
	/**
	 * 会员id取得
	 * @return 会员id
	 */
	public String getMember_id() {
	    return member_id;
	}
	/**
	 * 会员id设定
	 * @param member_id 会员id
	 */
	public void setMember_id(String member_id) {
	    this.member_id = member_id;
	}
	/**
	 * 被评论信息id取得
	 * @return 被评论信息id
	 */
	public String getInfo_id() {
	    return info_id;
	}
	/**
	 * 被评论信息id设定
	 * @param info_id 被评论信息id
	 */
	public void setInfo_id(String info_id) {
	    this.info_id = info_id;
	}
	/**
	 * 评论内容取得
	 * @return 评论内容
	 */
	public String getDetail_info() {
	    return detail_info;
	}
	/**
	 * 评论内容设定
	 * @param detail_info 评论内容
	 */
	public void setDetail_info(String detail_info) {
	    this.detail_info = detail_info;
	}
	/**
	 * 评论人名称取得
	 * @return 评论人名称
	 */
	public String getUser_name() {
	    return user_name;
	}
	/**
	 * 评论人名称设定
	 * @param user_name 评论人名称
	 */
	public void setUser_name(String user_name) {
	    this.user_name = user_name;
	}

}