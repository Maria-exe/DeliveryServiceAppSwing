package presentation;

import bll.BaseProduct;
import bll.CompositeProduct;
import bll.DeliveryService;
import bll.MenuItem;
import data.Serializator;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Vector;

public class AddProductWindow extends JFrame {
    private JTextField tfMenuName;
    private JTextField tfProdName;
    private JTextField tfRating;
    private JTextField tfPrice;
    private JTextField tfCalories;
    private JTextField tfProteins;
    private JTextField tfFats;
    private JTextField tfSodium;
    AdministratorWindow administratorWindow;
    HashSet<MenuItem> selectedMenuItems;
    DeliveryService deliveryService;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                   // AddProductWindow window = new AddProductWindow();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public AddProductWindow(AdministratorWindow administratorWindow, DeliveryService deliveryService) {
        this.administratorWindow = administratorWindow;
        this.deliveryService = deliveryService;
        initialize();
        this.setVisible(true);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        getContentPane().setBackground(new Color(240, 128, 128));
        getContentPane().setLayout(null);

        JPanel addProductsPanel = new JPanel();
        addProductsPanel.setLayout(null);
        addProductsPanel.setBackground(new Color(245, 245, 220));
        addProductsPanel.setBounds(81, 59, 271, 355);
        getContentPane().add(addProductsPanel);

        tfMenuName = new JTextField();
        tfMenuName.setColumns(10);
        tfMenuName.setBounds(116, 105, 131, 28);
        addProductsPanel.add(tfMenuName);

        JLabel lblMenuName = new JLabel("Menu name");
        lblMenuName.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblMenuName.setBounds(22, 111, 95, 16);
        addProductsPanel.add(lblMenuName);

        JButton btnClearAddProduct = new JButton("CLEAR");
        btnClearAddProduct.setForeground(Color.WHITE);
        btnClearAddProduct.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnClearAddProduct.setBackground(new Color(178, 34, 34));
        btnClearAddProduct.setBounds(43, 229, 192, 28);
        btnClearAddProduct.addActionListener(e-> {tfMenuName.setText(null); this.selectedMenuItems.clear();});
        addProductsPanel.add(btnClearAddProduct);

        JButton btnCreateMenu = new JButton("CREATE MENU");
        btnCreateMenu.setForeground(Color.WHITE);
        btnCreateMenu.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnCreateMenu.setBackground(new Color(95, 158, 160));
        btnCreateMenu.setBounds(43, 262, 192, 28);
        btnCreateMenu.addActionListener(e-> {
            if(tfMenuName.getText().isBlank() || this.selectedMenuItems.isEmpty()) JOptionPane.showMessageDialog(null,"ERROR: Select base items for menu and give the menu a name.", "Error: creating special menu", JOptionPane.INFORMATION_MESSAGE);
            else {
                CompositeProduct compositeProduct = this.deliveryService.createCompositeProduct(tfMenuName.getText(),this.selectedMenuItems);
                this.deliveryService.addProduct(compositeProduct);
                Serializator.serialize(compositeProduct, "products.ser");
                JOptionPane.showMessageDialog(null, "Composite menu item created and added successfully.");
            }
        });
        addProductsPanel.add(btnCreateMenu);

        JButton btnGetSelectedProduct = new JButton("<html><center>GET SELECTED\r\n PRODUCTS</center></html>");
        btnGetSelectedProduct.setForeground(Color.WHITE);
        btnGetSelectedProduct.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnGetSelectedProduct.setBackground(new Color(95, 158, 160));
        btnGetSelectedProduct.setBounds(43, 169, 192, 54);
        btnCreateMenu.addActionListener(e-> this.selectedMenuItems = this.administratorWindow.getSelectedMenuItems());
        addProductsPanel.add(btnGetSelectedProduct);

        JLabel lblNewLabel = new JLabel("<html><center>CREATE A NEW SPECIAL\r\n MENU</center></html>");
        lblNewLabel.setForeground(new Color(46, 139, 87));
        lblNewLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 16));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(22, 29, 225, 35);
        addProductsPanel.add(lblNewLabel);

        JPanel addProductsPanel_1 = new JPanel();
        addProductsPanel_1.setLayout(null);
        addProductsPanel_1.setBackground(new Color(245, 245, 220));
        addProductsPanel_1.setBounds(503, 59, 271, 355);
        getContentPane().add(addProductsPanel_1);

        tfProdName = new JTextField();
        tfProdName.setColumns(10);
        tfProdName.setBounds(114, 33, 131, 28);
        addProductsPanel_1.add(tfProdName);

        tfRating = new JTextField();
        tfRating.setColumns(10);
        tfRating.setBounds(114, 67, 131, 28);
        addProductsPanel_1.add(tfRating);

        tfPrice = new JTextField();
        tfPrice.setColumns(10);
        tfPrice.setBounds(114, 229, 131, 28);
        addProductsPanel_1.add(tfPrice);

        JLabel lblProdName = new JLabel("Product name");
        lblProdName.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblProdName.setBounds(20, 39, 95, 16);
        addProductsPanel_1.add(lblProdName);

        JLabel lblRating = new JLabel("Rating");
        lblRating.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblRating.setBounds(20, 73, 82, 16);
        addProductsPanel_1.add(lblRating);

        JLabel lblPrice_1 = new JLabel("Price");
        lblPrice_1.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblPrice_1.setBounds(20, 235, 82, 16);
        addProductsPanel_1.add(lblPrice_1);

        JButton btnClearAddProduct_1 = new JButton("CLEAR");
        btnClearAddProduct_1.setForeground(Color.WHITE);
        btnClearAddProduct_1.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnClearAddProduct_1.setBackground(new Color(178, 34, 34));
        btnClearAddProduct_1.setBounds(38, 273, 192, 28);
        btnClearAddProduct_1.addActionListener(e-> {tfProdName.setText(null); tfCalories.setText(null); tfProteins.setText(null); tfFats.setText(null); tfRating.setText(null); tfSodium.setText(null); tfPrice.setText(null);});
        addProductsPanel_1.add(btnClearAddProduct_1);

        JButton btnAddProduct = new JButton("ADD PRODUCT");
        btnAddProduct.setForeground(Color.WHITE);
        btnAddProduct.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnAddProduct.setBackground(new Color(95, 158, 160));
        btnAddProduct.setBounds(38, 306, 192, 28);
        btnAddProduct.addActionListener(e-> {if (tfProdName.getText().isBlank() || tfCalories.getText().isBlank() || tfProteins.getText().isBlank() || tfFats.getText().isBlank() || tfSodium.getText().isBlank() || tfRating.getText().isBlank() || tfPrice.getText().isBlank())
            JOptionPane.showMessageDialog(null,"ERROR: complete all fields with product data","Error: incomplete information about product", JOptionPane.INFORMATION_MESSAGE);
            else {
                BaseProduct bp = new BaseProduct(tfProdName.getText(),Double.parseDouble(tfRating.getText()),Integer.parseInt(tfCalories.getText()),Integer.parseInt(tfProteins.getText()),Integer.parseInt(tfFats.getText()),Integer.parseInt(tfSodium.getText()),Integer.parseInt(tfPrice.getText()));
                Serializator.serialize(bp, "products.ser");
                this.deliveryService.addProduct(bp);
            ((DefaultTableModel)this.administratorWindow.getTableMenuItems().getModel()).insertRow(0, new Vector<String>(Arrays.asList(tfProdName.getText(),tfRating.getText(),tfCalories.getText(),tfProteins.getText(),tfFats.getText(),tfSodium.getText(),tfPrice.getText())));
            }});
        addProductsPanel_1.add(btnAddProduct);

        tfCalories = new JTextField();
        tfCalories.setColumns(10);
        tfCalories.setBounds(114, 99, 131, 28);
        addProductsPanel_1.add(tfCalories);

        JLabel lblCalories = new JLabel("Calories");
        lblCalories.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblCalories.setBounds(20, 105, 82, 16);
        addProductsPanel_1.add(lblCalories);

        tfProteins = new JTextField();
        tfProteins.setColumns(10);
        tfProteins.setBounds(114, 132, 131, 28);
        addProductsPanel_1.add(tfProteins);

        JLabel lblProteins = new JLabel("Proteins");
        lblProteins.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblProteins.setBounds(20, 138, 82, 16);
        addProductsPanel_1.add(lblProteins);

        tfFats = new JTextField();
        tfFats.setColumns(10);
        tfFats.setBounds(114, 164, 131, 28);
        addProductsPanel_1.add(tfFats);

        JLabel lblFats = new JLabel("Fats");
        lblFats.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblFats.setBounds(20, 170, 82, 16);
        addProductsPanel_1.add(lblFats);

        tfSodium = new JTextField();
        tfSodium.setColumns(10);
        tfSodium.setBounds(114, 196, 131, 28);
        addProductsPanel_1.add(tfSodium);

        JLabel lblSodium = new JLabel("Sodium");
        lblSodium.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblSodium.setBounds(20, 202, 82, 16);
        addProductsPanel_1.add(lblSodium);

        JLabel lblAddBaseProduct = new JLabel("ADD BASE PRODUCT");
        lblAddBaseProduct.setHorizontalAlignment(SwingConstants.CENTER);
        lblAddBaseProduct.setForeground(new Color(46, 139, 87));
        lblAddBaseProduct.setFont(new Font("Mongolian Baiti", Font.BOLD, 15));
        lblAddBaseProduct.setBounds(20, 0, 225, 35);
        addProductsPanel_1.add(lblAddBaseProduct);

        JButton btnBack = new JButton("Back");
        btnBack.setForeground(Color.WHITE);
        btnBack.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnBack.setBackground(new Color(176, 224, 230));
        btnBack.setBounds(21, 452, 87, 21);
        getContentPane().add(btnBack);
        this.setBounds(100, 100, 876, 532);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
