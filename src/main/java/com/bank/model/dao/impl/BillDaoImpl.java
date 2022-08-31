package com.bank.model.dao.impl;

import com.bank.model.dao.BillDao;
import com.bank.model.entity.Bill;
import com.bank.model.entity.BillStatus;
import com.bank.model.entity.Card;
import com.bank.model.exception.ConnectionException;
import com.bank.model.exception.bill.CreateBillException;
import com.bank.model.exception.bill.DeleteBillException;
import com.bank.model.exception.bill.ReadBillException;
import com.bank.model.exception.bill.UpdateBillException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillDaoImpl implements BillDao {

    private int noOfRecords;
    private static final String QUERY_CREATE_BILL = "INSERT INTO bill (id, sum, date, card_id, bill_status_id, recipient) VALUE " +
            "(DEFAULT, ?, DEFAULT, ?, ?, ?)";
    private static final String QUERY_UPDATE_BILL = "UPDATE bill SET bill_status_id=?,recipient=? where id=?";
    private static final String QUERY_READ_BILL = "SELECT * FROM bill WHERE id=?";
    private static final String QUERY_READ_BILLS_BY_CARD_ID = "SELECT * FROM bill WHERE card_id=?";
    private static final String QUERY_READ_BILLS_BY_CARD_ID_SORTED_BY_ID = "SELECT SQL_CALC_FOUND_ROWS * FROM bill WHERE card_id=? ORDER BY id LIMIT ?, ?";
    private static final String QUERY_READ_BILLS_BY_CARD_ID_SORTED_BY_DATE = "SELECT SQL_CALC_FOUND_ROWS * FROM bill WHERE card_id=? ORDER BY date LIMIT ?, ?";
    private static final String QUERY_READ_BILLS_BY_CARD_ID_SORTED_BY_DATE_DESC = "SELECT SQL_CALC_FOUND_ROWS * FROM bill WHERE card_id=? ORDER BY date DESC LIMIT ?, ?";
    private static final String QUERY_SHOW_BILLS = "SELECT SQL_CALC_FOUND_ROWS * FROM bill WHERE card_id=? LIMIT ?, ?";
    private static final String QUERY_DELETE_BILL = "DELETE FROM bill WHERE id=?";
    public BillDaoImpl() {
    }

    @Override
    public Bill create(Bill bill) throws CreateBillException {
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_CREATE_BILL)) {
            preparedStatement.setInt(1, bill.getSum());
            preparedStatement.setInt(2, bill.getCard().getId());
            preparedStatement.setInt(3, bill.getBillStatus().getId());
            preparedStatement.setString(4, bill.getRecipient());
            int i = preparedStatement.executeUpdate();
            if(i != 1)
                throw new SQLException();
            return bill;
        } catch (SQLException e) {
            throw new CreateBillException(e.getMessage(), e);
        }
    }

    @Override
    public Bill read(Integer id) throws ReadBillException {
        Bill bill = new Bill();
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_READ_BILL)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            bill.setId(resultSet.getInt("id"));
            bill.setSum(resultSet.getInt("sum"));
            bill.setDate(resultSet.getDate("date"));
            bill.setCard(new Card(resultSet.getInt("card_id")));
            bill.setBillStatus(BillStatus.getBillStatus(resultSet.getInt("bill_status_id")));
            bill.setRecipient(resultSet.getString("recipient"));
            return bill;
        } catch (SQLException e) {
            throw new ReadBillException(e.getMessage(), e);
        }
    }

    @Override
    public Bill update(Bill bill) throws UpdateBillException {
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE_BILL)) {
            preparedStatement.setInt(1, bill.getBillStatus().getId());
            preparedStatement.setString(2, bill.getRecipient());
            preparedStatement.setInt(3, bill.getId());
            int i = preparedStatement.executeUpdate();
            if(i != 1)
                throw new SQLException();
            return bill;
        } catch (SQLException e) {
            throw new UpdateBillException(e.getMessage(), e);
        }
    }

    @Override
    public Integer delete(Integer id) throws DeleteBillException {
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_DELETE_BILL)){
            preparedStatement.setInt(1,id);
            int i = preparedStatement.executeUpdate();
            if(i != 1)
                throw new SQLException();
            return id;
        }catch (SQLException e){
            throw new DeleteBillException(e.getMessage(),e);
        }
    }

    @Override
    public List<Bill> getBills(Card card) throws ReadBillException{
        List<Bill> bills = new ArrayList<>();
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_READ_BILLS_BY_CARD_ID)) {
            preparedStatement.setInt(1, card.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Bill bill = new Bill();
                bill.setId(resultSet.getInt("id"));
                bill.setSum(resultSet.getInt("sum"));
                bill.setDate(resultSet.getDate("date"));
                bill.setCard(new Card(resultSet.getInt("card_id")));
                bill.setBillStatus(BillStatus.getBillStatus(resultSet.getInt("bill_status_id")));
                bill.setRecipient(resultSet.getString("recipient"));
                bills.add(bill);
            }
            return bills;
        } catch (SQLException e) {
            throw new ReadBillException(e.getMessage(), e);
        }
    }

    @Override
    public int getNoOfRecords() {
        return noOfRecords;
    }

    @Override
    public List<Bill> getBillsOnPage(Card card, int start, int end) throws ReadBillException {
        List<Bill> bills = new ArrayList<>();
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_SHOW_BILLS)){
            preparedStatement.setInt(1,card.getId());
            preparedStatement.setInt(2,start);
            preparedStatement.setInt(3,end);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Bill bill = new Bill();
                bill.setId(resultSet.getInt("id"));
                bill.setSum(resultSet.getInt("sum"));
                bill.setDate(resultSet.getDate("date"));
                bill.setCard(new Card(resultSet.getInt("card_id")));
                bill.setBillStatus(BillStatus.getBillStatus(resultSet.getInt("bill_status_id")));
                bill.setRecipient(resultSet.getString("recipient"));
                bills.add(bill);
            }
            resultSet.close();
            resultSet = preparedStatement.executeQuery("SELECT FOUND_ROWS()");
            if(resultSet.next()){
                this.noOfRecords = resultSet.getInt(1);

            }

        }catch (SQLException e){
            throw new ReadBillException(e.getMessage(), e);
        }
        return bills;
    }


    @Override
    public List<Bill> getBillsSortedById(Card card,int start, int end) throws ReadBillException {
        List<Bill> bills = new ArrayList<>();
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_READ_BILLS_BY_CARD_ID_SORTED_BY_ID)) {
            preparedStatement.setInt(1, card.getId());
            preparedStatement.setInt(2,start);
            preparedStatement.setInt(3,end);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Bill bill = new Bill();
                bill.setId(resultSet.getInt("id"));
                bill.setSum(resultSet.getInt("sum"));
                bill.setDate(resultSet.getDate("date"));
                bill.setCard(new Card(resultSet.getInt("card_id")));
                bill.setBillStatus(BillStatus.getBillStatus(resultSet.getInt("bill_status_id")));
                bill.setRecipient(resultSet.getString("recipient"));
                bills.add(bill);
            }
            resultSet.close();
            resultSet = preparedStatement.executeQuery("SELECT FOUND_ROWS()");
            if(resultSet.next()){
                this.noOfRecords = resultSet.getInt(1);

            }
            return bills;
        } catch (SQLException e) {
            throw new ReadBillException(e.getMessage(), e);
        }
    }

    @Override
    public List<Bill> getBillsSortedByDate(Card card,int start, int end) throws ReadBillException {
        List<Bill> bills = new ArrayList<>();
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_READ_BILLS_BY_CARD_ID_SORTED_BY_DATE)) {
            preparedStatement.setInt(1, card.getId());
            preparedStatement.setInt(2,start);
            preparedStatement.setInt(3,end);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Bill bill = new Bill();
                bill.setId(resultSet.getInt("id"));
                bill.setSum(resultSet.getInt("sum"));
                bill.setDate(resultSet.getDate("date"));
                bill.setCard(new Card(resultSet.getInt("card_id")));
                bill.setBillStatus(BillStatus.getBillStatus(resultSet.getInt("bill_status_id")));
                bill.setRecipient(resultSet.getString("recipient"));
                bills.add(bill);
            }
            resultSet.close();
            resultSet = preparedStatement.executeQuery("SELECT FOUND_ROWS()");
            if(resultSet.next()){
                this.noOfRecords = resultSet.getInt(1);

            }
            return bills;
        } catch (SQLException e) {
            throw new ReadBillException(e.getMessage(), e);
        }
    }

    @Override
    public List<Bill> getBillsSortedByDateDesc(Card card, int start,int end) throws ReadBillException {
        List<Bill> bills = new ArrayList<>();
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_READ_BILLS_BY_CARD_ID_SORTED_BY_DATE_DESC)) {
            preparedStatement.setInt(1, card.getId());
            preparedStatement.setInt(2,start);
            preparedStatement.setInt(3,end);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Bill bill = new Bill();
                bill.setId(resultSet.getInt("id"));
                bill.setSum(resultSet.getInt("sum"));
                bill.setDate(resultSet.getDate("date"));
                bill.setCard(new Card(resultSet.getInt("card_id")));
                bill.setBillStatus(BillStatus.getBillStatus(resultSet.getInt("bill_status_id")));
                bill.setRecipient(resultSet.getString("recipient"));
                bills.add(bill);
            }
            resultSet.close();
            resultSet = preparedStatement.executeQuery("SELECT FOUND_ROWS()");
            if(resultSet.next()){
                this.noOfRecords = resultSet.getInt(1);

            }
            return bills;
        } catch (SQLException e) {
            throw new ReadBillException(e.getMessage(), e);
        }
    }

    /**
     * gets connection
     * */
    private DataSource ds;

    public void setDs(DataSource ds) {
        this.ds = ds;
    }

    private Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

}
