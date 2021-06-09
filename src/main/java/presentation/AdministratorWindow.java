package presentation;

import bll.BaseProduct;
import bll.CompositeProduct;
import bll.DeliveryService;
import bll.MenuItem;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashSet;
import java.util.List;


public class AdministratorWindow {
    private Raports raportWindow;
    private JFrame frame;
    private JTable tableMenuItems;
    private DeliveryService deliveryService;
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AdministratorWindow window = new AdministratorWindow();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public AdministratorWindow() {
        this.deliveryService = new DeliveryService();
        initialize();
        this.frame.setVisible(true);
    }
    public AdministratorWindow(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
        initialize();
        this.frame.setVisible(true);
    }
    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.getContentPane().setBackground(new Color(102, 205, 170));
        frame.setBounds(100, 100, 869, 573);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JScrollPane scrollPaneMenuItems = new JScrollPane();
        scrollPaneMenuItems.setBounds(33, 88, 795, 397);
        frame.getContentPane().add(scrollPaneMenuItems);

        tableMenuItems = new JTable();
        tableMenuItems.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tableMenuItems.setFont(new Font("Tahoma", Font.BOLD, 12));
        tableMenuItems.setModel(new DefaultTableModel(
                new Object[][] {}, new String[] {"<html><b>Title</b></html>", "<html><b>Rating</b></html>", "<html><b>Calories</b></html>", "<html><b>Proteins</b></html>", "<html><b>Fats</b></html>", "<html><b>Sodium</b></html>", "<html><b>Price</b></html>"}));
        scrollPaneMenuItems.setViewportView(tableMenuItems);

        JButton btnBack = new JButton("Back");
        btnBack.setForeground(new Color(112, 128, 144));
        btnBack.setBackground(new Color(216, 191, 216));
        btnBack.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnBack.setBounds(33, 505, 85, 21);
        frame.getContentPane().add(btnBack);

        JButton btnImport = new JButton("Import products");
        btnImport.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnImport.setForeground(new Color(0, 0, 0));
        btnImport.setBackground(new Color(216, 191, 216));
        btnImport.setBounds(33, 34, 148, 21);
        btnImport.addActionListener(e -> populateProductsTable());
        frame.getContentPane().add(btnImport);

        JButton btnAddProd = new JButton("Add product");
        btnAddProd.setForeground(Color.BLACK);
        btnAddProd.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnAddProd.setBackground(new Color(216, 191, 216));
        btnAddProd.setBounds(191, 34, 114, 21);
        btnAddProd.addActionListener(e-> {AddProductWindow addProductWindow= new AddProductWindow(this, this.deliveryService);});
        frame.getContentPane().add(btnAddProd);

        JButton btnDelete = new JButton("Delete");
        btnDelete.setForeground(Color.BLACK);
        btnDelete.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnDelete.setBackground(new Color(216, 191, 216));
        btnDelete.setBounds(318, 35, 109, 21);
        btnDelete.addActionListener(e -> {
            deleteSelectedMenuItems();
            JOptionPane.showMessageDialog(null, "Selected items were successfully deleted.", "Info", JOptionPane.INFORMATION_MESSAGE); });
        frame.getContentPane().add(btnDelete);

        JButton btnModify = new JButton("Modify");
        btnModify.setForeground(Color.BLACK);
        btnModify.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnModify.setBackground(new Color(216, 191, 216));
        btnModify.setBounds(446, 35, 109, 21);
        btnModify.addActionListener(e-> {
            modifyProduct();
        JOptionPane.showMessageDialog(null, "Selected item was successfully modified.", "Info", JOptionPane.INFORMATION_MESSAGE);});
        frame.getContentPane().add(btnModify);

        JButton btnGenerateRaport = new JButton("Generate raport ");
        btnGenerateRaport.setForeground(Color.BLACK);
        btnGenerateRaport.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnGenerateRaport.setBackground(new Color(240, 230, 140));
        btnGenerateRaport.setBounds(617, 506, 211, 21);
        btnGenerateRaport.addActionListener(e->  raportWindow = new Raports(this));
        frame.getContentPane().add(btnGenerateRaport);

    }
    public JTable getTableMenuItems() {
        return tableMenuItems;
    }
    public void populateProductsTable() {
        this.deliveryService.importProducts();
        DefaultTableModel tableModel = (DefaultTableModel) this.getTableMenuItems().getModel();
        int it = 0;
        BaseProduct bp;
        CompositeProduct cp;
        for (MenuItem mi : deliveryService.getMenuItems()) {
            if (mi instanceof BaseProduct) {
                bp = (BaseProduct) mi;
                tableModel.addRow(new Object[]{bp.getTitle(), bp.getRating(), bp.getCalories(), bp.getProteins(), bp.getFats(), bp.getSodium(), bp.getPrice()});
            }
        }
    }

    public void deleteSelectedMenuItems(){
        DefaultTableModel tableModel = (DefaultTableModel) this.getTableMenuItems().getModel();
        int [] rows = this.tableMenuItems.getSelectedRows();
        for (int r :rows ) {
            BaseProduct bp = BaseProduct.createBaseProduct(tableModel.getDataVector().elementAt(r));
            deliveryService.deleteProduct(bp);
            tableModel.removeRow(r);
        }
    }

    public void modifyProduct(){
        DefaultTableModel tableModel = (DefaultTableModel) this.getTableMenuItems().getModel();
        int r = this.tableMenuItems.getSelectedRow();
        List bp = tableModel.getDataVector().elementAt(r);
        deliveryService.modifyProduct(r,bp);
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

    public DeliveryService getDeliveryService() {
        return deliveryService;
    }
}


