package cn.com.softvan.bean.wechar.receive;


public class WxRecvEventMsg extends WxRecvMsg {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2108543890708821710L;
	private String event;
	private String eventKey;
	
	public WxRecvEventMsg(WxRecvMsg msg,String event,String eventKey) {
		super(msg);
		this.event = event;
		this.eventKey = eventKey;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getEventKey() {
		return eventKey;
	}
	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
}
