package com.xiyou3g.xiyouhelper.web.service;

import com.xiyou3g.xiyouhelper.model.CGEMessage;
import okhttp3.OkHttpClient;

/**
 * @author mengchen
 * @time 18-7-26 上午10:31
 */
public interface ICGEService {

    CGEMessage parseCGEMessage(String hemlStr);
}
