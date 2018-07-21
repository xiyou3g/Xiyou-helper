package com.xiyou3g.xiyouhelper.web.controller;

import com.xiyou3g.xiyouhelper.common.ServerResponse;
import com.xiyou3g.xiyouhelper.model.Book;
import com.xiyou3g.xiyouhelper.okhttp.BookOkHttp;
import com.xiyou3g.xiyouhelper.util.redis.SessionUtil;
import com.xiyou3g.xiyouhelper.web.service.IBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

        ServerResponse response = bookService.login(barcode, password);

        return response;
    }

//    @GetMapping("/search")
//    public ServerResponse<List<Book>> search(String suchenType, String suchenWord, String suchenMatch,
//                                             String recordType, String libraryId, String showType) {
//
//
//
//    }
}
