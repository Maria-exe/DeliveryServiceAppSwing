package presentation;

import bll.*;
import bll.MenuItem;
import data.FileWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;

public class ClientWindow extends JFrame {
    
    private JTable tableMenuItems;
    private JTextField tfMinValue;
    private JTextField tfMaxValue;
    private JRadioButton btnPrice;
    private JRadioButton btnRating;
    private JRadioButton btnSodium;
    private JRadioButton btnFats;
    private JRadioButton btnProteins;
    private JRadioButton btnCalories;
    private JButton btnBack;
    private JButton btnGetSelectedItems;
    private JButton btnSearchMenu;
    private JScrollPane scrollPaneMenuItems;
    private JPanel panelCategories;
    private JTextField tfName;
    private JLabel lblName;
    private DeliveryService deliveryService;
    private ButtonGroup group;
    private int currentUserID;
    private EmployeeWindow employeeWindow;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ClientWindow window = new ClientWindow();
                    //window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public ClientWindow(DeliveryService deliveryService, int clienUID) {
        this.deliveryService = deliveryService;
        this.currentUserID = clienUID;
        initialize();

        this.employeeWindow= new EmployeeWindow(this.deliveryService);
        this.employeeWindow.setVisible(true);
        this.deliveryService.addObserver(this.employeeWindow);
    }
    public ClientWindow() {
        this.deliveryService = new DeliveryService();
        initialize();
    }

    /**
     * Initialize the contents of the this.
     */
    private void initialize() {
        setVisible(true);

        this.getContentPane().setBackground(new Color(250, 250, 210));
        this.getContentPane().setLayout(null);

        scrollPaneMenuItems = new JScrollPane();
        scrollPaneMenuItems.setBounds(32, 113, 795, 349);
        this.getContentPane().add(scrollPaneMenuItems);

        tableMenuItems = new JTable();
        tableMenuItems.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tableMenuItems.setFont(new Font("Tahoma", Font.BOLD, 12));
        tableMenuItems.setModel(new DefaultTableModel(
                new Object[][] {}, new String[] {
                        "<html><b>Title</b></html>", "<html><b>Rating</b></html>", "<html><b>Calories</b></html>", "<html><b>Proteins</b></html>", "<html><b>Fats</b></html>", "<html><b>Sodium</b></html>", "<html><b>Price</b></html>"}));
        populateProductsTable();
        scrollPaneMenuItems.setViewportView(tableMenuItems);

        btnSearchMenu = new JButton("Search menu ");
        btnSearchMenu.setForeground(new Color(255, 255, 255));
        btnSearchMenu.setBackground(new Color(72, 209, 204));
        btnSearchMenu.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnSearchMenu.setBounds(32, 53, 145, 24);
        btnSearchMenu.addActionListener(e -> {
            populateProductsTable(deliveryService.filterProducts(Integer.parseInt(tfMinValue.getText()),
                    Integer.parseInt(tfMaxValue.getText()), getSelectedCriteria()));
        });
        this.getContentPane().add(btnSearchMenu);

        btnGetSelectedItems = new JButton("PLACE ORDER");
        btnGetSelectedItems.setBackground(new Color(255, 99, 71));
        btnGetSelectedItems.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnGetSelectedItems.setForeground(new Color(255, 255, 255));
        btnGetSelectedItems.setBounds(645, 45, 182, 32);
        btnGetSelectedItems.addActionListener(e-> {

            Order o = placeOrder();
        });
        this.getContentPane().add(btnGetSelectedItems);

        tfMinValue = new JTextField();
        tfMinValue.setBounds(483, 30, 127, 19);
        this.getContentPane().add(tfMinValue);
        tfMinValue.setColumns(10);

        btnBack = new JButton("Back");
        btnBack.setForeground(new Color(255, 255, 255));
        btnBack.setBackground(new Color(176, 224, 230));
        btnBack.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnBack.setBounds(32, 10, 87, 21);
        btnBack.addActionListener( e-> { LogInWindow logInWindow = new LogInWindow(); dispose(); });
        this.getContentPane().add(btnBack);

        tfMaxValue = new JTextField();
        tfMaxValue.setColumns(10);
        tfMaxValue.setBounds(483, 53, 127, 19);
        this.getContentPane().add(tfMaxValue);

        panelCategories = new JPanel();
        group = new ButtonGroup();

        panelCategories.setBounds(201, 30, 210, 63);
        this.getContentPane().add(panelCategories);
        FlowLayout fl_panelCategories = new FlowLayout(FlowLayout.LEADING, 5, 5);
        panelCategories.setLayout(fl_panelCategories);

        btnCalories = new JRadioButton("Calories");
        group.add(btnCalories);
        panelCategories.add(btnCalories);

        btnProteins = new JRadioButton("Proteins");
        group.add(btnProteins);
        panelCategories.add(btnProteins);

        btnFats = new JRadioButton("Fats");
        group.add(btnFats);
        panelCategories.add(btnFats);

        btnSodium = new JRadioButton("Sodium");
        group.add(btnSodium);
        panelCategories.add(btnSodium);

        btnPrice = new JRadioButton("Price");
        group.add(btnPrice);
        panelCategories.add(btnPrice);

        btnRating = new JRadioButton("Rating");
        group.add(btnRating);
        panelCategories.add(btnRating);

        JLabel lblNewLabel = new JLabel("Min value");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 10));
        lblNewLabel.setBounds(421, 32, 52, 13);
        this.getContentPane().add(lblNewLabel);

        JLabel lblMaxValue = new JLabel("Max value");
        lblMaxValue.setFont(new Font("Tahoma", Font.BOLD, 10));
        lblMaxValue.setBounds(421, 55, 52, 13);
        this.getContentPane().add(lblMaxValue);

        JLabel lblNewLabel_1 = new JLabel("Filter products. Search products. Select products. Place your order.");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 10));
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setBounds(178, 10, 444, 13);
        this.getContentPane().add(lblNewLabel_1);

        tfName = new JTextField();
        tfName.setColumns(10);
        tfName.setBounds(483, 78, 127, 19);
        this.getContentPane().add(tfName);

        lblName = new JLabel("Name");
        lblName.setFont(new Font("Tahoma", Font.BOLD, 10));
        lblName.setBounds(421, 80, 52, 13);
        this.getContentPane().add(lblName);
        this.setBounds(100, 100, 873, 523);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                deliveryService.storeData();
                employeeWindow.dispose();
                e.getWindow().dispose();
            }
        });
    }

    public JTable getTableMenuItems() { return tableMenuItems;}

    public void populateProductsTable() {
        this.deliveryService.importProducts();
        DefaultTableModel tableModel = (DefaultTableModel) this.getTableMenuItems().getModel();
        int it = 0; BaseProduct bp; CompositeProduct cp;
        for (MenuItem mi : deliveryService.getMenuItems()) {
            if (mi instanceof BaseProduct) { bp = (BaseProduct) mi;
                tableModel.addRow(new Object[]{bp.getTitle(),bp.getRating(), bp.getCalories(),bp.getProteins(), bp.getFats(),bp.getSodium(),bp.getPrice()});
            }
        }
    }
    public void populateProductsTable(HashSet<MenuItem> menuItems) {
        DefaultTableModel tableModel = (DefaultTableModel) this.getTableMenuItems().getModel();
        tableModel.setRowCount(0);
        int it = 0; BaseProduct bp; CompositeProduct cp;
        for (MenuItem mi : menuItems){
            if (mi instanceof BaseProduct) { bp = (BaseProduct) mi;
                tableModel.addRow(new Object[]{bp.getTitle(),bp.getRating(), bp.getCalories(),bp.getProteins(), bp.getFats(),bp.getSodium(),bp.getPrice()});
            }
        }
    }

    public String getSelectedCriteria() {
        for (Enumeration<AbstractButton> buttons = this.group.getElements(); buttons.hasMoreElements(); ) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) { return button.getText(); } }
        return null;
    }
    public HashSet<MenuItem> getSelectedMenuItems(){
        HashSet<MenuItem> menuItems = new HashSet<>();
        DefaultTableModel tableModel = (DefaultTableModel) this.getTableMenuItems().getModel();
        int [] rows = this.tableMenuItems.getSelectedRows();
        for (int r :rows ) {
            BaseProduct bp = BaseProduct.createBaseProduct(tableModel.getDataVector().elementAt(r));
            menuItems.add(bp);
        }
        return menuItems;
    }

    public Order placeOrder(){
        HashSet<MenuItem> selectedMenuItems = this.getSelectedMenuItems();
        Order o = deliveryService.placeOrder(this.currentUserID, selectedMenuItems);
        deliveryService.createBill(o);
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.open(FileWriter.getInst().getMyFile());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return o;
    }

}
