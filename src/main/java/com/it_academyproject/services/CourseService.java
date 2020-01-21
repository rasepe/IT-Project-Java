package com.it_academyproject.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it_academyproject.domains.Course;
import com.it_academyproject.repositories.CourseRepository;

@Service
public class CourseService {
	
	@Autowired
	CourseRepository courseRepository;
	
	
	//Get All
	public List<Course> getAllCourse(){
		return courseRepository.findAll();
	}
	
	
	//Get course by user idDocument
	
	public List<Course> getCourseByDoc(String IdDocument){
		
		return courseRepository.findByUserStudentIdDocument(IdDocument);
		
	}
	
	//put course
	
	public boolean putCourse(Course course) {
		
		courseRepository.save(course);
		return true;
	}
	
	

}
