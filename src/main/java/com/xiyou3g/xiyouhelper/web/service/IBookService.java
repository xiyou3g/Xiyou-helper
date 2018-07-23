package com.xiyou3g.xiyouhelper.web.service;

import com.xiyou3g.xiyouhelper.common.ServerResponse;
import com.xiyou3g.xiyouhelper.model.Book;
import com.xiyou3g.xiyouhelper.model.BookStatus;

import java.util.List;

/**
 * @author zeng
 */

public interface IBookService {

    ServerResponse<String> login(String barcode, String password);

    ServerResponse<List<Book>> search(String barcode, String suchenType, String suchenWord, String libraryId);

    ServerResponse<List<BookStatus>> getMyBorrowedBooks(String barcode);

    ServerResponse<String> renew(String barcode, String bookCode);
}
