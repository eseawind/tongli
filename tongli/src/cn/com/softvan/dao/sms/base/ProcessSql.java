package cn.com.softvan.dao.sms.base;

import java.sql.PreparedStatement;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.com.softvan.common.Validator;
import cn.com.softvan.common.WebUtils;

public class ProcessSql {
	private static final transient Logger log = Logger.getLogger(ProcessSql.class);
	/**
	 * 对参数中的sql语句进行编辑，可以给sql语句加入select *　和limit int，int
	 */
	public static String limitQueryResults(String sql, final int pageNumber, 
			final int maxResults) {
		String selectSql = sql;
		
		/*
		 * 假如传入的sql语句不是select开头的
		 * 则在sql语句最前面加入select * from
		 */
		if (!sql.startsWith("SELECT ")) {
			 selectSql = "SELECT * FROM " + sql;
		}
		
		/*
		 * 假如每页显示的数量大于0，并且结果集也大于0
		 * 则在sql语句最后面加入limit
		 */
		if (pageNumber > 0 && maxResults > 0) {
			selectSql += " limit " + maxResults*(pageNumber-1) + "," + maxResults;
		}
		log.info(selectSql);
		
		return selectSql;
	}
	
	/**
	 * 此方法的作用
	 * 判断两个sql语句是否有 select * from 或 from  或order by 
	 * 若都没有则将两个sql拼接了
	 * 将传入的sql语句的from前面的去掉在加上包括from，在最前面加入select count(1) as cnt from
	 * 然后将order by和其后面的都去掉了
	 */
	public static String countResultSql(String countSql, String sql) {
		String selectSql = sql;

		/*
		 * 判断sql语句中是否有select 。。。。
		 * 将sql语句中from包括from前面的字符全部去掉
		 */
		if (selectSql.startsWith(" FROM ")) {
			selectSql = sql.substring(5);
		} else {
			
			/*
			 * sql语句中有select * from 则将其从sql语句中去除
			 */
			String regx = "SELECT * FROM ";
			
			/*
			 * lastIndexOf(String)返回所用字符串最后一次出现指定元素的索引，
			 * 如果此字符串不包含指定元素，则返回-1
			 */
			int pos = sql.lastIndexOf(regx);
			if (pos == -1) {
				regx = " FROM ";
				/*
				 * 找出sql语句中from的下标
				 */
				pos = sql.lastIndexOf(regx);
			}
			
			if (pos > -1) {
				/*
				 * 将sql语句from之前的语句全部删除
				 */
				selectSql = sql.substring(pos + regx.length());
			}
			pos = selectSql.lastIndexOf(" ORDER BY ");
			if (pos > -1) {
				selectSql = selectSql.substring(0, pos);
			}
		}
		selectSql = "SELECT " + countSql + " AS CNT FROM " + selectSql;
		log.info(selectSql);
		
		return selectSql;
	}
	
	/*
	 * 编辑查询的sql语句，向sql语句中插入条件
	 * 就是制造sql语句
	 */
	public static String makeQuerySql(Map data, List params) {
		StringBuffer sql = new StringBuffer();
		Iterator it = data.entrySet().iterator();

		/*
		 * 对每个字段都进行检索
		 */
		while(it.hasNext()) {
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
		return sql.toString();
	}	

	/*
	 * 因为PreparedStatement pres.setObject(i,"")i是从1开始
	 * 所以要多传入一个参数哟
	 */
	public static void setParams(PreparedStatement pstmt, Object[] params, 
												int offset) throws Exception {
		for(int i = offset; i < params.length + offset; i++) {
			pstmt.setObject(i, params[i-1]);
		}
	}

	/*
	 * 把参数和参数的值与PreparedStatement对应的起来，并加入进去
	 */
	public static void setParams(PreparedStatement pstmt, Object[] params) throws Exception {
		setParams(pstmt, params, 1);
	}
}
