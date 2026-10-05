package stockwatch.gui;

import stockwatch.model.CleaningSupply;
import stockwatch.model.Electronics;
import stockwatch.model.InventoryManager;
import stockwatch.model.OfficeSupply;
import stockwatch.model.StockItem;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

public class InventoryPanel extends JPanel {

    private final InventoryManager inventoryManager;

    private final JTextField itemNameField;
    private final JComboBox<String> categoryComboBox;
    private final JComboBox<String> typeComboBox;
    private final JSpinner quantitySpinner;
    private final JSpinner reorderSpinner;

    private final JTextField searchField;
    private final JComboBox<String> filterComboBox;

    private final DefaultTableModel tableModel;
    private final JTable inventoryTable;

    private final JLabel totalItemsValue;
    private final JLabel inStockValue;
    private final JLabel lowStockValue;
    private final JLabel visibleItemsLabel;

    public InventoryPanel(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;

        setLayout(new BorderLayout(0, 18));
        setBackground(AppTheme.BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(18, 20, 20, 20));

        itemNameField = createTextField();
        categoryComboBox = createComboBox(new String[]{
                "Electronics", "Office Supply", "Cleaning Supply"
        });
        typeComboBox = createComboBox(new String[]{});
        quantitySpinner = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));
        reorderSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));

        searchField = createTextField();
        filterComboBox = createComboBox(new String[]{
                "All Categories", "Electronics", "Office Supply", "Cleaning Supply"
        });

        totalItemsValue = createStatValue(AppTheme.PRIMARY_LIGHT);
        inStockValue = createStatValue(AppTheme.SUCCESS);
        lowStockValue = createStatValue(AppTheme.WARNING);
        visibleItemsLabel = new JLabel("0 items");

        tableModel = new DefaultTableModel(
                new String[]{"Item Name", "Category", "Type", "Quantity", "Reorder Level", "Status"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        inventoryTable = new JTable(tableModel);
        styleSpinner(quantitySpinner);
        styleSpinner(reorderSpinner);
        styleTable();

        categoryComboBox.addActionListener(e -> updateTypeOptions());

        add(createHeader(), BorderLayout.NORTH);
        add(createPageContent(), BorderLayout.CENTER);

        updateTypeOptions();
        refreshTable();
    }

    private JPanel createHeader() {
        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Inventory");
        title.setForeground(AppTheme.TEXT);
        title.setFont(new Font("Arial", Font.BOLD, 30));

        JLabel subtitle = new JLabel("Manage all school supply stock in one place.");
        subtitle.setForeground(AppTheme.MUTED_TEXT);
        subtitle.setFont(AppTheme.NORMAL_FONT);

        header.add(title);
        header.add(Box.createVerticalStrut(5));
        header.add(subtitle);
        return header;
    }

    private JPanel createPageContent() {
        JPanel page = new JPanel(new BorderLayout(0, 16));
        page.setOpaque(false);
        page.add(createStatsPanel(), BorderLayout.NORTH);

        JPanel body = new JPanel(new BorderLayout(16, 0));
        body.setOpaque(false);

        RoundedPanel addItemCard = createAddItemCard();
        addItemCard.setPreferredSize(new Dimension(300, 0));

        body.add(addItemCard, BorderLayout.WEST);
        body.add(createInventoryCard(), BorderLayout.CENTER);
        page.add(body, BorderLayout.CENTER);
        return page;
    }

    private JPanel createStatsPanel() {
        JPanel stats = new JPanel(new GridLayout(1, 3, 14, 0));
        stats.setOpaque(false);
        stats.add(createStatCard("TOTAL ITEMS", totalItemsValue, "Unique inventory records", AppTheme.PRIMARY_LIGHT));
        stats.add(createStatCard("IN STOCK", inStockValue, "Items above reorder level", AppTheme.SUCCESS));
        stats.add(createStatCard("LOW STOCK", lowStockValue, "Items needing attention", AppTheme.WARNING));
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

    private RoundedPanel createAddItemCard() {
        RoundedPanel card = new RoundedPanel(22, AppTheme.CARD);
        card.setLayout(new BorderLayout(0, 18));
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel heading = new JPanel();
        heading.setOpaque(false);
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Add New Item");
        title.setForeground(AppTheme.TEXT);
        title.setFont(AppTheme.SECTION_FONT);

        JLabel description = new JLabel("Create a new inventory record.");
        description.setForeground(AppTheme.MUTED_TEXT);
        description.setFont(new Font("Arial", Font.PLAIN, 11));

        heading.add(title);
        heading.add(Box.createVerticalStrut(4));
        heading.add(description);

        JPanel form = new JPanel();
        form.setOpaque(false);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.add(createFieldGroup("Item Name", itemNameField));
        form.add(createFieldGroup("Category", categoryComboBox));
        form.add(createFieldGroup("Type", typeComboBox));
        form.add(createFieldGroup("Quantity", quantitySpinner));
        form.add(createFieldGroup("Reorder Level", reorderSpinner));

        JButton addButton = createPrimaryButton("+ Add Item");
        addButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        addButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        addButton.addActionListener(e -> addItem());

        form.add(Box.createVerticalStrut(6));
        form.add(addButton);

        card.add(heading, BorderLayout.NORTH);
        card.add(form, BorderLayout.CENTER);
        return card;
    }

    private JPanel createFieldGroup(String labelText, JComponent component) {
        JPanel panel = new JPanel(new BorderLayout(0, 6));
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 65));

        JLabel label = new JLabel(labelText);
        label.setForeground(AppTheme.MUTED_TEXT);
        label.setFont(new Font("Arial", Font.BOLD, 11));

        component.setPreferredSize(new Dimension(250, 34));
        panel.add(label, BorderLayout.NORTH);
        panel.add(component, BorderLayout.CENTER);
        return panel;
    }

    private RoundedPanel createInventoryCard() {
        RoundedPanel card = new RoundedPanel(22, AppTheme.CARD);
        card.setLayout(new BorderLayout(0, 15));
        card.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        JPanel heading = new JPanel(new BorderLayout());
        heading.setOpaque(false);

        JPanel headingText = new JPanel();
        headingText.setOpaque(false);
        headingText.setLayout(new BoxLayout(headingText, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Inventory Items");
        title.setForeground(AppTheme.TEXT);
        title.setFont(AppTheme.SECTION_FONT);

        JLabel subtitle = new JLabel("Search and review current stock.");
        subtitle.setForeground(AppTheme.MUTED_TEXT);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 11));

        headingText.add(title);
        headingText.add(Box.createVerticalStrut(4));
        headingText.add(subtitle);

        visibleItemsLabel.setForeground(AppTheme.CYAN);
        visibleItemsLabel.setFont(new Font("Arial", Font.BOLD, 12));

        heading.add(headingText, BorderLayout.WEST);
        heading.add(visibleItemsLabel, BorderLayout.EAST);

        JPanel center = new JPanel(new BorderLayout(0, 12));
        center.setOpaque(false);
        center.add(createSearchBar(), BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(inventoryTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(AppTheme.BORDER));
        scrollPane.getViewport().setBackground(AppTheme.CARD);
        center.add(scrollPane, BorderLayout.CENTER);

        card.add(heading, BorderLayout.NORTH);
        card.add(center, BorderLayout.CENTER);
        return card;
    }

    private JPanel createSearchBar() {
        JPanel searchBar = new JPanel(new GridLayout(1, 4, 8, 0));
        searchBar.setOpaque(false);

        JButton searchButton = createPrimaryButton("Search");
        JButton clearButton = createSecondaryButton("Reset");

        searchButton.addActionListener(e -> refreshTable());
        clearButton.addActionListener(e -> {
            searchField.setText("");
            filterComboBox.setSelectedIndex(0);
            refreshTable();
        });

        searchBar.add(searchField);
        searchBar.add(filterComboBox);
        searchBar.add(searchButton);
        searchBar.add(clearButton);
        return searchBar;
    }

    private JLabel createStatValue(Color color) {
        JLabel label = new JLabel("0", SwingConstants.CENTER);
        label.setForeground(color);
        label.setFont(new Font("Arial", Font.BOLD, 28));
        return label;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setBackground(AppTheme.CARD_HOVER);
        field.setForeground(AppTheme.TEXT);
        field.setCaretColor(AppTheme.TEXT);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppTheme.BORDER),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        return field;
    }

    private JComboBox<String> createComboBox(String[] values) {
        JComboBox<String> comboBox = new JComboBox<>(values);
        comboBox.setBackground(AppTheme.CARD_HOVER);
        comboBox.setForeground(AppTheme.TEXT);
        return comboBox;
    }

    private void styleSpinner(JSpinner spinner) {
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) spinner.getEditor();
        editor.getTextField().setBackground(AppTheme.CARD_HOVER);
        editor.getTextField().setForeground(AppTheme.TEXT);
        editor.getTextField().setCaretColor(AppTheme.TEXT);
    }

    private JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(AppTheme.PRIMARY);
        button.setForeground(Color.WHITE);
        button.setFont(AppTheme.BOLD_FONT);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        return button;
    }

    private JButton createSecondaryButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(AppTheme.CARD_HOVER);
        button.setForeground(AppTheme.TEXT);
        button.setFont(AppTheme.BOLD_FONT);
        button.setFocusPainted(false);
        return button;
    }

    private void styleTable() {
        inventoryTable.setBackground(AppTheme.CARD);
        inventoryTable.setForeground(AppTheme.TEXT);
        inventoryTable.setGridColor(AppTheme.BORDER);
        inventoryTable.setRowHeight(34);
        inventoryTable.setSelectionBackground(AppTheme.PRIMARY);
        inventoryTable.setSelectionForeground(Color.WHITE);
        inventoryTable.setShowVerticalLines(false);
        inventoryTable.setFont(new Font("Arial", Font.PLAIN, 13));

        JTableHeader header = inventoryTable.getTableHeader();
        header.setBackground(AppTheme.CARD_HOVER);
        header.setForeground(AppTheme.TEXT);
        header.setFont(new Font("Arial", Font.BOLD, 12));
        header.setPreferredSize(new Dimension(0, 36));
        header.setReorderingAllowed(false);

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);

        for (int column = 0; column < inventoryTable.getColumnCount(); column++) {
            inventoryTable.getColumnModel().getColumn(column).setCellRenderer(center);
        }

        inventoryTable.getColumnModel().getColumn(5).setCellRenderer(createStatusRenderer());
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
                    component.setForeground(
                            "LOW STOCK".equals(String.valueOf(value)) ? AppTheme.WARNING : AppTheme.SUCCESS
                    );
                }

                return component;
            }
        };
    }

    private void updateTypeOptions() {
        typeComboBox.removeAllItems();
        String category = (String) categoryComboBox.getSelectedItem();

        if ("Electronics".equals(category)) {
            addTypes("HDMI Cable", "Adapter", "Mouse", "Others");
        } else if ("Office Supply".equals(category)) {
            addTypes("Bond Paper", "Marker", "Folder", "Others");
        } else if ("Cleaning Supply".equals(category)) {
            addTypes("Disinfectant", "Trash Bag", "Broom", "Others");
        }
    }

    private void addTypes(String... types) {
        for (String type : types) {
            typeComboBox.addItem(type);
        }
    }

    private void addItem() {
        String itemName = itemNameField.getText().trim();
        String category = (String) categoryComboBox.getSelectedItem();
        String type = (String) typeComboBox.getSelectedItem();
        int quantity = (Integer) quantitySpinner.getValue();
        int reorderLevel = (Integer) reorderSpinner.getValue();

        if (itemName.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this, "Please enter an item name.", "Missing Information", JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (inventoryManager.findItem(itemName) != null) {
            JOptionPane.showMessageDialog(
                    this, "An item with this name already exists.", "Duplicate Item", JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if ("Others".equals(type)) {
            String customType = JOptionPane.showInputDialog(this, "Enter the item type:");
            if (customType == null || customType.trim().isEmpty()) {
                return;
            }
            type = customType.trim();
        }

        StockItem item;
        if ("Electronics".equals(category)) {
            item = new Electronics(itemName, quantity, reorderLevel, type);
        } else if ("Office Supply".equals(category)) {
            item = new OfficeSupply(itemName, quantity, reorderLevel, type);
        } else {
            item = new CleaningSupply(itemName, quantity, reorderLevel, type);
        }

        inventoryManager.addItem(item);
        clearForm();
        refreshTable();

        JOptionPane.showMessageDialog(
                this, "Item added successfully.", "StockWatch", JOptionPane.INFORMATION_MESSAGE
        );
    }

    public void refreshTable() {
        tableModel.setRowCount(0);

        String search = searchField.getText().trim().toLowerCase();
        String category = (String) filterComboBox.getSelectedItem();

        for (StockItem item : inventoryManager.getAllItems()) {
            boolean matchesSearch = search.isEmpty()
                    || item.getItemName().toLowerCase().contains(search)
                    || item.getType().toLowerCase().contains(search);

            boolean matchesCategory = "All Categories".equals(category)
                    || item.getCategory().equals(category);

            if (matchesSearch && matchesCategory) {
                tableModel.addRow(new Object[]{
                        item.getItemName(),
                        item.getCategory(),
                        item.getType(),
                        item.getQuantity(),
                        item.getReorderLevel(),
                        item.checkStockLevel()
                });
            }
        }

        updateCounters();
    }

    private void updateCounters() {
        int totalItems = inventoryManager.getAllItems().size();
        int lowStock = 0;

        for (StockItem item : inventoryManager.getAllItems()) {
            if (item.getQuantity() <= item.getReorderLevel()) {
                lowStock++;
            }
        }

        totalItemsValue.setText(String.valueOf(totalItems));
        inStockValue.setText(String.valueOf(totalItems - lowStock));
        lowStockValue.setText(String.valueOf(lowStock));

        int visibleItems = tableModel.getRowCount();
        visibleItemsLabel.setText(visibleItems == 1 ? "1 item" : visibleItems + " items");
    }

    private void clearForm() {
        itemNameField.setText("");
        quantitySpinner.setValue(0);
        reorderSpinner.setValue(0);
        categoryComboBox.setSelectedIndex(0);
    }
}
