package ru.emfataliev;

import java.util.UUID;

public class RequestId {

    private String id;

    void generate() {
        id = UUID.randomUUID().toString();
    }

    public String asString() {
        return id;
    }
}
