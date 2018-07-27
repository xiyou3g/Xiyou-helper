package com.xiyou3g.xiyouhelper.model;

import org.springframework.stereotype.Component;

/**
 * 成绩实体类
 */
public class Achievement {

    //课程名称
    private String className;
    //总成绩
    private String achievement;
    //平时分
    private String Ordinary;
    //课程绩点
    private String point;
    //课程性质
    private String nature;
    //课程学分
    private String credit;
    //期末成绩
    private String finalexam;

    public String getFinalexam() {
        return finalexam;
    }

    public void setFinalexam(String finalexam) {
        this.finalexam = finalexam;
    }

    public String getOrdinary() {
        return Ordinary;
    }

    public void setOrdinary(String ordinary) {
        Ordinary = ordinary;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getAchievement() {
        return achievement;
    }

    public void setAchievement(String achievement) {
        this.achievement = achievement;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    @Override
    public String toString() {
        return "Achievement{" +
                "className='" + className + '\'' +
                ", achievement='" + achievement + '\'' +
                ", Ordinary='" + Ordinary + '\'' +
                ", point='" + point + '\'' +
                ", nature='" + nature + '\'' +
                ", credit='" + credit + '\'' +
                ", finalexam='" + finalexam + '\'' +
                '}';
    }
}
