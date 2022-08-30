package command;


import com.bank.controller.command.impl.client.MakePaymentCommand;
import com.bank.controller.service.client.MakePaymentService;
import com.bank.model.entity.Card;
import com.bank.model.entity.Page;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class MakePaymentCommandTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private MakePaymentService makePaymentService;
    @Mock
    private HttpSession session;
    @Before
    public void setUp(){
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(request.getParameter("sum")).thenReturn("100");
        Mockito.when(session.getAttribute("page")).thenReturn(new Page());
        Mockito.when(session.getAttribute("currentCard")).thenReturn(new Card(1));
    }
    @Test
    public void makePaymentExecute(){
        MakePaymentCommand makePaymentCommand = new MakePaymentCommand(makePaymentService);
        String path = makePaymentCommand.execute(request);
        assertEquals("redirect:/bank/payments?page=" + new Page().getNumber() + "&card=" + new Card(1).getId(),path);
    }
}
