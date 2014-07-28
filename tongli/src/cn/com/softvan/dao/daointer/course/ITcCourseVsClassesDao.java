
/*	
 * 课程与班级关联关系  数据库处理接口类	
 *		
 * VERSION      DATE          BY              REASON		
 * -------- ----------- --------------- ------------------------------------------	
 * 1.00     2014.07.28      wuxiaogang         程序.发布		
 * -------- ----------- --------------- ------------------------------------------	
 * Copyright 2014 tongli  System. - All Rights Reserved.		
 *	
 */
package cn.com.softvan.dao.daointer.course;
import java.util.List;

import cn.com.softvan.bean.classes.TcClassesBean;
import cn.com.softvan.dao.daointer.IBaseDao;
import cn.com.softvan.dao.entity.IEntity;
/**
 * <p>课程与班级关联关系  数据库处理接口类。</p>	
 * @author wuxiaogang
 */
public interface ITcCourseVsClassesDao extends IBaseDao{
	/**
	 * <p>信息列表。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>当前课程关联的的班级列表。</div>
	 * </ol>
	 * @return 处理结果
	 */
	List<TcClassesBean> findDataIsListClasses(IEntity dto);
}