package com.csumb.cst363;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@SuppressWarnings("unused")
@Controller   
public class ControllerPrescriptionFill {

	@Autowired
	private JdbcTemplate jdbcTemplate;


	/* 
	 * Patient requests form to search for prescription.
	 * Do not modify this method.
	 */
	@GetMapping("/prescription/fill")
	public String getfillForm(Model model) {
		model.addAttribute("prescription", new Prescription());
		return "prescription_fill";
	}


	/*
	 * Pharmacy fills prescription.
	 */
	@PostMapping("/prescription/fill")
	public String processFillForm(Prescription p, Model model) {

		System.out.println("processFillForm " + p);

		// TODO
		
		// obtain connection to database.
		
		// valid pharamcy name and address in the prescription object and obtain the
		// pharmacy id.
		
		
		// get prescription information for the rxid value and patient last name from
		// prescription object.
		
		// copy prescription information into the prescription object for display.
		
		// get cost of drug and copy into prescription for display.
		
		// update prescription table row with pharmacy id, fill date.

		model.addAttribute("message", "Prescription filled.");
		model.addAttribute("prescription", p);
		return "prescription_show";

		// if there is error
		// model.addAttribute("message", <error message>);
		// model.addAttribute("prescription", p);
		// return "prescription_fill";

	}

	/*
	 * return JDBC Connection using jdbcTemplate in Spring Server
	 */

	private Connection getConnection() throws SQLException {
		Connection conn = jdbcTemplate.getDataSource().getConnection();
		return conn;
	}

}