package com.example.studentmoneyapp.model;

import java.time.LocalDateTime;

public class SingleTransaction {
    private LocalDateTime date;
    private String type;
    private float amount;
    private String methode;
    private String store;

    public SingleTransaction(LocalDateTime date, String type, float amount, String methode, String store) {
        this.date = date;
        this.type = type;
        this.amount = amount;
        this.methode = methode;
        this.store = store;

    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getMethode() {
        return methode;
    }

    public void setMethode(String methode) {
        this.methode = methode;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    @Override
    public String toString() {
        return "SingleTransaction{" +
                "date=" + date +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", methode='" + methode + '\'' +
                ", store='" + store + '\'' +
                '}';
    }
}
