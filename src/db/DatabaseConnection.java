package db;

import java.sql.*; // importing java SQL related libraries.

public class DatabaseConnection {
    // Use a file named `coffee_shop.db` in the project root by default.
    // We return a new Connection for each call so callers (try-with-resources)
    // can safely close their Connection without affecting others.

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:coffee_shop.db");
    }

    // Initialize DB schema at class load time (creates tables if missing).
    static {
        initializeDatabase();
    }

    private static void initializeDatabase() {
        String createProducts = "CREATE TABLE IF NOT EXISTS products (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "price REAL NOT NULL" +
                ")";

        String createSales = "CREATE TABLE IF NOT EXISTS sales (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "product_name TEXT NOT NULL, " +
                "quantity INTEGER NOT NULL, " +
                "total REAL NOT NULL, " +
                "date TEXT NOT NULL" +
                ")";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createProducts);
            stmt.execute(createSales);
        } catch (SQLException e) {
            System.out.println("Failed to initialize database: " + e.getMessage());
        }
    }

    public DatabaseConnection() {

    }

    public static Connection connect() {
        try {
            return getConnection();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
