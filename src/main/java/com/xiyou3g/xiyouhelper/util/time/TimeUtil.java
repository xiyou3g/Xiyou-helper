package com.xiyou3g.xiyouhelper.util.time;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: zeng
 * @Date: 2018/7/23 19:56
 */
@Component
public class TimeUtil {

    public String compTime(Long time1, Long time2) {

        return time1 >= time2 ? "正常" : "逾期";
    }

    public Integer calculateStatus(String time) {

        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy/MM/dd").parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return Math.toIntExact((date.getTime() - System.currentTimeMillis()) / 86400000L);
    }
}
