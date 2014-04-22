package cn.com.softvan.bean.wechat.send;

import cn.com.softvan.bean.wechat.WxMsg;
/**
 * 客服响应 文本回复
 * @author wuxiaogang 
 *
 */
public class WxSendTextMsg extends WxMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9180438288300488808L;
	/** 信息内容 */
	private String  content;
	public WxSendTextMsg(String toUser,String msgType,String content){
		super(toUser, msgType);
		this.content=content;
	}
	public String toJson(){
		StringBuffer sb=new StringBuffer();
		sb.append("{");
		sb.append("\"touser\":\""+getToUser()+"\",");
		sb.append("\"msgtype\":\"text\",");
		sb.append("\"text\":");
		sb.append("{");
		sb.append("\"content\":\""+getContent()+"\"");
		sb.append("}");
		sb.append("}");
		return sb.toString();
	}
	/**
	 * 信息内容取得
	 * @return 信息内容
	 */
	public String getContent() {
	    return content;
	}
	/**
	 * 信息内容设定
	 * @param content 信息内容
	 */
	public void setContent(String content) {
	    this.content = content;
	}
	
	
}
