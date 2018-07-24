package com.xiyou3g.xiyouhelper.util.time;

import org.springframework.stereotype.Component;

/**
 * @Author: zeng
 * @Date: 2018/7/23 19:56
 */
@Component
public class TimeUtil {

    public String compTime(Long time1, Long time2) {

        return time1 >= time2 ? "正常" : "逾期";
    }
}
