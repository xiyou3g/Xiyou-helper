package com.xiyou3g.xiyouhelper.model;

import java.util.List;

/**
 * @author zeng
 */
public class SearchBookResult {

    private Integer curPage;
    private List<CurPageBookResult> curPageBookResults;

    public Integer getCurPage() {
        return curPage;
    }

    public void setCurPage(Integer curPage) {
        this.curPage = curPage;
    }

    public List<CurPageBookResult> getCurPageBookResults() {
        return curPageBookResults;
    }

    public void setCurPageBookResults(List<CurPageBookResult> curPageBookResults) {
        this.curPageBookResults = curPageBookResults;
    }
}
