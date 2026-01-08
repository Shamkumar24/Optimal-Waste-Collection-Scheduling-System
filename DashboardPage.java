package Demoswing;

import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DashboardPage {

    JFrame f;
    JLabel title;
    JTable table;
    JScrollPane sp;
    JButton refreshBtn, logoutBtn, updateBtn, collectBtn;
    DefaultTableModel model;

    // 🔹 Main Dashboard Method
    public void dashboard() {

        f = new JFrame("Waste Collection Dashboard");
        f.setSize(1920, 1080);
        f.setLayout(null);
        f.getContentPane().setBackground(Color.lightGray);
        f.setVisible(true);

        // Title
        title = new JLabel("Waste Collection Schedule");
        title.setBounds(600, 20, 500, 40);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.RED);
        f.add(title);

        // Table Columns
        String[] cols = { "Area Name", "Garbage Level", "Priority", "Status" };
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);

        sp = new JScrollPane(table);
        sp.setBounds(400, 90, 800, 400);
        f.add(sp);

        // Buttons
        updateBtn = new JButton("Update Garbage Level");
        updateBtn.setBounds(400, 550, 200, 45);
        f.add(updateBtn);

        refreshBtn = new JButton("Refresh");
        refreshBtn.setBounds(650, 550, 150, 45);
        f.add(refreshBtn);

        collectBtn = new JButton("Mark As Collected");
        collectBtn.setBounds(850, 550, 200, 45);
        f.add(collectBtn);

        logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(1100, 550, 150, 45);
        f.add(logoutBtn);

        // 🔴 IMPORTANT: Setup data + load table
        setupAreas();
        loadTableData();

        // Button Actions
        refreshBtn.addActionListener(e -> {
            model.setRowCount(0);
            loadTableData();
        });

        updateBtn.addActionListener(e -> {
            f.setVisible(false);
            GarbageLevelUpdatePage g = new GarbageLevelUpdatePage();
            g.updatePage();
        });

        collectBtn.addActionListener(e -> {
            f.setVisible(false);
            MarkAsCollectedPage m = new MarkAsCollectedPage();
            m.collectPage();
        });

        logoutBtn.addActionListener(e -> {
            f.setVisible(false);
            WelcomePage w = new WelcomePage();
            w.welcome();
        });

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // 🔹 Create table and insert default areas
    public void setupAreas() {

        try {
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/waste_db?useSSL=false&allowPublicKeyRetrieval=true",
                "root",
                "sham1234"   // 🔴 CHANGE THIS
            );

            Statement st = con.createStatement();

            st.executeUpdate(
                "CREATE TABLE IF NOT EXISTS areas (" +
                "area_id INT AUTO_INCREMENT PRIMARY KEY," +
                "area_name VARCHAR(100)," +
                "garbage_level VARCHAR(20)," +
                "status VARCHAR(20))"
            );

            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM areas");
            rs.next();

            if (rs.getInt(1) == 0) {
                st.executeUpdate(
                    "INSERT INTO areas(area_name, garbage_level, status) VALUES " +
                    "('Area 1','HIGH','Pending')," +
                    "('Area 2','MEDIUM','Pending')," +
                    "('Area 3','LOW','Pending')"
                );
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // 🔹 Load JTable Data
    public void loadTableData() {

        try {
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/waste_db?useSSL=false&allowPublicKeyRetrieval=true",
                "root",
                "sham1234"   // 🔴 CHANGE THIS
            );

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(
                "SELECT area_name, garbage_level, status FROM areas"
            );

            while (rs.next()) {

                String area = rs.getString("area_name");
                String level = rs.getString("garbage_level");
                String status = rs.getString("status");

                int priority;
                if (level.equalsIgnoreCase("HIGH")) {
                    priority = 1;
                } else if (level.equalsIgnoreCase("MEDIUM")) {
                    priority = 2;
                } else {
                    priority = 3;
                }

                model.addRow(new Object[] {
                    area, level, priority, status
                });
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // 🔹 Main method (testing)
    public static void main(String[] args) {
        DashboardPage d = new DashboardPage();
        d.dashboard();
    }
}
