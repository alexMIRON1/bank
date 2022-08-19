package com.bank.controller.service.admin;

import com.bank.model.entity.Card;
import com.bank.model.exception.card.ReadCardException;
import com.bank.model.exception.card.UpdateCardException;

import java.sql.SQLException;

public interface UnblockService {
    Card read(Integer id) throws ReadCardException;
    void update(Card card) throws UpdateCardException;
}
