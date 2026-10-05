package stockwatch.gui;

import stockwatch.model.InventoryManager;
import stockwatch.model.StockItem;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

public class LowStockPanel extends JPanel {

    private final InventoryManager inventoryManager;
    private final DefaultTableModel tableModel;
    private final JTable lowStockTable;
    private final JLabel lowStockCountValue;
    private final JLabel remainingUnitsValue;
    private final JLabel statusValue;

    public LowStockPanel(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;

        setLayout(new BorderLayout(0, 18));
        setBackground(AppTheme.BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(18, 20, 20, 20));

        lowStockCountValue = createStatValue(AppTheme.WARNING);
        remainingUnitsValue = createStatValue(AppTheme.CYAN);
        statusValue = createStatValue(AppTheme.SUCCESS);

        tableModel = new DefaultTableModel(
                new String[]{"Item Name", "Category", "Type", "Quantity", "Reorder Level", "Status"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        lowStockTable = new JTable(tableModel);
        styleTable();

        add(createHeader(), BorderLayout.NORTH);
        add(createContent(), BorderLayout.CENTER);

        refreshTable();
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setOpaque(false);

        JLabel title = new JLabel("Low Stock");
        title.setForeground(AppTheme.TEXT);
        title.setFont(new Font("Arial", Font.BOLD, 30));

        JLabel subtitle = new JLabel("Monitor supplies that have reached their reorder level.");
        subtitle.setForeground(AppTheme.MUTED_TEXT);
        subtitle.setFont(AppTheme.NORMAL_FONT);

        textPanel.add(title);
        textPanel.add(subtitle);

        JButton reportButton = new JButton("Generate Report");
        reportButton.setBackground(AppTheme.PRIMARY);
        reportButton.setForeground(Color.WHITE);
        reportButton.setFont(AppTheme.BOLD_FONT);
        reportButton.setFocusPainted(false);
        reportButton.setBorderPainted(false);
        reportButton.setOpaque(true);
        reportButton.setPreferredSize(new Dimension(150, 38));
        reportButton.addActionListener(e -> generateReport());

        header.add(textPanel, BorderLayout.WEST);
        header.add(reportButton, BorderLayout.EAST);
        return header;
    }

    private JPanel createContent() {
        JPanel content = new JPanel(new BorderLayout(0, 16));
        content.setOpaque(false);
        content.add(createStatsPanel(), BorderLayout.NORTH);
        content.add(createTableCard(), BorderLayout.CENTER);
        return content;
    }

    private JPanel createStatsPanel() {
        JPanel stats = new JPanel(new GridLayout(1, 3, 14, 0));
        stats.setOpaque(false);
        stats.add(createStatCard("LOW STOCK ITEMS", lowStockCountValue, "Items needing attention", AppTheme.WARNING));
        stats.add(createStatCard("REMAINING UNITS", remainingUnitsValue, "Units left in low-stock items", AppTheme.CYAN));
        stats.add(createStatCard("INVENTORY STATUS", statusValue, "Current low-stock condition", AppTheme.SUCCESS));
        return stats;
    }

    private RoundedPanel createStatCard(String title, JLabel value, String description, Color accent) {
        RoundedPanel card = new RoundedPanel(20, AppTheme.CARD);
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(14, 16, 14, 16));

        JPanel accentBar = new JPanel();
        accentBar.setBackground(accent);
        accentBar.setPreferredSize(new Dimension(4, 0));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(AppTheme.MUTED_TEXT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 11));

        JLabel descriptionLabel = new JLabel(description, SwingConstants.CENTER);
        descriptionLabel.setForeground(AppTheme.MUTED_TEXT);
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 10));

        card.add(accentBar, BorderLayout.WEST);
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(value, BorderLayout.CENTER);
        card.add(descriptionLabel, BorderLayout.SOUTH);
        return card;
    }

    private RoundedPanel createTableCard() {
        RoundedPanel card = new RoundedPanel(22, AppTheme.CARD);
        card.setLayout(new BorderLayout(0, 14));
        card.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        JPanel heading = new JPanel(new GridLayout(2, 1));
        heading.setOpaque(false);

        JLabel title = new JLabel("Low Stock Items");
        title.setForeground(AppTheme.TEXT);
        title.setFont(AppTheme.SECTION_FONT);

        JLabel subtitle = new JLabel("Items at or below their reorder level.");
        subtitle.setForeground(AppTheme.MUTED_TEXT);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 11));

        heading.add(title);
        heading.add(subtitle);

        JScrollPane scrollPane = new JScrollPane(lowStockTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(AppTheme.BORDER));
        scrollPane.getViewport().setBackground(AppTheme.CARD);

        card.add(heading, BorderLayout.NORTH);
        card.add(scrollPane, BorderLayout.CENTER);
        return card;
    }

    private JLabel createStatValue(Color color) {
        JLabel label = new JLabel("0", SwingConstants.CENTER);
        label.setForeground(color);
        label.setFont(new Font("Arial", Font.BOLD, 28));
        return label;
    }

    private void styleTable() {
        lowStockTable.setBackground(AppTheme.CARD);
        lowStockTable.setForeground(AppTheme.TEXT);
        lowStockTable.setGridColor(AppTheme.BORDER);
        lowStockTable.setRowHeight(34);
        lowStockTable.setSelectionBackground(AppTheme.PRIMARY);
        lowStockTable.setSelectionForeground(Color.WHITE);
        lowStockTable.setShowVerticalLines(false);
        lowStockTable.setFont(new Font("Arial", Font.PLAIN, 13));

        lowStockTable.getTableHeader().setBackground(AppTheme.CARD_HOVER);
        lowStockTable.getTableHeader().setForeground(AppTheme.TEXT);
        lowStockTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        lowStockTable.getTableHeader().setPreferredSize(new Dimension(0, 36));
        lowStockTable.getTableHeader().setReorderingAllowed(false);
        lowStockTable.getColumnModel().getColumn(5).setCellRenderer(createStatusRenderer());
    }

    private DefaultTableCellRenderer createStatusRenderer() {
        return new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column
            ) {
                Component component = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column
                );

                setHorizontalAlignment(SwingConstants.CENTER);

                if (!isSelected) {
                    component.setBackground(AppTheme.CARD);
                    component.setForeground(AppTheme.WARNING);
                }

                return component;
            }
        };
    }

    public void refreshTable() {
        tableModel.setRowCount(0);
        int remainingUnits = 0;

        for (StockItem item : inventoryManager.getLowStockItems()) {
            remainingUnits += item.getQuantity();
            tableModel.addRow(new Object[]{
                    item.getItemName(),
                    item.getCategory(),
                    item.getType(),
                    item.getQuantity(),
                    item.getReorderLevel(),
                    item.checkStockLevel()
            });
        }

        int lowStockCount = inventoryManager.getLowStockItems().size();
        lowStockCountValue.setText(String.valueOf(lowStockCount));
        remainingUnitsValue.setText(String.valueOf(remainingUnits));

        if (lowStockCount == 0) {
            statusValue.setText("GOOD");
            statusValue.setForeground(AppTheme.SUCCESS);
        } else {
            statusValue.setText("ATTENTION");
            statusValue.setForeground(AppTheme.WARNING);
        }
    }

    private void generateReport() {
        if (inventoryManager.getLowStockItems().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "There are no low-stock items.",
                    "Low Stock Report",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        StringBuilder report = new StringBuilder();
        report.append("STOCKWATCH LOW STOCK REPORT\n");
        report.append("========================================\n");

        for (StockItem item : inventoryManager.getLowStockItems()) {
            report.append("Item: ").append(item.getItemName()).append("\n");
            report.append("Category: ").append(item.getCategory()).append("\n");
            report.append("Type: ").append(item.getType()).append("\n");
            report.append("Quantity: ").append(item.getQuantity()).append("\n");
            report.append("Reorder Level: ").append(item.getReorderLevel()).append("\n");
            report.append("Status: ").append(item.checkStockLevel()).append("\n");
            report.append("----------------------------------------\n");
        }

        JTextArea reportArea = new JTextArea(report.toString(), 18, 40);
        reportArea.setEditable(false);
        reportArea.setBackground(AppTheme.CARD);
        reportArea.setForeground(AppTheme.TEXT);
        reportArea.setCaretColor(AppTheme.TEXT);
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 13));

        JOptionPane.showMessageDialog(
                this,
                new JScrollPane(reportArea),
                "Low Stock Report",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
