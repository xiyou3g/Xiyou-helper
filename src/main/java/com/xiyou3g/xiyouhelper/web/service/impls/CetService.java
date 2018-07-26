package com.xiyou3g.xiyouhelper.web.service.impls;

import com.xiyou3g.xiyouhelper.model.Cetscore;
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
    public int testLogin(Html htmlStr) {
        Html html = htmlStr;
        //准考证号及姓名有误
        String fault1 = html.xpath("//*[@id=\"leftH\"]/div/div[1]/text()").get();
        //验证码有误
        String fault2 = html.xpath("//*[@id=\"form1\"]/div/text()").get();

        System.out.println(fault1 + fault2);
        if (fault1.indexOf("最终结果") != -1 && fault2 == null){
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

    @Override
    public Cetscore searchCet(Html html) {
        Html html1 = html;

        Cetscore cs = new Cetscore();
        cs.setTotalscore(html.xpath("//*[@id=\"leftH\"]/div/table/tbody/tr[6]/td/span/text()").get());
        cs.setHearing(html.xpath("//*[@id=\"leftH\"]/div/table/tbody/tr[7]/td[2]/text()").get());
        cs.setReading(html.xpath("//*[@id=\"leftH\"]/div/table/tbody/tr[8]/td[2]/text()").get());
        cs.setWat(html.xpath("//*[@id=\"leftH\"]/div/table/tbody/tr[9]/td[2]/text()").get());
        cs.setKslevel(html.xpath("//*[@id=\"leftH\"]/div/table/tbody/tr[12]/td/text()").get());
        return cs;
    }
}
