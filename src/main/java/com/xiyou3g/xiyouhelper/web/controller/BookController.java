package com.xiyou3g.xiyouhelper.web.controller;

import com.xiyou3g.xiyouhelper.common.ServerResponse;
import com.xiyou3g.xiyouhelper.okhttp.BookOkHttp;
import com.xiyou3g.xiyouhelper.util.redis.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zeng
 */
@RestController
@RequestMapping(value = "/books")
public class BookController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SessionUtil sessionUtil;

    @PostMapping("/login")
    public ServerResponse<String> login(String barcode, String password) {

        if (!BookOkHttp.login(barcode, password)) {
            return ServerResponse.createByErrorMsg("登录失败");
        }

        logger.info(barcode);
        logger.info(password);

        sessionUtil.setSessionId(barcode, password);

        return ServerResponse.createBySuccessMsg("登录成功");
    }
}
