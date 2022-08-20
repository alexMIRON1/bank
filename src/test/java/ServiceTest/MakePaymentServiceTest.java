package ServiceTest;

import com.bank.controller.command.exception.CardBannedException;
import com.bank.controller.service.client.MakePaymentService;
import com.bank.controller.service.client.impl.MakePaymentServiceImpl;
import com.bank.model.dao.BillDao;
import com.bank.model.entity.Bill;
import com.bank.model.entity.Card;
import com.bank.model.entity.CardStatus;
import com.bank.model.exception.bill.CreateBillException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.Assert.*;
@RunWith(MockitoJUnitRunner.class)
public class MakePaymentServiceTest {
    @Mock
    private BillDao billDao;
    private Bill bill;
    private Card card;
    private MakePaymentService makePaymentService;
    @Before
    public void setUp() throws CreateBillException {
        bill = Entity.getBill(0);
        card = Entity.getCard(0);
        makePaymentService = new MakePaymentServiceImpl(billDao);
        Mockito.when(billDao.create(bill)).thenReturn(bill);
    }
    @Test
    public void testCreate() throws CardBannedException, CreateBillException {
        Bill testBill = makePaymentService.create(bill,100,card);
        assertEquals(bill.getId(),testBill.getId());
        assertEquals(bill.getBillStatus().getId(),testBill.getBillStatus().getId());
        assertEquals(bill.getDate(),testBill.getDate());
        assertEquals(bill.getSum(),testBill.getSum());
        assertEquals(bill.getCard().getId(),testBill.getCard().getId());
    }
    @Test(expected = CardBannedException.class)
    public void testCardBannedCreate() throws CardBannedException, CreateBillException {
        Bill testBill = Entity.createBill();
        Card testCard =Entity.createCard();
        testCard.setCardStatus(CardStatus.BLOCKED);
        testBill.setCard(testCard);
        makePaymentService.create(testBill,100,testCard);
    }
    @Test(expected = CreateBillException.class)
    public void testCreateBillCreate() throws CardBannedException, CreateBillException {
        makePaymentService.create(bill,0,card);
    }
}
