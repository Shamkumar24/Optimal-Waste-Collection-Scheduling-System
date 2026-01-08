package Demoswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class RegisterPage implements ActionListener {

    JFrame f;
    JLabel l, l1, l2, l3, l4;
    JTextField t1, t2, t3;
    JPasswordField t4;
    JButton b;

    // Register Page UI
    public void register() {

        f = new JFrame("Worker Register");
        f.setSize(1920,1080);
        f.getContentPane().setBackground(Color.lightGray);
        f.setLayout(null);
        f.setVisible(true);

        l = new JLabel("Worker Register");
        l.setBounds(700, 40, 300, 40);
        l.setFont(new Font("Arial", Font.BOLD, 30));
        l.setForeground(Color.RED);
        f.add(l);

        l1 = new JLabel("Worker Name :");
        l1.setBounds(600, 150, 200, 30);
        l1.setFont(new Font("Times New Roman", Font.CENTER_BASELINE, 20));
        f.add(l1);

        t1 = new JTextField();
        t1.setBounds(750, 150, 200, 30);
        t1.setFont(new Font("Aptos (Body)", Font.PLAIN, 20));
        f.add(t1);

        l2 = new JLabel("Phone No :");
        l2.setBounds(600, 200, 200, 30);
        l2.setFont(new Font("Times New Roman", Font.CENTER_BASELINE, 20));
        f.add(l2);

        t2 = new JTextField();
        t2.setBounds(750, 200, 200, 30);
        t2.setFont(new Font("Aptos (Body)", Font.PLAIN, 20));
        f.add(t2);

        l3 = new JLabel("Username :");
        l3.setBounds(600, 250, 200, 30);
        l3.setFont(new Font("Times New Roman", Font.CENTER_BASELINE, 20));
        f.add(l3);

        t3 = new JTextField();
        t3.setBounds(750, 250, 200, 30);
        t3.setFont(new Font("Aptos (Body)", Font.PLAIN, 20));
        f.add(t3);

        l4 = new JLabel("Password :");
        l4.setBounds(600, 300, 200, 30);
        l4.setFont(new Font("Times New Roman", Font.CENTER_BASELINE, 20));
        f.add(l4);

        t4 = new JPasswordField();
        t4.setBounds(750, 300, 200, 30);
        t4.setFont(new Font("Aptos (Body)", Font.PLAIN, 20));
        f.add(t4);

        b = new JButton("Register");
        b.setBounds(780, 360, 150, 45);
        b.setFont(new Font("Arial", Font.BOLD, 22));
        f.add(b);

        b.addActionListener(this);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Main method
    public static void main(String[] args) {
        RegisterPage r = new RegisterPage();
        r.register();
    }

    // Button click logic
    @Override
    public void actionPerformed(ActionEvent e) {

        String x1 = t1.getText();
        String x2 = t2.getText();
        String x3 = t3.getText();
        String x4 = new String(t4.getPassword()); // IMPORTANT

        if (x1.equals("") || x2.equals("") || x3.equals("") || x4.equals("")) {
            JOptionPane.showMessageDialog(b, "Fill all Fields");
        } else {

            try {
                // Connect to MySQL (no DB initially)
            	String url = "jdbc:mysql://localhost:3306/?useSSL=false&allowPublicKeyRetrieval=true";
                String user = "root";
                String pass = "sham1234"; // 🔴 change your DB password

                Connection c = DriverManager.getConnection(url, user, pass);
                Statement st = c.createStatement();

                // Create database & table automatically
                st.executeUpdate("CREATE DATABASE IF NOT EXISTS waste_db");
                st.executeUpdate("USE waste_db");
                st.executeUpdate(
                        "CREATE TABLE IF NOT EXISTS workers (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY," +
                        "name VARCHAR(100)," +
                        "phone VARCHAR(15)," +
                        "username VARCHAR(50)," +
                        "password VARCHAR(50))"
                );

                // Insert data
                PreparedStatement ps = c.prepareStatement(
                        "INSERT INTO workers(name, phone, username, password) VALUES (?,?,?,?)");

                ps.setString(1, x1);
                ps.setString(2, x2);
                ps.setString(3, x3);
                ps.setString(4, x4);
                ps.execute();

                JOptionPane.showMessageDialog(b, "Registered Successfully");

                ps.close();
                st.close();
                c.close();

                // Go to Login Page
                f.setVisible(false);
                LoginPage l = new LoginPage();
                l.login();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(b, ex.getMessage());
            }
        }
    }
}
