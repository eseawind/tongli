package cn.com.softvan.dao.daointer.wechar;

import cn.com.softvan.dao.entity.wechar.TcWxUser;

public interface ITcWxUserDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tc_wx_user
     *
     * @mbggenerated Tue Feb 25 17:19:42 CST 2014
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tc_wx_user
     *
     * @mbggenerated Tue Feb 25 17:19:42 CST 2014
     */
    int insert(TcWxUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tc_wx_user
     *
     * @mbggenerated Tue Feb 25 17:19:42 CST 2014
     */
    int insertSelective(TcWxUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tc_wx_user
     *
     * @mbggenerated Tue Feb 25 17:19:42 CST 2014
     */
    TcWxUser selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tc_wx_user
     *
     * @mbggenerated Tue Feb 25 17:19:42 CST 2014
     */
    int updateByPrimaryKeySelective(TcWxUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tc_wx_user
     *
     * @mbggenerated Tue Feb 25 17:19:42 CST 2014
     */
    int updateByPrimaryKey(TcWxUser record);
}