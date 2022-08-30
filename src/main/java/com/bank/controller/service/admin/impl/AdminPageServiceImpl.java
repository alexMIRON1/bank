package com.bank.controller.service.admin.impl;

import com.bank.controller.service.admin.AdminPageService;
import com.bank.model.dao.CardDao;
import com.bank.model.dao.ClientDao;
import com.bank.model.dao.impl.DaoEnum;
import com.bank.model.dao.impl.FactoryDao;
import com.bank.model.entity.Card;
import com.bank.model.entity.Client;
import com.bank.model.entity.Page;
import com.bank.model.exception.card.ReadCardException;
import com.bank.model.exception.client.ReadClientException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;

public class AdminPageServiceImpl implements AdminPageService {
    private final CardDao cardDao;
    private final ClientDao clientDao;
    private static final Logger LOG = LogManager.getLogger(AdminPageServiceImpl.class);

    public AdminPageServiceImpl(CardDao cardDao, ClientDao clientDao) {
        this.cardDao = cardDao;
        this.clientDao = clientDao;
    }

    @Override
    public List<Client> getClients(Page page) throws ReadClientException {
        int currentPage = page.getNumber();
        int records = page.getRecords();
        return clientDao.getClients((currentPage-1)*records,records);
    }

    @Override
    public List<Card> getCards(Page page) throws ReadCardException {
        int currentPage = page.getNumber();
        int records = page.getRecords();
        return cardDao.getCardsToUnblock((currentPage-1)*records,records);
    }

    @Override
    public Client fillClient(Integer id) throws ReadClientException {
        if(id == 0){
            LOG.debug("client with id" + id + "does not exist");
            throw new ReadClientException();
        }
        ClientDao clientsDao = (ClientDao) FactoryDao.getInstance().getDao(DaoEnum.CLIENT_DAO);
        return clientsDao.read(id);
    }

    @Override
    public int getNoOfRecords() {
        return clientDao.getNoOfRecords();
    }
    @Override
    public int getNoOfRecordsCard(){
        return cardDao.getNoOfRecords();
    }
}
