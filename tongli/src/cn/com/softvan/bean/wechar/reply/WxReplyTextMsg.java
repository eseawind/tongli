package cn.com.softvan.bean.wechar.reply;

import org.jdom.Document;

public class WxReplyTextMsg extends WxReplyMsg {
	private String content;
	
	public WxReplyTextMsg(WxReplyMsg msg,String content) {
		super(msg);
		setMsgType("text");
		this.content = content;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public Document toDocument() {
		Document doc = super.toDocument();
		createElement(doc.getRootElement(), "Content", getContent());
		return doc;
	}
}
