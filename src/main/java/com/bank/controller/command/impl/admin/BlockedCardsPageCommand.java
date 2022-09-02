package com.bank.controller.command.impl.admin;

import com.bank.controller.command.Command;
import com.bank.controller.service.admin.AdminPageService;
import com.bank.model.entity.Card;
import com.bank.model.entity.Page;
import com.bank.model.exception.card.ReadCardException;
import com.bank.model.exception.client.ReadClientException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class BlockedCardsPageCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(BlockedCardsPageCommand.class);
    private final AdminPageService adminPageService;

    public BlockedCardsPageCommand(AdminPageService adminPageService) {
        this.adminPageService = adminPageService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Page page = new Page();
        page.setNumberPage(1);
        page.setRecords(5);
        if(request.getParameter("page")!=null){
            page.setNumberPage(Integer.parseInt(request.getParameter("page")));
            LOG.debug("page = " + page.getNumber());
        }
        try {
           List<Card> cards = adminPageService.getCards(page);
           for (Card card: cards) {
                card.setClient(adminPageService.fillClient(card.getClient().getId()));
            }
            int noOfRecords = adminPageService.getNoOfRecordsCard();
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / page.getRecords());
            request.getSession().setAttribute("page",page);
            request.getSession().setAttribute("noOfPages", noOfPages);
            request.setAttribute("unlockCards", cards);
        } catch (ReadCardException e) {
            // cards are not read. error in the dao
            LOG.debug("cards are not obtained");
            return "/error.jsp";
        } catch (ReadClientException e) {
            // clients are not read. error in the dao
            LOG.debug("clients are not obtained");
            return "/error.jsp";
        }
        LOG.debug("page was successfully got");
        return "/blockedCards.jsp";
    }
}
