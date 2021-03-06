package com.mediamonks.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mediamonks.domain.WeChatAccount;
import com.mediamonks.repository.WechatAccountRepository;
import com.mediamonks.service.WechatService;
import com.mediamonks.utils.AES;
import com.mediamonks.utils.MessageUtil;
import com.mediamonks.utils.PropertiesUtils;
import com.mediamonks.utils.StringUtils;
import com.mediamonks.webservices.command.ErrorJson;
import com.mediamonks.webservices.command.JSApiJson;
import com.mediamonks.webservices.command.JSONResponse;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.ServletInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.UUID;

/**
 * Created by zhangchen on 16/6/2.
 */
@Service("WechatWebService")
public class WechatWebService implements WechatService{
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    protected WechatAccountRepository wechatAccountRepository;


    @Override
    public void handleMessage(ServletInputStream inputStream) {
        try {
            Map<String, String> incomingMessage = MessageUtil.xmlToMap(inputStream);

            String toUserName = incomingMessage.get("ToUserName");
            String fromUserName = incomingMessage.get("FromUserName");
            String msgType = incomingMessage.get("MsgType");
            if("event".equals(msgType)){
                String event = incomingMessage.get("Event");
                if(event.equals("subscribe")){

                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (DocumentException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public JSONResponse jsapiConfig(String data) {
        String decryptData = null;
        try {
            decryptData = AES.decrypt(data);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return errorResponse(ErrorJson.JSAPISIGNATURE,ErrorJson.JSAPISIGNATURE_MESSAGE);
        }
        if(!StringUtils.hasText(decryptData)){
            return errorResponse(ErrorJson.JSAPISIGNATURE,ErrorJson.JSAPISIGNATURE_MESSAGE);
        }
        JSONObject jsonObject = null;
        try {
            jsonObject = JSONObject.parseObject(decryptData);
        } catch (Exception e) {
            return errorResponse(ErrorJson.JSAPISIGNATURE,ErrorJson.JSAPISIGNATURE_MESSAGE);
        }

        String wechatId = jsonObject.getString("wechatId");
        String url = jsonObject.getString("url");

        WeChatAccount weChatAccount= wechatAccountRepository.findByGuid(wechatId);

        if(weChatAccount == null){
            return errorResponse(ErrorJson.WECHATID, ErrorJson.WECHATID_MESSAGE);
        }
        String ticket = weChatAccount.getTicket();
        String appId = weChatAccount.getAppId();
        String nonString = UUID.randomUUID().toString();
        long timestamp = System.currentTimeMillis() / 1000;

        String encodeString =
                        "jsapi_ticket=" + ticket +
                        "&noncestr=" + nonString +
                        "&timestamp=" + timestamp +
                        "&url=" + url;

        String signature = StringUtils.encode(encodeString);

        if(!StringUtils.hasText(signature)){
            return errorResponse(ErrorJson.JSAPISIGNATURE,ErrorJson.JSAPISIGNATURE_MESSAGE);
        }
        return JSApiJson
                .newInstance()
                .signature(signature)
                .appId(appId)
                .noncestr(nonString)
                .timestamp(timestamp);
    }

    private JSONResponse errorResponse(String code,String message) {
        return ErrorJson
                .newInstance()
                .errorCode(PropertiesUtils.getInteger(code))
                .errorMessage(PropertiesUtils.get(message));
    }
}
