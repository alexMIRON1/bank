package com.bank.controller.command.impl.admin;

import com.bank.controller.command.Command;
import com.bank.controller.service.admin.UnblockService;
import com.bank.model.entity.Card;
import com.bank.model.entity.Page;
import com.bank.model.exception.card.ReadCardException;
import com.bank.model.exception.card.UpdateCardException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
public class UnblockCommand implements Command {
    private final UnblockService unblockService;
    private static final Logger LOG = LogManager.getLogger(UnblockCommand.class);

    public UnblockCommand(UnblockService unblockService) {
        this.unblockService = unblockService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Integer cardId = Integer.parseInt(request.getParameter("id"));
        Page page = (Page) request.getSession().getAttribute("page");
        try {
            Card card = unblockService.read(cardId);
            unblockService.update(card);
        } catch (ReadCardException e) {
            // such card does not exist
            LOG.debug("card is not obtained");
            return "/error.jsp";
        } catch (UpdateCardException e) {
            // fail to update card
            LOG.debug("card is not updated");
            return "/error.jsp";
        }
        LOG.debug("card was successfully unblocked");
        return "redirect:/bank/blockedCards?page=" + page.getNumber();
    }
}
