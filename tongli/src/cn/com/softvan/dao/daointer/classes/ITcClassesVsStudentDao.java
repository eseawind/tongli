
/*	
 * 班级_学员关联表  数据库处理接口类	
 *		
 * VERSION      DATE          BY              REASON		
 * -------- ----------- --------------- ------------------------------------------	
 * 1.00     2014.07.26      wuxiaogang         程序.发布		
 * -------- ----------- --------------- ------------------------------------------	
 * Copyright 2014 tongli  System. - All Rights Reserved.		
 *	
 */
package cn.com.softvan.dao.daointer.classes;
import java.util.List;

import cn.com.softvan.bean.student.TcStudentBean;
import cn.com.softvan.dao.daointer.IBaseDao;
import cn.com.softvan.dao.entity.IEntity;
/**
 * <p>班级_学员关联表  数据库处理接口类。</p>	
 * @author wuxiaogang
 */
public interface ITcClassesVsStudentDao extends IBaseDao{
	 /**
	 * <p>信息列表。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>当前班级关联的学员列表。</div>
	 * </ol>
	 * @return 处理结果
	 */
	List<TcStudentBean> findDataIsListStudent(IEntity dto);
}