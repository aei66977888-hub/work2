package com.example.dao;

import com.example.model.User;

public interface UserDAO {
    boolean registerUser(User user);
    User loginUser(String username, String password);
    boolean isUsernameTaken(String username);
}