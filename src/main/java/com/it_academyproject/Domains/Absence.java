package com.it_academyproject.Domains;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Absence {
	
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	@Id
	private int id;
	private String dateMissing;
	@ManyToOne (fetch = FetchType.EAGER)
	@JoinColumn (name="student_id")
	private MyAppUser userStudent;
	
	public Absence() {
		
	}
	public Absence(int id, String dateMissing) {
		
		this.id = id;
		this.dateMissing = dateMissing;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDateMissing() {
		return dateMissing;
	}

	public void setDateMissing(String dateMissing) {
		this.dateMissing = dateMissing;
	}

	@Override
	public String toString() {
		return "Absence [id=" + id + ", dateMissing=" + dateMissing + ", user=" + userStudent + "]";
	}

	
}


