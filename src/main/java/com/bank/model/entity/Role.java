package com.bank.model.entity;

public enum Role {
    ADMIN(1, "admin"),
    CLIENT(2, "client"),
    ANONYMOUS(3,"anonymous");

    private final Integer id;
    private final String role;

    Role(Integer id, String role) {
        this.id = id;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public Integer getId() {
        return id;
    }

    public static Role getRole(Integer id) {
        switch (id) {
            case 1:
                return ADMIN;
            case 2:
                return CLIENT;
            default:
               return ANONYMOUS;
        }
    }

}
