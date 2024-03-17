package com.patika.kredinbizdenservice.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String name;
    private String email;
    private String address;
    private int age;
    private List<Order> orders;
    private static List<Customer> customers = new ArrayList<>();


    public int getAge() {
        return age;
    }

    public Customer(String name, String email, String address, int age) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.age= age;
        this.orders = new ArrayList<>();
        customers.add(this);
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public static List<Customer> getCustomers() {
        return customers;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public static int getTotalCustomerCount() {
        return customers.size();
    }
    public static BigDecimal calculateTotalAmountForCustomersBetweenAge(String targetName, int lowerAge, int upperAge) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (Customer customer : customers) {
            if (customer.getName().equals(targetName) && customer.getAge() >= lowerAge && customer.getAge() <= upperAge) {
                totalAmount = totalAmount.add(customer.calculateTotalOrderAmount());
            }
        }
        return totalAmount;
    }
    private BigDecimal calculateTotalOrderAmount() {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (Order order : orders) {
            totalAmount = totalAmount.add(BigDecimal.valueOf(order.calculateTotal()));
        }
        return totalAmount;
    }
    public static int getTotalProductCountForCustomer(List<Customer> customers, String targetName) {
        int totalCount = 0;
        for (Customer customer : customers) {
            if (customer.getName().equals(targetName)) {
                totalCount += 1;
            }
        }
        return totalCount;
    }




}
