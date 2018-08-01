package com.xiyou3g.xiyouhelper.model;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * @Author: zeng
 * @Date: 2018/7/22 9:01
 */
public class BorrowedBook {

    private Integer count;
    private String bookName;
    private String bookCode;
    private String collectionDepartment;
    private String circulationStatus;
    private String shouldReturnDay;
    private Integer status;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookCode() {
        return bookCode;
    }

    public void setBookCode(String bookCode) {
        this.bookCode = bookCode;
    }

    public String getCollectionDepartment() {
        return collectionDepartment;
    }

    public void setCollectionDepartment(String collectionDepartment) {
        this.collectionDepartment = collectionDepartment;
    }

    public String getCirculationStatus() {
        return circulationStatus;
    }

    public void setCirculationStatus(String circulationStatus) {
        this.circulationStatus = circulationStatus;
    }

    public String getShouldReturnDay() {
        return shouldReturnDay;
    }

    public void setShouldReturnDay(String shouldReturnDay) {
        this.shouldReturnDay = shouldReturnDay;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
