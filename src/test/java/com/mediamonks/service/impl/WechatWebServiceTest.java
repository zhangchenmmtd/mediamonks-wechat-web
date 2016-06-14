package com.mediamonks.service.impl;

import com.mediamonks.domain.AccountType;
import com.mediamonks.domain.WeChatAccount;
import com.mediamonks.repository.WechatAccountRepository;
import com.mediamonks.utils.AES;
import com.mediamonks.webservices.command.ErrorJson;
import com.mediamonks.webservices.command.JSApiJson;
import com.mediamonks.webservices.command.JSONResponse;
import org.junit.Before;
import org.junit.Test;

import java.net.URLEncoder;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by zhangchen on 16/6/14.
 */
public class WechatWebServiceTest {
    WechatWebService wechatWebService = new WechatWebService();
    @Before
    public void setup(){
        wechatWebService.wechatAccountRepository = mock(WechatAccountRepository.class);
    }
    @Test
    public void jsapiConfig_wrongData() throws Exception {
        JSONResponse jsonResponse = wechatWebService.jsapiConfig(null);
        assertTrue(jsonResponse instanceof ErrorJson);

        jsonResponse = wechatWebService.jsapiConfig("");
        assertTrue(jsonResponse instanceof ErrorJson);

        jsonResponse = wechatWebService.jsapiConfig(" ");
        assertTrue(jsonResponse instanceof ErrorJson);

        jsonResponse = wechatWebService.jsapiConfig("asdsadsadsd");
        assertTrue(jsonResponse instanceof ErrorJson);
    }

    /**
     * Correct : {'wechatId':'b83b0ab2-68f1-4a54-82ae-4724f3646053','url':'http://www.google.com'}
     * Current: {'wechatDDDId':'b83b0ab2-68f1-4a54-82ae-4724f3646053','url':'http://www.google.com'}
     * @throws Exception
     */
    @Test
    public void jsapiConfig_missing_Json_parameter() throws Exception {
        String encrypt = AES.encrypt("{'wechatDDDId':'b83b0ab2-68f1-4a54-82ae-4724f3646053','url':'http://www.google.com'}");
        JSONResponse jsonResponse = wechatWebService.jsapiConfig(encrypt);
        assertTrue(jsonResponse instanceof ErrorJson);
    }

    @Test
    public void jsapiConfig_null_wechat_account() throws Exception {
        String encrypt = AES.encrypt("{'wechatId':'b83b0ab2-68f1-4a54-82ae-4724f3646053','url':'http://www.google.com'}");
        JSONResponse jsonResponse = wechatWebService.jsapiConfig(encrypt);
        assertTrue(jsonResponse instanceof ErrorJson);
        ErrorJson errorJson = (ErrorJson) jsonResponse;
        assertEquals(errorJson.getErrorMessage(),"Wechat ID is wrong, please double verify");
    }

    @Test
    public void jsapiConfig() throws Exception {
        String wechatGuid = "b83b0ab2-68f1-4a54-82ae-4724f3646053";
        String encrypt = AES.encrypt("{'wechatId':'" + wechatGuid + "','url':'http://www.google.com'}");
        WeChatAccount weChatAccount = dummyWeChatAccount();
        when(wechatWebService.wechatAccountRepository.findByGuid(wechatGuid)).thenReturn(weChatAccount);
        JSONResponse jsonResponse = wechatWebService.jsapiConfig(encrypt);
        assertTrue(jsonResponse instanceof JSApiJson);
        JSApiJson jsApiJson = (JSApiJson) jsonResponse;
        assertEquals(jsApiJson.getAppId(),"test");
        assertNotNull(jsApiJson.getNoncestr());
        assertNotNull(jsApiJson.getTimestamp());
        assertNotNull(jsApiJson.getSignature());

    }

    private WeChatAccount dummyWeChatAccount() {
        WeChatAccount weChatAccount = new WeChatAccount();
        weChatAccount.initial("test","test123");
        weChatAccount.changeAccountType(AccountType.DEVELOPERACCOUNT);
        return weChatAccount;
    }

}