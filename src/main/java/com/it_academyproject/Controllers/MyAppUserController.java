package com.it_academyproject.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.it_academyproject.Services.MyAppUserService;
import com.it_academyproject.Tools.View;
import com.fasterxml.jackson.annotation.JsonView;
import com.it_academyproject.Domains.MyAppUser;

@RestController
public class MyAppUserController {
	
	@Autowired
	MyAppUserService myAppUserService;
	
	//Call for students
	@JsonView(View.Summary.class)
	@GetMapping("/api/students")
	public List<MyAppUser> getAllStudents(){
		return myAppUserService.getAll();
	}
	
	//Call students by name
	@JsonView(View.Summary.class)
	@GetMapping("api/students/name")
	public List<MyAppUser> getStudentsByName(@RequestBody MyAppUser student){
		return myAppUserService.getByName(student.getFirstName());
	}
	
	//Call students by surname
	@JsonView(View.Summary.class)
	@GetMapping("api/students/surname")
	public List<MyAppUser> getStudentsBySurname(@RequestBody MyAppUser student){
		return myAppUserService.getBySurname(student.getLastName());
	}
	
	//Call students by dni
	@JsonView(View.Summary.class)
	@GetMapping("api/students/dni")
	public MyAppUser getStudentByDni(@RequestBody MyAppUser student){
		return myAppUserService.getByDni(student.getIdDocument());
	}
		
	//Call student by Id
	@JsonView(View.SummaryWithOthers.class)
	@GetMapping("api/students/id")
	public MyAppUser getStudentById(@RequestBody MyAppUser student){
		return myAppUserService.getById(student.getId());
	}
	
		

}
