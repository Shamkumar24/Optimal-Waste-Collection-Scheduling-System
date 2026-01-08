package Demoswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPage implements ActionListener {

    JFrame f;
    JLabel title, l1, l2;
    JTextField t1;
    JPasswordField t2;
    JButton loginBtn;

    // Method to open Login Page
    public void login() {

        f = new JFrame("Login");
        f.setSize(1920, 1080);
        f.setLayout(null);
        f.getContentPane().setBackground(Color.lightGray);
        f.setVisible(true);

        // Page Title
        title = new JLabel("Worker Login");
        title.setBounds(700, 40, 300, 40);
        title.setFont(new Font("Times New Roman", Font.CENTER_BASELINE, 28));
        title.setForeground(Color.RED);
        f.add(title);

        // Username
        l1 = new JLabel("Username :");
        l1.setBounds(600, 140, 200, 30);
        l1.setFont(new Font("Times New Roman", Font.CENTER_BASELINE, 18));
        f.add(l1);

        t1 = new JTextField();
        t1.setBounds(700, 140, 200, 30);
        t1.setFont(new Font("Aptos (Body)", Font.PLAIN, 20));
        f.add(t1);

        // Password
        l2 = new JLabel("Password :");
        l2.setBounds(600, 200, 200, 30);
        l2.setFont(new Font("Times New Roman", Font.CENTER_BASELINE, 18));
        f.add(l2);

        t2 = new JPasswordField();
        t2.setBounds(700, 200, 200, 30);
        t2.setFont(new Font("Aptos (Body)", Font.PLAIN, 20));
        f.add(t2);

        // Login Button
        loginBtn = new JButton("Login");
        loginBtn.setBounds(730, 280, 150, 40);
        loginBtn.setFont(new Font("Arial", Font.BOLD, 20));
        f.add(loginBtn);

        loginBtn.addActionListener(this);

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Main method (for testing)
    public static void main(String[] args) {
        LoginPage l = new LoginPage();
        l.login();
    }

    // Button Click Logic
    @SuppressWarnings("deprecation")
	@Override
    public void actionPerformed(ActionEvent e) {

        String username = t1.getText();
        String password = t2.getText();

        // Validation
        if (username.equals("") || password.equals("")) {
            JOptionPane.showMessageDialog(loginBtn, "Please fill all fields");
        } else {

            String url = "jdbc:mysql://localhost/waste_db";
            String user = "root";
            String pass = "sham1234"; // 🔴 change to your DB password

            try {
                Connection con = DriverManager.getConnection(url, user, pass);

                PreparedStatement ps = con.prepareStatement(
                        "SELECT * FROM workers WHERE username = ? AND password = ?");

                ps.setString(1, username);
                ps.setString(2, password);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    JOptionPane.showMessageDialog(loginBtn, "Login Successful");

                    // Open Dashboard Page
                    f.setVisible(false);
                    DashboardPage d = new DashboardPage();
                    d.dashboard();

                } else {
                    JOptionPane.showMessageDialog(loginBtn, "Invalid Username or Password");
                }

                rs.close();
                ps.close();
                con.close();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
