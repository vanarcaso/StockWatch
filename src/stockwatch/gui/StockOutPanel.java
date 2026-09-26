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

public class StockOutPanel extends JPanel {

    private final InventoryManager inventoryManager;

    private final JComboBox<String> itemComboBox;
    private final JSpinner quantitySpinner;
    private final JTextField staffNameField;
    private final JTextField issuedToField;

    private final JLabel selectedItemLabel;
    private final JLabel currentQuantityLabel;
    private final JLabel afterQuantityLabel;

    public StockOutPanel(
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
        issuedToField = new JTextField();

        selectedItemLabel =
                createStatValue(
                        AppTheme.PRIMARY_LIGHT
                );

        currentQuantityLabel =
                createStatValue(
                        AppTheme.CYAN
                );

        afterQuantityLabel =
                createStatValue(
                        AppTheme.WARNING
                );

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
                new JPanel(
                        new GridLayout(2, 1)
                );

        header.setOpaque(false);

        JLabel title =
                new JLabel("Stock Out");

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
                        "Record issued supplies and update outgoing stock."
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

        JPanel formWrapper =
                new JPanel(
                        new BorderLayout()
                );

        formWrapper.setOpaque(false);

        formWrapper.add(
                createFormCard(),
                BorderLayout.NORTH
        );

        content.add(
                formWrapper,
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
                        "Item being issued",
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
                        "AFTER STOCK OUT",
                        afterQuantityLabel,
                        "Expected remaining stock",
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
                        "Stock Out Details"
                );

        title.setForeground(
                AppTheme.TEXT
        );

        title.setFont(
                AppTheme.SECTION_FONT
        );

        JLabel subtitle =
                new JLabel(
                        "Choose an item and enter the quantity to issue."
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
                                4, 2, 16, 14
                        )
                );

        form.setOpaque(false);

        form.add(
                createLabel("Item")
        );

        form.add(itemComboBox);

        form.add(
                createLabel(
                        "Quantity to Remove"
                )
        );

        form.add(quantitySpinner);

        form.add(
                createLabel(
                        "Staff Name"
                )
        );

        form.add(staffNameField);

        form.add(
                createLabel(
                        "Issued To"
                )
        );

        form.add(issuedToField);

        JButton stockOutButton =
                new JButton(
                        "- Record Stock Out"
                );

        stockOutButton.setBackground(
                AppTheme.PRIMARY
        );

        stockOutButton.setForeground(
                Color.WHITE
        );

        stockOutButton.setFont(
                AppTheme.BOLD_FONT
        );

        stockOutButton.setFocusPainted(false);
        stockOutButton.setBorderPainted(false);
        stockOutButton.setOpaque(true);

        stockOutButton.setPreferredSize(
                new Dimension(
                        190, 40
                )
        );

        stockOutButton.addActionListener(
                e -> processStockOut()
        );

        JPanel buttonPanel =
                new JPanel(
                        new BorderLayout()
                );

        buttonPanel.setOpaque(false);

        buttonPanel.add(
                stockOutButton,
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

        styleTextField(
                staffNameField
        );

        styleTextField(
                issuedToField
        );

        JSpinner.DefaultEditor editor =
                (JSpinner.DefaultEditor)
                        quantitySpinner
                                .getEditor();

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

    private void styleTextField(
            JTextField field
    ) {
        field.setBackground(
                AppTheme.CARD_HOVER
        );

        field.setForeground(
                AppTheme.TEXT
        );

        field.setCaretColor(
                AppTheme.TEXT
        );

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                AppTheme.BORDER
                        ),
                        BorderFactory.createEmptyBorder(
                                5, 8, 5, 8
                        )
                )
        );
    }

    public void refreshItems() {
        itemComboBox.removeAllItems();

        for (
                StockItem item :
                inventoryManager
                        .getAllItems()
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

            afterQuantityLabel.setForeground(
                    AppTheme.WARNING
            );

            return;
        }

        StockItem item =
                inventoryManager
                        .findItem(itemName);

        if (item == null) {
            return;
        }

        int quantityToRemove =
                (Integer)
                        quantitySpinner
                                .getValue();

        int remaining =
                item.getQuantity()
                        - quantityToRemove;

        selectedItemLabel.setText(
                item.getItemName()
        );

        currentQuantityLabel.setText(
                String.valueOf(
                        item.getQuantity()
                )
        );

        if (remaining < 0) {
            afterQuantityLabel.setText(
                    "INSUFFICIENT"
            );

            afterQuantityLabel.setForeground(
                    AppTheme.DANGER
            );

        } else {
            afterQuantityLabel.setText(
                    String.valueOf(
                            remaining
                    )
            );

            if (
                    remaining
                            <= item.getReorderLevel()
            ) {
                afterQuantityLabel.setForeground(
                        AppTheme.WARNING
                );

            } else {
                afterQuantityLabel.setForeground(
                        AppTheme.SUCCESS
                );
            }
        }
    }

    private void processStockOut() {
        String itemName =
                (String)
                        itemComboBox
                                .getSelectedItem();

        String staffName =
                staffNameField
                        .getText()
                        .trim();

        String issuedTo =
                issuedToField
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

        if (issuedTo.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter who will receive the item.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        StockItem item =
                inventoryManager
                        .findItem(itemName);

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
                        "STOCK OUT",
                        item.getItemName(),
                        quantity,
                        staffName,
                        issuedTo
                );

        boolean successful =
                transaction.stockOut(
                        item,
                        quantity
                );

        if (!successful) {
            JOptionPane.showMessageDialog(
                    this,
                    "Not enough stock available.",
                    "Stock Out Failed",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        inventoryManager.addTransaction(
                transaction
        );

        quantitySpinner.setValue(1);
        staffNameField.setText("");
        issuedToField.setText("");

        updatePreview();

        JOptionPane.showMessageDialog(
                this,
                "Stock removed successfully.",
                "StockWatch",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}