package stockwatch.model;

public abstract class StockItem {

    private String itemName;
    private int quantity;
    private int reorderLevel;

    public StockItem(String itemName, int quantity, int reorderLevel) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.reorderLevel = reorderLevel;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getReorderLevel() {
        return reorderLevel;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setReorderLevel(int reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public void updateQuantity(int amount) {
        quantity += amount;
    }

    public boolean isLowStock() {
        return quantity <= reorderLevel;
    }

    public abstract String getCategory();

    public abstract String getType();

    public abstract String checkStockLevel();
}