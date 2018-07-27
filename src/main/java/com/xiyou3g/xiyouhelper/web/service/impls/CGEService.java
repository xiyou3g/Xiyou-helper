package com.xiyou3g.xiyouhelper.web.service.impls;

import com.xiyou3g.xiyouhelper.model.CGEMessage;
import com.xiyou3g.xiyouhelper.web.service.ICGEService;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author mengchen
 * @time 18-7-26 上午10:31
 */
@Service
public class CGEService implements ICGEService {

    @Override
    public CGEMessage parseCGEMessage(String htmlStr) {
        CGEMessage cgeMessage = new CGEMessage();
        Html html = new Html(htmlStr);
        List<Selectable> nodes = html.xpath("/html/body/div[1]/table/tbody/tr").nodes();

        List<Selectable> childNodes1 = nodes.get(1).xpath("/tr/td").nodes();
        // 获取name
        String name = childNodes1.get(1).xpath("/td/text()").get();
        cgeMessage.setName(name);
        // 获取zkzh
        String zkzh = childNodes1.get(3).xpath("/td/text()").get();
        cgeMessage.setZkzh(zkzh);

        List<Selectable> childNodes2 = nodes.get(2).xpath("/tr/td").nodes();
        // 获取证件号
        String zjh = childNodes2.get(1).xpath("/td/text()").get();
        cgeMessage.setZjh(zjh);
        // 获取年份
        String year = childNodes2.get(3).xpath("/td/text()").get();
        cgeMessage.setYear(year);

        List<Selectable> childNodes3 = nodes.get(3).xpath("/tr/td").nodes();

        // 获取等级
        String status = childNodes3.get(1).xpath("/td/text()").get();
        cgeMessage.setStatus(status);

        // 获取月份
        String month = childNodes3.get(3).xpath("/td/text()").get();
        cgeMessage.setMonth(month);

        return cgeMessage;
    }
}
