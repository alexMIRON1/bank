package com.bank.model.connection;

import com.bank.model.exception.ConnectionException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionPool {
    private static final DataSource ds;

    static {
        try {
            InitialContext initContext = new InitialContext();
            ds = (DataSource) initContext.lookup("java:comp/env/jdbc/mysql/bank");
        } catch (NamingException e) {
            throw new ConnectionException("Fail to obtain connection..", e);
        }
    }

    private ConnectionPool() {

    }
    public static DataSource getDs() {
        return ds;
    }
}
