package com.xiyou3g.xiyouhelper.okhttp;

import com.xiyou3g.xiyouhelper.model.Achievement;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.util.StreamUtils;
import us.codecraft.webmagic.selector.Html;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static com.xiyou3g.xiyouhelper.util.constant.AchievementConstant.*;
import static com.xiyou3g.xiyouhelper.util.constant.AchievementConstant.VALUE4;

/**
 * @author mengchen
 * @time 18-7-26 下午4:57
 */
public class TestTest {

    public static List<Achievement> achievements = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        formBodyBuilder.add(NAME1, VALUE1);
        formBodyBuilder.add(NAME2, VALUE2);
        formBodyBuilder.add(NAME3, "dDwtMTIxNTQzNzk3NTt0PHA8bDxTb3J0RXhwcmVzO3NmZGNiaztkZzM7ZHlieXNjajtTb3J0RGlyZTt4aDtzdHJfdGFiX2JqZztjamN4X2xzYjt6eGNqY3h4czs+O2w8a2NtYztcZTtiamc7XGU7YXNjOzA0MTYxMDMxO3pmX2N4Y2p0al8wNDE2MTAzMTs7MDs+PjtsPGk8MT47PjtsPHQ8O2w8aTw0PjtpPDEwPjtpPDIzPjtpPDI4PjtpPDM2PjtpPDQwPjtpPDQyPjtpPDQ0PjtpPDQ2PjtpPDQ4PjtpPDUwPjtpPDUyPjtpPDU0PjtpPDU4PjtpPDYwPjtpPDYyPjs+O2w8dDx0PHA8cDxsPERhdGFUZXh0RmllbGQ7RGF0YVZhbHVlRmllbGQ7PjtsPFhOO1hOOz4+Oz47dDxpPDM+O0A8XGU7MjAxNy0yMDE4OzIwMTYtMjAxNzs+O0A8XGU7MjAxNy0yMDE4OzIwMTYtMjAxNzs+Pjs+Ozs+O3Q8dDxwPHA8bDxEYXRhVGV4dEZpZWxkO0RhdGFWYWx1ZUZpZWxkOz47bDxrY3h6bWM7a2N4emRtOz4+Oz47dDxpPDEyPjtAPOW/heS/ruivvjvpmZDpgInor7475Lu76YCJ6K++O+ivvuWkluWunui3teaVmeWtpjvovoXkv67or7476Leo5a2m56eRO+e0oOi0qOaLk+WxlTvlhazlhbHpgInkv67or7475Lq65paH57Sg6LSo6ZmQ6YCJO+mAieS/ruivvjvpgInkv67or74o5bCU6ZuFKTtcZTs+O0A8MDE7MDI7MDM7MDQ7MDU7MDY7MDc7MDg7MDk7MTA7MTE7XGU7Pj47Pjs7Pjt0PHA8cDxsPFZpc2libGU7PjtsPG88Zj47Pj47Pjs7Pjt0PHA8cDxsPFZpc2libGU7PjtsPG88Zj47Pj47Pjs7Pjt0PHA8cDxsPFZpc2libGU7PjtsPG88Zj47Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPFxlOz4+Oz47Oz47dDxwPHA8bDxUZXh0O1Zpc2libGU7PjtsPOWtpuWPt++8mjA0MTYxMDMxO288dD47Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7VmlzaWJsZTs+O2w85aeT5ZCN77ya5a2f5pmoO288dD47Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7VmlzaWJsZTs+O2w85a2m6Zmi77ya6K6h566X5py65a2m6ZmiO288dD47Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7VmlzaWJsZTs+O2w85LiT5Lia77yaO288dD47Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7VmlzaWJsZTs+O2w86K6h566X5py656eR5a2m5LiO5oqA5pyvO288dD47Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPOS4k+S4muaWueWQkTo7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7VmlzaWJsZTs+O2w86KGM5pS/54+t77ya6K6h56eRMTYwMTtvPHQ+Oz4+Oz47Oz47dDxAMDxwPHA8bDxWaXNpYmxlOz47bDxvPGY+Oz4+Oz47Ozs7Ozs7Ozs7Pjs7Pjt0PDtsPGk8MT47aTwzPjtpPDU+O2k8Nz47aTw5PjtpPDEzPjtpPDE1PjtpPDE3PjtpPDIxPjtpPDIzPjtpPDI0PjtpPDI1PjtpPDI3PjtpPDI5PjtpPDMxPjtpPDMzPjtpPDM1PjtpPDQzPjtpPDQ5PjtpPDUzPjtpPDU0Pjs+O2w8dDxwPHA8bDxWaXNpYmxlOz47bDxvPGY+Oz4+Oz47Oz47dDxAMDxwPHA8bDxWaXNpYmxlOz47bDxvPGY+Oz4+O3A8bDxzdHlsZTs+O2w8RElTUExBWTpub25lOz4+Pjs7Ozs7Ozs7Ozs+Ozs+O3Q8O2w8aTwxMz47PjtsPHQ8QDA8Ozs7Ozs7Ozs7Oz47Oz47Pj47dDxwPHA8bDxUZXh0O1Zpc2libGU7PjtsPOiHs+S7iuacqumAmui/h+ivvueoi+aIkOe7qe+8mjtvPHQ+Oz4+Oz47Oz47dDxAMDxwPHA8bDxQYWdlQ291bnQ7XyFJdGVtQ291bnQ7XyFEYXRhU291cmNlSXRlbUNvdW50O0RhdGFLZXlzOz47bDxpPDE+O2k8MT47aTwxPjtsPD47Pj47cDxsPHN0eWxlOz47bDxESVNQTEFZOmJsb2NrOz4+Pjs7Ozs7Ozs7Ozs+O2w8aTwwPjs+O2w8dDw7bDxpPDE+Oz47bDx0PDtsPGk8MD47aTwxPjtpPDI+O2k8Mz47aTw0PjtpPDU+O2k8Nj47PjtsPHQ8cDxwPGw8VGV4dDs+O2w8SlMxMDA2NTQ7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPOW+ruacuuWOn+eQhuS4juaOpeWPo+aKgOacr0U7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPOW/heS/ruivvjs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8NC4wOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDw0OTs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8Jm5ic3BcOzs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8Jm5ic3BcOzs+Pjs+Ozs+Oz4+Oz4+Oz4+O3Q8QDA8cDxwPGw8VmlzaWJsZTs+O2w8bzxmPjs+PjtwPGw8c3R5bGU7PjtsPERJU1BMQVk6bm9uZTs+Pj47Ozs7Ozs7Ozs7Pjs7Pjt0PEAwPDs7Ozs7Ozs7Ozs+Ozs+O3Q8QDA8cDxwPGw8VmlzaWJsZTs+O2w8bzxmPjs+PjtwPGw8c3R5bGU7PjtsPERJU1BMQVk6bm9uZTs+Pj47Ozs7Ozs7Ozs7Pjs7Pjt0PEAwPDs7Ozs7Ozs7Ozs+Ozs+O3Q8QDA8cDxwPGw8VmlzaWJsZTs+O2w8bzxmPjs+PjtwPGw8c3R5bGU7PjtsPERJU1BMQVk6bm9uZTs+Pj47Ozs7Ozs7Ozs7Pjs7Pjt0PEAwPHA8cDxsPFZpc2libGU7PjtsPG88Zj47Pj47cDxsPHN0eWxlOz47bDxESVNQTEFZOm5vbmU7Pj4+Ozs7Ozs7Ozs7Oz47Oz47dDxAMDxwPHA8bDxWaXNpYmxlOz47bDxvPGY+Oz4+Oz47Ozs7Ozs7Ozs7Pjs7Pjt0PEAwPHA8cDxsPFZpc2libGU7PjtsPG88Zj47Pj47cDxsPHN0eWxlOz47bDxESVNQTEFZOm5vbmU7Pj4+Ozs7Ozs7Ozs7Oz47Oz47dDxAMDxwPHA8bDxWaXNpYmxlOz47bDxvPGY+Oz4+O3A8bDxzdHlsZTs+O2w8RElTUExBWTpub25lOz4+Pjs7Ozs7Ozs7Ozs+Ozs+O3Q8QDA8O0AwPDs7QDA8cDxsPEhlYWRlclRleHQ7PjtsPOWIm+aWsOWGheWuuTs+Pjs7Ozs+O0AwPHA8bDxIZWFkZXJUZXh0Oz47bDzliJvmlrDlrabliIY7Pj47Ozs7PjtAMDxwPGw8SGVhZGVyVGV4dDs+O2w85Yib5paw5qyh5pWwOz4+Ozs7Oz47Ozs+Ozs7Ozs7Ozs7Pjs7Pjt0PHA8cDxsPFRleHQ7VmlzaWJsZTs+O2w85pys5LiT5Lia5YWxMjQx5Lq6O288Zj47Pj47Pjs7Pjt0PHA8cDxsPFZpc2libGU7PjtsPG88Zj47Pj47Pjs7Pjt0PHA8cDxsPFZpc2libGU7PjtsPG88Zj47Pj47Pjs7Pjt0PHA8cDxsPFZpc2libGU7PjtsPG88Zj47Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPFhJWU9VOz4+Oz47Oz47dDxwPHA8bDxJbWFnZVVybDs+O2w8Li9leGNlbC8wNDE2MTAzMS5qcGc7Pj47Pjs7Pjs+Pjt0PDtsPGk8Mz47PjtsPHQ8QDA8Ozs7Ozs7Ozs7Oz47Oz47Pj47Pj47Pj47PgngrQs2YPghl7fqVrAoG/qk6SsL");
        formBodyBuilder.add(YEAR, "2017-2018");
        formBodyBuilder.add(TERM, "2");
        formBodyBuilder.add(CLASS, CLASS_VALUE);
        formBodyBuilder.add(NAME4, VALUE4);
        Request request = new Request.Builder().url("http://222.24.62.120/xscjcx.aspx?xh=04161031&xm=%C3%CF%B3%BF&gnmkdm=N121605")
                .addHeader("Cookie", "ASP.NET_SessionId=a0psffq3leu23zvynm2ihp55")
                .addHeader("Referer", ACH_REFER)
                .post(formBodyBuilder.build())
                .build();
        Response response = okHttpClient.newCall(request).execute();
        InputStream inputStream = response.body().byteStream();
        String htmlStr = StreamUtils.copyToString(inputStream, Charset.forName("GBK"));
        Html html = new Html(htmlStr);
        //XPath解析
        String sec = html.xpath("//*[@id=\"Form1\"]/input[3]/@value").get();
        try {
            //BASE664解码
            String result = new String(Base64.getDecoder().decode(sec));
            String[] array = result.split("<Text;>;l<");

            for (int i = 3; i < array.length - array.length % 29; i += 29) {
                Achievement achievement = new Achievement();
                //设置学年
                achievement.setSchool_year(handelContents(array[i]));
                //设置学期
                achievement.setSemester(handelContents(array[i + 1]));
                //设置总成绩
                achievement.setAchievement(handelContents(array[i + 12]));
                //设置课程名字
                achievement.setClassname(handelContents(array[i + 3]));
                //设置绩点
                achievement.setPoint(handelContents(array[i + 7]).trim());
                //设置课程学分
                achievement.setCredit(handelContents(array[i + 6]));
                //设置课程性质
                achievement.setNature(handelContents(array[i + 4]));
                //设置平时成绩
                if (handelContents(array[i + 8]).trim().equals("&nbsp\\;")) {
                    achievement.setOrdinary("");
                } else {
                    achievement.setOrdinary(handelContents(array[i + 8]));
                }
                //设置期末成绩
                if (handelContents(array[i + 10]).equals("&nbsp\\;")) {
                    achievement.setFinalexam("");
                } else {
                    achievement.setFinalexam(handelContents(array[i + 10]));
                }
                achievements.add(achievement);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("耗时：" + (System.currentTimeMillis() - start));
        achievements.stream().forEach(System.out::println);
    }

    private static String handelContents(String res) {
        return res.split(";>>;>;;>;t<p<p<l")[0];
    }
}
