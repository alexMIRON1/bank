package com.bank.controller.service.client.impl;

import com.bank.controller.service.client.CardsService;
import com.bank.model.dao.CardDao;
import com.bank.model.dao.ClientDao;
import com.bank.model.dao.impl.DaoEnum;
import com.bank.model.dao.impl.FactoryDao;
import com.bank.model.entity.Card;
import com.bank.model.entity.CardStatus;
import com.bank.model.entity.Client;
import com.bank.model.exception.card.ReadCardException;
import com.bank.model.exception.card.UpdateCardException;
import com.bank.model.exception.client.ReadClientException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.SQLException;

public class CardsServiceImpl implements CardsService {
    private final CardDao cardDao;
    private static final Logger LOG = LogManager.getLogger(CardsServiceImpl.class);
    public CardsServiceImpl(CardDao cardDao) {
        this.cardDao = cardDao;
    }

    @Override
    public void updateStatus(Card card) throws UpdateCardException{
        if(card.getId() == null){
            throw new UpdateCardException();
        }
        if(card.getCardStatus() == CardStatus.BLOCKED){
            card.setCardStatus(CardStatus.READY_TO_UNBLOCK);
        }else {
            card.setCardStatus(CardStatus.BLOCKED);
        }
        cardDao.update(card);
    }

    @Override
    public Card read(Integer cardId) throws ReadCardException {
        if(cardId == 0){
            throw new ReadCardException();
        }
        return cardDao.read(cardId);
    }

    @Override
    public void updateTopUp(Card card, Integer topUp) throws UpdateCardException {
        if(card.getBalance()+topUp>=Integer.MAX_VALUE){
            LOG.debug("you reached max value in your account");
            throw new UpdateCardException();
        }
        card.setBalance(card.getBalance()+topUp);
        cardDao.update(card);
    }

    @Override
    public void updateCustomName(Card card, String name) throws UpdateCardException{
        if(card.getId() == null){
            throw new UpdateCardException();
        }
        card.setCustomName(name);
        cardDao.update(card);
    }

}
