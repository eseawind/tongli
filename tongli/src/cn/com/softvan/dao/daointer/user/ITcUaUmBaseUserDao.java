package cn.com.softvan.dao.daointer.user;
/*
 *接口Dao类  系统用户
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- -----------------------------------------
 * 1.00     2014.05.28  ll      程序.创建
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 picc  System. - All Rights Reserved.
 *
 */
import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import cn.com.softvan.bean.backuser.TcUaUmBaseUserBean;
import cn.com.softvan.dao.daointer.IBaseDao;
import cn.com.softvan.dao.entity.IEntity;

public interface ITcUaUmBaseUserDao  extends IBaseDao {
	 
    /**
     * 查询用户信息的工作量
     * 
     * @param bean 
     * @return
     */
    List<TcUaUmBaseUserBean> selectBeanUsersIsPage(TcUaUmBaseUserBean bean) throws Exception;
    
    /**
	 * <p>信息列表 分页。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>分页。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<?> findUserIsPage(IEntity dto) throws Exception;
	
	
	/**
	 * 保存用户角色功能
	 * @param user_id 用户Id
	 * @param roleIds  角色列表
	 */
	public void saveUserRole(@Param("user_id")String userId,@Param("create_id")String create_id,@Param("create_ip")String create_ip,
			@Param("update_id")String update_id,@Param("update_ip")String update_ip,@Param("roleIds")Set<String> roleIds);
	
	
	/**
	 * 删除角色功能权限
	 * @param id
	 * @return
	 */
	
	public void deleteUserRoleByUId(@Param("user_id")String userId);
	
	/**
	 * 判断用户名是否存在
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	int isUsernameExist(IEntity dto) throws Exception;
	
	 /**
     * 用户登录
     */
    Object userlogin(IEntity dto) throws Exception;
	
    
}