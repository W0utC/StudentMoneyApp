package com.example.studentmoneyapp.utils;

import java.time.LocalDateTime;

public class TransactionRecView {
    private LocalDateTime date;
    private float amount;
    private String Store;

    public TransactionRecView(LocalDateTime date, float amount, String store) {
        this.date = date;
        this.amount = amount;
        Store = store;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public float getAmount() {
        return amount;
    }

    public String getStore() {
        return Store;
    }

    @Override
    public String toString() {
        return "TransactionRecView{" +
                "date=" + date +
                ", amount=" + amount +
                ", Store='" + Store + '\'' +
                '}';
    }
}
