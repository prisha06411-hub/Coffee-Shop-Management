package ui;

/**
 * Entry point — demonstrates simple MVC composition:
 * UI layer (Swing) interacts with database and models.
 *
 * To run:
 * 1) Ensure sqlite-jdbc JAR is in classpath (or Maven dependency).
 * 2) The 'coffee_shop.db' file should be located in the /database folder.
 * 3) Run this Main class — it will open the Coffee Shop POS main menu.
 */
public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new MainMenuUI().main(null); // Launch main menu
        });
    }
}
