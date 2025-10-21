package ui;

import db.DatabaseConnection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MakeSaleUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Make a Sale");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);

        JLabel productLabel = new JLabel("Select Product:");
        productLabel.setBounds(30, 40, 100, 25);
        frame.add(productLabel);

        JComboBox<String> productBox = new JComboBox<>();
        productBox.setBounds(150, 40, 180, 25);
        frame.add(productBox);

        JLabel qtyLabel = new JLabel("Quantity:");
        qtyLabel.setBounds(30, 80, 100, 25);
        frame.add(qtyLabel);

        JTextField qtyField = new JTextField();
        qtyField.setBounds(150, 80, 80, 25);
        frame.add(qtyField);

        JButton saleBtn = new JButton("Complete Sale");
        saleBtn.setBounds(120, 140, 150, 30);
        frame.add(saleBtn);

        // Load products into ComboBox
        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT name FROM products")) {
            while (rs.next()) {
                productBox.addItem(rs.getString("name"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "❌ Error loading products: " + e.getMessage());
        }

        saleBtn.addActionListener((ActionEvent e) -> {
            String productName = (String) productBox.getSelectedItem();
            int quantity = Integer.parseInt(qtyField.getText());
            double price = 0;

            try (Connection conn = DatabaseConnection.connect()) {
                // Get product price
                String getPrice = "SELECT price FROM products WHERE name = ?";
                PreparedStatement pstmt = conn.prepareStatement(getPrice);
                pstmt.setString(1, productName);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    price = rs.getDouble("price");
                }

                double total = price * quantity;
                String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                // Insert into sales table
                String insertSale = "INSERT INTO sales (product_name, quantity, total, date) VALUES (?, ?, ?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertSale);
                insertStmt.setString(1, productName);
                insertStmt.setInt(2, quantity);
                insertStmt.setDouble(3, total);
                insertStmt.setString(4, date);
                insertStmt.executeUpdate();

                JOptionPane.showMessageDialog(frame, "✅ Sale completed! Total: ₹" + total);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "❌ Error: " + ex.getMessage());
            }
        });

        frame.setVisible(true);
    }
}
