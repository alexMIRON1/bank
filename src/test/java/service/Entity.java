package service;

import com.bank.model.entity.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Entity {
    private static final List<Client> clients;
    private static final List<Card> cards;
    private static final List<Bill> bills;
    static {
        clients = new ArrayList<>();
        clients.add(createClient());
        Client client2 = createClient();
        client2.setLogin("roma");
        client2.setPassword("roma");
        client2.setId(2);
        Client client3 = createClient();
        client3.setLogin("nik");
        client3.setPassword("nik");
        client3.setId(3);
        clients.add(client2);
        clients.add(client3);
        //
        cards = new ArrayList<>();
        cards.add(createCard());
        Card card2 = createCard();
        card2.setId(2);
        card2.setBalance(200);
        card2.setName("00002");
        Card card3 = createCard();
        card2.setId(3);
        card2.setBalance(300);
        card2.setName("00003");
        cards.add(card2);
        cards.add(card3);
        //
        bills = new ArrayList<>();
        bills.add(createBill());
        Bill bill2 =createBill();
        bill2.setId(2);
        bill2.setBillStatus(BillStatus.PAID);
        bill2.setSum(10);
        Bill bill3 = createBill();
        bill3.setId(3);
        bill3.setBillStatus(BillStatus.READY);
        bill3.setSum(10);
        bills.add(bill2);
        bills.add(bill3);
    }
    public static Client createClient(){
        Client client = new Client(1);
        client.setLogin("alex");
        client.setPassword("alex");
        client.setClientStatus(ClientStatus.UNBLOCKED);
        client.setRole(Role.CLIENT);
        return client;
    }
    public static Card createCard(){
        Card card  = new Card(1);
        card.setCardStatus(CardStatus.UNBLOCKED);
        card.setName("00001");
        card.setCustomName("my card");
        card.setBalance(200);
        card.setClient(createClient());
        return card;
    }
    public static Bill createBill(){
        Bill bill = new Bill(1);
        bill.setCard(createCard());
        bill.setBillStatus(BillStatus.READY);
        bill.setDate(new Date());
        bill.setSum(50);
        return bill;
    }
    public static Client getClient(Integer id){
        return clients.get(id);
    }
    public static Card getCard(Integer id){
        return cards.get(id);
    }
    public static Bill getBill(Integer id){
        return bills.get(id);
    }
    public static List<Bill> getBills(){
        return bills;
    }
    public static List<Card> getCards(){
        return cards;
    }
    public static List<Client> getClients(){
        return clients;
    }
}
