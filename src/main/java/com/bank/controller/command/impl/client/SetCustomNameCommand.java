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
import java.sql.SQLException;

public class SetCustomNameCommand implements Command {
    private final CardsService cardsService;
    private static final Logger LOG = LogManager.getLogger(SetCustomNameCommand.class);

    public SetCustomNameCommand(CardsService cardsService) {
        this.cardsService = cardsService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Integer cardId = Integer.parseInt(request.getParameter("card"));
        Page page = (Page) request.getSession().getAttribute("page");
        try {
            Card card = cardsService.read(cardId);
            String customName = request.getParameter("customName");
            cardsService.updateCustomName(card, customName);
            LOG.info("name was successfully set");
            return "redirect:/bank/home?page=" + page.getNumber();
        } catch (ReadCardException e) {
            LOG.debug("fail to set name because card does not exist");
            return "/error.jsp";
        } catch (UpdateCardException e) {
            // incorrect params
            LOG.debug("fail to update card-->incorrect params");
            return "/error.jsp";
        }

    }
}
