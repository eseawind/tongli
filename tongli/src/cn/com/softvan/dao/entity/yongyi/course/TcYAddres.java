
/*	
 * 课程地址_y  数据库entity类	
 *		
 * VERSION      DATE          BY              REASON		
 * -------- ----------- --------------- ------------------------------------------	
 * 1.00     2014.09.21      wuxiaogang         程序.发布		
 * -------- ----------- --------------- ------------------------------------------	
 * Copyright 2014 tongli  System. - All Rights Reserved.		
 *	
 */
package cn.com.softvan.dao.entity.yongyi.course;
import cn.com.softvan.dao.entity.BaseEntity;
/**
 * <p>课程地址_y  数据库entity类。</p>	
 * @author wuxiaogang
 */
public class TcYAddres extends BaseEntity{

	/**id */
	private String id;
	/**标题图 */
	private String pic_url;
	/**课程详情 */
	private String detail_info;
	/**名称 */
	private String title;
	/**地址 */
	private String addres;
	/**
	 * id 设定 
	 * @param idid
	 */
	public void setId(String id){
		this.id=id;
	}
	/**
	 * id 取得 
	 * @return id
	 */
	public String getId(){
		return id;
	}
	/**
	 * 标题图 设定 
	 * @param pic_url标题图
	 */
	public void setPic_url(String pic_url){
		this.pic_url=pic_url;
	}
	/**
	 * 标题图 取得 
	 * @return 标题图
	 */
	public String getPic_url(){
		return pic_url;
	}
	/**
	 * 课程详情 设定 
	 * @param detail_info课程详情
	 */
	public void setDetail_info(String detail_info){
		this.detail_info=detail_info;
	}
	/**
	 * 课程详情 取得 
	 * @return 课程详情
	 */
	public String getDetail_info(){
		return detail_info;
	}
	/**
	 * 名称 设定 
	 * @param title名称
	 */
	public void setTitle(String title){
		this.title=title;
	}
	/**
	 * 名称 取得 
	 * @return 名称
	 */
	public String getTitle(){
		return title;
	}
	/**
	 * 地址 设定 
	 * @param addres地址
	 */
	public void setAddres(String addres){
		this.addres=addres;
	}
	/**
	 * 地址 取得 
	 * @return 地址
	 */
	public String getAddres(){
		return addres;
	}
}