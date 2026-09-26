package stockwatch.gui;

import stockwatch.model.InventoryManager;
import stockwatch.model.StockItem;
import stockwatch.model.StockTransaction;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

public class StockInPanel extends JPanel {

    private final InventoryManager inventoryManager;

    private final JComboBox<String> itemComboBox;
    private final JSpinner quantitySpinner;
    private final JTextField staffNameField;

    private final JLabel selectedItemLabel;
    private final JLabel currentQuantityLabel;
    private final JLabel afterQuantityLabel;

    public StockInPanel(
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

        itemComboBox = new JComboBox<>();

        quantitySpinner = new JSpinner(
                new SpinnerNumberModel(
                        1, 1, 10000, 1
                )
        );

        staffNameField = new JTextField();

        selectedItemLabel =
                createStatValue(AppTheme.PRIMARY_LIGHT);

        currentQuantityLabel =
                createStatValue(AppTheme.CYAN);

        afterQuantityLabel =
                createStatValue(AppTheme.SUCCESS);

        styleInputs();

        add(
                createHeader(),
                BorderLayout.NORTH
        );

        add(
                createContent(),
                BorderLayout.CENTER
        );

        itemComboBox.addActionListener(
                e -> updatePreview()
        );

        quantitySpinner.addChangeListener(
                e -> updatePreview()
        );

        refreshItems();
    }

    private JPanel createHeader() {
        JPanel header =
                new JPanel(new GridLayout(2, 1));

        header.setOpaque(false);

        JLabel title =
                new JLabel("Stock In");

        title.setForeground(AppTheme.TEXT);

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        30
                )
        );

        JLabel subtitle =
                new JLabel(
                        "Record incoming supplies and update inventory quantities."
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
                createFormCard(),
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
                        "SELECTED ITEM",
                        selectedItemLabel,
                        "Item receiving stock",
                        AppTheme.PRIMARY_LIGHT
                )
        );

        stats.add(
                createStatCard(
                        "CURRENT QUANTITY",
                        currentQuantityLabel,
                        "Available stock now",
                        AppTheme.CYAN
                )
        );

        stats.add(
                createStatCard(
                        "AFTER STOCK IN",
                        afterQuantityLabel,
                        "Expected new quantity",
                        AppTheme.SUCCESS
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

        JPanel accentBar = new JPanel();

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

    private RoundedPanel createFormCard() {
        RoundedPanel card =
                new RoundedPanel(
                        22,
                        AppTheme.CARD
                );

        card.setLayout(
                new BorderLayout(
                        0, 18
                )
        );

        card.setBorder(
                BorderFactory.createEmptyBorder(
                        22, 22, 22, 22
                )
        );

        JPanel heading =
                new JPanel(
                        new GridLayout(2, 1)
                );

        heading.setOpaque(false);

        JLabel title =
                new JLabel(
                        "Stock In Details"
                );

        title.setForeground(
                AppTheme.TEXT
        );

        title.setFont(
                AppTheme.SECTION_FONT
        );

        JLabel subtitle =
                new JLabel(
                        "Choose an item and enter the quantity received."
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

        JPanel form =
                new JPanel(
                        new GridLayout(
                                3, 2, 16, 16
                        )
                );

        form.setOpaque(false);

        form.add(
                createLabel("Item")
        );

        form.add(itemComboBox);

        form.add(
                createLabel(
                        "Quantity to Add"
                )
        );

        form.add(quantitySpinner);

        form.add(
                createLabel(
                        "Staff Name"
                )
        );

        form.add(staffNameField);

        JButton stockInButton =
                new JButton(
                        "+ Record Stock In"
                );

        stockInButton.setBackground(
                AppTheme.PRIMARY
        );

        stockInButton.setForeground(
                Color.WHITE
        );

        stockInButton.setFont(
                AppTheme.BOLD_FONT
        );

        stockInButton.setFocusPainted(false);
        stockInButton.setBorderPainted(false);
        stockInButton.setOpaque(true);

        stockInButton.setPreferredSize(
                new Dimension(
                        190, 40
                )
        );

        stockInButton.addActionListener(
                e -> processStockIn()
        );

        JPanel buttonPanel =
                new JPanel(
                        new BorderLayout()
                );

        buttonPanel.setOpaque(false);

        buttonPanel.add(
                stockInButton,
                BorderLayout.EAST
        );

        card.add(
                heading,
                BorderLayout.NORTH
        );

        card.add(
                form,
                BorderLayout.CENTER
        );

        card.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        return card;
    }

    private JLabel createLabel(
            String text
    ) {
        JLabel label =
                new JLabel(text);

        label.setForeground(
                AppTheme.MUTED_TEXT
        );

        label.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        12
                )
        );

        return label;
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
                        27
                )
        );

        return label;
    }

    private void styleInputs() {
        itemComboBox.setBackground(
                AppTheme.CARD_HOVER
        );

        itemComboBox.setForeground(
                AppTheme.TEXT
        );

        itemComboBox.setPreferredSize(
                new Dimension(
                        300, 36
                )
        );

        staffNameField.setBackground(
                AppTheme.CARD_HOVER
        );

        staffNameField.setForeground(
                AppTheme.TEXT
        );

        staffNameField.setCaretColor(
                AppTheme.TEXT
        );

        staffNameField.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                AppTheme.BORDER
                        ),
                        BorderFactory.createEmptyBorder(
                                5, 8, 5, 8
                        )
                )
        );

        JSpinner.DefaultEditor editor =
                (JSpinner.DefaultEditor)
                        quantitySpinner.getEditor();

        editor.getTextField()
                .setBackground(
                        AppTheme.CARD_HOVER
                );

        editor.getTextField()
                .setForeground(
                        AppTheme.TEXT
                );

        editor.getTextField()
                .setCaretColor(
                        AppTheme.TEXT
                );
    }

    public void refreshItems() {
        itemComboBox.removeAllItems();

        for (
                StockItem item :
                inventoryManager.getAllItems()
        ) {
            itemComboBox.addItem(
                    item.getItemName()
            );
        }

        updatePreview();
    }

    private void updatePreview() {
        String itemName =
                (String)
                        itemComboBox
                                .getSelectedItem();

        if (itemName == null) {
            selectedItemLabel.setText("-");
            currentQuantityLabel.setText("0");
            afterQuantityLabel.setText("0");

            return;
        }

        StockItem item =
                inventoryManager.findItem(
                        itemName
                );

        if (item == null) {
            return;
        }

        int quantityToAdd =
                (Integer)
                        quantitySpinner
                                .getValue();

        selectedItemLabel.setText(
                item.getItemName()
        );

        currentQuantityLabel.setText(
                String.valueOf(
                        item.getQuantity()
                )
        );

        afterQuantityLabel.setText(
                String.valueOf(
                        item.getQuantity()
                                + quantityToAdd
                )
        );
    }

    private void processStockIn() {
        String itemName =
                (String)
                        itemComboBox
                                .getSelectedItem();

        String staffName =
                staffNameField
                        .getText()
                        .trim();

        int quantity =
                (Integer)
                        quantitySpinner
                                .getValue();

        if (itemName == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "There are no inventory items available.",
                    "No Item",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (staffName.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter the staff name.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        StockItem item =
                inventoryManager.findItem(
                        itemName
                );

        if (item == null) {
            return;
        }

        int transactionId =
                inventoryManager
                        .getTransactions()
                        .size()
                        + 1;

        StockTransaction transaction =
                new StockTransaction(
                        transactionId,
                        "STOCK IN",
                        item.getItemName(),
                        quantity,
                        staffName,
                        "N/A"
                );

        transaction.stockIn(
                item,
                quantity
        );

        inventoryManager.addTransaction(
                transaction
        );

        quantitySpinner.setValue(1);
        staffNameField.setText("");

        updatePreview();

        JOptionPane.showMessageDialog(
                this,
                "Stock added successfully.",
                "StockWatch",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}