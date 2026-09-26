package stockwatch.model;

import java.util.ArrayList;
import java.util.List;

public class StockKeeper {

    private String employeeId;
    private String name;
    private String role;

    public StockKeeper(String employeeId, String name, String role) {
        this.employeeId = employeeId;
        this.name = name;
        this.role = role;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<StockItem> viewLowStockItems(List<StockItem> items) {
        List<StockItem> lowStockItems = new ArrayList<>();

        for (StockItem item : items) {
            if (item.isLowStock()) {
                lowStockItems.add(item);
            }
        }

        return lowStockItems;
    }

    public String generateReport(List<StockItem> items) {
        StringBuilder report = new StringBuilder();

        report.append("LOW STOCK REPORT\n");
        report.append("-----------------------------\n");

        for (StockItem item : viewLowStockItems(items)) {
            report.append(item.getItemName())
                    .append(" | ")
                    .append(item.getCategory())
                    .append(" | Qty: ")
                    .append(item.getQuantity())
                    .append(" | Reorder Level: ")
                    .append(item.getReorderLevel())
                    .append("\n");
        }

        return report.toString();
    }
}