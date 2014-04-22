/*
 * BEAN    购物车表
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.04.01  wuxiaogang      程序.发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.bean.eb;

import cn.com.softvan.bean.BaseBean;

/**
 * <p>
 * 购物车表
 * <p>
 * 
 * @author wuxiaogang
 * 
 */
public class TcEbProductCartBean extends BaseBean {

	/** 编号 */
	private String id;
	/** 用户ID */
	private String user_id;
	/** 商品ID */
	private String product_id;
	/** 数量 */
	private String product_num;
	/** 商品类型 */
	private String product_type;
	/** 关联编号 */
	private String linked_id;
	/**
	 * 编号取得
	 * @return 编号
	 */
	public String getId() {
	    return id;
	}
	/**
	 * 编号设定
	 * @param id 编号
	 */
	public void setId(String id) {
	    this.id = id;
	}
	/**
	 * 用户ID取得
	 * @return 用户ID
	 */
	public String getUser_id() {
	    return user_id;
	}
	/**
	 * 用户ID设定
	 * @param user_id 用户ID
	 */
	public void setUser_id(String user_id) {
	    this.user_id = user_id;
	}
	/**
	 * 商品ID取得
	 * @return 商品ID
	 */
	public String getProduct_id() {
	    return product_id;
	}
	/**
	 * 商品ID设定
	 * @param product_id 商品ID
	 */
	public void setProduct_id(String product_id) {
	    this.product_id = product_id;
	}
	/**
	 * 数量取得
	 * @return 数量
	 */
	public String getProduct_num() {
	    return product_num;
	}
	/**
	 * 数量设定
	 * @param product_num 数量
	 */
	public void setProduct_num(String product_num) {
	    this.product_num = product_num;
	}
	/**
	 * 商品类型取得
	 * @return 商品类型
	 */
	public String getProduct_type() {
	    return product_type;
	}
	/**
	 * 商品类型设定
	 * @param product_type 商品类型
	 */
	public void setProduct_type(String product_type) {
	    this.product_type = product_type;
	}
	/**
	 * 关联编号取得
	 * @return 关联编号
	 */
	public String getLinked_id() {
	    return linked_id;
	}
	/**
	 * 关联编号设定
	 * @param linked_id 关联编号
	 */
	public void setLinked_id(String linked_id) {
	    this.linked_id = linked_id;
	}

}