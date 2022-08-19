package com.bank.controller.service.client.impl;

import com.bank.controller.command.exception.CardBannedException;
import com.bank.controller.service.client.MakePaymentService;
import com.bank.model.dao.BillDao;
import com.bank.model.entity.Bill;
import com.bank.model.entity.BillStatus;
import com.bank.model.entity.Card;
import com.bank.model.entity.CardStatus;
import com.bank.model.exception.bill.CreateBillException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class MakePaymentServiceImpl implements MakePaymentService {
    private static final Logger LOG = LogManager.getLogger(MakePaymentServiceImpl.class);
    private final BillDao billDao;

    public MakePaymentServiceImpl(BillDao billDao) {
        this.billDao = billDao;
    }

    @Override
    public Bill create(Bill bill, Integer sum, Card card) throws CreateBillException , CardBannedException {
        if(card.getCardStatus().equals(CardStatus.BLOCKED) || card.getCardStatus().equals(CardStatus.READY_TO_UNBLOCK)){
            LOG.debug("card is banned");
            throw new CardBannedException();
        }
        if(sum == 0){
            LOG.debug("sum payment was null");
            throw new CreateBillException();
        }
        bill.setSum(sum);
        bill.setCard(card);
        bill.setBillStatus(BillStatus.READY);
        return billDao.create(bill);
    }
}
