package com.example;

public class ThresholdDiscountPromotion implements Promotion {
    private int threshold;
    private int discount;

    public ThresholdDiscountPromotion(int threshold, int discount) {
        this.threshold = threshold;
        this.discount = discount;
    }

    public int getThreshold() {
        return threshold;
    }

    public int getDiscount() {
        return discount;
    }

    @Override
    public OrderSummary apply(OrderSummary summary) {
        int subtotal = summary.getOriginalAmount();
        int discountAmount = summary.getDiscount();
        if (subtotal >= threshold) {
            discountAmount += discount;
        }
        int totalAmount = subtotal - discountAmount;
        return new OrderSummary(totalAmount, subtotal, discountAmount, summary.getReceivedItems());
    }
}