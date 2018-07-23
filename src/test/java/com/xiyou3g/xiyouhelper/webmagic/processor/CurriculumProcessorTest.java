package com.xiyou3g.xiyouhelper.webmagic.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.HttpConstant;

import java.util.HashMap;
import java.util.Map;

import static com.xiyou3g.xiyouhelper.util.constant.CurriculumConstant.*;
import static com.xiyou3g.xiyouhelper.util.constant.CurriculumConstant.TERM;
import static com.xiyou3g.xiyouhelper.util.constant.CurriculumConstant.YEAR;

/**
 * mengchen
 * 18-7-20 上午9:51
 */
public class CurriculumProcessorTest implements PageProcessor {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private Site site = Site.me()
            .addHeader("Referer", "http://222.24.62.120/xskbcx.aspx?xh=04161031&xm=%C3%CF%B3%BF&gnmkdm=N121603")
            .addCookie("ASP.NET_SessionId", "m0k0yx45gcgy3gv0opy1r12n");

    @Override
    public void process(Page page) {
        logger.debug(page.toString());
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put(HIDDEN_NAME1, HIDDEN_VALUE1);
        map.put(HIDDEN_NAME2, HIDDEN_VALUE2);
        map.put("__VIEWSTATE", "dDwxNTM4MTc2MTcwO3Q8O2w8aTwxPjs+O2w8dDw7bDxpPDE+O2k8Mj47aTw0PjtpPDc+O2k8OT47aTwxMT47aTwxMz47aTwxNT47aTwyMj47aTwyNj47aTwyOD47aTwzMD47aTwzND47aTwzNj47aTw0MD47PjtsPHQ8cDxwPGw8VGV4dDs+O2w8XGU7Pj47Pjs7Pjt0PHQ8cDxwPGw8RGF0YVRleHRGaWVsZDtEYXRhVmFsdWVGaWVsZDs+O2w8eG47eG47Pj47Pjt0PGk8Mj47QDwyMDE3LTIwMTg7MjAxNi0yMDE3Oz47QDwyMDE3LTIwMTg7MjAxNi0yMDE3Oz4+O2w8aTwwPjs+Pjs7Pjt0PHQ8OztsPGk8MD47Pj47Oz47dDxwPHA8bDxUZXh0Oz47bDzlrablj7fvvJowNDE2MTAzMTs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w85aeT5ZCN77ya5a2f5pmoOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzlrabpmaLvvJrorqHnrpfmnLrlrabpmaI7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPOS4k+S4mu+8muiuoeeul+acuuenkeWtpuS4juaKgOacrzs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w86KGM5pS/54+t77ya6K6h56eRMTYwMTs+Pjs+Ozs+O3Q8O2w8aTwxPjtpPDM+Oz47bDx0PDtsPGk8MD47PjtsPHQ8O2w8aTwwPjs+O2w8dDxAMDw7Ozs7Ozs7Ozs7Pjs7Pjs+Pjs+Pjt0PDtsPGk8MD47PjtsPHQ8O2w8aTwwPjs+O2w8dDxAMDw7Ozs7Ozs7Ozs7Pjs7Pjs+Pjs+Pjs+Pjt0PDtsPGk8MT47PjtsPHQ8QDA8Ozs7Ozs7Ozs7Oz47Oz47Pj47dDxwPGw8VmlzaWJsZTs+O2w8bzxmPjs+PjtsPGk8MT47PjtsPHQ8QDA8Ozs7Ozs7Ozs7Oz47Oz47Pj47dDxAMDxwPHA8bDxQYWdlQ291bnQ7XyFJdGVtQ291bnQ7XyFEYXRhU291cmNlSXRlbUNvdW50O0RhdGFLZXlzOz47bDxpPDE+O2k8Mj47aTwyPjtsPD47Pj47Pjs7Ozs7Ozs7Ozs+O2w8aTwwPjs+O2w8dDw7bDxpPDE+O2k8Mj47PjtsPHQ8O2w8aTwwPjtpPDE+O2k8Mj47aTwzPjtpPDQ+O2k8NT47aTw2PjtpPDc+Oz47bDx0PHA8cDxsPFRleHQ7PjtsPDA0MDMwMDM7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPDA0MDMwMDM7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPOiwgzAwNTE7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPCgyMDE3LTIwMTgtMSktTFgxMTM1MDItMDQwMzAwMy0xOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzmpoLnjoforrrkuI7mlbDnkIbnu5/orqFCOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzlkagz56ysNeiKgui/nue7rTLoioJ756ysNy035ZGo5Y2V5ZGofS9GRjIwMy/liJjlu7rlhYM7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPOWRqDXnrKwz6IqC6L+e57utMuiKgnvnrKw3LTflkajljZXlkah9L0ZaMzIwL+WImOW7uuWFgzs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8MjAxNy0xMC0xOC0xNy0zMjs+Pjs+Ozs+Oz4+O3Q8O2w8aTwwPjtpPDE+O2k8Mj47aTwzPjtpPDQ+O2k8NT47aTw2PjtpPDc+Oz47bDx0PHA8cDxsPFRleHQ7PjtsPDEwMDEwMDc7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPDEwMDEwMDc7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPOiwgzAwNjg7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPCgyMDE3LTIwMTgtMSktUlcxMDAwMzAtMTAwMTAwNy0xOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzkuK3lm73ov5HnjrDku6Plj7LnurLopoE7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPOWRqDXnrKw16IqC6L+e57utMuiKgnvnrKw5LTnlkah9L0ZGMjAzL+W8oOWbreWbrTs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w85ZGoM+esrDnoioLov57nu60y6IqCe+esrDEwLTEw5ZGo5Y+M5ZGofS9GRjIwMy/lvKDlm63lm607Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPDIwMTctMTAtMzAtMTctMDM7Pj47Pjs7Pjs+Pjs+Pjs+Pjt0PDtsPGk8MD47PjtsPHQ8O2w8aTwwPjs+O2w8dDxAMDxwPHA8bDxQYWdlQ291bnQ7XyFJdGVtQ291bnQ7XyFEYXRhU291cmNlSXRlbUNvdW50O0RhdGFLZXlzOz47bDxpPDE+O2k8Mj47aTwyPjtsPD47Pj47Pjs7Ozs7Ozs7Ozs+O2w8aTwwPjs+O2w8dDw7bDxpPDE+O2k8Mj47PjtsPHQ8O2w8aTwwPjtpPDE+O2k8Mj47aTwzPjtpPDQ+O2k8NT47aTw2Pjs+O2w8dDxwPHA8bDxUZXh0Oz47bDzph5Hlt6Xlrp7kuaBCIDs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w86JSh56eA5qKFOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwxLjA7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPDAxLTE4Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47Pj47dDw7bDxpPDA+O2k8MT47aTwyPjtpPDM+O2k8ND47aTw1PjtpPDY+Oz47bDx0PHA8cDxsPFRleHQ7PjtsPOaVsOaNrue7k+aehOivvueoi+iuvuiuoTs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w86KGh6ZyeOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwxLjA7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPDAxLTE4Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47Pj47Pj47Pj47Pj47Pj47dDxAMDxwPHA8bDxQYWdlQ291bnQ7XyFJdGVtQ291bnQ7XyFEYXRhU291cmNlSXRlbUNvdW50O0RhdGFLZXlzOz47bDxpPDE+O2k8MD47aTwwPjtsPD47Pj47Pjs7Ozs7Ozs7Ozs+Ozs+O3Q8O2w8aTwwPjs+O2w8dDw7bDxpPDA+Oz47bDx0PEAwPHA8cDxsPFBhZ2VDb3VudDtfIUl0ZW1Db3VudDtfIURhdGFTb3VyY2VJdGVtQ291bnQ7RGF0YUtleXM7PjtsPGk8MT47aTw1PjtpPDU+O2w8Pjs+Pjs+Ozs7Ozs7Ozs7Oz47bDxpPDA+Oz47bDx0PDtsPGk8MT47aTwyPjtpPDM+O2k8ND47aTw1Pjs+O2w8dDw7bDxpPDA+O2k8MT47aTwyPjtpPDM+O2k8ND47PjtsPHQ8cDxwPGw8VGV4dDs+O2w8MjAxNy0yMDE4Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwxOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzlpKflrabniannkIblrp7pqoxCOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzpgrnlv5fnuq87Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPDIuMDs+Pjs+Ozs+Oz4+O3Q8O2w8aTwwPjtpPDE+O2k8Mj47aTwzPjtpPDQ+Oz47bDx0PHA8cDxsPFRleHQ7PjtsPDIwMTctMjAxODs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8MTs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w855S16Lev5Z+656GA5a6e6aqMOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzkuIHpuY/po547Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPDEuMDs+Pjs+Ozs+Oz4+O3Q8O2w8aTwwPjtpPDE+O2k8Mj47aTwzPjtpPDQ+Oz47bDx0PHA8cDxsPFRleHQ7PjtsPDIwMTctMjAxODs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8MTs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w86YeR5bel5a6e5LmgQiA7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPOiUoeengOaihTs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8MS4wOz4+Oz47Oz47Pj47dDw7bDxpPDA+O2k8MT47aTwyPjtpPDM+O2k8ND47PjtsPHQ8cDxwPGw8VGV4dDs+O2w8MjAxNy0yMDE4Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwxOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzmlbDmja7nu5PmnoTor77nqIvorr7orqE7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPOihoemcnjs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8MS4wOz4+Oz47Oz47Pj47dDw7bDxpPDA+O2k8MT47aTwyPjtpPDM+O2k8ND47PjtsPHQ8cDxwPGw8VGV4dDs+O2w8MjAxNy0yMDE4Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwxOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzmlbDlrZfnlLXot6/lrp7pqow7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPOW+kOmdmeiQjTs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8MS4wOz4+Oz47Oz47Pj47Pj47Pj47Pj47Pj47Pj47Pj47PuQgYmBDcPbp8MM4fzlR6YWjyKbo");
        map.put(YEAR, "2016-2017");
        map.put(TERM, 2);
        String url = "http://222.24.62.120/xskbcx.aspx?xh=04161031&xm=%C3%CF%B3%BF&gnmkdm=N121603";
        Request request = new Request(url);
        request.setMethod(HttpConstant.Method.POST);
        request.setRequestBody(HttpRequestBody.form(map, "GBK"));
        Spider.create(new CurriculumProcessorTest()).addRequest(request).run();
    }
}

