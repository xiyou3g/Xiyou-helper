package com.xiyou3g.xiyouhelper.web.service;

import com.xiyou3g.xiyouhelper.common.ServerResponse;

import com.xiyou3g.xiyouhelper.model.*;

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
     * @return
     */
    ServerResponse<SearchBookResult> search(String suchenType, String suchenWord, int curPage);

    /**
     * 查询自己的借阅书籍
     * @param barcode 一卡通
     * @return
     */
    ServerResponse<List<BorrowedBook>> getMyBorrowedBooks(String barcode);


    /**
     * 续借
     * @param barcode 一卡通
     * @param bookCode 图书条码
     * @return
     */
    ServerResponse<String> renew(String barcode, String bookCode);

    /**
     * 获取书籍详细信息
     * @param url 图片url
     * @return
     */
    ServerResponse<BookDetail> getBookDetail(String url);

    /**
     * 获取个人借阅历史
     * @param barcode 一卡通
     * @return
     */
    ServerResponse<List<BorrowedBookHistory>> getMyBorrowedBooksHistory(String barcode);

    /**
     * 获取个人主页信息
     * @param barcode
     * @return
     */

    ServerResponse<BookMainInfo> getMain(String barcode);

    /**
     * 登出
     * @param barcode
     * @return
     */
    ServerResponse<String> logout(String barcode);
}
