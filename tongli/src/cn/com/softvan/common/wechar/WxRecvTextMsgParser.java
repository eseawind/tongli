package cn.com.softvan.common.wechar;

import org.jdom.Element;
import org.jdom.JDOMException;

import cn.com.softvan.bean.wechar.receive.WxRecvMsg;
import cn.com.softvan.bean.wechar.receive.WxRecvTextMsg;
/**
 * 文本消息解析程序
 * @author wuxiaogang 
 *
 */
public class WxRecvTextMsgParser extends WxRecvMsgBaseParser{

	@Override
	protected WxRecvTextMsg parser(Element root, WxRecvMsg msg) throws JDOMException {
		return new WxRecvTextMsg(msg, getElementText(root, "Content"));
	}

	
}
