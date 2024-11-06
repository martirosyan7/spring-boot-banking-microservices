package com.banking.transaction_service.utils;

public enum TransactionDirection {
    SEND("Send"),
    RECEIVE("Receive"),
    INCOMING("Incoming");

    private String name;

    TransactionDirection(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
