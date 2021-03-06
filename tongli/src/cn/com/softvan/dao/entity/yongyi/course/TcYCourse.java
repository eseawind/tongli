
/*	
 * 课程信息_y  数据库entity类	
 *		
 * VERSION      DATE          BY              REASON		
 * -------- ----------- --------------- ------------------------------------------	
 * 1.00     2014.09.21      wuxiaogang         程序.发布		
 * -------- ----------- --------------- ------------------------------------------	
 * Copyright 2014 tongli  System. - All Rights Reserved.		
 *	
 */
package cn.com.softvan.dao.entity.yongyi.course;
import cn.com.softvan.dao.entity.BaseEntity;
/**
 * <p>课程信息_y  数据库entity类。</p>	
 * @author wuxiaogang
 */
public class TcYCourse extends BaseEntity{

	/**课程编号 */
	private String id;
	/**主题 */
	private String subject_id;
	/**标题图 */
	private String pic_url;
	/**课种 */
	private String course_type;
	/**课程名称 */
	private String title;
	/**时长 */
	private String duration;
	/**时长单位 */
	private String duration_unit;
	/**年龄段 */
	private String age_group;
	/**人数 */
	private String number;
	/**非会员价 */
	private String market_price;
	/**会员价 */
	private String member_price;
	/**是否室内 */
	private String is_indoor;
	/**是否包含场地费 */
	private String is_site_fee;
	/**课程地址 */
	private String addres;
	/**课程详情 */
	private String detail_info;
	/**
	 * 课程编号 设定 
	 * @param id课程编号
	 */
	public void setId(String id){
		this.id=id;
	}
	/**
	 * 课程编号 取得 
	 * @return 课程编号
	 */
	public String getId(){
		return id;
	}
	/**
	 * 主题 设定 
	 * @param subject_id主题
	 */
	public void setSubject_id(String subject_id){
		this.subject_id=subject_id;
	}
	/**
	 * 主题 取得 
	 * @return 主题
	 */
	public String getSubject_id(){
		return subject_id;
	}
	/**
	 * 标题图 设定 
	 * @param pic_url标题图
	 */
	public void setPic_url(String pic_url){
		this.pic_url=pic_url;
	}
	/**
	 * 标题图 取得 
	 * @return 标题图
	 */
	public String getPic_url(){
		return pic_url;
	}
	/**
	 * 课种 设定 
	 * @param course_type课种
	 */
	public void setCourse_type(String course_type){
		this.course_type=course_type;
	}
	/**
	 * 课种 取得 
	 * @return 课种
	 */
	public String getCourse_type(){
		return course_type;
	}
	/**
	 * 课程名称 设定 
	 * @param title课程名称
	 */
	public void setTitle(String title){
		this.title=title;
	}
	/**
	 * 课程名称 取得 
	 * @return 课程名称
	 */
	public String getTitle(){
		return title;
	}
	/**
	 * 时长 设定 
	 * @param duration时长
	 */
	public void setDuration(String duration){
		this.duration=duration;
	}
	/**
	 * 时长 取得 
	 * @return 时长
	 */
	public String getDuration(){
		return duration;
	}
	/**
	 * 时长单位 设定 
	 * @param duration_unit时长单位
	 */
	public void setDuration_unit(String duration_unit){
		this.duration_unit=duration_unit;
	}
	/**
	 * 时长单位 取得 
	 * @return 时长单位
	 */
	public String getDuration_unit(){
		return duration_unit;
	}
	/**
	 * 年龄段 设定 
	 * @param age_group年龄段
	 */
	public void setAge_group(String age_group){
		this.age_group=age_group;
	}
	/**
	 * 年龄段 取得 
	 * @return 年龄段
	 */
	public String getAge_group(){
		return age_group;
	}
	/**
	 * 人数 设定 
	 * @param number人数
	 */
	public void setNumber(String number){
		this.number=number;
	}
	/**
	 * 人数 取得 
	 * @return 人数
	 */
	public String getNumber(){
		return number;
	}
	/**
	 * 非会员价 设定 
	 * @param market_price非会员价
	 */
	public void setMarket_price(String market_price){
		this.market_price=market_price;
	}
	/**
	 * 非会员价 取得 
	 * @return 非会员价
	 */
	public String getMarket_price(){
		return market_price;
	}
	/**
	 * 会员价 设定 
	 * @param member_price会员价
	 */
	public void setMember_price(String member_price){
		this.member_price=member_price;
	}
	/**
	 * 会员价 取得 
	 * @return 会员价
	 */
	public String getMember_price(){
		return member_price;
	}
	/**
	 * 是否室内 设定 
	 * @param is_indoor是否室内
	 */
	public void setIs_indoor(String is_indoor){
		this.is_indoor=is_indoor;
	}
	/**
	 * 是否室内 取得 
	 * @return 是否室内
	 */
	public String getIs_indoor(){
		return is_indoor;
	}
	/**
	 * 是否包含场地费 设定 
	 * @param is_site_fee是否包含场地费
	 */
	public void setIs_site_fee(String is_site_fee){
		this.is_site_fee=is_site_fee;
	}
	/**
	 * 是否包含场地费 取得 
	 * @return 是否包含场地费
	 */
	public String getIs_site_fee(){
		return is_site_fee;
	}
	/**
	 * 课程地址 设定 
	 * @param addres课程地址
	 */
	public void setAddres(String addres){
		this.addres=addres;
	}
	/**
	 * 课程地址 取得 
	 * @return 课程地址
	 */
	public String getAddres(){
		return addres;
	}
	/**
	 * 课程详情 设定 
	 * @param detail_info课程详情
	 */
	public void setDetail_info(String detail_info){
		this.detail_info=detail_info;
	}
	/**
	 * 课程详情 取得 
	 * @return 课程详情
	 */
	public String getDetail_info(){
		return detail_info;
	}
}