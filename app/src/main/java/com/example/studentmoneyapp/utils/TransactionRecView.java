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

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getStore() {
        return Store;
    }

    public void setStore(String store) {
        Store = store;
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
