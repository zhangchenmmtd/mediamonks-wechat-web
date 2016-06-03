package com.mediamonks.controller.command;

import java.util.List;

/**
 * Created by zhangchen on 16/6/2.
 */
public class ClientCommand {
    private String guid;
    private String name;
    private int wechatAccounts;
    private boolean enabled;

    private List<WechatAccountCommand> wechatAccountCommands;

    public ClientCommand(String guid, String name, int wechatAccounts, boolean enabled) {
        this.guid = guid;
        this.name = name;
        this.wechatAccounts = wechatAccounts;
        this.enabled = enabled;
    }

    public ClientCommand(String guid, String name, List<WechatAccountCommand> wechatAccountCommands, boolean enabled) {
        this.guid = guid;
        this.name = name;
        this.wechatAccountCommands = wechatAccountCommands;
        this.enabled = enabled;
    }

    public ClientCommand() {
    }

    public String getGuid() {
        return guid;
    }

    public String getName() {
        return name;
    }

    public int getWechatAccounts() {
        return wechatAccounts;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WechatAccountCommand> getWechatAccountCommands() {
        return wechatAccountCommands;
    }
}
