package com.bank.model.dao;

import javax.sql.DataSource;

/**
 * inheritor
 * WARNING: methods which do not have implementation throws UnsupportedOperationException
 * */
public interface Dao {
    /**
     * set dataSource for connection
     * @param dataSource
     * **/
    void setDs(DataSource dataSource);
}
