/*
 * Course信息管理  service 接口类
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.03.18  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励 System. - All Rights Reserved.
 *
 */
package cn.com.softvan.service.yongyi.course;

import java.util.List;

import cn.com.softvan.bean.yongyi.course.TcYCourseBean;


/**
 * Course信息管理   service 接口类
 * @author wuxiaogang
 *
 */
public interface ITcYCourseManager {
	/**
	 * <p>信息编辑。</p>
	 * <ol>[功能概要] 
	 * <div>新增信息。</div>
	 * <div>修改信息。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String saveOrUpdateData(TcYCourseBean bean) throws Exception;
	/**
	 * <p>信息编辑。</p>
	 * <ol>[功能概要] 
	 * <div>物理删除。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String deleteData(TcYCourseBean bean) throws Exception;
	/**
	 * <p>信息 单条。</p>
	 * <ol>[功能概要] 
	 * <div>恢复逻辑删除的数据。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String recoveryDataById(TcYCourseBean bean) throws Exception;
	/**
	 * <p>信息 单条。</p>
	 * <ol>[功能概要] 
	 * <div>逻辑删除。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String deleteDataById(TcYCourseBean bean) throws Exception;
	/**
	 * <p>信息列表 分页。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>分页。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<TcYCourseBean> findDataIsPage(TcYCourseBean bean);
	/**
	 * <p>信息列表。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>列表。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<TcYCourseBean> findDataIsList(TcYCourseBean bean);
	/**
	 * <p>信息详情。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>详情。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public TcYCourseBean findDataById(TcYCourseBean bean);
//	/**
//	 * <p>信息列表。</p>
//	 * <ol>[功能概要] 
//	 * <div>信息检索。</div>
//	 * <div>当前课程关联的的班级列表。</div>
//	 * </ol>
//	 * @return 处理结果
//	 */
//	public List<TcYClassesBean> findDataIsListClasses(TcYCourseVsClassesBean bean);
}
