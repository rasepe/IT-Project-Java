package com.it_academyproject.Domains;


import java.util.Date;

import javax.persistence.*;


@Entity
public class VicCourse {
	
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	@Id
	@Column(nullable = false)
	private int id;
	@Column(nullable = true)
	private Date beginDate;
	@Column(nullable = true)
	private Date endDate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="student_id")
	private VicMyAppUser userStudent;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="teacher_id")
	private VicMyAppUser userTeacher;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="itinerary_id")
	private Itinerary itinerary;
	
	/*@ManyToOne (targetEntity = MyAppUser.class)
	@JoinColumn (name="id", insertable=false, updatable=false)
	private MyAppUser user;
	@ManyToOne (targetEntity = Itinerary.class)
	@JoinColumn (name="id", insertable=false, updatable=false)
	private Itinerary itinerary;*/
	
	public VicCourse() {
	}

	public VicCourse(int id, Date beginDate, Date endDate) {
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

	public VicMyAppUser getUserStudent() {
		return userStudent;
	}

	public void setUserStudent(VicMyAppUser userStudent) {
		this.userStudent = userStudent;
	}

	public VicMyAppUser getUserTeacher() {
		return userTeacher;
	}

	public void setUserTeacher(VicMyAppUser userTeacher) {
		this.userTeacher = userTeacher;
	}

	public Itinerary getItinerary() {
		return itinerary;
	}

	public void setItinerary(Itinerary itinerary) {
		this.itinerary = itinerary;
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", beginDate=" + beginDate + ", endDate=" + endDate + "]";
	}

}
