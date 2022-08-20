package com.bank.controller.command.impl.admin;

import com.bank.controller.command.Command;
import com.bank.controller.service.admin.ControlUserService;
import com.bank.model.entity.Client;
import com.bank.model.exception.client.ReadClientException;
import com.bank.model.exception.client.UpdateClientException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class LockCommand implements Command {
    private final ControlUserService controlUserService;
    private static final Logger LOG = LogManager.getLogger(LockCommand.class);

    public LockCommand(ControlUserService controlUserService) {
        this.controlUserService = controlUserService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Integer clientId = Integer.parseInt(request.getParameter("id"));
        try {
            Client client = controlUserService.read(clientId);
            controlUserService.update(client);
        } catch (ReadClientException e) {
            // such user does not exist
            LOG.debug("user is not obtained");
            return "/error.jsp";
        } catch (UpdateClientException e) {
            // fail to update user
            LOG.debug("user is not updated");
            return "/error.jsp";
        }
        LOG.info("client was successfully locked");
        return "redirect:/bank/admin";
    }
}
