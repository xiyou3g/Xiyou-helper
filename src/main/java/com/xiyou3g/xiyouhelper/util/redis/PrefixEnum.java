package com.xiyou3g.xiyouhelper.util.redis;

/**
 * @Author: zeng
 * @Date: 2018/7/20 19:41
 */
public enum PrefixEnum {

    XYEDU(0, "XYEDU"),
    Book(1, "BOOK"),
    CET(2,"CET");

    private int code;
    private String desc;

    PrefixEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
