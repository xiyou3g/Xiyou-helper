package com.xiyou3g.xiyouhelper.web.controller;

import com.xiyou3g.xiyouhelper.common.ServerResponse;
import com.xiyou3g.xiyouhelper.model.*;
import com.xiyou3g.xiyouhelper.web.service.IBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zeng
 */
@RestController
@RequestMapping(value = "/books")
public class BookController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IBookService bookService;

    @PostMapping("/login")
    public ServerResponse<String> login(String barcode, String password) {

        return bookService.login(barcode, password);
    }

    @PostMapping("/getBookDetail")
    public ServerResponse<BookDetail> getBookDetail(String url) {

        return bookService.getBookDetail(url);
    }

    @PostMapping("/search")
    public ServerResponse<SearchBookResult> search(String suchenType, String suchenWord, int curPage) {

        return bookService.search(suchenType, suchenWord, curPage);
    }


    @PostMapping("/borrowed")
    public ServerResponse<List<BorrowedBook>> getMyBorrowedBooks(String barcode) {

        return bookService.getMyBorrowedBooks(barcode);
    }

    @PostMapping("/renew")
    public ServerResponse<String> renew(String barcode, String bookcode) {

        return bookService.renew(barcode, bookcode);
    }

    @PostMapping("/borrowedHistory")
    public ServerResponse<List<BorrowedBookHistory>> getMyBorrowedBooksHistory(String barcode) {

        return bookService.getMyBorrowedBooksHistory(barcode);
    }

    @PostMapping("/main")
    public ServerResponse<BookMainInfo> getMain(String barcode) {

        return bookService.getMain(barcode);
    }

}
