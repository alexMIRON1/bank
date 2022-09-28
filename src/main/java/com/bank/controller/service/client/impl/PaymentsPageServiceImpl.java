package com.bank.controller.service.client.impl;

import com.bank.controller.service.client.PaymentsPageService;
import com.bank.model.dao.BillDao;
import com.bank.model.dao.CardDao;
import com.bank.model.dao.ClientDao;
import com.bank.model.dao.impl.DaoEnum;
import com.bank.model.dao.impl.FactoryDao;
import com.bank.model.entity.Bill;
import com.bank.model.entity.Card;
import com.bank.model.entity.Client;
import com.bank.model.entity.Page;
import com.bank.model.exception.bill.ReadBillException;
import com.bank.model.exception.card.ReadCardException;
import com.bank.model.exception.client.ReadClientException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;

public class PaymentsPageServiceImpl implements PaymentsPageService {
    private static final Logger LOG = LogManager.getLogger(PaymentsPageServiceImpl.class);
    private final CardDao cardDao;
    private final BillDao billDao;

    public PaymentsPageServiceImpl(CardDao cardDao, BillDao billDao) {
        this.cardDao = cardDao;
        this.billDao = billDao;
    }

    @Override
    public Card read(Integer id) throws ReadCardException{
        if(id == 0){
            LOG.debug("card with this id " + id  + "does not exist");
            throw new ReadCardException();
        }
        return cardDao.read(id);
    }

    @Override
    public List<Bill> getSortedBills(String sort, Card card, Page page) throws ReadBillException{
        if(card.getId() == 0){
            LOG.debug("card with id " + card.getId()  + "does not exist");
            throw new ReadBillException();
        }
        int currentPage = page.getNumber();
        int records = page.getRecords();
        LOG.info("page = " + page);
        switch (sort) {
            case "id":
                LOG.info("bills sorted by id");
                page.setState("id");
                return billDao.getBillsSortedById(card,(currentPage-1)*records,records);
            case "newest":
                LOG.info("bills sorted by newest");
                page.setState("newest");
                return billDao.getBillsSortedByDateDesc(card,(currentPage-1)*records,records);
            case "latest":
                LOG.info("bills sorted by latest");
                page.setState("latest");
                return billDao.getBillsSortedByDate(card,(currentPage-1)*records,records);
            default:
                LOG.info("bills default sorting");
                page.setState(null);
                return billDao.getBillsOnPage(card,(currentPage-1)*records,records);
        }
    }

    @Override
    public int getNoOfRecords() {
        return billDao.getNoOfRecords();
    }

    @Override
    public Client fillClient(Integer id) throws ReadClientException {
        if(id==0){
            LOG.debug("client with id " + id + "does not exist");
            throw new ReadClientException();
        }
        ClientDao clientDao = (ClientDao) FactoryDao.getInstance().getDao(DaoEnum.CLIENT_DAO);
        return clientDao.read(id);
    }

    @Override
    public Card fillCard(Integer id) throws ReadCardException {
        if(id == 0){
            LOG.debug("card with id " + id  + "does not exist");
            throw new ReadCardException();
        }
        CardDao fillCardDao = (CardDao) FactoryDao.getInstance().getDao(DaoEnum.CARD_DAO);
        return  fillCardDao.read(id);
    }
}
