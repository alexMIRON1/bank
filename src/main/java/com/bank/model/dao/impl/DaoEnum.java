package com.bank.model.dao.impl;

import com.bank.model.dao.Dao;

public enum DaoEnum {
    CLIENT_DAO(new ClientDaoImpl()),
    CARD_DAO(new CardDaoImpl()),
    BILL_DAO(new BillDaoImpl());

    private final Dao dao;

    DaoEnum(Dao dao) {
        this.dao = dao;
    }

    public Dao getDao() {
        return dao;
    }
}
