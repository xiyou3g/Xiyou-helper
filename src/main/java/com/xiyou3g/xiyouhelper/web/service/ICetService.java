package com.xiyou3g.xiyouhelper.web.service;

import com.xiyou3g.xiyouhelper.model.Cetscore;
import us.codecraft.webmagic.selector.Html;

/**
 * @author du
 */
public interface ICetService {

    //测试登录结果
    int testLogin(Html htmlStr);

    //查询信息
    Cetscore searchCet(Html html);
}
