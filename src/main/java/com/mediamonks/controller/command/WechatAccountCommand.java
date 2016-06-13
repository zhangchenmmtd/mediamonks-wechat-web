package com.mediamonks.controller.command;

import com.mediamonks.domain.AccountType;

/**
 * Created by zhangchen on 16/6/2.
 */
public class WechatAccountCommand {
    private String guid;
    private String appId;
    private String appSecret;
    private String accessTokenUrl;
    private String jsSDKTicketUrl;
    private String clientServerID;
    private String clientServerSecrect;
    private String accountType;
    private boolean enabled;

    //This is use for wechat account form
    private String clientGuid;

    public WechatAccountCommand() {
    }


    public WechatAccountCommand(String appId, String appSecret, AccountType accountType, String guid,boolean enabled) {
        this.appId = appId;
        this.appSecret = appSecret;
        this.accountType = accountType.getAccountName();
        this.guid = guid;
        this.enabled = enabled;
    }


    public WechatAccountCommand(String guid, String appId, String appSecret, String accessTokenUrl, String jsSDKTicketUrl, String clientServerID, String clientServerSecrect, String accountType, boolean enabled) {
        this.guid = guid;
        this.appId = appId;
        this.appSecret = appSecret;
        this.accessTokenUrl = accessTokenUrl;
        this.jsSDKTicketUrl = jsSDKTicketUrl;
        this.clientServerID = clientServerID;
        this.clientServerSecrect = clientServerSecrect;
        this.accountType = accountType;
        this.enabled = enabled;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getGuid() {
        return guid;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getClientGuid() {
        return clientGuid;
    }

    public void setClientGuid(String clientGuid) {
        this.clientGuid = clientGuid;
    }

    public String getAccessTokenUrl() {
        return accessTokenUrl;
    }

    public void setAccessTokenUrl(String accessTokenUrl) {
        this.accessTokenUrl = accessTokenUrl;
    }

    public String getJsSDKTicketUrl() {
        return jsSDKTicketUrl;
    }

    public void setJsSDKTicketUrl(String jsSDKTicketUrl) {
        this.jsSDKTicketUrl = jsSDKTicketUrl;
    }

    public String getClientServerID() {
        return clientServerID;
    }

    public void setClientServerID(String clientServerID) {
        this.clientServerID = clientServerID;
    }

    public String getClientServerSecrect() {
        return clientServerSecrect;
    }

    public void setClientServerSecrect(String clientServerSecrect) {
        this.clientServerSecrect = clientServerSecrect;
    }
}
