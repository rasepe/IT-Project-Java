package com.it_academyproject.Domains;


import com.it_academyproject.Exceptions.EmptyFieldException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.*;


@Entity
@Table(name="users")
public class VicMyAppUser {
	
	//@GeneratedValue(strategy=GenerationType.IDENTITY)	
	@Id
	@Column(nullable = false)
	private String id;
	@Column(nullable = false)
	private String firstName;
	@Column(nullable = false)
	private String lastName;
	@Column(nullable = false)
	private String idDocument;
	@Column(nullable = true)
	private Date birthday;
	@Column(nullable = false)
	private String email;
	@Column(nullable = false)
	private char gender;
	@Column(nullable = true)
	private String portrait;
	@Column(nullable = true)
	private String password;
	@Column(nullable = true)
	private boolean enabled;
	@Column(nullable = true)
	private Date lastLogin;
	
	@ManyToOne (fetch = FetchType.EAGER)
	@JoinColumn (name="rol_id")
	private Role role;
	
	@OneToMany (targetEntity = VicAbsence.class, cascade = CascadeType.ALL)
	private List <VicAbsence> vicAbsences = new ArrayList <VicAbsence>();
	@OneToMany (targetEntity = VicCourse.class, cascade = CascadeType.ALL)
	private List <VicCourse> cours = new ArrayList <VicCourse>();
	@OneToMany (targetEntity = UserExercice.class, cascade = CascadeType.ALL)
	private List <UserExercice> userExercices = new ArrayList <UserExercice>();
	
	public VicMyAppUser() {
		
	}
	
	public VicMyAppUser(String firstName, String lastName, String idDocument, String email, char gender,
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
	
	public VicMyAppUser(String email, String password) throws EmptyFieldException
	{
		this.setId();
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

	public void setId()
	{
		UUID uuid = UUID.randomUUID();
		this.id = uuid.toString();
	}
	public void setId ( String id )
	{
		this.id = id;
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

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", idDocument=" + idDocument
				+ ", email=" + email + ", gender=" + gender + ", portrait=" + portrait + ", password=" + password
				+ ", enabled=" + enabled + "]";
	}	

}