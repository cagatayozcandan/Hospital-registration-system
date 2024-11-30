
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PatientServices {

	public Connection getConnected() throws SQLException {
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital","root","root");
	}
	
	public ArrayList<Patient> getPatients() throws SQLException{
		
		Statement st = getConnected().createStatement(); 
		ResultSet rs = st.executeQuery("select * from patient");
		ArrayList<Patient> patients = new ArrayList<Patient>();
		while(rs.next()) {
			Patient patient = new Patient();
			patient.setPatientId(rs.getInt(1));
			patient.setName(rs.getString(2));
			patient.setSurname(rs.getString(3));
			patient.setPatientAge(rs.getString(4));
			patients.add(patient);
		}
		return patients;
	}
	
	public void savePatient(Patient p) throws SQLException {
		
		String query = "insert into patient values(?,?,?,?)";
		PreparedStatement ps = getConnected().prepareStatement(query);
		ps.setInt(1, p.getPatientId());
		ps.setString(2, p.getName());
		ps.setString(3, p.getSurname());
		ps.setString(4, p.getPatientAge());
		ps.executeUpdate();
	}
	
	
	
	
}
