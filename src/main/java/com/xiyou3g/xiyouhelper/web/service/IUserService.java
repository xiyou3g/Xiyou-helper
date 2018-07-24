package com.xiyou3g.xiyouhelper.web.service;


import com.xiyou3g.xiyouhelper.model.User;

/**
 * 18-7-21 下午3:02
 * @author mengchen
 */
public interface IUserService {
    /**
     * 测试登录结果
     * @param htmlStr
     * @return
     */
    int testLogin(String htmlStr);

    /**
     * 保存用户信息
     * @param user
     * @return
     */
    boolean saveUserMessage(User user);


    String getNameBySid(String sid);

    boolean isExist(String sid);
}
