package cn.com.softvan.service.wechar;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.com.softvan.bean.wechar.send.FansCountBean;
import cn.com.softvan.bean.wechar.send.ImgFileBean;
import cn.com.softvan.bean.wechar.send.ImgTextBean;
import cn.com.softvan.bean.wechar.send.ImgTextMsgListBean;
import cn.com.softvan.bean.wechar.send.LoginJsonBean;
import cn.com.softvan.bean.wechar.send.MsgBean;
import cn.com.softvan.bean.wechar.send.MsgJsonBean;
import cn.com.softvan.bean.wechar.send.MsgTypeBean;
import cn.com.softvan.common.HtmlUtil;

import com.alibaba.fastjson.JSON;

public class WeCharClent {
    private final static Log log = LogFactory.getLog(WeCharClent.class);
    public final static String HOST = "http://mp.weixin.qq.com";
    public final static String LOGIN_URL = "http://mp.weixin.qq.com/cgi-bin/login?lang=zh_CN";
    public final static String INDEX_URL = "http://mp.weixin.qq.com/cgi-bin/indexpage?t=wxm-index&lang=zh_CN";
    public final static String SINGLE_REFERER_URL="https://mp.weixin.qq.com/cgi-bin/singlemsgpage?t=wxm-singlechat&msgid=&source=&count=20&t=wxm-singlechat&lang=zh_CN";
    public final static String FANS_URL = "http://mp.weixin.qq.com/cgi-bin/contactmanagepage?t=wxm-friend&lang=zh_CN&pagesize=10&pageidx=0&type=0&groupid=0";
    public final static String LOGOUT_URL = "http://mp.weixin.qq.com/cgi-bin/logout?t=wxm-logout&lang=zh_CN";
    public final static String DOWNLOAD_URL = "http://mp.weixin.qq.com/cgi-bin/downloadfile?";
    public final static String VERIFY_CODE = "http://mp.weixin.qq.com/cgi-bin/verifycode?";
    public final static String POST_MSG = "https://mp.weixin.qq.com/cgi-bin/masssend?t=ajax-response";
    public final static String SINGLE_POST_MSG = "https://mp.weixin.qq.com/cgi-bin/singlesend?t=ajax-response&lang=zh_CN";
    public final static String VIEW_HEAD_IMG = "http://mp.weixin.qq.com/cgi-bin/viewheadimg";
    public final static String GET_IMG_DATA = "http://mp.weixin.qq.com/cgi-bin/getimgdata";
    public final static String GET_REGIONS = "http://mp.weixin.qq.com/cgi-bin/getregions";
    public final static String GET_MESSAGE = "http://mp.weixin.qq.com/cgi-bin/getmessage";
    public final static String OPER_ADVANCED_FUNC = "http://mp.weixin.qq.com/cgi-bin/operadvancedfunc";
    public final static String MASSSEND_PAGE = "http://mp.weixin.qq.com/cgi-bin/masssendpage";
    public final static String FILE_MANAGE_PAGE = "http://mp.weixin.qq.com/cgi-bin/filemanagepage";
    public final static String OPERATE_APPMSG = "https://mp.weixin.qq.com/cgi-bin/operate_appmsg";
    public final static String FMS_TRANSPORT = "http://mp.weixin.qq.com/cgi-bin/fmstransport";
    public final static String CONTACT_MANAGE_PAGE = "http://mp.weixin.qq.com/cgi-bin/contactmanagepage";
    public final static String OPER_SELF_MENU = "http://mp.weixin.qq.com/cgi-bin/operselfmenu";
    public final static String REPLY_RULE_PAGE = "http://mp.weixin.qq.com/cgi-bin/replyrulepage";
    public final static String SINGLE_MSG_PAGE = "http://mp.weixin.qq.com/cgi-bin/singlemsgpage";
    public final static String USER_INFO_PAGE = "http://mp.weixin.qq.com/cgi-bin/userinfopage";
    public final static String DEV_APPLY = "http://mp.weixin.qq.com/cgi-bin/devapply";
    public final static String UPLOAD_MATERIAL = "https://mp.weixin.qq.com/cgi-bin/uploadmaterial?cgi=uploadmaterial&type=2&token=&t=iframe-uploadfile&lang=zh_CN&formId=1";

    public final static String USER_AGENT_H = "User-Agent";
    public final static String REFERER_H = "Referer";
    public final static String USER_AGENT = "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.172 Safari/537.22";
    public final static String UTF_8 = "UTF-8";


    private HttpClient client = new HttpClient();

    private Cookie[] cookies;
    private String cookiestr;

    private String token;
    private int loginErrCode;
    private String loginErrMsg;
    private int msgSendCode;
    private String msgSendMsg;

    private String loginUser;
    private String loginPwd;
    public boolean isLogin = false;

    public WeCharClent(String user, String pwd) {
        this.loginUser = user;
        this.loginPwd = pwd;
    }

    public Cookie[] getCookies() {
        return cookies;
    }

    public void setCookies(Cookie[] cookies) {
        this.cookies = cookies;
    }

    public String getCookiestr() {
        return cookiestr;
    }

    public void setCookiestr(String cookiestr) {
        this.cookiestr = cookiestr;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getLoginErrCode() {
        return loginErrCode;
    }

    public void setLoginErrCode(int loginErrCode) {
        this.loginErrCode = loginErrCode;
    }

    public String getLoginErrMsg() {
        return loginErrMsg;
    }

    public void setLoginErrMsg(String loginErrMsg) {
        this.loginErrMsg = loginErrMsg;
    }

    public int getMsgSendCode() {
        return msgSendCode;
    }

    public void setMsgSendCode(int msgSendCode) {
        this.msgSendCode = msgSendCode;
    }

    public String getMsgSendMsg() {
        return msgSendMsg;
    }

    public void setMsgSendMsg(String msgSendMsg) {
        this.msgSendMsg = msgSendMsg;
    }

    public String getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }


    /**
     * 登录,登录失败会重复请求登录
     */
    public void login() {
        boolean bool = _login();
        if (!bool) {
            String info = "【登录失败】【错误代码：" + this.loginErrMsg + "】【账号："
                    + this.loginUser + "】正在尝试重新登录....";
            log.debug(info);
//            System.out.println(info);
            bool = _login();
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                bool = _login();
//            }

        }
//        System.out.println("登陆成功：");
    }

    /**
     * 发送登录信息,记录cookie，登录状态，token等信息
     *
     * @return
     */
    private boolean _login() {
        try {
            PostMethod post = new PostMethod(LOGIN_URL);
            post.setRequestHeader(USER_AGENT_H, USER_AGENT);
            NameValuePair[] params = new NameValuePair[]{
                    new NameValuePair("username", this.loginUser),
                    new NameValuePair("pwd", DigestUtils.md5Hex(this.loginPwd
                            .getBytes())), new NameValuePair("f", "json"),
                    new NameValuePair("imagecode", "")};
            post.setQueryString(params);
            int status = client.executeMethod(post);
            if (status == HttpStatus.SC_OK) {
                String ret = post.getResponseBodyAsString();
                LoginJsonBean retcode = JSON.parseObject(ret, LoginJsonBean.class);
                if (retcode.getRet() == 302 && retcode.getErrCode() == 0) {
                    this.cookies = client.getState().getCookies();
                    StringBuffer cookie = new StringBuffer();
                    for (Cookie c : client.getState().getCookies()) {
                        cookie.append(c.getName()).append("=")
                                .append(c.getValue()).append(";");
                    }
                    this.cookiestr = cookie.toString();
                    this.isLogin = true;
                    this.token = getToken(retcode.getErrMsg());
                    return true;
                }
                int errCode = retcode.getErrCode();
                this.loginErrCode = errCode;
                switch (errCode) {

                    case -1:
                        this.loginErrMsg = "系统错误";
                        return false;
                    case -2:
                        this.loginErrMsg = "帐号或密码错误";
                        return false;
                    case -3:
                        this.loginErrMsg = "密码错误";
                        return false;
                    case -4:
                        this.loginErrMsg = "不存在该帐户";
                        return false;
                    case -5:
                        this.loginErrMsg = "访问受限";
                        return false;
                    case -6:
                        this.loginErrMsg = "需要输入验证码";
                        return false;
                    case -7:
                        this.loginErrMsg = "此帐号已绑定私人微信号，不可用于公众平台登录";
                        return false;
                    case -8:
                        this.loginErrMsg = "邮箱已存在";
                        return false;
                    case -32:
                        this.loginErrMsg = "验证码输入错误";
                        return false;
                    case -200:
                        this.loginErrMsg = "因频繁提交虚假资料，该帐号被拒绝登录";
                        return false;
                    case -94:
                        this.loginErrMsg = "请使用邮箱登陆";
                        return false;
                    case 10:
                        this.loginErrMsg = "该公众会议号已经过期，无法再登录使用";
                        return false;
                    case 65201:
                    	System.out.println(65201);
                    case 65202:
                    	System.out.println(65202);
                        this.loginErrMsg = "成功登陆，正在跳转...";
                        return true;
                    case 0:
                    	System.out.println(0);
                        this.loginErrMsg = "成功登陆，正在跳转...";
                        return true;
                    default:
                        this.loginErrMsg = "未知的返回";
                        return false;
                }
            }
        } catch (Exception e) {
            String info = "【登录失败】【发生异常：" + e.getMessage() + "】";
//            System.err.println(info);
//            log.debug(info);
            log.info(info);
            return false;
        }finally{
        }
        return false;
    }

    /**
     * 从登录成功的信息中分离出token信息
     *
     * @param s
     * @return
     */
    private String getToken(String s) {
        try {
            if (StringUtils.isBlank(s))
                return null;
            String[] ss = StringUtils.split(s, "?");
            String[] params = null;
            if (ss.length == 2) {
                if (!StringUtils.isBlank(ss[1]))
                    params = StringUtils.split(ss[1], "&");
            } else if (ss.length == 1) {
                if (!StringUtils.isBlank(ss[0]) && ss[0].indexOf("&") != -1)
                    params = StringUtils.split(ss[0], "&");
            } else {
                return null;
            }
            for (String param : params) {
                if (StringUtils.isBlank(param))
                    continue;
                String[] p = StringUtils.split(param, "=");
                if (null != p && p.length == 2
                        && StringUtils.equalsIgnoreCase(p[0], "token"))
                    return p[1];

            }
        } catch (Exception e) {
            String info = "【解析Token失败】【发生异常：" + e.getMessage() + "】";
//            System.err.println(info);
//            log.debug(info);
            log.info(info);
            return null;
        }
        return null;
    }

    /**
     * 获取首页
     *
     * @throws org.apache.commons.httpclient.HttpException
     *
     * @throws java.io.IOException
     */
    public void index() throws HttpException, IOException {
        GetMethod get = new GetMethod(INDEX_URL);
        get.setRequestHeader(USER_AGENT_H, USER_AGENT);
        get.setRequestHeader("Cookie", this.cookiestr);
        int status = client.executeMethod(get);
        if (status == HttpStatus.SC_OK) {
            System.out.println(get.getResponseBodyAsString());
        }
    }

    /**
     * 登出操作
     *
     * @throws org.apache.commons.httpclient.HttpException
     *
     * @throws java.io.IOException
     */
    public void logout() throws HttpException, IOException {
        GetMethod get = new GetMethod(LOGOUT_URL);
        get.setRequestHeader(USER_AGENT_H, USER_AGENT);
        get.setRequestHeader("Cookie", this.cookiestr);
        int status = client.executeMethod(get);
        if (status == HttpStatus.SC_OK) {
//            System.err.println("-----------注销登录成功-----------");
        }
    }

    /**
     * 获取验证码
     *
     * @throws org.apache.commons.httpclient.HttpException
     *
     * @throws java.io.IOException
     */
    public InputStream code() throws HttpException, IOException {
        GetMethod get = new GetMethod(VERIFY_CODE);
        get.setRequestHeader(USER_AGENT_H, USER_AGENT);
        get.setRequestHeader("Cookie", this.cookiestr);
        NameValuePair[] params = new NameValuePair[]{
                new NameValuePair("username", this.loginUser),
                new NameValuePair("r", "1365318662649")};
        get.setQueryString(params);
        int status = client.executeMethod(get);
        if (status == HttpStatus.SC_OK) {
            return get.getResponseBodyAsStream();
        }
        return null;
    }

    /**
     * 获得粉丝数量,如果解析成功会返回粉丝数，如果解析失败则返回-1
     *
     * @return
     */
    public int getFansCount() {
        try {
            String paramStr = "?t=wxm-friend&token=" + this.token
                    + "&lang=zh_CN&pagesize=10&pageidx=0&type=0&groupid=0";
            if (!this.isLogin) {
                this._login();
            }
            if (this.isLogin) {
                GetMethod get = new GetMethod(CONTACT_MANAGE_PAGE + paramStr);
                get.setRequestHeader(REFERER_H, INDEX_URL);
                get.setRequestHeader("Cookie", this.cookiestr);
                int status = client.executeMethod(get);
                if (status == HttpStatus.SC_OK) {
                    return parseFansCount(get.getResponseBodyAsString());
                }
                return -1;
            }
        } catch (Exception e) {
            String info = "【获取粉丝数失败】【可能登录过期】";
//            System.err.println(info);
//            log.debug(info);
            log.info(info);
            return -1;
        }
        return -1;
    }

    /**
     * 从返回文本中提取粉丝数量
     *
     * @param text
     * @return
     */
    private int parseFansCount(String text) {
        try {
            StringBuffer json = new StringBuffer();
            final String start = "DATA.groupList =";
            for (int i = text.indexOf(start) + start.length(), len = text
                    .length(); i < len; i++) {
                char ci = text.charAt(i);
                if (ci == ';') {
                    break;
                }
                json.append(text.charAt(i));
            }
            String txt = json.toString().replaceAll("[*]1", "")
                    .replaceAll("defaultGroupName\\[0\\] \\|\\|", "")
                    .replaceAll("defaultGroupName\\[1\\] \\|\\|", "")
                    .replaceAll("defaultGroupName\\[2\\] \\|\\|", "")
                    .replaceAll("defaultGroupName\\[100\\] \\|\\|", "");
            List<FansCountBean> fans = JSON.parseArray(txt, FansCountBean.class);
            if (null != fans && !fans.isEmpty())
                for (FansCountBean fan : fans)
                    if (fan.getId() == 0)
                        return fan.getNum();
        } catch (Exception e) {
            String info = "【解析粉丝数失败】 " + "\t\n【文本：】\t\n" + text + "\t\n"
                    + "【发生异常：" + e.getMessage() + "】";
//            System.err.println(info);
//            log.debug(info);
            log.info(info);
            return -1;
        }
        return -1;
    }


    /**
     *
     * <strong>消息</strong>
     * <p>
     * 返回码说明<br>
     * 0：发送成功<br>
     * 64004:今天的数量已到，无法<br>
     * -20000:请求被禁止，请仔细检查token是否合法<br>
     * </p>
     * <p>
     * 可通过msgSendCode取得发送状态码
     * </p>
     *
     * @by liaokai
     *
     */
    /**
     * @param form
     * @param type
     * @return
     */
    public boolean msgSend(String post_msg_url,String referer,MsgBean form, MsgTypeBean type) {
    	//TODO -----------------------------
        try {
            if (!this.isLogin) {
                this._login();
            }
            if (this.isLogin) {
                form.setToken(this.token);
                PostMethod post = new PostMethod(post_msg_url);
                post.setRequestHeader(USER_AGENT_H, USER_AGENT);
                post.setRequestHeader(REFERER_H, referer);
                post.setRequestHeader("Cookie", this.cookiestr);
                Part[] parts = null;
                switch (type) {
                    case TEXT:
                        parts = new Part[]{
                        		new StringPart("content", form.getContent(),
                        				 "UTF-8"),
                        				 new StringPart("type", form.getType()),
                        				 new StringPart("error", form.getError()),
                        				 new StringPart("needcomment", form.getNeedcomment()),
                        				 new StringPart("groupid", form.getGroupid()),
                        				 new StringPart("sex", form.getSex()),
                        				 new StringPart("country", form.getCountry()),
                        				 new StringPart("province", form.getProvince()),
                        				 new StringPart("city", form.getCity()),
                        				 new StringPart("token", form.getToken()),
                        				 new StringPart("ajax", form.getAjax()),
                        				 new StringPart("t", "ajax-response")
                                };
//                        System.out.println("========token========"+form.getToken());
                        break;
                    case IMAGE_TEXT:
                        ImgTextMsgListBean list = this.getImgTextMsgList();
                        List<ImgTextMsgListBean.ImgTextMsg> imgTextMsgs = list.getList();
                        if (null != imgTextMsgs && !imgTextMsgs.isEmpty()) {
                            ImgTextMsgListBean.ImgTextMsg imgTextMsg = imgTextMsgs.get(0);
                            if (null != imgTextMsg) {
                                form.setAppmsgid(imgTextMsg.getAppId());
                                form.setFid(imgTextMsg.getAppId());
                            }
                        }
                        if (StringUtils.isBlank(form.getAppmsgid()) || StringUtils.isBlank(form.getFid())) {
                            this.msgSendMsg = "参数错误:appmsgid为空";
                            return false;
                        }
                        parts = new Part[]{
                                new StringPart("fid", form.getFid(),
                                        "UTF-8"),
                                new StringPart("appmsgid", form.getAppmsgid(),
                                        "UTF-8"),
                                new StringPart("type", type.getType() + ""),
                                new StringPart("error", form.getError()),
                                new StringPart("needcomment", form.getNeedcomment()),
                                new StringPart("groupid", form.getGroupid()),
                                new StringPart("sex", form.getSex()),
                                new StringPart("country", form.getCountry()),
                                new StringPart("province", form.getProvince()),
                                new StringPart("city", form.getCity()),
                                new StringPart("token", form.getToken()),
                                new StringPart("ajax", form.getAjax())
//                                new StringPart("t", form.getT())
                                };
                        break;
                    case IMAGE:
                    case VIDEO:
                    case VOICE:
                        parts = new Part[]{
                                new StringPart("content", form.getContent(),
                                        "UTF-8"),
                                new StringPart("type", type.getType()+""),
                                new StringPart("error", form.getError()),
                                new StringPart("token", form.getToken()),
                                new StringPart("ajax", form.getAjax()),
                                new StringPart("t", form.getT())
                          };

                        break;
                    case SINGLE_TEXT:
                        parts = new Part[]{
                                new StringPart("content", form.getContent(),"UTF-8"),
                                new StringPart("type", type.getType() + ""),
                                new StringPart("error", form.getError()),
                                new StringPart("tofakeid", form.getFakeid()),
                                new StringPart("token", form.getToken()),
                                new StringPart("ajax", form.getAjax())
                        };
                        break;
                    case SINGLE_IMAGE_TEXT:
                        list = this.getImgTextMsgList();
                        imgTextMsgs = list.getList();
                        if (null != imgTextMsgs && !imgTextMsgs.isEmpty()) {
                            ImgTextMsgListBean.ImgTextMsg imgTextMsg = imgTextMsgs.get(0);
                            if (null != imgTextMsg) {
                                form.setAppmsgid(imgTextMsg.getAppId());
                                form.setFid(imgTextMsg.getAppId());
                            }
                        }
                        if (StringUtils.isBlank(form.getAppmsgid()) || StringUtils.isBlank(form.getFid())) {
                            this.msgSendMsg = "参数错误:appmsgid为空";
                            return false;
                        }
                        parts = new Part[]{
                                new StringPart("fid", form.getFid(),"UTF-8"),
                                new StringPart("appmsgid", form.getAppmsgid(),"UTF-8"),
                                new StringPart("type", type.getType() + ""),
                                new StringPart("error", form.getError()),
                                new StringPart("fromfakeid", form.getFakeid()),
                                new StringPart("tofakeid", form.getFakeid()),
                                new StringPart("token", form.getToken()),
                                new StringPart("ajax", form.getAjax()),
                                new StringPart("t", form.getT())};
                        break;
                    default:
                        parts = new Part[]{
                                new StringPart("content", form.getContent(),"UTF-8"),
                                new StringPart("type",type.getType()+""),
                                new StringPart("fromfakeid", form.getFakeid()),
                                new StringPart("tofakeid", form.getFakeid()),
                                new StringPart("error", form.getError()),
                                new StringPart("token", form.getToken()),
                                new StringPart("ajax", form.getAjax()),
                                new StringPart("t", form.getT())};

                        break;
                }
                RequestEntity entity = new MultipartRequestEntity(parts,
                        post.getParams());
                post.setRequestEntity(entity);
                int status;
                status = client.executeMethod(post);
                if (status == HttpStatus.SC_OK) {
                    String text = post.getResponseBodyAsString();
                    try {
                        MsgJsonBean ret = JSON.parseObject(text, MsgJsonBean.class);
                        this.msgSendCode = ret.getRet();
                        switch (this.msgSendCode) {
                            case 0:
                                this.msgSendMsg = "发送成功";
                                return true;
                            case -2:
                                this.msgSendMsg = "参数错误，请仔细检查";
                                return false;
                            case 64004:
                                this.msgSendMsg = "今天的数量已到，无法群发";
                                return false;
                            case -20000:
                                this.msgSendMsg = "请求被禁止，请仔细检查token是否合法";
                                return false;
                            default:
                                this.msgSendMsg = "未知错误!";
                                return false;
                        }
                    } catch (Exception e) {
                        String info = "【信息失败】【解析json错误11111111111】" + e.getMessage()
                                + "\n\t【文本:】\n\t" ;
//                        System.err.println(info);
//                        log.debug(info);
                        log.error(info);
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            String info = "【信息失败】" + e.getMessage();
//            System.err.println(info);
//            log.debug(info);
            log.error(info);
            return false;
        }
        return false;
    }

    private String updateImgErr;

    public String getUpdateImgErr() {
        return updateImgErr;
    }

    public void setUpdateImgErr(String updateImgErr) {
        this.updateImgErr = updateImgErr;
    }

    //    public final static Pattern IMG_SUCCESS_REG = Pattern.compile("\.top\.W\.upload\.suc(\"")
    public String updateImg(ImgFileBean form) {
        try {
            if (!this.isLogin)
                this.isLogin();
            if (this.isLogin) {
                form.setToken(this.getToken());
                PostMethod post = new PostMethod(UPLOAD_MATERIAL);
                post.setRequestHeader(USER_AGENT_H, USER_AGENT);
                post.setRequestHeader(REFERER_H, INDEX_URL);
                post.setRequestHeader("Connection", "Keep-Alive");
                post.setRequestHeader("Cookie", this.cookiestr);
                post.setRequestHeader("Cache-Control", "no-cache");

                /**
                 *   private String cgi = "uploadmaterial";
                 private String type = "2";
                 private String token = "";
                 private String t = "iframe-uploadfile";
                 private String lang = "zh_CN";
                 private String formId = "1";
                 */
                String fileName = form.getUploadfile().getName();
                String ext = StringUtils.substring(fileName, fileName.indexOf("."), fileName.length());
                FilePart file = new FilePart("uploadfile", form.getUploadfile(), "image/" + ext, "UTF-8");
//                System.out.println(form.getToken());
                Part[] parts = new Part[]{
                        new StringPart("cgi", form.getCgi()),
                        new StringPart("type", form.getType()),
                        new StringPart("token", form.getToken()),
                        new StringPart("t", form.getT()),
                        new StringPart("lang", form.getLang()),
                        new StringPart("formId", form.getFormId()),
                        file};
                MultipartRequestEntity entity = new MultipartRequestEntity(parts, post.getParams());
                post.setRequestEntity(entity);
                int status = client.executeMethod(post);
                if (status == HttpStatus.SC_OK) {
                    String text = post.getResponseBodyAsString();
                    return parseUploadImgText(text);
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String parseUploadImgText(String text) {
        try {
            if (StringUtils.isBlank(text))
                return null;
            String sub = null;
            int type = 0;
            if (text.indexOf("top.W.upload.suc(") != -1) {
                sub = "top.W.upload.suc(";
                type = 1;
            } else if (text.indexOf("top.W.upload.err(") != -1) {
                sub = "top.W.upload.err(";
                type = 2;
            }
            StringBuffer ret = new StringBuffer();
            for (int i = text.indexOf(sub) + sub.length(), len = text.length(); i < len; i++) {
                char c = text.charAt(i);
                if (c == ')')
                    break;
                ret.append(c);
            }
            String result = ret.toString().replaceAll("['|\"]", "");
            String[] s = null;
            switch (type) {
                case 1:
                    s = StringUtils.split(result, ",");
                    if (null != s && s.length == 4) {
                        this.updateImgErr = StringUtils.trim(s[0]);
                        return StringUtils.trim(s[3]);
                    }
                    this.updateImgErr = "未知错误";
                    return null;
                case 2:
                    s = StringUtils.split(result, ",");
                    if (null != s && s.length == 3) {
                        this.updateImgErr = StringUtils.trim(s[0]);
                        return null;
                    }
                    this.updateImgErr = "未知错误";
                    return null;
                default:
                    this.updateImgErr = "未知错误";
                    return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    private String imgTextSendErr = "";
    private int imgTextSendCode;

    public String getImgTextSendErr() {
        return imgTextSendErr;
    }

    public void setImgTextSendErr(String imgTextSendErr) {
        this.imgTextSendErr = imgTextSendErr;
    }

    public int getImgTextSendCode() {
        return imgTextSendCode;
    }

    public void setImgTextSendCode(int imgTextSendCode) {
        this.imgTextSendCode = imgTextSendCode;
    }


    public boolean saveImgText(ImgTextBean form) {
        try {
            if (!this.isLogin)
                this.isLogin();
            if (this.isLogin) {
                form.setToken(this.getToken());
                PostMethod post = new PostMethod(OPERATE_APPMSG);
                post.setRequestHeader(USER_AGENT_H, USER_AGENT);
                post.setRequestHeader(REFERER_H, INDEX_URL);

                post.setRequestHeader("Cookie", this.cookiestr);

                /**
                 * private String error = "false";
                 private String count;
                 private String AppMsgId = "";
                 private String token;
                 private String ajax = "1";
                 private String lang = "zh_CN";
                 private String t = "ajax-response";
                 private String sub = "create";
                 private List<Piece> pieces;
                 */

//                System.out.println(form.getToken());
                List<Part> params = new ArrayList<Part>();
                params.add(new StringPart("error", form.getError()));
                params.add(new StringPart("count", form.getCount()));
                params.add(new StringPart("AppMsgId", form.getAppMsgId()));
                params.add(new StringPart("token", form.getToken()));
                params.add(new StringPart("ajax", form.getAjax()));
                params.add(new StringPart("lang", form.getLang()));
                params.add(new StringPart("t", form.getT()));
                params.add(new StringPart("sub", form.getSub()));

                int i = 0;
                for (ImgTextBean.Piece piece : form.getPieces()) {
                    if (null != piece.getImg()) {
                        String fileid = this.updateImg(piece.getImg());
                        if (StringUtils.isBlank(fileid))
                            continue;
                        piece.setFileid(fileid);
                        params.add(new StringPart("title" + i, piece.getTitle(), UTF_8));
                        params.add(new StringPart("digest" + i, piece.getDigest(), UTF_8));
                        params.add(new StringPart("content" + i, piece.getContent(), UTF_8));
                        params.add(new StringPart("fileid" + i, piece.getFileid(), UTF_8));
                        i++;
                    }
                }
                Part[] parts = new Part[params.size()];
                MultipartRequestEntity entity = new MultipartRequestEntity(params.toArray(parts), post.getParams());
                post.setRequestEntity(entity);
                int status = client.executeMethod(post);
                if (status == HttpStatus.SC_OK) {
                    String text = post.getResponseBodyAsString();
                    try {
                        MsgJsonBean ret = JSON.parseObject(text, MsgJsonBean.class);
                        this.imgTextSendCode = ret.getRet();
                        System.out.println("===text=="+text);
                        switch (this.msgSendCode) {
                            case 0:
                                this.imgTextSendErr = "发送成功";
                                return true;
                            case -2:
                                this.imgTextSendErr = "参数错误，请仔细检查";
                                return false;
                            case 64004:
                                this.imgTextSendErr = "今天的数量已到，无法";
                                return false;
                            case -20000:
                                this.imgTextSendErr = "请求被禁止，请仔细检查token是否合法";
                                return false;
                            default:
                                this.imgTextSendErr = "未知错误!";
                                return false;
                        }
                    } catch (Exception e) {
                        String info = "【发信息失败】【解析json错误】" + e.getMessage()
                                + "\n\t【文本:】\n\t" + text;
                        System.err.println(info);
                        log.debug(info);
                        log.info(info);
                        return false;
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public ImgTextMsgListBean getImgTextMsgList() {
        try {
            if (!this.isLogin)
                this.isLogin();
            if (this.isLogin) {

                GetMethod post = new GetMethod(OPERATE_APPMSG);
                post.setRequestHeader(USER_AGENT_H, USER_AGENT);
                post.setRequestHeader(REFERER_H, INDEX_URL);
                post.setRequestHeader("Connection", "Keep-Alive");
                post.setRequestHeader("Cookie", this.cookiestr);
                post.setRequestHeader("Cache-Control", "no-cache");
                /**
                 * sub=list&type=10&subtype=3&t=wxm-appmsgs-list-new&pagesize=10&pageidx=0&token=1004476860&lang=zh_CN
                 */
                NameValuePair[] params = new NameValuePair[]{
                        new NameValuePair("sub", "list"),
                        new NameValuePair("type", "10"),
                        new NameValuePair("subtype", "3"),
                        new NameValuePair("t", "wxm-appmsgs-list-new"),
                        new NameValuePair("pagesize", "10"),
                        new NameValuePair("pageidx", "0"),
                        new NameValuePair("token", this.getToken()),
                        new NameValuePair("lang", "zh_CN")
                };
                post.setQueryString(params);

                int status = client.executeMethod(post);
                if (status == HttpStatus.SC_OK) {
                    String text = post.getResponseBodyAsString();
                    Document doc = Jsoup.parse(text);
                    Elements eles = doc.select("script[id=json-msglist]");
                    for (Element e : eles) {
                        String html = e.html();
                        ImgTextMsgListBean ret = JSON.parseObject(html, ImgTextMsgListBean.class);
                        return ret;
                    }

                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 页面跳转
     *
     * @param url
     */

    public void redirect(String url) {
        if (url.indexOf("https://") == -1)
            url = HOST + url;
        try {
            if (this.isLogin) {
                GetMethod get = new GetMethod(url);
                get.setRequestHeader(USER_AGENT_H, USER_AGENT);
                get.setRequestHeader(REFERER_H, INDEX_URL);
                get.setRequestHeader("Cookie", this.cookiestr);
                int status = client.executeMethod(get);
                if (status == HttpStatus.SC_OK) {
                    System.err.println("正在跳转.....");
                    System.out.println(get.getResponseBodyAsString());
                }
            }
        } catch (Exception e) {
        }
    }
    /**
	 * 获取最近反馈信息的用户
	 * 
	 * @param html
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> getAllRecentUser(String html)
			throws Exception {
		NodeFilter filter = HtmlUtil.geNodeFilter2("script", "id",
				"json-msgList");
		Parser parser = new Parser();
		parser.setInputHTML(html);

		String result = HtmlUtil.getFilterHtmlInfo(filter, parser);
		JSONArray array = JSONArray.fromObject(result);
		List<Map<String, String>> users = new ArrayList<Map<String, String>>();
		if (array != null)
			for (int i = 0; i < array.size(); i++) {
				JSONObject json = JSONObject.fromObject(array.get(i));
				Map<String, String> params = new HashMap<String, String>();
				params.put("fakeId", json.get("fakeId") + ""); // fakeId
				params.put("nickName", json.get("nickName") + ""); // 昵称
				params.put("remarkName", json.get("remarkName") + ""); // 备注名称
				params.put("dateTime", json.get("dateTime") + ""); // 时间戳
				params.put("content", json.get("content") + ""); // 内容
				users.add(params);
			}
		return users;
	}

	/**
	 * 获取所有用户
	 * 
	 * @param html
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> getAllUser(String html)
			throws Exception {
		NodeFilter filter = HtmlUtil.geNodeFilter2("script", "id",
				"json-friendList");
		Parser parser = new Parser();
		parser.setInputHTML(html);
		String result = HtmlUtil.getFilterHtmlInfo(filter, parser);
		// System.out.println("===result===" + result);
		JSONArray array = JSONArray.fromObject(result);
		List<Map<String, String>> users = new ArrayList<Map<String, String>>();
		if (array != null)
			for (int i = 0; i < array.size(); i++) {
				JSONObject json = JSONObject.fromObject(array.get(i));
				Map<String, String> params = new HashMap<String, String>();
				params.put("fakeId", json.get("fakeId") + ""); // fakeId
				params.put("nickName", json.get("nickName") + ""); // 昵称
				params.put("remarkName", json.get("remarkName") + ""); // 备注名称
				params.put("dateTime", json.get("dateTime") + ""); // 时间戳
				users.add(params);
			}
		return users;
	}
	/**
	 * 获取微信后台最近的一条信息
	 * 
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public List<Map<String, String>> getTopMessage() throws Exception {
		GetMethod post = new GetMethod("https://mp.weixin.qq.com/cgi-bin/getmessage?t=wxm-message&token="+this.token+"&lang=zh_CN&count=50000");
		// 设置响应头信息
		 post.setRequestHeader(USER_AGENT_H, USER_AGENT);
         post.setRequestHeader(REFERER_H, INDEX_URL);
         post.setRequestHeader("Connection", "Keep-Alive");
         post.setRequestHeader("Cookie", this.cookiestr);
         post.setRequestHeader("Cache-Control", "no-cache");
         
        if (client.executeMethod(post) == HttpStatus.SC_OK) {
            String html = post.getResponseBodyAsString();
//            System.out.println(html);
            return getAllRecentUser(html);
        }		
		return new ArrayList<Map<String, String>>();
	}
	 /** 获取微信后台最近的一条信息
	 * 
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public List<Map<String, String>> getAllUser() throws Exception {
		List<Map<String, String>> users=new ArrayList<Map<String, String>>();
		GetMethod post = new GetMethod("https://mp.weixin.qq.com/cgi-bin/contactmanagepage?t=ajax-getcontactinfo&lang=zh_CN&token=" + this.token);
		// 设置响应头信息
		 post.setRequestHeader(USER_AGENT_H, USER_AGENT);
         post.setRequestHeader(REFERER_H, INDEX_URL);
         post.setRequestHeader("Connection", "Keep-Alive");
         post.setRequestHeader("Cookie", this.cookiestr);
         post.setRequestHeader("Cache-Control", "no-cache");
         
        if (client.executeMethod(post) == HttpStatus.SC_OK) {
        	
            String html = post.getResponseBodyAsString();
//            System.out.println("==html1=="+html);
            JSONObject json = JSONObject.fromObject(html);
           
			JSONArray array = JSONArray.fromObject(json.get("Groups"));
			if (array != null) {
				for (int i = 0; i < array.size(); i++) {
					JSONObject group = JSONObject.fromObject(array.get(i));
					String	url = "https://mp.weixin.qq.com/cgi-bin/contactmanagepage?t=wxm-friend&token="
							+ this.token
							+ "&lang=zh_CN&pagesize=10000&pageidx=0&type=0&groupid="
							+ group.getString("GroupId");
					post = new GetMethod(url);
					 post.setRequestHeader(USER_AGENT_H, USER_AGENT);
			         post.setRequestHeader(REFERER_H, INDEX_URL);
			         post.setRequestHeader("Connection", "Keep-Alive");
			         post.setRequestHeader("Cookie", this.cookiestr);
			         post.setRequestHeader("Cache-Control", "no-cache");
			        if (client.executeMethod(post) == HttpStatus.SC_OK) {
			        	html = post.getResponseBodyAsString();
			        	// add
						users.addAll(this.getAllUser(html));
			        }
				}
			}
        }		
		return users;
	}
    /**
     * 使用方法:<br>
     * new wechar()对象，先登录再取粉丝数或者。<br>
     * 需要传入一个MsgForm参数： 默认发送文本消息，发送给中国区<br>
     * 所以不需要再设置其他参数，调用setContent将需要发送的内容填充就OK<br>
     * 内容中的超链接可以直接发送不用使用<a>标签
     *
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
    	//TODO test

        String LOGIN_USER = "iTravels@962020.com";
        String LOGIN_PWD = "its962020";
        WeCharClent wx = new WeCharClent(LOGIN_USER, LOGIN_PWD);
        wx.login();
        wx.getCookiestr();
        
//		 //获取最近的实时信息
//		 List<Map<String, String>> user_msgs = wx.getTopMessage();
//		 if(user_msgs!=null){
//			 for(Map<String, String> user_msg:user_msgs){
//				 String fackId = user_msg.get("fakeId");
//				 System.out.println("==dateTime===="+DateUtil.getDateTimeByMillisecond(user_msg.get("dateTime")));//
//				 System.out.println("==fackId======"+fackId);//
//				 System.out.println("==nickName===="+user_msg.get("nickName")); // 昵称
//				 System.out.println("==remarkName=="+user_msg.get("remarkName")); // 备注名称
//				 System.out.println("==content====="+user_msg.get("content")); // 内容
//				 System.out.println("----------------------------------");
//			 }
//		 }
//		// 粉丝集合
//		List<Map<String, String>> users = wx.getAllUser();
//		System.out.println("users info :");
//		if (users != null) {
//			for (Map<String, String> user : users) {
//				System.out.println("fakeId:" + user.get("fakeId"));
//				System.out.println("nickName:" + user.get("nickName"));
//				System.out.println("dateTime:" + user.get("dateTime"));
//				System.out.println("---------------------------------");
////				// 下载头像
////				test.DownImage(test,map.get("fakeId"), map.get("fakeId"));
//			}
//		}
        
        //发送图文信息
        File file = new File("D:\\My Pictures\\http_imgloadCATFG6CB.jpg");
        ImgFileBean img = new ImgFileBean();
        img.setUploadfile(file);
        ImgTextBean form = new ImgTextBean();
        List<ImgTextBean.Piece> pieces = new ArrayList<ImgTextBean.Piece>();
        //--------------1----------------------------
        ImgTextBean.Piece piece1 = new ImgTextBean.Piece();
        piece1.setContent("<p style=\"font-family:'microsoft yahei', helvetica, verdana, arial, tahoma;font-size:14px;background-color:#FAFAFA;\">	接口权限表：</p><table class=\"listTable outerTable\" style=\"margin:8px 0px 0px;color:#000000;border:1px solid #C8C7C7;width:857px;text-align:center;font-family:'microsoft yahei', helvetica, verdana, arial, tahoma;font-size:14px;background-color:#FAFAFA;\">	<tbody>		<tr>			<th class=\"type \" style=\"background-color:#F1F1F1;\">				接口类别			</th>			<th class=\"desc\" style=\"background-color:#F1F1F1;text-align:left;\">				接口描述			</th>			<th class=\"benefit\" style=\"background-color:#F1F1F1;\">				权限			</th>		</tr>	</tbody>	<tbody>		<tr>			<td class=\"type\" rowspan=\"4\">				<a target=\"_blank\" href=\"http://mp.weixin.qq.com/wiki/index.php?title=%E6%B6%88%E6%81%AF%E6%8E%A5%E5%8F%A3%E6%8C%87%E5%8D%97#.E6.B6.88.E6.81.AF.E6.8E.A8.E9.80.81\">接收用户消息</a>			</td>			<td class=\"desc\" style=\"text-align:left;\">				接收文本类消息			</td>			<td class=\"benefit\">				<strong class=\"hook\">&nbsp;</strong>			</td>		</tr>		<tr>			<td class=\"desc\" style=\"text-align:left;\">				接收地理位置类消息			</td>			<td class=\"benefit\">				<strong class=\"hook\">&nbsp;</strong>			</td>		</tr>		<tr>			<td class=\"desc\" style=\"text-align:left;\">				接收图片类消息			</td>			<td class=\"benefit\">				<strong class=\"hook\">&nbsp;</strong>			</td>		</tr>		<tr>			<td class=\"desc\" style=\"text-align:left;\">				接收链接类消息			</td>			<td class=\"benefit\">				<strong class=\"hook\">&nbsp;</strong>			</td>		</tr>		<tr>			<td class=\"type\" rowspan=\"3\">				<a target=\"_blank\" href=\"http://mp.weixin.qq.com/wiki/index.php?title=%E6%B6%88%E6%81%AF%E6%8E%A5%E5%8F%A3%E6%8C%87%E5%8D%97#.E6.B6.88.E6.81.AF.E5.9B.9E.E5.A4.8D\">向用户回复消息</a>			</td>			<td class=\"desc\" style=\"text-align:left;\">				回复文本消息			</td>			<td class=\"benefit\">				<strong class=\"hook\">&nbsp;</strong>			</td>		</tr>		<tr>			<td class=\"desc\" style=\"text-align:left;\">				回复图文类消息			</td>			<td class=\"benefit\">				<strong class=\"hook\">&nbsp;</strong>			</td>		</tr>		<tr>			<td class=\"desc\" style=\"text-align:left;\">				回复音乐类消息			</td>			<td class=\"benefit\">				<strong class=\"hook\">&nbsp;</strong>			</td>		</tr>		<tr>			<td class=\"type\" rowspan=\"2\">				<a target=\"_blank\" href=\"http://mp.weixin.qq.com/wiki/index.php?title=%E6%B6%88%E6%81%AF%E6%8E%A5%E5%8F%A3%E6%8C%87%E5%8D%97#.E4.BA.8B.E4.BB.B6.E6.8E.A8.E9.80.81\">事件推送</a>			</td>			<td class=\"desc\" style=\"text-align:left;\">				用户关注事件推送			</td>			<td class=\"benefit\">				<strong class=\"hook\">&nbsp;</strong>			</td>		</tr>		<tr>			<td class=\"desc\" style=\"text-align:left;\">				用户取消关注事件推送			</td>			<td class=\"benefit\">				<strong class=\"hook\">&nbsp;</strong>			</td>		</tr>		<tr>			<td class=\"type\">				<a target=\"_blank\" href=\"http://mp.weixin.qq.com/wiki/index.php?title=%E8%87%AA%E5%AE%9A%E4%B9%89%E8%8F%9C%E5%8D%95%E6%8E%A5%E5%8F%A3\">会话界面自定义菜单</a>			</td>			<td class=\"desc\" style=\"text-align:left;\">				设置自定义菜单（内测）<strong class=\"badage_new\"></strong>			</td>			<td class=\"benefit\">				<span class=\"desc\">暂停申请内测资格</span>			</td>		</tr>	</tbody></table><br class=\"Apple-interchange-newline\" />");
        piece1.setDigest("暂停申请内测资格");
        piece1.setImg(img);
        piece1.setTitle("郑重讲个故事");
        pieces.add(piece1);
//        //--------------2----------------------------
//        file = new File("D:\\My Pictures\\http_imgload.jpg");
//        img = new ImgFileBean();
//        img.setUploadfile(file);
//        ImgTextBean.Piece piece2 = new ImgTextBean.Piece();
//        piece2.setContent("test 2 detail info");
//        piece2.setDigest("test 2 brief info");
//        piece2.setImg(img);
//        piece2.setTitle("test 2 title");
//        pieces.add(piece2);
//        //--------------3----------------------------
//        file = new File("D:\\My Pictures\\http_imgloadCABAB5TO.jpg");
//        img = new ImgFileBean();
//        img.setUploadfile(file);
//        ImgTextBean.Piece piece3 = new ImgTextBean.Piece();
//        piece3.setContent("test 3 detail info");
//        piece3.setDigest("test 3 brief info");
//        piece3.setImg(img);
//        piece3.setTitle("test 3 title");
//        pieces.add(piece3);
//
        form.setPieces(pieces);
        wx.saveImgText(form);
        //
        MsgBean msg = new MsgBean();
        //token
        msg.setToken(wx.getToken());
        //
        msg.setFakeid("413014280");
//      //文章id
//        msg.setAppmsgid("10000007");
//      //
//        msg.setGroupid("0");
//        //文章正文
//        msg.setContent("test info 单挑文体!");
//        
//        msg.setT("wxm-singlechat");
        //群发
//      wx.msgSend(POST_MSG,INDEX_URL,msg, MsgType.IMAGE_TEXT);
        //单发
        wx.msgSend(SINGLE_POST_MSG,"https://mp.weixin.qq.com/cgi-bin/singlemsgpage?"
						+ "token="
						+ wx.token
						+ "&fromfakeid="
						+ msg.getFakeid()
						+ "&msgid=&source=&count=20&t=wxm-singlechat&lang=zh_CN",msg, MsgTypeBean.SINGLE_IMAGE_TEXT);

        System.out.println(wx.getMsgSendMsg());
        System.out.println(wx.getFansCount());
        System.out.println(wx.getImgTextSendErr());


    }
}
