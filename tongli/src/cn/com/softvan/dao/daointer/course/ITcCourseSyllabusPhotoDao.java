/*
 * 接口Dao类  课程表-相册
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.05.21  wuxiaogang      程序.发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.dao.daointer.course;

import java.util.List;

import cn.com.softvan.bean.course.TcCourseSyllabusPhotoBean;
import cn.com.softvan.dao.entity.IEntity;
/**
 * <p> 课程表-相册 Dao类 <p>
 * @author wuxiaogang
 *
 */
public interface ITcCourseSyllabusPhotoDao {
    /**
     * 根据主键 物理删除
     */
    int deleteByPrimaryKey(IEntity dto);

    /**
     * 新增
     */
    int insert(IEntity dto);

    /**
     * 详情
     */
    TcCourseSyllabusPhotoBean selectByPrimaryKey(IEntity dto);

    /**
     * 更新
     */
    int updateByPrimaryKeySelective(IEntity dto);
    /**
     * 判断是否存在
     */
    int isDataYN(IEntity dto);
    /**
     * 逻辑删除
     */
    int deleteById(IEntity dto);
    /**
     * 恢复逻辑删除的数据
     */
    int recoveryDataById(IEntity dto);
    /**
	 * <p>信息列表 分页。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>分页。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<TcCourseSyllabusPhotoBean> findDataIsPage(IEntity dto);
	/**
	 * <p>信息列表。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>列表。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<TcCourseSyllabusPhotoBean> findDataIsList(IEntity dto);
}