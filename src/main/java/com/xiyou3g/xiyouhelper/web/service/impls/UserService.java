package com.xiyou3g.xiyouhelper.web.service.impls;

import com.xiyou3g.xiyouhelper.dao.UserMapper;
import com.xiyou3g.xiyouhelper.model.User;
import com.xiyou3g.xiyouhelper.web.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.selector.Html;

import static com.xiyou3g.xiyouhelper.util.constant.EduConstant.LOGIN_PASS_ERROR;
import static com.xiyou3g.xiyouhelper.util.constant.EduConstant.LOGIN_USER_NOTFOUND;
import static com.xiyou3g.xiyouhelper.util.constant.EduConstant.LOGIN_VALIDATE_CODE_ERROR;

/**
 * 18-7-21 下午3:03
 * @author mengchen
 */
@Service
public class UserService implements IUserService {


    @Autowired
    private UserMapper userMapper;




    @Override
    public boolean saveUserMessage(User user) {
        return userMapper.insertUserMessage(user) == 1;
    }

    @Override
    public String getNameBySid(String sid) {
        return userMapper.getNameBySid(sid);
    }

    @Override
    public boolean isExist(String sid) {
        return userMapper.isExist(sid) == 1;
    }

    @Override
    public User getUserBySid(String sid) {
        return userMapper.getUserBySid(sid);
    }

    @Override
    public String getXYEPassword(String studentNum) {
        return userMapper.getEduPassword(studentNum);
    }

    @Override
    public boolean saveEduPassword(String studetNum, String password) {
        if (userMapper.isExistUserPassword(studetNum) == 1) {
            return userMapper.updatePassword(studetNum, password, null) == 1;
        }
        return userMapper.insertEduPassword(studetNum, password) == 1;
    }


}
