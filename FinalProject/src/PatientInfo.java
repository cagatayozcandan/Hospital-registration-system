import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import java.awt.Color;
import javax.swing.JList;
import javax.swing.JOptionPane;
import java.awt.Window.Type;

public class PatientInfo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNewPatientId;
	private JTextField txtNewName;
	private JTextField txtNewSurname;
	private JComboBox<String> cbNewPatientAge;
	private JPanel panel;
	private DefaultListModel<String> model;
	private JList<String> list;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PatientInfo frame = new PatientInfo();
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
	public PatientInfo() {
		setTitle("HOSPITAL");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(150, 150, 450, 343);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnNewButton_1 = new JButton("Add Patient");
		btnNewButton_1.setFont(new Font("Zilla Slab Medium", Font.ITALIC, 17));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(42, 11, 129, 37);
		contentPane.add(btnNewButton_1);

		panel = new JPanel();
		panel.setBackground(new Color(95, 158, 160));
		panel.setBounds(238, 11, 186, 239);
		contentPane.add(panel);
		panel.setLayout(null);
		panel.setVisible(false);

		JLabel lblNewLabel = new JLabel("Name");
		lblNewLabel.setBounds(10, 34, 46, 14);
		panel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Surname");
		lblNewLabel_1.setBounds(10, 82, 56, 14);
		panel.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("ID");
		lblNewLabel_2.setBounds(10, 128, 46, 14);
		panel.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Age");
		lblNewLabel_3.setBounds(10, 172, 46, 14);
		panel.add(lblNewLabel_3);

		txtNewName = new JTextField();
		txtNewName.setBounds(90, 31, 86, 20);
		panel.add(txtNewName);
		txtNewName.setColumns(10);

		txtNewSurname = new JTextField();
		txtNewSurname.setBounds(90, 79, 86, 20);
		panel.add(txtNewSurname);
		txtNewSurname.setColumns(10);

		txtNewPatientId = new JTextField();
		txtNewPatientId.setBounds(90, 125, 86, 20);
		panel.add(txtNewPatientId);
		txtNewPatientId.setColumns(10);

		cbNewPatientAge = new JComboBox<>();
		cbNewPatientAge.setModel(new DefaultComboBoxModel<>(new String[] {"UnderAge", "Adult"}));
		cbNewPatientAge.setBounds(90, 168, 86, 22);
		panel.add(cbNewPatientAge);

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					Patient p = new Patient();
					p.setPatientId( Integer.parseInt( txtNewPatientId.getText() ) );
					
					
					p.setName(txtNewName.getText());
					p.setSurname(txtNewSurname.getText());
					p.setPatientAge(cbNewPatientAge.getSelectedItem().toString());
					
					
					
					String patientId = txtNewPatientId.getText();
					String name = txtNewName.getText();
					String surname = txtNewSurname.getText();
					String age = cbNewPatientAge.getSelectedItem().toString();
					
					if (patientId.isEmpty() || name.isEmpty() || surname.isEmpty()) {
						JOptionPane.showMessageDialog(contentPane, "Please fill all fields");
					} else {
						String patientInfo = patientId + " " + name + " " + surname + " " + age;
						model.addElement(patientInfo);
						
						JOptionPane.showMessageDialog(contentPane, "Patient Saved!");
						
						txtNewPatientId.setText("");
						txtNewName.setText("");
						txtNewSurname.setText("");
						cbNewPatientAge.setSelectedIndex(0);
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btnSave.setBounds(10, 205, 75, 23);
		panel.add(btnSave);

		model = new DefaultListModel<>();
		list = new JList<>(model);
		list.setBounds(21, 94, 186, 199);
		contentPane.add(list);

		JLabel lblNewLabel_4 = new JLabel("Patient List");
		lblNewLabel_4.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 11));
		lblNewLabel_4.setBounds(73, 69, 68, 14);
		contentPane.add(lblNewLabel_4);
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnNewButton.setBounds(295, 261, 89, 23);
		contentPane.add(btnNewButton);
	}
}
