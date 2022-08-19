package com.bank.model.entity;

public class Page {
   private int numberPage;
   private int recordsPerPage;
   private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getRecords() {
        return recordsPerPage;
    }

    public void setRecords(int recordsPerPage) {
        this.recordsPerPage = recordsPerPage;
    }

    public int getNumber() {
        return numberPage;
    }

    public void setNumberPage(int numberPage) {
        this.numberPage = numberPage;
    }
}
