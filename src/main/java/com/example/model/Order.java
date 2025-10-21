package com.example.model;

import java.sql.Timestamp;

public class Order {
    private int id;
    private int userId;
    private String productName;
    private int quantity;
    private Timestamp orderDate;

    public Order() {}

    public Order(int id, int userId, String productName, int quantity, Timestamp orderDate) {
        this.id = id;
        this.userId = userId;
        this.productName = productName;
        this.quantity = quantity;
        this.orderDate = orderDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public Timestamp getOrderDate() { return orderDate; }
    public void setOrderDate(Timestamp orderDate) { this.orderDate = orderDate; }

    @Override
    public String toString() {
        return "Order [id=" + id + ", userId=" + userId + ", productName=" + productName + ", quantity=" + quantity + ", orderDate=" + orderDate + "]";
    }
}