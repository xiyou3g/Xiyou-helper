package com.xiyou3g.xiyouhelper.model;

/**
 * @Author: zeng
 * @Date: 2018/7/20 19:29
 */
public class Book {

    private String bookImgUrl;
    private String bookName;
    private String author;
    private String publishingHouse;
    private String shelf;
    private String indexNumber;
    private Long leftNumber;

    public String getBookImgUrl() {
        return bookImgUrl;
    }

    public void setBookImgUrl(String bookImgUrl) {
        this.bookImgUrl = bookImgUrl;
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

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

    public String getIndexNumber() {
        return indexNumber;
    }

    public void setIndexNumber(String indexNumber) {
        this.indexNumber = indexNumber;
    }

    public Long getLeftNumber() {
        return leftNumber;
    }

    public void setLeftNumber(Long leftNumber) {
        this.leftNumber = leftNumber;
    }
}
