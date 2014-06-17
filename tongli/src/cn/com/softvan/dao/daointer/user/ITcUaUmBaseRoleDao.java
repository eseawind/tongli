package cn.com.softvan.dao.daointer.user;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

/*
 *接口Dao类  系统角色
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- -----------------------------------------
 * 1.00     2014.05.28  ll      程序.创建
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 picc  System. - All Rights Reserved.
 *
 */
import cn.com.softvan.dao.daointer.IBaseDao;

public interface ITcUaUmBaseRoleDao  extends IBaseDao {
	
	
	/**
	 * 保存角色功能权限
	 * @param roleId 角色Id
	 * @param perms  功能菜单列表
	 */
	public void savePermission(@Param("role_id")String roleId,@Param("create_id")String create_id,@Param("create_ip")String create_ip,
			@Param("update_id")String update_id,@Param("update_ip")String update_ip,@Param("perms")Set<String> perms);
	
	
	/**
	 * 删除角色功能权限
	 * @param id
	 * @return
	 */
	
	public void deletePermById(@Param("role_id")String roleId);
	
     
}