package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MainMenuUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("☕ Coffee Shop POS");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel label = new JLabel("Welcome to Kerala Coffee POS!");
        label.setBounds(80, 20, 250, 30);
        frame.add(label);

        JButton addProduct = new JButton("Add Product");
        addProduct.setBounds(100, 80, 200, 30);
        frame.add(addProduct);

        JButton viewMenu = new JButton("View Menu");
        viewMenu.setBounds(100, 130, 200, 30);
        frame.add(viewMenu);

        JButton makeSale = new JButton("Make Sale");
        makeSale.setBounds(100, 180, 200, 30);
        frame.add(makeSale);

        JButton viewSales = new JButton("View Sales");
        viewSales.setBounds(100, 230, 200, 30);
        frame.add(viewSales);

        addProduct.addActionListener((ActionEvent e) -> AddProductUI.main(null));
        viewMenu.addActionListener((ActionEvent e) -> ViewMenuUI.main(null));
        makeSale.addActionListener((ActionEvent e) -> MakeSaleUI.main(null));
        viewSales.addActionListener((ActionEvent e) -> ViewSalesUI.main(null));

        frame.setVisible(true);
    }
}
