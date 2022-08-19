package com.bank.controller.service.admin.impl;

import com.bank.controller.service.admin.AdminPageService;
import com.bank.model.dao.CardDao;
import com.bank.model.dao.ClientDao;
import com.bank.model.dao.impl.DaoEnum;
import com.bank.model.dao.impl.FactoryDao;
import com.bank.model.entity.Card;
import com.bank.model.entity.Client;
import com.bank.model.exception.card.ReadCardException;
import com.bank.model.exception.client.ReadClientException;

import java.sql.SQLException;
import java.util.List;

public class AdminPageServiceImpl implements AdminPageService {
    private final CardDao cardDao;
    private final ClientDao clientDao;

    public AdminPageServiceImpl(CardDao cardDao, ClientDao clientDao) {
        this.cardDao = cardDao;
        this.clientDao = clientDao;
    }

    @Override
    public List<Client> getClients() throws ReadClientException {
        return clientDao.getClients();
    }

    @Override
    public List<Card> getCards() throws ReadCardException {
        return cardDao.getCardsToUnblock();
    }

    @Override
    public Client fillClient(Integer id) throws ReadClientException {
        ClientDao clientsDao = (ClientDao) FactoryDao.getInstance().getDao(DaoEnum.CLIENT_DAO);
        return clientsDao.read(id);
    }
}
