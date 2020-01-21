package com.it_academyproject.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.it_academyproject.domains.Course;
import com.it_academyproject.domains.MyAppUser;
import com.it_academyproject.services.CourseService;


@RestController
public class CourseController {
	
	@Autowired
	CourseService courseService;
	
	

	@GetMapping("api/course")
	public List<Course> getAllCourses(){
		return courseService.getAllCourse();
	}
	
	@GetMapping("api/course/dni")
	public List<Course> getCourseByDni(@RequestBody MyAppUser student){
		
		
		return courseService.getCourseByDoc(student.getIdDocument());
		
	}
	
	@PutMapping("api/course/dni")
	public boolean putCourse(@RequestBody Course course) {
		
		
		
		courseService.putCourse( course);
		return true;
	}
	

}
