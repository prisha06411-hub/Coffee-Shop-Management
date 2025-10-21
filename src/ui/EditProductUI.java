package ui;


import db.DatabaseConnection;
import util.UIHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EditProductUI extends JFrame {
    public EditProductUI() {
        super("Edit Product");
        setSize(520, 370);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new java.awt.Color(245, 247, 250));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(14, 18, 14, 18);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Section Header
        JLabel header = new JLabel("Edit Product", JLabel.CENTER);
        header.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 20));
        header.setForeground(new java.awt.Color(40, 53, 88));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        this.add(header, gbc);

        gbc.gridwidth = 1;
        JLabel selectLabel = new JLabel("Select Product:");
        selectLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 1;
        this.add(selectLabel, gbc);
        JComboBox<String> productCombo = new JComboBox<>();
        productCombo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        gbc.gridx = 1;
        this.add(productCombo, gbc);
        UIHelper.updateProductCombo(productCombo);

        JLabel nameLabel = new JLabel("New Name:");
        nameLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 2;
        this.add(nameLabel, gbc);
        JTextField nameField = new JTextField();
        nameField.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        gbc.gridx = 1;
        this.add(nameField, gbc);

        JLabel priceLabel = new JLabel("New Price (\u20b9):");
        priceLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 3;
        this.add(priceLabel, gbc);
        JTextField priceField = new JTextField();
        priceField.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        gbc.gridx = 1;
        this.add(priceField, gbc);

        JLabel qtyLabel = new JLabel("New Quantity:");
        qtyLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 4;
        this.add(qtyLabel, gbc);
        JTextField qtyField = new JTextField();
        qtyField.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        gbc.gridx = 1;
        this.add(qtyField, gbc);

        JButton updateBtn = new JButton("Update");
        updateBtn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 15));
        updateBtn.setBackground(new java.awt.Color(33, 150, 243));
        updateBtn.setForeground(java.awt.Color.WHITE);
        updateBtn.setFocusPainted(false);
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        this.add(updateBtn, gbc);
        gbc.gridwidth = 1;

        productCombo.addActionListener((ActionEvent e) -> {
            String selected = (String) productCombo.getSelectedItem();
            if (selected == null) return;
            String productName = selected.split(" \\(")[0];
            try (Connection conn = DatabaseConnection.connect();
                 PreparedStatement stmt = conn.prepareStatement("SELECT price, quantity FROM products WHERE name = ?")) {
                stmt.setString(1, productName);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    nameField.setText(productName);
                    priceField.setText(String.valueOf(rs.getDouble("price")));
                    qtyField.setText(String.valueOf(rs.getInt("quantity")));
                }
            } catch (Exception ex) {
                UIHelper.showErrorDialog(this, "An error occurred while loading product details. Please try again.", "Database Error");
                ex.printStackTrace();
            }
        });

        updateBtn.addActionListener((ActionEvent e) -> {
            String selected = (String) productCombo.getSelectedItem();
            if (selected == null) {
                UIHelper.showErrorDialog(this, "No product selected.", "Input Error");
                return;
            }
            String oldName = selected.split(" \\(")[0];
            String newName = nameField.getText().trim();
            String priceText = priceField.getText().trim();
            String qtyText = qtyField.getText().trim();
            if (newName.isEmpty()) {
                UIHelper.showErrorDialog(this, "Product name cannot be empty.", "Input Error");
                return;
            }
            double price;
            int quantity;
            try {
                price = Double.parseDouble(priceText);
                if (price <= 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                UIHelper.showErrorDialog(this, "Invalid price. Enter a positive number.", "Input Error");
                return;
            }
            try {
                quantity = Integer.parseInt(qtyText);
                if (quantity < 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                UIHelper.showErrorDialog(this, "Invalid quantity. Enter zero or a positive integer.", "Input Error");
                return;
            }
            try (Connection conn = DatabaseConnection.connect()) {
                String sql = "UPDATE products SET name = ?, price = ?, quantity = ? WHERE name = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, newName);
                pstmt.setDouble(2, price);
                pstmt.setInt(3, quantity);
                pstmt.setString(4, oldName);
                int affected = pstmt.executeUpdate();
                if (affected > 0) {
                    UIHelper.showInfoDialog(this, "Product updated!");
                    UIHelper.updateProductCombo(productCombo);
                } else {
                    UIHelper.showErrorDialog(this, "Product not found.", "Database Error");
                }
            } catch (Exception ex) {
                UIHelper.showErrorDialog(this, "Error: " + ex.getMessage(), "Database Error");
            }
        });

    // setVisible(true); // Let caller decide
    }

    // updateProductCombo now handled by UIHelper
}
