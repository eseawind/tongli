
/*	
 * 班级_学员关联表  BEAN类	
 *		
 * VERSION      DATE          BY              REASON		
 * -------- ----------- --------------- ------------------------------------------	
 * 1.00     2014.07.26      wuxiaogang         程序.发布		
 * -------- ----------- --------------- ------------------------------------------	
 * Copyright 2014 tongli  System. - All Rights Reserved.		
 *	
 */
package cn.com.softvan.bean.classes;
import cn.com.softvan.bean.BaseBean;
/**
 * <p>班级_学员关联表  BEAN类。</p>	
 * @author wuxiaogang
 */
public class TcClassesVsStudentBean extends BaseBean{

	private static final long serialVersionUID = -647259783748062892L;
	/**学员id */
	private String student_id;
	/**班级id */
	private String classes_id;
	/**
	 * 学员id 设定 
	 * @param student_id学员id
	 */
	public void setStudent_id(String student_id){
		this.student_id=student_id;
	}
	/**
	 * 学员id 取得 
	 * @return 学员id
	 */
	public String getStudent_id(){
		return student_id;
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