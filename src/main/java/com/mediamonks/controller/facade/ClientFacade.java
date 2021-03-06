package com.mediamonks.controller.facade;


import com.mediamonks.controller.command.ClientCommand;
import com.mediamonks.controller.command.ClientsGroupCommand;
import com.mediamonks.controller.command.WechatAccountCommand;
import com.mediamonks.domain.AccountType;
import com.mediamonks.domain.Client;
import com.mediamonks.domain.WeChatAccount;
import com.mediamonks.repository.ClientRepository;
import com.mediamonks.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangchen on 16/6/2.
 */
@Transactional(propagation = Propagation.REQUIRED)
@Service("clientFacade")
public class ClientFacade {
    @Autowired
    protected ClientRepository clientRepository;

    public ClientsGroupCommand getAll(String pageNumber) {
        int page = Integer.valueOf(pageNumber);
        Page<Client> clientPage = clientRepository.findAll(new PageRequest(page, 25, new Sort(Sort.Direction.DESC, "id")));
        List<Client> clients = clientPage.getContent();
        List<ClientCommand> clientCommands = new ArrayList<>();
        for (Client client : clients) {
            List<WeChatAccount> weChatAccounts = client.getWeChatAccounts();
            clientCommands.add(new ClientCommand(client.getGuid(),client.getName(), weChatAccounts == null?0:weChatAccounts.size(),client.isEnabled()));
        }
        return new ClientsGroupCommand(clientCommands, clientPage.getTotalPages(), page);

    }

    public ClientCommand get(String clientguid) {
        Client client = clientRepository.findByGuid(clientguid);
        if(client == null){
            return new ClientCommand();
        }
        List<WeChatAccount> weChatAccounts = client.getWeChatAccounts();
        return new ClientCommand(client.getGuid(), client.getName(), weChatAccounts == null ? 0 : weChatAccounts.size(), client.isEnabled());
    }


    public boolean update(ClientCommand clientCommand) {
        String guid = clientCommand.getGuid();
        Client client = clientRepository.findByGuid(guid);
        if(client == null){
            client = new Client();
        }
        if(!StringUtils.hasText(clientCommand.getName())){
            return false;
        }
        client.changeName(clientCommand.getName());
        clientRepository.save(client);
        return true;
    }

    public ClientCommand getWechatAccounts(String clientguid) {
        Client client = clientRepository.findByGuid(clientguid);

        List<WeChatAccount> weChatAccounts = client.getWeChatAccounts();

        ArrayList<WechatAccountCommand> wechatAccountCommands = new ArrayList<WechatAccountCommand>();
        if (weChatAccounts != null) {
            for (WeChatAccount weChatAccount : weChatAccounts) {
                wechatAccountCommands.add(new WechatAccountCommand(weChatAccount.getAppId(),weChatAccount.getAppSecret(),weChatAccount.getAccountType(),weChatAccount.getGuid(),weChatAccount.isEnabled()));
            }
        }
        return new ClientCommand(client.getGuid(), client.getName(), wechatAccountCommands,client.isEnabled());
    }


    public WechatAccountCommand wechatAccount(String clientguid, String wechatguid) {
        Client client = clientRepository.findByGuid(clientguid);
        WeChatAccount weChatAccount = client.getWeChatAccount(wechatguid);
        if(weChatAccount == null){
            WechatAccountCommand wechatAccountCommand = new WechatAccountCommand();
            wechatAccountCommand.setClientGuid(clientguid);
            return wechatAccountCommand;
        }
        WechatAccountCommand wechatAccountCommand = new WechatAccountCommand(
                weChatAccount.getGuid(),
                weChatAccount.getAppId(),
                weChatAccount.getAppSecret(),
                weChatAccount.getAccessTokenUrl(),
                weChatAccount.getJsSDKTicketUrl(),
                weChatAccount.getClientServerID(),
                weChatAccount.getClientServerSecrect(),
                weChatAccount.getAccountType().getAccountName(),
                weChatAccount.isEnabled()
        );
        wechatAccountCommand.setClientGuid(clientguid);
        return wechatAccountCommand;

    }

    public String updateWechatAccount(WechatAccountCommand wechatAccountCommand) {
        String accountType = wechatAccountCommand.getAccountType();
        String guid = wechatAccountCommand.getGuid();
        String appId = wechatAccountCommand.getAppId();
        String appSecret = wechatAccountCommand.getAppSecret();
        String clientGuid = wechatAccountCommand.getClientGuid();
        String accessTokenUrl = wechatAccountCommand.getAccessTokenUrl();
        String jsSDKTicketUrl = wechatAccountCommand.getJsSDKTicketUrl();
        String clientServerID = wechatAccountCommand.getClientServerID();
        String clientServerSecrect = wechatAccountCommand.getClientServerSecrect();

        Client client = clientRepository.findByGuid(clientGuid);
        if(client == null){
            return null;
        }
        if(!StringUtils.hasText(appId) || !StringUtils.hasText(appSecret)){
            return null;
        }
        WeChatAccount weChatAccount = client.getWeChatAccount(guid);
        if(weChatAccount == null){
            weChatAccount = new WeChatAccount();
            client.addWeChatAccount(weChatAccount);
        }

        weChatAccount.initial(appId,appSecret);
        weChatAccount.changeClientServerInfo(clientServerID, clientServerSecrect);
        weChatAccount.changeUrls(accessTokenUrl, jsSDKTicketUrl);
        try {
            weChatAccount.changeAccountType(AccountType.valueOf(accountType));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return clientGuid;

    }

    public void updateWechatAccountStatus(String clientguid, String wechatguid) {
        Client client = clientRepository.findByGuid(clientguid);
        if(client!=null){
            WeChatAccount weChatAccount = client.getWeChatAccount(wechatguid);
            if(weChatAccount != null){
                weChatAccount.updateStatus();
            }
        }
    }

    public void updateClientStatus(String clientguid) {
        Client client = clientRepository.findByGuid(clientguid);
        if(client!=null){
            client.updateStatus();
        }
    }
}
