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

	public Student() {
		// TODO Auto-generated constructor stub
	}

}
