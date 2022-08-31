package com.bank.controller.command.impl.client;

import com.bank.controller.command.Command;
import com.bank.controller.command.exception.NotEnoughException;
import com.bank.controller.service.client.BillsService;
import com.bank.model.entity.Bill;
import com.bank.model.entity.BillStatus;
import com.bank.model.entity.Card;
import com.bank.model.entity.Page;
import com.bank.model.exception.bill.ReadBillException;
import com.bank.model.exception.bill.UpdateBillException;
import com.bank.model.exception.card.ReadCardException;
import com.bank.model.exception.card.UpdateCardException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

public class PayBillCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(PayBillCommand.class);
    private final BillsService billsService;

    public PayBillCommand(BillsService billsService) {
        this.billsService = billsService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Page page = (Page) request.getSession().getAttribute("page");
        Integer billId = Integer.parseInt(request.getParameter("bill"));
        String idToCard = request.getParameter("toCard");
        Integer idCard = Integer.parseInt(idToCard.replace("0",""));
        try {
            Bill bill = billsService.read(billId);
            Card card = billsService.fillCard(bill.getCard().getId());
            Card cardTo = billsService.fillCard(idCard);
            billsService.updateCard(card,cardTo,bill);
            LOG.info("card was successfully update");
            billsService.updateBill(bill,card,cardTo);
            LOG.info("bill was successfully update");
            List<Bill> bills = billsService.getBills(card);
            request.getSession().setAttribute("currentCard", card);
            request.getSession().setAttribute("bills", bills);
            LOG.info("Bill was successfully paid");
            return "redirect:/bank/payments?page=" + page.getNumber() + "&card=" + card.getId();
        } catch (ReadBillException | ReadCardException e) {
            // such bill does not exist
            LOG.debug("fail to obtain bill-->such bill does not exist");
            return "/error.jsp";
        } catch (UpdateBillException e) {
            // bill is not updated perhaps entered wrong data
            LOG.debug("fail to update bill-->entered wrong data");
            return "/error.jsp";
        } catch (UpdateCardException e) {
            // card was not updated error in the dao
            LOG.debug("fail to update card-->error in the dao");
            return "/error.jsp";
        } catch (NotEnoughException e) {
            //watch BillsServiceImpl that get problem
            LOG.debug("fail to obtain bill--> not enough money on bill" + billId);
            return "/error.jsp";
        }
    }

}
