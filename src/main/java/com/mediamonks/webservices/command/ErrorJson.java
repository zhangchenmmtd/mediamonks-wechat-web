package com.mediamonks.webservices.command;

/**
 * Created by zhangchen on 16/6/1.
 */
public class ErrorJson implements JSONResponse {
    public static final String JSAPISIGNATURE = "error.jsapi.signature.code";
    public static final String JSAPISIGNATURE_MESSAGE = "error.jsapi.signature.msg";

    public static final String WECHATID = "error.wechatid.code";
    public static final String WECHATID_MESSAGE = "error.wechatid.msg";

    private int errorCode;
    private String errorMessage;

    public static ErrorJson newInstance() {
        return new ErrorJson();
    }

    public ErrorJson errorCode(int errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public ErrorJson errorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
