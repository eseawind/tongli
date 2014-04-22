/*
 * 基础Entity类   商品信息表
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.04.01  wuxiaogang      程序.发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.dao.entity.eb;

import java.math.BigDecimal;

import cn.com.softvan.dao.entity.BaseEntity;

/**
 * <p>
 * 商品信息表
 * <p>
 * 
 * @author wuxiaogang
 * 
 */
public class TcEbProduct extends BaseEntity {
	/** 商品ID */
	private String id;
	/** 商品名称 */
	private String name;
	/** 商品型号 */
	private String model;
	/** 商品分类 */
	private String type_id;
	/** 市场价 */
	private BigDecimal market_price;
	/** 会员价 */
	private BigDecimal member_price;
	/** 商品LOGO图片 */
	private String logo_url;
	/** 库存 */
	private Integer stock;
	/** 总销量 */
	private Integer total_sale;
	/** 商品状态 */
	private String status;
	/** 商品描述 */
	private String intro_url;
	/** 浏览量 */
	private Integer hits;
	/** 是否展示 */
	private String is_show;
	/**
	 * 商品ID取得
	 * @return 商品ID
	 */
	public String getId() {
	    return id;
	}
	/**
	 * 商品ID设定
	 * @param id 商品ID
	 */
	public void setId(String id) {
	    this.id = id;
	}
	/**
	 * 商品名称取得
	 * @return 商品名称
	 */
	public String getName() {
	    return name;
	}
	/**
	 * 商品名称设定
	 * @param name 商品名称
	 */
	public void setName(String name) {
	    this.name = name;
	}
	/**
	 * 商品型号取得
	 * @return 商品型号
	 */
	public String getModel() {
	    return model;
	}
	/**
	 * 商品型号设定
	 * @param model 商品型号
	 */
	public void setModel(String model) {
	    this.model = model;
	}
	/**
	 * 商品分类取得
	 * @return 商品分类
	 */
	public String getType_id() {
	    return type_id;
	}
	/**
	 * 商品分类设定
	 * @param type_id 商品分类
	 */
	public void setType_id(String type_id) {
	    this.type_id = type_id;
	}
	/**
	 * 市场价取得
	 * @return 市场价
	 */
	public BigDecimal getMarket_price() {
	    return market_price;
	}
	/**
	 * 市场价设定
	 * @param market_price 市场价
	 */
	public void setMarket_price(BigDecimal market_price) {
	    this.market_price = market_price;
	}
	/**
	 * 会员价取得
	 * @return 会员价
	 */
	public BigDecimal getMember_price() {
	    return member_price;
	}
	/**
	 * 会员价设定
	 * @param member_price 会员价
	 */
	public void setMember_price(BigDecimal member_price) {
	    this.member_price = member_price;
	}
	/**
	 * 商品LOGO图片取得
	 * @return 商品LOGO图片
	 */
	public String getLogo_url() {
	    return logo_url;
	}
	/**
	 * 商品LOGO图片设定
	 * @param logo_url 商品LOGO图片
	 */
	public void setLogo_url(String logo_url) {
	    this.logo_url = logo_url;
	}
	/**
	 * 库存取得
	 * @return 库存
	 */
	public Integer getStock() {
	    return stock;
	}
	/**
	 * 库存设定
	 * @param stock 库存
	 */
	public void setStock(Integer stock) {
	    this.stock = stock;
	}
	/**
	 * 总销量取得
	 * @return 总销量
	 */
	public Integer getTotal_sale() {
	    return total_sale;
	}
	/**
	 * 总销量设定
	 * @param total_sale 总销量
	 */
	public void setTotal_sale(Integer total_sale) {
	    this.total_sale = total_sale;
	}
	/**
	 * 商品状态取得
	 * @return 商品状态
	 */
	public String getStatus() {
	    return status;
	}
	/**
	 * 商品状态设定
	 * @param status 商品状态
	 */
	public void setStatus(String status) {
	    this.status = status;
	}
	/**
	 * 商品描述取得
	 * @return 商品描述
	 */
	public String getIntro_url() {
	    return intro_url;
	}
	/**
	 * 商品描述设定
	 * @param intro_url 商品描述
	 */
	public void setIntro_url(String intro_url) {
	    this.intro_url = intro_url;
	}
	/**
	 * 浏览量取得
	 * @return 浏览量
	 */
	public Integer getHits() {
	    return hits;
	}
	/**
	 * 浏览量设定
	 * @param hits 浏览量
	 */
	public void setHits(Integer hits) {
	    this.hits = hits;
	}
	/**
	 * 是否展示取得
	 * @return 是否展示
	 */
	public String getIs_show() {
	    return is_show;
	}
	/**
	 * 是否展示设定
	 * @param is_show 是否展示
	 */
	public void setIs_show(String is_show) {
	    this.is_show = is_show;
	}

}