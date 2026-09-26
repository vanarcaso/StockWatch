package stockwatch.model;

public class Electronics extends StockItem {

    private String type;

    public Electronics(String itemName, int quantity, int reorderLevel, String type) {
        super(itemName, quantity, reorderLevel);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getCategory() {
        return "Electronics";
    }

    @Override
    public String checkStockLevel() {
        if (isLowStock()) {
            return "LOW STOCK";
        }

        return "IN STOCK";
    }
}