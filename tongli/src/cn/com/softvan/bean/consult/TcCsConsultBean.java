/*
 * BEAN类 咨询信息
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.04.16  wuxiaogang      程序.发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.bean.consult;

import cn.com.softvan.dao.entity.BaseEntity;

/**
 * <p>
 * 咨询信息 BEAN
 * </p>
 * 
 * @author wuxiaogang
 * 
 */
public class TcCsConsultBean extends BaseEntity {

	/** 咨询id */
	private String id;
	/** 用户id */
	private String user_id;
	/** 客服ID */
	private String cs_id;
	/** 状态 0待处理1处理中 2已处理 3完结*/
	private String consult_status;
	/** 完成时间 */
	private String finish_time;
	/** 用户名称 */
	private String user_name;
	//-----------------------------
	/** 客户名称备注 */
	private String remarkname;
	/** 客服名称 */
	private String cs_name;
	/**
	 * 咨询id取得
	 * @return 咨询id
	 */
	public String getId() {
	    return id;
	}
	/**
	 * 咨询id设定
	 * @param id 咨询id
	 */
	public void setId(String id) {
	    this.id = id;
	}
	/**
	 * 用户id取得
	 * @return 用户id
	 */
	public String getUser_id() {
	    return user_id;
	}
	/**
	 * 用户id设定
	 * @param user_id 用户id
	 */
	public void setUser_id(String user_id) {
	    this.user_id = user_id;
	}
	/**
	 * 客服ID取得
	 * @return 客服ID
	 */
	public String getCs_id() {
	    return cs_id;
	}
	/**
	 * 客服ID设定
	 * @param cs_id 客服ID
	 */
	public void setCs_id(String cs_id) {
	    this.cs_id = cs_id;
	}
	/**
	 * 状态 0待处理1处理中 2已处理 3完结取得
	 * @return 状态 0待处理1处理中 2已处理 3完结
	 */
	public String getConsult_status() {
	    return consult_status;
	}
	/**
	 * 状态 0待处理1处理中 2已处理 3完结设定
	 * @param consult_status 状态 0待处理1处理中 2已处理 3完结
	 */
	public void setConsult_status(String consult_status) {
	    this.consult_status = consult_status;
	}
	/**
	 * 完成时间取得
	 * @return 完成时间
	 */
	public String getFinish_time() {
	    return finish_time;
	}
	/**
	 * 完成时间设定
	 * @param finish_time 完成时间
	 */
	public void setFinish_time(String finish_time) {
	    this.finish_time = finish_time;
	}
	/**
	 * 用户名称取得
	 * @return 用户名称
	 */
	public String getUser_name() {
	    return user_name;
	}
	/**
	 * 用户名称设定
	 * @param user_name 用户名称
	 */
	public void setUser_name(String user_name) {
	    this.user_name = user_name;
	}
	/**
	 * 客户名称备注取得
	 * @return 客户名称备注
	 */
	public String getRemarkname() {
	    return remarkname;
	}
	/**
	 * 客户名称备注设定
	 * @param remarkname 客户名称备注
	 */
	public void setRemarkname(String remarkname) {
	    this.remarkname = remarkname;
	}
	/**
	 * 客服名称取得
	 * @return 客服名称
	 */
	public String getCs_name() {
	    return cs_name;
	}
	/**
	 * 客服名称设定
	 * @param cs_name 客服名称
	 */
	public void setCs_name(String cs_name) {
	    this.cs_name = cs_name;
	}

}