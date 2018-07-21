package com.xiyou3g.xiyouhelper.processor;

import com.xiyou3g.xiyouhelper.model.Cetscore;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

/**
 * @author du
 */
public class CetsearchProcessor implements PageProcessor {

    private String cetsearchUrl
            = "https://www.chsi.com.cn/cet/query?zkzh=610151172115428&xm=%E6%9D%9C%E6%B3%BD%E6%B1%9F&yzm=404";


    private Site site = Site.me()
            .setDomain("www.chsi.com.cn")
            .addHeader("Host", "www.chsi.com.cn")
            .addHeader("Referer", "https://www.chsi.com.cn/cet/")
            .addCookie("JSESSIONID", "885E9E888B933A99B3D9488B91339B26");

    @Override
    public void process(Page page) {
        Html html = new Html(page.getRawText(),cetsearchUrl);

        Cetscore cs = new Cetscore();

        cs.setName(html.xpath("//*[@id=\"leftH\"]/div/table/tbody/tr[1]/td/text()").get());
        cs.setSchool(html.xpath("//*[@id=\"leftH\"]/div/table/tbody/tr[2]/td/text()").get());
        cs.setTestlevel(html.xpath("//*[@id=\"leftH\"]/div/table/tbody/tr[3]/td/text()").get());
        cs.setBszkzh(html.xpath("//*[@id=\"leftH\"]/div/table/tbody/tr[5]/td/text()").get());
        cs.setTotalscore(html.xpath("//*[@id=\"leftH\"]/div/table/tbody/tr[6]/td/span/text()").get());
        cs.setHearing(html.xpath("//*[@id=\"leftH\"]/div/table/tbody/tr[7]/td[2]/text()").get());
        cs.setReading(html.xpath("//*[@id=\"leftH\"]/div/table/tbody/tr[8]/td[2]/text()").get());
        cs.setWat(html.xpath("//*[@id=\"leftH\"]/div/table/tbody/tr[9]/td[2]/text()").get());
        cs.setKszkzh(html.xpath("//*[@id=\"leftH\"]/div/table/tbody/tr[11]/td/text()").get());
        cs.setKslevel(html.xpath("//*[@id=\"leftH\"]/div/table/tbody/tr[12]/td/text()").get());
        System.out.println(cs);
    }

    @Override
    public Site getSite() { return site; }

    public static void main(String[] args) {
        CetsearchProcessor processor = new CetsearchProcessor();
        String url = processor.cetsearchUrl;
        Request request = new Request(url);
        Spider.create(new CetsearchProcessor()).addRequest(request).run();
    }
}
