package cn.com.softvan.dao.daointer.wechat;

import cn.com.softvan.dao.entity.wechat.TcWxUserGroups;

public interface ITcWxUserGroupsDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tc_wx_user_groups
     *
     * @mbggenerated Tue Feb 25 17:19:42 CST 2014
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tc_wx_user_groups
     *
     * @mbggenerated Tue Feb 25 17:19:42 CST 2014
     */
    int insert(TcWxUserGroups record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tc_wx_user_groups
     *
     * @mbggenerated Tue Feb 25 17:19:42 CST 2014
     */
    int insertSelective(TcWxUserGroups record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tc_wx_user_groups
     *
     * @mbggenerated Tue Feb 25 17:19:42 CST 2014
     */
    TcWxUserGroups selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tc_wx_user_groups
     *
     * @mbggenerated Tue Feb 25 17:19:42 CST 2014
     */
    int updateByPrimaryKeySelective(TcWxUserGroups record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tc_wx_user_groups
     *
     * @mbggenerated Tue Feb 25 17:19:42 CST 2014
     */
    int updateByPrimaryKey(TcWxUserGroups record);
}