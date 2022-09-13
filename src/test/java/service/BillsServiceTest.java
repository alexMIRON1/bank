package service;

import com.bank.controller.command.exception.NotEnoughException;
import com.bank.controller.service.client.BillsService;
import com.bank.controller.service.client.impl.BillsServiceImpl;
import com.bank.model.dao.BillDao;
import com.bank.model.dao.CardDao;
import com.bank.model.dao.impl.DaoEnum;
import com.bank.model.dao.impl.FactoryDao;
import com.bank.model.entity.Bill;
import com.bank.model.entity.BillStatus;
import com.bank.model.entity.Card;
import com.bank.model.exception.bill.DeleteBillException;
import com.bank.model.exception.bill.ReadBillException;
import com.bank.model.exception.bill.UpdateBillException;
import com.bank.model.exception.card.ReadCardException;
import com.bank.model.exception.card.UpdateCardException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class BillsServiceTest {
    @Mock
    private BillDao billDao;
    @Mock
    private CardDao cardDao;
    private Bill bill;
    @Mock
    private  FactoryDao factoryDao;
    private Card card;
    private List<Bill> billList;
    private BillsService billsService;
    @Before
    public void setUp() throws ReadBillException, DeleteBillException, ReadCardException {
        bill = Entity.getBill(0);
        card = Entity.getCard(0);
        billList = Entity.getBills();
        billsService = new BillsServiceImpl(billDao,cardDao);
        Mockito.when(billDao.read(bill.getId())).thenReturn(bill);
        Mockito.when(billDao.getBills(card)).thenReturn(billList);
        Mockito.when(cardDao.read(card.getId())).thenReturn(card);
        Mockito.when(factoryDao.getDao(DaoEnum.CARD_DAO)).thenReturn(cardDao);
    }
    @Test
    public void testRead() throws ReadBillException {
        Bill testBill = billsService.read(bill.getId());
        assertEquals(testBill.getId(),bill.getId());
        assertEquals(testBill.getBillStatus(),bill.getBillStatus());
        assertEquals(testBill.getCard().getId(),bill.getCard().getId());
        assertEquals(testBill.getSum(),bill.getSum());
        assertEquals(testBill.getDate(),bill.getDate());
    }
    @Test(expected = ReadBillException.class)
    public void testWrongId() throws ReadBillException{
        billsService.read(0);
    }
    @Test
    public void testUpdateCard() throws UpdateCardException, NotEnoughException {
        Card testCard = Entity.createCard();
        Card toCard = Entity.createCard();
        Card testToCard = Entity.createCard();
        toCard.setBalance(BigDecimal.valueOf(10));
        testToCard.setBalance(BigDecimal.valueOf(10));
        billsService.updateCard(testCard,toCard,bill);
        card.setBalance(card.getBalance().subtract(bill.getSum()));
        testToCard.setBalance(testToCard.getBalance().add(bill.getSum()));
        assertEquals(testCard.getBalance(),card.getBalance());
        assertEquals(toCard.getBalance(),testToCard.getBalance());
    }
    @Test(expected = UpdateCardException.class)
    public void testWrongIdCardUpdateCard() throws UpdateCardException, NotEnoughException {
        Card testCard =Entity.createCard();
        testCard.setId(0);
        billsService.updateCard(testCard,new Card(),bill);
    }
    @Test(expected = UpdateCardException.class)
    public void testWrongIdBillUpdateCard() throws UpdateCardException, NotEnoughException {
        Bill testBill = Entity.createBill();
        testBill.setId(0);
        billsService.updateCard(card,new Card(),testBill);
    }
    @Test(expected = NotEnoughException.class)
    public void testNotEnoughUpdateCard() throws UpdateCardException, NotEnoughException {
        Bill testBill = Entity.createBill();
        testBill.setSum(BigDecimal.valueOf(400));
        billsService.updateCard(card,new Card(),testBill);
    }
    @Test
    public void testUpdateBill() throws UpdateBillException {
        Bill testBill = Entity.createBill();
        billsService.updateBill(testBill,card, new Card());
        bill.setBillStatus(BillStatus.PAID);
        assertEquals(bill.getBillStatus().getId(),testBill.getBillStatus().getId());
    }
    @Test(expected = UpdateBillException.class)
    public void testWrongIdCardUpdateBill() throws UpdateBillException{
        Card testCard =Entity.createCard();
        testCard.setId(0);
        billsService.updateBill(bill,testCard, new Card());
    }
    @Test(expected = UpdateBillException.class)
    public void testWrongIdBillUpdateBill() throws UpdateBillException {
        Bill testBill = Entity.createBill();
        testBill.setId(0);
        billsService.updateBill(testBill,card, new Card());
    }
    @Test
    public void testGetBills() throws ReadBillException {
        List<Bill> testBills = billsService.getBills(card);
        for (int i = 0; i < testBills.size(); i++) {
            assertEquals(billList.get(i).getBillStatus(),testBills.get(i).getBillStatus());
            assertEquals(billList.get(i).getId(),testBills.get(i).getId());
            assertEquals(billList.get(i).getDate(),testBills.get(i).getDate());
            assertEquals(billList.get(i).getSum(),testBills.get(i).getSum());
            assertEquals(billList.get(i).getCard().getId(),testBills.get(i).getCard().getId());
        }
    }
    @Test(expected = ReadBillException.class)
    public void testWrongCardIdNullGetBills() throws ReadBillException {
        Card testCard = Entity.createCard();
        testCard.setId(null);
        billsService.getBills(testCard);
    }
    @Test(expected = ReadBillException.class)
    public void testWrongCardIdZeroGetBills() throws ReadBillException {
        Card testCard = Entity.createCard();
        testCard.setId(0);
        billsService.getBills(testCard);
    }
    @Test
    public void testDelete() throws DeleteBillException {
        billsService.delete(bill.getId());
        Mockito.verify(billDao,Mockito.times(1)).delete(bill.getId());
    }
    @Test(expected = DeleteBillException.class)
    public void testWrongBillDelete() throws DeleteBillException {
        billsService.delete(0);
    }
    @Test(expected = DeleteBillException.class)
    public void testWrongBillNullDelete() throws DeleteBillException {
        billsService.delete(null);
    }
    @Test
    public void testFillCard() throws ReadCardException {
        MockedStatic<FactoryDao> util = Mockito.mockStatic(FactoryDao.class);
        util.when(FactoryDao::getInstance).thenReturn(factoryDao);
        Card testCard = billsService.fillCard(card.getId());
        assertEquals(card.getId(),testCard.getId());
        assertEquals(card.getCardStatus().getId(),testCard.getCardStatus().getId());
        assertEquals(card.getBalance(),testCard.getBalance());
        assertEquals(card.getClient().getId(),testCard.getClient().getId());
        assertEquals(card.getName(),testCard.getName());
        assertEquals(card.getCustomName(),testCard.getCustomName());
        util.close();
    }
    @Test(expected = ReadCardException.class)
    public void testWrongIdCard() throws ReadCardException {
        billsService.fillCard(0);
    }
}
