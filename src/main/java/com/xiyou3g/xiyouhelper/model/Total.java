package com.xiyou3g.xiyouhelper.model;

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

    @Override
    public String toString() {
        return "Total{" +
                "averageGPA='" + averageGPA + '\'' +
                ", allGPA='" + allGPA + '\'' +
                ", choice=" + choice +
                ", essential=" + essential +
                '}';
    }
}
