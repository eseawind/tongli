package cn.com.softvan.common.wechat;

import org.jdom.Element;
import org.jdom.JDOMException;

import cn.com.softvan.bean.wechat.receive.WxRecvLinkMsg;
import cn.com.softvan.bean.wechat.receive.WxRecvMsg;
/**
 * 链接消息解析程序
 * @author wuxiaogang 
 *
 */
public class WxRecvLinkMsgParser extends WxRecvMsgBaseParser {

	@Override
	protected WxRecvLinkMsg parser(Element root, WxRecvMsg msg) throws JDOMException {
		
		String title = getElementText(root, "Title");
		String description = getElementText(root, "Description");
		String url = getElementText(root, "Url");
		return new WxRecvLinkMsg(msg, title, description, url);
	}

}
