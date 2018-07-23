package com.xiyou3g.xiyouhelper.model;

/**
 * @author du
 */

public class Cetscore {
    //姓名
    private String name;
    //学校
    private String school;
    //考试级别
    private String testlevel;
    //笔试准考证号
    private String bszkzh;
    //总分
    private String totalscore;
    //听力
    private String hearing;
    //阅读
    private String reading;
    //写作和翻译
    private String wat;
    //口试准考证号
    private String kszkzh;
    //口语等级
    private String kslevel;

    @Override
    public String toString() {
        return "Cetscore{" +
                "name='" + name + '\'' +
                ", school='" + school + '\'' +
                ", testlevel='" + testlevel + '\'' +
                ", bszkzh='" + bszkzh + '\'' +
                ", totalscore='" + totalscore + '\'' +
                ", hearing='" + hearing + '\'' +
                ", reading='" + reading + '\'' +
                ", wat='" + wat + '\'' +
                ", kszkzh='" + kszkzh + '\'' +
                ", kslevel='" + kslevel + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getTestlevel() {
        return testlevel;
    }

    public void setTestlevel(String testlevel) {
        this.testlevel = testlevel;
    }

    public String getBszkzh() {
        return bszkzh;
    }

    public void setBszkzh(String bszkzh) {
        this.bszkzh = bszkzh;
    }

    public String getTotalscore() {
        return totalscore;
    }

    public void setTotalscore(String totalscore) {
        this.totalscore = totalscore;
    }

    public String getHearing() {
        return hearing;
    }

    public void setHearing(String hearing) {
        this.hearing = hearing;
    }

    public String getReading() {
        return reading;
    }

    public void setReading(String reading) {
        this.reading = reading;
    }

    public String getWat() {
        return wat;
    }

    public void setWat(String wat) {
        this.wat = wat;
    }

    public String getKszkzh() {
        return kszkzh;
    }

    public void setKszkzh(String kszkzh) {
        this.kszkzh = kszkzh;
    }

    public String getKslevel() {
        return kslevel;
    }

    public void setKslevel(String kslevel) {
        this.kslevel = kslevel;
    }
}
