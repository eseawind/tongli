/*
 * 基础Entity类  用户与车辆多对多的关系表
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.02.26  wangzi           程序发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.dao.entity.carmanager;

import java.util.Date;

/**
 * <p> 用户与车辆多对多的关系表 <p>
 * @author wangzi
 *
 */
public class TcCTUserVsCarKey{
	
	/**
	 * 车辆绑定ID
	 */
	private String usercarId;
    /**
     *用户id
     */
    private String userid;
    /**
     * 微信id
     */
    private String openid;

    /**
     * 车辆id
     */
    private String vehicleid;
    
    /**
     * 是否删除
     */
    private  String del_flag;
    
    /**
     * 资料更新日期
     */
    private Date  last_updated;

	/**
	 * 车辆绑定ID取得。
	 * @return 车辆绑定ID
	 */
	public String getUsercarId() {
	    return usercarId;
	}

	/**
	 * 车辆绑定ID设定。
	 * @param usercarId 车辆绑定ID
	 */
	public void setUsercarId(String usercarId) {
	    this.usercarId = usercarId;
	}

	/**
	 * 用户id取得。
	 * @return 用户id
	 */
	public String getUserid() {
	    return userid;
	}

	/**
	 * 用户id设定。
	 * @param userid 用户id
	 */
	public void setUserid(String userid) {
	    this.userid = userid;
	}

	/**
	 * 微信id取得。
	 * @return 微信id
	 */
	public String getOpenid() {
	    return openid;
	}

	/**
	 * 微信id设定。
	 * @param openid 微信id
	 */
	public void setOpenid(String openid) {
	    this.openid = openid;
	}

	/**
	 * 车辆id取得。
	 * @return 车辆id
	 */
	public String getVehicleid() {
	    return vehicleid;
	}

	/**
	 * 车辆id设定。
	 * @param vehicleid 车辆id
	 */
	public void setVehicleid(String vehicleid) {
	    this.vehicleid = vehicleid;
	}

	/**
	 * 是否删除取得。
	 * @return 是否删除
	 */
	public String getDel_flag() {
	    return del_flag;
	}

	/**
	 * 是否删除设定。
	 * @param del_flag 是否删除
	 */
	public void setDel_flag(String del_flag) {
	    this.del_flag = del_flag;
	}

	/**
	 * 资料更新日期取得。
	 * @return 资料更新日期
	 */
	public Date getLast_updated() {
	    return last_updated;
	}

	/**
	 * 资料更新日期设定。
	 * @param last_updated 资料更新日期
	 */
	public void setLast_updated(Date last_updated) {
	    this.last_updated = last_updated;
	}
}