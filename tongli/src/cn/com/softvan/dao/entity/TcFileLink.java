/*
 * 基础Entity类  车主管家模块附件关联表
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.02.26  wuxiaogang           程序.发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.dao.entity;


/**
 * <p> 车主管家模块附件关联表  <p>
 * @author wuxiaogang
 *
 */
public class TcFileLink extends BaseEntity {
	/**
	 * 关联ID
	 */
	 private String link_id;
	 /**
	  * 附件ID
	  */
	 private String file_id;
    /**
     * 关联表名称
     */
    private String table_name;

    /**
     * 关联表字段名
     */
    private String table_column;

    /**
     * 排序
     */
    private Integer orders;

    

    

	/**
	 * 关联ID取得。
	 * @return 关联ID
	 */
	public String getLink_id() {
	    return link_id;
	}

	/**
	 * 关联ID设定。
	 * @param link_id 关联ID
	 */
	public void setLink_id(String link_id) {
	    this.link_id = link_id;
	}

	/**
	 * 附件ID取得。
	 * @return 附件ID
	 */
	public String getFile_id() {
	    return file_id;
	}

	/**
	 * 附件ID设定。
	 * @param file_id 附件ID
	 */
	public void setFile_id(String file_id) {
	    this.file_id = file_id;
	}

	/**
	 * 关联表名称取得。
	 * @return 关联表名称
	 */
	public String getTable_name() {
	    return table_name;
	}

	/**
	 * 关联表名称设定。
	 * @param table_name 关联表名称
	 */
	public void setTable_name(String table_name) {
	    this.table_name = table_name;
	}

	/**
	 * 关联表字段名取得。
	 * @return 关联表字段名
	 */
	public String getTable_column() {
	    return table_column;
	}

	/**
	 * 关联表字段名设定。
	 * @param table_column 关联表字段名
	 */
	public void setTable_column(String table_column) {
	    this.table_column = table_column;
	}

	/**
	 * 排序取得。
	 * @return 排序
	 */
	public Integer getOrders() {
	    return orders;
	}

	/**
	 * 排序设定。
	 * @param orders 排序
	 */
	public void setOrders(Integer orders) {
	    this.orders = orders;
	}

	 

	 

}