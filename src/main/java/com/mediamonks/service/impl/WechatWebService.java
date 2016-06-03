package com.mediamonks.service.impl;

import com.mediamonks.domain.WeChatAccount;
import com.mediamonks.repository.WechatAccountRepository;
import com.mediamonks.service.WechatService;
import com.mediamonks.utils.PropertiesUtils;
import com.mediamonks.utils.StringUtils;
import com.mediamonks.webservices.command.ErrorJson;
import com.mediamonks.webservices.command.JSApiJson;
import com.mediamonks.webservices.command.JSONResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.UUID;

/**
 * Created by zhangchen on 16/6/2.
 */
@Service("WechatWebService")
public class WechatWebService implements WechatService{
    @Autowired
    private WechatAccountRepository wechatAccountRepository;
    @Override
    public JSONResponse jsapiConfig(String url, String wechatid) {
        WeChatAccount weChatAccount= wechatAccountRepository.findByGuid(wechatid);

        if(weChatAccount == null){
            return ErrorJson
                    .newInstance()
                    .errorCode(PropertiesUtils.getInteger(ErrorJson.WECHATID))
                    .errorMessage(PropertiesUtils.get(ErrorJson.WECHATID_MESSAGE));
        }
        String ticket = weChatAccount.getTicket();
        String appId = weChatAccount.getAppId();
        String nonString = UUID.randomUUID().toString();
        long timestamp = System.currentTimeMillis() / 1000;

        String encodeString = "jsapi_ticket=" + ticket + "&noncestr=" + nonString + "&timestamp=" + timestamp + "&url=" + url;

        String signature = StringUtils.encode(encodeString);

        if(!StringUtils.hasText(signature)){
            return ErrorJson
                    .newInstance()
                    .errorCode(PropertiesUtils.getInteger(ErrorJson.JSAPISIGNATURE))
                    .errorMessage(PropertiesUtils.get(ErrorJson.JSAPISIGNATURE_MESSAGE));
        }
        return JSApiJson
                .newInstance()
                .signature(signature)
                .appId(appId)
                .noncestr(nonString)
                .timestamp(timestamp);
    }
}
