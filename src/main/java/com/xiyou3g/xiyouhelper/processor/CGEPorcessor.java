package com.xiyou3g.xiyouhelper.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;
import us.codecraft.webmagic.utils.HttpConstant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mengchen
 * @time 18-7-25 下午9:22
 */
public class CGEPorcessor implements PageProcessor {

    Map<String, String> map = new HashMap<>();

    private Site site = new Site();

    @Override
    public void process(Page page) {
        System.out.println(page);
        Html html = page.getHtml();
        System.out.println(html);
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Request request = new Request("http://search.neea.edu.cn/QueryMarkUpAction.do?act=doQueryResults");
        request.addHeader("Cookie", "UM_distinctid=164d15a7c00253-040aa097e3257e-38710357-1fa400-164d15a7c01" +
                "500; community=Home; language=1; CNZZDATA1256596322=967708668-1532515592-http%253A%252F%252Fcjcx.neea." +
                "edu.cn%252F%7C1532566188; language=1; Hm_lvt_dc1d69ab90346d48ee02f18510292577=1532520501,1532521243; Hm" +
                "_lpvt_dc1d69ab90346d48ee02f18510292577=1532571550; verify=enc|ccfc39c16536106f00870d1b43dad0c504516491c6fcef181ebeb51b4ef23308; path=/; expires=26 Jul 2018 02:56:55 GMT; domain=.neea.edu.cn; httponly")
                .addHeader("User-Agent:", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.104 Safari/537.36")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Referer", "http://search.neea.edu.cn/QueryMarkUpAction.do?act=doQueryCond&sid=300&pram=results&ksnf=23kozRaPlayV4oxufU78cP&sf=&bkjb=14&sfzh=610321199703234229&name=%E9%83%91%E6%9D%A8")
                .addHeader("Origin", "http://search.neea.edu.cn");
        Map<String, Object> param = new HashMap<>();
        param.put("pram", "results");
        param.put("ksxm", "300");
        param.put("ksnf", "3S6F6bZfhddXBBt0D8K8qW");
        param.put("bkjb", "65");
        param.put("sfzh", "610321199703234229");
        param.put("name", "郑杨");
        param.put("verify", "853p");
        request.setMethod(HttpConstant.Method.POST);
        request.setExtras(param);
        Spider.create(new CGEPorcessor()).addRequest(request)
                .run();

    }
}
