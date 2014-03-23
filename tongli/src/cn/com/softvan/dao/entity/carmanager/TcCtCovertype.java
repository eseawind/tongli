/*
 * 基础Entity类  保单_公司险种
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
 * <p> 保单_公司险种 <p>
 * @author wangzi
 *
 */
public class TcCtCovertype extends BaseEntity {
	/**	ID	**/
	private String	id;	
	/**	保单id	**/
	private String	policyid;	
	/**	公司险种名称	**/
	private String	coveragename;	
	/**	赔偿限额	**/
	private String	limitamount	;	
	/**	固定保费	**/
	private String	basedpremium;	
	/**	标准保费	**/
	private String	standardpremium;
	/**
	 * ID	*取得
	 * @return ID	*
	 */
	public String getId() {
	    return id;
	}
	/**
	 * ID	*设定
	 * @param id ID	*
	 */
	public void setId(String id) {
	    this.id = id;
	}
	/**
	 * 保单id	*取得
	 * @return 保单id	*
	 */
	public String getPolicyid() {
	    return policyid;
	}
	/**
	 * 保单id	*设定
	 * @param policyid 保单id	*
	 */
	public void setPolicyid(String policyid) {
	    this.policyid = policyid;
	}
	/**
	 * 公司险种名称	*取得
	 * @return 公司险种名称	*
	 */
	public String getCoveragename() {
	    return coveragename;
	}
	/**
	 * 公司险种名称	*设定
	 * @param coveragename 公司险种名称	*
	 */
	public void setCoveragename(String coveragename) {
	    this.coveragename = coveragename;
	}
	/**
	 * 赔偿限额	*取得
	 * @return 赔偿限额	*
	 */
	public String getLimitamount() {
	    return limitamount;
	}
	/**
	 * 赔偿限额	*设定
	 * @param limitamount 赔偿限额	*
	 */
	public void setLimitamount(String limitamount) {
	    this.limitamount = limitamount;
	}
	/**
	 * 固定保费	*取得
	 * @return 固定保费	*
	 */
	public String getBasedpremium() {
	    return basedpremium;
	}
	/**
	 * 固定保费	*设定
	 * @param basedpremium 固定保费	*
	 */
	public void setBasedpremium(String basedpremium) {
	    this.basedpremium = basedpremium;
	}
	/**
	 * 标准保费	*取得
	 * @return 标准保费	*
	 */
	public String getStandardpremium() {
	    return standardpremium;
	}
	/**
	 * 标准保费	*设定
	 * @param standardpremium 标准保费	*
	 */
	public void setStandardpremium(String standardpremium) {
	    this.standardpremium = standardpremium;
	}	

	
}