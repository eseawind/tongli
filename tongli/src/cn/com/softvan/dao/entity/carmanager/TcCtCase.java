/*
 * 基础Entity类  报案_案件表
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
 * 
 * <p> 报案_案件表 <p>
 * @author wangzi
 *
 */
public class TcCtCase extends BaseEntity {
	/**
     * id
     */
    private String id;

    /**
     * 案件号
     */
    private String caseno;

    /**
     * 报案号
     */
    private String reportno;

    /**
     * 保险公司编号
     */
    private String orgCode;

    /**
     * 案件状态0未处理1.处理中2.已处理3.定损4.理赔.5完成
     */
    private String casestatus;

	/**
	 * idを取得します。
	 * @return id
	 */
	public String getId() {
	    return id;
	}

	/**
	 * idを設定します。
	 * @param id id
	 */
	public void setId(String id) {
	    this.id = id;
	}

	/**
	 * 案件号を取得します。
	 * @return 案件号
	 */
	public String getCaseno() {
	    return caseno;
	}

	/**
	 * 案件号を設定します。
	 * @param caseno 案件号
	 */
	public void setCaseno(String caseno) {
	    this.caseno = caseno;
	}

	/**
	 * 报案号を取得します。
	 * @return 报案号
	 */
	public String getReportno() {
	    return reportno;
	}

	/**
	 * 报案号を設定します。
	 * @param reportno 报案号
	 */
	public void setReportno(String reportno) {
	    this.reportno = reportno;
	}

	/**
	 * 保险公司编号を取得します。
	 * @return 保险公司编号
	 */
	public String getOrgCode() {
	    return orgCode;
	}

	/**
	 * 保险公司编号を設定します。
	 * @param orgCode 保险公司编号
	 */
	public void setOrgCode(String orgCode) {
	    this.orgCode = orgCode;
	}

	/**
	 * 案件状态0未处理1.处理中2.已处理3.定损4.理赔.5完成を取得します。
	 * @return 案件状态0未处理1.处理中2.已处理3.定损4.理赔.5完成
	 */
	public String getCasestatus() {
	    return casestatus;
	}

	/**
	 * 案件状态0未处理1.处理中2.已处理3.定损4.理赔.5完成を設定します。
	 * @param casestatus 案件状态0未处理1.处理中2.已处理3.定损4.理赔.5完成
	 */
	public void setCasestatus(String casestatus) {
	    this.casestatus = casestatus;
	}

   
}