package com.example.studentmoneyapp.utils;

public class ExpenseSumPair {
    private String category;
    private float sum;

    public ExpenseSumPair(String category, float sum){
        this.category = category;
        this.sum = sum;
    }

    public String getCategory() {
        return category;
    }

    public float getSum() {
        return sum;
    }
}
