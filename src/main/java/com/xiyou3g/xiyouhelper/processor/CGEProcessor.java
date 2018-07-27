package com.xiyou3g.xiyouhelper.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mengchen
 * @time 18-7-26 上午8:27
 */
public class CGEProcessor implements PageProcessor {

    Map<String, String> map = new HashMap<>();

    private Site site = new Site()
            .addCookie("esessionid", "1E77DDAE8AF8F0049CF31EADC9225C2F")
            .addHeader("Referer", "http://search.neea.edu.cn/QueryMarkUpAction.do?act=doQueryCond&pram=results&community=Home&sid=300");

    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        System.out.println(html);
        List<Selectable> nodes = html.xpath("//*[@id=\"examselect\"]/select/option").nodes();
        nodes.stream().forEach((node) -> {
            String key = node.xpath("/option/text()").get();
            String value = node.xpath("/option/@value").get();
            map.put(key, value);
        });
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new CGEPorcessor()).addUrl("http://search.neea.edu.cn/QueryMarkUpAction.do?act=doQueryCond&pram=results&community=Home&sid=300")
                .run();
    }
}
