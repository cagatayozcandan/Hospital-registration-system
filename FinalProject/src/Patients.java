import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

public class Patients extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField idtxt;
    private JTextField Nametxt;
    private JTextField Surnametxt;
    private JTextField Agetxt;
    private String selectedpatientid;

    public Connection getConnected() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "root");
    }

    PreparedStatement ps;
    ResultSet rs;
    private JTable table;
    private JTextField Searchtext;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Patients frame = new Patients();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void table_load() {
        try (Connection conn = getConnected()) {
            ps = conn.prepareStatement("SELECT * FROM patient");
            rs = ps.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void table_search(String searchText) {
        try (Connection conn = getConnected()) {
            ps = conn.prepareStatement("SELECT * FROM patient WHERE name LIKE ?");
            ps.setString(1, "%" + searchText + "%");
            rs = ps.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Patients() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 672, 496);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel LabelShop = new JLabel("List");
        LabelShop.setFont(new Font("Tahoma", Font.BOLD, 20));
        LabelShop.setHorizontalAlignment(SwingConstants.CENTER);
        LabelShop.setBounds(320, 11, 232, 47);
        contentPane.add(LabelShop);

        JPanel PatientPanel = new JPanel();
        PatientPanel.setBackground(new Color(211, 211, 211));
        PatientPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Patients", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        PatientPanel.setBounds(286, 68, 310, 224);
        contentPane.add(PatientPanel);
        PatientPanel.setLayout(null);

        JLabel patinetlbl = new JLabel("Patientid");
        patinetlbl.setFont(new Font("Tahoma", Font.BOLD, 12));
        patinetlbl.setHorizontalAlignment(SwingConstants.LEFT);
        patinetlbl.setBounds(20, 35, 90, 28);
        PatientPanel.add(patinetlbl);

        JLabel Namelbl = new JLabel("Name");
        Namelbl.setHorizontalAlignment(SwingConstants.LEFT);
        Namelbl.setFont(new Font("Tahoma", Font.BOLD, 12));
        Namelbl.setBounds(20, 74, 90, 28);
        PatientPanel.add(Namelbl);

        JLabel Surnamelbl = new JLabel("Surname");
        Surnamelbl.setHorizontalAlignment(SwingConstants.LEFT);
        Surnamelbl.setFont(new Font("Tahoma", Font.BOLD, 12));
        Surnamelbl.setBounds(20, 112, 90, 28);
        PatientPanel.add(Surnamelbl);

        JLabel Agelbl = new JLabel("PatientAge");
        Agelbl.setHorizontalAlignment(SwingConstants.LEFT);
        Agelbl.setFont(new Font("Tahoma", Font.BOLD, 12));
        Agelbl.setBounds(20, 150, 90, 28);
        PatientPanel.add(Agelbl);

        idtxt = new JTextField();
        idtxt.setBounds(104, 41, 144, 19);
        PatientPanel.add(idtxt);
        idtxt.setColumns(10);

        Nametxt = new JTextField();
        Nametxt.setColumns(10);
        Nametxt.setBounds(104, 80, 144, 19);
        PatientPanel.add(Nametxt);

        Surnametxt = new JTextField();
        Surnametxt.setColumns(10);
        Surnametxt.setBounds(104, 118, 144, 19);
        PatientPanel.add(Surnametxt);

        Agetxt = new JTextField();
        Agetxt.setColumns(10);
        Agetxt.setBounds(104, 156, 144, 19);
        PatientPanel.add(Agetxt);

        JButton SaveButton = new JButton("Save");
        SaveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String patientid = idtxt.getText();
                String name = Nametxt.getText();
                String surname = Surnametxt.getText();
                String patientAge = Agetxt.getText();

                if (patientid.isEmpty() || name.isEmpty() || surname.isEmpty() || patientAge.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "All fields must be filled!");
                    return;
                }

                try (Connection conn = getConnected()) {
                    String query = "INSERT INTO patient (patientid, name, surname, patientAge) VALUES (?, ?, ?, ?)";
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setString(1, patientid);
                    ps.setString(2, name);
                    ps.setString(3, surname);
                    ps.setString(4, patientAge);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Patient Added");
                    table_load();

                    idtxt.setText("");
                    Nametxt.setText("");
                    Surnametxt.setText("");
                    Agetxt.setText("");
                    idtxt.requestFocus();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
                }
            }
        });
        SaveButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        SaveButton.setBounds(393, 303, 101, 47);
        contentPane.add(SaveButton);

        JButton ExitButton = new JButton("Exit");
        ExitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        ExitButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        ExitButton.setBounds(393, 380, 101, 47);
        contentPane.add(ExitButton);

        JButton UpdateButton = new JButton("Update");
        UpdateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String patientid = idtxt.getText();
                String name = Nametxt.getText();
                String surname = Surnametxt.getText();
                String patientAge = Agetxt.getText();

                if (patientid.isEmpty() || name.isEmpty() || surname.isEmpty() || patientAge.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "All fields must be filled!");
                    return;
                }

                if (selectedpatientid == null || selectedpatientid.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please select a patient to update!");
                    return;
                }

                try (Connection conn = getConnected()) {
                    String query = "UPDATE patient SET patientid = ?, name = ?, surname = ?, patientAge = ? WHERE patientid = ?";
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setString(1, patientid);
                    ps.setString(2, name);
                    ps.setString(3, surname);
                    ps.setString(4, patientAge);
                    ps.setString(5, selectedpatientid); 
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Patient Updated!");
                    table_load();

                    idtxt.setText("");
                    Nametxt.setText("");
                    Surnametxt.setText("");
                    Agetxt.setText("");
                    idtxt.requestFocus();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
                }
            }
        });
        UpdateButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        UpdateButton.setBounds(10, 360, 101, 47);
        contentPane.add(UpdateButton);

        JButton btnDelete = new JButton("Delete");
        btnDelete.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnDelete.setBounds(131, 360, 101, 47);
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectedpatientid == null || selectedpatientid.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Select a patient to delete!");
                    return;
                }

                try (Connection conn = getConnected()) {
                    String query = "DELETE FROM patient WHERE patientid = ?";
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setString(1, selectedpatientid); 
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Patient Deleted!");
                    table_load();
                    
                    Nametxt.setText("");
                    idtxt.setText("");
                    Surnametxt.setText("");
                    Agetxt.setText("");
                    idtxt.requestFocus();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
                }
            }
        });
        contentPane.add(btnDelete);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 43, 222, 306);
        contentPane.add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);

        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel.setBounds(35, 416, 20, 11);
        contentPane.add(panel);
        panel.setLayout(null);

        Searchtext = new JTextField();
        Searchtext.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                table_search(Searchtext.getText());
            }
        });
        Searchtext.setBounds(118, 33, 177, 20);
        panel.add(Searchtext);
        Searchtext.setColumns(10);

        JLabel SearchLabel = new JLabel("Search");
        SearchLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        SearchLabel.setBounds(10, 33, 80, 17);
        panel.add(SearchLabel);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                selectedpatientid = model.getValueAt(selectedRow, 0).toString();
                idtxt.setText(model.getValueAt(selectedRow, 0).toString());
                Nametxt.setText(model.getValueAt(selectedRow, 1).toString());
                Surnametxt.setText(model.getValueAt(selectedRow, 2).toString());
                Agetxt.setText(model.getValueAt(selectedRow, 3).toString());
            }
        });

        table_load();
    }
}
