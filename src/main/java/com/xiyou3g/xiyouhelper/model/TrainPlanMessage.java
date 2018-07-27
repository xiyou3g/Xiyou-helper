package com.xiyou3g.xiyouhelper.model;

/**
 * @author mengchen
 * @time 18-7-27 上午8:59
 */
public class TrainPlanMessage {

    private int id;


    /**
     * 专业
     */
    private String major;

    /**
     * 级别
     */
    private String level;

    /**
     * 学期
     */
    private int term;

    /**
     * 课程代码
     */
    private String classCode;

    /**
     * 课程名
     */
    private String className;

    /**
     * 课程性质
     */
    private String classCharacter;
    /**
     * 学分
     */
    private Float credit;

    /**
     * 考试类型
     */
    private String examType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getClassCharacter() {
        return classCharacter;
    }

    public void setClassCharacter(String classCharacter) {
        this.classCharacter = classCharacter;
    }

    public Float getCredit() {
        return credit;
    }

    public void setCredit(Float credit) {
        this.credit = credit;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    @Override
    public String toString() {
        return "TrainPlanMessage{" +
                "term=" + term +
                ", classCode='" + classCode + '\'' +
                ", className='" + className + '\'' +
                ", classCharacter='" + classCharacter + '\'' +
                ", credit=" + credit +
                ", examType='" + examType + '\'' +
                '}';
    }
}
