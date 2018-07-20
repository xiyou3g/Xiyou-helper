package com.xiyou3g.xiyouhelper.model;

/**
 * mengchen
 * 18-7-20 下午2:17
 */
public class Course {
    /** 课程名称 */
    private String name;
    /** 周几 */
    private String weekDay;
    /** 几周 */
    private String weeks;
    /** 节数 */
    private String time;
    /** 是否单周 */
    private boolean isOddWeek = false;
    /** 是否双周 */
    private boolean isEvenWeek = false;
    /** 老师 */
    private String teacherName;
    /** 教室 */

    private String classroom;

    public String getName() {
        return name;
    }

    public String getClassroom() {
        return classroom;
    }

    public boolean isOddWeek() {
        return isOddWeek;
    }

    public void setOddWeek(boolean oddWeek) {
        isOddWeek = oddWeek;
    }

    public boolean isEvenWeek() {
        return isEvenWeek;
    }

    public void setEvenWeek(boolean evenWeek) {
        isEvenWeek = evenWeek;
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
