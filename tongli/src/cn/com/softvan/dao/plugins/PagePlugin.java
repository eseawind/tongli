package cn.com.softvan.dao.plugins;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.PropertyException;

import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.apache.log4j.Logger;

import cn.com.softvan.common.Validator;
import cn.com.softvan.dao.utils.ReflectHelper;
import cn.com.softvan.web.tag.PageInfo;

@Intercepts({@Signature(type=StatementHandler.class,method="prepare",args={Connection.class})})
public class PagePlugin implements Interceptor {
	/** 日志 */
	private static final transient Logger log = Logger
			.getLogger(PagePlugin.class);
	private static String dialect = "";	//数据库方言
	private static String pageSqlId = ""; //mapper.xml中需要拦截的ID(正则匹配)
	
	public Object intercept(Invocation ivk) throws Throwable {
		// TODO Auto-generated method stub
		if(ivk.getTarget() instanceof RoutingStatementHandler){
			RoutingStatementHandler statementHandler = (RoutingStatementHandler)ivk.getTarget();
			BaseStatementHandler delegate = (BaseStatementHandler) ReflectHelper.getValueByFieldName(statementHandler, "delegate");
			MappedStatement mappedStatement = (MappedStatement) ReflectHelper.getValueByFieldName(delegate, "mappedStatement");
			
			if(mappedStatement.getId().matches(pageSqlId)){ //拦截需要分页的SQL
				BoundSql boundSql = delegate.getBoundSql();
				Object parameterObject = boundSql.getParameterObject();//分页SQL<select>中parameterType属性对应的实体参数，即Mapper接口中执行分页方法的参数,该参数不得为空
				if(parameterObject==null){
					throw new NullPointerException("parameterObject尚未实例化！");
				}else{
					Connection connection = (Connection) ivk.getArgs()[0];
					String sql = boundSql.getSql();
					String countSql = "select count(0) from (" + getCountSql(sql)+ ") as tmp_count"; //记录统计
					PreparedStatement countStmt = connection.prepareStatement(countSql);
					BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(),countSql,boundSql.getParameterMappings(),parameterObject);
					setParameters(countStmt,mappedStatement,countBS,parameterObject);
					ResultSet rs = countStmt.executeQuery();
					int count = 0;
					if (rs.next()) {
						count = rs.getInt(1);
					}
					rs.close();
					countStmt.close();
					PageInfo page = null;
					if(parameterObject instanceof PageInfo){	//参数就是Page实体
						 page = (PageInfo) parameterObject;
						 page.setRecordCount(count);
						 //
						 page.initPageCount(page.getRecordCount(),page.getPageRowCount());
						 page.initCurrPage();
					}else{	//参数为某个实体，该实体拥有Page属性
						try {
							Field pageField = ReflectHelper.getFieldByFieldName(parameterObject,"pageInfo");
							if(pageField!=null){
								page = (PageInfo) ReflectHelper.getValueByFieldName(parameterObject,"pageInfo");
								if(page==null){
									page = new PageInfo(); 
								}
								page.setRecordCount(count);
								page.initPageCount(page.getRecordCount(),page.getPageRowCount());
								page.initCurrPage();
								ReflectHelper.setValueByFieldName(parameterObject,"pageInfo", page); //通过反射，对实体对象设置分页对象
							}else{
								throw new NoSuchFieldException(parameterObject.getClass().getName()+"不存在 pageInfo 属性！");
							}
						} catch (Exception e) {
//							e.printStackTrace();
							log.error("分页属性反射出错!", e);
							throw new NoSuchFieldException(parameterObject.getClass().getName()+"不存在 pageInfo 属性！");
						}
					}
					String pageSql = generatePageSql(sql,page);
					ReflectHelper.setValueByFieldName2(boundSql, "sql", pageSql); //将分页sql语句反射回BoundSql.
				}
			}
		}
		return ivk.proceed();
	}

	
	/**
	 * 对SQL参数(?)设值,参考org.apache.ibatis.executor.parameter.DefaultParameterHandler
	 * @param ps
	 * @param mappedStatement
	 * @param boundSql
	 * @param parameterObject
	 * @throws SQLException
	 */
	private void setParameters(PreparedStatement ps,MappedStatement mappedStatement,BoundSql boundSql,Object parameterObject) throws SQLException {
		ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		if (parameterMappings != null) {
			Configuration configuration = mappedStatement.getConfiguration();
			TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
			MetaObject metaObject = parameterObject == null ? null: configuration.newMetaObject(parameterObject);
			for (int i = 0; i < parameterMappings.size(); i++) {
				ParameterMapping parameterMapping = parameterMappings.get(i);
				if (parameterMapping.getMode() != ParameterMode.OUT) {
					Object value;
					String propertyName = parameterMapping.getProperty();
					PropertyTokenizer prop = new PropertyTokenizer(propertyName);
					if (parameterObject == null) {
						value = null;
					} else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
						value = parameterObject;
					} else if (boundSql.hasAdditionalParameter(propertyName)) {
						value = boundSql.getAdditionalParameter(propertyName);
					} else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX)&& boundSql.hasAdditionalParameter(prop.getName())) {
						value = boundSql.getAdditionalParameter(prop.getName());
						if (value != null) {
							value = configuration.newMetaObject(value).getValue(propertyName.substring(prop.getName().length()));
						}
					} else {
						value = metaObject == null ? null : metaObject.getValue(propertyName);
					}
					TypeHandler typeHandler = parameterMapping.getTypeHandler();
					if (typeHandler == null) {
						throw new ExecutorException("There was no TypeHandler found for parameter "+ propertyName + " of statement "+ mappedStatement.getId());
					}
					typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());
				}
			}
		}
	}
	
	/**
	 * 根据数据库方言，生成特定的分页sql
	 * @param sql
	 * @param page
	 * @return
	 */
	private String generatePageSql(String sql,PageInfo page){
		if(page!=null && Validator.notEmpty(dialect)){
			StringBuffer pageSql = new StringBuffer();
			if("mysql".equals(dialect)){
				pageSql.append(sql);
				pageSql.append(" limit "+page.getCurrPageFirstIndex()+","+page.getPageRowCount());
			}else if("oracle".equals(dialect)){
				pageSql.append("select * from (select tmp_tb.*,ROWNUM row_id from (");
				pageSql.append(sql);
				pageSql.append(") as tmp_tb where ROWNUM<=");
				pageSql.append(page.getCurrPageFirstIndex()+page.getPageRowCount());
				pageSql.append(") where row_id>");
				pageSql.append(page.getCurrPageFirstIndex());
			}
			return pageSql.toString();
		}else{
			return sql;
		}
	}
	
	public Object plugin(Object arg0) {
		// TODO Auto-generated method stub
		return Plugin.wrap(arg0, this);
	}

	public void setProperties(Properties p) {
		dialect = p.getProperty("dialect");
		if (Validator.isEmpty(dialect)) {
			try {
				throw new PropertyException("dialect property is not found!");
			} catch (PropertyException e) {
				// TODO Auto-generated catch block
				log.error(e);
			}
		}
		pageSqlId = p.getProperty("pageSqlId");
		if (Validator.isEmpty(pageSqlId)) {
			try {
				throw new PropertyException("pageSqlId property is not found!");
			} catch (PropertyException e) {
				// TODO Auto-generated catch block
				log.error(e);
			}
		}
	}
	/**
	 * JAVA正则表达式根据查询SQL自动生成统计SQL
	 * @param sql
	 * @return
	 */
    public static String getCountSql(String sql) {
    	
    	String sql1=sql;
    	String sql2=sql1.replaceAll("from ","");
    	int xx= sql1.length()-sql2.length();
    	if(xx>7){
    		return sql;
    	}else{
    		Pattern p = Pattern.compile("\\t|\r|\n");
    		Matcher m = p.matcher(sql);
    		sql= m.replaceAll(" ");
            String regex = "(^select)(.*?)( from .*)";
             p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
             m = p.matcher(sql);
//             System.err.println(sql);
            boolean rs = m.find();
            if (rs == false || m.groupCount() != 3) {
                return sql;
            } else{
                return m.group(1) + " 1 " + m.group(3);
            }
    	}
    }
    //
    public static void main(String[] args){
    	String sql1=("select *, as xx from dd left join (select * from gg) on ss=dd)");
    	String sql2=sql1.replaceAll("from ","");
    	int xx= sql1.length()-sql2.length();
    	if(xx>7){
    		System.out.println("xxxx"+xx);
    	}else{
    		System.out.println(xx);
    	}
    }
}