package com.senior.challenge.user.enums;

public enum UserRoles {
    ADMIN(1, "Administrador"),
    CLIENT(2, "Cliente");

    private int id;
    private String name;

    UserRoles(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
