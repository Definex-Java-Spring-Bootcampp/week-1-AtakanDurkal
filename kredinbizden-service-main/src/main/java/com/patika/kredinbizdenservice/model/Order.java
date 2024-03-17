package com.patika.kredinbizdenservice.model;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private List<Item> items;
    private static List<Order> orders = new ArrayList<>();
    private Customer customer;

    public Order(Customer customer) {
        this.customer = customer;
        this.items = new ArrayList<>();
        orders.add(this);
    }

    public List<Item> getItems() {
        return items;
    }

    public void addProduct(Item item) {
        items.add(item);
    }

    public double calculateTotal() {
        double total = 0;
        for (Item item : items) {
            total += item.getPrice();
        }
        return total;
    }

    public static List<Order> getOrders() {
        return orders;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
