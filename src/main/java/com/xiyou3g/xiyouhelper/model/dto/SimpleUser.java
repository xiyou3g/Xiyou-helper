package com.xiyou3g.xiyouhelper.model.dto;

/**
 * 18-7-23 下午8:39
 * @author mengchen
 */
public class SimpleUser {
    private String sid;
    private String name;


    public SimpleUser(String sid, String name) {
        this.sid = sid;
        this.name = name;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
