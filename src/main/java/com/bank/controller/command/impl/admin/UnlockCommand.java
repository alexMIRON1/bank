package com.bank.controller.command.impl.admin;

import com.bank.controller.command.Command;
import com.bank.controller.service.admin.UnlockService;
import com.bank.model.entity.Client;
import com.bank.model.entity.ClientStatus;
import com.bank.model.exception.client.ReadClientException;
import com.bank.model.exception.client.UpdateClientException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class UnlockCommand implements Command {
    private final UnlockService unlockService;
    private static final Logger LOG = LogManager.getLogger(UnlockCommand.class);

    public UnlockCommand(UnlockService unlockService) {
        this.unlockService = unlockService;
    }


    @Override
    public String execute(HttpServletRequest request) {
        Integer clientId = Integer.parseInt(request.getParameter("id"));
        try {
            Client client = unlockService.read(clientId);
            unlockService.update(client);
        } catch (ReadClientException e) {
            // such user does not exist
            LOG.debug("user is not obtained");
            return "/error.jsp";
        } catch (UpdateClientException e) {
            // fail to update user
            LOG.debug("user is not updated");
            return "/error.jsp";
        }
        LOG.info("client was successfully unlocked");
        return "redirect:/bank/admin";
    }
}
