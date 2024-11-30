import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainPage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainPage frame = new MainPage();
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
	public MainPage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("HOSPITAL");
		lblNewLabel.setFont(new Font("Imprint MT Shadow", Font.BOLD, 16));
		lblNewLabel.setBounds(148, 11, 112, 54);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("New Patient");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				PatientInfo p = new PatientInfo();
				p.setVisible(true);
			}
		});
		btnNewButton.setBounds(221, 113, 118, 34);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Patient Info");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Patients p = new Patients();
				p.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(51, 113, 112, 34);
		contentPane.add(btnNewButton_1);
	}
}
