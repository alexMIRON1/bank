package ServiceTest;

import com.bank.controller.service.admin.UnblockService;
import com.bank.controller.service.admin.impl.UnblockServiceImpl;
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

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class UnblockServiceTest {
    @Mock
    private CardDao cardDao;
    private Card card;
    private UnblockService unblockService;
    @Before
    public void setUp() throws ReadCardException {
        unblockService = new UnblockServiceImpl(cardDao);
        card = Entity.getCard(0);
        Mockito.when(cardDao.read(card.getId())).thenReturn(card);
    }
    @Test
    public void testRead() throws ReadCardException {
        Card testCard = unblockService.read(card.getId());
        assertEquals(card.getId(),testCard.getId());
        assertEquals(card.getCardStatus().getId(),testCard.getCardStatus().getId());
        assertEquals(card.getBalance(),testCard.getBalance());
        assertEquals(card.getClient().getId(),testCard.getClient().getId());
        assertEquals(card.getName(),testCard.getName());
        assertEquals(card.getCustomName(),testCard.getCustomName());
    }
    @Test(expected = ReadCardException.class)
    public void testWrongIdRead() throws ReadCardException {
        unblockService.read(0);
    }
    @Test
    public void testUpdate() throws UpdateCardException {
        Card testCard = Entity.createCard();
        testCard.setCardStatus(CardStatus.READY_TO_UNBLOCK);
        unblockService.update(testCard);
        assertEquals(Entity.createCard().getCardStatus().getId(),testCard.getCardStatus().getId());
    }
}
