package com.bank.model.entity;

public enum CardStatus {
    UNBLOCKED(1, "unblocked"),
    BLOCKED(2, "blocked"),
    READY_TO_UNBLOCK(3, "ready to unblock");

    private final Integer id;
    private final String status;

    CardStatus(Integer id, String status) {
        this.id = id;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public static CardStatus getCardStatus(Integer id) {
        switch (id) {
            case 1:
                return UNBLOCKED;
            case 2:
                return BLOCKED;
            case 3:
                return READY_TO_UNBLOCK;
            default:
                throw new IllegalArgumentException("Provided wrong id ==> " + id);
        }

    }

}
