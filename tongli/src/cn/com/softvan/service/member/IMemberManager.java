/*
 * 会员信息管理  service 接口类
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.04.06  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励 System. - All Rights Reserved.
 *
 */
package cn.com.softvan.service.member;

import java.util.List;

import cn.com.softvan.bean.member.TcMemberBean;
import cn.com.softvan.bean.student.TcStudentBean;


/**
 * 会员信息管理   service 接口类
 * @author wuxiaogang
 *
 */
public interface IMemberManager {
	/**
	 * <p>信息编辑。</p>
	 * <ol>[功能概要] 
	 * <div>新增信息。</div>
	 * <div>修改信息。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String saveOrUpdateData(TcMemberBean bean) throws Exception;
	/**
	 * <p>信息编辑。</p>
	 * <ol>[功能概要] 
	 * <div>物理删除。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String deleteData(TcMemberBean bean) throws Exception;
	/**
	 * <p>信息 单条。</p>
	 * <ol>[功能概要] 
	 * <div>恢复逻辑删除的数据。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String recoveryDataById(TcMemberBean bean) throws Exception;
	/**
	 * <p>信息 单条。</p>
	 * <ol>[功能概要] 
	 * <div>逻辑删除。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String deleteDataById(TcMemberBean bean) throws Exception;
	/**
	 * <p>信息列表 分页。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>分页。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<TcMemberBean> findDataIsPage(TcMemberBean bean);
	/**
	 * <p>信息列表。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>列表。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<TcMemberBean> findDataIsList(TcMemberBean bean);
	/**
	 * <p>信息详情。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>详情。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public TcMemberBean findDataById(TcMemberBean bean);
	/**
	 * <p>信息列表。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>当前会员关联的学员列表。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<TcStudentBean> findDataIsListStudent(TcMemberBean bean);
	/**
	 * <p>信息列表。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>当前学员关联的会员列表。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<TcMemberBean> findDataIsListMember(TcStudentBean bean);
	
	/**
	 * <p>用户登录。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>密码验证。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public TcMemberBean checkMemberPWD(TcMemberBean bean);
	
}
