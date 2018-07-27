package com.xiyou3g.xiyouhelper.dao;

import org.apache.ibatis.annotations.Param;

/**
 * @author mengchen
 * @time 18-7-27 上午11:28
 */
public interface TrainPlanStatusMapper {
    int save(@Param("major") String major,
             @Param("level") String level);

    int isExist(@Param("major") String major,
                @Param("level") String level);
}
