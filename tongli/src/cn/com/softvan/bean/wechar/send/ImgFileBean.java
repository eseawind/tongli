package cn.com.softvan.bean.wechar.send;

import java.io.File;

import cn.com.softvan.bean.BaseBean;

public class ImgFileBean  extends BaseBean{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5602383514779900851L;
	//?cgi=uploadmaterial&type=2&token=416919388&t=iframe-uploadfile&lang=zh_CN&formId=1
    private String cgi = "uploadmaterial";
    private String type = "2";
    private String token = "";
    private String t = "iframe-uploadfile";
    private String lang = "zh_CN";
    private String formId = "1";
    private File uploadfile = null;

    public String getCgi() {
        return cgi;
    }

    public void setCgi(String cgi) {
        this.cgi = cgi;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public File getUploadfile() {
        return uploadfile;
    }

    public void setUploadfile(File uploadfile) {
        this.uploadfile = uploadfile;
    }
}
