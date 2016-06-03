package com.mediamonks.controller;

import com.mediamonks.controller.command.ClientCommand;
import com.mediamonks.controller.command.WechatAccountCommand;
import com.mediamonks.controller.facade.ClientFacade;
import com.mediamonks.domain.AccountType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by zhangchen on 16/6/1.
 */
@RequestMapping("/")
@Controller
public class MainController {
    @Autowired
    private ClientFacade clientFacade;


    @RequestMapping(method = RequestMethod.GET)
    public String main(Model model, @RequestParam(value = "pageNumber",defaultValue = "0") String pageNumber){
        model.addAttribute("clients", clientFacade.getAll(pageNumber));
        return "home";
    }

    @RequestMapping(value = "/search/{client}", method = RequestMethod.GET)
    public String search(Model model, @PathVariable(value = "client") String client){
        model.addAttribute("client", clientFacade.search(client));
        return "home";
    }
    @RequestMapping(value = "/client/{clientguid}/edit", method = RequestMethod.GET)
    public String addClient(Model model, @PathVariable(value = "clientguid") String clientguid){
        model.addAttribute("client", clientFacade.get(clientguid));
        return "clientform";
    }
    @RequestMapping(value = "/client/update", method = RequestMethod.POST)
    public String updateClient(ClientCommand clientCommand){
        clientFacade.update(clientCommand);
        return "redirect:/";
    }


    @RequestMapping(value = "/client/{clientguid}/wechataccounts", method = RequestMethod.GET)
    public String wechatAccounts(Model model,@PathVariable(value = "clientguid") String clientguid){
        model.addAttribute("client", clientFacade.getWechatAccounts(clientguid));
        return "wechataccounts";
    }

    @RequestMapping(value = "/client/{clientguid}/wechat/{wechatguid}/edit", method = RequestMethod.GET)
    public String addEditWechatAccount(Model model,@PathVariable(value = "clientguid") String clientguid,@PathVariable(value = "wechatguid") String wechatguid){
        model.addAttribute("wechatAccount", clientFacade.wechatAccount(clientguid,wechatguid));
        model.addAttribute("accountTypes", AccountType.values());
        return "wechataccountform";
    }

    @RequestMapping(value = "/client/wechat//update", method = RequestMethod.POST)
    public String updateWechat(WechatAccountCommand wechatAccountCommand){
        String clientGuid = clientFacade.updateWechatAccount(wechatAccountCommand);
        return "redirect:/client/"+clientGuid+"/wechataccounts";
    }
}
