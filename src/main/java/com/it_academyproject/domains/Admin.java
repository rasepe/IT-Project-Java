package com.it_academyproject.domains;
import com.it_academyproject.exceptions.EmptyFieldException;

public class Admin extends MyAppUser {

	public Admin(String firstName, String lastName, String idDocument, String email, char gender,
			String portrait, String password, boolean enabled, Role role) throws EmptyFieldException {

		
		super(firstName, lastName, idDocument, email, gender, portrait, password, enabled, role);
	}

	
	public Admin() {
		
	}
	
	
}
