package cn.com.softvan.dao.daointer.user;

import cn.com.softvan.dao.entity.user.TcUaUmOperationLog;
/**
 * <p> 操作日志信息  dao层<p>
 * @author wangzi
 *
 */
public interface ITcUaUmOperationLogDao {
    /**
     * 删除主键等于id的数据
     * @param id
     * @return
     */
    public void deleteByPrimaryKey(String id);

    /**
     * 新增日志信息
     * @param record
     */
    public void insert(TcUaUmOperationLog record) throws Exception;

    /**
     * 选择性新增有值信息
     * @param record
     * @return
     */
    public void insertSelective(TcUaUmOperationLog record);

    /**
     * 查询主键等于id的日志信息
     * @param id
     * @return
     */
    public TcUaUmOperationLog selectByPrimaryKey(String id);

   /**
    * 选择性更新日志信息
    * @param record
    * @return
    */
    public void updateByPrimaryKeySelective(TcUaUmOperationLog record);

   /**
    * 更新日志信息
    * @param record
    * @return
    */
    public void updateByPrimaryKey(TcUaUmOperationLog record);
}