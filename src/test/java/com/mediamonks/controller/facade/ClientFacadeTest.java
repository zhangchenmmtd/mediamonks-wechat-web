package com.mediamonks.controller.facade;

import com.mediamonks.controller.command.ClientCommand;
import com.mediamonks.controller.command.ClientsGroupCommand;
import com.mediamonks.controller.command.WechatAccountCommand;
import com.mediamonks.domain.AccountType;
import com.mediamonks.domain.Client;
import com.mediamonks.domain.WeChatAccount;
import com.mediamonks.repository.ClientRepository;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;

/**
 * Created by zhangchen on 16/6/14.
 */
public class ClientFacadeTest{
    private ClientFacade clientFacade;

    @Before
    public void setUp() throws Exception {
        clientFacade = new ClientFacade();
        clientFacade.clientRepository = mock(ClientRepository.class);

    }

    @Test
    public void getAll() throws Exception {
        String pageNumber = "0";
        ArrayList<Client> clientList = dummyClients();
        PageImpl<Client> clients = new PageImpl<Client>(clientList);
        PageRequest pageable = new PageRequest(Integer.valueOf(pageNumber), 25, new Sort(Sort.Direction.DESC, "id"));
        when(clientFacade.clientRepository.findAll(pageable)).thenReturn(clients);
        ClientsGroupCommand clientsGroupCommand = clientFacade.getAll(pageNumber);
        verify(clientFacade.clientRepository).findAll(pageable);
        assertEquals(clientsGroupCommand.getClientCommands().size(),2);
        assertEquals(clientsGroupCommand.getClientCommands().get(0).getName(),"test1");
        assertEquals(clientsGroupCommand.getClientCommands().get(1).getName(),"test2");

    }

    private ArrayList<Client> dummyClients() {
        ArrayList<Client> clients = new ArrayList<>();
        Client clientOne = new Client();
        Client clientTwo = new Client();
        clientOne.changeName("test1");
        clientTwo.changeName("test2");

        WeChatAccount weChatAccountOne = new WeChatAccount();
        WeChatAccount weChatAccountTwo = new WeChatAccount();
        WeChatAccount weChatAccountThree = new WeChatAccount();
        WeChatAccount weChatAccountFour = new WeChatAccount();

        weChatAccountOne.initial("test","test");
        weChatAccountTwo.initial("test","test");
        weChatAccountThree.initial("test","test");
        weChatAccountFour.initial("test","test");

        weChatAccountOne.changeAccountType(AccountType.DEVELOPERACCOUNT);
        weChatAccountTwo.changeAccountType(AccountType.DEVELOPERACCOUNT);
        weChatAccountThree.changeAccountType(AccountType.DEVELOPERACCOUNT);
        weChatAccountFour.changeAccountType(AccountType.DEVELOPERACCOUNT);

        clientOne.addWeChatAccount(weChatAccountOne);
        clientOne.addWeChatAccount(weChatAccountTwo);

        clientTwo.addWeChatAccount(weChatAccountThree);
        clientTwo.addWeChatAccount(weChatAccountFour);

        clients.add(clientOne);
        clients.add(clientTwo);
        return clients;
    }

    @Test
    public void get() throws Exception {
        String clientguid = "test guid";
        Client client = dummyClients().get(0);
        when(clientFacade.clientRepository.findByGuid(clientguid)).thenReturn(client);
        ClientCommand clientCommand = clientFacade.get(clientguid);
        verify(clientFacade.clientRepository).findByGuid(clientguid);
        assertEquals(client.getGuid(), clientCommand.getGuid());
    }

    @Test
    public void update_newClient_no_name() throws Exception {
        ClientCommand clientCommand = new ClientCommand();
        String guid = "test guid";
        clientCommand.setGuid(guid);
        when(clientFacade.clientRepository.findByGuid(guid)).thenReturn(null);
        assertFalse(clientFacade.update(clientCommand));
        verify(clientFacade.clientRepository).findByGuid(guid);
    }

    @Test
    public void update_newClient_has_name() throws Exception {
        ClientCommand clientCommand = new ClientCommand();
        String guid = "test guid";
        clientCommand.setGuid(guid);
        clientCommand.setName("test name");
        Client client = dummyClients().get(0);
        when(clientFacade.clientRepository.findByGuid(guid)).thenReturn(client);
        assertTrue(clientFacade.update(clientCommand));
        verify(clientFacade.clientRepository).findByGuid(guid);
    }

    @Test
    public void getWechatAccounts() throws Exception {
        String guid = "test";
        Client client = dummyClients().get(0);
        when(clientFacade.clientRepository.findByGuid(guid)).thenReturn(client);
        ClientCommand clientCommand = clientFacade.getWechatAccounts(guid);
        assertEquals(clientCommand.getWechatAccountCommands().size(),2);
        assertEquals(clientCommand.getWechatAccountCommands().get(0).getAppId(),"test");
    }

    @Test
    public void wechatAccount_new() throws Exception {
        String wechatguid = "wechatguid";
        String clientguid = "clientguid";
        Client client = dummyClients().get(0);
        when(clientFacade.clientRepository.findByGuid(clientguid)).thenReturn(client);
        WechatAccountCommand wechatAccountCommand = clientFacade.wechatAccount(clientguid, wechatguid);
        assertEquals(wechatAccountCommand.getClientGuid(), clientguid);
    }

    @Test
    public void wechatAccount_exist() throws Exception {
        String clientguid = "clientguid";
        Client client = dummyClients().get(0);
        String wechatguid = client.getWeChatAccounts().get(0).getGuid();
        when(clientFacade.clientRepository.findByGuid(clientguid)).thenReturn(client);
        WechatAccountCommand wechatAccountCommand = clientFacade.wechatAccount(clientguid, wechatguid);
        assertEquals(wechatAccountCommand.getClientGuid(), clientguid);
    }

    @Test
    public void updateWechatAccount_noClient() throws Exception {
        WechatAccountCommand wechatAccountCommand = new WechatAccountCommand();
        String clientGuid = "test";
        wechatAccountCommand.setClientGuid(clientGuid);

        when(clientFacade.clientRepository.findByGuid(clientGuid)).thenReturn(null);
        assertNull(clientFacade.updateWechatAccount(wechatAccountCommand));
    }

    @Test
    public void updateWechatAccount_hasClient_noAppId() throws Exception {
        WechatAccountCommand wechatAccountCommand = new WechatAccountCommand();
        wechatAccountCommand.setGuid("test123");
        String clientGuid = "test";
        wechatAccountCommand.setClientGuid(clientGuid);

        Client client = dummyClients().get(0);
        when(clientFacade.clientRepository.findByGuid(clientGuid)).thenReturn(client);
        assertNull(clientFacade.updateWechatAccount(wechatAccountCommand));
    }

    @Test
    public void updateWechatAccount_noAccountType() throws Exception {
        WechatAccountCommand wechatAccountCommand = new WechatAccountCommand();
        wechatAccountCommand.setAppId("test123");
        wechatAccountCommand.setAppSecret("1test123");
        wechatAccountCommand.setGuid("test123");
        String clientGuid = "test";
        wechatAccountCommand.setClientGuid(clientGuid);

        Client client = dummyClients().get(0);
        when(clientFacade.clientRepository.findByGuid(clientGuid)).thenReturn(client);
        assertNull(clientFacade.updateWechatAccount(wechatAccountCommand));
    }

    @Test
    public void updateWechatAccount() throws Exception {
        WechatAccountCommand wechatAccountCommand = new WechatAccountCommand();
        wechatAccountCommand.setAccountType(AccountType.DEVELOPERACCOUNT.toString());
        wechatAccountCommand.setAppId("test123");
        wechatAccountCommand.setAppSecret("1test123");
        wechatAccountCommand.setGuid("test123");
        String clientGuid = "test";
        wechatAccountCommand.setClientGuid(clientGuid);

        Client client = dummyClients().get(0);
        when(clientFacade.clientRepository.findByGuid(clientGuid)).thenReturn(client);
        assertEquals(clientFacade.updateWechatAccount(wechatAccountCommand),clientGuid);
    }

    @Test
    public void updateWechatAccountStatus() throws Exception {
        String clientGuid = "test";
        Client client = dummyClients().get(0);

        String wechatGuid = client.getWeChatAccounts().get(0).getGuid();
        boolean status = client.getWeChatAccounts().get(0).isEnabled();
        when(clientFacade.clientRepository.findByGuid(clientGuid)).thenReturn(client);
        clientFacade.updateWechatAccountStatus(clientGuid, wechatGuid);

        assertEquals(client.getWeChatAccount(wechatGuid).isEnabled(),!status);
    }

    @Test
    public void updateClientStatus() throws Exception {
        String clientGuid = "test";
        Client client = dummyClients().get(0);
        boolean status = client.isEnabled();
        when(clientFacade.clientRepository.findByGuid(clientGuid)).thenReturn(client);
        clientFacade.updateClientStatus(clientGuid);

        assertEquals(!status,client.isEnabled());
    }

}