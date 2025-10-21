
package ui;

import db.DatabaseConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class ViewSalesUI extends JFrame {
    private JTable salesTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton searchBtn, resetBtn, closeBtn;

    public ViewSalesUI() {
        setTitle("Sales Report");
        setSize(750, 440);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new java.awt.Color(245, 247, 250));
        setLayout(new BorderLayout(10, 10));

        // Section Header
        JLabel header = new JLabel("Sales Report", JLabel.CENTER);
        header.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 20));
        header.setForeground(new java.awt.Color(40, 53, 88));
        add(header, BorderLayout.NORTH);

        // Table setup
        tableModel = new DefaultTableModel(new String[]{"Product", "Quantity", "Total", "Date"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        salesTable = new JTable(tableModel);
        salesTable.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        salesTable.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        salesTable.setRowHeight(24);
        JScrollPane scrollPane = new JScrollPane(salesTable);
        add(scrollPane, BorderLayout.CENTER);

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(new java.awt.Color(245, 247, 250));
        searchField = new JTextField(20);
        searchField.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        searchField.setToolTipText("Search by product name or date (YYYY-MM-DD)");
        searchBtn = new JButton("Search");
        searchBtn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        searchBtn.setBackground(new java.awt.Color(76, 175, 80));
        searchBtn.setForeground(java.awt.Color.WHITE);
        searchBtn.setFocusPainted(false);
        resetBtn = new JButton("Reset");
        resetBtn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        resetBtn.setBackground(new java.awt.Color(158, 158, 158));
        resetBtn.setForeground(java.awt.Color.WHITE);
        resetBtn.setFocusPainted(false);
        closeBtn = new JButton("Close");
        closeBtn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        closeBtn.setBackground(new java.awt.Color(244, 67, 54));
        closeBtn.setForeground(java.awt.Color.WHITE);
        closeBtn.setFocusPainted(false);
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);
        searchPanel.add(resetBtn);
        searchPanel.add(closeBtn);
        add(searchPanel, BorderLayout.SOUTH);

        // Load all sales initially
        loadSalesTable("");

        // Search button action
        searchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                String query = searchField.getText().trim();
                loadSalesTable(query);
            }
        });

        // Reset button action
        resetBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                searchField.setText("");
                loadSalesTable("");
            }
        });

        // Close button action
        closeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                dispose();
            }
        });
    }

    private void loadSalesTable(String search) {
        tableModel.setRowCount(0);
        String sql = "SELECT sale_id, product_name, quantity, total, date, customer_name, customer_number FROM sales";
        boolean hasSearch = search != null && !search.isEmpty();
        if (hasSearch) {
            sql += " WHERE product_name LIKE ? OR date LIKE ? OR customer_name LIKE ? OR customer_number LIKE ?";
        }
        sql += " ORDER BY date DESC, sale_id DESC";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (hasSearch) {
                String like = "%" + search + "%";
                stmt.setString(1, like);
                stmt.setString(2, like);
                stmt.setString(3, like);
                stmt.setString(4, like);
            }
            ResultSet rs = stmt.executeQuery();
            String lastSaleId = null;
            String lastCustomer = null;
            String lastNumber = null;
            String lastDate = null;
            double saleTotal = 0;
            java.util.List<String> products = new java.util.ArrayList<>();
            while (rs.next()) {
                String saleId = rs.getString("sale_id");
                String customer = rs.getString("customer_name");
                String number = rs.getString("customer_number");
                String date = rs.getString("date");
                String product = rs.getString("product_name");
                int qty = rs.getInt("quantity");
                double total = rs.getDouble("total");
                if (lastSaleId != null && !saleId.equals(lastSaleId)) {
                    // Output previous sale group
                    Vector<Object> row = new Vector<>();
                    row.add("Customer: " + (lastCustomer == null ? "-" : lastCustomer) + " | Number: " + (lastNumber == null ? "-" : lastNumber));
                    row.add("");
                    row.add("");
                    row.add("Date: " + lastDate);
                    tableModel.addRow(row);
                    for (String prod : products) {
                        tableModel.addRow(new Object[]{prod, "", "", ""});
                    }
                    Vector<Object> totalRow = new Vector<>();
                    totalRow.add("Total: ");
                    totalRow.add("");
                    totalRow.add(saleTotal);
                    totalRow.add("");
                    tableModel.addRow(totalRow);
                    // Reset
                    products.clear();
                    saleTotal = 0;
                }
                lastSaleId = saleId;
                lastCustomer = customer;
                lastNumber = number;
                lastDate = date;
                products.add(product + " x" + qty + " (₹" + total + ")");
                saleTotal += total;
            }
            // Output last group
            if (lastSaleId != null) {
                Vector<Object> row = new Vector<>();
                row.add("Customer: " + (lastCustomer == null ? "-" : lastCustomer) + " | Number: " + (lastNumber == null ? "-" : lastNumber));
                row.add("");
                row.add("");
                row.add("Date: " + lastDate);
                tableModel.addRow(row);
                for (String prod : products) {
                    tableModel.addRow(new Object[]{prod, "", "", ""});
                }
                Vector<Object> totalRow = new Vector<>();
                totalRow.add("Total: ");
                totalRow.add("");
                totalRow.add(saleTotal);
                totalRow.add("");
                tableModel.addRow(totalRow);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "An error occurred while loading sales. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}

