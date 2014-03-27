package cn.com.softvan.common.wechat;

import org.jdom.Element;
import org.jdom.JDOMException;

import cn.com.softvan.bean.wechat.receive.WxRecvEventMsg;
import cn.com.softvan.bean.wechat.receive.WxRecvMsg;
/**
 * 事件消息解析程序
 * @author wuxiaogang 
 *
 */
public class WxRecvEventMsgParser extends WxRecvMsgBaseParser {

	@Override
	protected WxRecvEventMsg parser(Element root, WxRecvMsg msg) throws JDOMException {
		String event = getElementText(root, "Event");//消息类型
		String eventKey = getElementText(root, "EventKey");//事件类型
		String latitude=getElementText(root, "Latitude");//地理位置纬度
		String longitude=getElementText(root, "Longitude");//地理位置经度
		String precision=getElementText(root, "Precision");//地理位置精度
		String ticket=getElementText(root, "Ticket");//二维码的ticket，可用来换取二维码图片
		return new WxRecvEventMsg(msg, event,eventKey,latitude,longitude,precision,ticket);
	}

}
