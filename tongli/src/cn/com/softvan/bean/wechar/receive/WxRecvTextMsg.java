package cn.com.softvan.bean.wechar.receive;



public class WxRecvTextMsg extends WxRecvMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = -160832654018168520L;
	private String content;
	
	public WxRecvTextMsg(WxRecvMsg msg,String content) {
		super(msg);
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
