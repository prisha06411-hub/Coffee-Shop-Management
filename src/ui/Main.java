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
        // Set modern Look and Feel (FlatLaf if available, else Nimbus)
        try {
            // Try FlatLaf first
            Class.forName("com.formdev.flatlaf.FlatLightLaf");
            javax.swing.UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
        } catch (Exception flatlafEx) {
            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception nimbusEx) {
                // If Nimbus is not available, fall back to default
            }
        }
        javax.swing.SwingUtilities.invokeLater(() -> {
            new MainMenuUI().main(null); // Launch main menu
        });
    }
}
