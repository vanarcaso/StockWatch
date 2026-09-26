package stockwatch.gui;

import stockwatch.model.InventoryManager;
import stockwatch.model.StockTransaction;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.format.DateTimeFormatter;

public class TransactionsPanel extends JPanel {

    private final InventoryManager inventoryManager;

    private final DefaultTableModel tableModel;
    private final JTable transactionTable;

    private final JLabel totalValue;
    private final JLabel stockInValue;
    private final JLabel stockOutValue;

    private final DateTimeFormatter dateFormatter =
            DateTimeFormatter.ofPattern(
                    "yyyy-MM-dd HH:mm"
            );

    public TransactionsPanel(
            InventoryManager inventoryManager
    ) {
        this.inventoryManager = inventoryManager;

        setLayout(new BorderLayout(0, 18));
        setBackground(AppTheme.BACKGROUND);

        setBorder(
                BorderFactory.createEmptyBorder(
                        18, 20, 20, 20
                )
        );

        totalValue =
                createStatValue(
                        AppTheme.PRIMARY_LIGHT
                );

        stockInValue =
                createStatValue(
                        AppTheme.SUCCESS
                );

        stockOutValue =
                createStatValue(
                        AppTheme.WARNING
                );

        tableModel = new DefaultTableModel(
                new String[]{
                        "ID",
                        "Date",
                        "Type",
                        "Item",
                        "Quantity",
                        "Staff",
                        "Issued To"
                },
                0
        ) {
            @Override
            public boolean isCellEditable(
                    int row,
                    int column
            ) {
                return false;
            }
        };

        transactionTable =
                new JTable(tableModel);

        styleTable();

        add(
                createHeader(),
                BorderLayout.NORTH
        );

        add(
                createContent(),
                BorderLayout.CENTER
        );

        refreshTable();
    }

    private JPanel createHeader() {
        JPanel header =
                new JPanel(
                        new GridLayout(2, 1)
                );

        header.setOpaque(false);

        JLabel title =
                new JLabel("Transactions");

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
                        "Review all recorded stock movement and activity."
                );

        subtitle.setForeground(
                AppTheme.MUTED_TEXT
        );

        subtitle.setFont(
                AppTheme.NORMAL_FONT
        );

        header.add(title);
        header.add(subtitle);

        return header;
    }

    private JPanel createContent() {
        JPanel content =
                new JPanel(
                        new BorderLayout(
                                0, 16
                        )
                );

        content.setOpaque(false);

        content.add(
                createStatsPanel(),
                BorderLayout.NORTH
        );

        content.add(
                createTableCard(),
                BorderLayout.CENTER
        );

        return content;
    }

    private JPanel createStatsPanel() {
        JPanel stats =
                new JPanel(
                        new GridLayout(
                                1, 3, 14, 0
                        )
                );

        stats.setOpaque(false);

        stats.add(
                createStatCard(
                        "TOTAL TRANSACTIONS",
                        totalValue,
                        "All recorded activity",
                        AppTheme.PRIMARY_LIGHT
                )
        );

        stats.add(
                createStatCard(
                        "STOCK IN",
                        stockInValue,
                        "Incoming stock records",
                        AppTheme.SUCCESS
                )
        );

        stats.add(
                createStatCard(
                        "STOCK OUT",
                        stockOutValue,
                        "Outgoing stock records",
                        AppTheme.WARNING
                )
        );

        return stats;
    }

    private RoundedPanel createStatCard(
            String title,
            JLabel value,
            String description,
            Color accent
    ) {
        RoundedPanel card =
                new RoundedPanel(
                        20,
                        AppTheme.CARD
                );

        card.setLayout(
                new BorderLayout()
        );

        card.setBorder(
                BorderFactory.createEmptyBorder(
                        14, 16, 14, 16
                )
        );

        JPanel accentBar =
                new JPanel();

        accentBar.setBackground(accent);

        accentBar.setPreferredSize(
                new Dimension(4, 0)
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
                        11
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
                        10
                )
        );

        card.add(
                accentBar,
                BorderLayout.WEST
        );

        card.add(
                titleLabel,
                BorderLayout.NORTH
        );

        card.add(
                value,
                BorderLayout.CENTER
        );

        card.add(
                descriptionLabel,
                BorderLayout.SOUTH
        );

        return card;
    }

    private RoundedPanel createTableCard() {
        RoundedPanel card =
                new RoundedPanel(
                        22,
                        AppTheme.CARD
                );

        card.setLayout(
                new BorderLayout(
                        0, 14
                )
        );

        card.setBorder(
                BorderFactory.createEmptyBorder(
                        18, 18, 18, 18
                )
        );

        JPanel heading =
                new JPanel(
                        new GridLayout(2, 1)
                );

        heading.setOpaque(false);

        JLabel title =
                new JLabel(
                        "Transaction History"
                );

        title.setForeground(
                AppTheme.TEXT
        );

        title.setFont(
                AppTheme.SECTION_FONT
        );

        JLabel subtitle =
                new JLabel(
                        "Complete record of Stock In and Stock Out transactions."
                );

        subtitle.setForeground(
                AppTheme.MUTED_TEXT
        );

        subtitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        11
                )
        );

        heading.add(title);
        heading.add(subtitle);

        JScrollPane scrollPane =
                new JScrollPane(
                        transactionTable
                );

        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        AppTheme.BORDER
                )
        );

        scrollPane
                .getViewport()
                .setBackground(
                        AppTheme.CARD
                );

        card.add(
                heading,
                BorderLayout.NORTH
        );

        card.add(
                scrollPane,
                BorderLayout.CENTER
        );

        return card;
    }

    private JLabel createStatValue(
            Color color
    ) {
        JLabel label =
                new JLabel(
                        "0",
                        SwingConstants.CENTER
                );

        label.setForeground(color);

        label.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        return label;
    }

    private void styleTable() {
        transactionTable.setBackground(
                AppTheme.CARD
        );

        transactionTable.setForeground(
                AppTheme.TEXT
        );

        transactionTable.setGridColor(
                AppTheme.BORDER
        );

        transactionTable.setRowHeight(34);

        transactionTable.setSelectionBackground(
                AppTheme.PRIMARY
        );

        transactionTable.setSelectionForeground(
                Color.WHITE
        );

        transactionTable.setShowVerticalLines(false);

        transactionTable.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        transactionTable
                .getTableHeader()
                .setBackground(
                        AppTheme.CARD_HOVER
                );

        transactionTable
                .getTableHeader()
                .setForeground(
                        AppTheme.TEXT
                );

        transactionTable
                .getTableHeader()
                .setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                12
                        )
                );

        transactionTable
                .getTableHeader()
                .setPreferredSize(
                        new Dimension(
                                0, 36
                        )
                );

        transactionTable
                .getTableHeader()
                .setReorderingAllowed(false);
    }

    public void refreshTable() {
        tableModel.setRowCount(0);

        int stockInCount = 0;
        int stockOutCount = 0;

        for (
                StockTransaction transaction :
                inventoryManager
                        .getTransactions()
        ) {

            if (
                    "STOCK IN".equals(
                            transaction
                                    .getTransactionType()
                    )
            ) {
                stockInCount++;

            } else if (
                    "STOCK OUT".equals(
                            transaction
                                    .getTransactionType()
                    )
            ) {
                stockOutCount++;
            }

            tableModel.addRow(
                    new Object[]{
                            transaction
                                    .getTransactionId(),

                            transaction
                                    .getDateLogged()
                                    .format(
                                    dateFormatter
                            ),

                            transaction
                                    .getTransactionType(),

                            transaction
                                    .getItemName(),

                            transaction
                                    .getQuantity(),

                            transaction
                                    .getStaffName(),

                            transaction
                                    .getIssuedTo()
                    }
            );
        }

        totalValue.setText(
                String.valueOf(
                        inventoryManager
                                .getTransactions()
                                .size()
                )
        );

        stockInValue.setText(
                String.valueOf(
                        stockInCount
                )
        );

        stockOutValue.setText(
                String.valueOf(
                        stockOutCount
                )
        );
    }
}