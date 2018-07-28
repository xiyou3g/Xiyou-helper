package com.xiyou3g.xiyouhelper.web.service.impls;

import com.xiyou3g.xiyouhelper.dao.TrainPlanMapper;
import com.xiyou3g.xiyouhelper.dao.TrainPlanStatusMapper;
import com.xiyou3g.xiyouhelper.model.TrainPlanMessage;
import com.xiyou3g.xiyouhelper.web.service.ITrainPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mengchen
 * @time 18-7-27 上午10:58
 */
@Service
public class TrainPlanService implements ITrainPlanService {

    @Autowired
    private TrainPlanMapper trainPlanMapper;

    @Autowired
    private TrainPlanStatusMapper trainPlanStatusMapper;

    @Override
    public boolean saveTrainPlanService(List<TrainPlanMessage> trainPlanMessages) {
        return trainPlanMapper.insertTrainPlans(trainPlanMessages) == 1;
    }

    @Override
    public boolean isExist(String major, String level) {
        return trainPlanStatusMapper.isExist(major, level) == 1;
    }

    @Override
    public boolean saveStatus(String major, String level) {
        return trainPlanStatusMapper.save(major, level) == 1;
    }

    @Override
    public List<TrainPlanMessage> getTrainPlanMessages(String major, String level, int term) {
        return trainPlanMapper.getTrainPlans(major, level, term);
    }


}
