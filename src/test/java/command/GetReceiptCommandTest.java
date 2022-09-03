package command;

import com.bank.controller.command.impl.client.GetReceiptCommand;
import com.bank.controller.service.client.BillsService;
import com.bank.model.entity.Bill;
import com.bank.model.entity.BillStatus;
import com.bank.model.entity.Card;
import com.bank.model.exception.bill.ReadBillException;
import com.bank.model.exception.card.ReadCardException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class GetReceiptCommandTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private BillsService billsService;
    @AfterClass
    public static void globalTearDown() {
        File file = new File("receipt.pdf");
        file.delete();
    }
    @Before
    public void setUp() throws ReadCardException, ReadBillException, MessagingException {
        Mockito.when(request.getParameter("bill")).thenReturn("1");
        Mockito.when(request.getParameter("email")).thenReturn("to");
        Mockito.when(billsService.read(createBill().getId())).thenReturn(createBill());
        Mockito.when(billsService.fillCard(createBill().getCard().getId())).thenReturn(new Card(1));
    }
    @Test
    public void testGetReceiptExecute(){
        MockedStatic<Transport> util = Mockito.mockStatic(Transport.class);
        util.reset();
        GetReceiptCommand getReceiptCommand = new GetReceiptCommand(billsService);
        assertEquals("/payments.jsp",getReceiptCommand.execute(request));
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
