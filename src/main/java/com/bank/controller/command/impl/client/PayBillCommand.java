package com.bank.controller.command.impl.client;

import com.bank.controller.command.Command;
import com.bank.controller.command.exception.NotEnoughException;
import com.bank.controller.service.client.BillsService;
import com.bank.model.entity.Bill;
import com.bank.model.entity.Card;
import com.bank.model.entity.Page;
import com.bank.model.exception.bill.ReadBillException;
import com.bank.model.exception.bill.UpdateBillException;
import com.bank.model.exception.card.ReadCardException;
import com.bank.model.exception.card.UpdateCardException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class PayBillCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(PayBillCommand.class);
    private final BillsService billsService;
    private static final String ERROR = "/error.jsp";

    public PayBillCommand(BillsService billsService) {
        this.billsService = billsService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Page page = (Page) request.getSession().getAttribute("page");
        int billId = 0;
        int idCard = 0;
        String idToCard = request.getParameter("toCard");
        if(request.getParameter("bill")!=null || idToCard!=null){
             billId = Integer.parseInt(request.getParameter("bill"));
             idCard = Integer.parseInt(idToCard);
        }
        try {
            Bill bill = billsService.read(billId);
            Card card = billsService.fillCard(bill.getCard().getId());
            Card cardTo = billsService.fillCard(idCard);
            billsService.updateCard(card,cardTo,bill);
            LOG.debug("card was successfully update");
            billsService.updateBill(bill,card,cardTo);
            LOG.debug("bill was successfully update");
            List<Bill> bills = billsService.getBills(card);
            request.getSession().setAttribute("currentCard", card);
            request.getSession().setAttribute("bills", bills);
            LOG.debug("Bill was successfully paid to card " + idCard);
            return "redirect:/bank/payments?page=" + page.getNumber() + "&card=" + card.getId();
        } catch (ReadBillException | ReadCardException e) {
            // such bill does not exist
            LOG.debug("fail to obtain bill-->such bill does not exist or card");
            return "/errors/cardNotExist.jsp";
        } catch (UpdateBillException e) {
            // bill is not updated perhaps entered wrong data
            LOG.debug("fail to update bill-->entered wrong data");
            return ERROR;
        } catch (UpdateCardException e) {
            // card was not updated error in the dao
            LOG.debug("fail to update card-->error in the dao");
            return ERROR;
        } catch (NotEnoughException e) {
            //watch BillsServiceImpl that get problem
            LOG.debug("fail to obtain bill--> not enough money on bill" + billId);
            return "/errors/cardNotExist.jsp";
        }
    }

}
