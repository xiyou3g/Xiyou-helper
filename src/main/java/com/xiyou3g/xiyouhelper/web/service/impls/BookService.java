package com.xiyou3g.xiyouhelper.web.service.impls;

import com.xiyou3g.xiyouhelper.common.ServerResponse;
import com.xiyou3g.xiyouhelper.dao.UserMapper;
import com.xiyou3g.xiyouhelper.model.BookStatus;
import com.xiyou3g.xiyouhelper.model.SearchBookResult;
import com.xiyou3g.xiyouhelper.okhttp.BookOkHttp;
import com.xiyou3g.xiyouhelper.processor.SearchBookProcessor;
import com.xiyou3g.xiyouhelper.util.redis.PrefixEnum;
import com.xiyou3g.xiyouhelper.util.redis.SessionUtil;
import com.xiyou3g.xiyouhelper.util.time.TimeUtil;
import com.xiyou3g.xiyouhelper.web.service.IBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author zeng
 */
@Service
public class BookService implements IBookService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SessionUtil sessionUtil;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TimeUtil timeUtil;

    @Override
    public ServerResponse<String> login(String barcode, String password) {

        BookOkHttp bookOkHttp = new BookOkHttp();

        String sessionId = bookOkHttp.login(barcode, password);

        if (sessionId == null) {
            return ServerResponse.createByErrorMsg("登录失败");
        }

        sessionUtil.setSessionIdLong(PrefixEnum.Book.getDesc(), barcode, sessionId);


        String persistencePassword = userMapper.checkUserExisted(barcode);
//        用户已持久化且密码无变化
        if (persistencePassword != null && persistencePassword.equals(password)) {
            return ServerResponse.createBySuccessMsg("登录成功");
        }

//        登录之后持久化密码
        userMapper.insertUserBookSystemPassword(barcode, password);

        return ServerResponse.createBySuccessMsg("登录成功");
    }


    @Override
    public ServerResponse<List<SearchBookResult>> search(String suchenType, String suchenWord,
                                             String libraryId) {


//        SearchBookProcessor searchBookProcessor = new SearchBookProcessor();
//
//        searchBookProcessor.setSuchenType(suchenType);
//        searchBookProcessor.setSuchenWord(suchenWord);
//        searchBookProcessor.setLibraryId(libraryId);
//
//        List<SearchBookResult> searchBooksResponse = searchBookProcessor.searchBook();

        BookOkHttp bookOkHttp = new BookOkHttp();

        List<SearchBookResult> searchBooksResponse = bookOkHttp.searchBook(suchenType, suchenWord, libraryId);

        if (searchBooksResponse == null) {
            return ServerResponse.createByErrorMsg("没有内容");
        }

        return ServerResponse.createBySuccess("查询成功", searchBooksResponse);
    }


    @Override
    public ServerResponse<List<BookStatus>> getMyBorrowedBooks(String barcode) {

//
//
//        if (barcode == null) {
//            return ServerResponse.createByErrorMsg("一卡通为空，查询失败");
//        }
//
//        String password = userMapper.selectBookPassword(barcode);
//        if (password != null) {
//            this.login(barcode, password, 1);
//        } else {
//            return ServerResponse.createByErrorMsg("请登录");
//        }
//
//        String sessionId = sessionUtil.getSessionId(PrefixEnum.Book.getDesc(), barcode);
//
//        if (sessionId == null) {
//            return ServerResponse.createByErrorMsg("身份认证失效，请重新登录");
//        }
//
//        BorrowedBookListProcessor borrowedBookInfoProcessor = new BorrowedBookListProcessor(sessionId);
//
//        List<BookStatus> borrowedBooksResponse = borrowedBookInfoProcessor.getMyBorrowedBooks();
//
//        if (borrowedBooksResponse == null) {
//            return ServerResponse.createBySuccessMsg("无借阅信息");
//        }
//
////        SearchBookProcessor searchBookProcessor = new SearchBookProcessor(sessionId);
//        BorrowedHistoryProcessor borrowedHistoryProcessor = new BorrowedHistoryProcessor(sessionId);
//
//        for (BookStatus book : borrowedBooksResponse) {
//
//            Book bookInfo = searchBookProcessor.searchBook(book.getBookCode());
//
//            book.setAuthor(bookInfo.getAuthor());
//            book.setIndexNumber(bookInfo.getIndexNumber());
//            book.setPublishingHouse(bookInfo.getPublishingHouse());
//
//            String borrowDay = borrowedHistoryProcessor.getBorrowDay(book.getBookCode());
//
//
//            if (borrowDay != null) {
//                book.setBorrowDay(borrowDay);
//            }
//
//            String status = timeUtil.compTime(new Date("2018/09/10").getTime(), System.currentTimeMillis());
//            book.setStatus(status);
//
//        }
//

//        return ServerResponse.createBySuccess("查询成功", borrowedBooksResponse);

        return null;
    }


    @Override
    public ServerResponse<String> renew(String barcode, String bookCode) {

        String sessionId = sessionUtil.getSessionId(PrefixEnum.Book.getDesc(), barcode);

//        因为这里是续借逻辑 续借的话肯定是数据持久化之后了所以判断sessionId是否在redis存在就好
//        若不存在则使用login逻辑自动登录
        if (sessionId == null) {

            String password = userMapper.getBookPassword(barcode);

            this.login(barcode, password);

//            重新从redis获取最新sessionId

            sessionId = sessionUtil.getSessionId(PrefixEnum.Book.getDesc(), barcode);
        }

        if (barcode == null || bookCode == null) {
            return ServerResponse.createByErrorMsg("一卡通或图书条码为空，续借失败");
        }

        BookOkHttp bookOkHttp = new BookOkHttp();

        String renewResponse = bookOkHttp.renew(sessionId, bookCode);

        return ServerResponse.createBySuccessMsg(renewResponse);
    }


}
