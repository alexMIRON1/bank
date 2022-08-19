package com.bank.controller.service.client.impl;

import com.bank.controller.service.client.HomePageService;
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

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HomePageServiceImpl implements  HomePageService {
    private final Logger LOG = LogManager.getLogger(HomePageServiceImpl.class);
    private final CardDao cardDao;
    private ClientDao clientDao;

    public HomePageServiceImpl(CardDao cardDao) {
        this.cardDao = cardDao;
    }
    @Override
    public List<Card> sort(Client client, String sort, Page page) throws ReadCardException{
        int currentPage = page.getNumber();
        int records = page.getRecords();
        LOG.info("page = " + page);
        switch (sort){
            case "id":
                LOG.info("card sorted by id");
                page.setState("id");
                return cardDao.getCardsSortedById(client,(currentPage-1)*records,records);
            case "name":
                LOG.info("card sorted by name");
                page.setState("name");
                return cardDao.getCardsSortedByName(client,(currentPage-1)*records,records);
            case "balance":
                LOG.info("card sorted by balance");
                page.setState("balance");
                return cardDao.getCardsSortedByBalance(client,(currentPage-1)*records,records);
            default:
                LOG.info("card default sorting");
                page.setState(null);
                return cardDao.getCardsOnPage(client,(currentPage-1)*records,records);
        }
    }
    @Override
    public int getNoOfRecords(){
        return cardDao.getNoOfRecords();
    }
    @Override
    public Client fillClient(Integer id) throws ReadClientException {
        clientDao = (ClientDao) FactoryDao.getInstance().getDao(DaoEnum.CLIENT_DAO);
        return clientDao.read(id);
    }
}
