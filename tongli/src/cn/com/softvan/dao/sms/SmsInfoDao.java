package cn.com.softvan.dao.sms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.com.softvan.common.WebUtils;
import cn.com.softvan.common.sms.SmsConstant;
import cn.com.softvan.common.sms.SmsInfo;

import cn.com.softvan.common.Validator;
import cn.com.softvan.dao.sms.base.BaseDao;
import cn.com.softvan.dao.sms.base.ConnectionManager;
import cn.com.softvan.dao.sms.base.ProcessSql;

public class SmsInfoDao extends BaseDao {

	private static final transient Logger log = Logger.getLogger(SmsInfoDao.class); 
	
	public final static String TABLE_NAME = "tc_ct_sms_info";
	public final static String PK_NAME = "SMS_ID";
	public final static String ORDER_NAME = "SMS_SEND_TIME DESC";
	public final static String[] ITEMS = {
		"sms_id",
		"sms_src_id",
		"sms_dst_id",
		"sms_content",
		"sms_send_time",
		"sms_sended_time",
		"sms_send_count",
		"sms_status"
	};
	
	private Connection conn = null;
	
	public static Map populate(Map properties) {
		return WebUtils.fetchData(ITEMS, properties);
	}
	
	public static Map queryById(String id) throws Exception {
		return BaseDao.queryByKey(id, TABLE_NAME, PK_NAME);
	}
	
	public static void deleteById(String id) throws Exception {
		BaseDao.deleteByKey(id, TABLE_NAME, PK_NAME);
	}
	
	public static void updateById(Map data) throws Exception {
		BaseDao.updateByKey(data, TABLE_NAME, PK_NAME);
	}
	
	public void initConn() throws Exception{
		conn = ConnectionManager.getInstance().getConnection();
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			if (conn != null) {
				try {
					conn.close();
					conn = null;
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			throw e;
		}
	}
	
	public void commit() throws Exception {
		try {
			if (conn != null) {
				conn.commit();
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if (conn != null) {
				try {
					conn.close();
					conn = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void rollback() throws Exception {
		try {
			if (conn != null) {
				conn.rollback();
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if (conn != null) {
				try {
					conn.close();
					conn = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static List queryNoSendInfo() throws Exception {
		
		StringBuilder sqlb = new StringBuilder();
		sqlb.append("SELECT ");
		sqlb.append("  sms_id, ");
		sqlb.append("  sms_src_id, ");
		sqlb.append("  sms_dst_id, ");
		sqlb.append("  sms_content, ");
		sqlb.append("  sms_send_time, ");
		sqlb.append("  sms_send_count, ");
		sqlb.append("  sms_status ");
		sqlb.append(" FROM ");
		sqlb.append(TABLE_NAME);
		sqlb.append(" WHERE ");
		sqlb.append("  sms_status = ?");
		sqlb.append("  AND sms_send_count <= ?");
		sqlb.append(" ORDER BY");
		sqlb.append("  sms_send_time ASC");
		
		return executeQuery(sqlb.toString(), 
				new Object[]{SmsConstant.SMS_STATUS_NO, SmsConstant.SMS_RESEND_COUNT}, 0, 0, SmsInfo.class);
	}
	
	/*
	 *  向表里面插入数据
	 *  data集合数据
	 *  table表名
	 *  主要是完成对insert into biao表()values()的语法的拼接
	 */
	public int saveInfo(Map data, String table) throws Exception {
		
		if (conn == null) {
			log.info("没有初始化数据库连接对象");
			return 0;
		}
		
		StringBuffer sqlS = new StringBuffer("INSERT INTO " + table + " (");
		StringBuffer sqlInsert = new StringBuffer();
		StringBuffer sqlValues = new StringBuffer();
		Iterator it = data.entrySet().iterator();
		List paramsList = new ArrayList();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			sqlInsert.append("," + entry.getKey());
			if (Validator.isNullEmpty((String)entry.getValue())) {
				sqlValues.append(",null");
			} else {
				sqlValues.append(",?");
				paramsList.add(entry.getValue());
			}
		}
		sqlS.append(sqlInsert.substring(1).toString());
		sqlS.append(") VALUES (");
		sqlS.append(sqlValues.substring(1).toString());
		sqlS.append(")");
		
		String sql = sqlS.toString();
		Object[] params = paramsList.toArray();
		
		PreparedStatement pstmt = null;
		int updateCount = 0;
		try {
			
			pstmt = conn.prepareStatement(sql);
			ProcessSql.setParams(pstmt, params);
			
			updateCount = pstmt.executeUpdate();
			
			pstmt.close();
			pstmt = null;
			
		} catch (SQLException sqle) {
			conn.rollback();
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			sqle.printStackTrace();
			throw sqle;
		} catch (Exception e) {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return updateCount;
	}
	
	/*
	 *  批量修改状态值
	 *  idList 集合数据
	 *  table 表名
	 */
	public static void updateStatus(List<String> idList) throws Exception {
		
		StringBuilder sqlb = new StringBuilder();
		sqlb.append("UPDATE ");
		sqlb.append(TABLE_NAME);
		sqlb.append(" SET  sms_status = ?");
		sqlb.append(" WHERE ");
		
		Iterator<String> it = idList.iterator();
		StringBuilder parmaStr = null;
		while (it.hasNext()) {
			if (parmaStr == null) {
				parmaStr = new StringBuilder();
				parmaStr.append("('"+ it.next() +"'");
			} else {
				parmaStr.append(",'"+ it.next() +"'");
			}
		}
		parmaStr.append(")");
		
		sqlb.append("  sms_id in "+ parmaStr.toString());
		
		executeUpdate(sqlb.toString(), new Object[]{SmsConstant.SMS_STATUS_SENDING});
	}
}
