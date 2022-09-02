package com.bank.controller.command.impl.client;

import com.bank.controller.command.Command;
import com.bank.controller.service.client.CardsService;
import com.bank.model.entity.Card;
import com.bank.model.entity.Page;
import com.bank.model.exception.card.ReadCardException;
import com.bank.model.exception.card.UpdateCardException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class BlockCardCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(BlockCardCommand.class);
    private final CardsService cardsService;

    public BlockCardCommand(CardsService cardsService) {
        this.cardsService = cardsService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        Integer cardId = Integer.parseInt(request.getParameter("card"));
        Page page = (Page) request.getSession().getAttribute("page");
        try {
            Card card = cardsService.read(cardId);
            cardsService.updateStatus(card);
            LOG.debug("card was successfully update");
        } catch (ReadCardException e) {
            // such card does not exist
            LOG.debug("fail to block card because does not exist");
            return "/error.jsp";
        } catch (UpdateCardException e) {
            // incorrect params
            LOG.debug("fail to update card");
            return "/error.jsp";
        }
        return "redirect:/bank/home?page=" + page.getNumber() ;
    }
}
