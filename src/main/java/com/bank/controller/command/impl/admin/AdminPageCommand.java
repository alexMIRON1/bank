package com.bank.controller.command.impl.admin;

import com.bank.controller.command.Command;
import com.bank.controller.service.admin.AdminPageService;
import com.bank.model.entity.Card;
import com.bank.model.entity.Client;
import com.bank.model.entity.Page;
import com.bank.model.exception.card.ReadCardException;
import com.bank.model.exception.client.ReadClientException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class AdminPageCommand implements Command {
    private final AdminPageService adminPageService;
    private static final Logger LOG = LogManager.getLogger(AdminPageCommand.class);

    public AdminPageCommand(AdminPageService adminPageService) {
        this.adminPageService = adminPageService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        try {
            Page page = new Page();
            page.setNumberPage(1);
            page.setRecords(5);
            if(request.getParameter("page")!=null){
                page.setNumberPage(Integer.parseInt(request.getParameter("page")));
                LOG.debug("page = " + page.getNumber());
            }
            List<Client> clients = adminPageService.getClients(page);
            List<Card> cards = adminPageService.getCards(page);
            for (Card card: cards) {
                card.setClient(adminPageService.fillClient(card.getClient().getId()));
            }
            int noOfRecords = adminPageService.getNoOfRecords();
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / page.getRecords());
            request.getSession().setAttribute("page",page);
            request.getSession().setAttribute("noOfPages", noOfPages);
            request.setAttribute("clients", clients);
            request.setAttribute("unlockCards", cards);
        } catch (ReadClientException e) {
            // clients are not read. error in the dao
            LOG.debug("clients are not obtained");
            return "/error.jsp";
        } catch (ReadCardException e) {
            // cards are not read. error in the dao
            LOG.debug("cards are not obtained");
            return "/error.jsp";
        }
        LOG.debug("admin page successfully show");
        return "/admin.jsp";
    }
}
