package com.innervoice.user.domain;

public enum Role {

    PARENT("부모"),
    CHILD("자녀");

    private final String description;

    Role(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
