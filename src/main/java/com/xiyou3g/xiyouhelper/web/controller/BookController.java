package com.xiyou3g.xiyouhelper.web.controller;

import com.xiyou3g.xiyouhelper.common.ServerResponse;
import com.xiyou3g.xiyouhelper.model.Book;
//<<<<<<< HEAD
//=======
import com.xiyou3g.xiyouhelper.okhttp.BookOkHttp;
import com.xiyou3g.xiyouhelper.util.redis.SessionUtil;
//>>>>>>> f862279bb8e0cf28f27c51692e9ae3ba8e4ca023
import com.xiyou3g.xiyouhelper.web.service.IBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//<<<<<<< HEAD
//=======
import org.springframework.web.bind.annotation.GetMapping;
//>>>>>>> f862279bb8e0cf28f27c51692e9ae3ba8e4ca023
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

//<<<<<<< HEAD
    @PostMapping("/search")
    public ServerResponse<List<Book>> search(String barcode,
                                             String suchenType, String suchenWord, String libraryId) {
        return bookService.search(barcode, suchenType, suchenWord, libraryId);
    }


//=======
//    @GetMapping("/search")
//    public ServerResponse<List<Book>> search(String suchenType, String suchenWord, String suchenMatch,
//                                             String recordType, String libraryId, String showType) {
//
//
//
//    }
//>>>>>>> f862279bb8e0cf28f27c51692e9ae3ba8e4ca023
}
