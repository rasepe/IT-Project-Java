package com.it_academyproject.domains;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Emails {

	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	@Id
	private int id;
	private boolean sent;

    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn (name="student_id")
	private MyAppUser userStudent;

	private EmailType emailType;

	private Date date;

	public Emails() {
		super();
	}

	public Emails(String type) {
		super();
		switch (type) {
		case "ABSENCES":
			this.setEmailType(EmailType.ABSENCES);
			break;
		case "DAYSLEFT15":
			this.setEmailType(EmailType.DAYSLEFT15);
			break;
		case "DAYSLEFT30":
			this.setEmailType(EmailType.DAYSLEFT30);
			break;
		}
		Date today = new Date();	
		this.setDate(today);
	}

	public Emails(int id, int type, boolean sent) {
		super();
		this.id = id;

		switch (type) {
		case 1:
			this.setEmailType(EmailType.ABSENCES);
			break;
		case 2:
			this.setEmailType(EmailType.DAYSLEFT15);
			break;
		case 3:
			this.setEmailType(EmailType.DAYSLEFT30);
			break;
		}

		this.sent = sent;
		Date today = new Date();	
		this.setDate(today);
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

	public EmailType getEmailType() {
		return emailType;
	}

	public void setEmailType(EmailType emailType) {
		this.emailType = emailType;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}