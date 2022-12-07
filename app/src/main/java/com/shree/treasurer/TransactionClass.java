package com.shree.treasurer;

public class TransactionClass {
    private double amount;
    private String message;
    private boolean positive;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String date;

    public TransactionClass(double amount, String message, boolean positive, String date) {
        this.amount = amount;
        this.message = message;
        this.positive = positive;
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isPositive() {
        return positive;
    }

    public void setPositive(boolean positive) {
        this.positive = positive;
    }
}
