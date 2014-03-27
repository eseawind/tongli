package cn.com.softvan.bean.wechat.receive;


public class WxRecvEventMsg extends WxRecvMsg {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2108543890708821710L;
	/**事件类型*/
	private String event;
	/**事件KEY值*/
	private String eventKey;
	/**地理位置纬度*/
	private String latitude;
	/**地理位置经度*/
	private String longitude;
	/**地理位置精度*/
	private String precision;
	/**二维码的ticket，可用来换取二维码图片*/
	private String ticket;
	public WxRecvEventMsg(WxRecvMsg msg,String event,String eventKey,String latitude,String longitude,String precision,String ticket) {
		super(msg);
		this.event = event;
		this.eventKey = eventKey;
		this.latitude=latitude;
		this.longitude=longitude;
		this.precision=precision;
		this.ticket=ticket;
	}
	/**
	 * 事件类型取得
	 * @return 事件类型
	 */
	public String getEvent() {
		return event;
	}
	/**
	 * 事件类型设定
	 * @param event 事件类型
	 */
	public void setEvent(String event) {
		this.event = event;
	}
	/**
	 * 事件KEY值取得
	 * @return 事件KEY值
	 */
	public String getEventKey() {
		return eventKey;
	}
	/**
	 * 事件KEY值设定
	 * @param eventKey 事件KEY值
	 */
	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
	/**
	 * 地理位置纬度取得
	 * @return 地理位置纬度
	 */
	public String getLatitude() {
	    return latitude;
	}
	/**
	 * 地理位置纬度设定
	 * @param latitude 地理位置纬度
	 */
	public void setLatitude(String latitude) {
	    this.latitude = latitude;
	}
	/**
	 * 地理位置经度取得
	 * @return 地理位置经度
	 */
	public String getLongitude() {
	    return longitude;
	}
	/**
	 * 地理位置经度设定
	 * @param longitude 地理位置经度
	 */
	public void setLongitude(String longitude) {
	    this.longitude = longitude;
	}
	/**
	 * 地理位置精度取得
	 * @return 地理位置精度
	 */
	public String getPrecision() {
	    return precision;
	}
	/**
	 * 地理位置精度设定
	 * @param precision 地理位置精度
	 */
	public void setPrecision(String precision) {
	    this.precision = precision;
	}
	/**
	 * 二维码的ticket，可用来换取二维码图片取得
	 * @return 二维码的ticket，可用来换取二维码图片
	 */
	public String getTicket() {
	    return ticket;
	}
	/**
	 * 二维码的ticket，可用来换取二维码图片设定
	 * @param ticket 二维码的ticket，可用来换取二维码图片
	 */
	public void setTicket(String ticket) {
	    this.ticket = ticket;
	}
}
