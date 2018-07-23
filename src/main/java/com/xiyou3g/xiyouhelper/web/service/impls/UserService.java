package com.xiyou3g.xiyouhelper.web.service.impls;

import com.xiyou3g.xiyouhelper.web.service.IUserService;
import org.apache.commons.lang3.StringUtils;
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
    /**
     * 检测登录结果
     * - 0 登录成功
     * - 1 密码错误
     * - 2 用户名不存在
     * - 3 验证码错误
     *
     * -1 未知情况
     * @param htmlStr
     * @return
     */
    @Override
    public int testLogin(String htmlStr) {
        Html html = new Html(htmlStr);
        String loginMessage = html.xpath("/html/body/form/script").regex("[\\u4e00-\\u9fa5]+").get();
        if (loginMessage == null) {
            return 0;
        }
        if (StringUtils.equals(loginMessage, LOGIN_PASS_ERROR)) {
            return 1;
        } else if (StringUtils.equals(loginMessage, LOGIN_USER_NOTFOUND)) {
            return 2;
        } else if (StringUtils.equals(loginMessage, LOGIN_VALIDATE_CODE_ERROR)) {
            return 3;
        }
        return -1;
    }

}
