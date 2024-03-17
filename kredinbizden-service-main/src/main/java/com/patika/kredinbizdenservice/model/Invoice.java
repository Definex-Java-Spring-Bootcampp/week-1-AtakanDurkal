package com.patika.kredinbizdenservice.model;

public class Invoice {
    private Order order;

    public Invoice(Order order) {
        this.order = order;
    }

    public void printInvoice() {
        System.out.println("Invoice for customer: " + order.getCustomer().getName());
        System.out.println("Products:");
        for (Item item : order.getItems()) {
            System.out.println("- " + item.getName() + ": $" + item.getPrice());
        }
        System.out.println("Total: $" + order.calculateTotal());
    }
}
