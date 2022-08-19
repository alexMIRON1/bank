package com.bank.controller.command.impl;

import com.bank.controller.command.Command;
import com.bank.controller.command.exception.ClientBannedException;
import com.bank.controller.command.exception.WrongPasswordException;
import com.bank.controller.service.LoginService;
import com.bank.model.dao.ClientDao;
import com.bank.model.dao.impl.DaoEnum;
import com.bank.model.dao.impl.FactoryDao;
import com.bank.model.entity.Client;
import com.bank.model.entity.ClientStatus;
import com.bank.model.exception.client.ReadClientException;
import com.mysql.cj.log.Log;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class LoginCommand implements Command {
    private final static Logger LOG = LogManager.getLogger(LoginCommand.class);
    private final LoginService loginService;

    public LoginCommand(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        Map<String, String[]> params = request.getParameterMap();

        String login = params.get("login")[0];
        String password = params.get("password")[0];

        try {
            Client client = loginService.get(login,password);
            request.getSession().setAttribute("client", client);
            LOG.info(login + " successfully entered");
            if(login.equals("admin")){
                return "redirect:/bank/admin";
            }
            return "redirect:/bank/home";
        } catch (ReadClientException e) {
            // most likely such client does not exist (incorrect login) or problem in the dao
            LOG.debug(login + " is incorrect");
            return "redirect:/bank/login";
        } catch (WrongPasswordException e) {
            // real password and entered password are not equal
            LOG.debug("wrong password");
            return "redirect:/bank/login";
        } catch (ClientBannedException e) {
            // client status is blocked
            LOG.debug("status is blocked");
            return "redirect:/bank/login";
        }
    }

}
