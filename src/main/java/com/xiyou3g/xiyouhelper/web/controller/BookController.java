package com.xiyou3g.xiyouhelper.web.controller;

import com.xiyou3g.xiyouhelper.common.ServerResponse;
import com.xiyou3g.xiyouhelper.model.BookStatus;
import com.xiyou3g.xiyouhelper.model.SearchBookResult;
import com.xiyou3g.xiyouhelper.web.service.IBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
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

    @PostMapping("/search")
    public ServerResponse<List<SearchBookResult>> search(String suchenType,
                                                         String suchenWord, String libraryId) {
        return bookService.search(suchenType, suchenWord, libraryId);
    }


    @PostMapping("/borrowed")
    public ServerResponse<List<BookStatus>> getMyBorrowedBooks(String barcode) {

        return bookService.getMyBorrowedBooks(barcode);

    }

    @PostMapping("/renew")
    public ServerResponse<String> renew(String barcode, String bookCode) {

        return bookService.renew(barcode, bookCode);
    }

}
