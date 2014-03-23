/*
 * 基础Entity类  保险公司信息
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.02.26  wangzi           程序发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.dao.entity.carmanager;

import cn.com.softvan.dao.entity.BaseEntity;
/**
 * <p> 保险公司信息 <p>
 * @author wangzi
 *
 */
public class TcCtInsuranceOrg extends BaseEntity {
	/**
	 * id
	 */
    private String id;

    /**
	 * 公司编号
	 */
    private String org_code;

    /**
	 * 公司名称
	 */
    private String name;

    /**
	 * 地址
	 */
    private String addrs;

    /**
	 * 电话
	 */
    private String tel;
    
    /**
     * 是否与微信系统有合作关系（0无合作1有合作）
     */
    private String is_teamwork;

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
	 * 公司编号取得
	 * @return 公司编号
	 */
	public String getOrg_code() {
	    return org_code;
	}

	/**
	 * 公司编号设定
	 * @param org_code 公司编号
	 */
	public void setOrg_code(String org_code) {
	    this.org_code = org_code;
	}

	/**
	 * 公司名称取得
	 * @return 公司名称
	 */
	public String getName() {
	    return name;
	}

	/**
	 * 公司名称设定
	 * @param name 公司名称
	 */
	public void setName(String name) {
	    this.name = name;
	}

	/**
	 * 地址取得
	 * @return 地址
	 */
	public String getAddrs() {
	    return addrs;
	}

	/**
	 * 地址设定
	 * @param addrs 地址
	 */
	public void setAddrs(String addrs) {
	    this.addrs = addrs;
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
	 * 是否与微信系统有合作关系（0无合作1有合作） 取得
	 * @return 是否与微信系统有合作关系（0无合作1有合作）
	 */
	public String getIs_teamwork() {
		return is_teamwork;
	}

	/**
	 * 是否与微信系统有合作关系（0无合作1有合作）设定
	 * @param is_teamwork 是否与微信系统有合作关系（0无合作1有合作）
	 */
	public void setIs_teamwork(String is_teamwork) {
		this.is_teamwork = is_teamwork;
	}



}