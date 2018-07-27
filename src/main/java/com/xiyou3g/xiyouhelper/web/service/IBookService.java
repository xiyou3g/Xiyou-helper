package com.xiyou3g.xiyouhelper.web.service;

import com.xiyou3g.xiyouhelper.common.ServerResponse;

import com.xiyou3g.xiyouhelper.model.BookStatus;
import com.xiyou3g.xiyouhelper.model.SearchBookResult;

import java.util.List;

/**
 * @author zeng
 */

public interface IBookService {

    /**
     * 登录
     * @param barcode 一卡通
     * @param password 密码
     * @return
     */
    ServerResponse<String> login(String barcode, String password);

    /**
     * 图书检索
     * @param suchenType 检索类型
     * @param suchenWord 关键字
     * @param libraryId 分馆名称
     * @return
     */
    ServerResponse<List<SearchBookResult>> search(String suchenType, String suchenWord, String libraryId);

    /**
     * 查询自己的借阅书籍
     * @param barcode 一卡通
     * @return
     */
    ServerResponse<List<BookStatus>> getMyBorrowedBooks(String barcode);


    /**
     * 续借
     * @param barcode 一卡通
     * @param bookCode 图书条码
     * @return
     */
    ServerResponse<String> renew(String barcode, String bookCode);
}
