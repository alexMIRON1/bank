package com.bank.model.dao;

public class ConstantsDao {
    private ConstantsDao(){
    }
    //billDao
    public static final String QUERY_CREATE_BILL = "INSERT INTO bill (id, sum, date, card_id, bill_status_id, recipient) VALUE " +
            "(DEFAULT, ?, DEFAULT, ?, ?, ?)";
    public static final String QUERY_UPDATE_BILL = "UPDATE bill SET bill_status_id=?,recipient=? where id=?";
    public static final String QUERY_READ_BILL = "SELECT * FROM bill WHERE id=?";
    public static final String QUERY_READ_BILLS_BY_CARD_ID = "SELECT * FROM bill WHERE card_id=?";
    public static final String QUERY_READ_BILLS_BY_CARD_ID_SORTED_BY_ID = "SELECT SQL_CALC_FOUND_ROWS * FROM bill WHERE card_id=? ORDER BY id LIMIT ?, ?";
    public static final String QUERY_READ_BILLS_BY_CARD_ID_SORTED_BY_DATE = "SELECT SQL_CALC_FOUND_ROWS * FROM bill WHERE card_id=? ORDER BY date LIMIT ?, ?";
    public static final String QUERY_READ_BILLS_BY_CARD_ID_SORTED_BY_DATE_DESC = "SELECT SQL_CALC_FOUND_ROWS * FROM bill WHERE card_id=? ORDER BY date DESC LIMIT ?, ?";
    public static final String QUERY_SHOW_BILLS = "SELECT SQL_CALC_FOUND_ROWS * FROM bill WHERE card_id=? LIMIT ?, ?";
    public static final String QUERY_DELETE_BILL = "DELETE FROM bill WHERE id=?";
    public static final String ID_CARD = "card_id";
    public static final String  SUM = "sum";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String DATE = "date";
    public static final String BILL_STATUS_ID = "bill_status_id";
    public static final String RECIPIENT = "recipient";
    public static final String SELECT_ROWS = "SELECT FOUND_ROWS()";

    //cardDao
    public static final String QUERY_READ_CARDS_READY_TO_UNBLOCK = "SELECT SQL_CALC_FOUND_ROWS * FROM card WHERE card_status_id=3 LIMIT ?, ?";
    public static final String QUERY_READ_CARD_BY_CARD_ID = "SELECT * FROM card WHERE id=?";
    public static final String QUERY_READ_CARDS_BY_CLIENT_ID = "SELECT * FROM card WHERE client_id=?";
    public static final String QUERY_READ_LAST_CARD_NAME = "SELECT name FROM card ORDER BY name DESC LIMIT 1";
    public static final String QUERY_READ_CARDS_BY_CLIENT_ID_SORTED_BY_CUSTOM_NAME = "SELECT SQL_CALC_FOUND_ROWS * FROM card WHERE client_id=? ORDER BY name_custom LIMIT ?, ?";
    public static final String QUERY_READ_CARDS_BY_CLIENT_ID_SORTED_BY_NAME = "SELECT SQL_CALC_FOUND_ROWS * FROM card WHERE client_id=? ORDER BY name LIMIT ?, ?";
    public static final String QUERY_READ_CARDS_BY_CLIENT_ID_SORTED_BY_BALANCE = "SELECT SQL_CALC_FOUND_ROWS * FROM card WHERE client_id=? ORDER BY balance DESC LIMIT ?, ?";
    public static final String QUERY_CREATE_CARD = "INSERT INTO card (id, name, balance, card_status_id, client_id, name_custom) " +
            "VALUE (DEFAULT, ?, ?, ?, ?, ?)";
    public static final String QUERY_UPDATE_CARD = "UPDATE card SET balance=?, card_status_id=?, name_custom=? WHERE id=?";
    public static final String QUERY_SHOW_CARD_PAGE = "SELECT SQL_CALC_FOUND_ROWS * FROM card WHERE client_id=? LIMIT ?, ?";
    public static final String BALANCE = "balance";
    public static final String CARD_STATUS_ID = "card_status_id";
    public static final String CLIENT_ID = "client_id";
    public static final String NAME_CUSTOM = "name_custom";
    //clientDao
    public static final String QUERY_READ_CLIENTS = "SELECT SQL_CALC_FOUND_ROWS * FROM client WHERE role_id=2 LIMIT ?, ?";
    public static final String QUERY_READ_CLIENT_BY_ID = "SELECT * FROM client WHERE id=?";
    public static final String QUERY_READ_CLIENT_BY_LOGIN = "SELECT * FROM client WHERE login=?";
    public static final String QUERY_CREATE_CLIENT = "INSERT INTO client" +
            "(id, login, password, create_time, client_status_id, role_id)" +
            " value (DEFAULT, ?, ?, DEFAULT, ?, ?)";
    public static final String QUERY_UPDATE_CLIENT = "UPDATE client SET client_status_id=? where id=?";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String CLIENT_STATUS_ID = "client_status_id";
    public static final String ROLE_ID = "role_id";
}
