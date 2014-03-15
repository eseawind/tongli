package cn.com.softvan.bean.wechar.send;

import cn.com.softvan.bean.BaseBean;


/**
 * 群发消息表单
 */
public class MsgBean  extends BaseBean{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8990329538309620022L;
	/**
     * fid | appmsgid
     */
    private String type = "1";
    private String content = "";
    private String error = "false";
    private String needcomment = "0";
    private String groupid = "-1";
    private String sex = "0";
    private String country = "";
    private String province = "";
    private String city = "";
    private String token = "";
    private String ajax = "1";
    private String fakeid="";
    private String t="ajax-response";
    private String lang="zh_CN";
    private String count="20";
    private String fid;
    private String appmsgid;


	/**
	 * fakeid取得
	 * @return fakeid
	 */
	public String getFakeid() {
	    return fakeid;
	}

	/**
	 * fakeid设定
	 * @param fakeid fakeid
	 */
	public void setFakeid(String fakeid) {
	    this.fakeid = fakeid;
	}

	/**
	 * t取得
	 * @return t
	 */
	public String getT() {
	    return t;
	}

	/**
	 * t设定
	 * @param t t
	 */
	public void setT(String t) {
	    this.t = t;
	}

	/**
	 * lang取得
	 * @return lang
	 */
	public String getLang() {
	    return lang;
	}

	/**
	 * lang设定
	 * @param lang lang
	 */
	public void setLang(String lang) {
	    this.lang = lang;
	}

	/**
	 * count取得
	 * @return count
	 */
	public String getCount() {
	    return count;
	}

	/**
	 * count设定
	 * @param count count
	 */
	public void setCount(String count) {
	    this.count = count;
	}

	/**
	 * fid取得
	 * @return fid
	 */
	public String getFid() {
	    return fid;
	}

	/**
	 * fid设定
	 * @param fid fid
	 */
	public void setFid(String fid) {
	    this.fid = fid;
	}

	public void setType(MsgTypeBean type) {
        this.type = type.getType() + "";
    }

    /**
	 * fid | appmsgid取得
	 * @return fid | appmsgid
	 */
	public String getType() {
	    return type;
	}

	/**
	 * fid | appmsgid设定
	 * @param type fid | appmsgid
	 */
	public void setType(String type) {
	    this.type = type;
	}

	/**
	 * content取得
	 * @return content
	 */
	public String getContent() {
	    return content;
	}

	/**
	 * content设定
	 * @param content content
	 */
	public void setContent(String content) {
	    this.content = content;
	}

	/**
	 * error取得
	 * @return error
	 */
	public String getError() {
	    return error;
	}

	/**
	 * error设定
	 * @param error error
	 */
	public void setError(String error) {
	    this.error = error;
	}

	/**
	 * needcomment取得
	 * @return needcomment
	 */
	public String getNeedcomment() {
	    return needcomment;
	}

	/**
	 * needcomment设定
	 * @param needcomment needcomment
	 */
	public void setNeedcomment(String needcomment) {
	    this.needcomment = needcomment;
	}

	/**
	 * groupid取得
	 * @return groupid
	 */
	public String getGroupid() {
	    return groupid;
	}

	/**
	 * groupid设定
	 * @param groupid groupid
	 */
	public void setGroupid(String groupid) {
	    this.groupid = groupid;
	}

	/**
	 * sex取得
	 * @return sex
	 */
	public String getSex() {
	    return sex;
	}

	/**
	 * sex设定
	 * @param sex sex
	 */
	public void setSex(String sex) {
	    this.sex = sex;
	}

	/**
	 * country取得
	 * @return country
	 */
	public String getCountry() {
	    return country;
	}

	/**
	 * country设定
	 * @param country country
	 */
	public void setCountry(String country) {
	    this.country = country;
	}

	/**
	 * province取得
	 * @return province
	 */
	public String getProvince() {
	    return province;
	}

	/**
	 * province设定
	 * @param province province
	 */
	public void setProvince(String province) {
	    this.province = province;
	}

	/**
	 * city取得
	 * @return city
	 */
	public String getCity() {
	    return city;
	}

	/**
	 * city设定
	 * @param city city
	 */
	public void setCity(String city) {
	    this.city = city;
	}

	/**
	 * token取得
	 * @return token
	 */
	public String getToken() {
	    return token;
	}

	/**
	 * token设定
	 * @param token token
	 */
	public void setToken(String token) {
	    this.token = token;
	}

	public String getAjax() {
        return ajax;
    }

    public void setAjax(String ajax) {
        this.ajax = ajax;
    }

	/**
	 * appmsgid取得
	 * @return appmsgid
	 */
	public String getAppmsgid() {
	    return appmsgid;
	}

	/**
	 * appmsgid设定
	 * @param appmsgid appmsgid
	 */
	public void setAppmsgid(String appmsgid) {
	    this.appmsgid = appmsgid;
	}

}

