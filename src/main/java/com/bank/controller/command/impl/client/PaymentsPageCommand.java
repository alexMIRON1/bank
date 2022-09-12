package com.bank.controller.command.impl.client;

import com.bank.controller.command.Command;
import com.bank.controller.service.client.PaymentsPageService;
import com.bank.model.entity.Bill;
import com.bank.model.entity.Card;
import com.bank.model.entity.Page;
import com.bank.model.exception.bill.ReadBillException;
import com.bank.model.exception.card.ReadCardException;
import com.bank.model.exception.client.ReadClientException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

public class PaymentsPageCommand implements Command{

    private static final Logger LOG = LogManager.getLogger(PaymentsPageCommand.class);
    private final PaymentsPageService paymentsPageService;

    public PaymentsPageCommand(PaymentsPageService paymentsPageService) {
        this.paymentsPageService = paymentsPageService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Integer cardId = Integer.parseInt(request.getParameter("card"));
        String sort = Objects.isNull(request.getParameter("sort")) ? "" : request.getParameter("sort");

        try {
            Page page = new Page();
            page.setNumberPage(1);
            page.setRecords(3);
            int recordsPerPage = page.getRecords();
            if(request.getParameter("page")!=null){
                page.setNumberPage(Integer.parseInt(request.getParameter("page")));
                LOG.info("page = " + page.getNumber());
            }
            Card card = paymentsPageService.read(cardId);
            card.setClient(paymentsPageService.fillClient(card.getClient().getId()));
            List<Bill> bills = getBills(sort, card, page);
            int noOfRecords = paymentsPageService.getNoOfRecords();
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
            request.getSession().setAttribute("page",page);
            request.getSession().setAttribute("noOfPages", noOfPages);
            request.getSession().setAttribute("noOfRecords", noOfRecords);
            request.getSession().setAttribute("currentCard", card);
            request.getSession().setAttribute("bills", bills);
            LOG.debug("card was successfully read");
        } catch (ReadCardException  e) {
            // such card does not exist
            LOG.debug("fail to obtain card-->such card does not exist");
            return "/error.jsp";
        } catch (ReadBillException | ReadClientException e) {
            // fail to obtain bills or card is null
            LOG.debug("fail to obtain bills");
            return "/error.jsp";
        }
        return "/payments.jsp";
    }

    /**
     * gets bills by card and sort parameter
     * @param sort the string-param
     * @param card the card
     * @throws ReadBillException in case when bill was not read
     * @throws NullPointerException in case when card is null
     * */
    private List<Bill> getBills(String sort, Card card, Page page) throws ReadBillException, NullPointerException, ReadCardException {
        List<Bill> bills;
        bills = paymentsPageService.getSortedBills(sort, card, page);
        for (Bill bill: bills) {
            bill.setCard(paymentsPageService.fillCard(bill.getCard().getId()));
        }
        return bills;
    }

}
