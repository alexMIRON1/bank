package service;

import com.bank.controller.service.client.ReceiveCardService;
import com.bank.controller.service.client.impl.ReceiveCardServiceImpl;
import com.bank.model.dao.CardDao;
import com.bank.model.entity.Card;
import com.bank.model.entity.Client;
import com.bank.model.exception.card.CreateCardException;
import com.bank.model.exception.card.ReadCardException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.Assert.*;
@RunWith(MockitoJUnitRunner.class)
public class ReceiveCardServiceTest {
    @Mock
    private CardDao cardDao;
    private Client client;
    private Card card;
    private ReceiveCardService receiveCardService;
    @Before
    public void setUp() throws CreateCardException, ReadCardException {
        receiveCardService = new ReceiveCardServiceImpl(cardDao);
        client = Entity.getClient(0);
        card = Entity.getCard(0);
        Mockito.when(cardDao.create(card)).thenReturn(Entity.createCard());
        Mockito.when(cardDao.getLastCardName()).thenReturn(String.valueOf(Entity.getCards().get(Entity.getCards().size()-1).getId()));
    }
    @Test
    public void testCreate() throws ReadCardException, CreateCardException {
        Card testCard = card;
        receiveCardService.create(testCard,client);
        assertEquals(card.getId(),testCard.getId());
        assertEquals(card.getCardStatus().getId(),testCard.getCardStatus().getId());
        assertEquals(card.getBalance(),testCard.getBalance());
        assertEquals(card.getClient().getId(),testCard.getClient().getId());
        assertEquals(card.getName(),testCard.getName());
        assertEquals(card.getCustomName(),testCard.getCustomName());
    }
    @Test(expected = CreateCardException.class)
    public void testWrongClientCreate() throws ReadCardException, CreateCardException {
        Client testClient = Entity.createClient();
        testClient.setId(0);
        receiveCardService.create(card,testClient);
    }
}
