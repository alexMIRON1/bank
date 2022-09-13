package service;

import com.bank.controller.service.client.CardsService;
import com.bank.controller.service.client.impl.CardsServiceImpl;
import com.bank.model.dao.CardDao;
import com.bank.model.entity.Card;
import com.bank.model.entity.CardStatus;
import com.bank.model.exception.card.ReadCardException;
import com.bank.model.exception.card.UpdateCardException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;
@RunWith(MockitoJUnitRunner.class)
public class CardsServiceTest {
    @Mock
    CardDao cardDao;
    private Card card;
    private CardsService cardsService;
    @Before
    public void setUp() throws UpdateCardException, ReadCardException {
        card = Entity.getCard(0);
        System.out.println(card.getBalance());
        cardsService = new CardsServiceImpl(cardDao);
        Mockito.when(cardDao.read(card.getId())).thenReturn(card);
        card.setBalance(BigDecimal.valueOf(200));
    }
    @Test
    public void testUpdateStatus() throws UpdateCardException {
        Card testCard = Entity.createCard();
        cardsService.updateStatus(testCard);
        card.setCardStatus(CardStatus.BLOCKED);
        assertEquals(card.getCardStatus().getId(),testCard.getCardStatus().getId());
    }
    @Test(expected = UpdateCardException.class)
    public void testNullIdUpdate() throws UpdateCardException {
        Card testCard = Entity.createCard();
        testCard.setId(0);
        cardsService.updateStatus(testCard);
    }
    @Test
    public void testRead() throws ReadCardException {
        Card testCard = cardsService.read(card.getId());
        assertEquals(card.getId(),testCard.getId());
        assertEquals(card.getCardStatus().getId(),testCard.getCardStatus().getId());
        assertEquals(card.getBalance(),testCard.getBalance());
        assertEquals(card.getClient().getId(),testCard.getClient().getId());
        assertEquals(card.getName(),testCard.getName());
        assertEquals(card.getCustomName(),testCard.getCustomName());
    }
    @Test(expected = ReadCardException.class)
    public void testWrongRead() throws ReadCardException {
        cardsService.read(0);
    }
    @Test
    public void testUpdateTopUp() throws UpdateCardException {
        Card testCard = Entity.createCard();
        cardsService.updateTopUp(testCard,BigDecimal.valueOf(100));
        card.setBalance(card.getBalance().add(BigDecimal.valueOf(100)));
        assertEquals(card.getBalance(),testCard.getBalance());
    }
    @Test
    public void testUpdateCustomName() throws UpdateCardException {
        Card testCard = Entity.createCard();
        String customName = "wife's card";
        cardsService.updateCustomName(testCard,customName);
        card.setCustomName(customName);
        assertEquals(card.getCustomName(),testCard.getCustomName());
    }
    @Test(expected = UpdateCardException.class)
    public void testWrongIdUpdateCustomName() throws UpdateCardException {
        Card testCard = Entity.createCard();
        testCard.setId(0);
        cardsService.updateCustomName(testCard,"wife's card");
    }
}
