package com.xiyou3g.xiyouhelper.web.service.impls;

import com.xiyou3g.xiyouhelper.common.ServerResponse;
import com.xiyou3g.xiyouhelper.okhttp.BookOkHttp;
import com.xiyou3g.xiyouhelper.util.redis.SessionUtil;
import com.xiyou3g.xiyouhelper.web.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zeng
 */
@Service
public class BookService implements IBookService {

    @Autowired
    private SessionUtil sessionUtil;

    @Override
    public ServerResponse<String> login(String barcode, String password) {

        String sessionId = BookOkHttp.login(barcode, password);

        if (sessionId == null) {
            return ServerResponse.createByErrorMsg("登录失败");
        }

        sessionUtil.setSessionId(barcode, sessionId);

        return ServerResponse.createBySuccessMsg("登录成功");
    }
}
