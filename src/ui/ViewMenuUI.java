package ui;

import db.DatabaseConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ViewMenuUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("☕ Coffee Menu");
        frame.setSize(440, 340);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setBackground(new java.awt.Color(245, 247, 250));
        frame.setLayout(new java.awt.BorderLayout(10, 10));

        // Section Header
        JLabel header = new JLabel("Coffee Menu", JLabel.CENTER);
        header.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 20));
        header.setForeground(new java.awt.Color(40, 53, 88));
        frame.add(header, java.awt.BorderLayout.NORTH);

        String[] columns = {"ID", "Name", "Price (₹)"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        table.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        table.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        table.setRowHeight(24);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, java.awt.BorderLayout.CENTER);

        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM products")) {
            while (rs.next()) {
                Object[] row = {
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price")
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "An error occurred while loading the menu. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

