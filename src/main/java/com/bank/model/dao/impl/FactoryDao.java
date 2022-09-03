package com.bank.model.dao.impl;

import com.bank.model.dao.Dao;
import com.bank.model.exception.DaoFactoryException;

import java.util.Objects;

public class FactoryDao {

    private static FactoryDao instance;

    private FactoryDao() {
        // here you could place conf if needed
    }

    public static synchronized FactoryDao getInstance() {
        if(Objects.isNull(instance))
            instance = new FactoryDao();
        return instance;
    }

    /**
     * gets specify dao by dao enum
     * @param daoEnum the dao enum
     * @throws DaoFactoryException in case when smth went wrong
     * */
    public Dao getDao(DaoEnum daoEnum) throws DaoFactoryException {
        switch (daoEnum) {
            case CLIENT_DAO:
                return DaoEnum.CLIENT_DAO.getDao();
            case CARD_DAO:
                return DaoEnum.CARD_DAO.getDao();
            case BILL_DAO:
                return DaoEnum.BILL_DAO.getDao();
            default:
                throw new DaoFactoryException("Provided wrong dao enum. Null or non-existent received.");
        }
    }
}
