package CommandTest;

import com.bank.controller.command.impl.client.PaymentsPageCommand;
import com.bank.controller.service.client.PaymentsPageService;
import com.bank.model.entity.*;
import com.bank.model.exception.card.ReadCardException;
import com.bank.model.exception.client.ReadClientException;
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
public class PaymentsPageCommandTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private PaymentsPageService paymentsPageService;
    @Mock
    private HttpSession session;
    @Before
    public void setUp() throws ReadClientException, ReadCardException {
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(request.getParameter("card")).thenReturn("1");
        Mockito.when(request.getParameter("sort")).thenReturn("");
        Mockito.when(request.getParameter("page")).thenReturn("1");
        Mockito.when(paymentsPageService.read(createCard().getId())).thenReturn(createCard());
    }
    @Test
    public void paymentsPageExecute(){
        PaymentsPageCommand paymentsPageCommand = new PaymentsPageCommand(paymentsPageService);
        String path = paymentsPageCommand.execute(request);
        assertEquals("/payments.jsp",path);
    }
    private Card createCard(){
        Card creatingCard = new Card();
        creatingCard.setId(1);
        creatingCard.setName("0001");
        creatingCard.setBalance(100);
        creatingCard.setCardStatus(CardStatus.UNBLOCKED);
        creatingCard.setClient(new Client(1));
        creatingCard.setCustomName("My card");
        return creatingCard;
    }
}
