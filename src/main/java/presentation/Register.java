package presentation;

import data.Serializator;
import model.User;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Register extends JFrame {

    private JPanel contentPane;
    private JTextField tfEmail;
    private JPasswordField tfPassword;
    private JButton btnSignUp;
    private JButton btnSignIn;
    private JTextField tfName;
    private JButton btnBack;
    private JLabel lblUserType;
    private JComboBox cbUserType;
    private List<User> users = new ArrayList<>();
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Register frame = new Register();
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

    public Register() {
        initialize();
    }

    public void initialize() {
        setBackground(new Color(144, 238, 144));
        setVisible(true);
        setTitle("Register");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 865, 489);

        List<User> ul = Arrays.asList((User)Serializator.deserialize("usersList.ser"));
        if(ul.get(0) != null )
            users.addAll(ul);

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
        lblPassword.setBounds(250, 192, 94, 14);
        contentPane.add(lblPassword);

        tfEmail = new JTextField();
        tfEmail.setBounds(361, 155, 200, 25);
        contentPane.add(tfEmail);
        tfEmail.setColumns(10);

        tfPassword = new JPasswordField();
        tfPassword.setBounds(361, 190, 200, 25);
        contentPane.add(tfPassword);

        btnSignIn = new JButton("Sign in");
        btnSignIn.setFont(new Font("Tahoma", Font.BOLD, 15));

        btnSignIn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LogInWindow logInWindow = new LogInWindow();
                dispose();
            }
        });

        btnSignIn.setForeground(new Color(255, 255, 240));
        btnSignIn.setBackground(new Color(0, 139, 139));
        btnSignIn.setBounds(320, 271, 165, 38);
        contentPane.add(btnSignIn);

        btnSignUp = new JButton("Register");
        btnSignUp.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnSignUp.setBackground(new Color(0, 139, 139));
        btnSignUp.setForeground(new Color(255, 0, 0));
        btnSignUp.addActionListener(e -> {
            String email = tfEmail.getText();
            String password = tfPassword.getText();
            String name = tfName.getText();
            String userType = cbUserType.getSelectedItem().toString();
            if (email.isBlank() || password.isBlank() || name.isBlank() || cbUserType.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(null, "Fill in all the fields. ", "Sign in ERROR", JOptionPane.ERROR_MESSAGE);
                tfEmail.setText(null);
                tfPassword.setText(null);
                cbUserType.setSelectedItem(null);
                tfName.setText(null);
            } else {
                boolean added = false;
                User userToRegister = new User(email,password,name);
                switch (userType) {
                    case "ADMIN" -> { userToRegister.setUserType(User.Type.ADMIN); }
                    case "EMPLOYEE" -> { userToRegister.setUserType(User.Type.EMPLOYEE); }
                    case "CLIENT" -> { userToRegister.setUserType(User.Type.CLIENT); }
                }
                userToRegister.setUID(users.size()+1);
                users.add(userToRegister);
                try {
                    Serializator.serialize(users, "usersList.ser");
                    added = true;
                } catch(Exception exception) {
                    exception.printStackTrace();
                }

                if (!added) {
                    JOptionPane.showMessageDialog(null, "Credentials not registered in the system. Please register for a new account.", "ERROR : user not found", JOptionPane.ERROR_MESSAGE);
                    tfEmail.setText("");
                    tfPassword.setText("");
                    tfName.setText(null);
                }
            }
        });
        btnSignUp.setBounds(320, 319, 165, 38);
        contentPane.add(btnSignUp);

        JLabel lbTitleLabel = new JLabel("Create a new account");
        lbTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lbTitleLabel.setForeground(new Color(255, 255, 255));
        lbTitleLabel.setFont(new Font("Georgia", Font.BOLD, 40));
        lbTitleLabel.setBounds(136, 32, 598, 99);
        contentPane.add(lbTitleLabel);

        JLabel lblName = new JLabel("Name");
        lblName.setForeground(Color.WHITE);
        lblName.setFont(new Font("Tahoma", Font.BOLD, 17));
        lblName.setBounds(250, 122, 94, 14);
        contentPane.add(lblName);

        tfName = new JTextField();
        tfName.setBounds(361, 120, 200, 25);
        contentPane.add(tfName);

        btnBack = new JButton("Back");
        btnBack.setForeground(Color.WHITE);
        btnBack.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnBack.setBackground(new Color(176, 224, 230));
        btnBack.setBounds(10, 10, 87, 21);
        btnBack.addActionListener( e-> {
                LogInWindow logInWindow = new LogInWindow();
                dispose();
            }
        );
        contentPane.add(btnBack);

        lblUserType = new JLabel("User type");
        lblUserType.setForeground(Color.WHITE);
        lblUserType.setFont(new Font("Tahoma", Font.BOLD, 17));
        lblUserType.setBounds(250, 222, 94, 20);
        contentPane.add(lblUserType);

        cbUserType = new JComboBox();
        cbUserType.setFont(new Font("Tahoma",Font.BOLD, 10));
        cbUserType.setModel(new DefaultComboBoxModel(new String[] {"CLIENT", "ADMIN", "EMPLOYEE"}));
        cbUserType.setBounds(361, 223, 200, 25);
        contentPane.add(cbUserType);
    }
}
