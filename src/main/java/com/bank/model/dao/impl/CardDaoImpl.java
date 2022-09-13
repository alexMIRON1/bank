package com.bank.model.dao.impl;

import com.bank.model.dao.CardDao;
import com.bank.model.dao.ConstantsDao;
import com.bank.model.entity.Card;
import com.bank.model.entity.CardStatus;
import com.bank.model.entity.Client;
import com.bank.model.exception.card.CreateCardException;
import com.bank.model.exception.card.DeleteCardException;
import com.bank.model.exception.card.ReadCardException;
import com.bank.model.exception.card.UpdateCardException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CardDaoImpl implements CardDao {

    private int noOfRecords;
    @Override
    public Card create(Card card) throws CreateCardException {
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(ConstantsDao.QUERY_CREATE_CARD)) {
            preparedStatement.setString(1, card.getName());
            preparedStatement.setBigDecimal(2,card.getBalance());
            preparedStatement.setInt(3, card.getCardStatus().getId());
            preparedStatement.setInt(4, card.getClient().getId());
            preparedStatement.setString(5,card.getCustomName());
            int i = preparedStatement.executeUpdate();
            if(i != 1)
                throw new SQLException();
            return card;
        } catch (SQLException e) {
            throw new CreateCardException(e.getMessage(), e);
        }
    }

    @Override
    public Card read(Integer id) throws ReadCardException{
        Card card = new Card();
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(ConstantsDao.QUERY_READ_CARD_BY_CARD_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            card.setId(resultSet.getInt(ConstantsDao.ID));
            card.setName(resultSet.getString(ConstantsDao.NAME));
            card.setBalance(resultSet.getBigDecimal(ConstantsDao.BALANCE));
            card.setCardStatus(CardStatus.getCardStatus(resultSet.getInt(ConstantsDao.CARD_STATUS_ID)));
            card.setClient(new Client(resultSet.getInt(ConstantsDao.CLIENT_ID)));
            card.setCustomName(resultSet.getString(ConstantsDao.NAME_CUSTOM));
            return card;
        } catch (SQLException e) {
            throw new ReadCardException(e.getMessage(), e);
        }
    }

    @Override
    public Card update(Card card) throws UpdateCardException{
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(ConstantsDao.QUERY_UPDATE_CARD)) {
            preparedStatement.setBigDecimal(1, card.getBalance());
            preparedStatement.setInt(2, card.getCardStatus().getId());
            preparedStatement.setString(3, card.getCustomName());
            preparedStatement.setInt(4, card.getId());
            int i = preparedStatement.executeUpdate();
            if(i != 1)
                throw new SQLException();
            return card;
        } catch (SQLException e) {
            throw new UpdateCardException(e.getMessage(), e);
        }
    }
    @Override
    public List<Card> transferCard(Card from, Card to) throws UpdateCardException{
        try(Connection connection = getConnection()){
            connection.setAutoCommit(false);
            List<Card> cards = new ArrayList<>();
            try(PreparedStatement preparedStatementFrom = connection.prepareStatement(ConstantsDao.QUERY_UPDATE_CARD);
                PreparedStatement preparedStatementTo = connection.prepareStatement(ConstantsDao.QUERY_UPDATE_CARD)){
                preparedStatementFrom.setBigDecimal(1,from.getBalance());
                preparedStatementFrom.setInt(2,from.getCardStatus().getId());
                preparedStatementFrom.setString(3,from.getCustomName());
                preparedStatementFrom.setInt(4,from.getId());
                preparedStatementFrom.executeUpdate();

                preparedStatementTo.setBigDecimal(1,to.getBalance());
                preparedStatementTo.setInt(2,to.getCardStatus().getId());
                preparedStatementTo.setString(3,to.getCustomName());
                preparedStatementTo.setInt(4,to.getId());
                preparedStatementTo.executeUpdate();
                connection.commit();
                cards.add(from);
                cards.add(to);
                return cards;
            }catch (SQLException e){
                connection.rollback();
                throw new UpdateCardException(e.getMessage(),e);
            }
        }catch (SQLException e){
            throw  new UpdateCardException(e.getMessage(),e);
        }

    }

    @Override
    public Integer delete(Integer id) throws DeleteCardException {
        throw new UnsupportedOperationException("CardDaoImpl#delete");
    }

    @Override
    public List<Card> getCards(Client client) throws ReadCardException {
        List<Card> cards = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ConstantsDao.QUERY_READ_CARDS_BY_CLIENT_ID)) {
            preparedStatement.setInt(1, client.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Card card = new Card();
                card.setId(resultSet.getInt(ConstantsDao.ID));
                card.setName(resultSet.getString(ConstantsDao.NAME));
                card.setBalance(resultSet.getBigDecimal(ConstantsDao.BALANCE));
                card.setCardStatus(CardStatus.getCardStatus(resultSet.getInt(ConstantsDao.CARD_STATUS_ID)));
                card.setClient(new Client(resultSet.getInt(ConstantsDao.CLIENT_ID)));
                card.setCustomName(resultSet.getString(ConstantsDao.NAME_CUSTOM));
                cards.add(card);
            }
            return cards;
        } catch (SQLException e) {
            throw new ReadCardException(e.getMessage(), e);
        }
    }
    @Override
    public List<Card> getCardsOnPage(Client client, int start, int end) throws ReadCardException{
        List<Card> cards = new ArrayList<>();
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(ConstantsDao.QUERY_SHOW_CARD_PAGE)){
            preparedStatement.setInt(1,client.getId());
            preparedStatement.setInt(2,start);
            preparedStatement.setInt(3,end);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Card card = new Card();
                card.setId(resultSet.getInt(ConstantsDao.ID));
                card.setName(resultSet.getString(ConstantsDao.NAME));
                card.setBalance(resultSet.getBigDecimal(ConstantsDao.BALANCE));
                card.setCardStatus(CardStatus.getCardStatus(resultSet.getInt(ConstantsDao.CARD_STATUS_ID)));
                card.setClient(new Client(resultSet.getInt(ConstantsDao.CLIENT_ID)));
                card.setCustomName(resultSet.getString(ConstantsDao.NAME_CUSTOM));
                cards.add(card);
            }
            resultSet.close();
            resultSet = preparedStatement.executeQuery(ConstantsDao.SELECT_ROWS);
            if (resultSet.next()){
                this.noOfRecords = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new ReadCardException(e.getMessage(), e);
        }
        return cards;
    }
    @Override
    public int getNoOfRecords() {
        return noOfRecords;
    }

    @Override
    public List<Card> getCardsSortedById(Client client,int start, int end) throws ReadCardException{

        List<Card> cards = new ArrayList<>();
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(ConstantsDao.QUERY_READ_CARDS_BY_CLIENT_ID_SORTED_BY_NAME)) {
            preparedStatement.setInt(1, client.getId());
            preparedStatement.setInt(2,start);
            preparedStatement.setInt(3,end);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Card card = new Card();
                card.setId(resultSet.getInt(ConstantsDao.ID));
                card.setName(resultSet.getString(ConstantsDao.NAME));
                card.setBalance(resultSet.getBigDecimal(ConstantsDao.BALANCE));
                card.setCardStatus(CardStatus.getCardStatus(resultSet.getInt(ConstantsDao.CARD_STATUS_ID)));
                card.setClient(new Client(resultSet.getInt(ConstantsDao.CLIENT_ID)));
                card.setCustomName(resultSet.getString(ConstantsDao.NAME_CUSTOM));
                cards.add(card);
            }
            resultSet.close();
            resultSet = preparedStatement.executeQuery(ConstantsDao.SELECT_ROWS);
            if (resultSet.next()){
                this.noOfRecords = resultSet.getInt(1);
            }
            return cards;
        } catch (SQLException e)  {
            throw new ReadCardException(e.getMessage(), e);
        }
    }

    @Override
    public List<Card> getCardsSortedByName(Client client, int start, int end) throws ReadCardException {
        List<Card> cards = new ArrayList<>();
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(ConstantsDao.QUERY_READ_CARDS_BY_CLIENT_ID_SORTED_BY_CUSTOM_NAME)) {
            preparedStatement.setInt(1, client.getId());
            preparedStatement.setInt(2,start);
            preparedStatement.setInt(3,end);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Card card = new Card();
                card.setId(resultSet.getInt(ConstantsDao.ID));
                card.setName(resultSet.getString(ConstantsDao.NAME));
                card.setBalance(resultSet.getBigDecimal(ConstantsDao.BALANCE));
                card.setCardStatus(CardStatus.getCardStatus(resultSet.getInt(ConstantsDao.CARD_STATUS_ID)));
                card.setClient(new Client(resultSet.getInt(ConstantsDao.CLIENT_ID)));
                card.setCustomName(resultSet.getString(ConstantsDao.NAME_CUSTOM));
                cards.add(card);
            }
            resultSet.close();
            resultSet = preparedStatement.executeQuery(ConstantsDao.SELECT_ROWS);
            if (resultSet.next()){
                this.noOfRecords = resultSet.getInt(1);
            }
            return cards;
        } catch (SQLException e)  {
            throw new ReadCardException(e.getMessage(), e);
        }
    }

    @Override
    public List<Card> getCardsSortedByBalance(Client client, int start, int end) throws ReadCardException{
        List<Card> cards = new ArrayList<>();
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(ConstantsDao.QUERY_READ_CARDS_BY_CLIENT_ID_SORTED_BY_BALANCE)) {
            preparedStatement.setInt(1, client.getId());
            preparedStatement.setInt(2,start);
            preparedStatement.setInt(3,end);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Card card = new Card();
                card.setId(resultSet.getInt(ConstantsDao.ID));
                card.setName(resultSet.getString(ConstantsDao.NAME));
                card.setBalance(resultSet.getBigDecimal(ConstantsDao.BALANCE));
                card.setCardStatus(CardStatus.getCardStatus(resultSet.getInt(ConstantsDao.CARD_STATUS_ID)));
                card.setClient(new Client(resultSet.getInt(ConstantsDao.CLIENT_ID)));
                card.setCustomName(resultSet.getString(ConstantsDao.NAME_CUSTOM));
                cards.add(card);
            }
            resultSet.close();
            resultSet = preparedStatement.executeQuery(ConstantsDao.SELECT_ROWS);
            if (resultSet.next()){
                this.noOfRecords = resultSet.getInt(1);
            }
            return cards;
        } catch (SQLException e)  {
            throw new ReadCardException(e.getMessage(), e);
        }
    }

    @Override
    public String getLastCardName() throws ReadCardException{
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(ConstantsDao.QUERY_READ_LAST_CARD_NAME)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getString("name");
        } catch (SQLException e) {
            throw new ReadCardException(e.getMessage(), e);
        }
    }

    @Override
    public List<Card> getCardsToUnblock(int start, int end) throws ReadCardException{
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(ConstantsDao.QUERY_READ_CARDS_READY_TO_UNBLOCK)) {
            preparedStatement.setInt(1,start);
            preparedStatement.setInt(2,end);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Card> cards = new ArrayList<>();
            while (resultSet.next()) {
                Card card = new Card();
                card.setId(resultSet.getInt(ConstantsDao.ID));
                card.setName(resultSet.getString(ConstantsDao.NAME));
                card.setBalance(resultSet.getBigDecimal(ConstantsDao.BALANCE));
                card.setCardStatus(CardStatus.getCardStatus(resultSet.getInt(ConstantsDao.CARD_STATUS_ID)));
                card.setClient(new Client(resultSet.getInt(ConstantsDao.CLIENT_ID)));
                card.setCustomName(resultSet.getString(ConstantsDao.NAME_CUSTOM));
                cards.add(card);
            }
            resultSet.close();
            resultSet = preparedStatement.executeQuery(ConstantsDao.SELECT_ROWS);
            if (resultSet.next()){
                this.noOfRecords = resultSet.getInt(1);
            }
            return cards;
        } catch (SQLException e) {
            throw new ReadCardException(e.getMessage(), e);
        }
    }

    private DataSource ds;

    /**
     * set data source
     * @param ds for connection
     */
    public void setDs(DataSource ds) {
        this.ds = ds;
    }
    /**
     * gets connection
     * */
    private Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
