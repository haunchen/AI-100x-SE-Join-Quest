package com.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderService {
    private List<Promotion> promotions;

    public OrderService(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public OrderService() {
        this.promotions = new ArrayList<>();
    }

    public OrderSummary calculateTotal(Order order) {
        int subtotal = order.getSubtotal();
        double discount = 0;

        // Double 11 promotion
        for (OrderItem item : order.getItems()) {
            if (item.getQuantity() >= 10) {
                int setsOfTen = item.getQuantity() / 10;
                double discountPerSet = item.getProduct().getUnitPrice() * 10 * 0.2;
                discount += setsOfTen * discountPerSet;
            }
        }

        OrderSummary summary = new OrderSummary(subtotal - (int) discount, subtotal, (int) discount, order.getItems());

        // Other promotions
        for (Promotion promotion : promotions) {
            summary = promotion.apply(summary);
        }
        return summary;
    }
}