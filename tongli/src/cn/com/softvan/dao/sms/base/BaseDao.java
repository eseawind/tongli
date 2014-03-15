package cn.com.softvan.dao.sms.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.com.softvan.common.Validator;
import cn.com.softvan.common.WebUtils;

public class BaseDao {
	private static final transient Logger log = Logger.getLogger(BaseDao.class);
	/*
	 *  向表里面插入数据
	 *  data集合数据
	 *  table表名
	 *  主要是完成对insert into biao表()values()的语法的拼接
	 */
	public static void save(Map data, String table) throws Exception {
		StringBuffer sql = new StringBuffer("INSERT INTO " + table + " (");
		StringBuffer sqlInsert = new StringBuffer();
		StringBuffer sqlValues = new StringBuffer();
		Iterator it = data.entrySet().iterator();
		List params = new ArrayList();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			sqlInsert.append("," + entry.getKey());
			if (Validator.isNullEmpty((String)entry.getValue())) {
				sqlValues.append(",null");
			} else {
				sqlValues.append(",?");
				params.add(entry.getValue());
			}
		}
		sql.append(sqlInsert.substring(1).toString());
		sql.append(") VALUES (");
		sql.append(sqlValues.substring(1).toString());
		sql.append(")");
		
		executeUpdate(sql.toString(), params.toArray());
	}
	
	/*
	 * 删除数据，
	 * 参数拥有map数据集合，和表名
	 */
	public static void delete(Map data, String table) throws Exception {
		StringBuffer sql = new StringBuffer("DELETE FROM " + table + " WHERE 1=1 ");
		List params = new ArrayList();
		if (data != null && !data.isEmpty()) {
			Iterator it = data.entrySet().iterator();

			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();
				if (Validator.isNullEmpty(entry.getValue())) {
					continue;
				}
				String key = (String)entry.getKey();
				String value = (String)entry.getValue();
				boolean isLike = false;
				if (value.startsWith("*")) {
					value = "%" + WebUtils.ltrim(value, '*');
					isLike = true;
				}
				if (value.endsWith("*")) {
					value = WebUtils.rtrim(value, '*') + "%";
					isLike = true;
				}
				
				if (isLike) {
					sql.append(" AND " + key + " LIKE ? ");
					params.add(value);
				} else {
					sql.append(" AND " + key + " = ? ");
					params.add(value);
				}
			}
		}
		executeUpdate(sql.toString(), params.toArray());
	}
	
	/**
	 * 通过id，表名，id字段名来删除
	 */
	public static void deleteByKey(String id, String table, String keyName) throws Exception {
		String sql = "DELETE FROM " + table + " WHERE " + keyName + "=?";
		
		executeUpdate(sql.toString(), new Object[]{id});
	}
	
	/**
	 * 通过传入参数集合map，表名table，数据库字段名 keyName
	 * 对表进行修改
	 */
	public static void updateByKey(Map data, String table, String keyName) throws Exception {
		String pk = (String)data.remove(keyName);
		StringBuffer sql = new StringBuffer("UPDATE " + table + " SET ");
		StringBuffer sqlValues = new StringBuffer();
		Iterator it = data.entrySet().iterator();
		List params = new ArrayList();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			if (Validator.isNullEmpty((String)entry.getValue())) {
				sqlValues.append("," + entry.getKey() + "=null");
			} else {
				sqlValues.append("," + entry.getKey() + "=?");
				params.add(entry.getValue());
			}
		}
		sql.append(sqlValues.substring(1).toString());
		sql.append(" WHERE " + keyName + "=?");
		params.add(pk);
		data.put(keyName, pk);
		
		executeUpdate(sql.toString(), params.toArray());
	}
	
	/**
	 * 
	 * 更新操作
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static int executeUpdate(String sql, Object[] params) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean autoCommit = false;
		int updateCount = 0;
		try {
			conn = ConnectionManager.getInstance().getConnection();
			
			autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			
			pstmt = conn.prepareStatement(sql);
			ProcessSql.setParams(pstmt, params);
			
			updateCount = pstmt.executeUpdate();
			
			pstmt.close();
			conn.commit();
			conn.close();
			
			pstmt = null;
			conn = null;
			
		} catch (SQLException sqle) {
			conn.rollback();
			sqle.printStackTrace();
			throw sqle;
		} catch (Exception e) {
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
			if (conn != null) {
				try {
					conn.setAutoCommit(autoCommit);
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return updateCount;
	}
	
	/**
	 * 通过传入参数id，表名table，字段名 keyName
	 * 
	 * 返回MAP对象集
	 * 
	 */
	public static Map queryByKey(String id, String table, String keyName) throws Exception {
		if (Validator.isNullEmpty(id)) {
			return null;
		}
		String sql = table + " WHERE " + keyName + "=?";
		List results = executeQuery(sql, new Object[]{id}, 0, 0);
		if (results.isEmpty()) {
			return null;
		}
		return (Map)results.get(0);
	}
	
	public static int executeUpdate(String sql) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		boolean autoCommit = false;
		int updateCount = 0;
		try {
			conn = ConnectionManager.getInstance().getConnection();
			
			autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			
			stmt = conn.createStatement();
			updateCount = stmt.executeUpdate(sql);
			
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();			
			return -1;		
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
					stmt = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.commit();
					conn.setAutoCommit(autoCommit);
					conn.close();
					conn = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return updateCount;
	}
	
	/*
	 * 查找序号，通过用户Id
	 * 传入用户id，表名，还有表的字段名，用户id的字段名
	 */
	public static int getXH(String id, String table, String keyName) throws Exception {
		String sql = "SELECT XH FROM " + table + " WHERE " + keyName + "=?";
		List list = executeQuery(sql, new Object[]{id}, 0, 0);
		if (list.isEmpty()) {
			return -1;
		}
		return Integer.parseInt((String)((Map)list.get(0)).get("XH"));
	}
	
	public static int count(Map data, String table) throws Exception {
		return count(data, table, "");
	}
	
	public static int count(Map data, String table, String condition) throws Exception {
		StringBuffer sql = new StringBuffer(table + " WHERE 1=1 ");
		List params = new ArrayList();
		
		if(data != null && !data.isEmpty()) {
			if (data != null && !data.isEmpty()) {
				sql.append(ProcessSql.makeQuerySql(data, params));
			}
		}
		return executeCount(sql.toString() + condition, params.toArray());
	}
	
	public static int executeCount(String sql) throws Exception {
		return executeCount("COUNT(1)", sql);
	}
	
	public static int executeCount(String countSql, String sql) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet results = null;
		List resultList = null;
		try {
			conn = ConnectionManager.getInstance().getConnection();
			stmt = conn.createStatement();
			
			results = stmt.executeQuery(ProcessSql.countResultSql(countSql, sql));
			resultList = getResultList(results);
			
		
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw sqle;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return Integer.parseInt((String)((Map)resultList.get(0)).get("CNT"));
	}

	public static int executeCount(String sql, Object[] params) throws Exception {
		return executeCount("COUNT(1)", sql, params);
	}
	
	/*
	 * 此方法返回int
	 * 通过对传的sql语句对数据库进行操作了哟
	 */
	public static int executeCount(String countSql, String sql, Object[] params) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet results = null;
		List resultList = null;
		
		try {
			conn = ConnectionManager.getInstance().getConnection();
			// countResultSql(countSql,sql)拼接字符串哟
			pstmt = conn.prepareStatement(ProcessSql.countResultSql(countSql, sql));
			if (params != null) {
				ProcessSql.setParams(pstmt, params);
			}
			results = pstmt.executeQuery();
			resultList = getResultList(results);
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw sqle;
		} catch (Exception e) {
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
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return Integer.parseInt((String)((Map)resultList.get(0)).get("CNT"));
	}
	
	public static List query(Map data, String table, String orderKey, 
			final int firstResult, final int maxResults) throws Exception {
		
		return query(data, table, "", orderKey, firstResult, maxResults);
	}
	
	/*
	 * 传入参数map集合，表名table，condition ,orderKey,firstResult,maxResults
	 * map集合中就是用来判断的表的字段，和值
	 * 
	 */
	public static List query(Map data, String table, String condition, String orderKey, 
			final int firstResult, final int maxResults) throws Exception {
		/*
		 * sql语句中后面加入where 1=1的作用
		 */
		StringBuffer sql = new StringBuffer(table + " WHERE 1=1 ");
		List params = new ArrayList();
		if (data != null && !data.isEmpty()) {
			sql.append(ProcessSql.makeQuerySql(data, params));
		}
		return executeQuery(sql.toString() + condition + " ORDER BY " + orderKey,
				params.toArray(), firstResult, maxResults);
	}
	
	/*
	 * 通过传入参数id，表名table，字段名 keyName
	 * 来查询
	 */
	public static List queryByKey1(String id, String table, String keyName) throws Exception {
		if (Validator.isNullEmpty(id)) {
			return null;
		}
		String sql = table + " WHERE " + keyName + "=?";
		List results = executeQuery(sql, new Object[]{id}, 0, 0);
		if (results.isEmpty()) {
			return null;
		}
		return results;
	}
	
	/*
	 * 
	 */
	public static List executeQuery(String sql, Object[] params, 
			final int pageNumber, final int maxResults) throws Exception {
		
		return executeQuery(sql, params, pageNumber, maxResults, Map.class);
	}
	
	/**
	 * 查询sql语句，通过sql语句查找
	 * 返回list集合,list集合中的元素可能是list集合，map集合，其他
	 * 还分页的 哟
	 */
	public static List executeQuery(String sql, Class clazz) throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet results = null;
		List resultList = null;
		
		try {
			conn = ConnectionManager.getInstance().getConnection();
			pstmt = conn.prepareStatement(sql);
			/*
			 * 返回ResultSet结果集
			 */
			results = pstmt.executeQuery();
			/*
			 * 通过ResultSet结果集与class clazz将数据返回子啊一个list集合中，
			 * 此list集合中的元素可以说是list，map，baen等
			 */
			resultList = ResultSetTransform.getResultList(results, clazz);
		
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw sqle;
		} catch (Exception e) {
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
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return resultList;
	}
	
	/**
	 * 查询sql语句，通过sql语句查找
	 * 返回list集合哟,list集合中的元素可能是list集合，map集合，其他
	 * 还分页的 哟
	 */
	public static List executeQuery(String sql, Object[] params, final int pageNumber, 
			final int maxResults, Class clazz) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet results = null;
		List resultList = null;
		
		try {
			conn = ConnectionManager.getInstance().getConnection();
			/*
			 * limitQueryResults(sql,pageNumber, maxResults)
			 * 对参数中的sql语句进行编辑，可以给sql语句加入select *　和limit int，int
			 */
			if(conn==null){
				
			}
			pstmt = conn.prepareStatement(ProcessSql.limitQueryResults(sql, pageNumber, 
					maxResults));
			if (params != null) {
				ProcessSql.setParams(pstmt, params);
			}
			
			results = pstmt.executeQuery();
			/*
			 * 通过ResultSet结果集与class clazz将数据返回子啊一个list集合中，
			 * 此list集合中的元素可以说是list，map，baen等
			 */
			resultList = ResultSetTransform.getResultList(results, clazz);
		
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw sqle;
		} catch (Exception e) {
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
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return resultList;
	}
	
	private static List getResultList(ResultSet results) throws Exception {
		return ResultSetTransform.getResultList(results, Map.class);
	}
	
//	/**
//	 * 不带查询参数分页
//	 * 
//	 * @param sql
//	 * @param pageNumber
//	 * @param maxResults
//	 * @return
//	 * @throws Exception
//	 */
//	public static PaginatedList queryPaginatedList(String sql, int pageNumber, 
//			final int maxResults) throws Exception {
//		
//		PaginationAdapter paginationList = new PaginationAdapter();
//		int count = executeCount(sql);
//		if (count == 0) {
//			return paginationList;
//		}
//		int maxPage = count/maxResults;
//		if (count%maxResults > 0) {
//			maxPage++;
//		}
//		if (pageNumber > maxPage) {
//			pageNumber = maxPage;
//		}
//		log.info(maxPage + ":pageNumber:" + pageNumber);
//		
//		paginationList.setList(executeQuery(sql, pageNumber, maxResults));
//		paginationList.setFullListSize(count);
//		paginationList.setPageNumber(pageNumber);
//		paginationList.setObjectsPerPage(maxResults);
//		
//		return paginationList;
//	}
	
	/**
	 * 为分页服务
	 * 
	 * @param sql
	 * @param pageNumber
	 * @param maxResults
	 * @return
	 * @throws Exception
	 */
	public static List executeQuery(String sql, final int pageNumber, 
			final int maxResults) throws Exception {
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet results = null;
		List resultList = null;
		try {
			conn = ConnectionManager.getInstance().getConnection();
			stmt = conn.createStatement();
			
			results = stmt.executeQuery(ProcessSql.limitQueryResults(sql, pageNumber, maxResults));
			resultList = getResultList(results);
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw sqle;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return resultList;
	}
	
//	/**
//	 * 带查询参数分页
//	 * 
//	 * @param selectSql
//	 * @param fromSql
//	 * @param params
//	 * @param pageNumber
//	 * @param maxResults
//	 * @return
//	 * @throws Exception
//	 */
//	public static PaginatedList queryPaginatedList(String selectSql, String fromSql, 
//			Object[] params, int pageNumber, final int maxResults) throws Exception {
//		
//		PaginationAdapter paginationList = new PaginationAdapter();
//		
//		/**
//		 * 查处满足条件的数据有多少，其中去掉条件order by,
//		 * 将所有的sql语句开头都变为select count(1) as cnt from  
//		 */
//		int count = executeCount(fromSql, params);
//		if (count == 0) {
//			return paginationList;
//		}
//		int maxPage = count/maxResults;
//		if (count%maxResults > 0) {
//			maxPage++;
//		}
//		if (pageNumber > maxPage) {
//			pageNumber = maxPage;
//		}
//		log.info(maxPage + ":pageNumber:" + pageNumber);
//		//当前页的结果集哟
//		
//		paginationList.setList(executeQuery(selectSql, params, pageNumber, maxResults));
//		//满足条件的结果总数
//		paginationList.setFullListSize(count);
//		//当前页号,从1开始
//		paginationList.setPageNumber(pageNumber);
//		//每一页的记录数，默认为15
//		paginationList.setObjectsPerPage(maxResults);
//		
//		return paginationList;
//	}
	
	/**
	 * 批量操作
	 * 
	 * @param sql
	 * @param paramList
	 * @return
	 * @throws Exception
	 */
	public static int[] executeBatch(String sql, List paramList) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean autoCommit = false;
		int[] updateCount;
		try {
			conn = ConnectionManager.getInstance().getConnection();
			autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			
			pstmt = conn.prepareStatement(sql);
			pstmt.clearBatch();
			for(int i=0; i<paramList.size(); i++) {
				ProcessSql.setParams(pstmt, (Object[])paramList.get(i));
				pstmt.addBatch();
			}
			updateCount = pstmt.executeBatch();
			
		} catch (SQLException sqle) {
			conn.rollback();
			sqle.printStackTrace();
			throw sqle;
		} catch (Exception e) {
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
			if (conn != null) {
				try {
					conn.commit();
					conn.setAutoCommit(autoCommit);
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return updateCount;
	}
	
	/**
	 * 
	 * @param sql
	 * @param params
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public static int executeUpdate(String sql, Object[] params, Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		int updateCount = 0; 
		try {
			pstmt = conn.prepareStatement(sql);
			ProcessSql.setParams(pstmt, params);
			
			updateCount = pstmt.executeUpdate();
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw sqle;
		} catch (Exception e) {
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
}