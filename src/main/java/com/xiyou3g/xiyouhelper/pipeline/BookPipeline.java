package com.xiyou3g.xiyouhelper.pipeline;

import com.xiyou3g.xiyouhelper.model.Book;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zeng
 * @Date: 2018/7/21 16:05
 */
public class BookPipeline implements Pipeline {

    private List<Book> books = new ArrayList<>();

    @Override
    public void process(ResultItems resultItems, Task task) {

        Book book = new Book();
        book.setBookImgUrl(resultItems.get("img"));
        book.setShelf(resultItems.get("shelf"));
        book.setPublishingHouse(resultItems.get("publishingHouse"));
        book.setLeftNumber(resultItems.get("leftNumber"));
        book.setIndexNumber(resultItems.get("indexNumber"));
        book.setBookName(resultItems.get("bookName"));
        book.setAuthor(resultItems.get("author"));

        this.books.add(book);

    }

    public List<Book> getBooks() {

        if (this.books.size() >= 2) {
            books.remove(0);
        }

        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

}
