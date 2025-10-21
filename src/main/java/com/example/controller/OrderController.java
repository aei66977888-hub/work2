package com.example.controller;

import com.example.dao.OrderDAO;
import com.example.dao.OrderDAOImpl;
import com.example.model.Order;

import java.util.List;

public class OrderController {
    private OrderDAO orderDAO;

    public OrderController() {
        this.orderDAO = new OrderDAOImpl();
    }

    public void addOrder(int userId, String productName, int quantity) {
        Order order = new Order();
        order.setUserId(userId);
        order.setProductName(productName);
        order.setQuantity(quantity);
        orderDAO.addOrder(order);
    }

    public List<Order> getOrders(int userId) {
        return orderDAO.getOrdersByUserId(userId);
    }
}