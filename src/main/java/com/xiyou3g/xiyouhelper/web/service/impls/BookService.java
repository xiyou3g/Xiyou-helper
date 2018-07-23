package com.xiyou3g.xiyouhelper.web.service.impls;

import com.xiyou3g.xiyouhelper.common.ServerResponse;
import com.xiyou3g.xiyouhelper.dao.UserMapper;
import com.xiyou3g.xiyouhelper.model.Book;
import com.xiyou3g.xiyouhelper.model.BookStatus;
import com.xiyou3g.xiyouhelper.okhttp.BookOkHttp;
import com.xiyou3g.xiyouhelper.processor.BorrowedBookListProcessor;
import com.xiyou3g.xiyouhelper.processor.SearchBookProcessor;
import com.xiyou3g.xiyouhelper.util.redis.PrefixEnum;
import com.xiyou3g.xiyouhelper.util.redis.SessionUtil;
import com.xiyou3g.xiyouhelper.web.service.IBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        for (BookStatus book : borrowedBooksResponse) {
            System.out.println(book.getBookCode());
            Book bookInfo = searchBookProcessor.searchBook(book.getBookCode());

            System.out.println(bookInfo.getAuthor());
            book.setAuthor(bookInfo.getAuthor());
            book.setIndexNumber(bookInfo.getIndexNumber());
            book.setPublishingHouse(bookInfo.getPublishingHouse());
            // TODO: 2018/7/23 爬取归还日期 对比得到status
            book.setStatus("正常");
            book.setShouldReturnDay("11111111");
        }


        return ServerResponse.createBySuccess("查询成功", borrowedBooksResponse);
    }


}
