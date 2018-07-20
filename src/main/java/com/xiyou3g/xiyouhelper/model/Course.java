package com.xiyou3g.xiyouhelper.model;

/**
 * mengchen
 * 18-7-20 下午2:17
 */
public class Course {
    private String name;    // 课程名称
    private String weekDay; // 周几
    private String weeks;    // 几周
    private String time;    // 节数
    private String teacherName; // 老师
    private String classroom;   // 教室

    public String getName() {
        return name;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public String getWeeks() {
        return weeks;
    }

    public void setWeeks(String weeks) {
        this.weeks = weeks;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
