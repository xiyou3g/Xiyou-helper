package com.xiyou3g.xiyouhelper.model;

import java.util.List;

public class BookMainInfo {


    private Integer count;
    private List<BorrowedBook> mainInfos;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<BorrowedBook> getMainInfos() {
        return mainInfos;
    }

    public void setMainInfos(List<BorrowedBook> mainInfos) {
        this.mainInfos = mainInfos;
    }
}
