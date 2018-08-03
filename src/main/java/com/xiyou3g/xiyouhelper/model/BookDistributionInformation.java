package com.xiyou3g.xiyouhelper.model;

public class BookDistributionInformation {

//    索书号
    private String callNumber;
    private String collectionDepartment;
    private String status;

    public String getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    public String getCollectionDepartment() {
        return collectionDepartment;
    }

    public void setCollectionDepartment(String collectionDepartment) {
        this.collectionDepartment = collectionDepartment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
