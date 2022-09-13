package dao;

import com.bank.model.dao.BillDao;
import com.bank.model.dao.ClientDao;
import com.bank.model.dao.impl.BillDaoImpl;
import com.bank.model.dao.impl.DaoEnum;
import com.bank.model.dao.impl.FactoryDao;
import com.bank.model.entity.Bill;
import com.bank.model.entity.BillStatus;
import com.bank.model.entity.Card;
import com.bank.model.exception.bill.CreateBillException;
import com.bank.model.exception.bill.DeleteBillException;
import com.bank.model.exception.bill.ReadBillException;
import com.bank.model.exception.bill.UpdateBillException;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.*;

import javax.naming.NamingException;
import javax.sql.DataSource;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class BillDAOTest {
    private static final FactoryDao factoryDao = FactoryDao.getInstance();
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306";
    private static final String CONNECTION_ToDB_URL = "jdbc:mysql://localhost:3306/testDB";
    private static final String CREATE_DATABASE = "CREATE SCHEMA testDB";
    private static final String CREATE_TABLE_BILL_STATUS = Tables.CREATE_TABLE_BILL_STATUS;
    private static final String CREATE_TABLE_BILL = Tables.CREATE_TABLE_BILL;
    private static final String INSERT_DEFAULT_VALUE_BILL = "INSERT INTO bill (id, sum, date, card_id, bill_status_id) VALUE " +
            "(DEFAULT, ?, DEFAULT, ?, ?)";
    private static Connection connection;
    @BeforeClass
    public static void globalSetUp() throws SQLException{
        connection = getDsCreateSchema().getConnection();
        connection.prepareStatement(CREATE_DATABASE).execute();
        connection.close();
    }
    @AfterClass
    public static void globalTearDown() throws SQLException {
        connection = getDsCreateSchema().getConnection();
        connection.prepareStatement("drop schema if exists testDB").execute();
        connection.close();
    }
    private BillDao billDao;
    private Bill bill;
    @Before
    public void setUp() throws SQLException {
        connectionToTestDB();
        connection.createStatement().executeUpdate(CREATE_TABLE_BILL_STATUS);
        connection.createStatement().executeUpdate(CREATE_TABLE_BILL);
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_DEFAULT_VALUE_BILL);
        bill = createBill();
        preparedStatement.setBigDecimal(1,bill.getSum());
        preparedStatement.setInt(2,bill.getCard().getId());
        preparedStatement.setInt(3,bill.getBillStatus().getId());
        preparedStatement.executeUpdate();
    }
    @After
    public void tearDown() throws SQLException {
        connectionToTestDB();
        connection.createStatement().executeUpdate("DROP  table bill_status");
        connection.createStatement().executeUpdate("DROP table  bill");
        connection.close();
    }
    @Test
    public void testCreate() throws CreateBillException {
        bill.setSum(BigDecimal.valueOf(200));
        bill.setBillStatus(BillStatus.READY);
        bill.setRecipient("0000");
        Bill testBill = billDao.create(bill);
        assertEquals(bill.getSum(),testBill.getSum());
        assertEquals(bill.getId(),testBill.getId());
        assertEquals(bill.getBillStatus(),testBill.getBillStatus());
        assertEquals(bill.getDate(),testBill.getDate());
        assertEquals(bill.getCard().getId(),testBill.getCard().getId());
    }
    @Test(expected = CreateBillException.class)
    public void testWrongDataCreate() throws CreateBillException {
        bill.setSum(null);
        billDao.create(bill);
    }
    @Test
    public void testRead() throws ReadBillException {
        Bill testBill = billDao.read(bill.getId());
        assertEquals(bill.getSum(),testBill.getSum());
        assertEquals(bill.getId(),testBill.getId());
        assertEquals(bill.getBillStatus(),testBill.getBillStatus());
        assertEquals(bill.getCard().getId(),testBill.getCard().getId());
        bill.setDate(new Date());
        assertEquals(bill.getDate().getDate(),testBill.getDate().getDate());
    }
    @Test(expected = ReadBillException.class)
    public void testWrongDataRead() throws ReadBillException {
        billDao.read(3);
    }
    @Test
    public void testUpdate() throws UpdateBillException {
        bill.setBillStatus(BillStatus.PAID);
        bill.setRecipient("0000");
        Bill testBill = billDao.update(bill);
        assertEquals(bill.getSum(),testBill.getSum());
        assertEquals(bill.getId(),testBill.getId());
        assertEquals(bill.getBillStatus(),testBill.getBillStatus());
        assertEquals(bill.getCard().getId(),testBill.getCard().getId());
        bill.setDate(new Date());
        assertEquals(bill.getDate().getDate(),testBill.getDate().getDate());
    }
    @Test(expected = NullPointerException.class)
    public void testWrongDataUpdate() throws UpdateBillException {
        billDao.update(new Bill());
    }
    @Test
    public void testDelete() throws DeleteBillException, ReadBillException, SQLException {
        billDao.delete(bill.getId());
        connectionToTestDB();
        assertEquals(new ArrayList<>(),billDao.getBills(bill.getCard()));
    }
    @Test(expected = DeleteBillException.class)
    public void testWrongDelete() throws DeleteBillException {
        billDao.delete(4);
    }
    @Test
    public void testGetBills() throws CreateBillException, SQLException, ReadBillException {
        bill.setBillStatus(BillStatus.PAID);
        bill.setSum(BigDecimal.valueOf(500.11));
        bill.setId(2);
        bill.setRecipient("0000");
        billDao.create(bill);
        connectionToTestDB();
        List<Bill> bills = new ArrayList<>();
        Bill testBill = createBill();
        testBill.setDate(new Date());
        testBill.setId(1);
        bills.add(testBill);
        bill.setDate(new Date());
        bills.add(bill);
        List<Bill> testBills = billDao.getBills(bill.getCard());
        for (int i = 0; i < testBills.size(); i++) {
            assertEquals(bills.get(i).getId(),testBills.get(i).getId());
            assertEquals(bills.get(i).getBillStatus(),testBills.get(i).getBillStatus());
            assertEquals(bills.get(i).getDate().getDate(),testBills.get(i).getDate().getDate());
            assertEquals(bills.get(i).getCard().getId(),testBills.get(i).getCard().getId());
            assertEquals(bills.get(i).getSum(),testBills.get(i).getSum());
        }
    }
    @Test
    public void testGetBillsSortedById() throws ReadBillException, CreateBillException, SQLException {
        bill.setBillStatus(BillStatus.PAID);
        bill.setSum(BigDecimal.valueOf(500.11));
        bill.setId(2);
        bill.setRecipient("0000");
        billDao.create(bill);
        connectionToTestDB();
        List<Bill> bills = new ArrayList<>();
        Bill testBill = createBill();
        testBill.setDate(new Date());
        testBill.setId(1);
        bills.add(testBill);
        bill.setDate(new Date());
        bills.add(bill);
        bills.sort(Comparator.comparing(Bill::getId));
        List<Bill> testBills = billDao.getBillsSortedById(bill.getCard(),0,1);
        for (int i = 0; i < testBills.size(); i++) {
            assertEquals(bills.get(i).getId(),testBills.get(i).getId());
            assertEquals(bills.get(i).getBillStatus(),testBills.get(i).getBillStatus());
            assertEquals(bills.get(i).getDate().getDate(),testBills.get(i).getDate().getDate());
            assertEquals(bills.get(i).getCard().getId(),testBills.get(i).getCard().getId());
            assertEquals(bills.get(i).getSum(),testBills.get(i).getSum());
        }
    }
    @Test
    public void testGetBillsSortedByDate() throws CreateBillException, SQLException, ReadBillException {
        bill.setBillStatus(BillStatus.PAID);
        bill.setSum(BigDecimal.valueOf(500.11));
        bill.setId(2);
        bill.setRecipient("0000");
        billDao.create(bill);
        connectionToTestDB();
        List<Bill> bills = new ArrayList<>();
        Bill testBill = createBill();
        testBill.setDate(new Date());
        testBill.setId(1);
        bills.add(testBill);
        bill.setDate(new Date());
        bills.add(bill);
        bills.sort(Comparator.comparing(Bill::getDate));
        List<Bill> testBills = billDao.getBillsSortedByDate(bill.getCard(),0,1);
        for (int i = 0; i < testBills.size(); i++) {
            assertEquals(bills.get(i).getId(),testBills.get(i).getId());
            assertEquals(bills.get(i).getBillStatus(),testBills.get(i).getBillStatus());
            assertEquals(bills.get(i).getDate().getDate(),testBills.get(i).getDate().getDate());
            assertEquals(bills.get(i).getCard().getId(),testBills.get(i).getCard().getId());
            assertEquals(bills.get(i).getSum(),testBills.get(i).getSum());
        }
    }
    @Test
    public void testGetBillsSortedByDateDesc() throws CreateBillException, SQLException, ReadBillException {
        bill.setBillStatus(BillStatus.PAID);
        bill.setSum(BigDecimal.valueOf(500.11));
        bill.setId(2);
        bill.setRecipient("0000");
        billDao.create(bill);
        connectionToTestDB();
        List<Bill> bills = new ArrayList<>();
        Bill testBill = createBill();
        testBill.setDate(new Date());
        testBill.setId(1);
        bills.add(testBill);
        bill.setDate(new Date());
        bills.add(bill);
        bills.sort(Comparator.comparing(Bill::getDate).reversed());
        List<Bill> testBills = billDao.getBillsSortedByDateDesc(bill.getCard(),0,1);
        for (int i = 0; i < testBills.size(); i++) {
            assertEquals(bills.get(i).getId(),testBills.get(i).getId());
            assertEquals(bills.get(i).getBillStatus(),testBills.get(i).getBillStatus());
            assertEquals(bills.get(i).getDate().getDate(),testBills.get(i).getDate().getDate());
            assertEquals(bills.get(i).getCard().getId(),testBills.get(i).getCard().getId());
            assertEquals(bills.get(i).getSum(), testBills.get(i).getSum());
        }
    }

    private Bill createBill(){
        Bill creatingBill = new Bill();
        creatingBill.setId(1);
        creatingBill.setSum(BigDecimal.valueOf(500.11));
        creatingBill.setCard(new Card(1));
        creatingBill.setBillStatus(BillStatus.READY);
        return creatingBill;
    }
    private void connectionToTestDB() throws SQLException {
        billDao = (BillDao) factoryDao.getDao(DaoEnum.BILL_DAO);
        billDao.setDs(getDs());
        connection = getDs().getConnection();
    }

    private DataSource getDs() {
        MysqlDataSource ds = new MysqlDataSource();
        ds.setUrl(CONNECTION_ToDB_URL);
        ds.setUser("root");
        ds.setPassword("root");
        return ds;
    }
    private static DataSource getDsCreateSchema() {
        MysqlDataSource ds = new MysqlDataSource();
        ds.setUrl(CONNECTION_URL);
        ds.setUser("root");
        ds.setPassword("root");
        return ds;
    }
}
