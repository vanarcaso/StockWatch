package stockwatch.model;

import java.time.LocalDateTime;

public class StockTransaction {

    private int transactionId;
    private String transactionType;
    private String itemName;
    private int quantity;
    private String staffName;
    private String issuedTo;
    private LocalDateTime dateLogged;

    public StockTransaction(
            int transactionId,
            String transactionType,
            String itemName,
            int quantity,
            String staffName,
            String issuedTo
    ) {
        this.transactionId = transactionId;
        this.transactionType = transactionType;
        this.itemName = itemName;
        this.quantity = quantity;
        this.staffName = staffName;
        this.issuedTo = issuedTo;
        this.dateLogged = LocalDateTime.now();
    }

    public void stockIn(StockItem item, int amount) {
        if (amount > 0) {
            item.updateQuantity(amount);
        }
    }

    public boolean stockOut(StockItem item, int amount) {
        if (amount <= 0 || amount > item.getQuantity()) {
            return false;
        }

        item.updateQuantity(-amount);
        return true;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getStaffName() {
        return staffName;
    }

    public String getIssuedTo() {
        return issuedTo;
    }

    public LocalDateTime getDateLogged() {
        return dateLogged;
    }
}