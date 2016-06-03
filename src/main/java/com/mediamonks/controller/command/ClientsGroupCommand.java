package com.mediamonks.controller.command;

import com.mediamonks.domain.Client;

import java.util.List;

/**
 * Created by zhangchen on 16/6/2.
 */
public class ClientsGroupCommand {
    private List<ClientCommand> clientCommands;
    private int pages;
    private int currentpage;

    public ClientsGroupCommand(List<ClientCommand> clientCommands, int pages, int currentpage) {
        this.clientCommands = clientCommands;
        this.pages = pages;
        this.currentpage = currentpage;
    }

    public List<ClientCommand> getClientCommands() {
        return clientCommands;
    }

    public int getPages() {
        return pages;
    }

    public int getCurrentpage() {
        return currentpage;
    }
}
