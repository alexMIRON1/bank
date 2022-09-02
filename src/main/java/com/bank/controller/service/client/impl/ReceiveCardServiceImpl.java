package com.bank.controller.service.client.impl;

import com.bank.controller.service.client.ReceiveCardService;
import com.bank.model.dao.CardDao;
import com.bank.model.entity.Card;
import com.bank.model.entity.CardStatus;
import com.bank.model.entity.Client;
import com.bank.model.exception.card.CreateCardException;
import com.bank.model.exception.card.ReadCardException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class ReceiveCardServiceImpl implements ReceiveCardService {
    private final CardDao cardDao;
    private static final Logger LOG = LogManager.getLogger(ReceiveCardServiceImpl.class);

    public ReceiveCardServiceImpl(CardDao cardDao) {
        this.cardDao = cardDao;
    }

    @Override
    public void create(Card card, Client client) throws CreateCardException, ReadCardException{
        if(client.getId() ==0){
            LOG.debug("client with id " + client.getId()  + "does not exist");
            throw new CreateCardException();
        }
        card.setName(generateName());
        card.setCustomName("default");
        card.setBalance(100);
        card.setCardStatus(CardStatus.UNBLOCKED);
        card.setClient(client);
        cardDao.create(card);
    }

    @Override
    public String getCardLastName() throws ReadCardException {
        return cardDao.getLastCardName();
    }
    private String generateName() throws ReadCardException{
        String cardName = getCardLastName();
        return String.format("%04d", Integer.parseInt(cardName) + 1);
    }
}
