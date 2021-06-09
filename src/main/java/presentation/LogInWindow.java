package presentation;

import bll.DeliveryService;
import data.Serializator;
import model.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class LogInWindow extends JFrame {

    private JPanel contentPane;
    private JTextField tfEmail;
    private JPasswordField tfPassword;
    private JButton btnSignUp;
    private JButton btnSignIn;
    private DeliveryService deliveryService;
    private static List<User> users = new ArrayList<>();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LogInWindow frame = new LogInWindow();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public LogInWindow() {
        this.deliveryService = new DeliveryService();
        try {
            this.deliveryService = (DeliveryService) Serializator.deserialize( "delivery.ser");
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.deliveryService.getMenuItems().clear();
        initialize();
    }

    public void initialize() {
        setBackground(new Color(144, 238, 144));

        setTitle("LogIn");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 865, 489);


        contentPane = new JPanel() ;
        contentPane.setBackground(new Color(95, 158, 160));

        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);


        JLabel lblEmail = new JLabel("Email");
        lblEmail.setForeground(new Color(255, 255, 255));
        lblEmail.setFont(new Font("Tahoma", Font.BOLD, 17));
        lblEmail.setBounds(250, 157, 46, 14);
        contentPane.add(lblEmail);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setForeground(new Color(255, 255, 255));
        lblPassword.setFont(new Font("Tahoma", Font.BOLD, 17));
        lblPassword.setBounds(250, 201, 94, 14);
        contentPane.add(lblPassword);

        tfEmail = new JTextField();
        tfEmail.setBounds(361, 155, 200, 25);
        contentPane.add(tfEmail);
        tfEmail.setColumns(10);

        tfPassword = new JPasswordField();
        tfPassword.setBounds(361, 196, 200, 25);
        contentPane.add(tfPassword);

        btnSignIn = new JButton("Sign in");
        btnSignIn.setFont(new Font("Tahoma", Font.BOLD, 15));


        btnSignIn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email=tfEmail.getText();
                String password=tfPassword.getText();
                if (email.isBlank() || password.isBlank()) {
                    JOptionPane.showMessageDialog(null,"Incorrect password or email adress. Please try again. ","Log in ERROR",JOptionPane.ERROR_MESSAGE) ;
                    tfEmail.setText(null);
                    tfPassword.setText(null);
                }
                else {
                    User.Type userType;
                    boolean found = false;
                    try {
                        users = (ArrayList<User>)Serializator.deserialize("usersList.ser");
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Credentials not registered in the system. Please register for a new account.", "ERROR : user not found", JOptionPane.ERROR_MESSAGE);
                        tfEmail.setText("");
                        tfPassword.setText("");
                    }
                    for(User u: users) {
                        if(u.getEmail().equals(email) && u.getPassword().equals(password)) {
                            userType = u.getUserType();
                            found = true;
                            switch (userType) {

                                case ADMIN -> {
                                    AdministratorWindow adminWindow = new AdministratorWindow(deliveryService);
                                    dispose();
                                }
                                case EMPLOYEE -> {
                                    EmployeeWindow employeeWindow = new EmployeeWindow(deliveryService);
                                }
                                case CLIENT -> {
                                    ClientWindow clientWindow = new ClientWindow(deliveryService, u.getUID());
                                    dispose();
                                }
                            }
                        }
                    }
                    if (!found) {
                        JOptionPane.showMessageDialog(null, "Credentials not registered in the system. Please register for a new account.", "ERROR : user not found", JOptionPane.ERROR_MESSAGE);
                        tfEmail.setText("");
                        tfPassword.setText("");
                    }
                }

            }
        });

        btnSignIn.setForeground(new Color(255, 255, 240));
        btnSignIn.setBackground(new Color(0, 139, 139));
        btnSignIn.setBounds(332, 245, 165, 38);
        contentPane.add(btnSignIn);

        btnSignUp = new JButton("Register");
        btnSignUp.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnSignUp.setBackground(new Color(0, 139, 139));
        btnSignUp.setForeground(new Color(255, 0, 0));
        btnSignUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Register.main(null);
                dispose();
            }
        });
        btnSignUp.setBounds(332, 293, 165, 38);
        contentPane.add(btnSignUp);

        JLabel lbTitleLabel = new JLabel("Welcome to DeliveryService");
        lbTitleLabel.setForeground(new Color(255, 255, 255));
        lbTitleLabel.setFont(new Font("Georgia", Font.BOLD, 40));
        lbTitleLabel.setBounds(136, 32, 598, 99);
        contentPane.add(lbTitleLabel);
        setVisible(true);

    }

    public JTextField getTfEmail() {
        return tfEmail;
    }

    public JPasswordField getTfPassword() {
        return tfPassword;
    }

    public JButton getBtnSignUp() {
        return btnSignUp;
    }

    public JButton getBtnSignIn() {
        return btnSignIn;
    }

    public DeliveryService getDeliveryService() {
        return deliveryService;
    }

    public void setDeliveryService(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    public static List<User> getUsers() {
        return users;
    }

    public static void setUsers(List<User> users) {
        LogInWindow.users = users;
    }
}
