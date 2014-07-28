
/*	
 * 课程与班级关联关系  数据库entity类	
 *		
 * VERSION      DATE          BY              REASON		
 * -------- ----------- --------------- ------------------------------------------	
 * 1.00     2014.07.28      wuxiaogang         程序.发布		
 * -------- ----------- --------------- ------------------------------------------	
 * Copyright 2014 tongli  System. - All Rights Reserved.		
 *	
 */
package cn.com.softvan.dao.entity.course;
import cn.com.softvan.dao.entity.BaseEntity;
/**
 * <p>课程与班级关联关系  数据库entity类。</p>	
 * @author wuxiaogang
 */
public class TcCourseVsClasses extends BaseEntity{

	/**课程id */
	private String course_id;
	/**班级id */
	private String classes_id;
	/**
	 * 课程id 设定 
	 * @param course_id课程id
	 */
	public void setCourse_id(String course_id){
		this.course_id=course_id;
	}
	/**
	 * 课程id 取得 
	 * @return 课程id
	 */
	public String getCourse_id(){
		return course_id;
	}
	/**
	 * 班级id 设定 
	 * @param classes_id班级id
	 */
	public void setClasses_id(String classes_id){
		this.classes_id=classes_id;
	}
	/**
	 * 班级id 取得 
	 * @return 班级id
	 */
	public String getClasses_id(){
		return classes_id;
	}
}