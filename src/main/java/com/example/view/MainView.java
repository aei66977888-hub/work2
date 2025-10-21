package com.example.view;

import com.example.controller.OrderController;
import com.example.model.Order;
import com.example.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class MainView extends JFrame {
    private User user;
    private OrderController orderController;
    private JTextField productField, quantityField;
    private JButton addOrderButton, logoutButton, exportToExcelButton;
    private JTable orderTable;
    private DefaultTableModel tableModel;

    public MainView(User user) {
        this.user = user;
        this.orderController = new OrderController();
        initializeUI();
        loadOrders();
    }

    private void initializeUI() {
        setTitle("歡迎你, " + user.getName());
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        JLabel label = new JLabel("商品名稱(請自行輸入想要的商品名稱)");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        inputPanel.add(label);
        productField = new JTextField();
        inputPanel.add(productField);
        JLabel label_1 = new JLabel("商品數量");
        label_1.setHorizontalAlignment(SwingConstants.CENTER);
        inputPanel.add(label_1);
        quantityField = new JTextField();
        inputPanel.add(quantityField);
        addOrderButton = new JButton("我要下單");
        logoutButton = new JButton("登出");
        inputPanel.add(addOrderButton);
        inputPanel.add(logoutButton);
        exportToExcelButton = new JButton("匯出到 Excel");
        inputPanel.add(exportToExcelButton);
        inputPanel.add(new JLabel("")); // 佔位符，保持 GridLayout 對齊
        getContentPane().add(inputPanel, BorderLayout.NORTH);

        // Order Table
        String[] columns = {"訂單順序", "商品名稱", "數量", "下單日期"};
        tableModel = new DefaultTableModel(columns, 0);
        orderTable = new JTable(tableModel);

        // Center-align table header text
        JTableHeader header = orderTable.getTableHeader();
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) header.getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        getContentPane().add(new JScrollPane(orderTable), BorderLayout.CENTER);

        // Add Order Action
        addOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String productName = productField.getText();
                String quantityText = quantityField.getText();
                if (productName.isEmpty() || quantityText.isEmpty()) {
                    JOptionPane.showMessageDialog(MainView.this, "請填寫所有欄位！");
                    return;
                }
                try {
                    int quantity = Integer.parseInt(quantityText);
                    orderController.addOrder(user.getId(), productName, quantity);
                    productField.setText("");
                    quantityField.setText("");
                    loadOrders();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(MainView.this, "數量必須為數字！");
                }
            }
        });

        // Logout Action
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginView().setVisible(true);
                dispose();
            }
        });

        // Export to Excel Action
        exportToExcelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    exportOrdersToExcel();
                    JOptionPane.showMessageDialog(MainView.this, "訂單已成功匯出到 orders_export.xlsx！");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(MainView.this, "匯出失敗：" + ex.getMessage());
                }
            }
        });

        setLocationRelativeTo(null);
    }

    private void loadOrders() {
        tableModel.setRowCount(0);
        List<Order> orders = orderController.getOrders(user.getId());
        for (Order order : orders) {
            tableModel.addRow(new Object[]{order.getId(), order.getProductName(), order.getQuantity(), order.getOrderDate()});
        }
    }

    private void exportOrdersToExcel() throws IOException {
        // 建立 Excel 工作簿
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("訂單");

        // 建立標題列
        String[] headers = {"訂單順序", "商品名稱", "數量", "下單日期"};
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // 從資料庫獲取訂單資料
        List<Order> orders = orderController.getOrders(user.getId());
        int rowNum = 1;
        for (Order order : orders) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(order.getId());
            row.createCell(1).setCellValue(order.getProductName());
            row.createCell(2).setCellValue(order.getQuantity());
            row.createCell(3).setCellValue(order.getOrderDate().toString());
        }

        // 自動調整欄寬
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // 儲存 Excel 檔案
        try (FileOutputStream fileOut = new FileOutputStream("orders_export.xlsx")) {
            workbook.write(fileOut);
        }
        workbook.close();
    }
}