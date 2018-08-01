package com.xiyou3g.xiyouhelper.model;

import java.util.List;

public class BookDetail {

    private String img;
    private String indexNumber;
    private String publish;
//    丛编
    private String series;
    private String note;
    private String introduction;
    private String bookName;
    private String page;
    private String author;
//    分类号
    private String classificationNumber;
    private String theme;
    private List<BookDistributionInformation> bookDistributionInformations;

    public List<BookDistributionInformation> getBookDistributionInformations() {
        return bookDistributionInformations;
    }

    public void setBookDistributionInformations(List<BookDistributionInformation> bookDistributionInformations) {
        this.bookDistributionInformations = bookDistributionInformations;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getIndexNumber() {
        return indexNumber;
    }

    public void setIndexNumber(String indexNumber) {
        this.indexNumber = indexNumber;
    }

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getClassificationNumber() {
        return classificationNumber;
    }

    public void setClassificationNumber(String classificationNumber) {
        this.classificationNumber = classificationNumber;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}