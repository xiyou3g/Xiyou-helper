package com.xiyou3g.xiyouhelper.parse;

import com.xiyou3g.xiyouhelper.model.TrainPlanMessage;
import com.xiyou3g.xiyouhelper.web.service.ITrainPlanService;
import okhttp3.OkHttpClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

/**
 * @author mengchen
 * @time 18-7-26 下午7:45
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TrainPlanParseTest {

    @Autowired
    private ITrainPlanService service;

    @Autowired
    private OkHttpClient okHttpClient;

    @Autowired
    private TrainPlanParse parse;

    @Test
    public void testGetTrainPlan() throws IOException {
        Long start = System.currentTimeMillis();

        List<TrainPlanMessage> messageses = parse.parseTrainPlanMessages("04162106", "和书诚", "a0psffq3leu23zvynm2ihp55");
        System.out.println("耗时：" + (System.currentTimeMillis() - start));
        messageses.stream().forEach((trainPlanMessage -> {
            trainPlanMessage.setMajor("计算机科学与技术");
            trainPlanMessage.setLevel("2016");
        }));
        service.saveTrainPlanService(messageses);
    }

    @Test
    public void testGetHidden() throws IOException {
        Long start = System.currentTimeMillis();
        parse.getHidden("04162106", "和书诚", "a0psffq3leu23zvynm2ihp55");
        System.out.println("耗时：" + (System.currentTimeMillis() - start));
    }


}
