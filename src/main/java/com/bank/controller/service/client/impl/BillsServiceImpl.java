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

import java.math.BigDecimal;
import java.util.Date;
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
        if(id==0){
            LOG.debug("bill with id " + id + "does not exist");
            throw new ReadBillException();
        }
        return billDao.read(id);
    }

    @Override
    public void updateCard(Card card, Card cardTo, Bill bill) throws UpdateCardException, NotEnoughException{
        if(card.getBalance().subtract(bill.getSum()).compareTo(BigDecimal.ZERO)<0){
            LOG.debug("not enough money");
            throw new NotEnoughException();
        }
        if(card.getId() == 0 || bill.getId() == 0){
            LOG.debug("card or bill do not exist");
            throw new UpdateCardException();
        }
        card.setBalance(card.getBalance().subtract(bill.getSum()));
        if(cardTo.getBalance().add(bill.getSum()).compareTo(BigDecimal.ZERO)<0){
            LOG.debug("reached max value on card recipient");
            throw new UpdateCardException();
        }
        cardTo.setBalance(cardTo.getBalance().add(bill.getSum()));
        cardDao.transferCard(card,cardTo);
    }

    @Override
    public void updateBill(Bill bill, Card card, Card cardTo) throws UpdateBillException {
        if(bill.getId() == 0 || card.getId() ==0){
            LOG.debug("card or bill do not exist");
            throw new UpdateBillException();
        }
        bill.setBillStatus(BillStatus.PAID);
        bill.setCard(card);
        bill.setRecipient(cardTo.getName());
        bill.setDate(new Date());
        billDao.update(bill);
    }

    @Override
    public List<Bill> getBills(Card card) throws ReadBillException {
        if(card.getId() == null || card.getId() ==0){
            LOG.debug("card does not exist");
            throw new ReadBillException();
        }
        return billDao.getBills(card);
    }

    @Override
    public void delete(Integer id) throws DeleteBillException {
        if(id == null || id == 0){
            LOG.debug("bill does not exist");
            throw new DeleteBillException();
        }
        billDao.delete(id);
    }

    @Override
    public Card fillCard(Integer id) throws ReadCardException {
        if(id == 0){
            LOG.debug("card with id " + id + "does not exist");
            throw  new ReadCardException();
        }
        CardDao cardDao = (CardDao) FactoryDao.getInstance().getDao(DaoEnum.CARD_DAO);
        return cardDao.read(id);
    }
}
