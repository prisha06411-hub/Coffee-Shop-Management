package ui;

import db.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class InventoryViewUI extends JFrame {
    public InventoryViewUI() {
        super("Inventory View");
        setSize(650, 440);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new java.awt.Color(245, 247, 250));
        setLayout(new BorderLayout(10, 10));

        // Section Header
        JLabel header = new JLabel("Inventory View", JLabel.CENTER);
        header.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 20));
        header.setForeground(new java.awt.Color(40, 53, 88));
        add(header, BorderLayout.NORTH);

        String[] columns = {"Product Name", "Price (\u20b9)", "Quantity"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        table.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        table.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        table.setRowHeight(24);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(new java.awt.Color(245, 247, 250));
        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        JTextField searchField = new JTextField(20);
        searchField.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        JButton searchBtn = new JButton("Search");
        searchBtn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        searchBtn.setBackground(new java.awt.Color(76, 175, 80));
        searchBtn.setForeground(java.awt.Color.WHITE);
        searchBtn.setFocusPainted(false);
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);
        add(searchPanel, BorderLayout.SOUTH);

        Runnable loadTable = () -> {
            model.setRowCount(0);
            String filter = searchField.getText().trim();
            String sql = "SELECT name, price, quantity FROM products" + (filter.isEmpty() ? "" : " WHERE name LIKE ?");
            try (Connection conn = DatabaseConnection.connect();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                if (!filter.isEmpty()) stmt.setString(1, "%" + filter + "%");
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Object[] row = {
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity")
                    };
                    model.addRow(row);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "An error occurred while loading inventory. Please try again.", "Database Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        };

        searchBtn.addActionListener(e -> loadTable.run());
        searchField.addActionListener(e -> loadTable.run());
        loadTable.run();
    }
    }
