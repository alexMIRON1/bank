package DAOTest;

import com.bank.model.dao.CardDao;
import com.bank.model.dao.impl.CardDaoImpl;
import com.bank.model.entity.*;
import com.bank.model.exception.card.CreateCardException;
import com.bank.model.exception.card.ReadCardException;
import com.bank.model.exception.card.UpdateCardException;
import org.junit.*;
import static org.junit.Assert.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CardDAOTest {
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306";
    private static final String CONNECTION_ToDB_URL = "jdbc:mysql://localhost:3306/testDB";
    private static final String CREATE_DATABASE = "CREATE SCHEMA testDB";
    private static final String CREATE_TABLE_CARD_STATUS = Tables.CREATE_TABLE_CARD_STATUS;
    private static final String CREATE_TABLE_CLIENT_STATUS = Tables.CREATE_TABLE_CLIENT_STATUS;
    private static final String CREATE_TABLE_ROLE_CLIENT = Tables.CREATE_TABLE_ROLE_CLIENT;
    private static final String CREATE_TABLE_CLIENT = Tables.CREATE_TABLE_CLIENT;
    private static final String CREATE_TABLE_CARD = Tables.CREATE_TABLE_CARD;
    private static final String INSERT_DEFAULT_VALUE = "INSERT INTO card (id, name, balance, card_status_id, client_id, name_custom) " +
            "VALUE (DEFAULT, ?, ?, ?, ?, ?)";
    private static final String INSERT_DEFAULT_VALUE_CLIENT = "insert into client (id, login, password, create_time, client_status_id, role_id)\n" +
            "values(DEFAULT, ?, ?, DEFAULT, 1, 2 )";
    private static Connection connection;
    @BeforeClass
    public static void globalSetUp() throws SQLException {
        CardDaoImpl.flag = false;
        connection = DriverManager.getConnection(CONNECTION_URL,"root","root");
        connection.prepareStatement(CREATE_DATABASE).execute();
        connection.close();
    }
    @AfterClass
    public static void globalTearDown() throws SQLException {
        CardDaoImpl.flag= true;
        connection = DriverManager.getConnection(CONNECTION_URL,"root","root");
        connection.prepareStatement("drop schema if exists testDB").execute();
        connection.close();
    }
    private CardDao cardDao;
    private Card card;
    private Client client;
    @Before
    public void setUp() throws SQLException{
        connectionToTestDB();
        connection.createStatement().executeUpdate(CREATE_TABLE_CLIENT_STATUS);
        connection.createStatement().executeUpdate(CREATE_TABLE_ROLE_CLIENT);
        connection.createStatement().executeUpdate(CREATE_TABLE_CLIENT);
        connection.createStatement().executeUpdate(CREATE_TABLE_CARD_STATUS);
        connection.createStatement().executeUpdate(CREATE_TABLE_CARD);
        PreparedStatement preparedStatement =  connection.prepareStatement(INSERT_DEFAULT_VALUE);
        card = createCard();
        preparedStatement.setString(1, card.getName());
        preparedStatement.setInt(2, card.getBalance());
        preparedStatement.setInt(3,card.getCardStatus().getId());
        preparedStatement.setInt(4,card.getClient().getId());
        preparedStatement.setString(5,card.getCustomName());
        preparedStatement.executeUpdate();
        PreparedStatement preparedStatementClient = connection.prepareStatement(INSERT_DEFAULT_VALUE_CLIENT);
        client = createClient();
        preparedStatementClient.setString(1, client.getLogin());
        preparedStatementClient.setString(2, client.getPassword());
        preparedStatementClient.executeUpdate();
    }
    @After
    public void tearDown() throws SQLException {
        connectionToTestDB();
        connection.createStatement().executeUpdate("drop table client");
        connection.createStatement().executeUpdate("drop table client_status");
        connection.createStatement().executeUpdate("drop table role");
        connection.createStatement().executeUpdate("drop table card_status");
        connection.createStatement().executeUpdate("drop table card");
        connection.close();
    }
    @Test
    public void testCreate() throws CreateCardException {
        card.setName("0002");
        card.setBalance(200);
        card.setCustomName("Wife's card");
        Card testCard = cardDao.create(card);
        assertEquals(card.getName(),testCard.getName());
        assertEquals(card.getBalance(),testCard.getBalance());
        assertEquals(card.getCustomName(),testCard.getCustomName());
        assertEquals(card.getClient(),testCard.getClient());
    }
    @Test(expected = CreateCardException.class)
    public void testWrongDataCreate() throws CreateCardException {
        card.setName(null);
        cardDao.create(card);
    }
    @Test
    public void testRead() throws ReadCardException{
        Card testCard = cardDao.read(1);
        assertEquals(card.getName(),testCard.getName());
        assertEquals(card.getBalance(),testCard.getBalance());
        assertEquals(card.getCustomName(),testCard.getCustomName());
        assertEquals(card.getClient().getId(),testCard.getClient().getId());
    }
    @Test(expected = ReadCardException.class)
    public void testWrongDataRead() throws ReadCardException {
        cardDao.read(3);
    }
    @Test
    public void testUpdate() throws UpdateCardException {
        card.setCardStatus(CardStatus.BLOCKED);
        card.setBalance(500);
        card.setCustomName("Hobby card");
        Card testCard = cardDao.update(card);
        assertEquals(card.getName(),testCard.getName());
        assertEquals(card.getCardStatus(),testCard.getCardStatus());
        assertEquals(card.getBalance(), testCard.getBalance());
        assertEquals(card.getCustomName(),testCard.getCustomName());
        assertEquals(card.getClient().getId(),testCard.getClient().getId());
    }
    @Test(expected = NullPointerException.class)
    public void testWrongDataUpdate() throws UpdateCardException {
        cardDao.update(new Card());
    }
    @Test
    public void testGetCards() throws CreateCardException, ReadCardException, SQLException {
        card.setName("0002");
        card.setBalance(200);
        card.setCustomName("Wife's card");
        card.setId(2);
        cardDao.create(card);
        connectionToTestDB();
        List<Card> cardList = new ArrayList<>();
        Card testCard = createCard();
        testCard.setId(1);
        cardList.add(testCard);
        cardList.add(card);
        List<Card> testCards = cardDao.getCards(client);
        for (int i = 0; i < testCards.size(); i++) {
            assertEquals(cardList.get(i).getId(),testCards.get(i).getId());
            assertEquals(cardList.get(i).getName(),testCards.get(i).getName());
            assertEquals(cardList.get(i).getCardStatus(),testCards.get(i).getCardStatus());
            assertEquals(cardList.get(i).getBalance(),testCards.get(i).getBalance());
            assertEquals(cardList.get(i).getClient().getId(),testCards.get(i).getClient().getId());
            assertEquals(cardList.get(i).getCustomName(),testCards.get(i).getCustomName());
        }
    }
    @Test
    public void testGetCardsSortById() throws CreateCardException, SQLException, ReadCardException {
        card.setName("0002");
        card.setBalance(200);
        card.setCustomName("Wife's card");
        card.setId(2);
        cardDao.create(card);
        connectionToTestDB();
        List<Card> cardList = new ArrayList<>();
        Card testCard = createCard();
        testCard.setId(1);
        cardList.add(testCard);
        cardList.add(card);
        cardList.sort(Comparator.comparing(Card::getName));
        List<Card> testCards = cardDao.getCardsSortedById(client,0,1);
        for (int i = 0; i < testCards.size(); i++) {
            assertEquals(cardList.get(i).getId(),testCards.get(i).getId());
            assertEquals(cardList.get(i).getName(),testCards.get(i).getName());
            assertEquals(cardList.get(i).getCardStatus(),testCards.get(i).getCardStatus());
            assertEquals(cardList.get(i).getBalance(),testCards.get(i).getBalance());
            assertEquals(cardList.get(i).getClient().getId(),testCards.get(i).getClient().getId());
            assertEquals(cardList.get(i).getCustomName(),testCards.get(i).getCustomName());
        }
    }
    @Test
    public void testGetCardsSortedByName() throws CreateCardException, SQLException, ReadCardException {
        card.setName("0002");
        card.setBalance(200);
        card.setCustomName("Wife's card");
        card.setId(2);
        cardDao.create(card);
        connectionToTestDB();
        List<Card> cardList = new ArrayList<>();
        Card testCard = createCard();
        testCard.setId(1);
        cardList.add(testCard);
        cardList.add(card);
        cardList.sort(Comparator.comparing(Card::getCustomName));
        List<Card> testCards = cardDao.getCardsSortedByName(client,0,1);
        for (int i = 0; i < testCards.size(); i++) {
            assertEquals(cardList.get(i).getId(),testCards.get(i).getId());
            assertEquals(cardList.get(i).getName(),testCards.get(i).getName());
            assertEquals(cardList.get(i).getCardStatus(),testCards.get(i).getCardStatus());
            assertEquals(cardList.get(i).getBalance(),testCards.get(i).getBalance());
            assertEquals(cardList.get(i).getClient().getId(),testCards.get(i).getClient().getId());
            assertEquals(cardList.get(i).getCustomName(),testCards.get(i).getCustomName());
        }
    }
    @Test
    public void testGetCardsSortedByBalance() throws CreateCardException, SQLException, ReadCardException {
        card.setName("0002");
        card.setBalance(200);
        card.setCustomName("Wife's card");
        card.setId(2);
        cardDao.create(card);
        connectionToTestDB();
        List<Card> cardList = new ArrayList<>();
        Card testCard = createCard();
        testCard.setId(1);
        cardList.add(testCard);
        cardList.add(card);
        cardList.sort(Comparator.comparing(Card::getBalance).reversed());
        List<Card> testCards = cardDao.getCardsSortedByBalance(client,0,1);
        for (int i = 0; i < testCards.size(); i++) {
            assertEquals(cardList.get(i).getId(),testCards.get(i).getId());
            assertEquals(cardList.get(i).getName(),testCards.get(i).getName());
            assertEquals(cardList.get(i).getCardStatus(),testCards.get(i).getCardStatus());
            assertEquals(cardList.get(i).getBalance(),testCards.get(i).getBalance());
            assertEquals(cardList.get(i).getClient().getId(),testCards.get(i).getClient().getId());
            assertEquals(cardList.get(i).getCustomName(),testCards.get(i).getCustomName());
        }
    }
    @Test
    public void testGetLastCardName() throws CreateCardException, SQLException, ReadCardException {
        card.setName("0002");
        card.setBalance(200);
        card.setCustomName("Wife's card");
        card.setId(2);
        cardDao.create(card);
        connectionToTestDB();
        String testName = cardDao.getLastCardName();
        assertEquals(card.getName(),testName);
    }
    @Test
    public void testGetCardsToUnBlock() throws CreateCardException, SQLException, ReadCardException {
        card.setName("0002");
        card.setBalance(200);
        card.setCustomName("Wife's card");
        card.setId(2);
        card.setCardStatus(CardStatus.READY_TO_UNBLOCK);
        cardDao.create(card);
        connectionToTestDB();
        List<Card> cardList = new ArrayList<>();
        cardList.add(card);
        List<Card> testCards = cardDao.getCardsToUnblock();
        for (int i = 0; i < testCards.size(); i++) {
            assertEquals(cardList.get(i).getCardStatus(),testCards.get(i).getCardStatus());
            assertEquals(cardList.get(i).getBalance(),testCards.get(i).getBalance());
            assertEquals(cardList.get(i).getClient().getId(),testCards.get(i).getClient().getId());
            assertEquals(cardList.get(i).getCustomName(),testCards.get(i).getCustomName());
            assertEquals(cardList.get(i).getId(),testCards.get(i).getId());
            assertEquals(cardList.get(i).getName(),testCards.get(i).getName());
        }
    }
    private Card createCard(){
        Card creatingCard = new Card();
        creatingCard.setId(1);
        creatingCard.setName("0001");
        creatingCard.setBalance(100);
        creatingCard.setCardStatus(CardStatus.UNBLOCKED);
        creatingCard.setClient(createClient());
        creatingCard.setCustomName("My card");
        return creatingCard;
    }
    private Client createClient(){
        Client client = new Client();
        client.setId(1);
        client.setClientStatus(ClientStatus.UNBLOCKED);
        client.setPassword("aalex");
        client.setLogin("aalex");
        client.setRole(Role.CLIENT);
        return client;
    }
    private void connectionToTestDB() throws SQLException {
        connection = DriverManager.getConnection(CONNECTION_ToDB_URL,"root","root");
        cardDao = new CardDaoImpl(connection);
    }
}
