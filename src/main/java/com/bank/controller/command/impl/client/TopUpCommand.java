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

public class TopUpCommand implements Command {
    private final CardsService cardsService;
    private static final Logger LOG = LogManager.getLogger(TopUpCommand.class);

    public TopUpCommand(CardsService cardsService) {
        this.cardsService = cardsService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Integer cardId = Integer.parseInt(request.getParameter("card"));
        Page page = (Page) request.getSession().getAttribute("page");
        int topUp;
        if(request.getParameter("top-up").equals("")){
            topUp = 0;
        }else{
            topUp = Integer.parseInt(request.getParameter("top-up"));
        }
        try {
            Card card = cardsService.read(cardId);
            cardsService.updateTopUp(card,topUp);
            LOG.info("card was successfully top up");
        } catch (ReadCardException e) {
            // fail to read card. perhaps such card does not exist
            LOG.debug("fail to obtain card");
            return "/error.jsp";
        } catch (UpdateCardException e) {
            // fail to update card. perhaps entered incorrect sum to top-up
            LOG.debug("fail to update card");
            return "/error.jsp";
        }
        return "redirect:/bank/home?page=" + page.getNumber();
    }
}
