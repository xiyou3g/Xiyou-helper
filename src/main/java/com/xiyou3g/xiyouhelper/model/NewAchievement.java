package com.xiyou3g.xiyouhelper.model;

import java.util.List;

public class NewAchievement {

    //学年集合
    private List<String> year;
    //成绩集合
    List<Achievement> achievements;

    public List<Achievement> getAchievements() {
        return achievements;
    }

    public void setAchievements(List<Achievement> achievements) {
        this.achievements = achievements;
    }

    public List<String> getYear() {
        return year;
    }

    public void setYear(List<String> year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "NewAchievement{" +
                "year=" + year +
                ", achievements=" + achievements +
                '}';
    }
}
