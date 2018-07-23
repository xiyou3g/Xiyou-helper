package com.xiyou3g.xiyouhelper.web.service.impls;

import com.xiyou3g.xiyouhelper.common.ServerResponse;
import com.xiyou3g.xiyouhelper.dao.UserMapper;
import com.xiyou3g.xiyouhelper.model.Book;
import com.xiyou3g.xiyouhelper.okhttp.BookOkHttp;
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


    @Override
    public ServerResponse<String> login(String barcode, String password) {

        String sessionId = BookOkHttp.login(barcode, password);

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


}
