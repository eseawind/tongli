/**
 * 系统调用车险接口异常日志 Dao层接口
 * VERSION          DATE                 BY              REASON
 * -------- -------------------  ---------------------- ------------------------------------------
 * 1.00     2014-03-24 14:33:36             wuxiaogang              程序.发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 */
package cn.com.softvan.dao.daointer.sys;

import java.util.List;

import cn.com.softvan.bean.sys.TcSysInterfaceLogBean;
import cn.com.softvan.dao.entity.IEntity;

/**
 * <p> 系统调用车险接口异常日志 Dao层接口 <p>
 * @author wuxiaogang
 *
 */
public interface ITcSysInterfaceLogDao {
	/**
     * 根据主键 物理删除
     */
    public void deleteByPrimaryKey(IEntity dto);

    /**
     * 新增
     */
    public void insert(IEntity dto);

    /**
     * 详情
     */
    public TcSysInterfaceLogBean selectByPrimaryKey(IEntity dto);

    /**
     * 更新
     */
    public void updateByPrimaryKeySelective(IEntity dto);
   
    /**
	 * <p>信息列表 分页。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>分页。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<TcSysInterfaceLogBean> findDataIsPage(IEntity dto);
	/**
	 * <p>信息列表。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>列表。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<TcSysInterfaceLogBean> findDataIsList(IEntity dto);
}
