package presentation;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;

public class Raports extends JFrame {
     JSpinner spinStartHour;
     JSpinner spinNumOfTimes2;
     JSpinner spinEndHour;
     JTextField tfMin3;
     JButton btnGenerate4;
     JSpinner spinnerDate;
     JButton btnGenerateAll;
     JSpinner spinNumOfTimes3;
     AdministratorWindow administratorWindow;

    /**
     * Create the application.
     */
    public Raports(AdministratorWindow administratorWindow) {
        this.administratorWindow = administratorWindow;
        initialize();
        this.setVisible(true);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        getContentPane().setBackground(new Color(205, 92, 92));
        getContentPane().setLayout(null);

        JPanel addProductsPanel = new JPanel();
        addProductsPanel.setLayout(null);
        addProductsPanel.setBackground(new Color(245, 245, 220));
        addProductsPanel.setBounds(22, 43, 252, 355);
        getContentPane().add(addProductsPanel);

        spinStartHour = new JSpinner();
        spinStartHour.setModel(new SpinnerNumberModel(0, 0, 23, 1));
        spinStartHour.setBounds(107, 105, 121, 28);
        addProductsPanel.add(spinStartHour);

        JLabel lblMenuName = new JLabel("Start hour");
        lblMenuName.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblMenuName.setBounds(20, 111, 95, 16);
        addProductsPanel.add(lblMenuName);

        JButton btnGenerate1 = new JButton("GENERATE RAPORT 1");
        btnGenerate1.setForeground(Color.WHITE);
        btnGenerate1.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnGenerate1.setBackground(new Color(95, 158, 160));
        btnGenerate1.setBounds(36, 212, 192, 28);
        btnGenerate1.addActionListener(al-> administratorWindow.getDeliveryService().generateReportOne((int)spinStartHour.getValue(), (int) spinEndHour.getValue()));
        addProductsPanel.add(btnGenerate1);

        JLabel lblNewLabel = new JLabel("<html><center>RAPORT 1<br>\r\nthe orders performed \r\nbetween a given start hour and a given end hour regardless the date\r\n</center></html>");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setForeground(new Color(46, 139, 87));
        lblNewLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 16));
        lblNewLabel.setBounds(10, 10, 232, 85);
        addProductsPanel.add(lblNewLabel);

        spinEndHour = new JSpinner();
        spinEndHour.setBounds(108, 145, 120, 28);
        addProductsPanel.add(spinEndHour);

        JLabel lblEndHour = new JLabel("End hour");
        lblEndHour.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblEndHour.setBounds(20, 151, 95, 16);
        addProductsPanel.add(lblEndHour);

        JPanel addProductsPanel_1 = new JPanel();
        addProductsPanel_1.setLayout(null);
        addProductsPanel_1.setBackground(new Color(245, 245, 220));
        addProductsPanel_1.setBounds(288, 43, 252, 355);
        getContentPane().add(addProductsPanel_1);

        spinNumOfTimes2 = new JSpinner();
        spinNumOfTimes2.setBounds(136, 107, 95, 28);
        addProductsPanel_1.add(spinNumOfTimes2);

        JLabel lblMenuName_1 = new JLabel("Specify number\r\n");
        lblMenuName_1.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblMenuName_1.setBounds(22, 111, 117, 16);
        addProductsPanel_1.add(lblMenuName_1);

        JButton btnGenerate2 = new JButton("GENERATE RAPORT 2\r\n");
        btnGenerate2.setForeground(Color.WHITE);
        btnGenerate2.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnGenerate2.setBackground(new Color(95, 158, 160));
        btnGenerate2.setBounds(34, 212, 192, 28);
        btnGenerate2.addActionListener(al-> administratorWindow.getDeliveryService().generateReportTwo((int)spinNumOfTimes2.getValue()));
        addProductsPanel_1.add(btnGenerate2);

        JLabel lblNewLabel_1 = new JLabel("<html><center>RAPORT 2 <br>\r\nthe products ordered more than a specified number of times\r\n</center><html>\r\n");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setForeground(new Color(46, 139, 87));
        lblNewLabel_1.setFont(new Font("Mongolian Baiti", Font.BOLD, 16));
        lblNewLabel_1.setBounds(10, 10, 232, 85);
        addProductsPanel_1.add(lblNewLabel_1);

        JPanel addProductsPanel_2 = new JPanel();
        addProductsPanel_2.setLayout(null);
        addProductsPanel_2.setBackground(new Color(245, 245, 220));
        addProductsPanel_2.setBounds(555, 43, 252, 355);
        getContentPane().add(addProductsPanel_2);

        JLabel lblMenuName_2 = new JLabel("<html>Specify number<br> of times</html>\r\n");
        lblMenuName_2.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblMenuName_2.setBounds(20, 125, 109, 36);
        addProductsPanel_2.add(lblMenuName_2);

        JButton btnGenerate3 = new JButton("GENERATE RAPORT 3");
        btnGenerate3.setForeground(Color.WHITE);
        btnGenerate3.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnGenerate3.setBackground(new Color(95, 158, 160));
        btnGenerate3.setBounds(36, 214, 192, 28);
        btnGenerate3.addActionListener(al-> this.administratorWindow.getDeliveryService().generateReportThree((int) this.spinNumOfTimes3.getValue(),Integer.parseInt(this.tfMin3.getText())));
        addProductsPanel_2.add(btnGenerate3);

        JLabel lblNewLabel_2 = new JLabel("<html><center>RAPORT 3<br>\r\nthe clients that have ordered more than a specified number of times and the value \r\nof the order was higher than a specified amount\r\n</center></html>");
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_2.setForeground(new Color(46, 139, 87));
        lblNewLabel_2.setFont(new Font("Mongolian Baiti", Font.BOLD, 16));
        lblNewLabel_2.setBounds(10, 10, 232, 111);
        addProductsPanel_2.add(lblNewLabel_2);

        spinNumOfTimes3 = new JSpinner();
        spinNumOfTimes3.setBounds(139, 127, 95, 28);
        addProductsPanel_2.add(spinNumOfTimes3);

        tfMin3 = new JTextField();
        tfMin3.setColumns(10);
        tfMin3.setBounds(139, 165, 95, 28);
        addProductsPanel_2.add(tfMin3);

        JLabel lblMenuName_2_1_1 = new JLabel("Minimum amount\r\n");
        lblMenuName_2_1_1.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblMenuName_2_1_1.setBounds(20, 171, 119, 16);
        addProductsPanel_2.add(lblMenuName_2_1_1);

        JPanel addProductsPanel_2_1 = new JPanel();
        addProductsPanel_2_1.setLayout(null);
        addProductsPanel_2_1.setBackground(new Color(245, 245, 220));
        addProductsPanel_2_1.setBounds(823, 43, 252, 355);
        getContentPane().add(addProductsPanel_2_1);

        JLabel lblChooseDate = new JLabel("Choose date\r\n");
        lblChooseDate.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblChooseDate.setBounds(22, 111, 95, 16);
        addProductsPanel_2_1.add(lblChooseDate);

        JLabel lblRaport4 = new JLabel("<html><center>RAPORT 4 <br>\r\nthe products ordered within a specified day with the number of times they have \r\nbeen ordered\r\n</center></html>");
        lblRaport4.setForeground(new Color(46, 139, 87));
        lblRaport4.setFont(new Font("Mongolian Baiti", Font.BOLD, 16));
        lblRaport4.setBounds(10, 10, 232, 85);
        addProductsPanel_2_1.add(lblRaport4);

        btnGenerate4 = new JButton("GENERATE RAPORT 4\r\n");
        btnGenerate4.setForeground(Color.WHITE);
        btnGenerate4.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnGenerate4.setBackground(new Color(95, 158, 160));
        btnGenerate4.setBounds(32, 212, 192, 28);
        btnGenerate4.addActionListener(al-> this.administratorWindow.getDeliveryService().generateReportFour((Date)this.spinnerDate.getValue()));
        addProductsPanel_2_1.add(btnGenerate4);

        spinnerDate = new JSpinner();
        spinnerDate.setModel(new SpinnerDateModel(new Date(1622408400000L), null, null, Calendar.DAY_OF_YEAR));
        spinnerDate.setBounds(32, 135, 192, 20);
        addProductsPanel_2_1.add(spinnerDate);

        btnGenerateAll = new JButton("GENERATE ALL RAPORTS");
        btnGenerateAll.setBounds(439, 423, 221, 28);
        getContentPane().add(btnGenerateAll);
        btnGenerateAll.setForeground(Color.WHITE);
        btnGenerateAll.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnGenerateAll.setBackground(new Color(178, 34, 34));
        this.setBounds(100, 100, 1112, 515);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
