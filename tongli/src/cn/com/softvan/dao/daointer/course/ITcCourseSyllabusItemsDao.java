/*
 * 接口Dao类  课程表详情
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.04.01  wuxiaogang      程序.发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.dao.daointer.course;

import java.util.List;

import cn.com.softvan.bean.course.TcCourseSyllabusBean;
import cn.com.softvan.bean.course.TcCourseSyllabusItemsBean;
import cn.com.softvan.dao.daointer.IBaseDao;
import cn.com.softvan.dao.entity.IEntity;
/**
 * <p> 课程表详情 Dao类 <p>
 * @author wuxiaogang
 *
 */
public interface ITcCourseSyllabusItemsDao  extends IBaseDao{
	/**
	 * <p>信息列表。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>当前课程关联的学员列表。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<TcCourseSyllabusItemsBean> findDataIsListStudent(IEntity dto);
	/**
	 * <p>信息列表。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>当前学员关联的课程列表。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<TcCourseSyllabusBean> findDataIsPageCourse(IEntity dto);
	/**
	 * <p>信息编辑。</p>
	 * <ol>[功能概要] 
	 * <div>教师给学员打分。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public int updateDataByStudent(IEntity dto) throws Exception;
	/**
	 * <p>信息编辑。</p>
	 * <ol>[功能概要] 
	 * <div>学员给教师打分。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public int updateDataByTeacher(IEntity dto) throws Exception;
	/**
	 * <p>信息列表。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>教师课程表 同时查询 当前课程关联的学员列表。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<TcCourseSyllabusBean> findDataIsPageCourse2(IEntity dto);
}