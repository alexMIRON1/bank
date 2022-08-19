package com.bank.controller.service.admin.impl;

import com.bank.controller.service.admin.UnblockService;
import com.bank.model.dao.CardDao;
import com.bank.model.entity.Card;
import com.bank.model.entity.CardStatus;
import com.bank.model.exception.card.ReadCardException;
import com.bank.model.exception.card.UpdateCardException;

import java.sql.SQLException;

public class UnblockServiceImpl implements UnblockService {
    private final CardDao cardDao;

    public UnblockServiceImpl(CardDao cardDao) {
        this.cardDao = cardDao;
    }

    @Override
    public Card read(Integer id) throws ReadCardException{
        return cardDao.read(id);
    }

    @Override
    public void update(Card card) throws UpdateCardException {
        card.setCardStatus(CardStatus.UNBLOCKED);
        cardDao.update(card);
    }
}
