package com.bank.controller.service.client.impl;

import com.bank.controller.command.exception.NotEnoughException;
import com.bank.controller.service.client.BillsService;
import com.bank.model.dao.BillDao;
import com.bank.model.dao.CardDao;
import com.bank.model.dao.impl.DaoEnum;
import com.bank.model.dao.impl.FactoryDao;
import com.bank.model.entity.Bill;
import com.bank.model.entity.BillStatus;
import com.bank.model.entity.Card;
import com.bank.model.exception.bill.DeleteBillException;
import com.bank.model.exception.bill.ReadBillException;
import com.bank.model.exception.bill.UpdateBillException;
import com.bank.model.exception.card.ReadCardException;
import com.bank.model.exception.card.UpdateCardException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class BillsServiceImpl implements BillsService {
    private static final Logger LOG = LogManager.getLogger(BillsServiceImpl.class);
    private final BillDao billDao;
    private final CardDao cardDao;

    public BillsServiceImpl(BillDao billDao, CardDao cardDao) {
        this.billDao = billDao;
        this.cardDao = cardDao;
    }

    @Override
    public Bill read(Integer id) throws ReadBillException {
        return billDao.read(id);
    }

    @Override
    public void updateCard(Card card, Bill bill) throws UpdateCardException, NotEnoughException{
        if(card.getBalance() - bill.getSum()<0){
            LOG.debug("not enough money");
            throw new NotEnoughException();
        }
        card.setBalance(card.getBalance() - bill.getSum());
        cardDao.update(card);
    }

    @Override
    public void updateBill(Bill bill, Card card) throws UpdateBillException {
        bill.setBillStatus(BillStatus.PAID);
        bill.setCard(card);
        billDao.update(bill);
    }

    @Override
    public List<Bill> getBills(Card card) throws ReadBillException {
        return billDao.getBills(card);
    }

    @Override
    public void delete(Integer id) throws DeleteBillException {
         billDao.delete(id);
    }

    @Override
    public Card fillCard(Integer id) throws ReadCardException {
        CardDao cardDao = (CardDao) FactoryDao.getInstance().getDao(DaoEnum.CARD_DAO);
        return cardDao.read(id);
    }
}
