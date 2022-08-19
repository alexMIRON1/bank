//import com.bank.model.dao.ClientDao;
//import com.bank.model.dao.impl.ClientDaoImpl;
//import com.bank.model.entity.Client;
//import com.bank.model.entity.ClientStatus;
//import com.bank.model.entity.Role;
//import com.bank.model.exception.client.CreateClientException;
//import com.bank.model.exception.client.ReadClientException;
//import org.junit.*;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//
//import static org.mockito.Matchers.anyInt;
//import static org.mockito.Matchers.anyString;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import static org.junit.Assert.*;
//
//import javax.sql.DataSource;
//import java.sql.*;
//@RunWith(MockitoJUnitRunner.class)
//public class DaoTest{
//    @Mock
//    private  static DataSource mockDataSource;
//    @Mock
//    private Connection mockConn;
//    @Mock
//    private PreparedStatement mockPreparedStmnt;
//    @Mock
//    private ResultSet mockResultSet;
//    private static ClientDao clientDao;
//
//    @BeforeClass
//    public static void globalSetUp(){
//    }
//    @AfterClass
//    public static void globalTearDown() throws SQLException {
//    }
//    @Before
//    public void setUp() throws SQLException {
//        when(mockDataSource.getConnection()).thenReturn(mockConn);
//        when(mockDataSource.getConnection(anyString(), anyString())).thenReturn(mockConn);
//        doNothing().when(mockConn).commit();
//        when(mockConn.prepareStatement(anyString())).thenReturn(mockPreparedStmnt);
//        doNothing().when(mockPreparedStmnt).setString(anyInt(), anyString());
//        doNothing().when(mockPreparedStmnt).setInt(anyInt(),anyInt());
//        when(mockPreparedStmnt.executeUpdate()).thenReturn(1);
//        when(mockPreparedStmnt.executeQuery()).thenReturn(mockResultSet);
//        when(mockResultSet.next()).thenReturn(Boolean.TRUE,Boolean.FALSE);
//        when(mockResultSet.getInt("id")).thenReturn(createClient().getId());
//        when(mockResultSet.getString("login")).thenReturn(createClient().getLogin());
//        when(mockResultSet.getString("password")).thenReturn(createClient().getPassword());
//        when(mockResultSet.getInt("client_status_id")).thenReturn(createClient().getClientStatus().getId());
//        when(mockResultSet.getInt("role_id")).thenReturn(createClient().getRole().getId());
//        clientDao = new ClientDaoImpl(mockDataSource);
//    }
//    @After
//    public void tearDown() throws SQLException{
//    }
//    @Test
//    public void testGetConnection(){
//        assertNotNull(clientDao);
//        try (Connection connection = clientDao.getConnection()) {
//        } catch (SQLException e) {
//            fail("Cannot get connection");
//        }
//    }
//    @Test
//    public void testCreate() throws SQLException, CreateClientException {
//        Client testClient = clientDao.create(createClient());
//        verify(mockConn, times(1)).prepareStatement(anyString());
//        verify(mockPreparedStmnt, times(2)).setString(anyInt(), anyString());
//        verify(mockPreparedStmnt,times(2)).setInt(anyInt(),anyInt());
//        verify(mockPreparedStmnt, times(1)).executeUpdate();
//        testingClient(testClient);
//    }
//    @Test
//    public void testRead() throws ReadClientException, SQLException {
//       Client testClient = clientDao.read(createClient().getId());
//       verify(mockConn,times(1)).prepareStatement(anyString());
//       verify(mockPreparedStmnt,times(1)).setInt(anyInt(),anyInt());
//       verify(mockPreparedStmnt,times(1)).executeQuery();
//       verify(mockResultSet,times(1)).next();
//       verify(mockResultSet,times(1)).getInt("id");
//       verify(mockResultSet,times(1)).getInt("client_status_id");
//       verify(mockResultSet,times(1)).getInt("role_id");
//       verify(mockResultSet,times(1)).getString("login");
//       verify(mockResultSet,times(1)).getString("password");
//       testingClient(testClient);
//    }
//    private Client createClient(){
//        Client client = new Client();
//        client.setId(1);
//        client.setClientStatus(ClientStatus.UNBLOCKED);
//        client.setPassword("aalex");
//        client.setLogin("aalex");
//        client.setRole(Role.CLIENT);
//        return client;
//    }
//    private void testingClient(Client testClient){
//        assertEquals(createClient().getLogin(), testClient.getLogin());
//        assertEquals(createClient().getPassword(),testClient.getPassword());
//        assertEquals(createClient().getRole(),testClient.getRole());
//        assertEquals(createClient().getId(),testClient.getId());
//        assertEquals(createClient().getClientStatus(),testClient.getClientStatus());
//    }
//}
