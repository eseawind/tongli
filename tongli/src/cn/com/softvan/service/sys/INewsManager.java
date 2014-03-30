/*
 * 资讯信息管理  service 接口类
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.03.18  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家 System. - All Rights Reserved.
 *
 */
package cn.com.softvan.service.sys;

import java.util.List;

import cn.com.softvan.bean.sys.TcSysNewsBean;
import cn.com.softvan.bean.sys.TcSysNewsTypeBean;


/**
 * 资讯信息管理   service 接口类
 * @author wuxiaogang
 *
 */
public interface INewsManager {
	/**
	 * <p>信息编辑。</p>
	 * <ol>[功能概要] 
	 * <div>新增信息。</div>
	 * <div>修改信息。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String saveOrUpdateData(TcSysNewsBean bean) throws Exception;
	/**
	 * <p>信息编辑。</p>
	 * <ol>[功能概要] 
	 * <div>物理删除。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String deleteData(TcSysNewsBean bean) throws Exception;
	/**
	 * <p>信息 单条。</p>
	 * <ol>[功能概要] 
	 * <div>恢复逻辑删除的数据。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String recoveryDataById(TcSysNewsBean bean) throws Exception;
	/**
	 * <p>信息 单条。</p>
	 * <ol>[功能概要] 
	 * <div>逻辑删除。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String deleteDataById(TcSysNewsBean bean) throws Exception;
	/**
	 * <p>信息列表 分页。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>分页。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<TcSysNewsBean> findDataIsPage(TcSysNewsBean bean);
	/**
	 * <p>信息列表。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>列表。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<TcSysNewsBean> findDataIsList(TcSysNewsBean bean);
	/**
	 * <p>信息详情。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>详情。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public TcSysNewsBean findDataById(TcSysNewsBean bean);
	/**
	 * <p>根据资讯id查询资讯的所属栏目集合。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>集合。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<TcSysNewsTypeBean> findTypeDataByIdIsList(TcSysNewsBean bean);
}
