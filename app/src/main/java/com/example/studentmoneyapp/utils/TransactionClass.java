package com.example.studentmoneyapp.utils;

import com.example.studentmoneyapp.model.SingleTransaction;

import java.util.ArrayList;

public class TransactionClass {

    private static TransactionClass instance;
    private ArrayList<SingleTransaction> list;

    private TransactionClass() {
    }

    public ArrayList<SingleTransaction> getList() {
        return list;
    }

    public void setList(ArrayList<SingleTransaction> list) {
        this.list = list;
    }

    public static TransactionClass getInstance() {
        if (instance == null) {
            instance = new TransactionClass();
        }
        return instance;
    }
}