package com.bank.model.dao.impl;

import com.bank.model.dao.ClientDao;
import com.bank.model.dao.ConstantsDao;
import com.bank.model.entity.Client;
import com.bank.model.entity.ClientStatus;
import com.bank.model.entity.Role;
import com.bank.model.exception.client.CreateClientException;
import com.bank.model.exception.client.DeleteClientException;
import com.bank.model.exception.client.ReadClientException;
import com.bank.model.exception.client.UpdateClientException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ClientDaoImpl implements ClientDao {

    private int noOfRecords;
    @Override
    public Client create(Client client) throws CreateClientException{
        try(Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(ConstantsDao.QUERY_CREATE_CLIENT)) {
            preparedStatement.setString(1, client.getLogin());
            preparedStatement.setString(2, client.getPassword());
            preparedStatement.setInt(3, client.getClientStatus().getId());
            preparedStatement.setInt(4, client.getRole().getId());
            int i = preparedStatement.executeUpdate();
            if(i != 1)
                throw new SQLException();
            return client;
        } catch (SQLException e) {
            throw new CreateClientException(e);
        }
    }

    @Override
    public Client read(Integer id) throws ReadClientException {
        Client client = new Client();
        try(Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(ConstantsDao.QUERY_READ_CLIENT_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            client.setId(resultSet.getInt(ConstantsDao.ID));
            client.setLogin(resultSet.getString(ConstantsDao.LOGIN));
            client.setPassword(resultSet.getString(ConstantsDao.PASSWORD));
            client.setClientStatus(ClientStatus.getClientStatus(resultSet.getInt(ConstantsDao.CLIENT_STATUS_ID)));
            client.setRole(Role.getRole(resultSet.getInt(ConstantsDao.ROLE_ID)));
            return client;
        } catch (SQLException e) {
            throw new ReadClientException(e);
        }
    }

    @Override
    public Client update(Client client) throws UpdateClientException {
        try(Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(ConstantsDao.QUERY_UPDATE_CLIENT)) {
            preparedStatement.setInt(1, client.getClientStatus().getId());
            preparedStatement.setInt(2, client.getId());
            int i = preparedStatement.executeUpdate();
            if(i != 1)
                throw new SQLException();
            return client;
        } catch (SQLException e) {
            throw new UpdateClientException(e);
        }
    }

    @Override
    public Integer delete(Integer id) throws DeleteClientException {
        throw new UnsupportedOperationException("ClientDaoImpl#delete");
    }

    @Override
    public Client getClient(String login) throws ReadClientException {
        Client client = new Client();
        try(Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(ConstantsDao.QUERY_READ_CLIENT_BY_LOGIN)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            client.setId(resultSet.getInt(ConstantsDao.ID));
            client.setLogin(resultSet.getString(ConstantsDao.LOGIN));
            client.setPassword(resultSet.getString(ConstantsDao.PASSWORD));
            client.setClientStatus(ClientStatus.getClientStatus(resultSet.getInt(ConstantsDao.CLIENT_STATUS_ID)));
            client.setRole(Role.getRole(resultSet.getInt(ConstantsDao.ROLE_ID)));
            return client;
        } catch (SQLException e) {
            throw new ReadClientException(e);
        }
    }

    @Override
    public List<Client> getClients(int start, int end) throws ReadClientException {
        List<Client> clients = new ArrayList<>();
        try(Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(ConstantsDao.QUERY_READ_CLIENTS)) {
            preparedStatement.setInt(1,start);
            preparedStatement.setInt(2,end);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Client client = new Client();
                client.setId(resultSet.getInt(ConstantsDao.ID));
                client.setLogin(resultSet.getString(ConstantsDao.LOGIN));
                client.setPassword(resultSet.getString(ConstantsDao.PASSWORD));
                client.setClientStatus(ClientStatus.getClientStatus(resultSet.getInt(ConstantsDao.CLIENT_STATUS_ID)));
                client.setRole(Role.getRole(resultSet.getInt(ConstantsDao.ROLE_ID)));
                clients.add(client);
            }
            resultSet.close();
            resultSet = preparedStatement.executeQuery(ConstantsDao.SELECT_ROWS);
            if (resultSet.next()){
                this.noOfRecords = resultSet.getInt(1);
            }
            return clients;
        } catch (SQLException e) {
            throw new ReadClientException(e);
        }
    }
    @Override
    public int getNoOfRecords() {
        return noOfRecords;
    }

    /**
     * gets connection
     * */
    private Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    private DataSource ds;

    /**
     * set data source
     * @param ds for connection
     */
    public void setDs(DataSource ds) {
        this.ds = ds;
    }
}
