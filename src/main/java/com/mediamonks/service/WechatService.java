package com.mediamonks.service;

import com.mediamonks.webservices.command.JSONResponse;

import javax.servlet.ServletInputStream;

/**
 * Created by zhangchen on 16/6/2.
 */
public interface WechatService {
    JSONResponse jsapiConfig(String url, String wechatid);
    void handleMessage(ServletInputStream inputStream);
}
