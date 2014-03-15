/*
 * 短消息常量类
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2011.11.16  Huyunlin           程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2011 softvan System. - All Rights Reserved.
 *
 */
package cn.com.softvan.common.sms;


/**
 * <p>短消息常量</p>
 * @author Huyunlin
 *
 */
public class SmsConstant {

    /** 默认的构造函数*/
    private SmsConstant() {
    }

    /** 短信最大群发数量 */
    public static final int SMS_GROUP_DST_COUNT = 3;
//    /** 短信默认发送编码 */
//    public static final String SMS_DEFAULT_ENCODE = "GBK";
//	/** 主机 */
//    public static final String SMS_HOST = "211.136.163.68";
//	/** 端口 */
//    public static final int SMS_PORT = 9981;
//	/** 用户名 */
//    public static final String SMS_ACCOUNT_ID = "10657109042619";
//	/** 密码 */
//    public static final String SMS_PASSWORD = "Shanghai1106Tour";
//	/** 企业服务代码 */
//    public static final String SMS_SERVICE_ID = "QSH0110500";
    
	/** 长短消息字符数 */
    public static final int SMS_LONG_CHAR = 70;
	/** 消息字符数 */
    public static final int SMS_CHAR_COUNT = 140;
    
	/** 发送短信状态:OK */
    public static final int SMS_STATUS_OK = 0;
	/** 发送短信状态:ERROR */
    public static final int SMS_STATUS_ERROR = 1;
    /** 短信重复发送次数*/
    public static final int SMS_RESEND_COUNT = 3;
    /** 短信收件人区分长度*/
    public static final int SMS_TO_LENGTH = 255;
    /** 定时器间隔时间*/
    public static final int SMS_TIMER_COUNT = 10;
    
    /** 短信状态：1 未发送*/
    public static final String SMS_STATUS_NO = "1";
    /** 短信状态：2 发送中*/
    public static final String SMS_STATUS_SENDING = "2";
    /** 短信状态：3 已发送*/
    public static final String SMS_STATUS_FINISHED = "3";
    
    /** 收件人分隔符*/
    public static final String SMS_SEPRATE_TO = ",";
    
    /** 收件人分隔符*/
    public static final String SMS_SEPRATE_TO_NEW = ";";
}
