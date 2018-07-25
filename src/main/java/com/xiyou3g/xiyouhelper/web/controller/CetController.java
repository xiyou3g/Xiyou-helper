package com.xiyou3g.xiyouhelper.web.controller;

import com.xiyou3g.xiyouhelper.common.ServerResponse;
import com.xiyou3g.xiyouhelper.model.Cetscore;
import com.xiyou3g.xiyouhelper.okhttp.CetOkHttp;
import com.xiyou3g.xiyouhelper.util.redis.PrefixEnum;
import com.xiyou3g.xiyouhelper.util.redis.SessionUtil;
import com.xiyou3g.xiyouhelper.web.service.impls.CetService;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.selector.Html;


import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;

import static com.xiyou3g.xiyouhelper.util.constant.CetConstant.*;

/**
 * @author du
 */
@RestController
public class CetController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CetOkHttp cetOkHttp;

    @Autowired
    private SessionUtil sessionUtil;

    /**
     * 转发验证码
     * equipment 设备
     *
     * @param response
     * @param equipmentId 设备Id，即设备号
     */
    @GetMapping("/cet_sys/validate_code")
    public void sendValidateCode(HttpServletResponse response, String equipmentId) {
        if (equipmentId == null) {
           response.setStatus(400);
           return;
        }
        // 如果sessionId == null，flag = false;
        boolean flag = false;
        // 从redis中取出sessionId
        String sessionId = sessionUtil.getSessionId(PrefixEnum.CET.getDesc(), equipmentId);
        // 发送验证码的请求
        Request request;
        // 如果sessionId不存在
        if (sessionId == null) {
            logger.info("sessionId不存在");
            request = new Request.Builder().url(VALIDATOR_URL).build();
        } else {
            // 将flag设置为true
            flag = true;
            logger.info("sessionId为" + sessionId);
            // 使用sessionId构建一个request
            request = new Request
                    .Builder()
                    .url(VALIDATOR_URL)
                    .addHeader("Cookie" ,CET_SESSION_KEY + "=" + sessionId)
                    .build();
        }
        // 发送请求
        Call call = cetOkHttp.getClient().newCall(request);
        try {
            // 得到响应
            Response validateCodeResponse = call.execute();

            // 如果flag为false将sessionId存储起来
            if (flag == false) {

                // 从header中拿出cookies
                String cookies = validateCodeResponse.header("Set-Cookie");
                // 解析拿到sessionId
                String sessionIdValue = cookies.substring(cookies.indexOf("=") + 1, cookies.indexOf(';'));

                // 存储在redis中
                sessionUtil.setSessionId(PrefixEnum.CET.getDesc(), equipmentId, sessionIdValue);
                logger.info("sessionId为" + sessionIdValue);
            }

            // 获取输入流
            InputStream is = validateCodeResponse.body().byteStream();
            // 使用工具类讲输入流复制到response的输入流中，完成转发
            StreamUtils.copy(is, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        @PostMapping("/cet_sys/login")
    public ServerResponse<Cetscore> simulationLogin(String zkzh, String name,
                                                  String validateCode, String equipmentId) {
        String sessionId = sessionUtil.getSessionId(PrefixEnum.CET.getDesc(), equipmentId);
        Html html = cetOkHttp.handlerSimulationLogin(zkzh, name, validateCode, sessionId);
        int status;
        CetService cetService  = new CetService();
        if (sessionId != null) {
            status = cetService.testLogin(html);
        } else {
            status = -1;
        }
        if (status == 0) {
            Cetscore cs = cetService.searchCet(html);
            return ServerResponse.createBySuccess("登录成功", cs);
        } else if (status == 1) {
            return ServerResponse.createByErrorMsg(LOGIN_ZKZH_NAME_ERROR);
        } else if (status == 2) {
            return ServerResponse.createByErrorMsg(LOGIN_VALIDATE_CODE_ERROR);
        } else {
            return ServerResponse.createByErrorMsg("未知情况");
        }
    }
}
