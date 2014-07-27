
/*	
 * 课程_上课地点关联关系  BEAN类	
 *		
 * VERSION      DATE          BY              REASON		
 * -------- ----------- --------------- ------------------------------------------	
 * 1.00     2014.07.27      wuxiaogang         程序.发布		
 * -------- ----------- --------------- ------------------------------------------	
 * Copyright 2014 tongli  System. - All Rights Reserved.		
 *	
 */
package cn.com.softvan.bean.addres;
import cn.com.softvan.bean.BaseBean;
/**
 * <p>课程_上课地点关联关系  BEAN类。</p>	
 * @author wuxiaogang
 */
public class TcCourseVsAddresBean extends BaseBean{

	private static final long serialVersionUID = -324374666729440080L;
	/**课程编号 */
	private String course_id;
	/**地址编号 */
	private String addres_id;
	/**
	 * 课程编号 设定 
	 * @param course_id课程编号
	 */
	public void setCourse_id(String course_id){
		this.course_id=course_id;
	}
	/**
	 * 课程编号 取得 
	 * @return 课程编号
	 */
	public String getCourse_id(){
		return course_id;
	}
	/**
	 * 地址编号 设定 
	 * @param addres_id地址编号
	 */
	public void setAddres_id(String addres_id){
		this.addres_id=addres_id;
	}
	/**
	 * 地址编号 取得 
	 * @return 地址编号
	 */
	public String getAddres_id(){
		return addres_id;
	}
}