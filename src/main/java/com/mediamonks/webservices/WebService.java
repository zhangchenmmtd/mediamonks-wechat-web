package com.mediamonks.webservices;


import com.mediamonks.service.WechatService;
import com.mediamonks.webservices.command.JSONResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by zhangchen on 16/6/1.
 */
@RequestMapping("/api")
@RestController
public class WebService {
    @Autowired
    private WechatService wechatService;

    @RequestMapping("/jsapi_config")
    JSONResponse jsapiConfig(@RequestParam String url, String wechatid) {
        return wechatService.jsapiConfig(url, wechatid);
    }

    @RequestMapping("/incomingmessage")
    @ResponseBody String message(HttpServletRequest httpServletRequest) throws IOException {
        wechatService.handleMessage(httpServletRequest.getInputStream());
        return "success";
    }


}
