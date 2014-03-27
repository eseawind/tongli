package cn.com.softvan.dao.daointer.wechat;

import java.util.List;

import cn.com.softvan.bean.wechat.TcWxUserBean;
import cn.com.softvan.dao.entity.IEntity;

public interface ITcWxUserDao {
	/**
     * 根据主键id删除信息
     */
    int deleteByPrimaryKey(IEntity dto) throws Exception;
    /**
     * 新增信息(全字段)
     */
    int insert(IEntity dto) throws Exception;

    /**
     * 根据主键获取对象
     */
    TcWxUserBean selectByPrimaryKey(IEntity dto) throws Exception;

    /**
     * 更新对象
     */
    int updateByPrimaryKeySelective(IEntity dto) throws Exception;

    /**
     * 判断信息是否存在
     */
    int isDataYN(IEntity dto) throws Exception;
    /**
     * 查询信息 列表
     */
    List<TcWxUserBean> findDataIsList(IEntity dto) throws Exception;
    /**
     * 查询信息 分页
     */
    List<TcWxUserBean> findDataIsPage(IEntity dto) throws Exception;
}