package com.bank.model.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Bill {
    private Integer id;
    private BigDecimal sum;
    private Date date;
    private Card card;
    private String recipient;
    private BillStatus billStatus;

    public Bill() {

    }

    public Bill(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public BillStatus getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(BillStatus billStatus) {
        this.billStatus = billStatus;
    }
    public String getRecipient(){return recipient;}
    public void setRecipient(String recipient){this.recipient = recipient;}
}
