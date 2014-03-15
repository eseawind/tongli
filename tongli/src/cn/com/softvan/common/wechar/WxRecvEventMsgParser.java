package cn.com.softvan.common.wechar;

import org.jdom.Element;
import org.jdom.JDOMException;

import cn.com.softvan.bean.wechar.receive.WxRecvEventMsg;
import cn.com.softvan.bean.wechar.receive.WxRecvMsg;
/**
 * 事件消息解析程序
 * @author wuxiaogang 
 *
 */
public class WxRecvEventMsgParser extends WxRecvMsgBaseParser {

	@Override
	protected WxRecvEventMsg parser(Element root, WxRecvMsg msg) throws JDOMException {
		String event = getElementText(root, "Event");
		String eventKey = getElementText(root, "EventKey");
		
		return new WxRecvEventMsg(msg, event,eventKey);
	}

}
