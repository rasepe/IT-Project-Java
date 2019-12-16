package com.it_academyproject.Domains;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;
import com.it_academyproject.Exceptions.EmptyFieldException;
import com.it_academyproject.Tools.View;



@Entity
@Table(name="users")
public class MyAppUser {
	
	//@GeneratedValue(strategy=GenerationType.IDENTITY)	
	@Id
	@JsonView(View.Summary.class)
	private String id;
	
	@JsonView(View.Summary.class)
	private String firstName;
	
	@JsonView(View.Summary.class)
	private String lastName;
	
	@JsonView(View.Summary.class)
	private String idDocument;
	
	@JsonView(View.SummaryWithOthers.class)
	private String email;
	
	@JsonView(View.SummaryWithOthers.class)
	private byte gender;
	
	@JsonView(View.SummaryWithOthers.class)
	private String portrait;
	
	private String password;
	private boolean enabled;
	private Date lastLogin;
	
	@ManyToOne (fetch = FetchType.EAGER)
	@JoinColumn (name="rol_id")
	private Rol rol;
	
	@OneToMany (targetEntity = Absence.class, cascade = CascadeType.ALL)
	private List <Absence> absences = new ArrayList <Absence>();
	@OneToMany (targetEntity = Course.class, cascade = CascadeType.ALL)
	private List <Course> courses = new ArrayList <Course>();
	@OneToMany (targetEntity = UserExercice.class, cascade = CascadeType.ALL)
	private List <UserExercice> userExercices = new ArrayList <UserExercice>();
	
	public MyAppUser() {
		
	}
	
	public MyAppUser(String firstName, String lastName, String idDocument, String email, byte gender,
			String portrait, String password, boolean enabled, Rol rol) {
		
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.idDocument = idDocument;
		this.email = email;
		this.gender = gender;
		this.portrait = portrait;
		this.password = password;
		this.enabled = enabled;
		this.rol = rol;
	}
	
	 public MyAppUser(String email, String password) throws EmptyFieldException
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

	public String getId() {
		return id;
	}

	public void setId(UUID id) {
		UUID uuid = UUID.randomUUID();
		this.id = id.toString();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getIdDocument() {
		return idDocument;
	}

	public void setIdDocument(String idDocument) {
		this.idDocument = idDocument;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public byte getGender() {
		return gender;
	}

	public void setGender(byte gender) {
		this.gender = gender;
	}

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}
	
	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	


/*	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", idDocument=" + idDocument
				+ ", email=" + email + ", gender=" + gender + ", portrait=" + portrait + ", password=" + password
				+ ", enabled=" + enabled + "]";
	}	*/
	
	
	

}