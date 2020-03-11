package com.it_academyproject.domains;
import java.util.Date;

import com.it_academyproject.exceptions.EmptyFieldException;

public class Student extends MyAppUser {

	public Student(String email, String password) throws EmptyFieldException
	{
		if ((email != "")&&(password!=""))
		{
			this.email = email;
			this.password = password;
			this.lastLogin = new Date();
			this.enabled = true;
		}
		else if (email == "")
		{
			throw (new EmptyFieldException("email"));
		}
		else if ( password == "" )
		{
			throw (new EmptyFieldException("password"));
		}
	}

	public Student(String firstName, String lastName, String idDocument, String email, char gender,
			String portrait, String password, boolean enabled, Role role) throws EmptyFieldException {

		
		super(firstName, lastName, idDocument, email, gender, portrait, password, enabled, role);
	}

	public Student() {
		
	}



}
