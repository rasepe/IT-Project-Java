package com.it_academyproject.Domains;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class Course {
	
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	@Id
	private int id;
	private Date beginDate;
	private Date endDate;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="student_id")
	private MyAppUser userStudent;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="teacher_id")
	private MyAppUser userTeacher;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="itinerary_id")
	private Itinerary itinerary;
	
	/*@ManyToOne (targetEntity = MyAppUser.class)
	@JoinColumn (name="id", insertable=false, updatable=false)
	private MyAppUser user;
	@ManyToOne (targetEntity = Itinerary.class)
	@JoinColumn (name="id", insertable=false, updatable=false)
	private Itinerary itinerary;*/
	
	public Course() {
	}

	public Course(int id, Date beginDate, Date endDate) {
		this.id = id;
		this.beginDate = beginDate;
		this.endDate = endDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", beginDate=" + beginDate + ", endDate=" + endDate + "]";
	}

}
