package stockwatch.gui;

import stockwatch.model.InventoryManager;
import stockwatch.model.StockItem;
import stockwatch.model.StockTransaction;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

public class DashboardPanel extends JPanel {

    private final InventoryManager inventoryManager;

    private final JLabel totalItemsValue;
    private final JLabel totalQuantityValue;
    private final JLabel lowStockValue;
    private final JLabel transactionsValue;

    private final JLabel inventoryHealthLabel;
    private final JLabel recentActivityLabel;

    public DashboardPanel(
            InventoryManager inventoryManager
    ) {
        this.inventoryManager = inventoryManager;

        setLayout(
                new BorderLayout(0, 22)
        );

        setBackground(
                AppTheme.BACKGROUND
        );

        setBorder(
                BorderFactory.createEmptyBorder(
                        18,
                        20,
                        20,
                        20
                )
        );

        totalItemsValue =
                createValueLabel(
                        AppTheme.PRIMARY_LIGHT
                );

        totalQuantityValue =
                createValueLabel(
                        AppTheme.CYAN
                );

        lowStockValue =
                createValueLabel(
                        AppTheme.WARNING
                );

        transactionsValue =
                createValueLabel(
                        AppTheme.SUCCESS
                );

        inventoryHealthLabel =
                createBodyLabel();

        recentActivityLabel =
                createBodyLabel();

        add(
                createHeader(),
                BorderLayout.NORTH
        );

        add(
                createDashboardContent(),
                BorderLayout.CENTER
        );

        refreshDashboard();
    }

    private JPanel createHeader() {
        JPanel header =
                new JPanel();

        header.setOpaque(false);

        header.setLayout(
                new BoxLayout(
                        header,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel title =
                new JLabel(
                        "Dashboard"
                );

        title.setForeground(
                AppTheme.TEXT
        );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        30
                )
        );

        JLabel subtitle =
                new JLabel(
                        "Overview of your inventory and stock activity."
                );

        subtitle.setForeground(
                AppTheme.MUTED_TEXT
        );

        subtitle.setFont(
                AppTheme.NORMAL_FONT
        );

        header.add(title);

        header.add(
                Box.createVerticalStrut(5)
        );

        header.add(subtitle);

        return header;
    }

    private JPanel createDashboardContent() {
        JPanel content =
                new JPanel(
                        new BorderLayout(
                                0,
                                18
                        )
                );

        content.setOpaque(false);

        JPanel statsPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                4,
                                15,
                                0
                        )
                );

        statsPanel.setOpaque(false);

        statsPanel.add(
                createStatCard(
                        "TOTAL ITEMS",
                        totalItemsValue,
                        "Unique inventory items",
                        AppTheme.PRIMARY_LIGHT
                )
        );

        statsPanel.add(
                createStatCard(
                        "TOTAL QUANTITY",
                        totalQuantityValue,
                        "Available stock units",
                        AppTheme.CYAN
                )
        );

        statsPanel.add(
                createStatCard(
                        "LOW STOCK",
                        lowStockValue,
                        "Items needing attention",
                        AppTheme.WARNING
                )
        );

        statsPanel.add(
                createStatCard(
                        "TRANSACTIONS",
                        transactionsValue,
                        "Recorded stock activity",
                        AppTheme.SUCCESS
                )
        );

        JPanel bottomPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                15,
                                0
                        )
                );

        bottomPanel.setOpaque(false);

        bottomPanel.add(
                createInfoCard(
                        "Inventory Health",
                        "Current stock condition",
                        inventoryHealthLabel,
                        AppTheme.WARNING
                )
        );

        bottomPanel.add(
                createInfoCard(
                        "Recent Activity",
                        "Latest inventory movement",
                        recentActivityLabel,
                        AppTheme.CYAN
                )
        );

        content.add(
                statsPanel,
                BorderLayout.NORTH
        );

        content.add(
                bottomPanel,
                BorderLayout.CENTER
        );

        return content;
    }

    private RoundedPanel createStatCard(
            String title,
            JLabel valueLabel,
            String description,
            Color accentColor
    ) {
        RoundedPanel card =
                new RoundedPanel(
                        22,
                        AppTheme.CARD
                );

        card.setLayout(
                new BorderLayout()
        );

        card.setBorder(
                BorderFactory.createEmptyBorder(
                        18,
                        18,
                        18,
                        18
                )
        );

        JPanel accent =
                new JPanel();

        accent.setBackground(
                accentColor
        );

        accent.setPreferredSize(
                new Dimension(
                        5,
                        0
                )
        );

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setForeground(
                AppTheme.MUTED_TEXT
        );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        12
                )
        );

        JLabel descriptionLabel =
                new JLabel(
                        description,
                        SwingConstants.CENTER
                );

        descriptionLabel.setForeground(
                AppTheme.MUTED_TEXT
        );

        descriptionLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        11
                )
        );

        card.add(
                accent,
                BorderLayout.WEST
        );

        card.add(
                titleLabel,
                BorderLayout.NORTH
        );

        card.add(
                valueLabel,
                BorderLayout.CENTER
        );

        card.add(
                descriptionLabel,
                BorderLayout.SOUTH
        );

        return card;
    }

    private RoundedPanel createInfoCard(
            String title,
            String subtitle,
            JLabel contentLabel,
            Color accentColor
    ) {
        RoundedPanel card =
                new RoundedPanel(
                        22,
                        AppTheme.CARD
                );

        card.setLayout(
                new BorderLayout(
                        0,
                        15
                )
        );

        card.setBorder(
                BorderFactory.createEmptyBorder(
                        22,
                        22,
                        22,
                        22
                )
        );

        JPanel header =
                new JPanel();

        header.setOpaque(false);

        header.setLayout(
                new BoxLayout(
                        header,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setForeground(
                AppTheme.TEXT
        );

        titleLabel.setFont(
                AppTheme.SECTION_FONT
        );

        JLabel subtitleLabel =
                new JLabel(subtitle);

        subtitleLabel.setForeground(
                AppTheme.MUTED_TEXT
        );

        subtitleLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        header.add(titleLabel);

        header.add(
                Box.createVerticalStrut(4)
        );

        header.add(subtitleLabel);

        JPanel accent =
                new JPanel();

        accent.setBackground(
                accentColor
        );

        accent.setPreferredSize(
                new Dimension(
                        0,
                        4
                )
        );

        JPanel topSection =
                new JPanel(
                        new BorderLayout(
                                0,
                                12
                        )
                );

        topSection.setOpaque(false);

        topSection.add(
                header,
                BorderLayout.CENTER
        );

        topSection.add(
                accent,
                BorderLayout.SOUTH
        );

        card.add(
                topSection,
                BorderLayout.NORTH
        );

        card.add(
                contentLabel,
                BorderLayout.CENTER
        );

        return card;
    }

    private JLabel createValueLabel(
            Color color
    ) {
        JLabel label =
                new JLabel(
                        "0",
                        SwingConstants.CENTER
                );

        label.setForeground(
                color
        );

        label.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        38
                )
        );

        return label;
    }

    private JLabel createBodyLabel() {
        JLabel label =
                new JLabel();

        label.setForeground(
                AppTheme.TEXT
        );

        label.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        16
                )
        );

        label.setVerticalAlignment(
                SwingConstants.TOP
        );

        return label;
    }

    public void refreshDashboard() {
        int totalItems =
                inventoryManager
                        .getAllItems()
                        .size();

        int totalQuantity = 0;

        for (
                StockItem item :
                inventoryManager
                        .getAllItems()
        ) {
            totalQuantity +=
                    item.getQuantity();
        }

        int lowStockItems =
                inventoryManager
                        .getLowStockItems()
                        .size();

        int totalTransactions =
                inventoryManager
                        .getTransactions()
                        .size();

        totalItemsValue.setText(
                String.valueOf(
                        totalItems
                )
        );

        totalQuantityValue.setText(
                String.valueOf(
                        totalQuantity
                )
        );

        lowStockValue.setText(
                String.valueOf(
                        lowStockItems
                )
        );

        transactionsValue.setText(
                String.valueOf(
                        totalTransactions
                )
        );

        updateInventoryHealth(
                lowStockItems
        );

        updateRecentActivity();
    }

    private void updateInventoryHealth(
            int lowStockItems
    ) {
        if (
                inventoryManager
                        .getAllItems()
                        .isEmpty()
        ) {
            inventoryHealthLabel.setText(
                    "<html>No inventory items yet.<br>"
                            + "Add your first item to begin tracking stock.</html>"
            );

        } else if (
                lowStockItems == 0
        ) {
            inventoryHealthLabel.setText(
                    "<html>All inventory items are currently above "
                            + "their reorder levels.<br><br>"
                            + "<font color='#22C55E'>Stock levels look good.</font>"
                            + "</html>"
            );

        } else {
            inventoryHealthLabel.setText(
                    "<html>"
                            + lowStockItems
                            + " item(s) currently need attention.<br><br>"
                            + "<font color='#F59E0B'>Check the Low Stock page.</font>"
                            + "</html>"
            );
        }
    }

    private void updateRecentActivity() {
        if (
                inventoryManager
                        .getTransactions()
                        .isEmpty()
        ) {
            recentActivityLabel.setText(
                    "<html>No stock transactions recorded yet.<br>"
                            + "Stock In and Stock Out activity will appear here.</html>"
            );

            return;
        }

        int lastIndex =
                inventoryManager
                        .getTransactions()
                        .size()
                        - 1;

        StockTransaction latest =
                inventoryManager
                        .getTransactions()
                        .get(lastIndex);

        recentActivityLabel.setText(
                "<html>"
                        + latest.getTransactionType()
                        + "<br><br>"
                        + "<font color='#94A3B8'>Item:</font> "
                        + latest.getItemName()
                        + "<br>"
                        + "<font color='#94A3B8'>Quantity:</font> "
                        + latest.getQuantity()
                        + "<br>"
                        + "<font color='#94A3B8'>Staff:</font> "
                        + latest.getStaffName()
                        + "</html>"
        );
    }
}