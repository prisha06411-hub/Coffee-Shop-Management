package util;

import db.DatabaseConnection;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UIHelper {
    /**
     * Updates a JComboBox with all products and their quantities from the database.
     * @param combo The combo box to update.
     */
    public static void updateProductCombo(JComboBox<String> combo) {
        combo.removeAllItems();
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement("SELECT name, quantity FROM products");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String name = rs.getString("name");
                int qty = rs.getInt("quantity");
                combo.addItem(name + " (Qty: " + qty + ")");
            }
        } catch (Exception e) {
            // Optionally log or show error
        }
    }

    /**
     * Shows an error dialog with the given message and title.
     * @param parent The parent component (can be null).
     * @param message The error message.
     * @param title The dialog title.
     */
    public static void showErrorDialog(java.awt.Component parent, String message, String title) {
        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Shows a success/info dialog with the given message.
     * @param parent The parent component (can be null).
     * @param message The message.
     */
    public static void showInfoDialog(java.awt.Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }
}
