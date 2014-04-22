/**
 *  系统调用车险接口日志 bean类
 * VERSION          DATE                 BY              REASON
 * -------- -------------------  ---------------------- ------------------------------------------
 * 1.00     2014-03-24 下14:32:33             wangzi              程序发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 */
package cn.com.softvan.bean.sys;

import java.util.Date;

/**
 * <p> 系统调用车险接口日志 bean类 <p>
 * @author wangzi
 *
 */
public class TcSysInterfaceLogBean {
	/**
	 * 主键id
	 */
	private String id;
	/**
	 * 日志
	 */
	private String logDescription;
	/**
	 * 创建时间
	 */
	private Date dateCreated;
	/**
	 * 主键idを取得します。
	 * @return 主键id
	 */
	public String getId() {
	    return id;
	}
	/**
	 * 主键idを設定します。
	 * @param id 主键id
	 */
	public void setId(String id) {
	    this.id = id;
	}
	/**
	 * 日志を取得します。
	 * @return 日志
	 */
	public String getLogDescription() {
	    return logDescription;
	}
	/**
	 * 日志を設定します。
	 * @param logDescription 日志
	 */
	public void setLogDescription(String logDescription) {
	    this.logDescription = logDescription;
	}
	/**
	 * 创建时间を取得します。
	 * @return 创建时间
	 */
	public Date getDateCreated() {
	    return dateCreated;
	}
	/**
	 * 创建时间を設定します。
	 * @param dateCreated 创建时间
	 */
	public void setDateCreated(Date dateCreated) {
	    this.dateCreated = dateCreated;
	}

}
