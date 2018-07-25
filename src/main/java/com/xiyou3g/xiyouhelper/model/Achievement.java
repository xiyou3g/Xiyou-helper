package com.xiyou3g.xiyouhelper.model;


/**
 * 成绩实体类
 */
public class Achievement {

    private Integer id;

    //学号
    private String num;
    //学年
    private String school_year;
    //学期
    private String semester;
    //课程名称
    private String classname;
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

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getSchool_year() {
        return school_year;
    }

    public void setSchool_year(String school_year) {
        this.school_year = school_year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getAchievement() {
        return achievement;
    }

    public void setAchievement(String achievement) {
        this.achievement = achievement;
    }

    public String getOrdinary() {
        return Ordinary;
    }

    public void setOrdinary(String ordinary) {
        Ordinary = ordinary;
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

    public String getFinalexam() {
        return finalexam;
    }

    public void setFinalexam(String finalexam) {
        this.finalexam = finalexam;
    }

    @Override
    public String toString() {
        return "Achievement{" +
                "id=" + id +
                ", num='" + num + '\'' +
                ", school_year='" + school_year + '\'' +
                ", semester='" + semester + '\'' +
                ", classname='" + classname + '\'' +
                ", achievement='" + achievement + '\'' +
                ", Ordinary='" + Ordinary + '\'' +
                ", point='" + point + '\'' +
                ", nature='" + nature + '\'' +
                ", credit='" + credit + '\'' +
                ", finalexam='" + finalexam + '\'' +
                '}';
    }
}
