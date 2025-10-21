package ui;


import db.DatabaseConnection;
import util.UIHelper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.ResultSet;

public class AddProductUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Add or Remove Product");
        frame.setSize(520, 420);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new java.awt.GridBagLayout());
        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.insets = new java.awt.Insets(12, 16, 12, 16);
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;

        // Set a subtle background color for modern look
        frame.getContentPane().setBackground(new java.awt.Color(245, 247, 250));

        // Section Header
        JLabel header = new JLabel("Product Inventory Management", JLabel.CENTER);
        header.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 22));
        header.setForeground(new java.awt.Color(40, 53, 88));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        frame.add(header, gbc);

        // Add Product Section
        JLabel addLabel = new JLabel("Add Product");
        addLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 16));
        addLabel.setForeground(new java.awt.Color(60, 80, 120));
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        frame.add(addLabel, gbc);

        gbc.gridwidth = 1;
        JLabel nameLabel = new JLabel("Product Name:");
        nameLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        nameLabel.setToolTipText("Enter the name of the product to add.");
        gbc.gridx = 0; gbc.gridy = 2;
        frame.add(nameLabel, gbc);
        JTextField nameField = new JTextField();
        nameField.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        nameField.setToolTipText("Type the product name here.");
        gbc.gridx = 1;
        frame.add(nameField, gbc);

        JLabel priceLabel = new JLabel("Price (₹):");
        priceLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        priceLabel.setToolTipText("Enter the price of the product in rupees.");
        gbc.gridx = 0; gbc.gridy = 3;
        frame.add(priceLabel, gbc);
        JTextField priceField = new JTextField();
        priceField.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        priceField.setToolTipText("Type the price (e.g., 99.99).");
        gbc.gridx = 1;
        frame.add(priceField, gbc);

        JLabel qtyLabel = new JLabel("Quantity:");
        qtyLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        qtyLabel.setToolTipText("Enter the quantity to add.");
        gbc.gridx = 0; gbc.gridy = 4;
        frame.add(qtyLabel, gbc);
        JTextField qtyField = new JTextField();
        qtyField.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        qtyField.setToolTipText("Type the quantity to add (must be positive integer).");
        gbc.gridx = 1;
        frame.add(qtyField, gbc);

        JButton addBtn = new JButton("Add");
        addBtn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        addBtn.setBackground(new java.awt.Color(76, 175, 80));
        addBtn.setForeground(java.awt.Color.WHITE);
        addBtn.setFocusPainted(false);
        addBtn.setToolTipText("Add the product or increase its quantity.");
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        frame.add(addBtn, gbc);
        gbc.gridwidth = 1;

        // Remove Product Section
        JLabel removeLabel = new JLabel("Remove Product");
        removeLabel.setFont(removeLabel.getFont().deriveFont(java.awt.Font.BOLD, 16f));
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        frame.add(removeLabel, gbc);
        gbc.gridwidth = 1;

    JLabel selectLabel = new JLabel("Select Product:");
    selectLabel.setToolTipText("Choose a product to remove or decrease quantity.");
        gbc.gridx = 0; gbc.gridy = 6;
        frame.add(selectLabel, gbc);
    JComboBox<String> productCombo = new JComboBox<>();
    productCombo.setToolTipText("Select the product to remove or decrease quantity.");
        gbc.gridx = 1;
        frame.add(productCombo, gbc);
    UIHelper.updateProductCombo(productCombo);

    JLabel removeQtyLabel = new JLabel("Quantity to Remove:");
    removeQtyLabel.setToolTipText("Enter the quantity to remove.");
        gbc.gridx = 0; gbc.gridy = 7;
        frame.add(removeQtyLabel, gbc);
    JTextField removeQtyField = new JTextField();
    removeQtyField.setToolTipText("Type the quantity to remove (must be positive integer).");
        gbc.gridx = 1;
        frame.add(removeQtyField, gbc);

    JButton removeBtn = new JButton("Remove");
    removeBtn.setToolTipText("Remove the selected product or decrease its quantity.");
        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2;
        frame.add(removeBtn, gbc);
        gbc.gridwidth = 1;

        // Add product logic
        addBtn.addActionListener((ActionEvent e) -> {
            String name = nameField.getText().trim();
            String priceText = priceField.getText().trim();
            String qtyText = qtyField.getText().trim();
            if (name.isEmpty()) {
                UIHelper.showErrorDialog(frame, "Product name cannot be empty.", "Input Error");
                return;
            }
            double price;
            int quantity;
            try {
                price = Double.parseDouble(priceText);
                if (price <= 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                UIHelper.showErrorDialog(frame, "Invalid price. Enter a positive number.", "Input Error");
                return;
            }
            try {
                quantity = Integer.parseInt(qtyText);
                if (quantity < 1) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                UIHelper.showErrorDialog(frame, "Invalid quantity. Enter a positive integer.", "Input Error");
                return;
            }
            try (Connection conn = DatabaseConnection.connect()) {
                // Check if product exists
                String checkSql = "SELECT id, quantity FROM products WHERE name = ?";
                PreparedStatement checkStmt = conn.prepareStatement(checkSql);
                checkStmt.setString(1, name);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    String updateSql = "UPDATE products SET quantity = quantity + ?, price = ? WHERE name = ?";
                    PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                    updateStmt.setInt(1, quantity);
                    updateStmt.setDouble(2, price);
                    updateStmt.setString(3, name);
                    updateStmt.executeUpdate();
                    UIHelper.showInfoDialog(frame, "Product quantity updated!");
                    nameField.setText("");
                    priceField.setText("");
                    qtyField.setText("");
                } else {
                    // New product
                    String sql = "INSERT INTO products (name, price, quantity) VALUES (?, ?, ?)";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, name);
                    pstmt.setDouble(2, price);
                    pstmt.setInt(3, quantity);
                    pstmt.executeUpdate();
                    UIHelper.showInfoDialog(frame, "Product added!");
                    nameField.setText("");
                    priceField.setText("");
                    qtyField.setText("");
                }
                UIHelper.updateProductCombo(productCombo);
            } catch (Exception ex) {
                UIHelper.showErrorDialog(frame, "Error: " + ex.getMessage(), "Database Error");
            }
        });


        // Remove product logic
        removeBtn.addActionListener((ActionEvent e) -> {
            String selected = (String) productCombo.getSelectedItem();
            String removeQtyText = removeQtyField.getText().trim();
            if (selected == null) {
                UIHelper.showErrorDialog(frame, "No product selected.", "Input Error");
                return;
            }
            int removeQty;
            try {
                removeQty = Integer.parseInt(removeQtyText);
                if (removeQty < 1) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                UIHelper.showErrorDialog(frame, "Invalid quantity to remove. Enter a positive integer.", "Input Error");
                return;
            }
            // Extract product name from combo box item
            String productName = selected.split(" \\(")[0];

            int confirm = JOptionPane.showConfirmDialog(frame,
                "Are you sure you want to " + (removeQty == 1 ? "remove 1 unit of " : ("remove " + removeQty + " units of ")) + productName +
                ((removeQty == 1) ? "?" : "? This may delete the product if quantity reaches zero."),
                "Confirm Removal", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (confirm != JOptionPane.YES_OPTION) return;

            try (Connection conn = DatabaseConnection.connect()) {
                // Get current quantity
                String getQtySql = "SELECT quantity FROM products WHERE name = ?";
                PreparedStatement getQtyStmt = conn.prepareStatement(getQtySql);
                getQtyStmt.setString(1, productName);
                ResultSet rs = getQtyStmt.executeQuery();
                if (rs.next()) {
                    int currentQty = rs.getInt("quantity");
                    if (removeQty >= currentQty) {
                        // Remove product
                        String delSql = "DELETE FROM products WHERE name = ?";
                        PreparedStatement delStmt = conn.prepareStatement(delSql);
                        delStmt.setString(1, productName);
                        delStmt.executeUpdate();
                        UIHelper.showInfoDialog(frame, "Product removed completely!");
                        removeQtyField.setText("");
                    } else {
                        // Decrease quantity
                        String updSql = "UPDATE products SET quantity = quantity - ? WHERE name = ?";
                        PreparedStatement updStmt = conn.prepareStatement(updSql);
                        updStmt.setInt(1, removeQty);
                        updStmt.setString(2, productName);
                        updStmt.executeUpdate();
                        UIHelper.showInfoDialog(frame, "Product quantity decreased!");
                        removeQtyField.setText("");
                    }
                    UIHelper.updateProductCombo(productCombo);
                } else {
                    UIHelper.showErrorDialog(frame, "Product not found.", "Database Error");
                }
            } catch (Exception ex) {
                UIHelper.showErrorDialog(frame, "Error: " + ex.getMessage(), "Database Error");
            }
        });

        frame.setVisible(true);
    }

    // Helper to update product combo box
    // updateProductCombo now handled by UIHelper
}
