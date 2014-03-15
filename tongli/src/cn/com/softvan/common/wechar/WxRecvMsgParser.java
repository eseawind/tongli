package cn.com.softvan.common.wechar;

import org.jdom.Document;
import org.jdom.JDOMException;

import cn.com.softvan.bean.wechar.receive.WxRecvMsg;
/**
 * 信息接口
 * @author wuxiaogang 
 *
 */
public interface WxRecvMsgParser {
	WxRecvMsg parser(Document doc) throws JDOMException;
}
