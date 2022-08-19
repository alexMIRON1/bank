package com.bank.model.entity;

public enum ClientStatus {
    UNBLOCKED(1, "unblocked"),
    BLOCKED(2, "blocked");

    private final Integer id;
    private final String status;

    ClientStatus(Integer id, String status) {
        this.id = id;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public static ClientStatus getClientStatus(Integer id) {
        switch (id) {
            case 1:
                return UNBLOCKED;
            case 2:
                return BLOCKED;
            default:
                throw new IllegalArgumentException("Provided wrong id ==> " + id);
        }
    }

}
