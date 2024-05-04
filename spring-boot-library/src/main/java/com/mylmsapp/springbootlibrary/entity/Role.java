package com.mylmsapp.springbootlibrary.entity;

public enum Role {
    USER("STUDENT"),

    TEACHER("TEACHER"),
    ADMIN("ADMIN");

    private final String type;

    Role(String string) {
        type = string;
    }

    @Override
    public String toString() {
        return type;
    }
}
