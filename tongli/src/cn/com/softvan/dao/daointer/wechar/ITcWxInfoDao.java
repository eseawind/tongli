package cn.com.softvan.dao.daointer.wechar;

import java.util.List;

import cn.com.softvan.bean.wechar.TcWxInfoBean;
import cn.com.softvan.dao.entity.IEntity;

public interface ITcWxInfoDao {
	 /**
     * 根据主键id删除信息
     */
    int deleteByPrimaryKey(IEntity dto) throws Exception;
    /**
     * 根据aid 逻辑删除信息
     */
    int deleteByAid(IEntity dto) throws Exception;
    /**
     * 根据Aid获取对象
     */
    TcWxInfoBean selectByAid(IEntity dto) throws Exception;
    /**
     * 根据z主键id 逻辑删除信息
     */
    int deleteById(IEntity dto) throws Exception;
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
    TcWxInfoBean selectByPrimaryKey(IEntity dto) throws Exception;

    /**
     * 更新对象
     */
    int updateByPrimaryKeySelective(IEntity dto) throws Exception;
    /**
     * 更新 媒体id
     */
    int updateMediaIdByPrimaryKey(IEntity dto) throws Exception;

    /**
     * 判断信息是否存在
     */
    int isDataYN(IEntity dto) throws Exception;
    /**
     * 查询信息 列表
     */
    List<TcWxInfoBean> findDataIsList(IEntity dto) throws Exception;
    /**
     * 查询信息 分页
     */
    List<TcWxInfoBean> findDataIsPage(IEntity dto) throws Exception;
}