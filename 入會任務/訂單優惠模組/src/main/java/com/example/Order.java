package com.example;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private Customer customer;
    private List<OrderItem> items;

    public Order(Customer customer, List<OrderItem> items) {
        this.customer = customer;
        this.items = items;
    }

    public Order(Customer customer) {
        this.customer = customer;
        this.items = new ArrayList<>();
    }

    public void addItem(Product product, int quantity) {
        this.items.add(new OrderItem(product, quantity));
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public int getSubtotal() {
        return items.stream().mapToInt(OrderItem::getSubtotal).sum();
    }
}