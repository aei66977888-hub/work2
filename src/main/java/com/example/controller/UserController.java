package com.example.controller;

import com.example.dao.UserDAO;
import com.example.dao.UserDAOImpl;
import com.example.model.User;

public class UserController {
    private UserDAO userDAO;

    public UserController() {
        this.userDAO = new UserDAOImpl();
    }

    public boolean register(String name, String username, String password) {
        if (userDAO.isUsernameTaken(username)) {
            return false;
        }
        User user = new User();
        user.setName(name);
        user.setUsername(username);
        user.setPassword(password);
        return userDAO.registerUser(user);
    }

    public User login(String username, String password) {
        return userDAO.loginUser(username, password);
    }
}