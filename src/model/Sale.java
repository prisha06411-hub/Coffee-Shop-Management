package model;

public class Sale {
    private int id;
    private String productName;
    private int quantity;
    private double total;
    private String date;

    // ✅ Empty constructor (required for flexibility)
    public Sale() {}

    // ✅ Constructor with parameters
    public Sale(String productName, int quantity, double total, String date) {
        this.productName = productName;
        this.quantity = quantity;
        this.total = total;
        this.date = date;
    }

    // ✅ Full constructor (includes ID)
    public Sale(int id, String productName, int quantity, double total, String date) {
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
        this.total = total;
        this.date = date;
    }

    // 🧱 Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    // ✅ Helper method to print sale details
    @Override
    public String toString() {
        return "Sale{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", total=" + total +
                ", date='" + date + '\'' +
                '}';
    }
}

