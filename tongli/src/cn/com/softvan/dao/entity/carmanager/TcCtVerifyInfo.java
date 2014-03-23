package cn.com.softvan.dao.entity.carmanager;

import java.sql.Timestamp;

public class TcCtVerifyInfo {
	
	/** 微信Id */
    private String openid;
    /** 验证对象号码 */
    private String target_code;
    /** 信息类型 */
    private String target_type;
    /** 验证内容 */
    private String content;
 
    /**
     * 插入日期
     */
    private Timestamp insert_date;

	/**
	 * 微信Id取得。
	 * @return 微信Id
	 */
	public String getOpenid() {
	    return openid;
	}

	/**
	 * 微信Id设定。
	 * @param openid 微信Id
	 */
	public void setOpenid(String openid) {
	    this.openid = openid;
	}

	/**
	 * 验证对象号码取得。
	 * @return 验证对象号码
	 */
	public String getTarget_code() {
	    return target_code;
	}

	/**
	 * 验证对象号码设定。
	 * @param target_code 验证对象号码
	 */
	public void setTarget_code(String target_code) {
	    this.target_code = target_code;
	}

	/**
	 * 信息类型取得。
	 * @return 信息类型
	 */
	public String getTarget_type() {
	    return target_type;
	}

	/**
	 * 信息类型设定。
	 * @param target_type 信息类型
	 */
	public void setTarget_type(String target_type) {
	    this.target_type = target_type;
	}

	/**
	 * 验证内容取得。
	 * @return 验证内容
	 */
	public String getContent() {
	    return content;
	}

	/**
	 * 验证内容设定。
	 * @param content 验证内容
	 */
	public void setContent(String content) {
	    this.content = content;
	}

	/**
	 * 插入日期取得。
	 * @return 插入日期
	 */
	public Timestamp getInsert_date() {
	    return insert_date;
	}

	/**
	 * 插入日期设定。
	 * @param insert_date 插入日期
	 */
	public void setInsert_date(Timestamp insert_date) {
	    this.insert_date = insert_date;
	}

	 
	 

}
