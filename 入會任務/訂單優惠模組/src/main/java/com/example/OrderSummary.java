package com.example;

import java.util.List;

public class OrderSummary {
    private int totalAmount;
    private int originalAmount;
    private int discount;
    private List<OrderItem> receivedItems;

    public OrderSummary(int totalAmount, int originalAmount, int discount, List<OrderItem> receivedItems) {
        this.totalAmount = totalAmount;
        this.originalAmount = originalAmount;
        this.discount = discount;
        this.receivedItems = receivedItems;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public int getOriginalAmount() {
        return originalAmount;
    }

    public int getDiscount() {
        return discount;
    }

    public List<OrderItem> getReceivedItems() {
        return receivedItems;
    }
}