package com.xiyou3g.xiyouhelper.model;

/**
 * @Author: zeng
 * @Date: 2018/7/22 9:01
 */
public class BookStatus {

    private String bookName;
    private String author;
    private String publishingHouse;
    private String indexNumber;
    private String borrowDay;
    private String shouldReturnDay;
    private String bookCode;
    private String status;

    public String getBookCode() {
        return bookCode;
    }

    public void setBookCode(String bookCode) {
        this.bookCode = bookCode;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishingHouse() {
        return publishingHouse;
    }

    public void setPublishingHouse(String publishingHouse) {
        this.publishingHouse = publishingHouse;
    }

    public String getIndexNumber() {
        return indexNumber;
    }

    public void setIndexNumber(String indexNumber) {
        this.indexNumber = indexNumber;
    }

    public String getBorrowDay() {
        return borrowDay;
    }

    public void setBorrowDay(String borrowDay) {
        this.borrowDay = borrowDay;
    }

    public String getShouldReturnDay() {
        return shouldReturnDay;
    }

    public void setShouldReturnDay(String shouldReturnDay) {
        this.shouldReturnDay = shouldReturnDay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
