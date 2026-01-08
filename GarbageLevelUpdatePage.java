package Demoswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class GarbageLevelUpdatePage implements ActionListener {

    JFrame f;
    JLabel title, l1, l2;
    JComboBox<String> areaBox, levelBox;
    JButton updateBtn, backBtn;

    public void updatePage() {

        f = new JFrame("Update Garbage Level");
        f.setSize(1920, 1080);
        f.setLayout(null);
        f.getContentPane().setBackground(Color.lightGray);
        f.setVisible(true);

        title = new JLabel("Update Garbage Level");
        title.setBounds(700, 40, 300, 40);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setForeground(Color.RED);
        f.add(title);

        // Area label
        l1 = new JLabel("Select Area :");
        l1.setBounds(600, 120, 200, 30);
        l1.setFont(new Font("Arial", Font.PLAIN, 18));
        f.add(l1);

        // Area dropdown
        areaBox = new JComboBox<>();
        areaBox.setBounds(750, 120, 200, 30);
        f.add(areaBox);

        // Garbage level label
        l2 = new JLabel("Garbage Level :");
        l2.setBounds(600, 180, 200, 30);
        l2.setFont(new Font("Arial", Font.PLAIN, 18));
        f.add(l2);

        // Garbage level dropdown
        levelBox = new JComboBox<>(new String[]{"LOW", "MEDIUM", "HIGH"});
        levelBox.setBounds(750, 180, 200, 30);
        f.add(levelBox);

        // Update button
        updateBtn = new JButton("Update");
        updateBtn.setBounds(630, 260, 120, 40);
        f.add(updateBtn);

        // Back button
        backBtn = new JButton("Back");
        backBtn.setBounds(800, 260, 120, 40);
        f.add(backBtn);

        updateBtn.addActionListener(this);
        backBtn.addActionListener(this);

        loadAreas();

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Load areas from database
    public void loadAreas() {

        try {
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/waste_db?useSSL=false&allowPublicKeyRetrieval=true",
                "root",
                "sham1234"
            );

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT area_name FROM areas");

            while (rs.next()) {
                areaBox.addItem(rs.getString("area_name"));
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(f, ex.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == updateBtn) {

            String area = (String) areaBox.getSelectedItem();
            String level = (String) levelBox.getSelectedItem();

            try {
                Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/waste_db?useSSL=false&allowPublicKeyRetrieval=true",
                    "root",
                    "sham1234"
                );

                PreparedStatement ps = con.prepareStatement(
                    "UPDATE areas SET garbage_level = ?, status = 'Pending' WHERE area_name = ?"
                );

                ps.setString(1, level);
                ps.setString(2, area);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(f, "Garbage Level Updated");

                ps.close();
                con.close();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(f, ex.getMessage());
            }
        }

        if (e.getSource() == backBtn) {
            f.setVisible(false);
            DashboardPage d = new DashboardPage();
            d.dashboard();
        }
    }

    public static void main(String[] args) {
        GarbageLevelUpdatePage g = new GarbageLevelUpdatePage();
        g.updatePage();
    }
}
