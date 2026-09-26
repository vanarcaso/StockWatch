package stockwatch.gui;

import stockwatch.model.InventoryManager;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

public class StockWatchFrame extends JFrame {

    private final InventoryManager inventoryManager =
            new InventoryManager();

    private final CardLayout cardLayout =
            new CardLayout();

    private final JPanel contentPanel =
            new JPanel(cardLayout);

    private DashboardPanel dashboardPanel;
    private InventoryPanel inventoryPanel;
    private StockInPanel stockInPanel;
    private StockOutPanel stockOutPanel;
    private TransactionsPanel transactionsPanel;
    private LowStockPanel lowStockPanel;

    private JButton activeButton;

    public StockWatchFrame() {
        setTitle("StockWatch - School Supply Room Inventory");
        setSize(1180, 720);
        setMinimumSize(new Dimension(1000, 650));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        getContentPane().setBackground(
                AppTheme.BACKGROUND
        );

        createPages();

        add(
                createSidebar(),
                BorderLayout.WEST
        );

        add(
                createWorkspace(),
                BorderLayout.CENTER
        );
    }

    private void createPages() {
        dashboardPanel =
                new DashboardPanel(
                        inventoryManager
                );

        inventoryPanel =
                new InventoryPanel(
                        inventoryManager
                );

        stockInPanel =
                new StockInPanel(
                        inventoryManager
                );

        stockOutPanel =
                new StockOutPanel(
                        inventoryManager
                );

        transactionsPanel =
                new TransactionsPanel(
                        inventoryManager
                );

        lowStockPanel =
                new LowStockPanel(
                        inventoryManager
                );

        contentPanel.setBackground(
                AppTheme.BACKGROUND
        );

        contentPanel.add(
                dashboardPanel,
                "Dashboard"
        );

        contentPanel.add(
                inventoryPanel,
                "Inventory"
        );

        contentPanel.add(
                stockInPanel,
                "Stock In"
        );

        contentPanel.add(
                stockOutPanel,
                "Stock Out"
        );

        contentPanel.add(
                transactionsPanel,
                "Transactions"
        );

        contentPanel.add(
                lowStockPanel,
                "Low Stock"
        );
    }

    private JPanel createWorkspace() {
        JPanel workspace =
                new JPanel(
                        new BorderLayout()
                );

        workspace.setBackground(
                AppTheme.BACKGROUND
        );

        workspace.add(
                createTopBar(),
                BorderLayout.NORTH
        );

        workspace.add(
                contentPanel,
                BorderLayout.CENTER
        );

        return workspace;
    }

    private JPanel createTopBar() {
        JPanel topBar =
                new JPanel(
                        new BorderLayout()
                );

        topBar.setBackground(
                AppTheme.BACKGROUND
        );

        topBar.setBorder(
                BorderFactory.createEmptyBorder(
                        14,
                        20,
                        10,
                        20
                )
        );

        JLabel systemName =
                new JLabel(
                        "School Supply Room Inventory"
                );

        systemName.setForeground(
                AppTheme.MUTED_TEXT
        );

        systemName.setFont(
                AppTheme.NORMAL_FONT
        );

        RoundedPanel statusCard =
                new RoundedPanel(
                        18,
                        AppTheme.CARD
                );

        statusCard.setLayout(
                new BorderLayout()
        );

        statusCard.setBorder(
                BorderFactory.createEmptyBorder(
                        7,
                        14,
                        7,
                        14
                )
        );

        JLabel status =
                new JLabel(
                        "●  SYSTEM READY"
                );

        status.setForeground(
                AppTheme.SUCCESS
        );

        status.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        12
                )
        );

        statusCard.add(
                status,
                BorderLayout.CENTER
        );

        topBar.add(
                systemName,
                BorderLayout.WEST
        );

        topBar.add(
                statusCard,
                BorderLayout.EAST
        );

        return topBar;
    }

    private JPanel createSidebar() {
        JPanel sidebar =
                new JPanel();

        sidebar.setBackground(
                AppTheme.SIDEBAR
        );

        sidebar.setPreferredSize(
                new Dimension(
                        225,
                        0
                )
        );

        sidebar.setLayout(
                new BoxLayout(
                        sidebar,
                        BoxLayout.Y_AXIS
                )
        );

        sidebar.setBorder(
                BorderFactory.createEmptyBorder(
                        25,
                        16,
                        20,
                        16
                )
        );

        JLabel brand =
                new JLabel(
                        "STOCKWATCH"
                );

        brand.setForeground(
                AppTheme.TEXT
        );

        brand.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        brand.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel brandSubtitle =
                new JLabel(
                        "Inventory Management"
                );

        brandSubtitle.setForeground(
                AppTheme.CYAN
        );

        brandSubtitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        11
                )
        );

        brandSubtitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        sidebar.add(brand);

        sidebar.add(
                Box.createVerticalStrut(3)
        );

        sidebar.add(brandSubtitle);

        sidebar.add(
                Box.createVerticalStrut(35)
        );

        JButton dashboardButton =
                createMenuButton(
                        "Dashboard"
                );

        JButton inventoryButton =
                createMenuButton(
                        "Inventory"
                );

        JButton stockInButton =
                createMenuButton(
                        "Stock In"
                );

        JButton stockOutButton =
                createMenuButton(
                        "Stock Out"
                );

        JButton transactionsButton =
                createMenuButton(
                        "Transactions"
                );

        JButton lowStockButton =
                createMenuButton(
                        "Low Stock"
                );

        addMenuButton(
                sidebar,
                dashboardButton
        );

        addMenuButton(
                sidebar,
                inventoryButton
        );

        addMenuButton(
                sidebar,
                stockInButton
        );

        addMenuButton(
                sidebar,
                stockOutButton
        );

        addMenuButton(
                sidebar,
                transactionsButton
        );

        addMenuButton(
                sidebar,
                lowStockButton
        );

        sidebar.add(
                Box.createVerticalGlue()
        );

        JLabel footer =
                new JLabel(
                        "Mini Capstone Project"
                );

        footer.setForeground(
                AppTheme.MUTED_TEXT
        );

        footer.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        11
                )
        );

        footer.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        sidebar.add(footer);

        dashboardButton.addActionListener(e -> {
            dashboardPanel.refreshDashboard();

            showPage(
                    "Dashboard",
                    dashboardButton
            );
        });

        inventoryButton.addActionListener(e -> {
            inventoryPanel.refreshTable();

            showPage(
                    "Inventory",
                    inventoryButton
            );
        });

        stockInButton.addActionListener(e -> {
            stockInPanel.refreshItems();

            showPage(
                    "Stock In",
                    stockInButton
            );
        });

        stockOutButton.addActionListener(e -> {
            stockOutPanel.refreshItems();

            showPage(
                    "Stock Out",
                    stockOutButton
            );
        });

        transactionsButton.addActionListener(e -> {
            transactionsPanel.refreshTable();

            showPage(
                    "Transactions",
                    transactionsButton
            );
        });

        lowStockButton.addActionListener(e -> {
            lowStockPanel.refreshTable();

            showPage(
                    "Low Stock",
                    lowStockButton
            );
        });

        setActiveButton(
                dashboardButton
        );

        return sidebar;
    }

    private JButton createMenuButton(
            String text
    ) {
        JButton button =
                new JButton(text);

        button.setHorizontalAlignment(
                SwingConstants.LEFT
        );

        button.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        44
                )
        );

        button.setPreferredSize(
                new Dimension(
                        190,
                        44
                )
        );

        button.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        button.setBackground(
                AppTheme.SIDEBAR
        );

        button.setForeground(
                AppTheme.MUTED_TEXT
        );

        button.setFont(
                AppTheme.BOLD_FONT
        );

        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        0,
                        14,
                        0,
                        10
                )
        );

        return button;
    }

    private void addMenuButton(
            JPanel sidebar,
            JButton button
    ) {
        sidebar.add(button);

        sidebar.add(
                Box.createVerticalStrut(7)
        );
    }

    private void showPage(
            String pageName,
            JButton button
    ) {
        cardLayout.show(
                contentPanel,
                pageName
        );

        setActiveButton(button);
    }

    private void setActiveButton(
            JButton selectedButton
    ) {
        if (activeButton != null) {

            activeButton.setBackground(
                    AppTheme.SIDEBAR
            );

            activeButton.setForeground(
                    AppTheme.MUTED_TEXT
            );
        }

        selectedButton.setBackground(
                AppTheme.PRIMARY
        );

        selectedButton.setForeground(
                Color.WHITE
        );

        activeButton =
                selectedButton;
    }

    public static void main(
            String[] args
    ) {
        SwingUtilities.invokeLater(() -> {

            StockWatchFrame frame =
                    new StockWatchFrame();

            frame.setVisible(true);
        });
    }
}