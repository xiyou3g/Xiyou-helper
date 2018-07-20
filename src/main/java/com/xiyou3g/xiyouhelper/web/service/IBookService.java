package com.xiyou3g.xiyouhelper.web.service;

import com.xiyou3g.xiyouhelper.common.ServerResponse;

/**
 * @author zeng
 */

public interface IBookService {

    ServerResponse<String> login(String barcode, String password);
}
