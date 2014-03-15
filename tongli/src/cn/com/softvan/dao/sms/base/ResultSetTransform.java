package cn.com.softvan.dao.sms.base;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

public class ResultSetTransform {
	
	/*
	 * 通过结果集，map集合来返回李list集合数据哟
	 * 假如Class clazz是map集合则list存入的元素是map集合
	 * 加入class clazz是list集合则list存入的元素是list集合
	 */
	public static List getResultList(ResultSet results, Class clazz) throws Exception {
		List resultList = new ArrayList();
		ResultSetMetaData metaData = results.getMetaData();
		if (clazz == Map.class) {
			while(results.next()) {
				Map recordmap = new HashMap();
				for(int i = 1; i <= metaData.getColumnCount(); i++) {
					recordmap.put(metaData.getColumnLabel(i), results.getString(i));
				}
				resultList.add(recordmap);
			}
		}
		else if (clazz == List.class) {
			while(results.next()) {
				List recordList = new ArrayList();
				for(int i = 1; i <= metaData.getColumnCount(); i++) {
					recordList.add(results.getString(i));
				}
				resultList.add(recordList);
			}
		}
		else {
			while(results.next()) {
				Object bean = clazz.newInstance();
				
				for(int i = 1; i <= metaData.getColumnCount(); i++) {
					if (Types.LONGVARBINARY == metaData.getColumnType(i)) {
						
						InputStream in = results.getBinaryStream(i);
						if (in != null) {
						    ByteArrayOutputStream outp = new ByteArrayOutputStream();
						    int c;
						    while ((c = in.read()) != -1){
						    	outp.write(c);
						    }
						    
							BeanUtils.setProperty(bean, metaData.getColumnName(i), outp.toString());
						    
						    in.close();
						    outp.close();
						}
					    
					} else {
						BeanUtils.setProperty(bean, metaData.getColumnName(i), 
								results.getString(i));
					}
				}
				resultList.add(bean);
			}
		}
		return resultList;
	}
	
	private static String makeMethodName(String name) {
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < name.length(); i++) {
			if (i > 0) {
				sb.append(Character.toLowerCase(name.charAt(i)));
			} else {
				sb.append(Character.toUpperCase(name.charAt(i)));
			}
		}
		return sb.toString();
	}
}