package presentation;

import bll.DeliveryService;
import bll.MenuItem;
import bll.Order;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.awt.EventQueue;
import javax.swing.*;
import java.awt.Color;
import java.awt.Font;

public class EmployeeWindow extends JFrame implements Observer {
    private JButton btnNewButton;
    private JButton buttonMain;
    private JTextArea txtPlacedOrders;
    private DeliveryService deliveryService;
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    EmployeeWindow window = new EmployeeWindow();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public EmployeeWindow() {
        initialize();
        this.setVisible(true);
    }
    public EmployeeWindow(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
        initialize();
    }
    @Override
    public void update(Observable o, Object arg) {
        this.deliveryService = (DeliveryService)o;
        Order order = this.deliveryService.getLastOrder();
        JOptionPane.showMessageDialog(null, "ALERT: New order received!", "NEW ORDER NOTIFICATION", JOptionPane.INFORMATION_MESSAGE);
        displayNewOrder(order);
    }
    private void initialize() {
        this.setBackground(new Color(176, 196, 222));
        this.getContentPane().setBackground(new Color(176, 196, 222));
        this.getContentPane().setLayout(null);

        btnNewButton = new JButton("Prepare delivery");
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnNewButton.setBackground(new Color(240, 230, 140));
        btnNewButton.setBounds(307, 448, 233, 30);
        btnNewButton.addActionListener(al-> { int orderDel = prepareDelivery();
            this.txtPlacedOrders.setText(" Placed orders: \n");
            deliveryService.displayUnresolvedOrders(this.txtPlacedOrders);
        });
        this.getContentPane().add(btnNewButton);

        buttonMain = new JButton("Back");
        buttonMain.setForeground(new Color(112, 128, 144));
        buttonMain.setFont(new Font("Tahoma", Font.BOLD, 12));
        buttonMain.setBackground(new Color(216, 191, 216));
        buttonMain.setBounds(25, 502, 85, 21);
        buttonMain.addActionListener(al -> { LogInWindow mainWindow = new LogInWindow(); this.dispose();});
        this.getContentPane().add(buttonMain);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(55, 57, 746, 360);
        this.getContentPane().add(scrollPane);

        txtPlacedOrders = new JTextArea();
        txtPlacedOrders.setLineWrap(true);
        txtPlacedOrders.setText("Placed orders:\r\n");
        scrollPane.setViewportView(txtPlacedOrders);
        txtPlacedOrders.setBounds(55, 57, 746, 360);
        this.getContentPane().add(txtPlacedOrders);
        deliveryService.displayUnresolvedOrders(this.txtPlacedOrders);

        this.setBounds(100, 100, 865, 582);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    deliveryService.storeData();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                e.getWindow().dispose();
            }
        });
    }

    public void displayNewOrder(Order o){
        this.txtPlacedOrders.append("\n");
        this.txtPlacedOrders.append(o.display());
    }

    public int prepareDelivery() {
        Order order = null; int toDelete = Integer.MAX_VALUE;
        for(Map.Entry<Order, HashSet<MenuItem>> map:deliveryService.getOrders().entrySet()){
            if(map.getKey().getOrderID()<toDelete) {toDelete = map.getKey().getOrderID(); order = map.getKey(); }}
        deliveryService.getOrders().remove(order);
        return order.getOrderID();
    }
}
