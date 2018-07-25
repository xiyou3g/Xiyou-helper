package com.xiyou3g.xiyouhelper.model;

/**
 * @author du
 */

public class Cetscore {
    //总分
    private String totalscore;
    //听力
    private String hearing;
    //阅读
    private String reading;
    //写作和翻译
    private String wat;
    //口语等级
    private String kslevel;

    @Override
    public String toString() {
        return "Cetscore{" +
                "totalscore='" + totalscore + '\'' +
                ", hearing='" + hearing + '\'' +
                ", reading='" + reading + '\'' +
                ", wat='" + wat + '\'' +
                ", kslevel='" + kslevel + '\'' +
                '}';
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

    public String getKslevel() {
        return kslevel;
    }

    public void setKslevel(String kslevel) {
        this.kslevel = kslevel;
    }
}
