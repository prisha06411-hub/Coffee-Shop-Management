package ui;

import db.DatabaseConnection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddProductUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Add New Item");
        frame.setSize(350, 250);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);

        JLabel nameLabel = new JLabel("Product Name:");
        nameLabel.setBounds(30, 30, 100, 25);
        frame.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(150, 30, 150, 25);
        frame.add(nameField);

        JLabel priceLabel = new JLabel("Price (₹):");
        priceLabel.setBounds(30, 70, 100, 25);
        frame.add(priceLabel);

        JTextField priceField = new JTextField();
        priceField.setBounds(150, 70, 150, 25);
        frame.add(priceField);

        JButton addBtn = new JButton("Add");
        addBtn.setBounds(100, 120, 100, 30);
        frame.add(addBtn);

        addBtn.addActionListener((ActionEvent e) -> {
            String name = nameField.getText();
            double price = Double.parseDouble(priceField.getText());

            try (Connection conn = DatabaseConnection.connect()) {
                String sql = "INSERT INTO products (name, price) VALUES (?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, name);
                pstmt.setDouble(2, price);
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(frame, "Product added!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            }
        });

        frame.setVisible(true);
    }
}
