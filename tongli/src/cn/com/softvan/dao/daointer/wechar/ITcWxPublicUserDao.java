package cn.com.softvan.dao.daointer.wechar;

import java.util.List;

import cn.com.softvan.bean.wechar.TcWxPublicUserBean;
import cn.com.softvan.dao.entity.IEntity;

public interface ITcWxPublicUserDao {
	 /**
     * 根据主键id删除信息
     */
    int deleteByPrimaryKey(IEntity dto) throws Exception;

    /**
     * 新增信息(全字段)
     */
    int insert(IEntity dto) throws Exception;

    /**
     * 新增信息(部分字段)
     */
    int insertSelective(IEntity dto) throws Exception;

    /**
     * 根据主键获取对象
     */
    TcWxPublicUserBean selectByPrimaryKey(IEntity dto) throws Exception;

    /**
     * 更新对象
     */
    int updateByPrimaryKeySelective(IEntity dto) throws Exception;

    /**
     * 判断信息是否存在
     */
    int isDataYN(IEntity dto) throws Exception;
    /**
     * 查询信息列表
     */
    List<TcWxPublicUserBean> findDataIsList(IEntity dto) throws Exception;
}