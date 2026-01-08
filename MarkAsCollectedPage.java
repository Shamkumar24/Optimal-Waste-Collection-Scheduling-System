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

public class MarkAsCollectedPage implements ActionListener {

    JFrame f;
    JLabel title, l1;
    JComboBox<String> areaBox;
    JButton collectBtn, backBtn;

    public void collectPage() {

        f = new JFrame("Mark as Collected");
        f.setSize(1920, 1080);
        f.setLayout(null);
        f.getContentPane().setBackground(Color.lightGray);
        f.setVisible(true);

        title = new JLabel("Mark Area as Collected");
        title.setBounds(700, 30, 350, 40);
        title.setFont(new Font("Times New Roman", Font.BOLD, 26));
        title.setForeground(Color.RED);
        f.add(title);

        // Area label
        l1 = new JLabel("Select Pending Area :");
        l1.setBounds(600, 120, 200, 30);
        l1.setFont(new Font("Arial", Font.PLAIN, 18));
        f.add(l1);

        // Area dropdown
        areaBox = new JComboBox<>();
        areaBox.setBounds(800, 120, 200, 30);
        f.add(areaBox);

        // Mark Collected button
        collectBtn = new JButton("Mark Collected");
        collectBtn.setBounds(650, 220, 150, 40);
        f.add(collectBtn);

        // Back button
        backBtn = new JButton("Back");
        backBtn.setBounds(900, 220, 150, 40);
        f.add(backBtn);

        collectBtn.addActionListener(this);
        backBtn.addActionListener(this);

        loadPendingAreas();

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Load only Pending areas
    public void loadPendingAreas() {

        try {
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/waste_db?useSSL=false&allowPublicKeyRetrieval=true",
                "root",
                "sham1234"   // 🔴 change
            );

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(
                "SELECT area_name FROM areas WHERE status = 'Pending'"
            );

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

        if (e.getSource() == collectBtn) {

            String area = (String) areaBox.getSelectedItem();

            if (area == null) {
                JOptionPane.showMessageDialog(f, "No Pending Areas");
                return;
            }

            try {
                Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/waste_db?useSSL=false&allowPublicKeyRetrieval=true",
                    "root",
                    "sham1234"   // 🔴 change
                );

                PreparedStatement ps = con.prepareStatement(
                    "UPDATE areas SET status = 'Collected' WHERE area_name = ?"
                );

                ps.setString(1, area);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(f, "Area Marked as Collected");

                ps.close();
                con.close();

                // Refresh page
                f.setVisible(false);
                new MarkAsCollectedPage().collectPage();

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
        MarkAsCollectedPage m = new MarkAsCollectedPage();
        m.collectPage();
    }
}
