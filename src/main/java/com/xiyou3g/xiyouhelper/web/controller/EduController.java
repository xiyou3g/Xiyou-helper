package com.xiyou3g.xiyouhelper.web.controller;

import com.xiyou3g.xiyouhelper.common.ServerResponse;
import com.xiyou3g.xiyouhelper.eventlistener.LoginSuccessEvent;
import com.xiyou3g.xiyouhelper.eventlistener.LoginSuccessPublisher;
import com.xiyou3g.xiyouhelper.model.dto.SimpleUser;
import com.xiyou3g.xiyouhelper.okhttp.EduLoginParse;
import com.xiyou3g.xiyouhelper.util.redis.PrefixEnum;
import com.xiyou3g.xiyouhelper.util.redis.SessionUtil;
import com.xiyou3g.xiyouhelper.web.service.IUserService;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

import static com.xiyou3g.xiyouhelper.util.constant.CommonConstant.XYE_SESSION_KEY;
import static com.xiyou3g.xiyouhelper.util.constant.EduConstant.*;


/**
 * mengchen
 * 18-7-20 下午7:40
 */
@RestController
public class EduController {

    private Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private SessionUtil sessionUtil;

    @Autowired
    private IUserService userService;

    @Autowired
    LoginSuccessPublisher loginSuccessPublisher;

    @Autowired
    private OkHttpClient okHttpClient;

    /**
     * 转发验证码
     * equipment 设备
     *
     * @param response
     * @param equipmentId 设备Id，即设备号
     */
    @GetMapping("/xiyou_edu_sys/validate_code")
    public void sendValidateCode(HttpServletResponse response, String equipmentId) {
        OkHttpClient client = new OkHttpClient();
        response.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9," +
                "image/webp,image/apng,*/*;q=0.8");
        // 如果sessionId == null，flag = false;
        boolean flag = false;
        // 从redis中取出sessionId
        String sessionId = sessionUtil.getSessionId(PrefixEnum.XYEDU.getDesc(), equipmentId);
        // 发送验证码的请求
        Request request;
        // 如果sessionId不存在
        if (sessionId == null) {
            logger.info("sessionId不存在");
            request = new Request.Builder().url(VALIDATE_URL).build();
        } else {
            // 将flag设置为true
            flag = true;
            logger.info("sessionId为" + sessionId);
            // 使用sessionId构建一个request
            request = new Request.Builder().url(VALIDATE_URL).addHeader("Cookie", XYE_SESSION_KEY + "=" + sessionId).build();
        }
        // 发送请求
        Call call = client.newCall(request);
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
                sessionUtil.setSessionId(PrefixEnum.XYEDU.getDesc(), equipmentId, sessionIdValue);
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

    @PostMapping("/xiyou_edu_sys/login")
    public ServerResponse<String> simulationLogin(String studentNum, String password,
                                                  String validateCode, String equipmentId, HttpSession session) {
        String sessionId = sessionUtil.getSessionId(PrefixEnum.XYEDU.getDesc(), equipmentId);
        System.out.println(sessionId);
        int status;
        if (sessionId != null) {
            EduLoginParse parse = new EduLoginParse(okHttpClient);
            status = parse.handlerSimulationLogin(studentNum, password, validateCode, sessionId);
        } else {
            status = -1;
        }
        if (status == 0) {
            // 如果数据库中有信息就不用爬取了
            if (!userService.isExist(studentNum)) {
                LoginSuccessEvent event = new LoginSuccessEvent(studentNum, sessionId);

                loginSuccessPublisher.publishEvent(event);
            }
            SimpleUser user = new SimpleUser(studentNum, userService.getNameBySid(studentNum));
            session.setAttribute("user", user);
            sessionUtil.setSessionIdLong(PrefixEnum.XYEDU.getDesc(), studentNum, sessionId);
            sessionUtil.removeSessionId(PrefixEnum.XYEDU.getDesc(), equipmentId);
            return ServerResponse.createBySuccessMsg("登录成功");
        } else if (status == 1) {
            return ServerResponse.createByErrorMsg(LOGIN_PASS_ERROR);
        } else if (status == 2) {
            return ServerResponse.createByErrorMsg(LOGIN_USER_NOTFOUND);
        } else if (status == 3) {
            return ServerResponse.createByErrorMsg(LOGIN_VALIDATE_CODE_ERROR);
        } else {
            return ServerResponse.createByErrorMsg("未知情况");
        }

    }


}
