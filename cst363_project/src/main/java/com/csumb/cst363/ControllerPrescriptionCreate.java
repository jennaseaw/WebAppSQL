package com.csumb.cst363;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@SuppressWarnings("unused")
@Controller    
public class ControllerPrescriptionCreate {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/*
	 * Doctor requests blank form for new prescription.
	 * Do not modify this method.
	 */
	@GetMapping("/prescription/new")
	public String getPrescriptionForm(Model model) {
		model.addAttribute("prescription", new Prescription());
		return "prescription_create";
	}
	

	/*
	 * Doctor creates a prescription.
	 */
	@PostMapping("/prescription")
	public String createPrescription(Prescription p, Model model) {

		System.out.println("createPrescription " + p);
		
		
		try (Connection con = getConnection();) {
			PreparedStatement p1 = con.prepareStatement("select DoctorID from Doctor where lastName = ?");
			p1.setString(1,p.getDoctorLastName());
			
			ResultSet r1 = p1.executeQuery();
			if(r1.next()) {
				int doctorID = r1.getInt("doctorID");
				PreparedStatement p2 = con.prepareStatement("select PatientID from Patient where lastName = ?");
				p2.setString(1,p.getPatientLastName());
				ResultSet r2 = p2.executeQuery();
				if(r2.next()) {
					int patientID = r2.getInt("patientID");
					PreparedStatement p3 = con.prepareStatement("select DrugID from DRUG where DrugName = ?");
					p3.setString(1,p.getDrugName());
					ResultSet r3 = p3.executeQuery();
					if(r3.next()) {
						int drugID = r3.getInt("drugID");
						PreparedStatement ps = con.prepareStatement("insert into Prescription(DoctorID, PatientID, Quantity, DrugID) values(?, ?, ?, ?)", 
								Statement.RETURN_GENERATED_KEYS);
						ps.setInt(1, doctorID);
						ps.setInt(2, patientID);
						ps.setInt(3, p.getQuantity());
						ps.setInt(4, drugID);
						ps.executeUpdate();
						
						ResultSet rs = ps.getGeneratedKeys();
						if (rs.next()) p.setRxid(rs.getString(1));
						
						model.addAttribute("message", "Prescription created.");
						model.addAttribute("prescription", p);
						return "prescription_show";
						
					}else{
						model.addAttribute("message",  "Drug not found.");
						model.addAttribute("prescription", p);
						return "prescription_create";
					}
					
				}else {
					model.addAttribute("message",  "Patient not found.");
					model.addAttribute("prescription", p);
					return "prescription_create";
				}
			} else {
				model.addAttribute("message",  "Doctor not found.");
				model.addAttribute("prescription", p);
				return "prescription_create";
			}
			
		} catch (SQLException e) {
			model.addAttribute("message", "SQL Error."+e.getMessage());
			model.addAttribute("patient", p);
			return "prescription_create";
		}


		// TODO

		/*-
		 * Process the new prescription form. 
		 * 1. Obtain connection to database. 
		 * 2. Validate that doctor id and name exists 
		 * 3. Validate that patient id and name exists 
		 * 4. Validate that Drug name exists and obtain drug id. 
		 * 5. Insert new prescription 
		 * 6. Get generated value for rxid 
		 * 7. Update prescription object and return
		 */

		
	}
	
	/*
	 * return JDBC Connection using jdbcTemplate in Spring Server
	 */

	private Connection getConnection() throws SQLException {
		Connection conn = jdbcTemplate.getDataSource().getConnection();
		return conn;
	}
	
}
