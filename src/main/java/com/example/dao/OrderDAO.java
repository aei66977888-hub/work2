package com.example.dao;

import com.example.model.Order;

import java.util.List;

public interface OrderDAO {
    void addOrder(Order order);
    List<Order> getOrdersByUserId(int userId);
}