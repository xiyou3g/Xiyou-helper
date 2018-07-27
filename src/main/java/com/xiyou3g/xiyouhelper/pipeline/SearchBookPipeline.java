package com.xiyou3g.xiyouhelper.pipeline;

import com.xiyou3g.xiyouhelper.model.SearchBookResult;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zeng
 * @Date: 2018/7/21 16:05
 */
public class SearchBookPipeline implements Pipeline {

    private List<SearchBookResult> searchBookResultList = new ArrayList<>();


    @Override
    public void process(ResultItems resultItems, Task task) {

        if (resultItems.getAll().size() == 0) {
            return;
        }

        SearchBookResult searchBookResult = new SearchBookResult();
        searchBookResult.setBookName(resultItems.get("bookName"));
        searchBookResult.setAuthor(resultItems.get("bookName"));
        searchBookResult.setPublishingHouse(resultItems.get("publishingHouse"));
        searchBookResult.setStandardNumber(resultItems.get("standardNumber"));
        searchBookResult.setPublishingYear(resultItems.get("publishingYear"));
        searchBookResult.setIndexNumber(resultItems.get("indexNumber"));
        searchBookResult.setTotal(resultItems.get("total"));
        searchBookResult.setTotal(resultItems.get("left"));

        this.searchBookResultList.add(searchBookResult);
    }


    public List<SearchBookResult> getSearchBookResultList() {
        return searchBookResultList;
    }

    public void setSearchBookResultList(List<SearchBookResult> searchBookResultList) {
        this.searchBookResultList = searchBookResultList;
    }
}
