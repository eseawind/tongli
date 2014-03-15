package cn.com.softvan.common;

import java.io.File;


/**
 * <p>系统程序用常量</p>
 * @author wuxiaogang
 *
 */
public class CommonConstant {
	/** 数据库事务默认超时时间 */
	public static final int DB_DEFAULT_TIMEOUT = 300;
	/** 分页对象KEY */
	public static final String PAGEROW_OBJECT_KEY = "PAGEROW_OBJECT_KEY";
	/** 分页偏移量名称 */
	public static final String PAGEROW_OFFSET_NAME = "offset";
	/** 默认画面每页的记录数 */
	public static final int PAGEROW_DEFAULT_COUNT = 15;
	/** 画面显示的页码数量 */
	public static final int PAGEROW_CURR_NENT_COUNT = 15;
	/** SESSION里面存放认证码 */
	public static final String SESSION_VERIFY_CODE = "SESSION_VERIFY_CODE";
	/** SESSION里面存放 用户信息 */
	public static final String SESSION_KEY_USER = "SESSION_KEY_USER";
	/** 路径分隔符 */
	public static final String PATH_SEPARATOR = File.separator;
	/** 系统默认编码 */
	public static final String DEFAULT_ENCODE = "UTF-8";
	/** 空字符串 */
	public static final String EMPTY_STRING = "";
	/** 空格 */
	public static final String BLANK_STRING = " ";
	/** 所属地区县 */
	public static final String LOG_ERROR_TITLE = "异常信息";
	
	
	/**缓存时间 车辆   24小时*/
	public static final int VEHICLE_KEEP_TIME = 60*60*24;
	/**缓存时间  保单12小时*/
	public static final int POLICY_KEEP_TIME = 60*60*12;
	/**缓存时间  理赔 30分钟*/
	public static final int CLAIM_KEEP_TIME = 60*30;
	/**缓存时间  违章 24小时*/
	public static final int VIOLATION_KEEP_TIME = 60*60*24;
	
	/**缓存 用户对应的车辆id列表key前缀*/
	public static final String USER_VEHICLE_KEY = "user_vehicle_";
	/**缓存 车辆id对应的车辆信息key前缀*/
	public static final String VEHICLE_KEY = "vehicle_";
	/**缓存 用户对应保单key前缀*/
	public static final String POLICY_KEY = "user_policy_";
	/**缓存 用户理赔key前缀*/
	public static final String CLAIM_KEY = "user_claim_";
	/**缓存 用户违章key前缀*/
	public static final String VIOLATION_KEY = "user_violation_";
	
	
}
