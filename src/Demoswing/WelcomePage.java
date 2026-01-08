package Demoswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class WelcomePage implements ActionListener {

    JFrame f;
    JLabel title;
    JButton registerBtn, loginBtn;

    public void welcome() {

        f = new JFrame("Welcome");
        f.setSize(1920, 1080);
        f.setLayout(null);
        f.setVisible(true);

        // 🔹 Background Image
        ImageIcon bgImage = new ImageIcon(
            "C:\\Users\\shamk\\Downloads\\Garbage.jpg\\"
        );

        JLabel bg = new JLabel(bgImage);
        bg.setBounds(0, 0, 1920, 1080);
        f.setContentPane(bg);

        // 🔹 Project Title
        title = new JLabel("OPTIMAL WASTE COLLECTION SCHEDULING SYSTEM");
        title.setBounds(400, 50, 800, 100);
        title.setFont(new Font("Algerian", Font.BOLD, 28));
        title.setForeground(Color.BLUE);
        bg.add(title);

        // 🔹 Register Button
        registerBtn = new JButton("Register");
        registerBtn.setBounds(500, 250, 150, 40);
        registerBtn.setFont(new Font("Arial", Font.BOLD, 18));
        bg.add(registerBtn);

        // 🔹 Login Button
        loginBtn = new JButton("Login");
        loginBtn.setBounds(900, 250, 150, 40);
        loginBtn.setFont(new Font("Arial", Font.BOLD, 18));
        bg.add(loginBtn);

        registerBtn.addActionListener(this);
        loginBtn.addActionListener(this);

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        WelcomePage w = new WelcomePage();
        w.welcome();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == registerBtn) {
            f.setVisible(false);
            RegisterPage r = new RegisterPage();
            r.register();
        }

        if (e.getSource() == loginBtn) {
            f.setVisible(false);
            LoginPage l = new LoginPage();
            l.login();
        }
    }
}
