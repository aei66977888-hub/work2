package com.example.view;

import com.example.controller.UserController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterView extends JFrame {
    private JTextField nameField, usernameField;
    private JPasswordField passwordField;
    private JButton registerButton, backButton;
    private UserController userController;

    public RegisterView() {
        userController = new UserController();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Register");
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new GridLayout(4, 2));

        JLabel label = new JLabel("名稱");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(label);
        nameField = new JTextField();
        getContentPane().add(nameField);

        JLabel label_1 = new JLabel("帳號");
        label_1.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(label_1);
        usernameField = new JTextField();
        getContentPane().add(usernameField);

        JLabel label_2 = new JLabel("密碼");
        label_2.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(label_2);
        passwordField = new JPasswordField();
        getContentPane().add(passwordField);

        registerButton = new JButton("確定");
        backButton = new JButton("回到登入畫面");
        getContentPane().add(registerButton);
        getContentPane().add(backButton);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(RegisterView.this, "Please fill in all fields!");
                    return;
                }
                if (userController.register(name, username, password)) {
                    JOptionPane.showMessageDialog(RegisterView.this, "Registration successful!");
                    new LoginView().setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(RegisterView.this, "Username already taken!");
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginView().setVisible(true);
                dispose();
            }
        });

        setLocationRelativeTo(null);
    }
}