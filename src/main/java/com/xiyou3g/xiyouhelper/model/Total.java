package com.xiyou3g.xiyouhelper.model;

/**
 * 成绩统计实体类
 */
public class Total {

    private String averageGPA;
    private String allGPA;
    private Choice choice;
    private Essential essential;

    public String getAverageGPA() {
        return averageGPA;
    }

    public void setAverageGPA(String averageGPA) {
        this.averageGPA = averageGPA;
    }

    public String getAllGPA() {
        return allGPA;
    }

    public void setAllGPA(String allGPA) {
        this.allGPA = allGPA;
    }

    public Choice getChoice() {
        return choice;
    }

    public void setChoice(Choice choice) {
        this.choice = choice;
    }

    public Essential getEssential() {
        return essential;
    }

    public void setEssential(Essential essential) {
        this.essential = essential;
    }

}
