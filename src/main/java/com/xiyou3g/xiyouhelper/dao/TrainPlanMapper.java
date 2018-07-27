package com.xiyou3g.xiyouhelper.dao;

import com.xiyou3g.xiyouhelper.model.TrainPlanMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author mengchen
 * @time 18-7-27 上午10:49
 */
public interface TrainPlanMapper {
    int saveTrainPlans(List<TrainPlanMessage> trainPlanMessages);

    List<TrainPlanMessage> getTrainPlans(@Param("major") String major,
                                         @Param("level") String level,
                                         @Param("term") int term);
}
