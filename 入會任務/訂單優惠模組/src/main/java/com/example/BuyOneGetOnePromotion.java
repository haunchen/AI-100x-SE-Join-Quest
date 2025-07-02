package com.example;

import java.util.List;
import java.util.stream.Collectors;

public class BuyOneGetOnePromotion implements Promotion {
    private String category;

    public BuyOneGetOnePromotion(String category) {
        this.category = category;
    }

    @Override
    public OrderSummary apply(OrderSummary summary) {
        List<OrderItem> newItems = summary.getReceivedItems().stream()
            .map(item -> item.getProduct().getCategory().equals(category) ? new OrderItem(item.getProduct(), item.getQuantity() + 1) : item)
            .collect(Collectors.toList());

        return new OrderSummary(summary.getTotalAmount(), summary.getOriginalAmount(), summary.getDiscount(), newItems);
    }
}