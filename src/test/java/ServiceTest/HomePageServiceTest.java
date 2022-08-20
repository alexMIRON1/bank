package ServiceTest;

import com.bank.controller.service.client.HomePageService;
import com.bank.controller.service.client.impl.HomePageServiceImpl;
import com.bank.model.dao.CardDao;
import com.bank.model.dao.ClientDao;
import com.bank.model.dao.impl.DaoEnum;
import com.bank.model.dao.impl.FactoryDao;
import com.bank.model.entity.Card;
import com.bank.model.entity.Client;
import com.bank.model.entity.Page;
import com.bank.model.exception.card.ReadCardException;
import com.bank.model.exception.client.ReadClientException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.Assert.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(MockitoJUnitRunner.class)
public class HomePageServiceTest {
    @Mock
    private CardDao cardDao;
    @Mock
    private ClientDao clientDao;
    @Mock
    private FactoryDao factoryDao;
    private Client client;
    private Page page;

    private HomePageService homePageService;
    @Before
    public void setUp() throws ReadCardException, ReadClientException {
        client = Entity.getClient(0);
        homePageService = new HomePageServiceImpl(cardDao);
        page= new Page();
        Mockito.when(cardDao.getCardsOnPage(client,(page.getNumber()-1)* page.getRecords(), page.getRecords()))
                .thenReturn(Entity.getCards());

        Mockito.when(cardDao.getCardsSortedById(client,(page.getNumber()-1)* page.getRecords(), page.getRecords()))
                .thenReturn(Entity.getCards().stream().sorted(Comparator.comparing(Card::getName)).collect(Collectors.toList()));
        Mockito.when(cardDao.getCardsSortedByName(client,(page.getNumber()-1)* page.getRecords(), page.getRecords()))
                .thenReturn(Entity.getCards().stream().sorted(Comparator.comparing(Card::getCustomName)).collect(Collectors.toList()));
        Mockito.when(cardDao.getCardsSortedByBalance(client,(page.getNumber()-1)* page.getRecords(), page.getRecords()))
                .thenReturn(Entity.getCards().stream().sorted(Comparator.comparing(Card::getBalance).reversed()).collect(Collectors.toList()));
        Mockito.when(cardDao.getNoOfRecords()).thenReturn(page.getRecords());
        Mockito.when(clientDao.read(Entity.createClient().getId())).thenReturn(Entity.createClient());
    }
    @Test
    public void testSort() throws ReadCardException {
        List<Card> testDefaultCard = homePageService.sort(client,"",page);
        List<Card> testIdCard = homePageService.sort(client,"id",page);
        List<Card> testNameCard = homePageService.sort(client, "name",page);
        List<Card> testBalanceCard = homePageService.sort(client,"balance",page);
        assertEquals(Entity.getCards(),testDefaultCard);
        assertEquals(Entity.getCards().stream().sorted(Comparator.comparing(Card::getName))
                .collect(Collectors.toList()), testIdCard);
        assertEquals(Entity.getCards().stream().sorted(Comparator.comparing(Card::getCustomName))
                .collect(Collectors.toList()), testNameCard);
        assertEquals(Entity.getCards().stream().sorted(Comparator.comparing(Card::getBalance).reversed())
                .collect(Collectors.toList()), testBalanceCard);


    }
    @Test(expected = ReadCardException.class)
    public void testWrongIdSort() throws ReadCardException {
        Client testClient = Entity.createClient();
        testClient.setId(0);
        homePageService.sort(testClient,"",page);
    }
    @Test
    public void testGetNoOfRecords(){
        int i = homePageService.getNoOfRecords();
        assertEquals(page.getRecords(),i);
    }
    @Test
    public void testFillClient() throws ReadClientException {
        MockedStatic<FactoryDao> util = Mockito.mockStatic(FactoryDao.class);
        util.when(FactoryDao::getInstance).thenReturn(factoryDao);
        Mockito.when(factoryDao.getDao(DaoEnum.CLIENT_DAO)).thenReturn(clientDao);
        Client testClient = homePageService.fillClient(Entity.createClient().getId());
        assertEquals(Entity.createClient().getId(),testClient.getId());
        assertEquals(Entity.createClient().getClientStatus().getId(),testClient.getClientStatus().getId());
        assertEquals(Entity.createClient().getRole().getId(),testClient.getRole().getId());
        assertEquals(Entity.createClient().getLogin(),testClient.getLogin());
        assertEquals(Entity.createClient().getPassword(),testClient.getPassword());
    }
    @Test(expected = ReadClientException.class)
    public void testWrongFillClient() throws ReadClientException {
        homePageService.fillClient(0);
    }
}
