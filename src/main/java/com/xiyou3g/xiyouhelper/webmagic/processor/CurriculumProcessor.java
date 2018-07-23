package com.xiyou3g.xiyouhelper.webmagic.processor;

import com.xiyou3g.xiyouhelper.model.Course;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.utils.HttpConstant;

import java.util.HashMap;
import java.util.Map;

import static com.xiyou3g.xiyouhelper.util.constant.CommonConstant.XYE_HOST;
import static com.xiyou3g.xiyouhelper.util.constant.CurriculumConstant.*;

/**
 * @author mengchen
 * @date 18-7-20 上午9:30
 */
public class CurriculumProcessor implements PageProcessor {

    private String curriculumUrl = "http://222.24.62.120/xskbcx.aspx?xh=04161031&xm=%C3%CF%B3%BF&gnmkdm=N121603";
    private String sessionId = "m0k0yx45gcgy3gv0opy1r12n";
    private String year = "2017-2018";
    private String term = "1";


    private Site site = Site.me()
            .setDomain(XYE_HOST)
            .addHeader("Host", XYE_HOST)
            .addHeader("Referer", "http://222.24.62.120/xskbcx.aspx")
            .addCookie("ASP.NET_SessionId", sessionId);

    @Override
    public void process(Page page) {
        Html html = new Html(page.getRawText(), "http://222.24.62.120/xskbcx.aspx?xh=04161031&xm=%C3%CF%B3%BF&gnmkdm=N121603");
        String courseNode = html.xpath("/html/body/form/div/div/span/table[2]/tbody/tr[3]/td[3]").toString();

    }

    private void printCourses() {
        for (Course course : courses) {
            System.out.println(course.getName());
            System.out.println(course.getWeekDay());
            System.out.println(course.getTime());
            System.out.println(course.getWeeks());
            System.out.println(course.getTeacherName());
            System.out.println(course.getClassroom());
            System.out.println("是否单周：" + course.isOddWeek());
            System.out.println("是否双周：" + course.isEvenWeek());
        }
    }

    public void handlerCourse(String courseStr) {
        Course course = new Course();
        int i = courseStr.indexOf('>'), j;
        // 取出课程名字
        course.setName(courseStr.substring(i + 1, courseStr.indexOf('<', i)));
        i = courseStr.indexOf('>', i + 1);
        // 取出周几
        course.setWeekDay(courseStr.substring(i + 1, i + 3));
        // 取出节数
        course.setTime(courseStr.substring(i + 3, courseStr.indexOf('{', i)));
        // 设置单双周
        if ((j = courseStr.indexOf('|', i)) != -1) {
            i = courseStr.indexOf('{', i);
            // 设置周数
            course.setWeeks(courseStr.substring(i + 1, courseStr.indexOf('|', i)));
            if (courseStr.substring(j + 1, j + 2).equals("单")) {
                course.setOddWeek(true);
            } else {
                course.setEvenWeek(true);
            }
        } else {
            i = courseStr.indexOf('{', i);
            // 设置周数
            course.setWeeks(courseStr.substring(i + 1, courseStr.indexOf('}', i)));
        }
        i = courseStr.indexOf('>', i);
        // 设置老师姓名
        course.setTeacherName(courseStr.substring(i + 1, courseStr.indexOf('<', i)));
        i = courseStr.indexOf('>', i + 1);
        // 设置教室
        course.setClassroom(courseStr.substring(i + 1, courseStr.indexOf('<', i)));
        // 处理一个单双周课不同或者有调课的地方
        if ((i = courseStr.indexOf("<br><br>", i)) != -1) {
            // 单双周有不同的课
            if (courseStr.indexOf("font") == -1) {
                courseStr = courseStr.substring(i + 8);
                // 构造一个和上面类似的字符串，然后使用递归
                courseStr = ">" + courseStr;
                handlerCourse(courseStr);
            }
        }
        courses.add(course);
    }


    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        CurriculumProcessor processor = new CurriculumProcessor();
        Map<String, Object> map = new HashMap<>();
        map.put(HIDDEN_NAME1, HIDDEN_VALUE1);
        map.put(HIDDEN_NAME2, HIDDEN_VALUE2);
        map.put(HIDDEN_NAME3, HIDDEN_VALUE3);
        map.put(YEAR, processor.year);
        map.put(TERM, processor.term);
        String url = processor.curriculumUrl;
        Request request = new Request(url);
        request.setMethod(HttpConstant.Method.POST);
        request.setRequestBody(HttpRequestBody.form(map, "GBK"));
        Spider.create(new CurriculumProcessor()).addRequest(request).run();
    }

}
