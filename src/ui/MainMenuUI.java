package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MainMenuUI {
    public static void main(String[] args) {
    JFrame frame = new JFrame("\u2615 Coffee Shop POS");
    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setBackground(new java.awt.Color(245, 247, 250));
    frame.setLayout(new java.awt.GridBagLayout());
    java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
    gbc.insets = new java.awt.Insets(24, 0, 24, 0);
    gbc.fill = java.awt.GridBagConstraints.NONE;
    gbc.anchor = java.awt.GridBagConstraints.CENTER;
    gbc.weightx = 0;
    gbc.weighty = 0;

    // Create a panel for header and image
    JPanel headerPanel = new JPanel();
    headerPanel.setOpaque(false);
    headerPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 24, 0));
    JLabel header = new JLabel("Pichu's Cafe", JLabel.CENTER);
    header.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 48));
    header.setForeground(new java.awt.Color(40, 53, 88));
    // Load Pichu image
    ImageIcon pichuIcon = new ImageIcon("src/assets/Pichu.png");
    // Resize image if needed
    java.awt.Image img = pichuIcon.getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH);
    JLabel pichuLabel = new JLabel(new ImageIcon(img));
    headerPanel.add(pichuLabel);
    headerPanel.add(header);
    gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
    frame.add(headerPanel, gbc);

    JLabel label = new JLabel("Welcome! Please select an option:", JLabel.CENTER);
    label.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 28));
    label.setForeground(new java.awt.Color(60, 80, 120));
    gbc.gridy = 1;
    frame.add(label, gbc);

    gbc.gridwidth = 2;
    gbc.gridy = 2;
    JButton addProduct = new JButton("Add / Remove Product");
    addProduct.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 28));
    addProduct.setBackground(new java.awt.Color(76, 175, 80));
    addProduct.setForeground(java.awt.Color.WHITE);
    addProduct.setFocusPainted(false);
    addProduct.setPreferredSize(new java.awt.Dimension(300, 60));
    frame.add(addProduct, gbc);

    gbc.gridy = 3;
    JButton editProduct = new JButton("Edit Product");
    editProduct.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 28));
    editProduct.setBackground(new java.awt.Color(33, 150, 243));
    editProduct.setForeground(java.awt.Color.WHITE);
    editProduct.setFocusPainted(false);
    editProduct.setPreferredSize(new java.awt.Dimension(300, 60));
    frame.add(editProduct, gbc);

    gbc.gridy = 4;
    JButton viewMenu = new JButton("View Menu");
    viewMenu.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 28));
    viewMenu.setBackground(new java.awt.Color(255, 193, 7));
    viewMenu.setForeground(java.awt.Color.DARK_GRAY);
    viewMenu.setFocusPainted(false);
    viewMenu.setPreferredSize(new java.awt.Dimension(300, 60));
    frame.add(viewMenu, gbc);

    gbc.gridy = 5;
    JButton inventoryView = new JButton("Inventory View");
    inventoryView.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 28));
    inventoryView.setBackground(new java.awt.Color(121, 85, 72));
    inventoryView.setForeground(java.awt.Color.WHITE);
    inventoryView.setFocusPainted(false);
    inventoryView.setPreferredSize(new java.awt.Dimension(300, 60));
    frame.add(inventoryView, gbc);

    gbc.gridy = 6;
    JButton makeSale = new JButton("Make Sale");
    makeSale.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 28));
    makeSale.setBackground(new java.awt.Color(244, 67, 54));
    makeSale.setForeground(java.awt.Color.WHITE);
    makeSale.setFocusPainted(false);
    makeSale.setPreferredSize(new java.awt.Dimension(300, 60));
    frame.add(makeSale, gbc);

    gbc.gridy = 7;
    JButton viewSales = new JButton("Sales Report");
    viewSales.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 28));
    viewSales.setBackground(new java.awt.Color(0, 188, 212));
    viewSales.setForeground(java.awt.Color.WHITE);
    viewSales.setFocusPainted(false);
    viewSales.setPreferredSize(new java.awt.Dimension(300, 60));
    frame.add(viewSales, gbc);

    gbc.gridy = 8;
    JButton exitBtn = new JButton("Exit");
    exitBtn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 28));
    exitBtn.setBackground(new java.awt.Color(158, 158, 158));
    exitBtn.setForeground(java.awt.Color.WHITE);
    exitBtn.setFocusPainted(false);
    exitBtn.setPreferredSize(new java.awt.Dimension(300, 60));
    frame.add(exitBtn, gbc);

        addProduct.addActionListener((ActionEvent e) -> AddProductUI.main(null));
        editProduct.addActionListener((ActionEvent e) -> new EditProductUI().setVisible(true));
        viewMenu.addActionListener((ActionEvent e) -> ViewMenuUI.main(null));
        inventoryView.addActionListener((ActionEvent e) -> new InventoryViewUI().setVisible(true));
        makeSale.addActionListener((ActionEvent e) -> MakeSaleUI.main(null));
        viewSales.addActionListener((ActionEvent e) -> new ViewSalesUI().setVisible(true));
        exitBtn.addActionListener((ActionEvent e) -> frame.dispose());

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
