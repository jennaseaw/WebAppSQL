package com.csumb.cst363;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
/*
 * Controller class for patient interactions.
 *   update patient profile.
 */
@SuppressWarnings("unused")
@Controller
public class ControllerPatientUpdate {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	

	/*
	 *  Display patient profile for patient id.
	 */
	@GetMapping("/patient/edit/{id}")
	public String getUpdateForm(@PathVariable int id, Model model) {

		System.out.println("getUpdateForm "+ id );  // debug
		
		// TODO

		// get a connection to the database
		// using patient id and patient last name from patient object
		// retrieve patient profile and doctor's last name
		
		

		Patient p = new Patient();
		p.setId(id);
		
		// update patient object with patient profile data
		
		model.addAttribute("patient", p);
		return "patient_edit";

		// if there is error
		// model.addAttribute("message", <error message>);
		// model.addAttribute("patient", p);
		// return "index";
	}
	
	
	/*
	 * Process changes to patient profile.  
	 */
	@PostMapping("/patient/edit")
	public String updatePatient(Patient p, Model model) {
		
		System.out.println("updatePatient " + p);  // for debug 
		
		// TODO

		// get a connection to the database
		// validate the doctor's last name and obtain the doctor id
		// update the patient's profile for street, city, state, zip and doctor id

		model.addAttribute("message", "Update successful.");
		model.addAttribute("patient", p);
		return "patient_show";

		// if there is error
		// model.addAttribute("message",  <error message>);
		// model.addAttribute("patient", p);
		// return "patient_edit";

	}

	/*
	 * return JDBC Connection using jdbcTemplate in Spring Server
	 */

	private Connection getConnection() throws SQLException {
		Connection conn = jdbcTemplate.getDataSource().getConnection();
		return conn;
	}

}
