/*
 * 基础Entity类  保险公司_第三方机构关联
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
 * <p> 保险公司_第三方机构关联 <p>
 * @author wangzi
 *
 */
public class TcCtInsuranceOrgVsDamageKey extends BaseEntity {
	/**
	 * 保险公司id
	 */
    private String insurance_org_id;

    /**
	 * 第三方机构id
	 */
    private String damage_addrs_id;

	/**
	 * 保险公司idを取得します。
	 * @return 保险公司id
	 */
	public String getInsurance_org_id() {
	    return insurance_org_id;
	}

	/**
	 * 保险公司idを設定します。
	 * @param insurance_org_id 保险公司id
	 */
	public void setInsurance_org_id(String insurance_org_id) {
	    this.insurance_org_id = insurance_org_id;
	}

	/**
	 * 第三方机构idを取得します。
	 * @return 第三方机构id
	 */
	public String getDamage_addrs_id() {
	    return damage_addrs_id;
	}

	/**
	 * 第三方机构idを設定します。
	 * @param damage_addrs_id 第三方机构id
	 */
	public void setDamage_addrs_id(String damage_addrs_id) {
	    this.damage_addrs_id = damage_addrs_id;
	}

	
}