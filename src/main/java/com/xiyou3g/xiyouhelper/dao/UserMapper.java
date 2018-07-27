package com.xiyou3g.xiyouhelper.dao;

import com.xiyou3g.xiyouhelper.model.User;

/**
 * @author mengchen
 */
public interface UserMapper {

    int insertUserMessage(User user);

    String getNameBySid(String sid);

    int isExist(String sid);

    User getUserBySid(String sid);
}