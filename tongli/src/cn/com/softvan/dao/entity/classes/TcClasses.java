
/*	
 * 班级信息表  数据库entity类	
 *		
 * VERSION      DATE          BY              REASON		
 * -------- ----------- --------------- ------------------------------------------	
 * 1.00     2014.07.26      wuxiaogang         程序.发布		
 * -------- ----------- --------------- ------------------------------------------	
 * Copyright 2014 tongli  System. - All Rights Reserved.		
 *	
 */
package cn.com.softvan.dao.entity.classes;
import cn.com.softvan.dao.entity.BaseEntity;
/**
 * <p>班级信息表  数据库entity类。</p>	
 * @author wuxiaogang
 */
public class TcClasses extends BaseEntity{

	/**ID */
	private String id;
	/**班级名称 */
	private String name;
	/**序号 */
	private String sort_num;
	/**摘要 */
	private String brief_info;
	/**内容 */
	private String detail_info;
	/**pic_url */
	private String pic_url;
	/**
	 * ID 设定 
	 * @param idID
	 */
	public void setId(String id){
		this.id=id;
	}
	/**
	 * ID 取得 
	 * @return ID
	 */
	public String getId(){
		return id;
	}
	/**
	 * 班级名称 设定 
	 * @param name班级名称
	 */
	public void setName(String name){
		this.name=name;
	}
	/**
	 * 班级名称 取得 
	 * @return 班级名称
	 */
	public String getName(){
		return name;
	}
	/**
	 * 序号 设定 
	 * @param sort_num序号
	 */
	public void setSort_num(String sort_num){
		this.sort_num=sort_num;
	}
	/**
	 * 序号 取得 
	 * @return 序号
	 */
	public String getSort_num(){
		return sort_num;
	}
	/**
	 * 摘要 设定 
	 * @param brief_info摘要
	 */
	public void setBrief_info(String brief_info){
		this.brief_info=brief_info;
	}
	/**
	 * 摘要 取得 
	 * @return 摘要
	 */
	public String getBrief_info(){
		return brief_info;
	}
	/**
	 * 内容 设定 
	 * @param detail_info内容
	 */
	public void setDetail_info(String detail_info){
		this.detail_info=detail_info;
	}
	/**
	 * 内容 取得 
	 * @return 内容
	 */
	public String getDetail_info(){
		return detail_info;
	}
	/**
	 * pic_url 设定 
	 * @param pic_urlpic_url
	 */
	public void setPic_url(String pic_url){
		this.pic_url=pic_url;
	}
	/**
	 * pic_url 取得 
	 * @return pic_url
	 */
	public String getPic_url(){
		return pic_url;
	}
}