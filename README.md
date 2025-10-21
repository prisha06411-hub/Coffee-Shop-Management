
# Pichu's Cafe - Coffee Shop Management System

## Table of Contents
1. [Project Overview](#project-overview)
2. [Architecture & Design](#architecture--design)
7. [Setup & Usage](#setup--usage)
8. [Extensibility](#extensibility)
9. [Troubleshooting](#troubleshooting)
10. [Contributing](#contributing)
11. [License](#license)

### Key Classes & Responsibilities
- `db.DatabaseConnection`: Handles DB connection, schema creation, error logging.
- `model.Product`: Product entity (name, price, quantity), with getters/setters.
- `model.Sale`: Sale entity (id, productName, quantity, total, date, customer info).
- `ui.MainMenuUI`: Main navigation, mascot display, launches all screens.
- `ui.AddProductUI`: Add/remove products, validates input, updates DB.
- `ui.EditProductUI`: Edit product details, validates and updates DB.
- `ui.InventoryViewUI`: Table view of products, search/filter, live updates.
- `ui.MakeSaleUI`: Multi-item cart, customer info, stock checks, records sales.
- `ui.ViewSalesUI`: Grouped sales report, customer info, search/filter.
- `util.UIHelper`: Shared UI logic (combos, dialogs, error/info popups).

### UI/UX Design
- Consistent color palette, font sizes, spacing for clarity.
- Responsive layouts (GridBagLayout, BorderLayout, FlowLayout).
- Tooltips for all interactive elements.
- Dialogs for errors, confirmations, and success.
- Mascot image (`Pichu.png`) beside main title.

### Threading & Event Dispatch
- All UI creation and updates use `SwingUtilities.invokeLater` for thread safety.
- DB operations run synchronously; for heavy tasks, use background threads.

### Error Handling
- All exceptions caught and shown via dialogs (`UIHelper.showErrorDialog`).
- Input validation prevents empty/invalid data.
- DB errors logged to console and shown to user.

### Extensibility
- Add new screens: Create new UI class in `src/ui/`, add button in `MainMenuUI`.
- Add new DB fields: Update schema in `DatabaseConnection.java`, extend models.
- Add new reports: Create new UI/report class, query DB as needed.

### Example Workflow
1. Launch app (`Main.java`).
2. Add products via Add/Remove screen.
3. Edit products as needed.
4. View inventory for stock levels.
5. Make a sale: select products, set quantities, enter customer info, confirm.
6. View sales report: see grouped transactions, customer info, search/filter.

---

---

## 1. Project Overview

## 2. Architecture & Design
- **Language:** Java 17+
- **UI:** Java Swing (JFrame, JPanel, JTable, etc.)
- **Design Principles:**
	- Modular UI classes for each screen
	- MVC-inspired separation (model, db, ui, util)
	- Helper utilities for repeated UI/DB logic
	- Thread safety: All UI updates on Event Dispatch Thread
	- Exception handling: User-friendly dialogs for all errors

## 3. Features
- Product management: Add, remove, edit products (name, price, quantity)
- Inventory view: Table of products, search/filter

### SQL Schema
```sql
CREATE TABLE IF NOT EXISTS products (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    price REAL NOT NULL,
    quantity INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS sales (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    sale_id TEXT NOT NULL,
    product_name TEXT NOT NULL,
    quantity INTEGER NOT NULL,
    total REAL NOT NULL,
    date TEXT NOT NULL,
    customer_name TEXT,
    customer_number TEXT
);
```

---
- Sales: Multi-item cart, customer name & number, quantity checks

### Screen Details
- **MainMenuUI:**
  - Full-screen, centered buttons, mascot beside "Pichu's Cafe".
  - All features accessible via large, color-coded buttons.
- **AddProductUI:**
  - Form for name, price, quantity. Add/remove buttons. Success/error dialogs.
  - Tooltips for all fields. Input validation (no empty/negative values).
- **EditProductUI:**
  - Select product, edit fields, save changes. Error handling for invalid edits.
- **InventoryViewUI:**
  - Table of products, live search/filter. Non-editable rows. Refresh button.
- **MakeSaleUI:**
  - Product dropdown, quantity field, add to cart. Cart table shows all items.
  - Customer name/number fields. Sale confirmation dialog. Stock checks.
- **ViewSalesUI:**
  - Table grouped by sale_id, shows all products per transaction, customer info.
  - Search by product/date/customer. Reset button. Non-editable rows.

---
**File:** `coffee_shop.db` (auto-created)

	- `id` INTEGER PRIMARY KEY AUTOINCREMENT
	- `name` TEXT NOT NULL
	- `price` REAL NOT NULL
	- `quantity` INTEGER NOT NULL DEFAULT 0
- **sales**
	- `id` INTEGER PRIMARY KEY AUTOINCREMENT
	- `sale_id` TEXT NOT NULL (groups items in a transaction)
	- `product_name` TEXT NOT NULL
	- `quantity` INTEGER NOT NULL
	- `total` REAL NOT NULL
	- `date` TEXT NOT NULL
	- `customer_name` TEXT
	- `customer_number` TEXT

**Schema is initialized automatically by `DatabaseConnection.java` on first run.**

## 5. UI Screens & Flow
- **Main Menu:** Full-screen, mascot beside title, navigation buttons
- **Sales Report:** Grouped by sale, shows all items per transaction, customer info, search/filter

- All screens accessible from main menu

## 6. Project Structure
```
Coffee-Shop-Management/
├── lib/                     # External libraries (JDBC, etc.)
├── src/
│   ├── assets/              # Images and other assets
│   │   └── Pichu.png        # Pichu mascot image
│   │   └── DatabaseConnection.java  # DB connection & schema
│   ├── model/
│   │   ├── Ingredient.java  # Ingredient model (future use)
│   │   ├── Product.java     # Product model
│   │   └── Sale.java        # Sale model
│   ├── ui/
│   │   ├── AddProductUI.java
│   │   ├── EditProductUI.java
│   │   ├── InventoryViewUI.java
│   │   ├── Main.java        # App entry point
│   │   ├── MainMenuUI.java  # Main menu screen
│   │   ├── MakeSaleUI.java  # Sale screen (multi-item, customer info)
│   │   ├── ViewMenuUI.java  # Product menu view
│   │   └── ViewSalesUI.java # Sales report view
│   └── util/
│       └── UIHelper.java    # UI helper methods
```

## 7. Setup & Usage
### Prerequisites
- Java 17 or newer
- SQLite JDBC driver in `lib/`

### Running the Application
1. Clone the repository
2. Ensure `lib/` contains the SQLite JDBC jar
3. Run `Main.java` from your IDE or command line:
	 ```
	 java -cp "lib/*;src" ui.Main
	 ```
4. The app launches in full-screen mode. Use the main menu to access all features.

### Typical Workflow
1. Add products to inventory
2. Edit or remove products as needed
3. View inventory for stock levels
4. Make sales (multi-item, customer info)
5. View sales report (grouped by transaction)

## 8. Extensibility
- **Add new features:** Create new UI classes in `src/ui/`, update DB schema in `DatabaseConnection.java`
- **Integrate with other systems:** Use JDBC for external DBs, REST APIs via Java libraries
- **Customize UI:** Edit color/font/layout in UI classes, add assets to `src/assets/`
- **Unit testing:** Add test classes for models and DB logic

## 9. Troubleshooting
- **DB errors:** Ensure `coffee_shop.db` is writable, SQLite JDBC is present
- **UI issues:** Run on Java 17+, check for missing assets (e.g., `Pichu.png`)
- **Thread safety:** All UI updates use `SwingUtilities.invokeLater` for EDT compliance
- **Common errors:**
	- SQL error: Check schema in `DatabaseConnection.java`
	- Null pointer: Validate all user inputs
	- Image not loading: Verify path `src/assets/Pichu.png`

## 10. Contributing
- Fork and clone the repo
- Open issues or pull requests for improvements
- Add JavaDoc/comments for new code

## 11. License
MIT License

---
*Made with ❤️ for coffee lovers!*