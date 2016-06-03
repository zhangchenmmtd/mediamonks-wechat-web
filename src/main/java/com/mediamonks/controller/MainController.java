package com.mediamonks.controller;

import com.mediamonks.controller.command.ClientCommand;
import com.mediamonks.controller.command.WechatAccountCommand;
import com.mediamonks.controller.facade.ClientFacade;
import com.mediamonks.domain.AccountType;
import com.mediamonks.utils.StringUtils;
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
    @RequestMapping(value = "/client/{clientguid}/updatestatus", method = RequestMethod.GET)
    public String updateClientStatus(@PathVariable(value = "clientguid") String clientguid){
        clientFacade.updateClientStatus(clientguid);
        return "redirect:/";
    }
    @RequestMapping(value = "/client/{clientguid}/edit", method = RequestMethod.GET)
    public String addClient(Model model, @PathVariable(value = "clientguid") String clientguid){
        model.addAttribute("client", clientFacade.get(clientguid));
        return "clientform";
    }
    @RequestMapping(value = "/client/update", method = RequestMethod.POST)
    public String updateClient(Model model,ClientCommand clientCommand){
        if(!clientFacade.update(clientCommand)){
            model.addAttribute("client", clientCommand);
            model.addAttribute("error", "Something is wrong, please make sure you fill in everything");
            return "clientform";
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/client/{clientguid}/wechat/{wechatguid}/updatestatus", method = RequestMethod.GET)
    public String updateStatus(Model model,@PathVariable(value = "clientguid") String clientguid,@PathVariable(value = "wechatguid") String wechatguid){
        clientFacade.updateWechatAccountStatus(clientguid, wechatguid);
        return "redirect:/client/"+clientguid+"/wechataccounts";
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
    public String updateWechat(Model model,WechatAccountCommand wechatAccountCommand){
        String clientGuid = clientFacade.updateWechatAccount(wechatAccountCommand);
        if(!StringUtils.hasText(clientGuid)){
            model.addAttribute("wechatAccount", wechatAccountCommand);
            model.addAttribute("accountTypes", AccountType.values());
            model.addAttribute("error", "Something is wrong, please make sure you fill in everything");
            return "wechataccountform";
        }
        return "redirect:/client/"+clientGuid+"/wechataccounts";
    }
}
