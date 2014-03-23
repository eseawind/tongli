/*
 * 基础Entity类  保费试算_险种
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.02.26  wangzi           程序发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.dao.entity.carmanager;

import java.math.BigDecimal;

import cn.com.softvan.dao.entity.BaseEntity;
/**
 * 
 * <p> 保费试算_险种 <p>
 * @author wangzi
 *
 */
public class TcCtCalcullatetype extends BaseEntity {
	/**
	 * id
	 */
    private String id;

    /**
	 * 试算id
	 */
    private String calcullateid;

    /**
	 * 险种名称
	 */
    private String coveragename;

    /**
	 * 赔偿限额
	 */
    private BigDecimal limitamount;

    /**
	 * 固定保费
	 */
    private BigDecimal basedpremium;

    /**
	 * 标准保费
	 */
    private BigDecimal standardpremium;

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
	 * 试算id取得
	 * @return 试算id
	 */
	public String getCalcullateid() {
	    return calcullateid;
	}

	/**
	 * 试算id设定
	 * @param calcullateid 试算id
	 */
	public void setCalcullateid(String calcullateid) {
	    this.calcullateid = calcullateid;
	}

	/**
	 * 险种名称取得
	 * @return 险种名称
	 */
	public String getCoveragename() {
	    return coveragename;
	}

	/**
	 * 险种名称设定
	 * @param coveragename 险种名称
	 */
	public void setCoveragename(String coveragename) {
	    this.coveragename = coveragename;
	}

	/**
	 * 赔偿限额取得
	 * @return 赔偿限额
	 */
	public BigDecimal getLimitamount() {
	    return limitamount;
	}

	/**
	 * 赔偿限额设定
	 * @param limitamount 赔偿限额
	 */
	public void setLimitamount(BigDecimal limitamount) {
	    this.limitamount = limitamount;
	}

	/**
	 * 固定保费取得
	 * @return 固定保费
	 */
	public BigDecimal getBasedpremium() {
	    return basedpremium;
	}

	/**
	 * 固定保费设定
	 * @param basedpremium 固定保费
	 */
	public void setBasedpremium(BigDecimal basedpremium) {
	    this.basedpremium = basedpremium;
	}

	/**
	 * 标准保费取得
	 * @return 标准保费
	 */
	public BigDecimal getStandardpremium() {
	    return standardpremium;
	}

	/**
	 * 标准保费设定
	 * @param standardpremium 标准保费
	 */
	public void setStandardpremium(BigDecimal standardpremium) {
	    this.standardpremium = standardpremium;
	}

   
}