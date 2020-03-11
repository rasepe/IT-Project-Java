package com.it_academyproject.domains;
import com.it_academyproject.exceptions.EmptyFieldException;

public class Admin extends MyAppUser {

	public Admin(String firstName, String lastName, String idDocument, String email, char gender,
			String portrait, String password, boolean enabled, Role role) {

		
		this.firstName = firstName;
		this.lastName = lastName;
		this.idDocument = idDocument;
		this.email = email;
		this.gender = gender;
		this.portrait = portrait;
		this.password = password;
		this.enabled = enabled;
		this.role = role;
	}

	
}
