package com.xiyou3g.xiyouhelper.web.service.impls;

import com.xiyou3g.xiyouhelper.common.ResponseCode;
import com.xiyou3g.xiyouhelper.common.ServerResponse;
import com.xiyou3g.xiyouhelper.model.*;
import com.xiyou3g.xiyouhelper.parse.BookParse;
import com.xiyou3g.xiyouhelper.util.redis.PrefixEnum;
import com.xiyou3g.xiyouhelper.util.redis.SessionUtil;
import com.xiyou3g.xiyouhelper.util.time.TimeUtil;
import com.xiyou3g.xiyouhelper.web.service.IBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.*;

import static com.xiyou3g.xiyouhelper.util.constant.BookConstant.BOOK_DETAIL_PREFIX;
import static com.xiyou3g.xiyouhelper.util.constant.BookConstant.BOOK_DETAIL_URL_REGEX;

/**
 * @author zeng
 */
@Service
public class BookService implements IBookService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SessionUtil sessionUtil;
    @Autowired
    private TimeUtil timeUtil;
    @Autowired
    private BookParse bookParse;

    @Override
    public ServerResponse<String> login(String barcode, String password) {

        ServerResponse response = bookParse.login(barcode, password);
        if (response.isSuccess()) {
            String sessionId = (String) response.getData();
            if (sessionId == null) {
                return ServerResponse.createByErrorMsg("服务器内部错误");
            }
            sessionUtil.setSessionIdLong(PrefixEnum.Book.getDesc(), barcode, sessionId);

            return ServerResponse.createBySuccessMsg("登录成功");
        }

        return ServerResponse.createByErrorMsg(response.getMsg());
    }


    @Override
    public ServerResponse<SearchBookResult> search(String suchenType, String suchenWord, int curPage) {

        if (suchenType == null || suchenWord == null) {
            return ServerResponse.createByErrorMsg("检索类型或检索词为空，检索失败");
        }

        SearchBookResult searchBooksResponse = bookParse.searchBook(suchenType, suchenWord, curPage);

        if (searchBooksResponse == null) {
            return ServerResponse.createBySuccessMsg("检索结果为空");
        }

        return ServerResponse.createBySuccess("查询成功", searchBooksResponse);
    }


    @Override
    public ServerResponse<List<BorrowedBook>> getMyBorrowedBooks(String barcode) {



        if (barcode == null) {
            return ServerResponse.createByErrorMsg("一卡通为空，查询失败");
        }

        String sessionId = sessionUtil.getSessionId(PrefixEnum.Book.getDesc(), barcode);

        if (sessionId == null) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "身份认证失效，请重新登录");
        }

        List<BorrowedBook> borrowedBooks = bookParse.getMyBorrowed(sessionId);
        if (borrowedBooks == null) {
            return ServerResponse.createByErrorMsg("不存在借阅");
        }

        for (BorrowedBook borrowedBook : borrowedBooks) {
            String time = borrowedBook.getShouldReturnDay();
            borrowedBook.setStatus(timeUtil.calculateStatus(time));
        }

        return ServerResponse.createBySuccess("查询成功", borrowedBooks);
    }

    @Override
    public ServerResponse<String> renew(String barcode, String bookCode) {

        if (barcode == null || bookCode == null) {
            return ServerResponse.createByErrorMsg("一卡通或图书条码为空，续借失败");
        }

        String sessionId = sessionUtil.getSessionId(PrefixEnum.Book.getDesc(), barcode);

//        取sessionId 若sessionId存在则说明身份信息未失效
        if (sessionId == null) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "身份认证失效，请重新登录");
        }

        String renewResponse = bookParse.renew(sessionId, bookCode);

        if (renewResponse == null) {
            return ServerResponse.createByErrorMsg("网络请求失败，续借失败");
        }

        return ServerResponse.createBySuccessMsg(renewResponse);
    }

    @Override
    public ServerResponse<BookDetail> getBookDetail(String url) {

        if (url == null) {
            return ServerResponse.createByErrorMsg("无法获取该图书详细信息，link为空");
        }

        if (!Pattern.matches(BOOK_DETAIL_URL_REGEX, url)) {
            return ServerResponse.createByErrorMsg("无效链接，检索失败");
        }

        StringBuilder stringBuilderUrl = new StringBuilder(BOOK_DETAIL_PREFIX);
        String bookDetailUrl = String.valueOf(stringBuilderUrl.append(url));
        logger.info("url " + bookDetailUrl);

        BookDetail bookDetail = bookParse.getBookDetail(bookDetailUrl);
        if (bookDetail == null) {
            return ServerResponse.createByErrorMsg("查询失败，无信息");
        }

        return ServerResponse.createBySuccess("查询成功", bookDetail);
    }

    @Override
    public ServerResponse<List<BorrowedBookHistory>> getMyBorrowedBooksHistory(String barcode) {

        if (barcode == null) {
            return ServerResponse.createByErrorMsg("一卡通为空，查询失败");
        }

        String sessionId = sessionUtil.getSessionId(PrefixEnum.Book.getDesc(), barcode);
        if (sessionId == null) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "身份认证失效，请重新登录");
        }

        List<BorrowedBookHistory> borrowedBookHistories = bookParse.getMyBorrowedBooksHistory(sessionId);
        if (borrowedBookHistories == null) {
            return ServerResponse.createByErrorMsg("不存在借阅历史");
        }

        return ServerResponse.createBySuccess("查询成功", borrowedBookHistories);

    }

    @Override
    public ServerResponse<BookMainInfo> getMain(String barcode) {

        if (barcode == null) {
            return ServerResponse.createByErrorMsg("一卡通为空，查询失败");
        }

        String sessionId = sessionUtil.getSessionId(PrefixEnum.Book.getDesc(), barcode);
        if (sessionId == null) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "身份认证失效，请重新登录");
        }

        BookMainInfo bookMainInfo = bookParse.getMain(sessionId);


        if (bookMainInfo == null) {
            return  ServerResponse.createByErrorMsg("无内容");
        }
        ServerResponse<List<BorrowedBook>> borrowedBooks = this.getMyBorrowedBooks(barcode);
        bookMainInfo.setMainInfos(borrowedBooks.getData());

        return ServerResponse.createBySuccess("查询成功", bookMainInfo);
    }

    @Override
    public ServerResponse<String> logout(String barcode) {

        if (barcode == null) {
            return ServerResponse.createByErrorMsg("登出操作无效，一卡通为空");
        }

        String sessionId = sessionUtil.getSessionId(PrefixEnum.Book.getDesc(), barcode);
        if (sessionId == null) {
            return ServerResponse.createByErrorMsg("注销失败");
        }

        boolean result = bookParse.logout(barcode);
        if (result) {
            sessionUtil.removeSessionId(PrefixEnum.Book.getDesc(), barcode);
            return ServerResponse.createBySuccessMsg("注销成功");
        }

        return ServerResponse.createByErrorMsg("注销失败");
    }


}
