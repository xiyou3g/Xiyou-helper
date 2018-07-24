package com.xiyou3g.xiyouhelper.model;

/**
 * @author mengchen
 */
public class User {
    /**
     * 用户id，即学号
     */
    private String sid;

    /**
     * 姓名
     */
    private String name;


    /**
     * 性别
     */
    private Integer gender;

    /**
     * 学院
     */
    private String college;

    /**
     * 专业
     */
    private String major;

    /**
     * 班级
     */
    private String adclass;

    /**
     * 所在级Level
     */
    private String level;

    /**
     * 学历
     */
    private String education;

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
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

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getAdclass() {
        return adclass;
    }

    public void setAdclass(String adclass) {
        this.adclass = adclass;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "User{" +
                "sid='" + sid + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", college='" + college + '\'' +
                ", major='" + major + '\'' +
                ", adclass='" + adclass + '\'' +
                ", level='" + level + '\'' +
                ", education='" + education + '\'' +
                '}';
    }
}