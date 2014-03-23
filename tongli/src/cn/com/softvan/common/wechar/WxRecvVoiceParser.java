package cn.com.softvan.common.wechar;

import org.jdom.Element;
import org.jdom.JDOMException;

import cn.com.softvan.bean.wechar.receive.WxRecvMsg;
import cn.com.softvan.bean.wechar.receive.WxRecvVoiceMsg;
/**
 * 语音消息解析程序
 * @author wuxiaogang 
 *
 */
public class WxRecvVoiceParser extends WxRecvMsgBaseParser {

	@Override
	protected WxRecvVoiceMsg parser(Element root, WxRecvMsg msg) throws JDOMException {
		return new WxRecvVoiceMsg(msg, getElementText(root, "MediaId"),getElementText(root, "Format"));
	}

}
