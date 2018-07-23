package com.xiyou3g.xiyouhelper.regex;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * @Author: zeng
 * @Date: 2018/7/21 16:29
 */
public class RegexTest {

    @Test
    public void regex() {
        String str = "s_detail.jsp?sid=01h0126397";
        String pattern = "s_detail\\.jsp\\?sid=\\w{10}";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        System.out.println(m.matches());
    }

}
