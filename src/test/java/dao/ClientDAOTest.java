package dao;

import com.bank.model.dao.ClientDao;
import com.bank.model.dao.impl.ClientDaoImpl;
import com.bank.model.entity.Client;
import com.bank.model.entity.ClientStatus;
import com.bank.model.entity.Role;
import com.bank.model.exception.client.CreateClientException;
import com.bank.model.exception.client.ReadClientException;
import com.bank.model.exception.client.UpdateClientException;
import com.mysql.cj.jdbc.MysqlDataSource;
import com.mysql.cj.jdbc.MysqlDataSourceFactory;
import org.apache.taglibs.standard.tag.common.sql.DataSourceWrapper;
import org.junit.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class ClientDAOTest {
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306";
    private static final String CONNECTION_ToDB_URL = "jdbc:mysql://localhost:3306/testDB";
    private static final String CREATE_DATABASE = "CREATE SCHEMA testDB";
    private static final String CREATE_TABLE_CLIENT_STATUS = Tables.CREATE_TABLE_CLIENT_STATUS;
    private static final String CREATE_TABLE_ROLE_CLIENT = Tables.CREATE_TABLE_ROLE_CLIENT;
    private static final String CREATE_TABLE_CLIENT = Tables.CREATE_TABLE_CLIENT;
    private static final String INSERT_DEFAULT_VALUE = "insert into client (id, login, password, create_time, client_status_id, role_id)\n" +
            "values(DEFAULT, ?, ?, DEFAULT, 1, 2 )";
    private static Connection connection;

    @BeforeClass
    public static void globalSetUp() throws SQLException, NamingException {
       connection = DriverManager.getConnection(CONNECTION_URL,"root","root");
       connection.prepareStatement(CREATE_DATABASE).execute();
       connection.close();
    }
    @AfterClass
    public static void globalTearDown() throws SQLException {
        connection = DriverManager.getConnection(CONNECTION_URL,"root","root");
        connection.prepareStatement("drop schema if exists testDB").execute();
        connection.close();
    }
    private ClientDao clientDao;
    private Client client;
    @Before
    public void setUp() throws SQLException{
        connectionToTestDB();
        connection.createStatement().executeUpdate(CREATE_TABLE_CLIENT_STATUS);
        connection.createStatement().executeUpdate(CREATE_TABLE_ROLE_CLIENT);
        connection.createStatement().executeUpdate(CREATE_TABLE_CLIENT);
       PreparedStatement preparedStatement =  connection.prepareStatement(INSERT_DEFAULT_VALUE);
       client = createClient();
       preparedStatement.setString(1, client.getLogin());
       preparedStatement.setString(2, client.getPassword());
       preparedStatement.executeUpdate();
    }
    @After
    public void tearDown() throws SQLException {
        connectionToTestDB();
        connection.createStatement().executeUpdate("drop table client");
        connection.createStatement().executeUpdate("drop table client_status");
        connection.createStatement().executeUpdate("drop table role");
        connection.close();
    }
    @Test
    public void testCreate() throws CreateClientException {
        client.setLogin("kolya");
        client.setPassword("kolya");
        Client testClient = clientDao.create(client);
        assertEquals(client.getLogin(),testClient.getLogin());
        assertEquals(client.getPassword(), testClient.getPassword());
        assertEquals(client.getRole(),testClient.getRole());
        assertEquals(client.getClientStatus(),testClient.getClientStatus());
    }
    @Test(expected = CreateClientException.class)
    public void testWrongPasswordClientCreate() throws CreateClientException {
        client.setPassword(null);
        clientDao.create(client);
    }
    @Test
    public void testRead() throws ReadClientException{
        Client testClient = clientDao.read(1);
        assertEquals(Optional.of(1), Optional.of(testClient.getId()));
        assertEquals(createClient().getLogin(), testClient.getLogin());
        assertEquals(createClient().getPassword(), testClient.getPassword());
        assertEquals(Role.CLIENT, testClient.getRole());
        assertEquals(ClientStatus.UNBLOCKED,testClient.getClientStatus());
    }
    @Test(expected = NullPointerException.class)
    public void testNullIdRead() throws ReadClientException {
        Client client = createClient();
        clientDao.read(client.getId());
    }
    @Test(expected = ReadClientException.class)
    public void testWrongIdRead() throws ReadClientException{
        clientDao.read(2);
    }
    @Test
    public void testUpdate() throws UpdateClientException, ReadClientException, SQLException {
        Client daoClient = clientDao.getClient(client.getLogin());
        connectionToTestDB();
        daoClient.setClientStatus(ClientStatus.BLOCKED);
        Client testClient =  clientDao.update(daoClient);
        assertEquals(daoClient.getClientStatus(),testClient.getClientStatus());
    }
    @Test(expected = NullPointerException.class)
    public void testNullIdUpdate() throws UpdateClientException {
        client.setClientStatus(ClientStatus.BLOCKED);
        clientDao.update(client);
    }
    @Test(expected = NullPointerException.class)
    public void testWrongStatus() throws UpdateClientException {
        Client client = createClient();
        client.setId(1);
        client.setClientStatus(null);
        clientDao.update(client);
    }
    @Test
    public void testGetClient() throws ReadClientException {
        Client testClient = clientDao.getClient(client.getLogin());
        assertEquals(client.getLogin(),testClient.getPassword());
        assertEquals(client.getClientStatus(),testClient.getClientStatus());
        assertEquals(client.getRole(), testClient.getRole());
    }
    @Test(expected = ReadClientException.class)
    public void testWrongLoginGetClient() throws ReadClientException {
        clientDao.getClient(null);
    }
    @Test
    public void testGetClients() throws CreateClientException, ReadClientException {
        client.setLogin("kolya");
        client.setPassword("kolya");
        client.setId(2);
        clientDao.create(client);
        List<Client> clients = new ArrayList<>();
        Client client1 = createClient();
        client1.setId(1);
        clients.add(client1);
        clients.add(client);
        List<Client> testClients = clientDao.getClients(0 ,1);
        for (int i = 0; i < testClients.size(); i++) {
            assertEquals(clients.get(i).getClientStatus(),testClients.get(i).getClientStatus());
            assertEquals(clients.get(i).getLogin(),testClients.get(i).getLogin());
            assertEquals(clients.get(i).getPassword(),testClients.get(i).getPassword());
            assertEquals(clients.get(i).getRole(),testClients.get(i).getRole());
            assertEquals(clients.get(i).getId(),testClients.get(i).getId());
        }
    }
    private static Client createClient(){
        Client client = new Client();
        client.setClientStatus(ClientStatus.UNBLOCKED);
        client.setPassword("aalex");
        client.setLogin("aalex");
        client.setRole(Role.CLIENT);
        return client;
    }
    private void connectionToTestDB() throws SQLException {
        connection = DriverManager.getConnection(CONNECTION_ToDB_URL,"root","root");
        clientDao = new ClientDaoImpl(connection);
    }
}
