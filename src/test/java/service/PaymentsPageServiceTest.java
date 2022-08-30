package service;

import com.bank.controller.service.client.PaymentsPageService;
import com.bank.controller.service.client.impl.PaymentsPageServiceImpl;
import com.bank.model.dao.BillDao;
import com.bank.model.dao.CardDao;
import com.bank.model.dao.ClientDao;
import com.bank.model.dao.impl.DaoEnum;
import com.bank.model.dao.impl.FactoryDao;
import com.bank.model.entity.Bill;
import com.bank.model.entity.Card;
import com.bank.model.entity.Client;
import com.bank.model.entity.Page;
import com.bank.model.exception.bill.ReadBillException;
import com.bank.model.exception.card.ReadCardException;
import com.bank.model.exception.client.ReadClientException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
@RunWith(MockitoJUnitRunner.class)
public class PaymentsPageServiceTest {
    @Mock
    private CardDao cardDao;
    @Mock
    private BillDao billDao;
    @Mock
    private ClientDao clientDao;
    @Mock
    private FactoryDao factoryDao;
    private Card card;
    private Page page;
    private PaymentsPageService paymentsPageService;
    @Before
    public void setUp() throws ReadCardException, ReadBillException, ReadClientException {
        card = Entity.getCard(0);
        paymentsPageService = new PaymentsPageServiceImpl(cardDao,billDao);
        page = new Page();
        Mockito.when(cardDao.read(card.getId())).thenReturn(card);
        Mockito.when(clientDao.read(Entity.createClient().getId())).thenReturn(Entity.createClient());
        Mockito.when(billDao.getBillsOnPage(card,(page.getNumber()-1)* page.getRecords(), page.getRecords()))
                .thenReturn(Entity.getBills());
        Mockito.when(billDao.getBillsSortedById(card,(page.getNumber()-1)* page.getRecords(), page.getRecords()))
                .thenReturn(Entity.getBills().stream().sorted(Comparator.comparing(Bill::getId)).collect(Collectors.toList()));
        Mockito.when(billDao.getBillsSortedByDate(card,(page.getNumber()-1)* page.getRecords(), page.getRecords()))
                .thenReturn(Entity.getBills().stream().sorted(Comparator.comparing(Bill::getDate)).collect(Collectors.toList()));
        Mockito.when(billDao.getBillsSortedByDateDesc(card,(page.getNumber()-1)* page.getRecords(), page.getRecords()))
                .thenReturn(Entity.getBills().stream().sorted(Comparator.comparing(Bill::getDate).reversed()).collect(Collectors.toList()));
        Mockito.when(billDao.getNoOfRecords()).thenReturn(page.getRecords());
    }
    @Test
    public void testRead() throws ReadCardException {
        Card testCard = paymentsPageService.read(card.getId());
        assertEquals(card.getId(),testCard.getId());
        assertEquals(card.getCardStatus().getId(),testCard.getCardStatus().getId());
        assertEquals(card.getBalance(),testCard.getBalance());
        assertEquals(card.getCustomName(),testCard.getCustomName());
        assertEquals(card.getName(),testCard.getName());
        assertEquals(card.getClient().getId(),testCard.getClient().getId());
    }
    @Test(expected = ReadCardException.class)
    public void testWrongIdRead() throws ReadCardException {
        paymentsPageService.read(0);
    }
    @Test
    public void testGetSortedBills() throws ReadBillException {
        List<Bill> testBill = paymentsPageService.getSortedBills("",card,page);
        List<Bill> testIdBill = paymentsPageService.getSortedBills("id",card,page);
        List<Bill> testDateBill = paymentsPageService.getSortedBills("latest",card,page);
        List<Bill> testDateDescBill = paymentsPageService.getSortedBills("newest",card,page);
        assertEquals(Entity.getBills(),testBill);
        assertEquals(Entity.getBills().stream().sorted(Comparator.comparing(Bill::getId))
                .collect(Collectors.toList()), testIdBill);
        assertEquals(Entity.getBills().stream().sorted(Comparator.comparing(Bill::getDate))
                .collect(Collectors.toList()), testDateBill);
        assertEquals(Entity.getBills().stream().sorted(Comparator.comparing(Bill::getDate).reversed())
                .collect(Collectors.toList()), testDateDescBill);
    }
    @Test(expected = ReadBillException.class)
    public void testGetSortedBillsWrongId() throws ReadBillException {
        Card testCard = Entity.createCard();
        testCard.setId(0);
        paymentsPageService.getSortedBills("",testCard,page);
    }
    @Test
    public void testGetNoOfRecords(){
        int id = paymentsPageService.getNoOfRecords();
        assertEquals(page.getRecords(),id);
    }
    @Test
    public void testFillClient() throws ReadClientException {
        MockedStatic<FactoryDao> util = Mockito.mockStatic(FactoryDao.class);
        util.when(FactoryDao::getInstance).thenReturn(factoryDao);
        Mockito.when(factoryDao.getDao(DaoEnum.CLIENT_DAO)).thenReturn(clientDao);
        Client testClient = paymentsPageService.fillClient(Entity.createClient().getId());
        assertEquals(Entity.createClient().getId(),testClient.getId());
        assertEquals(Entity.createClient().getClientStatus().getId(),testClient.getClientStatus().getId());
        assertEquals(Entity.createClient().getLogin(),testClient.getLogin());
        assertEquals(Entity.createClient().getPassword(),testClient.getPassword());
        assertEquals(Entity.createClient().getRole().getId(),testClient.getRole().getId());
        util.close();
    }

    @Test(expected = ReadClientException.class)
    public void testWrongIdFillClient() throws ReadClientException {
        paymentsPageService.fillClient(0);
    }
    @Test
    public void testFillCard() throws ReadCardException {
        MockedStatic<FactoryDao> util = Mockito.mockStatic(FactoryDao.class);
        util.when(FactoryDao::getInstance).thenReturn(factoryDao);
        Mockito.when(factoryDao.getDao(DaoEnum.CARD_DAO)).thenReturn(cardDao);
        Card testCard = paymentsPageService.fillCard(card.getId());
        assertEquals(card.getId(),testCard.getId());
        assertEquals(card.getCardStatus().getId(),testCard.getCardStatus().getId());
        assertEquals(card.getBalance(),testCard.getBalance());
        assertEquals(card.getClient().getId(),testCard.getClient().getId());
        assertEquals(card.getName(),testCard.getName());
        assertEquals(card.getCustomName(),testCard.getCustomName());
        util.close();
    }
    @Test(expected = ReadCardException.class)
    public void testWrongIdFillCard() throws ReadCardException {
        paymentsPageService.fillCard(0);
    }
}
