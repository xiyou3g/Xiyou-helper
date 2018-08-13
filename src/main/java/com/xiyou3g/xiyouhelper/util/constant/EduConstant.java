package com.xiyou3g.xiyouhelper.util.constant;

import static com.xiyou3g.xiyouhelper.util.constant.CommonConstant.XYE_BASEURL;

/**
 * 18-7-20 下午7:50
 * @author mengchen
 */
public class EduConstant {
    public static final String VALIDATE_URL = XYE_BASEURL +  "CheckCode.aspx";
    public static final String LOGIN_URL = XYE_BASEURL + "default2.aspx";
    public static final String LOGIN_HIDDEN_NAME = "__VIEWSTATE";
    public static final String LOGIN_HIDDEN_VALUE = "dDwxNTMxMDk5Mzc0Ozs+lYSKnsl/mKGQ7CKkWFJpv0btUa8=";
    public static final String LOGIN_USERNAME = "txtUserName";
    public static final String LOGIN_OTHER = "TextBox1";
    public static final String LOGIN_PASSWORD = "TextBox2";
    public static final String LOGIN_TYPE = "RadioButtonList1";
    public static final String LOGIN_TYPE_STUDENT = "学生";
    public static final String LOGIN_HIDDEN_NAME2 = "Button1";
    public static final String LOGIN_HIDDEN_VALUE2 = "";
    public static final String LOGIN_VALIDATE_CODE = "txtSecretCode";
    public static final String LOGIN_PASS_ERROR = "密码错误";
    public static final String LOGIN_USER_NOTFOUND = "用户名不存在或未按照要求参加教学活动";
    public static final String LOGIN_VALIDATE_CODE_ERROR = "验证码不正确";

    public static final String USER_MESSAGE_URL = XYE_BASEURL + "xsgrxx.aspx?xh=%s&xm=%s&gnmkdm=N121501";

    public static final String TRAINPLAN_URL = XYE_BASEURL + "pyjh.aspx?xh=%s&xm=%s&gnmkdm=N121607";

    public static final String EDU_REFERER = "http://222.24.62.120/xskbcx.aspx";
    public static final String REFERER = "Referer";
}
