package service;

import com.bank.controller.service.admin.AdminPageService;
import com.bank.controller.service.admin.impl.AdminPageServiceImpl;
import com.bank.model.dao.CardDao;
import com.bank.model.dao.ClientDao;
import com.bank.model.dao.impl.DaoEnum;
import com.bank.model.dao.impl.FactoryDao;
import com.bank.model.entity.Card;
import com.bank.model.entity.CardStatus;
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

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
@RunWith(MockitoJUnitRunner.class)
public class AdminPageServiceTest {
    @Mock
    private ClientDao clientDao;
    @Mock
    private CardDao cardDao;
    @Mock
    private FactoryDao factoryDao;
    private List<Card> cardList;
    private List<Client> clientList;
    private AdminPageService adminPageService;
    @Before
    public void setUp() throws ReadClientException, ReadCardException {
        adminPageService = new AdminPageServiceImpl(cardDao,clientDao);
        cardList = Entity.getCards();
        Card card = Entity.createCard();
        card.setCardStatus(CardStatus.READY_TO_UNBLOCK);
        cardList.add(card);
        clientList = Entity.getClients();
        Mockito.when(clientDao.read(clientList.get(0).getId())).thenReturn(clientList.get(0));
    }
    @Test
    public void testGetClients() throws ReadClientException {
        List<Client> testClients = adminPageService.getClients(new Page());
        for (int i = 0; i < testClients.size(); i++) {
            assertEquals(clientList.get(i).getId(),testClients.get(i).getId());
            assertEquals(clientList.get(i).getClientStatus().getId(),testClients.get(i).getClientStatus().getId());
            assertEquals(clientList.get(i).getRole().getId(),testClients.get(i).getRole().getId());
            assertEquals(clientList.get(i).getPassword(),testClients.get(i).getPassword());
            assertEquals(clientList.get(i).getLogin(),testClients.get(i).getLogin());
        }
    }
    @Test
    public void testGetCards() throws ReadCardException {
        List<Card> testCards = adminPageService.getCards(new Page());
        List<Card> filterCardList = cardList.stream().filter(x->x.getCardStatus().getId() == 3).collect(Collectors.toList());
        for (int i = 0; i < testCards.size(); i++) {
            assertEquals(filterCardList.get(i).getId(),testCards.get(i).getId());
            assertEquals(filterCardList.get(i).getClient().getId(),testCards.get(i).getClient().getId());
            assertEquals(filterCardList.get(i).getName(),testCards.get(i).getName());
            assertEquals(filterCardList.get(i).getBalance(),testCards.get(i).getBalance());
            assertEquals(filterCardList.get(i).getCustomName(),testCards.get(i).getCustomName());
        }
    }
    @Test
    public void testFillClient() throws ReadClientException {
        MockedStatic<FactoryDao> util = Mockito.mockStatic(FactoryDao.class);
        util.when(FactoryDao::getInstance).thenReturn(factoryDao);
        Mockito.when(factoryDao.getDao(DaoEnum.CLIENT_DAO)).thenReturn(clientDao);
        Client testClient = adminPageService.fillClient(Entity.createClient().getId());
        assertEquals(Entity.createClient().getId(),testClient.getId());
        assertEquals(Entity.createClient().getClientStatus().getId(),testClient.getClientStatus().getId());
        assertEquals(Entity.createClient().getRole().getId(),testClient.getRole().getId());
        assertEquals(Entity.createClient().getLogin(),testClient.getLogin());
        assertEquals(Entity.createClient().getPassword(),testClient.getPassword());
        util.close();
    }
    @Test(expected = ReadClientException.class)
    public void testWrongIdClient() throws ReadClientException {
        adminPageService.fillClient(0);
    }
}
