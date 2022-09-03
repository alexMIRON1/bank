package com.bank.controller.command.impl;

import com.bank.controller.command.Command;
import com.bank.controller.command.exception.ClientBannedException;
import com.bank.controller.command.exception.WrongPasswordException;
import com.bank.controller.service.AuthorizedService;
import com.bank.model.entity.Client;
import com.bank.model.exception.client.ReadClientException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class LoginCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(LoginCommand.class);
    private final AuthorizedService authorizedService;
    private static final String ERROR = "redirect:/bank/login";

    public LoginCommand(AuthorizedService authorizedService) {
        this.authorizedService = authorizedService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        Map<String, String[]> params = request.getParameterMap();

        String login = params.get("login")[0];
        String password = params.get("password")[0];

        try {
            Client client = authorizedService.get(login,password);
            request.getSession().setAttribute("role",client.getRole());
            request.getSession().setAttribute("client", client);
            LOG.debug(login + " successfully entered");
            if(login.equals("admin")){
                return "redirect:/bank/admin";
            }
            return "redirect:/bank/home";
        } catch (ReadClientException e) {
            // most likely such client does not exist (incorrect login) or problem in the dao
            LOG.debug(login + " is incorrect");
            return ERROR;
        } catch (WrongPasswordException e) {
            // real password and entered password are not equal
            LOG.debug("wrong password");
            return ERROR;
        } catch (ClientBannedException e) {
            // client status is blocked
            LOG.debug("status is blocked");
            return ERROR;
        }
    }

}
