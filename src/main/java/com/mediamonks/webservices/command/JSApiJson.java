package com.mediamonks.webservices.command;

import java.util.List;

/**
 * Created by zhangchen on 16/6/1.
 */
public class JSApiJson implements JSONResponse{
    private String signature;
    private long timestamp;
    private String noncestr;
    private String appId;
    private String[] jsApiList = new String[]{
            "checkJsApi",
            "onMenuShareTimeline",
            "onMenuShareAppMessage",
            "showOptionMenu",

    };


    public static JSApiJson newInstance(){
        return new JSApiJson();
    }

    public JSApiJson signature(String signature){
        this.signature = signature;
        return this;
    }
    public JSApiJson timestamp(long timestamp){
        this.timestamp = timestamp;
        return this;
    }

    public JSApiJson noncestr(String noncestr){
        this.noncestr = noncestr;
        return this;
    }
    public JSApiJson appId(String appId){
        this.appId = appId;
        return this;
    }

    public String getSignature() {
        return signature;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public String getAppId() {
        return appId;
    }

    public String[] getJsApiList() {
        return jsApiList;
    }
}
