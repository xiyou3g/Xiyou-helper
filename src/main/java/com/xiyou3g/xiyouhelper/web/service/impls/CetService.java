package com.xiyou3g.xiyouhelper.web.service.impls;

import com.xiyou3g.xiyouhelper.web.service.ICetService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.selector.Html;

import static com.xiyou3g.xiyouhelper.util.constant.CetConstant.LOGIN_VALIDATE_CODE_ERROR;
import static com.xiyou3g.xiyouhelper.util.constant.CetConstant.LOGIN_ZKZH_NAME_ERROR;

/**
 * @author du
 */
@Service
public class CetService implements ICetService{
    /**
     * 检测登录结果
     * - 0 登录成功
     * - 1 准考证号或姓名错误
     * - 2 验证码错误
     *
     * -1 未知情况
     * @param htmlStr
     * @return
     */
    @Override
    public int testLogin(String htmlStr) {
        Html html = new Html(htmlStr);
        //准考证号及姓名有误
        String fault1 = html.xpath("//*[@id=\"leftH\"]/div/div[1]/text()").get();
        //验证码有误
        String fault2 = html.xpath("//*[@id=\"form1\"]/div/text()").get();

        if (fault1 == null && fault2 == null){
            return 0;
        }
        if (StringUtils.equals(fault1, LOGIN_ZKZH_NAME_ERROR)){
            return 1;
        }
        if (StringUtils.equals(fault2,LOGIN_VALIDATE_CODE_ERROR)){
            return 2;
        }

        return -1;
    }
}
