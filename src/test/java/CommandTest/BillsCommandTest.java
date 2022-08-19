package CommandTest;
import com.bank.controller.command.impl.client.DeleteBillCommand;
import com.bank.controller.command.impl.client.PayBillCommand;
import com.bank.controller.service.client.BillsService;
import com.bank.model.entity.Bill;
import com.bank.model.entity.BillStatus;
import com.bank.model.entity.Card;
import com.bank.model.entity.Page;
import com.bank.model.exception.bill.ReadBillException;
import com.bank.model.exception.card.ReadCardException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class BillsCommandTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private BillsService billsService;
    @Mock
    private HttpSession session;
    @Before
    public void setUp() throws ReadCardException, ReadBillException {
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(request.getParameter(Mockito.anyString())).thenReturn("1");
        Mockito.when(session.getAttribute(Mockito.anyString())).thenReturn(new Page());
        Mockito.when(billsService.read(createBill().getId())).thenReturn(createBill());
        Mockito.when(billsService.fillCard(createBill().getCard().getId())).thenReturn(new Card(1));
    }
    @Test
    public void testDeleteBillExecute(){
        DeleteBillCommand deleteBillCommand = new DeleteBillCommand(billsService);
        String path = deleteBillCommand.execute(request);
        assertEquals("redirect:/bank/payments?page=" + new Page().getNumber() + "&card=" + new Card(1).getId(),path);

    }
    @Test
    public void testPayBillExecute(){
        PayBillCommand payBillCommand = new PayBillCommand(billsService);
        String path = payBillCommand.execute(request);
        assertEquals("redirect:/bank/payments?page=" + new Page().getNumber() + "&card=" + new Card(1).getId(),path);
    }
    private Bill createBill(){
        Bill creatingBill = new Bill();
        creatingBill.setId(1);
        creatingBill.setSum(100);
        creatingBill.setCard(new Card(1));
        creatingBill.setBillStatus(BillStatus.READY);
        return creatingBill;
    }
}
