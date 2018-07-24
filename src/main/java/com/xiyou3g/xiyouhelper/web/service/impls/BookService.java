package com.xiyou3g.xiyouhelper.web.service.impls;

import com.xiyou3g.xiyouhelper.common.ServerResponse;
import com.xiyou3g.xiyouhelper.dao.UserMapper;
import com.xiyou3g.xiyouhelper.model.Book;
import com.xiyou3g.xiyouhelper.model.BookStatus;
import com.xiyou3g.xiyouhelper.okhttp.BookOkHttp;
import com.xiyou3g.xiyouhelper.processor.BorrowedBookListProcessor;
import com.xiyou3g.xiyouhelper.processor.BorrowedHistoryProcessor;
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
    private BookOkHttp bookOkHttp;
    @Autowired
    private TimeUtil timeUtil;

    @Override
    public ServerResponse<String> login(String barcode, String password) {

        String sessionId = bookOkHttp.login(barcode, password);

        if (sessionId == null) {
            return ServerResponse.createByErrorMsg("登录失败");
        }

        sessionUtil.setSessionId(PrefixEnum.Book.getDesc(), barcode, sessionId);


        // TODO: 2018/7/20 账户密码持久到数据库


        return ServerResponse.createBySuccessMsg("登录成功");
    }

    @Override
    public ServerResponse<List<Book>> search(String barcode, String suchenType,
                                             String suchenWord, String libraryId) {

        if (barcode == null) {
            return ServerResponse.createByErrorMsg("一卡通为空，查询失败");
        }

        String sessionId = sessionUtil.getSessionId(PrefixEnum.Book.getDesc(), barcode);

        if (sessionId == null) {
            return ServerResponse.createByErrorMsg("身份认证失效，请重新登录");
        }

        SearchBookProcessor bookProcessor = new SearchBookProcessor(sessionId, suchenType, suchenWord, libraryId);

        List<Book> booksResponse = bookProcessor.searchBooks();

        if (booksResponse == null || (booksResponse.size() == 0 && booksResponse.get(0) == null)) {
            return ServerResponse.createByErrorMsg("没有内容");
        }

        return ServerResponse.createBySuccess("查询成功", booksResponse);
    }

    @Override
    public ServerResponse<List<BookStatus>> getMyBorrowedBooks(String barcode) {

        if (barcode == null) {
            return ServerResponse.createByErrorMsg("一卡通为空，查询失败");
        }

        String sessionId = sessionUtil.getSessionId(PrefixEnum.Book.getDesc(), barcode);

        if (sessionId == null) {
            return ServerResponse.createByErrorMsg("身份认证失效，请重新登录");
        }

        BorrowedBookListProcessor borrowedBookInfoProcessor = new BorrowedBookListProcessor(sessionId);

        List<BookStatus> borrowedBooksResponse = borrowedBookInfoProcessor.getMyBorrowedBooks();

        if (borrowedBooksResponse == null) {
            return ServerResponse.createBySuccessMsg("无借阅信息");
        }

        SearchBookProcessor searchBookProcessor = new SearchBookProcessor(sessionId);
        BorrowedHistoryProcessor borrowedHistoryProcessor = new BorrowedHistoryProcessor(sessionId);

        for (BookStatus book : borrowedBooksResponse) {

            Book bookInfo = searchBookProcessor.searchBook(book.getBookCode());

            book.setAuthor(bookInfo.getAuthor());
            book.setIndexNumber(bookInfo.getIndexNumber());
            book.setPublishingHouse(bookInfo.getPublishingHouse());

            String borrowDay = borrowedHistoryProcessor.getBorrowDay(book.getBookCode());


            if (borrowDay != null) {
                book.setBorrowDay(borrowDay);
            }

            String status = timeUtil.compTime(new Date("2018/09/10").getTime(), System.currentTimeMillis());
            book.setStatus(status);

        }


        return ServerResponse.createBySuccess("查询成功", borrowedBooksResponse);
    }

    @Override
    public ServerResponse<String> renew(String barcode, String bookCode) {

        if (barcode == null || bookCode == null) {
            return ServerResponse.createByErrorMsg("一卡通或图书条码为空，续借失败");
        }

        String sessionId = sessionUtil.getSessionId(PrefixEnum.Book.getDesc(), barcode);

        if (sessionId == null) {
            return ServerResponse.createByErrorMsg("身份认证失效，请重新登录");
        }

        String renewResponse = bookOkHttp.renew(sessionId, bookCode);

        return ServerResponse.createBySuccessMsg(renewResponse);
    }


}
