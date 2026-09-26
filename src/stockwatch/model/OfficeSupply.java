package stockwatch.model;

public class OfficeSupply extends StockItem {

    private String type;

    public OfficeSupply(String itemName, int quantity, int reorderLevel, String type) {
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
        return "Office Supply";
    }

    @Override
    public String checkStockLevel() {
        if (isLowStock()) {
            return "LOW STOCK";
        }

        return "IN STOCK";
    }
}