package com.bank.model.dao.impl;

import com.bank.model.dao.ClientDao;
import com.bank.model.entity.Client;
import com.bank.model.entity.ClientStatus;
import com.bank.model.entity.Role;
import com.bank.model.exception.ConnectionException;
import com.bank.model.exception.client.CreateClientException;
import com.bank.model.exception.client.DeleteClientException;
import com.bank.model.exception.client.ReadClientException;
import com.bank.model.exception.client.UpdateClientException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDaoImpl implements ClientDao {

    // change to yours
    private  Connection conn;
    public static boolean flag = true;

    // queries
    private static final String QUERY_READ_CLIENTS = "SELECT * FROM client WHERE role_id=2";
    private static final String QUERY_READ_CLIENT_BY_ID = "SELECT * FROM client WHERE id=?";
    private static final String QUERY_READ_CLIENT_BY_LOGIN = "SELECT * FROM client WHERE login=?";
    private static final String QUERY_CREATE_CLIENT = "INSERT INTO client" +
            "(id, login, password, create_time, client_status_id, role_id)" +
            " value (DEFAULT, ?, ?, DEFAULT, ?, ?)";
    private static final String QUERY_UPDATE_CLIENT = "UPDATE client SET client_status_id=? where id=?";

    public ClientDaoImpl() {
    }
    public ClientDaoImpl(Connection connection){
       conn = connection;
    }

    @Override
    public Client create(Client client) throws CreateClientException{
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(QUERY_CREATE_CLIENT)) {
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
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(QUERY_READ_CLIENT_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            client.setId(resultSet.getInt("id"));
            client.setLogin(resultSet.getString("login"));
            client.setPassword(resultSet.getString("password"));
            client.setClientStatus(ClientStatus.getClientStatus(resultSet.getInt("client_status_id")));
            client.setRole(Role.getRole(resultSet.getInt("role_id")));
            return client;
        } catch (SQLException e) {
            throw new ReadClientException(e);
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new ConnectionException(e.getMessage(),e);
            }
        }
    }

    @Override
    public Client update(Client client) throws UpdateClientException {
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(QUERY_UPDATE_CLIENT)) {
            preparedStatement.setInt(1, client.getClientStatus().getId());
            preparedStatement.setInt(2, client.getId());
            int i = preparedStatement.executeUpdate();
            if(i != 1)
                throw new SQLException();
            return client;
        } catch (SQLException e) {
            throw new UpdateClientException(e);
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new ConnectionException(e.getMessage(),e);
            }
        }
    }

    @Override
    public Integer delete(Integer id) throws DeleteClientException {
        throw new UnsupportedOperationException("ClientDaoImpl#delete");
    }

    @Override
    public Client getClient(String login) throws ReadClientException {
        Client client = new Client();
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(QUERY_READ_CLIENT_BY_LOGIN)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            client.setId(resultSet.getInt("id"));
            client.setLogin(resultSet.getString("login"));
            client.setPassword(resultSet.getString("password"));
            client.setClientStatus(ClientStatus.getClientStatus(resultSet.getInt("client_status_id")));
            client.setRole(Role.getRole(resultSet.getInt("role_id")));
            return client;
        } catch (SQLException e) {
            throw new ReadClientException(e);
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new ConnectionException(e.getMessage(),e);
            }
        }
    }

    @Override
    public List<Client> getClients() throws ReadClientException {
        List<Client> clients = new ArrayList<>();
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(QUERY_READ_CLIENTS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Client client = new Client();
                client.setId(resultSet.getInt("id"));
                client.setLogin(resultSet.getString("login"));
                client.setPassword(resultSet.getString("password"));
                client.setClientStatus(ClientStatus.getClientStatus(resultSet.getInt("client_status_id")));
                client.setRole(Role.getRole(resultSet.getInt("role_id")));
                clients.add(client);
            }
            return clients;
        } catch (SQLException e) {
            throw new ReadClientException(e);
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new ConnectionException(e.getMessage(),e);
            }
        }
    }

    /**
     * gets connection
     * */
    public   Connection getConnection() {
        try {
            if(flag){
                InitialContext initContext = new InitialContext();
                DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/mysql/bank");
                conn = ds.getConnection();
            }
            return conn;
        } catch (NamingException | SQLException e) {
            throw new ConnectionException("Fail to obtain connection..", e);
        }
    }
}
