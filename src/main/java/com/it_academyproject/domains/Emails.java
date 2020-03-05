package com.it_academyproject.domains;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Emails {
	
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	@Id
	private int id;
	private boolean sent;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="student_id")
	private MyAppUser userStudent;

	
	
	public Emails() {
		super();
	}
	

	public Emails(int id, boolean sent) {
		super();
		this.id = id;
		this.sent = sent;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isSent() {
		return sent;
	}

	public void setSent(boolean sent) {
		this.sent = sent;
	}

	public MyAppUser getUserStudent() {
		return userStudent;
	}

	public void setUserStudent(MyAppUser userStudent) {
		this.userStudent = userStudent;
	}

	
	
}