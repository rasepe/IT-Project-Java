package com.it_academyproject.Domains;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class VicAbsence {
	
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	@Id
	private int id;
	private Date dateMissing;
	@ManyToOne (fetch = FetchType.EAGER)
	@JoinColumn (name="student_id")
	private VicMyAppUser userStudent;
	
	public VicAbsence() {
		
	}
	public VicAbsence(int id, Date dateMissing) {
		
		this.id = id;
		this.dateMissing = dateMissing;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDateMissing() {
		return dateMissing;
	}

	public void setDateMissing(Date dateMissing) {
		this.dateMissing = dateMissing;
	}

	public VicMyAppUser getUserStudent() {
		return userStudent;
	}

	public void setUserStudent(VicMyAppUser userStudent) {
		this.userStudent = userStudent;
	}

	@Override
	public String toString() {
		return "Absence [id=" + id + ", dateMissing=" + dateMissing + ", user=" + userStudent + "]";
	}

	
}


