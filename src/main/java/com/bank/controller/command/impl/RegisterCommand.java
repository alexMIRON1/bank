package com.bank.controller.command.impl;

import com.bank.controller.command.Command;
import com.bank.controller.service.AuthorizedService;
import com.bank.model.entity.Client;
import com.bank.model.exception.client.CreateClientException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class RegisterCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(RegisterCommand.class);
    private final AuthorizedService authorizedService;

    public RegisterCommand(AuthorizedService authorizedService) {
        this.authorizedService = authorizedService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();

        String login = params.get("login")[0];
        String password = params.get("password")[0];
        String passwordConfirm = params.get("confirm_password")[0];

        try {
            Client client = new Client();
            authorizedService.create(client,login,password,passwordConfirm);
            LOG.info( login + " successfully creates");
            request.getSession().setAttribute("client", client);
            return "redirect:/bank/login";
        } catch (CreateClientException e) {
            // most likely such login already exist or problem in the dao
            LOG.debug("Such login already exist or password not equals");
            return "redirect:/bank/register";
        }
    }

}
