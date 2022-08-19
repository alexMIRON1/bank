package com.bank.model.dao;

import com.bank.model.entity.Bill;
import com.bank.model.entity.Card;
import com.bank.model.exception.bill.UpdateBillException;
import com.bank.model.exception.bill.CreateBillException;
import com.bank.model.exception.bill.DeleteBillException;
import com.bank.model.exception.bill.ReadBillException;

import java.sql.SQLException;
import java.util.List;

/**
 * interface Bill Dao
 * contains CRUD methods and
 * few useful methods to manipulate Bill's table
 * */
public interface BillDao extends Dao {

    /**
     * creates bill in the database
     * @param bill the bill need to insert to database
     * @return inserted bill
     * @throws CreateBillException in case when bill was not inserted
     * */
    Bill create(Bill bill) throws CreateBillException;

    /**
     * reads bill by id in the database
     * @param id the bill's id
     * @return bill
     * @throws ReadBillException in case when bill was not found
     * */
    Bill read(Integer id) throws ReadBillException;

    /**
     * updates bill in the database
     * @param bill the bill need to update to database
     * @return updated bill
     * @throws UpdateBillException in case when bill was not updated
     * */
    Bill update(Bill bill) throws UpdateBillException;

    /**
     * deletes bill by id in the database
     * @param id the bill's id
     * @return deleted bill's id
     * @throws DeleteBillException in case when bill was not deleted
     * */
    Integer delete(Integer id) throws DeleteBillException;

    /**
     * gets card's bills
     * @param card the card
     * @throws ReadBillException in case when bill was not read
     * */
    List<Bill> getBills(Card card) throws ReadBillException;

    /**
     * gets card's bill on page
     * @param card the card
     * @param start the start page
     * @param end the end page
     * @throws ReadBillException in case when bill was not read
     * **/
    List<Bill> getBillsOnPage(Card card,int start, int end) throws ReadBillException;
    /**
     * get int amount of records
     * **/
    int getNoOfRecords();

    /**
     * gets card's bills sorted by id
     * @param card the card
     * @param start the start page
     * @param end the end page
     * @throws ReadBillException in case when bill was not read
     * */
    List<Bill> getBillsSortedById(Card card,int start, int end) throws ReadBillException;

    /**
     * gets card's bills sorted by date
     * @param card the card
     * @param start the start page
     * @param end the end page
     * @throws ReadBillException in case when bill was not read
     * */
    List<Bill> getBillsSortedByDate(Card card,int start,int end) throws ReadBillException;

    /**
     * gets card's bills sorted by date (desc)
     * @param card the card
     * @param start the start page
     * @param end the end page
     * @throws ReadBillException in case when bill was not read
     * */
    List<Bill> getBillsSortedByDateDesc(Card card, int start, int end) throws ReadBillException;

}
