package stockwatch.model;

import java.util.ArrayList;
import java.util.List;

public class InventoryManager {

    private List<StockItem> items;
    private List<StockTransaction> transactions;

    public InventoryManager() {
        items = new ArrayList<>();
        transactions = new ArrayList<>();
    }

    public void addItem(StockItem item) {
        items.add(item);
    }

    public List<StockItem> getAllItems() {
        return items;
    }

    public List<StockTransaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(StockTransaction transaction) {
        transactions.add(transaction);
    }

    public StockItem findItem(String itemName) {
        for (StockItem item : items) {
            if (item.getItemName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }

        return null;
    }

    public List<StockItem> getLowStockItems() {
        List<StockItem> lowStockItems = new ArrayList<>();

        for (StockItem item : items) {
            if (item.isLowStock()) {
                lowStockItems.add(item);
            }
        }

        return lowStockItems;
    }
}