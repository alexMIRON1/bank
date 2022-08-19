package com.bank.model.entity;

public enum BillStatus {
    READY(1, "ready"),
    PAID(2, "paid");

    private final Integer id;
    private final String status;

    BillStatus(Integer id, String status) {
        this.id = id;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public static BillStatus getBillStatus(Integer id) {
        switch (id) {
            case 1:
                return READY;
            case 2:
                return PAID;
            default:
                throw new IllegalArgumentException("Provided wrong id ==> " + id);
        }
    }

}
