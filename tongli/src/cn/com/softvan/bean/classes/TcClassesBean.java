
/*	
 * 班级信息表  BEAN类	
 *		
 * VERSION      DATE          BY              REASON		
 * -------- ----------- --------------- ------------------------------------------	
 * 1.00     2014.07.26      wuxiaogang         程序.发布		
 * -------- ----------- --------------- ------------------------------------------	
 * Copyright 2014 tongli  System. - All Rights Reserved.		
 *	
 */
package cn.com.softvan.bean.classes;
import java.util.List;

import cn.com.softvan.bean.BaseBean;
/**
 * <p>班级信息表  BEAN类。</p>	
 * @author wuxiaogang
 */
public class TcClassesBean extends BaseBean{

	private static final long serialVersionUID = -711000271307597405L;
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
	/** 学员id集合 */
	private List<String> sids;
	/**
	 * ID取得
	 * @return ID
	 */
	public String getId(){
		return id;
	}
	/**
	 * ID设定
	 * @param id ID
	 */
	public void setId(String id){
		this.id=id;
	}
	/**
	 * 班级名称取得
	 * @return 班级名称
	 */
	public String getName(){
		return name;
	}
	/**
	 * 班级名称设定
	 * @param name 班级名称
	 */
	public void setName(String name){
		this.name=name;
	}
	/**
	 * 序号取得
	 * @return 序号
	 */
	public String getSort_num(){
		return sort_num;
	}
	/**
	 * 序号设定
	 * @param sort_num 序号
	 */
	public void setSort_num(String sort_num){
		this.sort_num=sort_num;
	}
	/**
	 * 摘要取得
	 * @return 摘要
	 */
	public String getBrief_info(){
		return brief_info;
	}
	/**
	 * 摘要设定
	 * @param brief_info 摘要
	 */
	public void setBrief_info(String brief_info){
		this.brief_info=brief_info;
	}
	/**
	 * 内容取得
	 * @return 内容
	 */
	public String getDetail_info(){
		return detail_info;
	}
	/**
	 * 内容设定
	 * @param detail_info 内容
	 */
	public void setDetail_info(String detail_info){
		this.detail_info=detail_info;
	}
	/**
	 * pic_url取得
	 * @return pic_url
	 */
	public String getPic_url(){
		return pic_url;
	}
	/**
	 * pic_url设定
	 * @param pic_url pic_url
	 */
	public void setPic_url(String pic_url){
		this.pic_url=pic_url;
	}
	/**
	 * 学员id集合取得
	 * @return 学员id集合
	 */
	public List<String> getSids() {
	    return sids;
	}
	/**
	 * 学员id集合设定
	 * @param sids 学员id集合
	 */
	public void setSids(List<String> sids) {
	    this.sids = sids;
	}
}