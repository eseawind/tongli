/*
 * 基础Entity类  数据字典
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.02.26  wangzi           程序发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.dao.entity.sys;

import cn.com.softvan.dao.entity.BaseEntity;
/**
 * <p> 数据字典 <p>
 * @author wangzi
 *
 */
public class TcSysVariable extends BaseEntity {
	/**
	 * 变量关键字
	 */
	private String variable_id;
	/**
	 * 变量名称
	 */
	private String variable_name;
    /**
     *变量子项关键字
     */
    private String variable_sub_id;

    /**
     * 变量子项名称
     */
    private String variable_sub_name;

	/**
	 * 变量关键字取得
	 * @return 变量关键字
	 */
	public String getVariable_id() {
	    return variable_id;
	}

	/**
	 * 变量关键字设定
	 * @param variable_id 变量关键字
	 */
	public void setVariable_id(String variable_id) {
	    this.variable_id = variable_id;
	}

	/**
	 * 变量名称取得
	 * @return 变量名称
	 */
	public String getVariable_name() {
	    return variable_name;
	}

	/**
	 * 变量名称设定
	 * @param variable_name 变量名称
	 */
	public void setVariable_name(String variable_name) {
	    this.variable_name = variable_name;
	}

	/**
	 * 变量子项关键字取得
	 * @return 变量子项关键字
	 */
	public String getVariable_sub_id() {
	    return variable_sub_id;
	}

	/**
	 * 变量子项关键字设定
	 * @param variable_sub_id 变量子项关键字
	 */
	public void setVariable_sub_id(String variable_sub_id) {
	    this.variable_sub_id = variable_sub_id;
	}

	/**
	 * 变量子项名称取得
	 * @return 变量子项名称
	 */
	public String getVariable_sub_name() {
	    return variable_sub_name;
	}

	/**
	 * 变量子项名称设定
	 * @param variable_sub_name 变量子项名称
	 */
	public void setVariable_sub_name(String variable_sub_name) {
	    this.variable_sub_name = variable_sub_name;
	}




    
}