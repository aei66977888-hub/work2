package com.example.view;

import com.example.controller.UserController;
import com.example.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;
    private UserController userController;

    public LoginView() {
        userController = new UserController();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new GridLayout(3, 2));

        JLabel label = new JLabel("帳號");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("新細明體", Font.BOLD, 15));
        getContentPane().add(label);
        usernameField = new JTextField();
        getContentPane().add(usernameField);

        JLabel label_1 = new JLabel("密碼");
        label_1.setFont(new Font("新細明體", Font.BOLD, 15));
        label_1.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(label_1);
        passwordField = new JPasswordField();
        getContentPane().add(passwordField);

        loginButton = new JButton("登入");
        loginButton.setFont(new Font("新細明體", Font.BOLD, 15));
        registerButton = new JButton("註冊");
        registerButton.setFont(new Font("新細明體", Font.BOLD, 15));
        getContentPane().add(loginButton);
        getContentPane().add(registerButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                User user = userController.login(username, password);
                if (user != null) {
                    JOptionPane.showMessageDialog(LoginView.this, "登入成功");
                    new MainView(user).setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginView.this, "登入失敗");
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegisterView().setVisible(true);
                dispose();
            }
        });

        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }
}