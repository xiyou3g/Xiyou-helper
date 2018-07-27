package com.xiyou3g.xiyouhelper.web.service;

import com.xiyou3g.xiyouhelper.model.TrainPlanMessage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author mengchen
 * @time 18-7-27 上午10:46
 */
public interface ITrainPlanService {

    boolean saveTrainPlanService(List<TrainPlanMessage> trainPlanMessages);
    boolean isExist(String major, String level);
    boolean saveStatus(String major, String level);

    List<TrainPlanMessage> getTrainPlanMessages(String major, String level, int term);

}
