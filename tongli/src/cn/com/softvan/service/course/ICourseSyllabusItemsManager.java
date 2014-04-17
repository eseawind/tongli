/*
 * 课程表-详情管理  service 接口类
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.04.07  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家 System. - All Rights Reserved.
 *
 */
package cn.com.softvan.service.course;

import java.util.List;

import cn.com.softvan.bean.course.TcCourseSyllabusBean;
import cn.com.softvan.bean.course.TcCourseSyllabusItemsBean;


/**
 * 课程表-详情管理   service 接口类
 * @author wuxiaogang
 *
 */
public interface ICourseSyllabusItemsManager {
	/**
	 * <p>信息编辑。</p>
	 * <ol>[功能概要] 
	 * <div>新增信息。</div>
	 * <div>修改信息。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String saveOrUpdateData(TcCourseSyllabusItemsBean bean) throws Exception;
	/**
	 * <p>信息编辑。</p>
	 * <ol>[功能概要] 
	 * <div>物理删除。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String deleteData(TcCourseSyllabusItemsBean bean) throws Exception;
	/**
	 * <p>信息 单条。</p>
	 * <ol>[功能概要] 
	 * <div>恢复逻辑删除的数据。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String recoveryDataById(TcCourseSyllabusItemsBean bean) throws Exception;
	/**
	 * <p>信息 单条。</p>
	 * <ol>[功能概要] 
	 * <div>逻辑删除。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String deleteDataById(TcCourseSyllabusItemsBean bean) throws Exception;
	/**
	 * <p>信息列表 分页。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>分页。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<TcCourseSyllabusItemsBean> findDataIsPage(TcCourseSyllabusItemsBean bean);
	/**
	 * <p>信息列表。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>列表。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<TcCourseSyllabusItemsBean> findDataIsList(TcCourseSyllabusItemsBean bean);
	/**
	 * <p>信息详情。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>详情。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public TcCourseSyllabusItemsBean findDataById(TcCourseSyllabusItemsBean bean);
	/**
	 * <p>信息列表。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>当前课程关联的学员列表。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<TcCourseSyllabusItemsBean> findDataIsListStudent(TcCourseSyllabusItemsBean bean);
	/**
	 * <p>信息列表。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>当前学员关联的课程列表。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<TcCourseSyllabusBean> findDataIsPageCourse(TcCourseSyllabusItemsBean bean1);
}
